package com.huan.HTed.account.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.account.constants.UserConstants;
import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.exception.UserException;
import com.huan.HTed.account.service.IUserInfoService;
import com.huan.HTed.account.service.IUserService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.security.PasswordManager;


@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {

    private final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    // 跳转
    protected static final String REDIRECT = "redirect:";

    private static final Long BASE_MENBER_EIGHT = 8L;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private PasswordManager passwordManager;
   
   
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User update(IRequest request, User user) throws Exception {
        user = userService.updateByPrimaryKeySelective(request, user);
        if (user == null) {
            throw new UserException(UserException.USER_UPDATE_FAIL, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("成功update USER {}", user.getUserId());
        }
        return user;
    }
    
    
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User selectUserByPrimaryKey(IRequest request, Long userId) throws UserException {
        User user = new User();
        user.setUserId(userId);
        List<User> checkUsers = userService.select(request, user, UserConstants.DEFAULT_PAGE,
                UserConstants.DEFAULT_PAGE_SIZE);
        if (checkUsers.isEmpty()) {
            throw new UserException(UserException.USER_NOT_EXIST, null);
        }
        User checkUser = checkUsers.get(0);
        return checkUser;
    }

    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long userId)
            throws UserException {
        // 不为空则是用户密码过期  不需要验证旧密码
        Object expire = request.getAttribute(User.PASSWORD_EXPIRE_VERIFY);
        
        // 密码不为空校验
        if(expire == null){
            if ("".equals(oldPwd) || "".equals(newPwd) || "".equals(newPwdAgain)) {
                throw new UserException(UserException.PASSWORD_NOT_EMPTY, null);
            }
        }else{
            //不验证旧密码
            if ("".equals(newPwd) || "".equals(newPwdAgain)) {
                throw new UserException(UserException.PASSWORD_NOT_EMPTY, null);
            }
        }
        
        Integer length = passwordManager.getPasswordMinLength();
        String  complexity = passwordManager.getPasswordComplexity();
        if(newPwd.length()<length)
        { 
        	throw new UserException(UserException.USER_PASSWORD_LENGTH_INSUFFICIENT, null);
        	
        }else{
        	if("no_limit".equals(complexity))
        	{
        	
        	}else if("digits_and_letters".equals(complexity)&&!newPwd.matches(".*[0-9]+.*")&&!newPwd.matches(".*[a-zA-Z]+.*")){
        		throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
        	}else if("digits_and_case_letters".equals(complexity)&&!newPwd.matches(".*[a-z]+.*")&&!newPwd.matches(".*[A-Z]+.*")&&!newPwd.matches(".*[0-9]+.*"))
        	{
        		throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
        	}
        }
        // 两次密码一致性检查
        if (!newPwd.equals(newPwdAgain)) {
            throw new UserException(UserException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }
        // 获取指定用户密码
        User tmp = new User();
        tmp.setUserId(userId);
        
        User userInDB = userService.selectByPrimaryKey(request, tmp);
        String pwd = userInDB.getPasswordEncrypted();
        // 验证旧密码是否正确
        // 密码过期 则不需要验证旧密码
        if(expire == null){
            if (!passwordManager.matches(oldPwd,pwd)) {
                throw new UserException(UserException.USER_PASSWORD_WRONG, null);
            }
        }
        // 验证新密码有效1-格式
        // String regPwdOne
        // ="^(?![^a-zA-Z]+$)(?!\\D+$)[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$";
       /* if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) {
            throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
        }*/
        // 验证新密码有效2-与旧密码不一致
        if (passwordManager.matches(newPwd,pwd)) {
            throw new UserException(UserException.USER_PASSWORD_SAME, null);
        }
        return true;

    }
}

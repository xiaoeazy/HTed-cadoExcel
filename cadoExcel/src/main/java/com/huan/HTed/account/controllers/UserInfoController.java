/*
 * #{copyright}#
 */
package com.huan.HTed.account.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.huan.HTed.account.constants.UserConstants;
import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.exception.UserException;
import com.huan.HTed.account.service.IUserInfoService;
import com.huan.HTed.account.service.IUserService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.security.PasswordManager;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;


@Controller
public class UserInfoController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    
    @Autowired
    private PasswordManager passwordManager;
    /**
     * 更新个人信息.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            包含需要更新的字段信息
     * @return 响应信息
     * @throws UserException
     *             抛出更新用户信息失败的异常
     * @throws Exception
     *             抛出短信接口异常
     */
    @RequestMapping(value = "/sys/um/updateUserInfo")
    public ResponseData updateUserInfo(HttpServletRequest request, @RequestBody User user) throws Exception {
        IRequest iRequest = createRequestContext(request);
        iRequest.setUserId(10001l);
        user.setUserId(iRequest.getUserId());
        
        // 匹配电话格式.
        if (!user.getPhone().matches(UserConstants.PHONE_REGEX)) {
            throw new UserException(UserException.PHONE_FORMAT, new Object[] {});
        }
        // 匹配邮箱格式.
        if (!user.getEmail().matches(UserConstants.EMAIL_REGEX)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[] {});
        }
        userInfoService.update(iRequest, user);
        List<User> list = new ArrayList<>();
        list.add(user);
        return new ResponseData(list);
    }


    @RequestMapping(value = "/sys/um/sys_user_info.html")
    @ResponseBody
    public ModelAndView userInfo(final HttpServletRequest request) throws UserException {
        ModelAndView mv = new ModelAndView(getViewPath() + "/sys/um/sys_user_info");
        IRequest requestContext = createRequestContext(request);
        requestContext.setUserId(10001l);
        User user = userInfoService.selectUserByPrimaryKey(requestContext, requestContext.getUserId());
        Integer length = passwordManager.getPasswordMinLength();
        String  complexity = passwordManager.getPasswordComplexity();
        mv.addObject("user", user);
        mv.addObject("length",length);
        mv.addObject("complexity",complexity);
        return mv;
    }
    
    
    /**
     * 用户登录密码更改,输入新密码点击确定修改密码.
     * 
     * @param request
     *            统一上下文
     * @param oldPwd
     *            当前密码
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            再次输入密码
     * @return 响应信息
     * @throws UserException
     *             抛出密码更新失败的异常
     */
    @RequestMapping(value = "/sys/um/updatePassword", method = RequestMethod.POST)
    public ResponseData updatePassword(HttpServletRequest request,String oldPwd, String newPwd, String newPwdAgain)
            throws UserException {
        IRequest iRequest = createRequestContext(request);
        Long accountId = iRequest.getUserId();
        HttpSession session = request.getSession(false);
        Object expire = session.getAttribute(User.PASSWORD_EXPIRE_VERIFY);
        iRequest.setAttribute(User.PASSWORD_EXPIRE_VERIFY,expire);
        if (userInfoService.validatePassword(iRequest, oldPwd, newPwd, newPwdAgain, accountId)) {
            userService.updatePassword(accountId, newPwd);
            //如果用户密码过期 修改成功后 清除session
            if(session != null){
                if(expire  !=null)
                    session.removeAttribute(User.PASSWORD_EXPIRE_VERIFY);
            }
            return new ResponseData(true); 
        }
        return new ResponseData(false);
    }
   
    /**
     * 管理员重置用户密码.
     * @param request
     * @param password
     * @return
     * @throws UserException
     */
    @RequestMapping(value = "/sys/um/resetPasswordAdmin", method = RequestMethod.POST)
    public ResponseData updatePassword(HttpServletRequest request, String password,Long userId) throws UserException {
        userService.updatePassword(userId, password);
        return new ResponseData(true);
    }

    

    
   
}

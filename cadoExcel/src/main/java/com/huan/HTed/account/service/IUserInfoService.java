package com.huan.HTed.account.service;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.exception.UserException;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;



public interface IUserInfoService extends ProxySelf<IUserInfoService> {

 
	 /**
     * 更新用户信息.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            用户信息
     * @return 响应信息
     * @throws UserException
     *             抛出更新用户失败异常
     * @throws Exception
     *             抛出短信接口异常
     */
    User update(IRequest request,  User ipointUser) throws UserException, Exception;
    /**
     * 忘记密码,接收用户ID查询用户详细信息.
     * 
     * @param request
     *            统一上下文
     * @param userId
     *            用户ID
     * @return 返回查询到的用户信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    User selectUserByPrimaryKey(IRequest request, Long userId) throws UserException;

    /**
     * 用户密码修改前校验参数是否有效.
     * 
     * @param request
     *            统一上下文
     * @param oldPwd
     *            原密码
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            新密码再次输入
     * @param accountId
     *            用户账户ID
     * @return 返回是否通过校验
     * @throws UserException
     *             抛出验证密码失败的业务异常
     */
    boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long accountId)
            throws UserException;

}


package com.huan.HTed.account.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.service.IUserService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.exception.BaseException;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;

@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 查询用户数据.
     *
     * @param user
     *            用户
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/user/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectUsers(HttpServletRequest request,@ModelAttribute User user, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(userService.select(iRequest, user, page, pagesize));
    }

    /**
     * 保存更新账户数据.
     * 
     * @param users
     *            用户
     * @param result
     *            BindingResult
     * @param request
     *            请求上下文
     * @return ResponseData ResponseData
     * @throws BaseException
     *             BaseException
     */
    @RequestMapping(value = "/sys/user/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitUsers(@RequestBody List<User> users, BindingResult result, HttpServletRequest request)
            throws BaseException {
        getValidator().validate(users, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
            IRequest requestCtx = createRequestContext(request);
            userService.batchUpdate(requestCtx, users);
            return new ResponseData(users);
        }
    }

    /**
     * 删除账户.
     *
     * @param users
     *            用户列表
     * @return ResponseData ResponseData
     * @throws BaseException
     *             BaseException
     */
    @RequestMapping(value = "/sys/user/remove", method = RequestMethod.POST)
    public ResponseData remove(@RequestBody List<User> users) throws BaseException {
        userService.batchDelete(users);
        return new ResponseData(users);
    }
    
    
    
    @RequestMapping(value = "/sys/user/common/{resource}", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getCommonData(@PathVariable String resource, @RequestParam Map<String, String> params,
            HttpServletRequest request) throws JsonProcessingException {
    	List<User> data = userService.selectAll();
        String var = params.get("var");
        StringBuilder sb = new StringBuilder();
        toJson(sb, var, data);
        return sb.toString();
    }
    
    protected void toJson(StringBuilder sb, String var, Object data) throws JsonProcessingException {
        boolean hasVar = var != null && var.length() > 0;
        if (hasVar) {
            sb.append("var ").append(var).append('=');
        }
        sb.append(objectMapper.writeValueAsString(data));
        if (hasVar) {
            sb.append(';');
        }
    }
}

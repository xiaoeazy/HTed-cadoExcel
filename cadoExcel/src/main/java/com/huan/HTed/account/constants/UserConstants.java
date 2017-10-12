
package com.huan.HTed.account.constants;


import com.huan.HTed.core.BaseConstants;


public class UserConstants implements BaseConstants {
	/**
     * 正则表达式-phone.
     */
    public static final String PHONE_REGEX = "^1[3|4|5|8][0-9]\\d{4,8}";
    /**
     * 正则表达式-email.
     */
    public static final String EMAIL_REGEX = "^([\\s\\S]*)+@([\\S\\s]*)+(\\.([\\S\\s]*)+)+$";
    
    /**
     * 调用userService的selectUsers时. 必须传入分页信息page
     */
    public static final Integer DEFAULT_PAGE = 1;
    /**
     * 调用userService的selectUsers时. 必须传入分页信息pageSize
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

}

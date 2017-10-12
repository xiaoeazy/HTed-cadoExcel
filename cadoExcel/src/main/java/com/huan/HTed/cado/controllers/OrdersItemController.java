package com.huan.HTed.cado.controllers;

import org.springframework.stereotype.Controller;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.dto.ResponseData;
import com.huan.HTed.cado.dto.OrdersItem;
import com.huan.HTed.cado.service.IOrdersItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class OrdersItemController extends BaseController{

    @Autowired
    private IOrdersItemService service;


    @RequestMapping(value = "/orders/item/query")
    @ResponseBody
    public ResponseData query(OrdersItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/orders/item/submit")
    @ResponseBody
	public ResponseData update(@RequestBody List<OrdersItem> dto, BindingResult result, HttpServletRequest request){
//		getValidator().validate(dto, result);
//		if (result.hasErrors()) {
//			ResponseData responseData = new ResponseData(false);
//			responseData.setMessage(getErrorMessage(result, request));
//			return responseData;
//		}
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.update(requestCtx, dto));
    }

    @RequestMapping(value = "/orders/item/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<OrdersItem> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }
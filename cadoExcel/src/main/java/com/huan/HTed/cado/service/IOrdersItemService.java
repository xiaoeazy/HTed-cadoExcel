package com.huan.HTed.cado.service;

import java.util.List;

import com.huan.HTed.cado.dto.Orders;
import com.huan.HTed.cado.dto.OrdersItem;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface IOrdersItemService extends IBaseService<OrdersItem>, ProxySelf<IOrdersItemService>{
	public List<OrdersItem> update(IRequest requestCtx ,List<OrdersItem> dto);
	
	public List<OrdersItem> queryForOrders(IRequest request, Orders condition, OrdersItem condition2, int pageNum, int pageSize);
}
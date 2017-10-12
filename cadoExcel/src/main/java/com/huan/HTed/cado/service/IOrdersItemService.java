package com.huan.HTed.cado.service;

import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

import java.util.List;

import com.huan.HTed.cado.dto.OrdersItem;

public interface IOrdersItemService extends IBaseService<OrdersItem>, ProxySelf<IOrdersItemService>{
	public List<OrdersItem> update(IRequest requestCtx ,List<OrdersItem> dto);
}
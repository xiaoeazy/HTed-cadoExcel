package com.huan.HTed.cado.service;

import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

import java.util.List;

import com.huan.HTed.cado.dto.Orders;
import com.huan.HTed.cado.dto.OrdersItem;

public interface IOrdersService extends IBaseService<Orders>, ProxySelf<IOrdersService>{
	public List<Orders> queryAll(IRequest request);
}
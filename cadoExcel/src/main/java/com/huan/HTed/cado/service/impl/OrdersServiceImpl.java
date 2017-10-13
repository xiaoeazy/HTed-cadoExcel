package com.huan.HTed.cado.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Orders;
import com.huan.HTed.cado.mapper.OrdersItemMapper;
import com.huan.HTed.cado.mapper.OrdersMapper;
import com.huan.HTed.cado.service.IOrdersService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements IOrdersService{
	@Autowired
	private OrdersMapper orderMapper;
	public List<Orders> queryAll(IRequest request){
        return orderMapper.queryAll();
	}
}
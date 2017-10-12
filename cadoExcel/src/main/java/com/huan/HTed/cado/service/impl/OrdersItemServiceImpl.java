package com.huan.HTed.cado.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.OrdersItem;
import com.huan.HTed.cado.dto.OrdersItemLog;
import com.huan.HTed.cado.mapper.OrdersItemMapper;
import com.huan.HTed.cado.service.IOrdersItemLogService;
import com.huan.HTed.cado.service.IOrdersItemService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.exception.UpdateFailedException;
import com.huan.HTed.system.dto.DTOStatus;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersItemServiceImpl extends BaseServiceImpl<OrdersItem> implements IOrdersItemService{
				@Autowired
				OrdersItemMapper OrdersItemMapper;
				@Autowired
				IOrdersItemLogService iOrdersItemLogService;
				@Override
				public List<OrdersItem> update(IRequest requestCtx ,List<OrdersItem> dtolist){
					Date nowDate = new Date();
					for(OrdersItem ordersItem:dtolist){
						Map<String,Object> map = new HashMap<String,Object>();
						OrdersItem oriordersItem = new OrdersItem();
						oriordersItem.setOrderNo(ordersItem.getOrderNo());
						oriordersItem.setUpdateTime(ordersItem.getUpdateTime());
						ordersItem.setUpdateTime(nowDate);
						map.put("ordersItem", ordersItem);
						map.put("oriOrdersItem", oriordersItem);
						int i = OrdersItemMapper.update(map);
						if(i==0){
							 throw new RuntimeException(new UpdateFailedException(ordersItem));
						}
					}
					
					List<OrdersItemLog> dtologlist = new ArrayList<OrdersItemLog>();
					for(OrdersItem ordersItem:dtolist){
						OrdersItemLog ordersitemLog = new OrdersItemLog();
						ordersitemLog.cloneOrdersItem(ordersItem);
						ordersitemLog.setUpdateTime(nowDate);
						ordersitemLog.setUpdateBz("修改");
						ordersitemLog.set__status(DTOStatus.ADD);
						dtologlist.add(ordersitemLog);
					}
					iOrdersItemLogService.batchUpdate(requestCtx, dtologlist);
					return dtolist;
				}
}
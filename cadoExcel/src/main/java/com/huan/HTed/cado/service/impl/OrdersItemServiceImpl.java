package com.huan.HTed.cado.service.impl;

import com.huan.HTed.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.huan.HTed.cado.dto.OrdersItem;
import com.huan.HTed.cado.service.IOrdersItemService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersItemServiceImpl extends BaseServiceImpl<OrdersItem> implements IOrdersItemService{

}
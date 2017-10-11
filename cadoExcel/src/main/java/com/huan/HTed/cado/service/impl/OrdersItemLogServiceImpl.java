package com.huan.HTed.cado.service.impl;

import com.huan.HTed.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.huan.HTed.cado.dto.OrdersItemLog;
import com.huan.HTed.cado.service.IOrdersItemLogService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersItemLogServiceImpl extends BaseServiceImpl<OrdersItemLog> implements IOrdersItemLogService{

}
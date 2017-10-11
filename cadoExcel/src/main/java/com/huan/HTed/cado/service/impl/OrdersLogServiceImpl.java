package com.huan.HTed.cado.service.impl;

import com.huan.HTed.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.huan.HTed.cado.dto.OrdersLog;
import com.huan.HTed.cado.service.IOrdersLogService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersLogServiceImpl extends BaseServiceImpl<OrdersLog> implements IOrdersLogService{

}
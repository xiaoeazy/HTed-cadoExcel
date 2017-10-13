package com.huan.HTed.cado.mapper;

import java.util.List;

import com.huan.HTed.cado.dto.Orders;
import com.huan.HTed.mybatis.common.Mapper;

public interface OrdersMapper extends Mapper<Orders>{
	public List<Orders> queryAll();
}
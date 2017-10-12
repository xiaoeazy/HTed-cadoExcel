package com.huan.HTed.cado.mapper;

import java.util.Map;

import com.huan.HTed.cado.dto.OrdersItem;
import com.huan.HTed.mybatis.common.Mapper;

public interface OrdersItemMapper extends Mapper<OrdersItem>{
			public int update(Map<String, Object> params);
}
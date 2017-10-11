package com.huan.HTed.cado.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.huan.HTed.cado.dto.Orders;
import com.huan.HTed.cado.dto.OrdersItem;
import com.huan.HTed.cado.dto.OrdersItemLog;
import com.huan.HTed.cado.dto.OrdersLog;
import com.huan.HTed.cado.service.IOrdersItemLogService;
import com.huan.HTed.cado.service.IOrdersItemService;
import com.huan.HTed.cado.service.IOrdersLogService;
import com.huan.HTed.cado.service.IOrdersService;
import com.huan.HTed.cado.service.IUploadExcelService;
import com.huan.HTed.cado.util.ExcelUtil;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.dto.DTOStatus;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class UploadExcelServiceImpl extends BaseServiceImpl<Orders> implements IUploadExcelService{
	
	@Autowired
	private IOrdersService iOrdersService;
	@Autowired
	private IOrdersLogService iOrdersLogService;
	@Autowired
	private IOrdersItemService iOrdersItemService;
	@Autowired
	private IOrdersItemLogService iOrdersItemLogService;
	
	
	public void fildUpload(IRequest requestCtx ,MultipartFile file) throws Exception{
		    Date updateUpdate = new Date();
			 //获得文件类型（可以判断如果不是图片，禁止上传）  
		    String contentType=file.getContentType();  
		    //获得文件名
		    String excelName=file.getOriginalFilename();
		    System.out.println(contentType);
		    System.out.println(excelName);
		 	ExcelUtil excelUtil = new ExcelUtil();
		    List<List<Object>> list =excelUtil.getBankListByExcel(file.getInputStream(), excelName);
		    
		    List<Orders> excelOrdersList = new ArrayList<Orders>();
		    List<OrdersItem> excelOrdersItemList = new ArrayList<OrdersItem>();
		    
		    List<OrdersLog> excelOrdersLogList = new ArrayList<OrdersLog>();
		    List<OrdersItemLog> excelOrdersItemLogList = new ArrayList<OrdersItemLog>();
		    
		    for(List<Object> innerlist:list){
		    	System.out.println("=====================");
		    	Orders orders = new Orders();
		    	OrdersItem ordersItem = new OrdersItem();
		    	int columnSize = innerlist.size();//一行记录多少列
		    	if(columnSize!=40)
		    		throw new Exception("列数不一样");
		    	for(int i =0 ;i<columnSize;i++){
		    		String info = (String) innerlist.get(i);
		    		InfotoObject(orders, ordersItem, i,info);
	    		}
		    	excelOrdersList.add(orders);
		    	excelOrdersItemList.add(ordersItem);
	    	}
		    
		    List<Orders> databaseOrdersList = iOrdersService.selectAll(requestCtx);
		    List<OrdersItem> databaseOrdersItemList = iOrdersItemService.selectAll(requestCtx);
		    
		    for(Orders order :excelOrdersList){
		    	boolean isHave = false;
		    	Orders item = null;
		    	for(Orders order2 :databaseOrdersList){
		    		if(order2.getOrderNo().equals(order.getOrderNo())){
		    			item=order2;
		    			isHave=true;
		    			break;
		    		}
		    	}
		    	
		    	if(isHave){ //如果数据库里存在相同orderno
		    		boolean isSame =  order.isSame(item);
		    		if(!isSame){//如果数据时相同的就不做任何更新
		    			order.set__status(DTOStatus.UPDATE);
		    			order.setUpdateTime(updateUpdate);
		    		}
		    	}else{
		    		order.set__status(DTOStatus.ADD);
		    		order.setUpdateTime(updateUpdate);
		    	}
		    	
		    	if(order.get__status()!=null){
		    		OrdersLog ordersLog = new OrdersLog();
		    		ordersLog.cloneOrders(order);
	    			ordersLog.set__status(DTOStatus.ADD);
	    			ordersLog.setUpdateTime(updateUpdate);
	    			if(ordersLog.get__status().equals(DTOStatus.ADD)){
	    				ordersLog.setUpdateBz("上传excel新增");
	    			}else{
	    				ordersLog.setUpdateBz("上传excel更新");
	    			}
	    			excelOrdersLogList.add(ordersLog);
		    	}
		    }
		    
		    for(OrdersItem orderItem :excelOrdersItemList){
		    	boolean isHave = false;
		    	OrdersItem item = null;
		    	for(OrdersItem order2 :databaseOrdersItemList){
		    		if(order2.getOrderNo().equals(orderItem.getOrderNo())){
		    			item=order2;
		    			isHave=true;
		    			break;
		    		}
		    	}
		    	if(isHave){ //如果数据库里存在相同orderno
		    		boolean isSame =  orderItem.isSame(item);
		    		if(!isSame){//如果数据时相同的就不做任何更新
		    			orderItem.set__status(DTOStatus.UPDATE);
		    			orderItem.setUpdateTime(updateUpdate);
		    		}
		    	}else{
		    		orderItem.set__status(DTOStatus.ADD);
		    		orderItem.setUpdateTime(updateUpdate);
		    	}
		    	
		    	if(orderItem.get__status()!=null){
		    		OrdersItemLog ordersItemLog = new OrdersItemLog();
		    		ordersItemLog.cloneOrdersItem(orderItem);
		    		ordersItemLog.set__status(DTOStatus.ADD);
		    		ordersItemLog.setUpdateTime(updateUpdate);
		    		if(orderItem.get__status().equals(DTOStatus.ADD)){
		    			ordersItemLog.setUpdateBz("上传excel新增");
		    		}else{
		    			ordersItemLog.setUpdateBz("上传excel更新");
		    		}
	    			excelOrdersItemLogList.add(ordersItemLog);
		    	}
		    }
		    iOrdersService.batchUpdate(requestCtx, excelOrdersList);
		    iOrdersItemService.batchUpdate(requestCtx, excelOrdersItemList);
		    iOrdersLogService.batchUpdate(requestCtx, excelOrdersLogList);
		    iOrdersItemLogService.batchUpdate(requestCtx, excelOrdersItemLogList);
    }
	
	
	private void InfotoObject(Orders orders,OrdersItem ordersItem,int i,String info){
		switch(i){
			case 0:
				orders.setOrderNo(info);
				ordersItem.setOrderNo(info);
				break;
			case 1:
				orders.setBBranchName(info);
				break;
			case 2:
				orders.setCMerchantName(info);
				break;
			case 3:
				orders.setDAuthorizedOperator(info);
				break;
			case 4:
				orders.setEMarketingPersonnelCode(info);
				break;
			case 5:
				orders.setFRecommendedPersonnelCode(info);
				break;
			case 6:
				orders.setGCustomerName(info);
				break;
			case 7:
				orders.setHCardLastFourNumber(info);
				break;
			case 8:
				orders.setICertificatesLastFiveNumber(info);
				break;
			case 9:
				orders.setJContactNumber(info);
				break;
			case 10:
				orders.setKTelphone(info);
				break;
			case 11:
				orders.setLDeliveryAddress(info);
				break;
			case 12:
				orders.setMZipCode(info);
				break;
			case 13:
				orders.setNInvoiceHeader(info);
				break;
			case 14:
				orders.setOCommodityNumber(info);
				break;
			case 15:
				orders.setPCommodityPrice(info);
				break;
			case 16:
				orders.setQApplicationNumber(info);
				break;
			case 17:
				orders.setRAuthorizationCode(info);
				break;
			case 18:
				orders.setSProductNumber(info);
				break;
			case 19:
				orders.setTCustomerOrderDate(info);
				break;
			case 20:
				orders.setUActualDeliveryDate(info);
				break;
			case 21:
				orders.setVCourierNumber(info);
				break;
			case 22:
				orders.setWCourierServicesCompany(info);
				break;
			case 23:
				orders.setXOverdueMark(info);
				break;
			case 24:
				orders.setYDeliveryFileCategory(info);
				break;
			case 25:
				orders.setZCardProduct(info);
				break;
			case 26:
				orders.setAaNameOfAgent(info);
				break;
			case 27:
				orders.setAbTelphoneOfAgent(info);
				break;
			case 28:
				orders.setAcOrderStatus(info);
				break;
			case 29:
				orders.setAdBz(info);
				break;
			case 30:
				ordersItem.setAeModel(info);
				break;
			case 31:
				ordersItem.setAfOrderAttribute(info);
				break;
			case 32:
				ordersItem.setAgLogisticsName(info);
				break;
			case 33:
				ordersItem.setAhLogisticsNo(info);
				break;
			case 34:
				ordersItem.setAiDeliveryTime(info);
				break;
			case 35:
				ordersItem.setAjBz(info);
				break;
			case 36:
				ordersItem.setAkOrderBatch(info);
				break;
			case 37:
				orders.setAlBankFeedbackTime(info);
				break;
			case 38:
				orders.setAmBankFeedbackType(info);
				break;
			case 39:
				orders.setAnBankFeedbackInstruction(info);
				break;
		}
	}
}
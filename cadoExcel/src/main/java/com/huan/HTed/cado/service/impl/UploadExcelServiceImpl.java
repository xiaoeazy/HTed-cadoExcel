package com.huan.HTed.cado.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.Listener.ProgressStatus;
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
	private static final int CAPTCHA_EXPIRE = 60 * 5;
	
	public static final String UPLOAD_PROGRESS = "upload_progress";
	/**
     * 过期时间,默认5分钟.
     */
    private Integer expire = CAPTCHA_EXPIRE;
    
	@Autowired
	private IOrdersService iOrdersService;
	@Autowired
	private IOrdersLogService iOrdersLogService;
	@Autowired
	private IOrdersItemService iOrdersItemService;
	@Autowired
	private IOrdersItemLogService iOrdersItemLogService;
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	   /**
     * @return 过期时间,单位秒
     */
    public Integer getExpire() {
        return expire;
    }

    /**
     *
     * @param expire
     *            过期时间
     */
    public void setExpire(Integer expire) {
        this.expire = expire;
    }
	
	private void setProgressInfo(IRequest requestCtx,ProgressStatus progressStatus, String status,String progress){
		progressStatus.setStatus(status);
		progressStatus.setProgress(progress);
//		redisTemplate.opsForValue().set(session.getId() + ":" +UPLOAD_PROGRESS , progressStatus, getExpire(), TimeUnit.SECONDS);
		redisTemplate.opsForHash().put(UPLOAD_PROGRESS,requestCtx.getUserId()+"",progressStatus.getSuccess()+":"+progressStatus.getProgress()+":"+progressStatus.getStatus()); //将对象存入redis
	}
	
	public void startFileUpload(IRequest requestCtx ,ProgressStatus progressStatus, String status,String progress){
		setProgressInfo(requestCtx,progressStatus, status, progress);
	}
	
	public void fildUpload(IRequest requestCtx  ,ProgressStatus progressStatus,InputStream fileInputStream,String excelName) throws Exception{
			setProgressInfo(requestCtx,progressStatus, ProgressStatus.START_UPLOAD, "10"); //设置状态
		 	ExcelUtil excelUtil = new ExcelUtil();
		    List<List<Object>> list =excelUtil.getBankListByExcel(fileInputStream, excelName);
		
		 	Date updateUpdate = new Date();
		    System.out.println("===============读取数据完成===================");
		    setProgressInfo(requestCtx,progressStatus, ProgressStatus.EXCEL_LOAD_END, "20"); //设置状态
		    
		    List<Orders> excelOrdersList = new ArrayList<Orders>();
		    List<OrdersItem> excelOrdersItemList = new ArrayList<OrdersItem>();
		    
		    List<OrdersLog> excelOrdersLogList = new ArrayList<OrdersLog>();
		    List<OrdersItemLog> excelOrdersItemLogList = new ArrayList<OrdersItemLog>();
		    
		    for(List<Object> innerlist:list){
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
		    setProgressInfo(requestCtx,progressStatus, ProgressStatus.DATA_LOAD_END, "30"); //设置状态
		    List<Orders> databaseOrdersList = iOrdersService.selectAll(requestCtx);
		    List<OrdersItem> databaseOrdersItemList = iOrdersItemService.selectAll(requestCtx);
		    
		    setProgressInfo(requestCtx,progressStatus, ProgressStatus.DATA_LOAD_END, "50"); //设置状态
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
		    		}else{
		    			order.set__status(DTOStatus.NOTHING);
		    		}
		    	}else{
		    		order.set__status(DTOStatus.ADD);
		    		order.setUpdateTime(updateUpdate);
		    	}
		    	
		    	if(!DTOStatus.NOTHING.equals(order.get__status())){
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
		    		}else{
		    			orderItem.set__status(DTOStatus.NOTHING);
		    		}
		    	}else{
		    		orderItem.set__status(DTOStatus.ADD);
		    		orderItem.setUpdateTime(updateUpdate);
		    	}
		    	
		    	if(!DTOStatus.NOTHING.equals(orderItem.get__status())){
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
		    
		    System.out.println("===============开始插入==================");
		    setProgressInfo(requestCtx,progressStatus, ProgressStatus.UPDATE_DATA, "80"); //设置状态
		    
		    iOrdersService.batchUpdate(requestCtx, excelOrdersList);
		    iOrdersItemService.batchUpdate(requestCtx, excelOrdersItemList);
		    iOrdersLogService.batchUpdate(requestCtx, excelOrdersLogList);
		    iOrdersItemLogService.batchUpdate(requestCtx, excelOrdersItemLogList);
		    
		    System.out.println("===============插入结束==================");
		    setProgressInfo(requestCtx,progressStatus, ProgressStatus.END_UPLOAD, "100"); //设置状态
    }
	
	public Workbook makeExcel(IRequest requestCtx )throws Exception{
		
	    String columnNames[]={"订单编号","分行名称","商户名称","授权操作员","营销人员代码","推荐人员代码","客户姓名",
				   "卡号末四位","证件号后五位","联系电话","手机","送货地址","邮编","发票抬头",
				   "商品编号","商品价格(元)","申请编号","授权码","产品编号","客户订单日期","实际送货日期",
				   "快递单号","快递公司","快递公司","送货文件类别","账户已激活卡片被保护",
				   "代领人姓名","代领人姓名","订单状态","备注","型号","订单属性",
				   "物流名称","物流单号","发货时间","备注说明","订单批次","银行反馈时间","银行反馈分类","银行反馈说明"};
	  
		String keys[]  = {"orderNo","bBranchName","cMerchantName","dAuthorizedOperator","eMarketingPersonnelCode","fRecommendedPersonnelCode","gCustomerName",
				"hCardLastFourNumber","iCertificatesLastFiveNumber","jContactNumber","kTelphone","lDeliveryAddress","mZipCode","nInvoiceHeader",
				"oCommodityNumber","pCommodityPrice","qApplicationNumber","rAuthorizationCode","sProductNumber","tCustomerOrderDate","uActualDeliveryDate",
				"vCourierNumber","wCourierServicesCompany","xOverdueMark","yDeliveryFileCategory","zCardProduct","aaNameOfAgent","abTelphoneOfAgent",
				"acOrderStatus","adBz","aeModel","afOrderAttribute","agLogisticsName","ahLogisticsNo","aiDeliveryTime",
				"ajBz","akOrderBatch","alBankFeedbackTime","amBankFeedbackType","anBankFeedbackInstruction"};//map中的key
		
		List<List<Map<String,Object>>> list = new ArrayList<List<Map<String,Object>>>();
		List<Orders> orderlist =   iOrdersService.queryAll(requestCtx);
		List<Map<String, Object>> listmap1 = createExcelRecord(orderlist);
		list.add(listmap1);
		
		List<Map<String, Object>> listmap2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("sheetName", "sheet2");
		listmap2.add(map2);
		list.add(listmap2);
		
		List<Map<String, Object>> listmap3 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("sheetName", "sheet3");
		listmap3.add(map3);
		list.add(listmap3);
		
		return ExcelUtil.createWorkBook(list, keys, columnNames);
	}
	
	
	private List<Map<String, Object>> createExcelRecord(List<Orders> OrdersList) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        for (int j = 0; j < OrdersList.size(); j++) {
            Orders orders=OrdersList.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("orderNo", orders.getOrderNo());
            mapValue.put("bBranchName",orders.getbBranchName());
            mapValue.put("cMerchantName",orders.getcMerchantName());
            mapValue.put("dAuthorizedOperator",orders.getdAuthorizedOperator());
            mapValue.put("eMarketingPersonnelCode",orders.geteMarketingPersonnelCode());
            mapValue.put("fRecommendedPersonnelCode",orders.getfRecommendedPersonnelCode());
            mapValue.put("gCustomerName",orders.getgCustomerName());
            mapValue.put("hCardLastFourNumber",orders.gethCardLastFourNumber());
            mapValue.put("iCertificatesLastFiveNumber",orders.getiCertificatesLastFiveNumber());
            mapValue.put("jContactNumber",orders.getjContactNumber());
            mapValue.put("kTelphone",orders.getkTelphone());
            mapValue.put("lDeliveryAddress",orders.getlDeliveryAddress());
            mapValue.put("mZipCode",orders.getmZipCode());
            mapValue.put("nInvoiceHeader",orders.getnInvoiceHeader());
            mapValue.put("oCommodityNumber",orders.getoCommodityNumber());
            mapValue.put("pCommodityPrice",orders.getpCommodityPrice());
            mapValue.put("qApplicationNumber",orders.getqApplicationNumber());
            mapValue.put("rAuthorizationCode",orders.getrAuthorizationCode());
            mapValue.put("sProductNumber",orders.getsProductNumber());
            mapValue.put("tCustomerOrderDate",orders.gettCustomerOrderDate());
            mapValue.put("uActualDeliveryDate",orders.getuActualDeliveryDate());
            mapValue.put("vCourierNumber",orders.getvCourierNumber());
            mapValue.put("wCourierServicesCompany",orders.getwCourierServicesCompany());
            mapValue.put("xOverdueMark",orders.getxOverdueMark());
            mapValue.put("yDeliveryFileCategory",orders.getyDeliveryFileCategory());
            mapValue.put("zCardProduct",orders.getzCardProduct());
            
            mapValue.put("aaNameOfAgent",orders.getAaNameOfAgent());
            mapValue.put("abTelphoneOfAgent",orders.getAbTelphoneOfAgent());
            mapValue.put("acOrderStatus",orders.getAcOrderStatus());
            mapValue.put("adBz",orders.getAdBz());
            mapValue.put("aeModel",orders.getOrdersItem().getAeModel());
            mapValue.put("afOrderAttribute",orders.getOrdersItem().getAfOrderAttribute());
            mapValue.put("agLogisticsName",orders.getOrdersItem().getAgLogisticsName());
            mapValue.put("ahLogisticsNo",orders.getOrdersItem().getAhLogisticsNo());
            mapValue.put("aiDeliveryTime",orders.getOrdersItem().getAiDeliveryTime());
            mapValue.put("ajBz",orders.getOrdersItem().getAjBz());
            mapValue.put("akOrderBatch",orders.getOrdersItem().getAkOrderBatch());
            mapValue.put("alBankFeedbackTime",orders.getAlBankFeedbackTime());
            mapValue.put("amBankFeedbackType",orders.getAmBankFeedbackType());
            mapValue.put("anBankFeedbackInstruction",orders.getAnBankFeedbackInstruction());
            listmap.add(mapValue);
        }
        return listmap;
    }
	
	private void InfotoObject(Orders orders,OrdersItem ordersItem,int i,String info){
		switch(i){
			case 0:
				orders.setOrderNo(info);
				ordersItem.setOrderNo(info);
				break;
			case 1:
				orders.setbBranchName(info);
				break;
			case 2:
				orders.setcMerchantName(info);
				break;
			case 3:
				orders.setdAuthorizedOperator(info);
				break;
			case 4:
				orders.seteMarketingPersonnelCode(info);
				break;
			case 5:
				orders.setfRecommendedPersonnelCode(info);
				break;
			case 6:
				orders.setgCustomerName(info);
				break;
			case 7:
				orders.sethCardLastFourNumber(info);
				break;
			case 8:
				orders.setiCertificatesLastFiveNumber(info);
				break;
			case 9:
				orders.setjContactNumber(info);
				break;
			case 10:
				orders.setkTelphone(info);
				break;
			case 11:
				orders.setlDeliveryAddress(info);
				break;
			case 12:
				orders.setmZipCode(info);
				break;
			case 13:
				orders.setnInvoiceHeader(info);
				break;
			case 14:
				orders.setoCommodityNumber(info);
				break;
			case 15:
				orders.setpCommodityPrice(info);
				break;
			case 16:
				orders.setqApplicationNumber(info);
				break;
			case 17:
				orders.setrAuthorizationCode(info);
				break;
			case 18:
				orders.setsProductNumber(info);
				break;
			case 19:
				orders.settCustomerOrderDate(info);
				break;
			case 20:
				orders.setuActualDeliveryDate(info);
				break;
			case 21:
				orders.setvCourierNumber(info);
				break;
			case 22:
				orders.setwCourierServicesCompany(info);
				break;
			case 23:
				orders.setxOverdueMark(info);
				break;
			case 24:
				orders.setyDeliveryFileCategory(info);
				break;
			case 25:
				orders.setzCardProduct(info);
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

package com.huan.HTed.cado.dto;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huan.HTed.core.BaseConstants;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import com.huan.HTed.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "cadoexcel_orders_item")
public class OrdersItem extends BaseDTO {

     public static final String FIELD_ORDER_NO = "orderNo";
     public static final String FIELD_AE_MODEL = "aeModel";
     public static final String FIELD_AF_ORDER_ATTRIBUTE = "afOrderAttribute";
     public static final String FIELD_AG_LOGISTICS_NAME = "agLogisticsName";
     public static final String FIELD_AH_LOGISTICS_NO = "ahLogisticsNo";
     public static final String FIELD_AI_DELIVERY_TIME = "aiDeliveryTime";
     public static final String FIELD_AJ_BZ = "ajBz";
     public static final String FIELD_AK_ORDER_BATCH = "akOrderBatch";
     public static final String FIELD_UPDATE_TIME = "updateTime";


     @Id
     private String orderNo;

     @Length(max = 100)
     private String aeModel;

     @Length(max = 40)
     private String afOrderAttribute;

     @Length(max = 100)
     private String agLogisticsName;

     @Length(max = 40)
     private String ahLogisticsNo;

     @Length(max = 8)
     private String aiDeliveryTime;

     @Length(max = 200)
     private String ajBz;

     @Length(max = 8)
     private String akOrderBatch;
     
     @JsonFormat(pattern=BaseConstants.DATE_TIME_FORMAT)
     private Date updateTime;
     
     @Transient
     private Orders orders;


     public void setOrderNo(String orderNo){
         this.orderNo = orderNo;
     }

     public String getOrderNo(){
         return orderNo;
     }

     public void setAeModel(String aeModel){
         this.aeModel = aeModel;
     }

     public String getAeModel(){
         return aeModel;
     }

     public void setAfOrderAttribute(String afOrderAttribute){
         this.afOrderAttribute = afOrderAttribute;
     }

     public String getAfOrderAttribute(){
         return afOrderAttribute;
     }

     public void setAgLogisticsName(String agLogisticsName){
         this.agLogisticsName = agLogisticsName;
     }

     public String getAgLogisticsName(){
         return agLogisticsName;
     }

     public void setAhLogisticsNo(String ahLogisticsNo){
         this.ahLogisticsNo = ahLogisticsNo;
     }

     public String getAhLogisticsNo(){
         return ahLogisticsNo;
     }

     public void setAiDeliveryTime(String aiDeliveryTime){
         this.aiDeliveryTime = aiDeliveryTime;
     }

     public String getAiDeliveryTime(){
         return aiDeliveryTime;
     }

     public void setAjBz(String ajBz){
         this.ajBz = ajBz;
     }

     public String getAjBz(){
         return ajBz;
     }

     public void setAkOrderBatch(String akOrderBatch){
         this.akOrderBatch = akOrderBatch;
     }

     public String getAkOrderBatch(){
         return akOrderBatch;
     }

     public void setUpdateTime(Date updateTime){
         this.updateTime = updateTime;
     }

     public Date getUpdateTime(){
         return updateTime;
     }
     
     public boolean isSame(OrdersItem ordersItem){
    	 boolean flag =true;
    	 if(!getOrderNo().equals(ordersItem.getOrderNo()))
    		 return false;
    	 if(!getAeModel().equals(ordersItem.getAeModel()))
    		 return false;
    	 if(!getAfOrderAttribute().equals(ordersItem.getAfOrderAttribute()))
    		 return false;
    	 if(!getAgLogisticsName().equals(ordersItem.getAgLogisticsName()))
    		 return false;
    	 if(!getAhLogisticsNo().equals(ordersItem.getAhLogisticsNo()))
    		 return false;
    	 if(!getAiDeliveryTime().equals(ordersItem.getAiDeliveryTime()))
    		 return false;
    	 if(!getAjBz().equals(ordersItem.getAjBz()))
    		 return false;
    	 if(!getAkOrderBatch().equals(ordersItem.getAkOrderBatch()))
    		 return false;
    	 
    	 
    	 return flag;
     }

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
     
     

     }

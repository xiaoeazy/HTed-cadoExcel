package com.huan.HTed.cado.dto;

/**Auto Generated By HTed Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huan.HTed.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "cado_catalog_type_detail")
public class CatalogTypeDetail extends BaseDTO {

     public static final String FIELD_CATALOG_ID = "catalogId";
     public static final String FIELD_TYPE_ID = "typeId";
     public static final String FIELD_ORDERS = "orders";

     @Id
     private Long catalogId;

     @Id
     private Long typeId;
     
     @Length(max = 20)
     private Long orders;
     
     @Transient
     private Type type;
     
     


     public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setCatalogId(Long catalogId){
         this.catalogId = catalogId;
     }

     public Long getCatalogId(){
         return catalogId;
     }

     public void setTypeId(Long typeId){
         this.typeId = typeId;
     }

     public Long getTypeId(){
         return typeId;
     }

     public void setOrders(Long orders){
         this.orders = orders;
     }

     public Long getOrders(){
         return orders;
     }

     }

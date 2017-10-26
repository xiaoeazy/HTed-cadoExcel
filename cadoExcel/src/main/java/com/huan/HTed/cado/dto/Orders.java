package com.huan.HTed.cado.dto;

/**Auto Generated By HTed Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huan.HTed.system.dto.BaseDTO;
import java.util.Date;
@ExtensionAttribute(disable=true)
@Table(name = "cadoexcel_orders")
public class Orders extends BaseDTO {

     public static final String FIELD_ORDER_NO = "orderNo";
     public static final String FIELD_B_BRANCH_NAME = "bBranchName";
     public static final String FIELD_C_MERCHANT_NAME = "cMerchantName";
     public static final String FIELD_D_AUTHORIZED_OPERATOR = "dAuthorizedOperator";
     public static final String FIELD_E_MARKETING_PERSONNEL_CODE = "eMarketingPersonnelCode";
     public static final String FIELD_F_RECOMMENDED_PERSONNEL_CODE = "fRecommendedPersonnelCode";
     public static final String FIELD_G_CUSTOMER_NAME = "gCustomerName";
     public static final String FIELD_H_CARD_LAST_FOUR_NUMBER = "hCardLastFourNumber";
     public static final String FIELD_I_CERTIFICATES_LAST_FIVE_NUMBER = "iCertificatesLastFiveNumber";
     public static final String FIELD_J_CONTACT_NUMBER = "jContactNumber";
     public static final String FIELD_K_TELPHONE = "kTelphone";
     public static final String FIELD_L_DELIVERY_ADDRESS = "lDeliveryAddress";
     public static final String FIELD_M_ZIP_CODE = "mZipCode";
     public static final String FIELD_N_INVOICE_HEADER = "nInvoiceHeader";
     public static final String FIELD_O_COMMODITY_NUMBER = "oCommodityNumber";
     public static final String FIELD_P_COMMODITY_PRICE = "pCommodityPrice";
     public static final String FIELD_Q_APPLICATION_NUMBER = "qApplicationNumber";
     public static final String FIELD_R_AUTHORIZATION_CODE = "rAuthorizationCode";
     public static final String FIELD_S_PRODUCT_NUMBER = "sProductNumber";
     public static final String FIELD_T_CUSTOMER_ORDER_DATE = "tCustomerOrderDate";
     public static final String FIELD_U_ACTUAL_DELIVERY_DATE = "uActualDeliveryDate";
     public static final String FIELD_V_COURIER_NUMBER = "vCourierNumber";
     public static final String FIELD_W_COURIER_SERVICES_COMPANY = "wCourierServicesCompany";
     public static final String FIELD_X_OVERDUE_MARK = "xOverdueMark";
     public static final String FIELD_Y_DELIVERY_FILE_CATEGORY = "yDeliveryFileCategory";
     public static final String FIELD_Z_CARD_PRODUCT = "zCardProduct";
     public static final String FIELD_AA_NAME_OF_AGENT = "aaNameOfAgent";
     public static final String FIELD_AB_TELPHONE_OF_AGENT = "abTelphoneOfAgent";
     public static final String FIELD_AC_ORDER_STATUS = "acOrderStatus";
     public static final String FIELD_AD_BZ = "adBz";
     public static final String FIELD_AL_BANK_FEEDBACK_TIME = "alBankFeedbackTime";
     public static final String FIELD_AM_BANK_FEEDBACK_TYPE = "amBankFeedbackType";
     public static final String FIELD_AN_BANK_FEEDBACK_INSTRUCTION = "anBankFeedbackInstruction";
     public static final String FIELD_UPDATE_TIME = "updateTime";


     @Id
     private String orderNo;

     @Length(max = 45)
     private String bBranchName;

     @Length(max = 200)
     private String cMerchantName;

     @Length(max = 45)
     private String dAuthorizedOperator;

     @Length(max = 45)
     private String eMarketingPersonnelCode;

     @Length(max = 45)
     private String fRecommendedPersonnelCode;

     @Length(max = 45)
     private String gCustomerName;

     @Length(max = 4)
     private String hCardLastFourNumber;

     @Length(max = 5)
     private String iCertificatesLastFiveNumber;

     @Length(max = 20)
     private String jContactNumber;

     @Length(max = 20)
     private String kTelphone;

     @Length(max = 200)
     private String lDeliveryAddress;

     @Length(max = 6)
     private String mZipCode;

     @Length(max = 200)
     private String nInvoiceHeader;

     @Length(max = 45)
     private String oCommodityNumber;

     @Length(max = 45)
     private String pCommodityPrice;

     @Length(max = 45)
     private String qApplicationNumber;

     @Length(max = 45)
     private String rAuthorizationCode;

     @Length(max = 45)
     private String sProductNumber;

     @Length(max = 8)
     private String tCustomerOrderDate;

     @Length(max = 8)
     private String uActualDeliveryDate;

     @Length(max = 45)
     private String vCourierNumber;

     @Length(max = 45)
     private String wCourierServicesCompany;

     @Length(max = 45)
     private String xOverdueMark;

     @Length(max = 45)
     private String yDeliveryFileCategory;

     @Length(max = 45)
     private String zCardProduct;

     @Length(max = 45)
     private String aaNameOfAgent;

     @Length(max = 45)
     private String abTelphoneOfAgent;

     @Length(max = 45)
     private String acOrderStatus;

     @Length(max = 45)
     private String adBz;

     @Length(max = 10)
     private String alBankFeedbackTime;

     @Length(max = 45)
     private String amBankFeedbackType;

     @Length(max = 2000)
     private String anBankFeedbackInstruction;

     private Date updateTime;
     
     @Transient
     private OrdersItem ordersItem;
     
     public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getbBranchName() {
		return bBranchName;
	}

	public void setbBranchName(String bBranchName) {
		this.bBranchName = bBranchName;
	}

	public String getcMerchantName() {
		return cMerchantName;
	}

	public void setcMerchantName(String cMerchantName) {
		this.cMerchantName = cMerchantName;
	}

	public String getdAuthorizedOperator() {
		return dAuthorizedOperator;
	}

	public void setdAuthorizedOperator(String dAuthorizedOperator) {
		this.dAuthorizedOperator = dAuthorizedOperator;
	}

	public String geteMarketingPersonnelCode() {
		return eMarketingPersonnelCode;
	}

	public void seteMarketingPersonnelCode(String eMarketingPersonnelCode) {
		this.eMarketingPersonnelCode = eMarketingPersonnelCode;
	}

	public String getfRecommendedPersonnelCode() {
		return fRecommendedPersonnelCode;
	}

	public void setfRecommendedPersonnelCode(String fRecommendedPersonnelCode) {
		this.fRecommendedPersonnelCode = fRecommendedPersonnelCode;
	}

	public String getgCustomerName() {
		return gCustomerName;
	}

	public void setgCustomerName(String gCustomerName) {
		this.gCustomerName = gCustomerName;
	}

	public String gethCardLastFourNumber() {
		return hCardLastFourNumber;
	}

	public void sethCardLastFourNumber(String hCardLastFourNumber) {
		this.hCardLastFourNumber = hCardLastFourNumber;
	}

	public String getiCertificatesLastFiveNumber() {
		return iCertificatesLastFiveNumber;
	}

	public void setiCertificatesLastFiveNumber(String iCertificatesLastFiveNumber) {
		this.iCertificatesLastFiveNumber = iCertificatesLastFiveNumber;
	}

	public String getjContactNumber() {
		return jContactNumber;
	}

	public void setjContactNumber(String jContactNumber) {
		this.jContactNumber = jContactNumber;
	}

	public String getkTelphone() {
		return kTelphone;
	}

	public void setkTelphone(String kTelphone) {
		this.kTelphone = kTelphone;
	}

	public String getlDeliveryAddress() {
		return lDeliveryAddress;
	}

	public void setlDeliveryAddress(String lDeliveryAddress) {
		this.lDeliveryAddress = lDeliveryAddress;
	}

	public String getmZipCode() {
		return mZipCode;
	}

	public void setmZipCode(String mZipCode) {
		this.mZipCode = mZipCode;
	}

	public String getnInvoiceHeader() {
		return nInvoiceHeader;
	}

	public void setnInvoiceHeader(String nInvoiceHeader) {
		this.nInvoiceHeader = nInvoiceHeader;
	}

	public String getoCommodityNumber() {
		return oCommodityNumber;
	}

	public void setoCommodityNumber(String oCommodityNumber) {
		this.oCommodityNumber = oCommodityNumber;
	}

	public String getpCommodityPrice() {
		return pCommodityPrice;
	}

	public void setpCommodityPrice(String pCommodityPrice) {
		this.pCommodityPrice = pCommodityPrice;
	}

	public String getqApplicationNumber() {
		return qApplicationNumber;
	}

	public void setqApplicationNumber(String qApplicationNumber) {
		this.qApplicationNumber = qApplicationNumber;
	}

	public String getrAuthorizationCode() {
		return rAuthorizationCode;
	}

	public void setrAuthorizationCode(String rAuthorizationCode) {
		this.rAuthorizationCode = rAuthorizationCode;
	}

	public String getsProductNumber() {
		return sProductNumber;
	}

	public void setsProductNumber(String sProductNumber) {
		this.sProductNumber = sProductNumber;
	}

	public String gettCustomerOrderDate() {
		return tCustomerOrderDate;
	}

	public void settCustomerOrderDate(String tCustomerOrderDate) {
		this.tCustomerOrderDate = tCustomerOrderDate;
	}

	public String getuActualDeliveryDate() {
		return uActualDeliveryDate;
	}

	public void setuActualDeliveryDate(String uActualDeliveryDate) {
		this.uActualDeliveryDate = uActualDeliveryDate;
	}

	public String getvCourierNumber() {
		return vCourierNumber;
	}

	public void setvCourierNumber(String vCourierNumber) {
		this.vCourierNumber = vCourierNumber;
	}

	public String getwCourierServicesCompany() {
		return wCourierServicesCompany;
	}

	public void setwCourierServicesCompany(String wCourierServicesCompany) {
		this.wCourierServicesCompany = wCourierServicesCompany;
	}

	public String getxOverdueMark() {
		return xOverdueMark;
	}

	public void setxOverdueMark(String xOverdueMark) {
		this.xOverdueMark = xOverdueMark;
	}

	public String getyDeliveryFileCategory() {
		return yDeliveryFileCategory;
	}

	public void setyDeliveryFileCategory(String yDeliveryFileCategory) {
		this.yDeliveryFileCategory = yDeliveryFileCategory;
	}

	public String getzCardProduct() {
		return zCardProduct;
	}

	public void setzCardProduct(String zCardProduct) {
		this.zCardProduct = zCardProduct;
	}

	public String getAaNameOfAgent() {
		return aaNameOfAgent;
	}

	public void setAaNameOfAgent(String aaNameOfAgent) {
		this.aaNameOfAgent = aaNameOfAgent;
	}

	public String getAbTelphoneOfAgent() {
		return abTelphoneOfAgent;
	}

	public void setAbTelphoneOfAgent(String abTelphoneOfAgent) {
		this.abTelphoneOfAgent = abTelphoneOfAgent;
	}

	public String getAcOrderStatus() {
		return acOrderStatus;
	}

	public void setAcOrderStatus(String acOrderStatus) {
		this.acOrderStatus = acOrderStatus;
	}

	public String getAdBz() {
		return adBz;
	}

	public void setAdBz(String adBz) {
		this.adBz = adBz;
	}

	public String getAlBankFeedbackTime() {
		return alBankFeedbackTime;
	}

	public void setAlBankFeedbackTime(String alBankFeedbackTime) {
		this.alBankFeedbackTime = alBankFeedbackTime;
	}

	public String getAmBankFeedbackType() {
		return amBankFeedbackType;
	}

	public void setAmBankFeedbackType(String amBankFeedbackType) {
		this.amBankFeedbackType = amBankFeedbackType;
	}

	public String getAnBankFeedbackInstruction() {
		return anBankFeedbackInstruction;
	}

	public void setAnBankFeedbackInstruction(String anBankFeedbackInstruction) {
		this.anBankFeedbackInstruction = anBankFeedbackInstruction;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

	public OrdersItem getOrdersItem() {
		return ordersItem;
	}

	public void setOrdersItem(OrdersItem ordersItem) {
		this.ordersItem = ordersItem;
	}

	public boolean isSame(Orders order){
    	 boolean flag =true;
    	 if(!getOrderNo().equals(order.getOrderNo()))
    		 return false;
    	 if(!getbBranchName().equals(order.getbBranchName()))
    		 return false;
    	 if(!getcMerchantName().equals(order.getcMerchantName()))
    		 return false;
    	 if(!getdAuthorizedOperator().equals(order.getdAuthorizedOperator()))
    		 return false;
    	 if(!geteMarketingPersonnelCode().equals(order.geteMarketingPersonnelCode()))
    		 return false;
    	 if(!getfRecommendedPersonnelCode().equals(order.getfRecommendedPersonnelCode()))
    		 return false;
    	 if(!getgCustomerName().equals(order.getgCustomerName()))
    		 return false;
    	 if(!gethCardLastFourNumber().equals(order.gethCardLastFourNumber()))
    		 return false;
    	 if(!getiCertificatesLastFiveNumber().equals(order.getiCertificatesLastFiveNumber()))
    		 return false;
    	 if(!getjContactNumber().equals(order.getjContactNumber()))
    		 return false;
    	 if(!getkTelphone().equals(order.getkTelphone()))
    		 return false;
    	 if(!getlDeliveryAddress().equals(order.getlDeliveryAddress()))
    		 return false;
    	 if(!getmZipCode().equals(order.getmZipCode()))
    		 return false;
    	 if(!getnInvoiceHeader().equals(order.getnInvoiceHeader()))
    		 return false;
    	 if(!getoCommodityNumber().equals(order.getoCommodityNumber()))
    		 return false;
    	 if(!getpCommodityPrice().equals(order.getpCommodityPrice()))
    		 return false;
    	 if(!getqApplicationNumber().equals(order.getqApplicationNumber()))
    		 return false;
    	 if(!getrAuthorizationCode().equals(order.getrAuthorizationCode()))
    		 return false;
    	 if(!getsProductNumber().equals(order.getsProductNumber()))
    		 return false;
    	 if(!gettCustomerOrderDate().equals(order.gettCustomerOrderDate()))
    		 return false;
    	 if(!getuActualDeliveryDate().equals(order.getuActualDeliveryDate()))
    		 return false;
    	 if(!getvCourierNumber().equals(order.getvCourierNumber()))
    		 return false;
    	 if(!getwCourierServicesCompany().equals(order.getwCourierServicesCompany()))
    		 return false;
    	 if(!getxOverdueMark().equals(order.getxOverdueMark()))
    		 return false;
    	 if(!getyDeliveryFileCategory().equals(order.getyDeliveryFileCategory()))
    		 return false;
    	 if(!getzCardProduct().equals(order.getzCardProduct()))
    		 return false;
    	 
    	 if(!getAaNameOfAgent().equals(order.getAaNameOfAgent()))
    		 return false;
    	 if(!getAbTelphoneOfAgent().equals(order.getAbTelphoneOfAgent()))
    		 return false;
    	 if(!getAcOrderStatus().equals(order.getAcOrderStatus()))
    		 return false;
    	 if(!getAdBz().equals(order.getAdBz()))
    		 return false;
    	 if(!getAlBankFeedbackTime().equals(order.getAlBankFeedbackTime()))
    		 return false;
    	 if(!getAmBankFeedbackType().equals(order.getAmBankFeedbackType()))
    		 return false;
    	 if(!getAnBankFeedbackInstruction().equals(order.getAnBankFeedbackInstruction()))
    		 return false;
//    	 getCMerchantName
//    	 getDAuthorizedOperator
//    	 getEMarketingPersonnelCode
//    	 getFRecommendedPersonnelCode
//
//    	 getGCustomerName
//    	 getHCardLastFourNumber
//    	 getICertificatesLastFiveNumber
//    	 getJContactNumber
//    	 getKTelphone
//    	 getLDeliveryAddress
//    	 getMZipCode
//    	 getNInvoiceHeader
//    	 getOCommodityNumber
//    	 getPCommodityPrice
//    	 getQApplicationNumber
//    	 getRAuthorizationCode
//    	 getSProductNumber
//    	 getTCustomerOrderDate
//    	 getUActualDeliveryDate
//    	 getVCourierNumber
//    	 getWCourierServicesCompany
//    	 getXOverdueMark
//    	 getYDeliveryFileCategory
//    	 getZCardProduct
//
//    	 getAaNameOfAgent
//    	 getAbTelphoneOfAgent
//    	 getAcOrderStatus
//    	 getAdBz
//    	 getAlBankFeedbackTime
//    	 getAmBankFeedbackType
//    	 getAnBankFeedbackInstruction
    	 return flag;
     }

     }

package com.huan.HTed.cado.Listener;

import java.io.Serializable;

public class ProgressStatus implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 196016556854258242L;
	/**
	 * 
	 */
	
	public static final String START_UPLOAD = "开始导入"; //10
	public static final String EXCEL_LOAD_END = "获取excel数据完成";//20
	public static final String DATA_LOAD_END = "获取数据库保存数据";//30
	public static final String COMPARE_DATA = "比对数据";	//50
	public static final String UPDATE_DATA= "更新数据库数据"; //80
	public static final String END_UPLOAD = "导入结束";	//100
	
	private   Boolean success = true;
	private   String status="";
	private   String progress="0";
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}  
	

}

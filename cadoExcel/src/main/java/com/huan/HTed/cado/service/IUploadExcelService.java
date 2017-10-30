package com.huan.HTed.cado.service;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;

import com.huan.HTed.cado.Listener.ProgressStatus;
import com.huan.HTed.cado.dto.Orders;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface IUploadExcelService extends IBaseService<Orders>, ProxySelf<IUploadExcelService>{
		public void startFileUpload(IRequest requestCtx ,ProgressStatus progressStatus, String status,String progress);
	
		public void fildUpload(IRequest requestCtx ,ProgressStatus progressStatus,InputStream fileInputStream,String excelName) throws Exception;
	
		public Workbook makeExcel(IRequest requestCtx )throws Exception;
}
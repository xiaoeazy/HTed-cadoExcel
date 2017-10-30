package com.huan.HTed.cado.Listener;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import com.huan.HTed.cado.service.IUploadExcelService;
import com.huan.HTed.core.IRequest;

public class UploadThread implements Runnable{
	
	
	
	private IRequest requestCtx;
	private ProgressStatus progressStatus;
	private IUploadExcelService service;
	private InputStream fileInputStream;
	private String excelName;
	
	
	public UploadThread(IRequest requestCtx,ProgressStatus progressStatus,IUploadExcelService service, InputStream fileInputStream,String excelName) {
		super();
		this.requestCtx = requestCtx;
		this.progressStatus = progressStatus;
		this.service = service;
		this.fileInputStream = fileInputStream;
		this.excelName = excelName;
	}


	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			service.fildUpload(requestCtx,progressStatus,fileInputStream,excelName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			progressStatus.setSuccess(false);
			progressStatus.setProgress("0");
			progressStatus.setStatus(e.getMessage());
		}
	}

}

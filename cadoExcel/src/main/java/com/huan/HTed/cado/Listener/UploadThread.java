package com.huan.HTed.cado.Listener;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import com.huan.HTed.cado.service.IUploadExcelService;
import com.huan.HTed.core.IRequest;

public class UploadThread implements Runnable{
	
	
	
	private IRequest requestCtx;
	private HttpSession session;
	private IUploadExcelService service;
	private InputStream fileInputStream;
	private String excelName;
	private ProgressStatus progressStatus;
	
	
	public UploadThread(IRequest requestCtx,HttpSession session,IUploadExcelService service, InputStream fileInputStream,String excelName, ProgressStatus progressStatus) {
		super();
		this.requestCtx = requestCtx;
		this.session = session;
		this.service = service;
		this.fileInputStream = fileInputStream;
		this.excelName = excelName;
		this.progressStatus = progressStatus;
	}


	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			service.fildUpload(requestCtx, session,fileInputStream,excelName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			progressStatus.setSuccess(false);
			progressStatus.setProgress("0");
			progressStatus.setStatus(e.getMessage());
		}
	}

}

package com.huan.HTed.cado.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import com.huan.HTed.cado.dto.Orders;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface IUploadExcelService extends IBaseService<Orders>, ProxySelf<IUploadExcelService>{
		public void fildUpload(IRequest requestCtx ,MultipartFile file) throws Exception;
		
		public Workbook makeExcel(IRequest requestCtx )throws Exception;
}
package com.huan.HTed.cado.service;

import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

import org.springframework.web.multipart.MultipartFile;

import com.huan.HTed.cado.dto.Orders;

public interface IUploadExcelService extends IBaseService<Orders>, ProxySelf<IUploadExcelService>{
		public void fildUpload(IRequest requestCtx ,MultipartFile file) throws Exception;
}
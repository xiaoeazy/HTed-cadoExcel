package com.huan.HTed.cado.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huan.HTed.cado.service.IUploadExcelService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;

@Controller
public class UploadExcelController extends BaseController{
	/** 日志记录. **/
    private static Logger logger = LoggerFactory.getLogger(UploadExcelController.class);
    @Autowired
    private IUploadExcelService service;

    
    
    

    @RequestMapping(value="/upload/excel",method=RequestMethod.POST)  
    @ResponseBody
    public ResponseData fildUpload(HttpServletRequest request,@RequestParam(value="file",required=false) MultipartFile file){  
        //基本表单  
    	ResponseData rd = new ResponseData();
        try {
			String pathRoot = request.getSession().getServletContext().getRealPath("");  
			String path="";  
			if(!file.isEmpty()){  
				IRequest requestCtx = createRequestContext(request);
				service.fildUpload(requestCtx,file);
			}
			rd.setSuccess(true);
			rd.setMessage(path);
		} catch (Exception e) {
			logger.error("上传出错"+e.getMessage());
			rd.setSuccess(false);
			rd.setMessage(e.getMessage());
		}  
        return rd;
    }  
    
   
  
}
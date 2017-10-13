package com.huan.HTed.cado.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.cado.service.IUploadExcelService;
import com.huan.HTed.cado.util.ExcelUtil;
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
    
    
    @RequestMapping(value="/upload/download",produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public String download(HttpServletRequest request,HttpServletResponse response){
        try {
        	IRequest requestCtx = createRequestContext(request);
            String fileName="浦发自助平台Cado订单银行反馈信息表_auto";
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            service.makeExcel(requestCtx);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        }catch (Exception e){
        	e.printStackTrace();
        }

        return null;
    }
   
  
}
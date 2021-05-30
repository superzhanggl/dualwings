package com.dualwings.apifile.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dualwings.common.utils.SnowFlake;

@RestController
@RequestMapping("upload")
public class UploadController {
	
	@RequestMapping("file")
    public CommonResult logUpload(MultipartFile file) throws Exception {
    	CommonResult commonResult=new CommonResult();
    	try {
    		if (file == null || file.isEmpty()) {
            	commonResult.setCode(500);
            	commonResult.setMessage("未选择需上传的文件");
            	return commonResult;
            }
            if(file.getSize()>2048) {
            	commonResult.setCode(500);
            	commonResult.setMessage("上传文件不能大于2M");
            	return commonResult;
            }
            
            String filePath = new File("/root/upload/file/resources").getAbsolutePath();
            File fileUpload = new File(filePath);
            if (!fileUpload.exists()) {
                fileUpload.mkdirs();
            }
            
            //获取文件的原文件名,保存到服务器的文件名,需要重新定义,防止同名文件覆盖问题,而且防止中文乱码问题
    		String oldFileName = file.getOriginalFilename();//原文件名
    		//获取文件的后缀,用于判断文件的格式是否正确
    		String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀   
    		
            String newNm=SnowFlake.getSequence()+prefix;// 文件新名称
            fileUpload = new File(filePath, newNm);
            
            if (fileUpload.exists()) {
            	commonResult.setCode(500);
            	commonResult.setMessage("文件已存在");
            	return commonResult;
            }
            file.transferTo(fileUpload);
            
            Map respMap=new HashMap();
            respMap.put("fileName", filePath+File.separator+newNm);
            commonResult.setData(respMap);
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
        	commonResult.setMessage("文件上传服务异常："+e.getMessage());
        	return commonResult;
		}
        
        return commonResult;
    }
}

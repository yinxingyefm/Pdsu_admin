package pdsu.project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pdsu.dqw.util.ResponseData;
import pdsu.project.service.impl.ExcelTool;

@RestController
@RequestMapping("/")
public class UpController {
	@Autowired
	private ExcelTool exceltool;
	
	@PostMapping("fileupload")
    public ResponseData fileload(MultipartFile file, HttpServletRequest request) throws IOException {
		FileOutputStream fos=null;
        InputStream in=null;
		ResponseData responseData = ResponseData.ok();
        //获取文件名称
        String fileName = file.getOriginalFilename();
        //时间格式化格式
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并作为时间戳
        String timeStamp=simpleDateFormat.format(new Date());
        //拼接新的文件名
        String newName =timeStamp+fileName;
        // 获得项目的路径
        ServletContext sc = request.getSession().getServletContext();
        // 上传位置项目下的服务器地址
        String path = sc.getRealPath("/files") + "\\"; // 设定文件保存的目录
        //String path="D:/test1/";
        System.out.println(path);
        
        File f= new File(path,newName);
        //判断路径是否存在，不存在则创建
        if(!f.exists()){
            f.mkdirs();
        }
        //判断上传文件是否为空
        if(!file.isEmpty()){
            try {
            	file.transferTo(f);
                System.out.println("filepath:"+path + newName);
                responseData.getData().put("upload","success");
                //向数据库插入数据   	
                Boolean importTrue = exceltool.Excel(path + newName);
              
                if(importTrue==true){
                responseData.getData().put("import","success");
                }else {
                responseData.getData().put("import","faild");
				}
            }catch (Exception e){
                e.printStackTrace();
                responseData=ResponseData.ImportFileError();
            }finally {
            	if(in!=null){
            		in.close();
            	}
            	if(fos!=null){
            		fos.close();
            	}
			}
        }else {
        	System.out.println("文件为空");
        	responseData=ResponseData.UploadNullFile();
		}
        return responseData;
    }
}

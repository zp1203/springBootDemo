package com.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * Created by zp on 2019/4/25.
 * @author zp
 */
@Controller
@RequestMapping(value = "/file/")
public class FileController {

    /**
     * 获取 上传文件路径
     */
    @Value(value = "${filePath}")
    private String filePath;




    /**
     * 先展示 fileUploadAndDownload 页面
     *
     * @return
     */
    @RequestMapping(value = "uploadReady")
    public String upLoadReady(){
        return "fileUploadAndDownload";
    }




    /**
     * 在选择文件点击上传
     * @param multipartFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "upload")
    public String upLoad(@RequestParam("cdssfile") MultipartFile multipartFile){

        //获得文件名
        String fileName = multipartFile.getOriginalFilename();
        //对文件名进行处理
        fileName = filePath + UUID.randomUUID() + fileName;
        //文件对象
        File file = new File(fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(file);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 文件的下载
     *
     */
    @RequestMapping(value = "downLoad")
    public void downLoad(HttpServletResponse response) throws IOException {

        //找到文件
        File file = new File("D:\\web2\\hello.html");
        //文件流
        FileInputStream fileInputStream = new FileInputStream(file);

        response.setContentType("application/force-download");
        //设置下载的文件名
        response.setHeader("Content-disposition","attachment;fileName=hello.html");
        OutputStream os = response.getOutputStream();
        byte[] buf = new byte[1024];
        int  len = 0;
        while((len=fileInputStream.read(buf))!=-1){
            os.write(buf,0,len);
        }



    }

}
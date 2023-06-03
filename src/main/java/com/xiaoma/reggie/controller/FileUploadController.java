package com.xiaoma.reggie.controller;

import com.xiaoma.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class FileUploadController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * spring框架在spring-web包中对文件上传进行了封装，只需要在Controller的方法中声明一个
     * MultipartFile类型的参数即可接收上传的文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //1.获取原始文件名xxx.jpg
        String originalFilename = file.getOriginalFilename();
        //2.截取文件名后缀.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3.使用UUID重新生成文件名，防止文件名重复造成文件覆盖
        String fileName = UUID.randomUUID().toString()+suffix;
        //4.创建一个目录对象
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdir();
        }
        //5.将临时文件转储到指定位置
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //6.返回文件名称
        return R.success(fileName);
    }


    /**
     * 文件下载
     * @param name 前端传过来的参数---文件名
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //1.输入流，通过输入流读取上传的文件
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
            //2.输出流，通过输出流将文件写回了浏览器，在浏览器展示
            ServletOutputStream outputStream = response.getOutputStream();
            //3.设置相应格式是图片格式
            response.setContentType("image/jpeg");
            //4.将文件读取进bytes数组，通过输出流写回浏览器
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //5.关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

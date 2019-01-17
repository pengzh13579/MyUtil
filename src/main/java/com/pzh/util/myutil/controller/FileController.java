package com.pzh.util.myutil.controller;

import com.pzh.util.myutil.common.utils.PictureAddWaterMarkUtil;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping ("/fileController")
public class FileController {

    /**
     * 文件上传
     * @param request
     * @param response
     * @param uploadFile
     * @throws Exception
     */
    @RequestMapping(value = "/addWaterMark")
    @ResponseBody
    public String addWaterMark(@RequestParam (value = "uploadFile")
            CommonsMultipartFile[] uploadFile, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (uploadFile[0].getSize() > 0) {
                Font font = new Font("微软雅黑", Font.PLAIN, 60);
                Color color = new Color(255,255,255,128);
                response.setContentType("image/jpeg");
                OutputStream bos = response.getOutputStream();
                //PictureAddWaterMarkUtil.addWaterMark(uploadFile[0].getInputStream(), "12333333333333", color, font, bos);
//                bos.flush();
//                bos.close();
            }
            return "{\"msg\":\"OK\"}";
        } catch(Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"ERROR\"}";
        }

    }
}

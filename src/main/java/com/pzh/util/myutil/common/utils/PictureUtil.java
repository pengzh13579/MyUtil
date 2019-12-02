package com.pzh.util.myutil.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/***
 * 图片操作工具类
 * @author pengzh
 */
public class PictureUtil {

    private PictureUtil() {
    }

    /**
     * 为图片添加水印
     * @param input 源图片
     * @param waterMarkContent 水印内容
     * @param markContentColor 水印颜色
     * @param font 水印字体
     */
    public static void addWaterMark(InputStream input, String waterMarkContent, Color markContentColor, Font font, OutputStream output) {
        try {
            // 读取原图片信息
            Image srcImg = ImageIO.read(input);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            // 根据图片的背景设置水印颜色
            g.setColor(markContentColor);
            // 设置字体
            g.setFont(font);

            //设置水印的坐标
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 10;
            int y = srcImgHeight - 10;
            // 画出水印
            g.drawString(waterMarkContent, x, y);
            FileOutputStream fos = new FileOutputStream(new File("E:/1.jpg"));
            // 输出图片
            ImageIO.write(bufImg, "jpg", output);
            ImageIO.write(bufImg, "jpg", fos);
            g.dispose();
            System.out.println("添加水印完成");
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}

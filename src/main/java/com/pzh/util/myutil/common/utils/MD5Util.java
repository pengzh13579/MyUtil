package com.pzh.util.myutil.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/***
 * MD5工具类
 * @author pengzh
 */
public class MD5Util {

    private MD5Util() {
    }

    /***
     * 加密字符串
     * @param str 待加密字符串
     * @return MD5加密后数据
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String EncoderStringByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return EncoderStringByMd5(str, "utf-8");
    }

    /***
     * 加密字符串
     * @param str 待加密字符串
     * @param charset 编码格式
     * @return MD5加密后数据
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String EncoderStringByMd5(String str, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();

        // 加密后的字符串
        String newStr = base64en.encode(md5.digest(str.getBytes(charset)));
        return newStr;
    }
    /**
     * 加密文件
     */
    public static String EncoderFileByMd5(File file) {

        String newstr = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int length = -1;
            while ((length = bis.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            // 加密后的字符串
            newstr = base64en.encode(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseIOUtil.closeAll(bis, fis);
        }
        return newstr;
    }
}


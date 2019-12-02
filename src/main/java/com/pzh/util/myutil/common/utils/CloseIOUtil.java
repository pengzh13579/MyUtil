package com.pzh.util.myutil.common.utils;

import java.io.Closeable;

/***
 * IO流关闭工具类
 * @author pengzh
 */
public class CloseIOUtil {

    private CloseIOUtil() {
    }

    /***
     * 关闭IO流
     * @param cls 需要关闭的IO流
     */
    public static void closeAll(Closeable... cls) {
        if (cls != null) {
            for (Closeable cl : cls) {
                try {
                    if (cl != null) {
                        cl.close();
                    }
                } catch (Exception e) {
                } finally {
                    cl = null;
                }
            }
        }
    }
}


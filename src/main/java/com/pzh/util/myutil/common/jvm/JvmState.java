package com.pzh.util.myutil.common.jvm;

import com.pzh.util.myutil.common.system.GetSystemInfo;
import com.pzh.util.myutil.common.utils.ArrayUtil;
import com.pzh.util.myutil.common.utils.ExecuteCmdUtil;
import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public class JvmState {

    /***
     * Jstat 模板方法
     * @param strings 命令
     * @return 集合
     */
    private static List<Pair<String, String>> jvmState(String[] strings) throws Exception {
        List<Pair<String, String>> list = new ArrayList<>();
        String s = ExecuteCmdUtil.execute(strings);
        assert s != null;
        BufferedReader reader = new BufferedReader(new StringReader(s));
        String[] keys = ArrayUtil.trim(reader.readLine().split("\\s+|\t"));
        String[] values = ArrayUtil.trim(reader.readLine().split("\\s+|\t"));
        // 特殊情况
        if (strings[1].equals("-compiler")) {
            for (int i = 0; i < 4; i++) {
                list.add(new Pair<String,String>(keys[i], values[i]));
            }
            return list;
        }
        // 正常流程
        for (int i = 0; i < keys.length; i++) {
            list.add(new Pair(keys[i], values[i]));
        }
        return list;
    }

    /***
     * 类加载信息
     * X轴为时间，Y轴为值的变化
     * @return
     */
    public static List<Pair<String, String>> jvmStateClass() throws Exception {
        String id = getPid();
        List<Pair<String, String>> jvmStateClass = jvmState(new String[]{"jstat", "-class", id});
        List<Pair<String, String>> jvmStateCompiler = jvmState(new String[]{"jstat", "-compiler", id});
        jvmStateClass.addAll(jvmStateCompiler);
        return jvmStateClass;
    }

    /***
     * 堆内存信息
     * X轴为时间，Y轴为值的变化
     * @return
     */
    public static List<Pair<String, String>> jvmStateGc() throws Exception {
        String id=getPid();
        return jvmState(new String[]{"jstat", "-gc", id});
    }

    /***
     * 堆内存百分比
     * 实时监控
     * @return
     */
    public static List<Pair<String, String>> jvmStateUtil() throws Exception {
        String id=getPid();
        return jvmState(new String[]{"jstat", "-gcutil", id});
    }


    /***
     * 获取当前应用进程id
     * @return
     */
    public static String getPid(){
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return  pid;
    }

    /***
     * 执行jvm linux命令
     * @return
     */
    public static String getRuntimeExec(){
        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", "free");
            params.put("value", "-m");
            ArrayList<String> list=new ArrayList<>();
            for(String s:params.keySet()){
                list.add(params.get(s));
            }
            String[] array = (String[])list.toArray(new String[list.size()]);
            String result = ExecuteCmdUtil.execute(array);
            return result;
        }catch (Exception e){
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
        while(true){
            GetSystemInfo s = new GetSystemInfo();
            s.getSystemInfo();
            System.out.println(s.getJvm());
        }
    }
}

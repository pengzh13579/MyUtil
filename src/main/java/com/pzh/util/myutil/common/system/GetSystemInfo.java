package com.pzh.util.myutil.common.system;

import com.pzh.util.myutil.common.model.CpuInfo;
import com.pzh.util.myutil.common.model.JvmInfo;
import com.pzh.util.myutil.common.model.MemoryInfo;
import com.pzh.util.myutil.common.model.SystemFileInfo;
import com.pzh.util.myutil.common.utils.ArithmeticalMethodUtil;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/***
 * 获取系统相关信息,包含CPU/内存/硬盘等信息
 */
public class GetSystemInfo {

    private static final int OS_HI_WAIT_SECOND = 1000;

    /***
     * CPU相关信息
     */
    private CpuInfo cpu = new CpuInfo();

    /***
     * 內存相关信息
     */
    private MemoryInfo mem = new MemoryInfo();

    /***
     * JVM相关信息
     */
    private JvmInfo jvm = new JvmInfo();

    /***
     * 磁盘相关信息
     */
    private List<SystemFileInfo> sysFiles = new LinkedList<>();

    public CpuInfo getCpu() {
        return cpu;
    }

    public void setCpu(CpuInfo cpu) {
        this.cpu = cpu;
    }

    public MemoryInfo getMem() {
        return mem;
    }

    public void setMem(MemoryInfo mem) {
        this.mem = mem;
    }

    public JvmInfo getJvm() {
        return jvm;
    }

    public void setJvm(JvmInfo jvm) {
        this.jvm = jvm;
    }

    public List<SystemFileInfo> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SystemFileInfo> sysFiles) {
        this.sysFiles = sysFiles;
    }

    /***
     * 获取系统相关信息,包含CPU/内存/硬盘等信息
     */
    public void getSystemInfo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        setCpuInfo(hal.getProcessor());

        setMemInfo(hal.getMemory());

        setJvmInfo();

        setSysFiles(si.getOperatingSystem());
    }

    /***
     * 设置CPU信息
     */
    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OS_HI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softIrq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long ioWait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + ioWait + irq + softIrq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(ioWait);
        cpu.setFree(idle);
    }

    /***
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    /***
     * 设置Java虚拟机
     */
    private void setJvmInfo() {
        Properties props = System.getProperties();
        jvm.setTotalByte(Runtime.getRuntime().totalMemory());
        jvm.setMaxByte(Runtime.getRuntime().maxMemory());
        jvm.setFreeByte(Runtime.getRuntime().freeMemory());
        jvm.setTotal(convertFileSize(Runtime.getRuntime().totalMemory()));
        jvm.setMax(convertFileSize(Runtime.getRuntime().maxMemory()));
        jvm.setFree(convertFileSize(Runtime.getRuntime().freeMemory()));
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    /***
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray)
        {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SystemFileInfo sysFile = new SystemFileInfo();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(ArithmeticalMethodUtil.mul(ArithmeticalMethodUtil.div(used, total, 4).doubleValue(), 100).doubleValue());
            sysFiles.add(sysFile);
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size)
    {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb)
        {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb)
        {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb)
        {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else
        {
            return String.format("%d B", size);
        }
    }

}

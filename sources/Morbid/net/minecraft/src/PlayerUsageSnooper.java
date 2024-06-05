package net.minecraft.src;

import java.net.*;
import java.lang.management.*;
import java.util.*;

public class PlayerUsageSnooper
{
    private Map dataMap;
    private final String uniqueID;
    private final URL serverUrl;
    private final IPlayerUsage playerStatsCollector;
    private final Timer threadTrigger;
    private final Object syncLock;
    private final long field_98224_g;
    private boolean isRunning;
    private int selfCounter;
    
    public PlayerUsageSnooper(final String par1Str, final IPlayerUsage par2IPlayerUsage) {
        this.dataMap = new HashMap();
        this.uniqueID = UUID.randomUUID().toString();
        this.threadTrigger = new Timer("Snooper Timer", true);
        this.syncLock = new Object();
        this.field_98224_g = System.currentTimeMillis();
        this.isRunning = false;
        this.selfCounter = 0;
        try {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + par1Str + "?version=" + 1);
        }
        catch (MalformedURLException var4) {
            throw new IllegalArgumentException();
        }
        this.playerStatsCollector = par2IPlayerUsage;
    }
    
    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.addBaseDataToSnooper();
            this.threadTrigger.schedule(new PlayerUsageSnooperThread(this), 0L, 900000L);
        }
    }
    
    private void addBaseDataToSnooper() {
        this.addJvmArgsToSnooper();
        this.addData("snooper_token", this.uniqueID);
        this.addData("os_name", System.getProperty("os.name"));
        this.addData("os_version", System.getProperty("os.version"));
        this.addData("os_architecture", System.getProperty("os.arch"));
        this.addData("java_version", System.getProperty("java.version"));
        this.addData("version", "1.5.2");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }
    
    private void addJvmArgsToSnooper() {
        final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        final List var2 = var1.getInputArguments();
        int var3 = 0;
        for (final String var5 : var2) {
            if (var5.startsWith("-X")) {
                this.addData("jvm_arg[" + var3++ + "]", var5);
            }
        }
        this.addData("jvm_args", var3);
    }
    
    public void addMemoryStatsToSnooper() {
        this.addData("memory_total", Runtime.getRuntime().totalMemory());
        this.addData("memory_max", Runtime.getRuntime().maxMemory());
        this.addData("memory_free", Runtime.getRuntime().freeMemory());
        this.addData("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.addData("run_time", (System.currentTimeMillis() - this.field_98224_g) / 60L * 1000L);
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }
    
    public void addData(final String par1Str, final Object par2Obj) {
        final Object var3 = this.syncLock;
        synchronized (this.syncLock) {
            this.dataMap.put(par1Str, par2Obj);
        }
        // monitorexit(this.syncLock)
    }
    
    public Map getCurrentStats() {
        final LinkedHashMap var1 = new LinkedHashMap();
        final Object var2 = this.syncLock;
        synchronized (this.syncLock) {
            this.addMemoryStatsToSnooper();
            for (final Map.Entry var4 : this.dataMap.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
            // monitorexit(this.syncLock)
            return var1;
        }
    }
    
    public boolean isSnooperRunning() {
        return this.isRunning;
    }
    
    public void stopSnooper() {
        this.threadTrigger.cancel();
    }
    
    public String getUniqueID() {
        return this.uniqueID;
    }
    
    static IPlayerUsage getStatsCollectorFor(final PlayerUsageSnooper par0PlayerUsageSnooper) {
        return par0PlayerUsageSnooper.playerStatsCollector;
    }
    
    static Object getSyncLockFor(final PlayerUsageSnooper par0PlayerUsageSnooper) {
        return par0PlayerUsageSnooper.syncLock;
    }
    
    static Map getDataMapFor(final PlayerUsageSnooper par0PlayerUsageSnooper) {
        return par0PlayerUsageSnooper.dataMap;
    }
    
    static int getSelfCounterFor(final PlayerUsageSnooper par0PlayerUsageSnooper) {
        return par0PlayerUsageSnooper.selfCounter++;
    }
    
    static URL getServerUrlFor(final PlayerUsageSnooper par0PlayerUsageSnooper) {
        return par0PlayerUsageSnooper.serverUrl;
    }
}

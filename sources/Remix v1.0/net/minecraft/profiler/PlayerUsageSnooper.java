package net.minecraft.profiler;

import com.google.common.collect.*;
import java.net.*;
import net.minecraft.util.*;
import java.lang.management.*;
import java.util.*;

public class PlayerUsageSnooper
{
    private final Map field_152773_a;
    private final Map field_152774_b;
    private final String uniqueID;
    private final URL serverUrl;
    private final IPlayerUsage playerStatsCollector;
    private final Timer threadTrigger;
    private final Object syncLock;
    private final long minecraftStartTimeMilis;
    private boolean isRunning;
    private int selfCounter;
    
    public PlayerUsageSnooper(final String p_i1563_1_, final IPlayerUsage p_i1563_2_, final long p_i1563_3_) {
        this.field_152773_a = Maps.newHashMap();
        this.field_152774_b = Maps.newHashMap();
        this.uniqueID = UUID.randomUUID().toString();
        this.threadTrigger = new Timer("Snooper Timer", true);
        this.syncLock = new Object();
        try {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + p_i1563_1_ + "?version=" + 2);
        }
        catch (MalformedURLException var6) {
            throw new IllegalArgumentException();
        }
        this.playerStatsCollector = p_i1563_2_;
        this.minecraftStartTimeMilis = p_i1563_3_;
    }
    
    static int access$308(final PlayerUsageSnooper p_access$308_0_) {
        return p_access$308_0_.selfCounter++;
    }
    
    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.func_152766_h();
            this.threadTrigger.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (PlayerUsageSnooper.this.playerStatsCollector.isSnooperEnabled()) {
                        final HashMap var1;
                        synchronized (PlayerUsageSnooper.this.syncLock) {
                            var1 = Maps.newHashMap(PlayerUsageSnooper.this.field_152774_b);
                            if (PlayerUsageSnooper.this.selfCounter == 0) {
                                var1.putAll(PlayerUsageSnooper.this.field_152773_a);
                            }
                            var1.put("snooper_count", PlayerUsageSnooper.access$308(PlayerUsageSnooper.this));
                            var1.put("snooper_token", PlayerUsageSnooper.this.uniqueID);
                        }
                        HttpUtil.postMap(PlayerUsageSnooper.this.serverUrl, var1, true);
                    }
                }
            }, 0L, 900000L);
        }
    }
    
    private void func_152766_h() {
        this.addJvmArgsToSnooper();
        this.addClientStat("snooper_token", this.uniqueID);
        this.addStatToSnooper("snooper_token", this.uniqueID);
        this.addStatToSnooper("os_name", System.getProperty("os.name"));
        this.addStatToSnooper("os_version", System.getProperty("os.version"));
        this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
        this.addStatToSnooper("java_version", System.getProperty("java.version"));
        this.addStatToSnooper("version", "1.8");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }
    
    private void addJvmArgsToSnooper() {
        final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        final List var2 = var1.getInputArguments();
        int var3 = 0;
        for (final String var5 : var2) {
            if (var5.startsWith("-X")) {
                this.addClientStat("jvm_arg[" + var3++ + "]", var5);
            }
        }
        this.addClientStat("jvm_args", var3);
    }
    
    public void addMemoryStatsToSnooper() {
        this.addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
        this.addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
        this.addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
        this.addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }
    
    public void addClientStat(final String p_152768_1_, final Object p_152768_2_) {
        final Object var3 = this.syncLock;
        synchronized (this.syncLock) {
            this.field_152774_b.put(p_152768_1_, p_152768_2_);
        }
    }
    
    public void addStatToSnooper(final String p_152767_1_, final Object p_152767_2_) {
        final Object var3 = this.syncLock;
        synchronized (this.syncLock) {
            this.field_152773_a.put(p_152767_1_, p_152767_2_);
        }
    }
    
    public Map getCurrentStats() {
        final LinkedHashMap var1 = Maps.newLinkedHashMap();
        final Object var2 = this.syncLock;
        synchronized (this.syncLock) {
            this.addMemoryStatsToSnooper();
            for (final Map.Entry var4 : this.field_152773_a.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
            for (final Map.Entry var4 : this.field_152774_b.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
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
    
    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }
}

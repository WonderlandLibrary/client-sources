package HORIZON-6-0-SKIDPROTECTION;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.TimerTask;
import java.net.MalformedURLException;
import java.util.UUID;
import com.google.common.collect.Maps;
import java.util.Timer;
import java.net.URL;
import java.util.Map;

public class PlayerUsageSnooper
{
    private final Map HorizonCode_Horizon_È;
    private final Map Â;
    private final String Ý;
    private final URL Ø­áŒŠá;
    private final IPlayerUsage Âµá€;
    private final Timer Ó;
    private final Object à;
    private final long Ø;
    private boolean áŒŠÆ;
    private int áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001515";
    
    public PlayerUsageSnooper(final String p_i1563_1_, final IPlayerUsage p_i1563_2_, final long p_i1563_3_) {
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        this.Â = Maps.newHashMap();
        this.Ý = UUID.randomUUID().toString();
        this.Ó = new Timer("Snooper Timer", true);
        this.à = new Object();
        try {
            this.Ø­áŒŠá = new URL("http://snoop.minecraft.net/" + p_i1563_1_ + "?version=" + 2);
        }
        catch (MalformedURLException var6) {
            throw new IllegalArgumentException();
        }
        this.Âµá€ = p_i1563_2_;
        this.Ø = p_i1563_3_;
    }
    
    public void HorizonCode_Horizon_È() {
        if (!this.áŒŠÆ) {
            this.áŒŠÆ = true;
            this.Ø();
            this.Ó.schedule(new TimerTask() {
                private static final String Â = "CL_00001516";
                
                @Override
                public void run() {
                    if (PlayerUsageSnooper.this.Âµá€.ŒÏ()) {
                        final HashMap var1;
                        synchronized (PlayerUsageSnooper.this.à) {
                            var1 = Maps.newHashMap(PlayerUsageSnooper.this.Â);
                            if (PlayerUsageSnooper.this.áˆºÑ¢Õ == 0) {
                                var1.putAll(PlayerUsageSnooper.this.HorizonCode_Horizon_È);
                            }
                            var1.put("snooper_count", PlayerUsageSnooper.HorizonCode_Horizon_È(PlayerUsageSnooper.this));
                            var1.put("snooper_token", PlayerUsageSnooper.this.Ý);
                        }
                        // monitorexit(PlayerUsageSnooper.\u00dd(this.HorizonCode_Horizon_\u00c8))
                        HttpUtil.HorizonCode_Horizon_È(PlayerUsageSnooper.this.Ø­áŒŠá, var1, true);
                    }
                }
            }, 0L, 900000L);
        }
    }
    
    private void Ø() {
        this.áŒŠÆ();
        this.HorizonCode_Horizon_È("snooper_token", this.Ý);
        this.Â("snooper_token", this.Ý);
        this.Â("os_name", System.getProperty("os.name"));
        this.Â("os_version", System.getProperty("os.version"));
        this.Â("os_architecture", System.getProperty("os.arch"));
        this.Â("java_version", System.getProperty("java.version"));
        this.Â("version", "1.8");
        this.Âµá€.Â(this);
    }
    
    private void áŒŠÆ() {
        final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        final List var2 = var1.getInputArguments();
        int var3 = 0;
        for (final String var5 : var2) {
            if (var5.startsWith("-X")) {
                this.HorizonCode_Horizon_È("jvm_arg[" + var3++ + "]", var5);
            }
        }
        this.HorizonCode_Horizon_È("jvm_args", var3);
    }
    
    public void Â() {
        this.Â("memory_total", Runtime.getRuntime().totalMemory());
        this.Â("memory_max", Runtime.getRuntime().maxMemory());
        this.Â("memory_free", Runtime.getRuntime().freeMemory());
        this.Â("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.Âµá€.HorizonCode_Horizon_È(this);
    }
    
    public void HorizonCode_Horizon_È(final String p_152768_1_, final Object p_152768_2_) {
        final Object var3 = this.à;
        synchronized (this.à) {
            this.Â.put(p_152768_1_, p_152768_2_);
        }
        // monitorexit(this.\u00e0)
    }
    
    public void Â(final String p_152767_1_, final Object p_152767_2_) {
        final Object var3 = this.à;
        synchronized (this.à) {
            this.HorizonCode_Horizon_È.put(p_152767_1_, p_152767_2_);
        }
        // monitorexit(this.\u00e0)
    }
    
    public Map Ý() {
        final LinkedHashMap var1 = Maps.newLinkedHashMap();
        final Object var2 = this.à;
        synchronized (this.à) {
            this.Â();
            for (final Map.Entry var4 : this.HorizonCode_Horizon_È.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
            for (final Map.Entry var4 : this.Â.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
            // monitorexit(this.\u00e0)
            return var1;
        }
    }
    
    public boolean Ø­áŒŠá() {
        return this.áŒŠÆ;
    }
    
    public void Âµá€() {
        this.Ó.cancel();
    }
    
    public String Ó() {
        return this.Ý;
    }
    
    public long à() {
        return this.Ø;
    }
    
    static int HorizonCode_Horizon_È(final PlayerUsageSnooper p_access$308_0_) {
        return p_access$308_0_.áˆºÑ¢Õ++;
    }
}

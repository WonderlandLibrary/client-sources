package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class Profiler
{
    private static final Logger Ý;
    private final List Ø­áŒŠá;
    private final List Âµá€;
    public boolean HorizonCode_Horizon_È;
    private String Ó;
    private final Map à;
    private static final String Ø = "CL_00001497";
    public boolean Â;
    private boolean áŒŠÆ;
    private static final String áˆºÑ¢Õ = "scheduledExecutables";
    private static final String ÂµÈ = "tick";
    private static final String á = "preRenderErrors";
    private static final int ˆÏ­;
    private static final int £á;
    private static final int Å;
    
    static {
        Ý = LogManager.getLogger();
        ˆÏ­ = "scheduledExecutables".hashCode();
        £á = "tick".hashCode();
        Å = "preRenderErrors".hashCode();
    }
    
    public Profiler() {
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Âµá€ = Lists.newArrayList();
        this.Ó = "";
        this.à = Maps.newHashMap();
        this.Â = true;
        this.áŒŠÆ = this.Â;
    }
    
    public void HorizonCode_Horizon_È() {
        this.à.clear();
        this.Ó = "";
        this.Ø­áŒŠá.clear();
        this.áŒŠÆ = this.Â;
    }
    
    public void HorizonCode_Horizon_È(final String name) {
        if (Lagometer.Ý()) {
            final int hashName = name.hashCode();
            if (hashName == Profiler.ˆÏ­ && name.equals("scheduledExecutables")) {
                Lagometer.Ý.HorizonCode_Horizon_È();
            }
            else if (hashName == Profiler.£á && name.equals("tick") && Config.Ø()) {
                Lagometer.Ý.Â();
                Lagometer.Â.HorizonCode_Horizon_È();
            }
            else if (hashName == Profiler.Å && name.equals("preRenderErrors")) {
                Lagometer.Â.Â();
            }
        }
        if (this.áŒŠÆ && this.HorizonCode_Horizon_È) {
            if (this.Ó.length() > 0) {
                this.Ó = String.valueOf(this.Ó) + ".";
            }
            this.Ó = String.valueOf(this.Ó) + name;
            this.Ø­áŒŠá.add(this.Ó);
            this.Âµá€.add(System.nanoTime());
        }
    }
    
    public void Â() {
        if (this.áŒŠÆ && this.HorizonCode_Horizon_È) {
            final long var1 = System.nanoTime();
            final long var2 = this.Âµá€.remove(this.Âµá€.size() - 1);
            this.Ø­áŒŠá.remove(this.Ø­áŒŠá.size() - 1);
            final long var3 = var1 - var2;
            if (this.à.containsKey(this.Ó)) {
                this.à.put(this.Ó, this.à.get(this.Ó) + var3);
            }
            else {
                this.à.put(this.Ó, var3);
            }
            if (var3 > 100000000L) {
                Profiler.Ý.warn("Something's taking too long! '" + this.Ó + "' took aprox " + var3 / 1000000.0 + " ms");
            }
            this.Ó = (this.Ø­áŒŠá.isEmpty() ? "" : this.Ø­áŒŠá.get(this.Ø­áŒŠá.size() - 1));
        }
    }
    
    public List Â(String p_76321_1_) {
        if (!(this.áŒŠÆ = this.Â)) {
            return new ArrayList(Arrays.asList(new HorizonCode_Horizon_È("root", 0.0, 0.0)));
        }
        if (!this.HorizonCode_Horizon_È) {
            return null;
        }
        long var3 = this.à.containsKey("root") ? this.à.get("root") : 0L;
        final long var4 = this.à.containsKey(p_76321_1_) ? this.à.get(p_76321_1_) : -1L;
        final ArrayList var5 = Lists.newArrayList();
        if (p_76321_1_.length() > 0) {
            p_76321_1_ = String.valueOf(p_76321_1_) + ".";
        }
        long var6 = 0L;
        for (final String var8 : this.à.keySet()) {
            if (var8.length() > p_76321_1_.length() && var8.startsWith(p_76321_1_) && var8.indexOf(".", p_76321_1_.length() + 1) < 0) {
                var6 += this.à.get(var8);
            }
        }
        final float var9 = var6;
        if (var6 < var4) {
            var6 = var4;
        }
        if (var3 < var6) {
            var3 = var6;
        }
        for (final String var11 : this.à.keySet()) {
            if (var11.length() > p_76321_1_.length() && var11.startsWith(p_76321_1_) && var11.indexOf(".", p_76321_1_.length() + 1) < 0) {
                final long var12 = this.à.get(var11);
                final double var13 = var12 * 100.0 / var6;
                final double var14 = var12 * 100.0 / var3;
                final String var15 = var11.substring(p_76321_1_.length());
                var5.add(new HorizonCode_Horizon_È(var15, var13, var14));
            }
        }
        for (final String var11 : this.à.keySet()) {
            this.à.put(var11, this.à.get(var11) * 950L / 1000L);
        }
        if (var6 > var9) {
            var5.add(new HorizonCode_Horizon_È("unspecified", (var6 - var9) * 100.0 / var6, (var6 - var9) * 100.0 / var3));
        }
        Collections.sort((List<Comparable>)var5);
        var5.add(0, new HorizonCode_Horizon_È(p_76321_1_, 100.0, var6 * 100.0 / var3));
        return var5;
    }
    
    public void Ý(final String name) {
        if (this.áŒŠÆ) {
            this.Â();
            this.HorizonCode_Horizon_È(name);
        }
    }
    
    public String Ý() {
        return (this.Ø­áŒŠá.size() == 0) ? "[UNKNOWN]" : this.Ø­áŒŠá.get(this.Ø­áŒŠá.size() - 1);
    }
    
    public static final class HorizonCode_Horizon_È implements Comparable
    {
        public double HorizonCode_Horizon_È;
        public double Â;
        public String Ý;
        private static final String Ø­áŒŠá = "CL_00001498";
        
        public HorizonCode_Horizon_È(final String p_i1554_1_, final double p_i1554_2_, final double p_i1554_4_) {
            this.Ý = p_i1554_1_;
            this.HorizonCode_Horizon_È = p_i1554_2_;
            this.Â = p_i1554_4_;
        }
        
        public int HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_compareTo_1_) {
            return (p_compareTo_1_.HorizonCode_Horizon_È < this.HorizonCode_Horizon_È) ? -1 : ((p_compareTo_1_.HorizonCode_Horizon_È > this.HorizonCode_Horizon_È) ? 1 : p_compareTo_1_.Ý.compareTo(this.Ý));
        }
        
        public int HorizonCode_Horizon_È() {
            return (this.Ý.hashCode() & 0xAAAAAA) + 4473924;
        }
        
        @Override
        public int compareTo(final Object p_compareTo_1_) {
            return this.HorizonCode_Horizon_È((HorizonCode_Horizon_È)p_compareTo_1_);
        }
    }
}

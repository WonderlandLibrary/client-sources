package net.minecraft.src;

import java.util.*;

public class Profiler
{
    private final List sectionList;
    private final List timestampList;
    public boolean profilingEnabled;
    private String profilingSection;
    private final Map profilingMap;
    public boolean profilerGlobalEnabled;
    private boolean profilerLocalEnabled;
    private long startTickNano;
    public long timeTickNano;
    private long startUpdateChunksNano;
    public long timeUpdateChunksNano;
    
    public Profiler() {
        this.sectionList = new ArrayList();
        this.timestampList = new ArrayList();
        this.profilingEnabled = false;
        this.profilingSection = "";
        this.profilingMap = new HashMap();
        this.profilerGlobalEnabled = true;
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
        this.startTickNano = 0L;
        this.timeTickNano = 0L;
        this.startUpdateChunksNano = 0L;
        this.timeUpdateChunksNano = 0L;
    }
    
    public void clearProfiling() {
        this.profilingMap.clear();
        this.profilingSection = "";
        this.sectionList.clear();
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
    }
    
    public void startSection(final String par1Str) {
        if (Config.getGameSettings().showDebugInfo) {
            if (this.startTickNano == 0L && par1Str.equals("tick")) {
                this.startTickNano = System.nanoTime();
            }
            if (this.startTickNano != 0L && par1Str.equals("preRenderErrors")) {
                this.timeTickNano = System.nanoTime() - this.startTickNano;
                this.startTickNano = 0L;
            }
            if (this.startUpdateChunksNano == 0L && par1Str.equals("updatechunks")) {
                this.startUpdateChunksNano = System.nanoTime();
            }
            if (this.startUpdateChunksNano != 0L && par1Str.equals("terrain")) {
                this.timeUpdateChunksNano = System.nanoTime() - this.startUpdateChunksNano;
                this.startUpdateChunksNano = 0L;
            }
        }
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            if (this.profilingSection.length() > 0) {
                this.profilingSection = String.valueOf(this.profilingSection) + ".";
            }
            this.profilingSection = String.valueOf(this.profilingSection) + par1Str;
            this.sectionList.add(this.profilingSection);
            this.timestampList.add(System.nanoTime());
        }
    }
    
    public void endSection() {
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            final long var1 = System.nanoTime();
            final long var2 = this.timestampList.remove(this.timestampList.size() - 1);
            this.sectionList.remove(this.sectionList.size() - 1);
            final long var3 = var1 - var2;
            if (this.profilingMap.containsKey(this.profilingSection)) {
                this.profilingMap.put(this.profilingSection, this.profilingMap.get(this.profilingSection) + var3);
            }
            else {
                this.profilingMap.put(this.profilingSection, var3);
            }
            if (var3 > 100000000L) {
                System.out.println("Something's taking too long! '" + this.profilingSection + "' took aprox " + var3 / 1000000.0 + " ms");
            }
            this.profilingSection = (this.sectionList.isEmpty() ? "" : this.sectionList.get(this.sectionList.size() - 1));
        }
    }
    
    public List getProfilingData(String par1Str) {
        if (!(this.profilerLocalEnabled = this.profilerGlobalEnabled)) {
            return new ArrayList(Arrays.asList(new ProfilerResult("root", 0.0, 0.0)));
        }
        if (!this.profilingEnabled) {
            return null;
        }
        long var2 = this.profilingMap.containsKey("root") ? this.profilingMap.get("root") : 0L;
        final long var3 = this.profilingMap.containsKey(par1Str) ? this.profilingMap.get(par1Str) : -1L;
        final ArrayList var4 = new ArrayList();
        if (par1Str.length() > 0) {
            par1Str = String.valueOf(par1Str) + ".";
        }
        long var5 = 0L;
        for (final String var7 : this.profilingMap.keySet()) {
            if (var7.length() > par1Str.length() && var7.startsWith(par1Str) && var7.indexOf(".", par1Str.length() + 1) < 0) {
                var5 += this.profilingMap.get(var7);
            }
        }
        final float var8 = var5;
        if (var5 < var3) {
            var5 = var3;
        }
        if (var2 < var5) {
            var2 = var5;
        }
        for (final String var10 : this.profilingMap.keySet()) {
            if (var10.length() > par1Str.length() && var10.startsWith(par1Str) && var10.indexOf(".", par1Str.length() + 1) < 0) {
                final long var11 = this.profilingMap.get(var10);
                final double var12 = var11 * 100.0 / var5;
                final double var13 = var11 * 100.0 / var2;
                final String var14 = var10.substring(par1Str.length());
                var4.add(new ProfilerResult(var14, var12, var13));
            }
        }
        for (final String var10 : this.profilingMap.keySet()) {
            this.profilingMap.put(var10, this.profilingMap.get(var10) * 999L / 1000L);
        }
        if (var5 > var8) {
            var4.add(new ProfilerResult("unspecified", (var5 - var8) * 100.0 / var5, (var5 - var8) * 100.0 / var2));
        }
        Collections.sort((List<Comparable>)var4);
        var4.add(0, new ProfilerResult(par1Str, 100.0, var5 * 100.0 / var2));
        return var4;
    }
    
    public void endStartSection(final String par1Str) {
        if (this.profilerLocalEnabled) {
            this.endSection();
            this.startSection(par1Str);
        }
    }
    
    public String getNameOfLastSection() {
        return (this.sectionList.size() == 0) ? "[UNKNOWN]" : this.sectionList.get(this.sectionList.size() - 1);
    }
}

package net.minecraft.src;

import java.io.*;
import argo.saj.*;
import argo.jdom.*;
import java.util.*;

public class StatFileWriter
{
    private Map field_77457_a;
    private Map field_77455_b;
    private boolean field_77456_c;
    private StatsSyncher statsSyncher;
    
    public StatFileWriter(final Session par1Session, final File par2File) {
        this.field_77457_a = new HashMap();
        this.field_77455_b = new HashMap();
        this.field_77456_c = false;
        final File var3 = new File(par2File, "stats");
        if (!var3.exists()) {
            var3.mkdir();
        }
        for (final File var7 : par2File.listFiles()) {
            if (var7.getName().startsWith("stats_") && var7.getName().endsWith(".dat")) {
                final File var8 = new File(var3, var7.getName());
                if (!var8.exists()) {
                    System.out.println("Relocating " + var7.getName());
                    var7.renameTo(var8);
                }
            }
        }
        this.statsSyncher = new StatsSyncher(par1Session, this, var3);
    }
    
    public void readStat(final StatBase par1StatBase, final int par2) {
        this.writeStatToMap(this.field_77455_b, par1StatBase, par2);
        this.writeStatToMap(this.field_77457_a, par1StatBase, par2);
        this.field_77456_c = true;
    }
    
    private void writeStatToMap(final Map par1Map, final StatBase par2StatBase, final int par3) {
        final Integer var4 = par1Map.get(par2StatBase);
        final int var5 = (var4 == null) ? 0 : var4;
        par1Map.put(par2StatBase, var5 + par3);
    }
    
    public Map func_77445_b() {
        return new HashMap(this.field_77455_b);
    }
    
    public void writeStats(final Map par1Map) {
        if (par1Map != null) {
            this.field_77456_c = true;
            for (final StatBase var3 : par1Map.keySet()) {
                this.writeStatToMap(this.field_77455_b, var3, par1Map.get(var3));
                this.writeStatToMap(this.field_77457_a, var3, par1Map.get(var3));
            }
        }
    }
    
    public void func_77452_b(final Map par1Map) {
        if (par1Map != null) {
            for (final StatBase var3 : par1Map.keySet()) {
                final Integer var4 = this.field_77455_b.get(var3);
                final int var5 = (var4 == null) ? 0 : var4;
                this.field_77457_a.put(var3, par1Map.get(var3) + var5);
            }
        }
    }
    
    public void func_77448_c(final Map par1Map) {
        if (par1Map != null) {
            this.field_77456_c = true;
            for (final StatBase var3 : par1Map.keySet()) {
                this.writeStatToMap(this.field_77455_b, var3, par1Map.get(var3));
            }
        }
    }
    
    public static Map func_77453_b(final String par0Str) {
        final HashMap var1 = new HashMap();
        try {
            final String var2 = "local";
            final StringBuilder var3 = new StringBuilder();
            final JsonRootNode var4 = new JdomParser().parse(par0Str);
            final List var5 = var4.getArrayNode("stats-change");
            for (final JsonNode var7 : var5) {
                final Map var8 = var7.getFields();
                final Map.Entry var9 = (Map.Entry)var8.entrySet().iterator().next();
                final int var10 = Integer.parseInt(var9.getKey().getText());
                final int var11 = Integer.parseInt(var9.getValue().getText());
                StatBase var12 = StatList.getOneShotStat(var10);
                if (var12 == null) {
                    System.out.println(String.valueOf(var10) + " is not a valid stat, creating place-holder");
                    var12 = new StatPlaceholder(var10).registerStat();
                }
                var3.append(StatList.getOneShotStat(var10).statGuid).append(",");
                var3.append(var11).append(",");
                var1.put(var12, var11);
            }
            final MD5String var13 = new MD5String(var2);
            final String var14 = var13.getMD5String(var3.toString());
            if (!var14.equals(var4.getStringValue("checksum"))) {
                System.out.println("CHECKSUM MISMATCH");
                return null;
            }
        }
        catch (InvalidSyntaxException var15) {
            var15.printStackTrace();
        }
        return var1;
    }
    
    public static String func_77441_a(final String par0Str, final String par1Str, final Map par2Map) {
        final StringBuilder var3 = new StringBuilder();
        final StringBuilder var4 = new StringBuilder();
        boolean var5 = true;
        var3.append("{\r\n");
        if (par0Str != null && par1Str != null) {
            var3.append("  \"user\":{\r\n");
            var3.append("    \"name\":\"").append(par0Str).append("\",\r\n");
            var3.append("    \"sessionid\":\"").append(par1Str).append("\"\r\n");
            var3.append("  },\r\n");
        }
        var3.append("  \"stats-change\":[");
        for (final StatBase var7 : par2Map.keySet()) {
            if (var5) {
                var5 = false;
            }
            else {
                var3.append("},");
            }
            var3.append("\r\n    {\"").append(var7.statId).append("\":").append(par2Map.get(var7));
            var4.append(var7.statGuid).append(",");
            var4.append(par2Map.get(var7)).append(",");
        }
        if (!var5) {
            var3.append("}");
        }
        final MD5String var8 = new MD5String(par1Str);
        var3.append("\r\n  ],\r\n");
        var3.append("  \"checksum\":\"").append(var8.getMD5String(var4.toString())).append("\"\r\n");
        var3.append("}");
        return var3.toString();
    }
    
    public boolean hasAchievementUnlocked(final Achievement par1Achievement) {
        return this.field_77457_a.containsKey(par1Achievement);
    }
    
    public boolean canUnlockAchievement(final Achievement par1Achievement) {
        return par1Achievement.parentAchievement == null || this.hasAchievementUnlocked(par1Achievement.parentAchievement);
    }
    
    public int writeStat(final StatBase par1StatBase) {
        final Integer var2 = this.field_77457_a.get(par1StatBase);
        return (var2 == null) ? 0 : var2;
    }
    
    public void syncStats() {
        this.statsSyncher.syncStatsFileWithMap(this.func_77445_b());
    }
    
    public void func_77449_e() {
        if (this.field_77456_c && this.statsSyncher.func_77425_c()) {
            this.statsSyncher.beginSendStats(this.func_77445_b());
        }
        this.statsSyncher.func_77422_e();
    }
}

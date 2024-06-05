package net.minecraft.src;

import java.util.*;

public class FlatGeneratorInfo
{
    private final List flatLayers;
    private final Map worldFeatures;
    private int biomeToUse;
    
    public FlatGeneratorInfo() {
        this.flatLayers = new ArrayList();
        this.worldFeatures = new HashMap();
        this.biomeToUse = 0;
    }
    
    public int getBiome() {
        return this.biomeToUse;
    }
    
    public void setBiome(final int par1) {
        this.biomeToUse = par1;
    }
    
    public Map getWorldFeatures() {
        return this.worldFeatures;
    }
    
    public List getFlatLayers() {
        return this.flatLayers;
    }
    
    public void func_82645_d() {
        int var1 = 0;
        for (final FlatLayerInfo var3 : this.flatLayers) {
            var3.setMinY(var1);
            var1 += var3.getLayerCount();
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder var1 = new StringBuilder();
        var1.append(2);
        var1.append(";");
        for (int var2 = 0; var2 < this.flatLayers.size(); ++var2) {
            if (var2 > 0) {
                var1.append(",");
            }
            var1.append(this.flatLayers.get(var2).toString());
        }
        var1.append(";");
        var1.append(this.biomeToUse);
        if (!this.worldFeatures.isEmpty()) {
            var1.append(";");
            int var2 = 0;
            for (final Map.Entry var4 : this.worldFeatures.entrySet()) {
                if (var2++ > 0) {
                    var1.append(",");
                }
                var1.append(var4.getKey().toLowerCase());
                final Map var5 = var4.getValue();
                if (!var5.isEmpty()) {
                    var1.append("(");
                    int var6 = 0;
                    for (final Map.Entry var8 : var5.entrySet()) {
                        if (var6++ > 0) {
                            var1.append(" ");
                        }
                        var1.append(var8.getKey());
                        var1.append("=");
                        var1.append(var8.getValue());
                    }
                    var1.append(")");
                }
            }
        }
        else {
            var1.append(";");
        }
        return var1.toString();
    }
    
    private static FlatLayerInfo func_82646_a(final String par0Str, final int par1) {
        String[] var2 = par0Str.split("x", 2);
        int var3 = 1;
        int var4 = 0;
        if (var2.length == 2) {
            try {
                var3 = Integer.parseInt(var2[0]);
                if (par1 + var3 >= 256) {
                    var3 = 256 - par1;
                }
                if (var3 < 0) {
                    var3 = 0;
                }
            }
            catch (Throwable var8) {
                return null;
            }
        }
        int var6;
        try {
            final String var5 = var2[var2.length - 1];
            var2 = var5.split(":", 2);
            var6 = Integer.parseInt(var2[0]);
            if (var2.length > 1) {
                var4 = Integer.parseInt(var2[1]);
            }
            if (Block.blocksList[var6] == null) {
                var6 = 0;
                var4 = 0;
            }
            if (var4 < 0 || var4 > 15) {
                var4 = 0;
            }
        }
        catch (Throwable var9) {
            return null;
        }
        final FlatLayerInfo var7 = new FlatLayerInfo(var3, var6, var4);
        var7.setMinY(par1);
        return var7;
    }
    
    private static List func_82652_b(final String par0Str) {
        if (par0Str != null && par0Str.length() >= 1) {
            final ArrayList var1 = new ArrayList();
            final String[] var2 = par0Str.split(",");
            int var3 = 0;
            final String[] var4 = var2;
            for (int var5 = var2.length, var6 = 0; var6 < var5; ++var6) {
                final String var7 = var4[var6];
                final FlatLayerInfo var8 = func_82646_a(var7, var3);
                if (var8 == null) {
                    return null;
                }
                var1.add(var8);
                var3 += var8.getLayerCount();
            }
            return var1;
        }
        return null;
    }
    
    public static FlatGeneratorInfo createFlatGeneratorFromString(final String par0Str) {
        if (par0Str == null) {
            return getDefaultFlatGenerator();
        }
        final String[] var1 = par0Str.split(";", -1);
        final int var2 = (var1.length == 1) ? 0 : MathHelper.parseIntWithDefault(var1[0], 0);
        if (var2 < 0 || var2 > 2) {
            return getDefaultFlatGenerator();
        }
        final FlatGeneratorInfo var3 = new FlatGeneratorInfo();
        int var4 = (var1.length != 1) ? 1 : 0;
        final List var5 = func_82652_b(var1[var4++]);
        if (var5 != null && !var5.isEmpty()) {
            var3.getFlatLayers().addAll(var5);
            var3.func_82645_d();
            int var6 = BiomeGenBase.plains.biomeID;
            if (var2 > 0 && var1.length > var4) {
                var6 = MathHelper.parseIntWithDefault(var1[var4++], var6);
            }
            var3.setBiome(var6);
            if (var2 > 0 && var1.length > var4) {
                final String[] var8;
                final String[] var7 = var8 = var1[var4++].toLowerCase().split(",");
                for (int var9 = var7.length, var10 = 0; var10 < var9; ++var10) {
                    final String var11 = var8[var10];
                    final String[] var12 = var11.split("\\(", 2);
                    final HashMap var13 = new HashMap();
                    if (var12[0].length() > 0) {
                        var3.getWorldFeatures().put(var12[0], var13);
                        if (var12.length > 1 && var12[1].endsWith(")") && var12[1].length() > 1) {
                            final String[] var14 = var12[1].substring(0, var12[1].length() - 1).split(" ");
                            for (int var15 = 0; var15 < var14.length; ++var15) {
                                final String[] var16 = var14[var15].split("=", 2);
                                if (var16.length == 2) {
                                    var13.put(var16[0], var16[1]);
                                }
                            }
                        }
                    }
                }
            }
            else {
                var3.getWorldFeatures().put("village", new HashMap());
            }
            return var3;
        }
        return getDefaultFlatGenerator();
    }
    
    public static FlatGeneratorInfo getDefaultFlatGenerator() {
        final FlatGeneratorInfo var0 = new FlatGeneratorInfo();
        var0.setBiome(BiomeGenBase.plains.biomeID);
        var0.getFlatLayers().add(new FlatLayerInfo(1, Block.bedrock.blockID));
        var0.getFlatLayers().add(new FlatLayerInfo(2, Block.dirt.blockID));
        var0.getFlatLayers().add(new FlatLayerInfo(1, Block.grass.blockID));
        var0.func_82645_d();
        var0.getWorldFeatures().put("village", new HashMap());
        return var0;
    }
}

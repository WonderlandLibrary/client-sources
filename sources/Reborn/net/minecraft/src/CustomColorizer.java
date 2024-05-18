package net.minecraft.src;

import java.awt.image.*;
import java.io.*;
import java.util.*;

public class CustomColorizer
{
    private static int[] grassColors;
    private static int[] waterColors;
    private static int[] foliageColors;
    private static int[] foliagePineColors;
    private static int[] foliageBirchColors;
    private static int[] swampFoliageColors;
    private static int[] swampGrassColors;
    private static int[][] blockPalettes;
    private static int[][] paletteColors;
    private static int[] skyColors;
    private static int[] fogColors;
    private static int[] underwaterColors;
    private static float[][][] lightMapsColorsRgb;
    private static int[] lightMapsHeight;
    private static float[][] sunRgbs;
    private static float[][] torchRgbs;
    private static int[] redstoneColors;
    private static int[] stemColors;
    private static int[] myceliumParticleColors;
    private static boolean useDefaultColorMultiplier;
    private static int particleWaterColor;
    private static int particlePortalColor;
    private static int lilyPadColor;
    private static Vec3 fogColorNether;
    private static Vec3 fogColorEnd;
    private static Vec3 skyColorEnd;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    private static Random random;
    
    static {
        CustomColorizer.grassColors = null;
        CustomColorizer.waterColors = null;
        CustomColorizer.foliageColors = null;
        CustomColorizer.foliagePineColors = null;
        CustomColorizer.foliageBirchColors = null;
        CustomColorizer.swampFoliageColors = null;
        CustomColorizer.swampGrassColors = null;
        CustomColorizer.blockPalettes = null;
        CustomColorizer.paletteColors = null;
        CustomColorizer.skyColors = null;
        CustomColorizer.fogColors = null;
        CustomColorizer.underwaterColors = null;
        CustomColorizer.lightMapsColorsRgb = null;
        CustomColorizer.lightMapsHeight = null;
        CustomColorizer.sunRgbs = new float[16][3];
        CustomColorizer.torchRgbs = new float[16][3];
        CustomColorizer.redstoneColors = null;
        CustomColorizer.stemColors = null;
        CustomColorizer.myceliumParticleColors = null;
        CustomColorizer.useDefaultColorMultiplier = true;
        CustomColorizer.particleWaterColor = -1;
        CustomColorizer.particlePortalColor = -1;
        CustomColorizer.lilyPadColor = -1;
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.random = new Random();
    }
    
    public static void update(final RenderEngine var0) {
        CustomColorizer.grassColors = null;
        CustomColorizer.waterColors = null;
        CustomColorizer.foliageColors = null;
        CustomColorizer.foliageBirchColors = null;
        CustomColorizer.foliagePineColors = null;
        CustomColorizer.swampGrassColors = null;
        CustomColorizer.swampFoliageColors = null;
        CustomColorizer.skyColors = null;
        CustomColorizer.fogColors = null;
        CustomColorizer.underwaterColors = null;
        CustomColorizer.redstoneColors = null;
        CustomColorizer.stemColors = null;
        CustomColorizer.myceliumParticleColors = null;
        CustomColorizer.lightMapsColorsRgb = null;
        CustomColorizer.lightMapsHeight = null;
        CustomColorizer.lilyPadColor = -1;
        CustomColorizer.particleWaterColor = -1;
        CustomColorizer.particlePortalColor = -1;
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.blockPalettes = null;
        CustomColorizer.paletteColors = null;
        CustomColorizer.useDefaultColorMultiplier = true;
        CustomColorizer.grassColors = getCustomColors("/misc/grasscolor.png", var0, 65536);
        CustomColorizer.foliageColors = getCustomColors("/misc/foliagecolor.png", var0, 65536);
        CustomColorizer.waterColors = getCustomColors("/misc/watercolorX.png", var0, 65536);
        if (Config.isCustomColors()) {
            CustomColorizer.foliagePineColors = getCustomColors("/misc/pinecolor.png", var0, 65536);
            CustomColorizer.foliageBirchColors = getCustomColors("/misc/birchcolor.png", var0, 65536);
            CustomColorizer.swampGrassColors = getCustomColors("/misc/swampgrasscolor.png", var0, 65536);
            CustomColorizer.swampFoliageColors = getCustomColors("/misc/swampfoliagecolor.png", var0, 65536);
            CustomColorizer.skyColors = getCustomColors("/misc/skycolor0.png", var0, 65536);
            CustomColorizer.fogColors = getCustomColors("/misc/fogcolor0.png", var0, 65536);
            CustomColorizer.underwaterColors = getCustomColors("/misc/underwatercolor.png", var0, 65536);
            CustomColorizer.redstoneColors = getCustomColors("/misc/redstonecolor.png", var0, 16);
            CustomColorizer.stemColors = getCustomColors("/misc/stemcolor.png", var0, 8);
            CustomColorizer.myceliumParticleColors = getCustomColors("/misc/myceliumparticlecolor.png", var0, -1);
            final int[][] var = new int[3][];
            CustomColorizer.lightMapsColorsRgb = new float[3][][];
            CustomColorizer.lightMapsHeight = new int[3];
            for (int var2 = 0; var2 < var.length; ++var2) {
                final String var3 = "/environment/lightmap" + (var2 - 1) + ".png";
                var[var2] = getCustomColors(var3, var0, -1);
                if (var[var2] != null) {
                    CustomColorizer.lightMapsColorsRgb[var2] = toRgb(var[var2]);
                }
                CustomColorizer.lightMapsHeight[var2] = getTextureHeight(var0, var3, 32);
            }
            readColorProperties("/color.properties", var0);
            updateUseDefaultColorMultiplier();
        }
    }
    
    private static int getTextureHeight(final RenderEngine var0, final String var1, final int var2) {
        try {
            final BufferedImage var3 = var0.readTextureImage(var1);
            return (var3 == null) ? var2 : var3.getHeight();
        }
        catch (IOException var4) {
            return var2;
        }
    }
    
    private static float[][] toRgb(final int[] var0) {
        final float[][] var = new float[var0.length][3];
        for (int var2 = 0; var2 < var0.length; ++var2) {
            final int var3 = var0[var2];
            final float var4 = (var3 >> 16 & 0xFF) / 255.0f;
            final float var5 = (var3 >> 8 & 0xFF) / 255.0f;
            final float var6 = (var3 & 0xFF) / 255.0f;
            final float[] var7 = var[var2];
            var7[0] = var4;
            var7[1] = var5;
            var7[2] = var6;
        }
        return var;
    }
    
    private static void readColorProperties(final String var0, final RenderEngine var1) {
        try {
            final InputStream var2 = var1.getTexturePack().getSelectedTexturePack().getResourceAsStream(var0);
            if (var2 == null) {
                return;
            }
            Config.log("Loading " + var0);
            final Properties var3 = new Properties();
            var3.load(var2);
            CustomColorizer.lilyPadColor = readColor(var3, "lilypad");
            CustomColorizer.particleWaterColor = readColor(var3, new String[] { "particle.water", "drop.water" });
            CustomColorizer.particlePortalColor = readColor(var3, "particle.portal");
            CustomColorizer.fogColorNether = readColorVec3(var3, "fog.nether");
            CustomColorizer.fogColorEnd = readColorVec3(var3, "fog.end");
            CustomColorizer.skyColorEnd = readColorVec3(var3, "sky.end");
            readCustomPalettes(var3, var1);
        }
        catch (FileNotFoundException var5) {}
        catch (IOException var4) {
            var4.printStackTrace();
        }
    }
    
    private static void readCustomPalettes(final Properties var0, final RenderEngine var1) {
        CustomColorizer.blockPalettes = new int[256][1];
        for (int var2 = 0; var2 < 256; ++var2) {
            CustomColorizer.blockPalettes[var2][0] = -1;
        }
        final String var3 = "palette.block.";
        final HashMap var4 = new HashMap();
        final Set var5 = var0.keySet();
        for (final String var7 : var5) {
            final String var8 = var0.getProperty(var7);
            if (var7.startsWith(var3)) {
                var4.put(var7, var8);
            }
        }
        final String[] var9 = (String[])var4.keySet().toArray(new String[var4.size()]);
        CustomColorizer.paletteColors = new int[var9.length][];
        for (int var10 = 0; var10 < var9.length; ++var10) {
            final String var8 = var9[var10];
            final String var11 = var0.getProperty(var8);
            Config.log("Block palette: " + var8 + " = " + var11);
            final String var12 = var8.substring(var3.length());
            final int[] var13 = getCustomColors(var12, var1, 65536);
            CustomColorizer.paletteColors[var10] = var13;
            final String[] var14 = Config.tokenize(var11, " ,;");
            for (int var15 = 0; var15 < var14.length; ++var15) {
                String var16 = var14[var15];
                int var17 = -1;
                if (var16.contains(":")) {
                    final String[] var18 = Config.tokenize(var16, ":");
                    var16 = var18[0];
                    final String var19 = var18[1];
                    var17 = Config.parseInt(var19, -1);
                    if (var17 < 0 || var17 > 15) {
                        Config.log("Invalid block metadata: " + var16 + " in palette: " + var8);
                        continue;
                    }
                }
                final int var20 = Config.parseInt(var16, -1);
                if (var20 >= 0 && var20 <= 255) {
                    if (var20 != Block.grass.blockID && var20 != Block.tallGrass.blockID && var20 != Block.leaves.blockID && var20 != Block.vine.blockID) {
                        if (var17 == -1) {
                            CustomColorizer.blockPalettes[var20][0] = var10;
                        }
                        else {
                            if (CustomColorizer.blockPalettes[var20].length < 16) {
                                Arrays.fill(CustomColorizer.blockPalettes[var20] = new int[16], -1);
                            }
                            CustomColorizer.blockPalettes[var20][var17] = var10;
                        }
                    }
                }
                else {
                    Config.log("Invalid block index: " + var20 + " in palette: " + var8);
                }
            }
        }
    }
    
    private static int readColor(final Properties var0, final String[] var1) {
        for (int var2 = 0; var2 < var1.length; ++var2) {
            final String var3 = var1[var2];
            final int var4 = readColor(var0, var3);
            if (var4 >= 0) {
                return var4;
            }
        }
        return -1;
    }
    
    private static int readColor(final Properties var0, final String var1) {
        final String var2 = var0.getProperty(var1);
        if (var2 == null) {
            return -1;
        }
        try {
            final int var3 = Integer.parseInt(var2, 16) & 0xFFFFFF;
            Config.log("Custom color: " + var1 + " = " + var2);
            return var3;
        }
        catch (NumberFormatException var4) {
            Config.log("Invalid custom color: " + var1 + " = " + var2);
            return -1;
        }
    }
    
    private static Vec3 readColorVec3(final Properties var0, final String var1) {
        final int var2 = readColor(var0, var1);
        if (var2 < 0) {
            return null;
        }
        final int var3 = var2 >> 16 & 0xFF;
        final int var4 = var2 >> 8 & 0xFF;
        final int var5 = var2 & 0xFF;
        final float var6 = var3 / 255.0f;
        final float var7 = var4 / 255.0f;
        final float var8 = var5 / 255.0f;
        return Vec3.createVectorHelper(var6, var7, var8);
    }
    
    private static int[] getCustomColors(final String var0, final RenderEngine var1, final int var2) {
        try {
            final InputStream var3 = var1.getTexturePack().getSelectedTexturePack().getResourceAsStream(var0);
            if (var3 == null) {
                return null;
            }
            final int[] var4 = var1.getTextureContents(var0);
            if (var4 == null) {
                return null;
            }
            if (var2 > 0 && var4.length != var2) {
                Config.log("Invalid custom colors length: " + var4.length + ", path: " + var0);
                return null;
            }
            Config.log("Loading custom colors: " + var0);
            return var4;
        }
        catch (FileNotFoundException var6) {
            return null;
        }
        catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }
    
    public static void updateUseDefaultColorMultiplier() {
        CustomColorizer.useDefaultColorMultiplier = (CustomColorizer.foliageBirchColors == null && CustomColorizer.foliagePineColors == null && CustomColorizer.swampGrassColors == null && CustomColorizer.swampFoliageColors == null && CustomColorizer.blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes());
    }
    
    public static int getColorMultiplier(final Block var0, final IBlockAccess var1, final int var2, final int var3, final int var4) {
        if (CustomColorizer.useDefaultColorMultiplier) {
            return var0.colorMultiplier(var1, var2, var3, var4);
        }
        int[] var5 = null;
        int[] var6 = null;
        if (CustomColorizer.blockPalettes != null) {
            final int var7 = var0.blockID;
            if (var7 >= 0 && var7 < 256) {
                final int[] var8 = CustomColorizer.blockPalettes[var7];
                final boolean var9 = true;
                int var11;
                if (var8.length > 1) {
                    final int var10 = var1.getBlockMetadata(var2, var3, var4);
                    var11 = var8[var10];
                }
                else {
                    var11 = var8[0];
                }
                if (var11 >= 0) {
                    var5 = CustomColorizer.paletteColors[var11];
                }
            }
            if (var5 != null) {
                if (Config.isSmoothBiomes()) {
                    return getSmoothColorMultiplier(var0, var1, var2, var3, var4, var5, var5, 0, 0);
                }
                return getCustomColor(var5, var1, var2, var3, var4);
            }
        }
        final boolean var12 = Config.isSwampColors();
        boolean var13 = false;
        byte var14 = 0;
        int var10 = 0;
        if (var0 != Block.grass && var0 != Block.tallGrass) {
            if (var0 == Block.leaves) {
                var14 = 2;
                var13 = Config.isSmoothBiomes();
                var10 = var1.getBlockMetadata(var2, var3, var4);
                if ((var10 & 0x3) == 0x1) {
                    var5 = CustomColorizer.foliagePineColors;
                }
                else if ((var10 & 0x3) == 0x2) {
                    var5 = CustomColorizer.foliageBirchColors;
                }
                else {
                    var5 = CustomColorizer.foliageColors;
                    if (var12) {
                        var6 = CustomColorizer.swampFoliageColors;
                    }
                    else {
                        var6 = var5;
                    }
                }
            }
            else if (var0 == Block.vine) {
                var14 = 2;
                var13 = Config.isSmoothBiomes();
                var5 = CustomColorizer.foliageColors;
                if (var12) {
                    var6 = CustomColorizer.swampFoliageColors;
                }
                else {
                    var6 = var5;
                }
            }
        }
        else {
            var14 = 1;
            var13 = Config.isSmoothBiomes();
            var5 = CustomColorizer.grassColors;
            if (var12) {
                var6 = CustomColorizer.swampGrassColors;
            }
            else {
                var6 = var5;
            }
        }
        if (var13) {
            return getSmoothColorMultiplier(var0, var1, var2, var3, var4, var5, var6, var14, var10);
        }
        if (var6 != var5 && var1.getBiomeGenForCoords(var2, var4) == BiomeGenBase.swampland) {
            var5 = var6;
        }
        return (var5 != null) ? getCustomColor(var5, var1, var2, var3, var4) : var0.colorMultiplier(var1, var2, var3, var4);
    }
    
    private static int getSmoothColorMultiplier(final Block var0, final IBlockAccess var1, final int var2, final int var3, final int var4, final int[] var5, final int[] var6, final int var7, final int var8) {
        int var9 = 0;
        int var10 = 0;
        int var11 = 0;
        for (int var12 = var2 - 1; var12 <= var2 + 1; ++var12) {
            for (int var13 = var4 - 1; var13 <= var4 + 1; ++var13) {
                int[] var14 = var5;
                if (var6 != var5 && var1.getBiomeGenForCoords(var12, var13) == BiomeGenBase.swampland) {
                    var14 = var6;
                }
                final boolean var15 = false;
                int var16 = 0;
                if (var14 == null) {
                    switch (var7) {
                        case 1: {
                            var16 = var1.getBiomeGenForCoords(var12, var13).getBiomeGrassColor();
                            break;
                        }
                        case 2: {
                            if ((var8 & 0x3) == 0x1) {
                                var16 = ColorizerFoliage.getFoliageColorPine();
                                break;
                            }
                            if ((var8 & 0x3) == 0x2) {
                                var16 = ColorizerFoliage.getFoliageColorBirch();
                                break;
                            }
                            var16 = var1.getBiomeGenForCoords(var12, var13).getBiomeFoliageColor();
                            break;
                        }
                        default: {
                            var16 = var0.colorMultiplier(var1, var12, var3, var13);
                            break;
                        }
                    }
                }
                else {
                    var16 = getCustomColor(var14, var1, var12, var3, var13);
                }
                var9 += (var16 >> 16 & 0xFF);
                var10 += (var16 >> 8 & 0xFF);
                var11 += (var16 & 0xFF);
            }
        }
        int var12 = var9 / 9;
        int var13 = var10 / 9;
        final int var17 = var11 / 9;
        return var12 << 16 | var13 << 8 | var17;
    }
    
    public static int getFluidColor(final Block var0, final IBlockAccess var1, final int var2, final int var3, final int var4) {
        return (var0.blockMaterial != Material.water) ? var0.colorMultiplier(var1, var2, var3, var4) : ((CustomColorizer.waterColors != null) ? (Config.isSmoothBiomes() ? getSmoothColor(CustomColorizer.waterColors, var1, var2, var3, var4, 3, 1) : getCustomColor(CustomColorizer.waterColors, var1, var2, var3, var4)) : (Config.isSwampColors() ? var0.colorMultiplier(var1, var2, var3, var4) : 16777215));
    }
    
    private static int getCustomColor(final int[] var0, final IBlockAccess var1, final int var2, final int var3, final int var4) {
        final BiomeGenBase var5 = var1.getBiomeGenForCoords(var2, var4);
        final double var6 = MathHelper.clamp_float(var5.getFloatTemperature(), 0.0f, 1.0f);
        double var7 = MathHelper.clamp_float(var5.getFloatRainfall(), 0.0f, 1.0f);
        var7 *= var6;
        final int var8 = (int)((1.0 - var6) * 255.0);
        final int var9 = (int)((1.0 - var7) * 255.0);
        return var0[var9 << 8 | var8] & 0xFFFFFF;
    }
    
    public static void updatePortalFX(final EntityFX var0) {
        if (CustomColorizer.particlePortalColor >= 0) {
            final int var = CustomColorizer.particlePortalColor;
            final int var2 = var >> 16 & 0xFF;
            final int var3 = var >> 8 & 0xFF;
            final int var4 = var & 0xFF;
            final float var5 = var2 / 255.0f;
            final float var6 = var3 / 255.0f;
            final float var7 = var4 / 255.0f;
            var0.particleRed = var5;
            var0.particleGreen = var6;
            var0.particleBlue = var7;
        }
    }
    
    public static void updateMyceliumFX(final EntityFX var0) {
        if (CustomColorizer.myceliumParticleColors != null) {
            final int var = CustomColorizer.myceliumParticleColors[CustomColorizer.random.nextInt(CustomColorizer.myceliumParticleColors.length)];
            final int var2 = var >> 16 & 0xFF;
            final int var3 = var >> 8 & 0xFF;
            final int var4 = var & 0xFF;
            final float var5 = var2 / 255.0f;
            final float var6 = var3 / 255.0f;
            final float var7 = var4 / 255.0f;
            var0.particleRed = var5;
            var0.particleGreen = var6;
            var0.particleBlue = var7;
        }
    }
    
    public static void updateReddustFX(final EntityFX var0, final IBlockAccess var1, final double var2, final double var4, final double var6) {
        if (CustomColorizer.redstoneColors != null) {
            final int var7 = var1.getBlockMetadata((int)var2, (int)var4, (int)var6);
            final int var8 = getRedstoneColor(var7);
            if (var8 != -1) {
                final int var9 = var8 >> 16 & 0xFF;
                final int var10 = var8 >> 8 & 0xFF;
                final int var11 = var8 & 0xFF;
                final float var12 = var9 / 255.0f;
                final float var13 = var10 / 255.0f;
                final float var14 = var11 / 255.0f;
                var0.particleRed = var12;
                var0.particleGreen = var13;
                var0.particleBlue = var14;
            }
        }
    }
    
    public static int getRedstoneColor(final int var0) {
        return (CustomColorizer.redstoneColors == null) ? -1 : ((var0 >= 0 && var0 <= 15) ? (CustomColorizer.redstoneColors[var0] & 0xFFFFFF) : -1);
    }
    
    public static void updateWaterFX(final EntityFX var0, final IBlockAccess var1) {
        if (CustomColorizer.waterColors != null) {
            final int var2 = (int)var0.posX;
            final int var3 = (int)var0.posY;
            final int var4 = (int)var0.posZ;
            final int var5 = getFluidColor(Block.waterStill, var1, var2, var3, var4);
            final int var6 = var5 >> 16 & 0xFF;
            final int var7 = var5 >> 8 & 0xFF;
            final int var8 = var5 & 0xFF;
            float var9 = var6 / 255.0f;
            float var10 = var7 / 255.0f;
            float var11 = var8 / 255.0f;
            if (CustomColorizer.particleWaterColor >= 0) {
                final int var12 = CustomColorizer.particleWaterColor >> 16 & 0xFF;
                final int var13 = CustomColorizer.particleWaterColor >> 8 & 0xFF;
                final int var14 = CustomColorizer.particleWaterColor & 0xFF;
                var9 *= var12 / 255.0f;
                var10 *= var13 / 255.0f;
                var11 *= var14 / 255.0f;
            }
            var0.particleRed = var9;
            var0.particleGreen = var10;
            var0.particleBlue = var11;
        }
    }
    
    public static int getLilypadColor() {
        return (CustomColorizer.lilyPadColor < 0) ? Block.waterlily.getBlockColor() : CustomColorizer.lilyPadColor;
    }
    
    public static Vec3 getFogColorNether(final Vec3 var0) {
        return (CustomColorizer.fogColorNether == null) ? var0 : CustomColorizer.fogColorNether;
    }
    
    public static Vec3 getFogColorEnd(final Vec3 var0) {
        return (CustomColorizer.fogColorEnd == null) ? var0 : CustomColorizer.fogColorEnd;
    }
    
    public static Vec3 getSkyColorEnd(final Vec3 var0) {
        return (CustomColorizer.skyColorEnd == null) ? var0 : CustomColorizer.skyColorEnd;
    }
    
    public static Vec3 getSkyColor(final Vec3 var0, final IBlockAccess var1, final double var2, final double var4, final double var6) {
        if (CustomColorizer.skyColors == null) {
            return var0;
        }
        final int var7 = getSmoothColor(CustomColorizer.skyColors, var1, var2, var4, var6, 10, 1);
        final int var8 = var7 >> 16 & 0xFF;
        final int var9 = var7 >> 8 & 0xFF;
        final int var10 = var7 & 0xFF;
        float var11 = var8 / 255.0f;
        float var12 = var9 / 255.0f;
        float var13 = var10 / 255.0f;
        final float var14 = (float)var0.xCoord / 0.5f;
        final float var15 = (float)var0.yCoord / 0.66275f;
        final float var16 = (float)var0.zCoord;
        var11 *= var14;
        var12 *= var15;
        var13 *= var16;
        return Vec3.createVectorHelper(var11, var12, var13);
    }
    
    public static Vec3 getFogColor(final Vec3 var0, final IBlockAccess var1, final double var2, final double var4, final double var6) {
        if (CustomColorizer.fogColors == null) {
            return var0;
        }
        final int var7 = getSmoothColor(CustomColorizer.fogColors, var1, var2, var4, var6, 10, 1);
        final int var8 = var7 >> 16 & 0xFF;
        final int var9 = var7 >> 8 & 0xFF;
        final int var10 = var7 & 0xFF;
        float var11 = var8 / 255.0f;
        float var12 = var9 / 255.0f;
        float var13 = var10 / 255.0f;
        final float var14 = (float)var0.xCoord / 0.753f;
        final float var15 = (float)var0.yCoord / 0.8471f;
        final float var16 = (float)var0.zCoord;
        var11 *= var14;
        var12 *= var15;
        var13 *= var16;
        return Vec3.createVectorHelper(var11, var12, var13);
    }
    
    public static Vec3 getUnderwaterColor(final IBlockAccess var0, final double var1, final double var3, final double var5) {
        if (CustomColorizer.underwaterColors == null) {
            return null;
        }
        final int var6 = getSmoothColor(CustomColorizer.underwaterColors, var0, var1, var3, var5, 10, 1);
        final int var7 = var6 >> 16 & 0xFF;
        final int var8 = var6 >> 8 & 0xFF;
        final int var9 = var6 & 0xFF;
        final float var10 = var7 / 255.0f;
        final float var11 = var8 / 255.0f;
        final float var12 = var9 / 255.0f;
        return Vec3.createVectorHelper(var10, var11, var12);
    }
    
    public static int getSmoothColor(final int[] var0, final IBlockAccess var1, final double var2, final double var4, final double var6, final int var8, final int var9) {
        if (var0 == null) {
            return -1;
        }
        final int var10 = (int)Math.floor(var2);
        final int var11 = (int)Math.floor(var4);
        final int var12 = (int)Math.floor(var6);
        final int var13 = var8 * var9 / 2;
        int var14 = 0;
        int var15 = 0;
        int var16 = 0;
        int var17 = 0;
        for (int var18 = var10 - var13; var18 <= var10 + var13; var18 += var9) {
            for (int var19 = var12 - var13; var19 <= var12 + var13; var19 += var9) {
                final int var20 = getCustomColor(var0, var1, var18, var11, var19);
                var14 += (var20 >> 16 & 0xFF);
                var15 += (var20 >> 8 & 0xFF);
                var16 += (var20 & 0xFF);
                ++var17;
            }
        }
        int var18 = var14 / var17;
        int var19 = var15 / var17;
        final int var20 = var16 / var17;
        return var18 << 16 | var19 << 8 | var20;
    }
    
    public static int mixColors(final int var0, final int var1, final float var2) {
        if (var2 <= 0.0f) {
            return var1;
        }
        if (var2 >= 1.0f) {
            return var0;
        }
        final float var3 = 1.0f - var2;
        final int var4 = var0 >> 16 & 0xFF;
        final int var5 = var0 >> 8 & 0xFF;
        final int var6 = var0 & 0xFF;
        final int var7 = var1 >> 16 & 0xFF;
        final int var8 = var1 >> 8 & 0xFF;
        final int var9 = var1 & 0xFF;
        final int var10 = (int)(var4 * var2 + var7 * var3);
        final int var11 = (int)(var5 * var2 + var8 * var3);
        final int var12 = (int)(var6 * var2 + var9 * var3);
        return var10 << 16 | var11 << 8 | var12;
    }
    
    private static int averageColor(final int var0, final int var1) {
        final int var2 = var0 >> 16 & 0xFF;
        final int var3 = var0 >> 8 & 0xFF;
        final int var4 = var0 & 0xFF;
        final int var5 = var1 >> 16 & 0xFF;
        final int var6 = var1 >> 8 & 0xFF;
        final int var7 = var1 & 0xFF;
        final int var8 = (var2 + var5) / 2;
        final int var9 = (var3 + var6) / 2;
        final int var10 = (var4 + var7) / 2;
        return var8 << 16 | var9 << 8 | var10;
    }
    
    public static int getStemColorMultiplier(final BlockStem var0, final IBlockAccess var1, final int var2, final int var3, final int var4) {
        if (CustomColorizer.stemColors == null) {
            return var0.colorMultiplier(var1, var2, var3, var4);
        }
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        if (var5 < 0) {
            var5 = 0;
        }
        if (var5 >= CustomColorizer.stemColors.length) {
            var5 = CustomColorizer.stemColors.length - 1;
        }
        return CustomColorizer.stemColors[var5];
    }
    
    public static boolean updateLightmap(final World var0, final EntityRenderer var1, final int[] var2, final boolean var3) {
        if (var0 == null) {
            return false;
        }
        if (CustomColorizer.lightMapsColorsRgb == null) {
            return false;
        }
        if (!Config.isCustomColors()) {
            return false;
        }
        final int var4 = var0.provider.dimensionId;
        if (var4 < -1 || var4 > 1) {
            return false;
        }
        final int var5 = var4 + 1;
        final float[][] var6 = CustomColorizer.lightMapsColorsRgb[var5];
        if (var6 == null) {
            return false;
        }
        final int var7 = CustomColorizer.lightMapsHeight[var5];
        if (var3 && var7 < 64) {
            return false;
        }
        final int var8 = var6.length / var7;
        if (var8 < 16) {
            Config.dbg("Invalid lightmap width: " + var8 + " for: /environment/lightmap" + var4 + ".png");
            CustomColorizer.lightMapsColorsRgb[var5] = null;
            return false;
        }
        int var9 = 0;
        if (var3) {
            var9 = var8 * 16 * 2;
        }
        float var10 = 1.1666666f * (var0.getSunBrightness(1.0f) - 0.2f);
        if (var0.lastLightningBolt > 0) {
            var10 = 1.0f;
        }
        var10 = Config.limitTo1(var10);
        final float var11 = var10 * (var8 - 1);
        final float var12 = Config.limitTo1(var1.torchFlickerX + 0.5f) * (var8 - 1);
        final float var13 = Config.limitTo1(Config.getGameSettings().gammaSetting);
        final boolean var14 = var13 > 1.0E-4f;
        getLightMapColumn(var6, var11, var9, var8, CustomColorizer.sunRgbs);
        getLightMapColumn(var6, var12, var9 + 16 * var8, var8, CustomColorizer.torchRgbs);
        final float[] var15 = new float[3];
        for (int var16 = 0; var16 < 16; ++var16) {
            for (int var17 = 0; var17 < 16; ++var17) {
                for (int var18 = 0; var18 < 3; ++var18) {
                    float var19 = Config.limitTo1(CustomColorizer.sunRgbs[var16][var18] + CustomColorizer.torchRgbs[var17][var18]);
                    if (var14) {
                        float var20 = 1.0f - var19;
                        var20 = 1.0f - var20 * var20 * var20 * var20;
                        var19 = var13 * var20 + (1.0f - var13) * var19;
                    }
                    var15[var18] = var19;
                }
                int var18 = (int)(var15[0] * 255.0f);
                final int var21 = (int)(var15[1] * 255.0f);
                final int var22 = (int)(var15[2] * 255.0f);
                var2[var16 * 16 + var17] = (0xFF000000 | var18 << 16 | var21 << 8 | var22);
            }
        }
        return true;
    }
    
    private static void getLightMapColumn(final float[][] var0, final float var1, final int var2, final int var3, final float[][] var4) {
        final int var5 = (int)Math.floor(var1);
        final int var6 = (int)Math.ceil(var1);
        if (var5 == var6) {
            for (int var7 = 0; var7 < 16; ++var7) {
                final float[] var8 = var0[var2 + var7 * var3 + var5];
                final float[] var9 = var4[var7];
                for (int var10 = 0; var10 < 3; ++var10) {
                    var9[var10] = var8[var10];
                }
            }
        }
        else {
            final float var11 = 1.0f - (var1 - var5);
            final float var12 = 1.0f - (var6 - var1);
            for (int var13 = 0; var13 < 16; ++var13) {
                final float[] var14 = var0[var2 + var13 * var3 + var5];
                final float[] var15 = var0[var2 + var13 * var3 + var6];
                final float[] var16 = var4[var13];
                for (int var17 = 0; var17 < 3; ++var17) {
                    var16[var17] = var14[var17] * var11 + var15[var17] * var12;
                }
            }
        }
    }
}

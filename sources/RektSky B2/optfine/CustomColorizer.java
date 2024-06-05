package optfine;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.client.particle.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
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
    
    public static void update() {
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
        final String s = "mcpatcher/colormap/";
        CustomColorizer.grassColors = getCustomColors("textures/colormap/grass.png", 65536);
        CustomColorizer.foliageColors = getCustomColors("textures/colormap/foliage.png", 65536);
        final String[] astring = { "water.png", "watercolorX.png" };
        CustomColorizer.waterColors = getCustomColors(s, astring, 65536);
        if (Config.isCustomColors()) {
            final String[] astring2 = { "pine.png", "pinecolor.png" };
            CustomColorizer.foliagePineColors = getCustomColors(s, astring2, 65536);
            final String[] astring3 = { "birch.png", "birchcolor.png" };
            CustomColorizer.foliageBirchColors = getCustomColors(s, astring3, 65536);
            final String[] astring4 = { "swampgrass.png", "swampgrasscolor.png" };
            CustomColorizer.swampGrassColors = getCustomColors(s, astring4, 65536);
            final String[] astring5 = { "swampfoliage.png", "swampfoliagecolor.png" };
            CustomColorizer.swampFoliageColors = getCustomColors(s, astring5, 65536);
            final String[] astring6 = { "sky0.png", "skycolor0.png" };
            CustomColorizer.skyColors = getCustomColors(s, astring6, 65536);
            final String[] astring7 = { "fog0.png", "fogcolor0.png" };
            CustomColorizer.fogColors = getCustomColors(s, astring7, 65536);
            final String[] astring8 = { "underwater.png", "underwatercolor.png" };
            CustomColorizer.underwaterColors = getCustomColors(s, astring8, 65536);
            final String[] astring9 = { "redstone.png", "redstonecolor.png" };
            CustomColorizer.redstoneColors = getCustomColors(s, astring9, 16);
            final String[] astring10 = { "stem.png", "stemcolor.png" };
            CustomColorizer.stemColors = getCustomColors(s, astring10, 8);
            final String[] astring11 = { "myceliumparticle.png", "myceliumparticlecolor.png" };
            CustomColorizer.myceliumParticleColors = getCustomColors(s, astring11, -1);
            final int[][] aint = new int[3][];
            CustomColorizer.lightMapsColorsRgb = new float[3][][];
            CustomColorizer.lightMapsHeight = new int[3];
            for (int i = 0; i < aint.length; ++i) {
                final String s2 = "mcpatcher/lightmap/world" + (i - 1) + ".png";
                aint[i] = getCustomColors(s2, -1);
                if (aint[i] != null) {
                    CustomColorizer.lightMapsColorsRgb[i] = toRgb(aint[i]);
                }
                CustomColorizer.lightMapsHeight[i] = getTextureHeight(s2, 32);
            }
            readColorProperties("mcpatcher/color.properties");
            updateUseDefaultColorMultiplier();
        }
    }
    
    private static int getTextureHeight(final String p_getTextureHeight_0_, final int p_getTextureHeight_1_) {
        try {
            final InputStream inputstream = Config.getResourceStream(new ResourceLocation(p_getTextureHeight_0_));
            if (inputstream == null) {
                return p_getTextureHeight_1_;
            }
            final BufferedImage bufferedimage = ImageIO.read(inputstream);
            return (bufferedimage == null) ? p_getTextureHeight_1_ : bufferedimage.getHeight();
        }
        catch (IOException var4) {
            return p_getTextureHeight_1_;
        }
    }
    
    private static float[][] toRgb(final int[] p_toRgb_0_) {
        final float[][] afloat = new float[p_toRgb_0_.length][3];
        for (int i = 0; i < p_toRgb_0_.length; ++i) {
            final int j = p_toRgb_0_[i];
            final float f = (j >> 16 & 0xFF) / 255.0f;
            final float f2 = (j >> 8 & 0xFF) / 255.0f;
            final float f3 = (j & 0xFF) / 255.0f;
            final float[] afloat2 = afloat[i];
            afloat2[0] = f;
            afloat2[1] = f2;
            afloat2[2] = f3;
        }
        return afloat;
    }
    
    private static void readColorProperties(final String p_readColorProperties_0_) {
        try {
            final ResourceLocation resourcelocation = new ResourceLocation(p_readColorProperties_0_);
            final InputStream inputstream = Config.getResourceStream(resourcelocation);
            if (inputstream == null) {
                return;
            }
            Config.log("Loading " + p_readColorProperties_0_);
            final Properties properties = new Properties();
            properties.load(inputstream);
            CustomColorizer.lilyPadColor = readColor(properties, "lilypad");
            CustomColorizer.particleWaterColor = readColor(properties, new String[] { "particle.water", "drop.water" });
            CustomColorizer.particlePortalColor = readColor(properties, "particle.portal");
            CustomColorizer.fogColorNether = readColorVec3(properties, "fog.nether");
            CustomColorizer.fogColorEnd = readColorVec3(properties, "fog.end");
            CustomColorizer.skyColorEnd = readColorVec3(properties, "sky.end");
            readCustomPalettes(properties, p_readColorProperties_0_);
        }
        catch (FileNotFoundException var4) {}
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }
    
    private static void readCustomPalettes(final Properties p_readCustomPalettes_0_, final String p_readCustomPalettes_1_) {
        CustomColorizer.blockPalettes = new int[256][1];
        for (int i = 0; i < 256; ++i) {
            CustomColorizer.blockPalettes[i][0] = -1;
        }
        final String s7 = "palette.block.";
        final Map map = new HashMap();
        for (final Object s8 : ((Hashtable<Object, V>)p_readCustomPalettes_0_).keySet()) {
            final String s9 = p_readCustomPalettes_0_.getProperty((String)s8);
            if (((String)s8).startsWith(s7)) {
                map.put(s8, s9);
            }
        }
        final String[] astring2 = (String[])map.keySet().toArray(new String[map.size()]);
        CustomColorizer.paletteColors = new int[astring2.length][];
        for (int l = 0; l < astring2.length; ++l) {
            final String s10 = astring2[l];
            final String s11 = p_readCustomPalettes_0_.getProperty(s10);
            Config.log("Block palette: " + s10 + " = " + s11);
            String s12 = s10.substring(s7.length());
            final String s13 = TextureUtils.getBasePath(p_readCustomPalettes_1_);
            s12 = TextureUtils.fixResourcePath(s12, s13);
            final int[] aint = getCustomColors(s12, 65536);
            CustomColorizer.paletteColors[l] = aint;
            final String[] astring3 = Config.tokenize(s11, " ,;");
            for (int j = 0; j < astring3.length; ++j) {
                String s14 = astring3[j];
                int k = -1;
                if (s14.contains(":")) {
                    final String[] astring4 = Config.tokenize(s14, ":");
                    s14 = astring4[0];
                    final String s15 = astring4[1];
                    k = Config.parseInt(s15, -1);
                    if (k < 0 || k > 15) {
                        Config.log("Invalid block metadata: " + s14 + " in palette: " + s10);
                        continue;
                    }
                }
                final int i2 = Config.parseInt(s14, -1);
                if (i2 >= 0 && i2 <= 255) {
                    if (i2 != Block.getIdFromBlock(Blocks.grass) && i2 != Block.getIdFromBlock(Blocks.tallgrass) && i2 != Block.getIdFromBlock(Blocks.leaves) && i2 != Block.getIdFromBlock(Blocks.vine)) {
                        if (k == -1) {
                            CustomColorizer.blockPalettes[i2][0] = l;
                        }
                        else {
                            if (CustomColorizer.blockPalettes[i2].length < 16) {
                                CustomColorizer.blockPalettes[i2] = new int[16];
                                Arrays.fill(CustomColorizer.blockPalettes[i2], -1);
                            }
                            CustomColorizer.blockPalettes[i2][k] = l;
                        }
                    }
                }
                else {
                    Config.log("Invalid block index: " + i2 + " in palette: " + s10);
                }
            }
        }
    }
    
    private static int readColor(final Properties p_readColor_0_, final String[] p_readColor_1_) {
        for (int i = 0; i < p_readColor_1_.length; ++i) {
            final String s = p_readColor_1_[i];
            final int j = readColor(p_readColor_0_, s);
            if (j >= 0) {
                return j;
            }
        }
        return -1;
    }
    
    private static int readColor(final Properties p_readColor_0_, final String p_readColor_1_) {
        final String s = p_readColor_0_.getProperty(p_readColor_1_);
        if (s == null) {
            return -1;
        }
        try {
            final int i = Integer.parseInt(s, 16) & 0xFFFFFF;
            Config.log("Custom color: " + p_readColor_1_ + " = " + s);
            return i;
        }
        catch (NumberFormatException var4) {
            Config.log("Invalid custom color: " + p_readColor_1_ + " = " + s);
            return -1;
        }
    }
    
    private static Vec3 readColorVec3(final Properties p_readColorVec3_0_, final String p_readColorVec3_1_) {
        final int i = readColor(p_readColorVec3_0_, p_readColorVec3_1_);
        if (i < 0) {
            return null;
        }
        final int j = i >> 16 & 0xFF;
        final int k = i >> 8 & 0xFF;
        final int l = i & 0xFF;
        final float f = j / 255.0f;
        final float f2 = k / 255.0f;
        final float f3 = l / 255.0f;
        return new Vec3(f, f2, f3);
    }
    
    private static int[] getCustomColors(final String p_getCustomColors_0_, final String[] p_getCustomColors_1_, final int p_getCustomColors_2_) {
        for (int i = 0; i < p_getCustomColors_1_.length; ++i) {
            String s = p_getCustomColors_1_[i];
            s = p_getCustomColors_0_ + s;
            final int[] aint = getCustomColors(s, p_getCustomColors_2_);
            if (aint != null) {
                return aint;
            }
        }
        return null;
    }
    
    private static int[] getCustomColors(final String p_getCustomColors_0_, final int p_getCustomColors_1_) {
        try {
            final ResourceLocation resourcelocation = new ResourceLocation(p_getCustomColors_0_);
            final InputStream inputstream = Config.getResourceStream(resourcelocation);
            if (inputstream == null) {
                return null;
            }
            final int[] aint = TextureUtil.readImageData(Config.getResourceManager(), resourcelocation);
            if (aint == null) {
                return null;
            }
            if (p_getCustomColors_1_ > 0 && aint.length != p_getCustomColors_1_) {
                Config.log("Invalid custom colors length: " + aint.length + ", path: " + p_getCustomColors_0_);
                return null;
            }
            Config.log("Loading custom colors: " + p_getCustomColors_0_);
            return aint;
        }
        catch (FileNotFoundException var5) {
            return null;
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            return null;
        }
    }
    
    public static void updateUseDefaultColorMultiplier() {
        CustomColorizer.useDefaultColorMultiplier = (CustomColorizer.foliageBirchColors == null && CustomColorizer.foliagePineColors == null && CustomColorizer.swampGrassColors == null && CustomColorizer.swampFoliageColors == null && CustomColorizer.blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes());
    }
    
    public static int getColorMultiplier(final BakedQuad p_getColorMultiplier_0_, final Block p_getColorMultiplier_1_, final IBlockAccess p_getColorMultiplier_2_, final BlockPos p_getColorMultiplier_3_, final RenderEnv p_getColorMultiplier_4_) {
        if (CustomColorizer.useDefaultColorMultiplier) {
            return -1;
        }
        int[] aint = null;
        int[] aint2 = null;
        if (CustomColorizer.blockPalettes != null) {
            final int i = p_getColorMultiplier_4_.getBlockId();
            if (i >= 0 && i < 256) {
                final int[] aint3 = CustomColorizer.blockPalettes[i];
                int j = -1;
                if (aint3.length > 1) {
                    final int k = p_getColorMultiplier_4_.getMetadata();
                    j = aint3[k];
                }
                else {
                    j = aint3[0];
                }
                if (j >= 0) {
                    aint = CustomColorizer.paletteColors[j];
                }
            }
            if (aint != null) {
                if (Config.isSmoothBiomes()) {
                    return getSmoothColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, aint, aint, 0, 0, p_getColorMultiplier_4_);
                }
                return getCustomColor(aint, p_getColorMultiplier_2_, p_getColorMultiplier_3_);
            }
        }
        if (!p_getColorMultiplier_0_.hasTintIndex()) {
            return -1;
        }
        if (p_getColorMultiplier_1_ == Blocks.waterlily) {
            return getLilypadColorMultiplier(p_getColorMultiplier_2_, p_getColorMultiplier_3_);
        }
        if (p_getColorMultiplier_1_ instanceof BlockStem) {
            return getStemColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, p_getColorMultiplier_4_);
        }
        final boolean flag = Config.isSwampColors();
        boolean flag2 = false;
        int l = 0;
        int i2 = 0;
        if (p_getColorMultiplier_1_ != Blocks.grass && p_getColorMultiplier_1_ != Blocks.tallgrass) {
            if (p_getColorMultiplier_1_ == Blocks.leaves) {
                l = 2;
                flag2 = Config.isSmoothBiomes();
                i2 = p_getColorMultiplier_4_.getMetadata();
                if ((i2 & 0x3) == 0x1) {
                    aint = CustomColorizer.foliagePineColors;
                }
                else if ((i2 & 0x3) == 0x2) {
                    aint = CustomColorizer.foliageBirchColors;
                }
                else {
                    aint = CustomColorizer.foliageColors;
                    if (flag) {
                        aint2 = CustomColorizer.swampFoliageColors;
                    }
                    else {
                        aint2 = aint;
                    }
                }
            }
            else if (p_getColorMultiplier_1_ == Blocks.vine) {
                l = 2;
                flag2 = Config.isSmoothBiomes();
                aint = CustomColorizer.foliageColors;
                if (flag) {
                    aint2 = CustomColorizer.swampFoliageColors;
                }
                else {
                    aint2 = aint;
                }
            }
        }
        else {
            l = 1;
            flag2 = Config.isSmoothBiomes();
            aint = CustomColorizer.grassColors;
            if (flag) {
                aint2 = CustomColorizer.swampGrassColors;
            }
            else {
                aint2 = aint;
            }
        }
        if (flag2) {
            return getSmoothColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, aint, aint2, l, i2, p_getColorMultiplier_4_);
        }
        if (aint2 != aint && p_getColorMultiplier_2_.getBiomeGenForCoords(p_getColorMultiplier_3_) == BiomeGenBase.swampland) {
            aint = aint2;
        }
        return (aint != null) ? getCustomColor(aint, p_getColorMultiplier_2_, p_getColorMultiplier_3_) : -1;
    }
    
    private static int getSmoothColorMultiplier(final Block p_getSmoothColorMultiplier_0_, final IBlockAccess p_getSmoothColorMultiplier_1_, final BlockPos p_getSmoothColorMultiplier_2_, final int[] p_getSmoothColorMultiplier_3_, final int[] p_getSmoothColorMultiplier_4_, final int p_getSmoothColorMultiplier_5_, final int p_getSmoothColorMultiplier_6_, final RenderEnv p_getSmoothColorMultiplier_7_) {
        int i = 0;
        int j = 0;
        int k = 0;
        final int l = p_getSmoothColorMultiplier_2_.getX();
        final int i2 = p_getSmoothColorMultiplier_2_.getY();
        final int j2 = p_getSmoothColorMultiplier_2_.getZ();
        final BlockPosM blockposm = p_getSmoothColorMultiplier_7_.getColorizerBlockPos();
        for (int k2 = l - 1; k2 <= l + 1; ++k2) {
            for (int l2 = j2 - 1; l2 <= j2 + 1; ++l2) {
                blockposm.setXyz(k2, i2, l2);
                int[] aint = p_getSmoothColorMultiplier_3_;
                if (p_getSmoothColorMultiplier_4_ != p_getSmoothColorMultiplier_3_ && p_getSmoothColorMultiplier_1_.getBiomeGenForCoords(blockposm) == BiomeGenBase.swampland) {
                    aint = p_getSmoothColorMultiplier_4_;
                }
                int i3 = 0;
                if (aint == null) {
                    switch (p_getSmoothColorMultiplier_5_) {
                        case 1: {
                            i3 = p_getSmoothColorMultiplier_1_.getBiomeGenForCoords(blockposm).getGrassColorAtPos(blockposm);
                            break;
                        }
                        case 2: {
                            if ((p_getSmoothColorMultiplier_6_ & 0x3) == 0x1) {
                                i3 = ColorizerFoliage.getFoliageColorPine();
                                break;
                            }
                            if ((p_getSmoothColorMultiplier_6_ & 0x3) == 0x2) {
                                i3 = ColorizerFoliage.getFoliageColorBirch();
                                break;
                            }
                            i3 = p_getSmoothColorMultiplier_1_.getBiomeGenForCoords(blockposm).getFoliageColorAtPos(blockposm);
                            break;
                        }
                        default: {
                            i3 = p_getSmoothColorMultiplier_0_.colorMultiplier(p_getSmoothColorMultiplier_1_, blockposm);
                            break;
                        }
                    }
                }
                else {
                    i3 = getCustomColor(aint, p_getSmoothColorMultiplier_1_, blockposm);
                }
                i += (i3 >> 16 & 0xFF);
                j += (i3 >> 8 & 0xFF);
                k += (i3 & 0xFF);
            }
        }
        final int j3 = i / 9;
        final int k3 = j / 9;
        final int l3 = k / 9;
        return j3 << 16 | k3 << 8 | l3;
    }
    
    public static int getFluidColor(final Block p_getFluidColor_0_, final IBlockAccess p_getFluidColor_1_, final BlockPos p_getFluidColor_2_) {
        return (p_getFluidColor_0_.getMaterial() != Material.water) ? p_getFluidColor_0_.colorMultiplier(p_getFluidColor_1_, p_getFluidColor_2_) : ((CustomColorizer.waterColors != null) ? (Config.isSmoothBiomes() ? getSmoothColor(CustomColorizer.waterColors, p_getFluidColor_1_, p_getFluidColor_2_.getX(), p_getFluidColor_2_.getY(), p_getFluidColor_2_.getZ(), 3, 1) : getCustomColor(CustomColorizer.waterColors, p_getFluidColor_1_, p_getFluidColor_2_)) : (Config.isSwampColors() ? p_getFluidColor_0_.colorMultiplier(p_getFluidColor_1_, p_getFluidColor_2_) : 16777215));
    }
    
    private static int getCustomColor(final int[] p_getCustomColor_0_, final IBlockAccess p_getCustomColor_1_, final BlockPos p_getCustomColor_2_) {
        final BiomeGenBase biomegenbase = p_getCustomColor_1_.getBiomeGenForCoords(p_getCustomColor_2_);
        final double d0 = MathHelper.clamp_float(biomegenbase.getFloatTemperature(p_getCustomColor_2_), 0.0f, 1.0f);
        double d2 = MathHelper.clamp_float(biomegenbase.getFloatRainfall(), 0.0f, 1.0f);
        d2 *= d0;
        final int i = (int)((1.0 - d0) * 255.0);
        final int j = (int)((1.0 - d2) * 255.0);
        return p_getCustomColor_0_[j << 8 | i] & 0xFFFFFF;
    }
    
    public static void updatePortalFX(final EntityFX p_updatePortalFX_0_) {
        if (CustomColorizer.particlePortalColor >= 0) {
            final int i = CustomColorizer.particlePortalColor;
            final int j = i >> 16 & 0xFF;
            final int k = i >> 8 & 0xFF;
            final int l = i & 0xFF;
            final float f = j / 255.0f;
            final float f2 = k / 255.0f;
            final float f3 = l / 255.0f;
            p_updatePortalFX_0_.setRBGColorF(f, f2, f3);
        }
    }
    
    public static void updateMyceliumFX(final EntityFX p_updateMyceliumFX_0_) {
        if (CustomColorizer.myceliumParticleColors != null) {
            final int i = CustomColorizer.myceliumParticleColors[CustomColorizer.random.nextInt(CustomColorizer.myceliumParticleColors.length)];
            final int j = i >> 16 & 0xFF;
            final int k = i >> 8 & 0xFF;
            final int l = i & 0xFF;
            final float f = j / 255.0f;
            final float f2 = k / 255.0f;
            final float f3 = l / 255.0f;
            p_updateMyceliumFX_0_.setRBGColorF(f, f2, f3);
        }
    }
    
    public static void updateReddustFX(final EntityFX p_updateReddustFX_0_, final IBlockAccess p_updateReddustFX_1_, final double p_updateReddustFX_2_, final double p_updateReddustFX_4_, final double p_updateReddustFX_6_) {
        if (CustomColorizer.redstoneColors != null) {
            final IBlockState iblockstate = p_updateReddustFX_1_.getBlockState(new BlockPos(p_updateReddustFX_2_, p_updateReddustFX_4_, p_updateReddustFX_6_));
            final int i = getRedstoneLevel(iblockstate, 15);
            final int j = getRedstoneColor(i);
            if (j != -1) {
                final int k = j >> 16 & 0xFF;
                final int l = j >> 8 & 0xFF;
                final int i2 = j & 0xFF;
                final float f = k / 255.0f;
                final float f2 = l / 255.0f;
                final float f3 = i2 / 255.0f;
                p_updateReddustFX_0_.setRBGColorF(f, f2, f3);
            }
        }
    }
    
    private static int getRedstoneLevel(final IBlockState p_getRedstoneLevel_0_, final int p_getRedstoneLevel_1_) {
        final Block block = p_getRedstoneLevel_0_.getBlock();
        if (!(block instanceof BlockRedstoneWire)) {
            return p_getRedstoneLevel_1_;
        }
        final Object object = p_getRedstoneLevel_0_.getValue((IProperty<Object>)BlockRedstoneWire.POWER);
        if (!(object instanceof Integer)) {
            return p_getRedstoneLevel_1_;
        }
        final Integer integer = (Integer)object;
        return integer;
    }
    
    public static int getRedstoneColor(final int p_getRedstoneColor_0_) {
        return (CustomColorizer.redstoneColors == null) ? -1 : ((p_getRedstoneColor_0_ >= 0 && p_getRedstoneColor_0_ <= 15) ? (CustomColorizer.redstoneColors[p_getRedstoneColor_0_] & 0xFFFFFF) : -1);
    }
    
    public static void updateWaterFX(final EntityFX p_updateWaterFX_0_, final IBlockAccess p_updateWaterFX_1_, final double p_updateWaterFX_2_, final double p_updateWaterFX_4_, final double p_updateWaterFX_6_) {
        if (CustomColorizer.waterColors != null) {
            final int i = getFluidColor(Blocks.water, p_updateWaterFX_1_, new BlockPos(p_updateWaterFX_2_, p_updateWaterFX_4_, p_updateWaterFX_6_));
            final int j = i >> 16 & 0xFF;
            final int k = i >> 8 & 0xFF;
            final int l = i & 0xFF;
            float f = j / 255.0f;
            float f2 = k / 255.0f;
            float f3 = l / 255.0f;
            if (CustomColorizer.particleWaterColor >= 0) {
                final int i2 = CustomColorizer.particleWaterColor >> 16 & 0xFF;
                final int j2 = CustomColorizer.particleWaterColor >> 8 & 0xFF;
                final int k2 = CustomColorizer.particleWaterColor & 0xFF;
                f *= i2 / 255.0f;
                f2 *= j2 / 255.0f;
                f3 *= k2 / 255.0f;
            }
            p_updateWaterFX_0_.setRBGColorF(f, f2, f3);
        }
    }
    
    public static int getLilypadColorMultiplier(final IBlockAccess p_getLilypadColorMultiplier_0_, final BlockPos p_getLilypadColorMultiplier_1_) {
        return (CustomColorizer.lilyPadColor < 0) ? Blocks.waterlily.colorMultiplier(p_getLilypadColorMultiplier_0_, p_getLilypadColorMultiplier_1_) : CustomColorizer.lilyPadColor;
    }
    
    public static Vec3 getFogColorNether(final Vec3 p_getFogColorNether_0_) {
        return (CustomColorizer.fogColorNether == null) ? p_getFogColorNether_0_ : CustomColorizer.fogColorNether;
    }
    
    public static Vec3 getFogColorEnd(final Vec3 p_getFogColorEnd_0_) {
        return (CustomColorizer.fogColorEnd == null) ? p_getFogColorEnd_0_ : CustomColorizer.fogColorEnd;
    }
    
    public static Vec3 getSkyColorEnd(final Vec3 p_getSkyColorEnd_0_) {
        return (CustomColorizer.skyColorEnd == null) ? p_getSkyColorEnd_0_ : CustomColorizer.skyColorEnd;
    }
    
    public static Vec3 getSkyColor(final Vec3 p_getSkyColor_0_, final IBlockAccess p_getSkyColor_1_, final double p_getSkyColor_2_, final double p_getSkyColor_4_, final double p_getSkyColor_6_) {
        if (CustomColorizer.skyColors == null) {
            return p_getSkyColor_0_;
        }
        final int i = getSmoothColor(CustomColorizer.skyColors, p_getSkyColor_1_, p_getSkyColor_2_, p_getSkyColor_4_, p_getSkyColor_6_, 7, 1);
        final int j = i >> 16 & 0xFF;
        final int k = i >> 8 & 0xFF;
        final int l = i & 0xFF;
        float f = j / 255.0f;
        float f2 = k / 255.0f;
        float f3 = l / 255.0f;
        final float f4 = (float)p_getSkyColor_0_.xCoord / 0.5f;
        final float f5 = (float)p_getSkyColor_0_.yCoord / 0.66275f;
        final float f6 = (float)p_getSkyColor_0_.zCoord;
        f *= f4;
        f2 *= f5;
        f3 *= f6;
        return new Vec3(f, f2, f3);
    }
    
    public static Vec3 getFogColor(final Vec3 p_getFogColor_0_, final IBlockAccess p_getFogColor_1_, final double p_getFogColor_2_, final double p_getFogColor_4_, final double p_getFogColor_6_) {
        if (CustomColorizer.fogColors == null) {
            return p_getFogColor_0_;
        }
        final int i = getSmoothColor(CustomColorizer.fogColors, p_getFogColor_1_, p_getFogColor_2_, p_getFogColor_4_, p_getFogColor_6_, 7, 1);
        final int j = i >> 16 & 0xFF;
        final int k = i >> 8 & 0xFF;
        final int l = i & 0xFF;
        float f = j / 255.0f;
        float f2 = k / 255.0f;
        float f3 = l / 255.0f;
        final float f4 = (float)p_getFogColor_0_.xCoord / 0.753f;
        final float f5 = (float)p_getFogColor_0_.yCoord / 0.8471f;
        final float f6 = (float)p_getFogColor_0_.zCoord;
        f *= f4;
        f2 *= f5;
        f3 *= f6;
        return new Vec3(f, f2, f3);
    }
    
    public static Vec3 getUnderwaterColor(final IBlockAccess p_getUnderwaterColor_0_, final double p_getUnderwaterColor_1_, final double p_getUnderwaterColor_3_, final double p_getUnderwaterColor_5_) {
        if (CustomColorizer.underwaterColors == null) {
            return null;
        }
        final int i = getSmoothColor(CustomColorizer.underwaterColors, p_getUnderwaterColor_0_, p_getUnderwaterColor_1_, p_getUnderwaterColor_3_, p_getUnderwaterColor_5_, 7, 1);
        final int j = i >> 16 & 0xFF;
        final int k = i >> 8 & 0xFF;
        final int l = i & 0xFF;
        final float f = j / 255.0f;
        final float f2 = k / 255.0f;
        final float f3 = l / 255.0f;
        return new Vec3(f, f2, f3);
    }
    
    public static int getSmoothColor(final int[] p_getSmoothColor_0_, final IBlockAccess p_getSmoothColor_1_, final double p_getSmoothColor_2_, final double p_getSmoothColor_4_, final double p_getSmoothColor_6_, final int p_getSmoothColor_8_, final int p_getSmoothColor_9_) {
        if (p_getSmoothColor_0_ == null) {
            return -1;
        }
        final int i = MathHelper.floor_double(p_getSmoothColor_2_);
        final int j = MathHelper.floor_double(p_getSmoothColor_4_);
        final int k = MathHelper.floor_double(p_getSmoothColor_6_);
        final int l = p_getSmoothColor_8_ * p_getSmoothColor_9_ / 2;
        int i2 = 0;
        int j2 = 0;
        int k2 = 0;
        int l2 = 0;
        final BlockPosM blockposm = new BlockPosM(0, 0, 0);
        for (int i3 = i - l; i3 <= i + l; i3 += p_getSmoothColor_9_) {
            for (int j3 = k - l; j3 <= k + l; j3 += p_getSmoothColor_9_) {
                blockposm.setXyz(i3, j, j3);
                final int k3 = getCustomColor(p_getSmoothColor_0_, p_getSmoothColor_1_, blockposm);
                i2 += (k3 >> 16 & 0xFF);
                j2 += (k3 >> 8 & 0xFF);
                k2 += (k3 & 0xFF);
                ++l2;
            }
        }
        final int l3 = i2 / l2;
        final int i4 = j2 / l2;
        final int j4 = k2 / l2;
        return l3 << 16 | i4 << 8 | j4;
    }
    
    public static int mixColors(final int p_mixColors_0_, final int p_mixColors_1_, final float p_mixColors_2_) {
        if (p_mixColors_2_ <= 0.0f) {
            return p_mixColors_1_;
        }
        if (p_mixColors_2_ >= 1.0f) {
            return p_mixColors_0_;
        }
        final float f = 1.0f - p_mixColors_2_;
        final int i = p_mixColors_0_ >> 16 & 0xFF;
        final int j = p_mixColors_0_ >> 8 & 0xFF;
        final int k = p_mixColors_0_ & 0xFF;
        final int l = p_mixColors_1_ >> 16 & 0xFF;
        final int i2 = p_mixColors_1_ >> 8 & 0xFF;
        final int j2 = p_mixColors_1_ & 0xFF;
        final int k2 = (int)(i * p_mixColors_2_ + l * f);
        final int l2 = (int)(j * p_mixColors_2_ + i2 * f);
        final int i3 = (int)(k * p_mixColors_2_ + j2 * f);
        return k2 << 16 | l2 << 8 | i3;
    }
    
    private static int averageColor(final int p_averageColor_0_, final int p_averageColor_1_) {
        final int i = p_averageColor_0_ >> 16 & 0xFF;
        final int j = p_averageColor_0_ >> 8 & 0xFF;
        final int k = p_averageColor_0_ & 0xFF;
        final int l = p_averageColor_1_ >> 16 & 0xFF;
        final int i2 = p_averageColor_1_ >> 8 & 0xFF;
        final int j2 = p_averageColor_1_ & 0xFF;
        final int k2 = (i + l) / 2;
        final int l2 = (j + i2) / 2;
        final int i3 = (k + j2) / 2;
        return k2 << 16 | l2 << 8 | i3;
    }
    
    public static int getStemColorMultiplier(final Block p_getStemColorMultiplier_0_, final IBlockAccess p_getStemColorMultiplier_1_, final BlockPos p_getStemColorMultiplier_2_, final RenderEnv p_getStemColorMultiplier_3_) {
        if (CustomColorizer.stemColors == null) {
            return p_getStemColorMultiplier_0_.colorMultiplier(p_getStemColorMultiplier_1_, p_getStemColorMultiplier_2_);
        }
        int i = p_getStemColorMultiplier_3_.getMetadata();
        if (i < 0) {
            i = 0;
        }
        if (i >= CustomColorizer.stemColors.length) {
            i = CustomColorizer.stemColors.length - 1;
        }
        return CustomColorizer.stemColors[i];
    }
    
    public static boolean updateLightmap(final World p_updateLightmap_0_, final float p_updateLightmap_1_, final int[] p_updateLightmap_2_, final boolean p_updateLightmap_3_) {
        if (p_updateLightmap_0_ == null) {
            return false;
        }
        if (CustomColorizer.lightMapsColorsRgb == null) {
            return false;
        }
        if (!Config.isCustomColors()) {
            return false;
        }
        final int i = p_updateLightmap_0_.provider.getDimensionId();
        if (i < -1 || i > 1) {
            return false;
        }
        final int j = i + 1;
        final float[][] afloat = CustomColorizer.lightMapsColorsRgb[j];
        if (afloat == null) {
            return false;
        }
        final int k = CustomColorizer.lightMapsHeight[j];
        if (p_updateLightmap_3_ && k < 64) {
            return false;
        }
        final int l = afloat.length / k;
        if (l < 16) {
            Config.warn("Invalid lightmap width: " + l + " for: /environment/lightmap" + i + ".png");
            CustomColorizer.lightMapsColorsRgb[j] = null;
            return false;
        }
        int i2 = 0;
        if (p_updateLightmap_3_) {
            i2 = l * 16 * 2;
        }
        float f = 1.1666666f * (p_updateLightmap_0_.getSunBrightness(1.0f) - 0.2f);
        if (p_updateLightmap_0_.getLastLightningBolt() > 0) {
            f = 1.0f;
        }
        f = Config.limitTo1(f);
        final float f2 = f * (l - 1);
        final float f3 = Config.limitTo1(p_updateLightmap_1_ + 0.5f) * (l - 1);
        final float f4 = Config.limitTo1(Config.getGameSettings().gammaSetting);
        final boolean flag = f4 > 1.0E-4f;
        getLightMapColumn(afloat, f2, i2, l, CustomColorizer.sunRgbs);
        getLightMapColumn(afloat, f3, i2 + 16 * l, l, CustomColorizer.torchRgbs);
        final float[] afloat2 = new float[3];
        for (int j2 = 0; j2 < 16; ++j2) {
            for (int k2 = 0; k2 < 16; ++k2) {
                for (int l2 = 0; l2 < 3; ++l2) {
                    float f5 = Config.limitTo1(CustomColorizer.sunRgbs[j2][l2] + CustomColorizer.torchRgbs[k2][l2]);
                    if (flag) {
                        float f6 = 1.0f - f5;
                        f6 = 1.0f - f6 * f6 * f6 * f6;
                        f5 = f4 * f6 + (1.0f - f4) * f5;
                    }
                    afloat2[l2] = f5;
                }
                final int i3 = (int)(afloat2[0] * 255.0f);
                final int j3 = (int)(afloat2[1] * 255.0f);
                final int k3 = (int)(afloat2[2] * 255.0f);
                p_updateLightmap_2_[j2 * 16 + k2] = (0xFF000000 | i3 << 16 | j3 << 8 | k3);
            }
        }
        return true;
    }
    
    private static void getLightMapColumn(final float[][] p_getLightMapColumn_0_, final float p_getLightMapColumn_1_, final int p_getLightMapColumn_2_, final int p_getLightMapColumn_3_, final float[][] p_getLightMapColumn_4_) {
        final int i = (int)Math.floor(p_getLightMapColumn_1_);
        final int j = (int)Math.ceil(p_getLightMapColumn_1_);
        if (i == j) {
            for (int i2 = 0; i2 < 16; ++i2) {
                final float[] afloat3 = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + i2 * p_getLightMapColumn_3_ + i];
                final float[] afloat4 = p_getLightMapColumn_4_[i2];
                for (int j2 = 0; j2 < 3; ++j2) {
                    afloat4[j2] = afloat3[j2];
                }
            }
        }
        else {
            final float f = 1.0f - (p_getLightMapColumn_1_ - i);
            final float f2 = 1.0f - (j - p_getLightMapColumn_1_);
            for (int k = 0; k < 16; ++k) {
                final float[] afloat5 = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + k * p_getLightMapColumn_3_ + i];
                final float[] afloat6 = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + k * p_getLightMapColumn_3_ + j];
                final float[] afloat7 = p_getLightMapColumn_4_[k];
                for (int l = 0; l < 3; ++l) {
                    afloat7[l] = afloat5[l] * f + afloat6[l] * f2;
                }
            }
        }
    }
    
    public static Vec3 getWorldFogColor(Vec3 p_getWorldFogColor_0_, final WorldClient p_getWorldFogColor_1_, final Entity p_getWorldFogColor_2_, final float p_getWorldFogColor_3_) {
        final int i = p_getWorldFogColor_1_.provider.getDimensionId();
        switch (i) {
            case -1: {
                p_getWorldFogColor_0_ = getFogColorNether(p_getWorldFogColor_0_);
                break;
            }
            case 0: {
                final Minecraft minecraft = Minecraft.getMinecraft();
                p_getWorldFogColor_0_ = getFogColor(p_getWorldFogColor_0_, minecraft.theWorld, p_getWorldFogColor_2_.posX, p_getWorldFogColor_2_.posY + 1.0, p_getWorldFogColor_2_.posZ);
                break;
            }
            case 1: {
                p_getWorldFogColor_0_ = getFogColorEnd(p_getWorldFogColor_0_);
                break;
            }
        }
        return p_getWorldFogColor_0_;
    }
    
    public static Vec3 getWorldSkyColor(Vec3 p_getWorldSkyColor_0_, final WorldClient p_getWorldSkyColor_1_, final Entity p_getWorldSkyColor_2_, final float p_getWorldSkyColor_3_) {
        final int i = p_getWorldSkyColor_1_.provider.getDimensionId();
        switch (i) {
            case 0: {
                final Minecraft minecraft = Minecraft.getMinecraft();
                p_getWorldSkyColor_0_ = getSkyColor(p_getWorldSkyColor_0_, minecraft.theWorld, p_getWorldSkyColor_2_.posX, p_getWorldSkyColor_2_.posY + 1.0, p_getWorldSkyColor_2_.posZ);
                break;
            }
            case 1: {
                p_getWorldSkyColor_0_ = getSkyColorEnd(p_getWorldSkyColor_0_);
                break;
            }
        }
        return p_getWorldSkyColor_0_;
    }
    
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
}

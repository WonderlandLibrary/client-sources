package optfine;

import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.world.biome.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.util.*;

public class CustomColorizer
{
    private static float[][][] lightMapsColorsRgb;
    private static Vec3 skyColorEnd;
    private static int[] fogColors;
    private static int[] lightMapsHeight;
    private static int[] foliageColors;
    private static int[] foliagePineColors;
    private static final int TYPE_NONE;
    private static int[] waterColors;
    private static int[] myceliumParticleColors;
    private static float[][] sunRgbs;
    private static int[] redstoneColors;
    private static boolean useDefaultColorMultiplier;
    private static int[][] blockPalettes;
    private static float[][] torchRgbs;
    private static final String[] I;
    private static int[][] paletteColors;
    private static int[] swampGrassColors;
    private static Vec3 fogColorNether;
    private static int[] underwaterColors;
    private static int[] swampFoliageColors;
    private static Vec3 fogColorEnd;
    private static int particlePortalColor;
    private static int particleWaterColor;
    private static int[] stemColors;
    private static int lilyPadColor;
    private static int[] skyColors;
    private static Random random;
    private static int[] grassColors;
    private static final int TYPE_FOLIAGE;
    private static final int TYPE_GRASS;
    private static int[] foliageBirchColors;
    
    public static int getStemColorMultiplier(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final RenderEnv renderEnv) {
        if (CustomColorizer.stemColors == null) {
            return block.colorMultiplier(blockAccess, blockPos);
        }
        int n = renderEnv.getMetadata();
        if (n < 0) {
            n = "".length();
        }
        if (n >= CustomColorizer.stemColors.length) {
            n = CustomColorizer.stemColors.length - " ".length();
        }
        return CustomColorizer.stemColors[n];
    }
    
    private static float[][] toRgb(final int[] array) {
        final float[][] array2 = new float[array.length]["   ".length()];
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < array.length) {
            final int n = array[i];
            final float n2 = (n >> (0xB1 ^ 0xA1) & 205 + 238 - 405 + 217) / 255.0f;
            final float n3 = (n >> (0x70 ^ 0x78) & 214 + 16 - 23 + 48) / 255.0f;
            final float n4 = (n & 147 + 89 - 70 + 89) / 255.0f;
            final float[] array3 = array2[i];
            array3["".length()] = n2;
            array3[" ".length()] = n3;
            array3["  ".length()] = n4;
            ++i;
        }
        return array2;
    }
    
    public static Vec3 getWorldSkyColor(Vec3 vec3, final WorldClient worldClient, final Entity entity, final float n) {
        switch (worldClient.provider.getDimensionId()) {
            case 0: {
                vec3 = getSkyColor(vec3, Minecraft.getMinecraft().theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                vec3 = getSkyColorEnd(vec3);
                break;
            }
        }
        return vec3;
    }
    
    private static void I() {
        (I = new String[0x46 ^ 0x7E])["".length()] = I("*\u0017*\n\"$\u001c?\u0019y$\u001b6\u0004$*\u0015*D", "GtZkV");
        CustomColorizer.I[" ".length()] = I("\u00025\u0000\u001a8\u00045\u000bA.\u0019<\u0017\u001c \u0017 W\t?\u0017#\u000b@=\u00187", "vPxnM");
        CustomColorizer.I["  ".length()] = I("\u00041\f\u001f\u0002\u00021\u0007D\u0014\u001f8\u001b\u0019\u001a\u0011$[\r\u0018\u001c=\u0015\f\u0012^$\u001a\f", "pTtkw");
        CustomColorizer.I["   ".length()] = I("\u000f.1\u00136V?+\u0011", "xOEvD");
        CustomColorizer.I[0x9E ^ 0x9A] = I("\u0013\u0017\u000e0\"\u0007\u0019\u0016:\"<X\n;7", "dvzUP");
        CustomColorizer.I[0xC6 ^ 0xC3] = I("\u0019,\u001b\u0002x\u0019+\u0012", "iEugV");
        CustomColorizer.I[0xBE ^ 0xB8] = I("3\u0007\u001a14,\u0002\u001b&y3\u0000\u0013", "CntTW");
        CustomColorizer.I[0xC4 ^ 0xC3] = I("\u0017\n\u0001!\u0006[\u0013\u001d%", "ucsBn");
        CustomColorizer.I[0xC9 ^ 0xC1] = I("\f\u0000\"\"-\r\u0006<.7@\u0019>&", "niPAE");
        CustomColorizer.I[0x6D ^ 0x64] = I("50\u0017\u0000\n!5\u0017\u001e\th7\u0018\n", "FGvmz");
        CustomColorizer.I[0xB4 ^ 0xBE] = I("\u001e\u0003\u0018\u001e\u0014\n\u0006\u0018\u0000\u0017\u000e\u001b\u0015\u001c\u0016C\u0004\u0017\u0014", "mtysd");
        CustomColorizer.I[0x57 ^ 0x5C] = I("2\u000f\u000e\u0006\"'\u0017\u0003\u00023&\u001dA\u001b<&", "AxokR");
        CustomColorizer.I[0x1 ^ 0xD] = I("=2\u0005\u000e1(*\b\n ) \u0007\f-!7J\u0013/)", "NEdcA");
        CustomColorizer.I[0x59 ^ 0x54] = I(">\t\ryy=\f\u0013", "MbtIW");
        CustomColorizer.I[0x40 ^ 0x4E] = I("\u0010=\u001d3\u0002\u000f9\u0016`C\u00138\u0003", "cVdPm");
        CustomColorizer.I[0x43 ^ 0x4C] = I("!!\u001dE]7 \u001d", "GNzus");
        CustomColorizer.I[0x74 ^ 0x64] = I("\u001e,\r1\u000e\u0014,\u0018bO\b-\r", "xCjRa");
        CustomColorizer.I[0xB5 ^ 0xA4] = I("\u0002%1!1\u0000*!!1Y;;#", "wKUDC");
        CustomColorizer.I[0x37 ^ 0x25] = I("\u000364\u0013!\u00019$\u0013!\u00157<\u0019!X(>\u0011", "vXPvS");
        CustomColorizer.I[0xA4 ^ 0xB7] = I("\u0007\u0013\u00111!\u001a\u0018\u0010l%\u001b\u0011", "uvuBU");
        CustomColorizer.I[0x30 ^ 0x24] = I("$\u0011\u001e\u001b=9\u001a\u001f\u000b&:\u001b\bF98\u0013", "VtzhI");
        CustomColorizer.I[0x12 ^ 0x7] = I(";\u001d\t(X8\u0007\u000b", "HilEv");
        CustomColorizer.I[0x4 ^ 0x12] = I("\u0012\u0010\b\t9\u000e\b\u0002\u0016t\u0011\n\n", "admdZ");
        CustomColorizer.I[0x29 ^ 0x3E] = I("\n\u001d\n(\u0000\u000e\u0011\u0004=\r\u0015\u0010\u0000.\u0000\u0002J\u0019#\u000b", "gdiMl");
        CustomColorizer.I[0x21 ^ 0x39] = I("\u001e\u0016\u0004\u0002\u000f\u001a\u001a\n\u0017\u0002\u0001\u001b\u000e\u0004\u000f\u0016\f\b\u000b\f\u0001A\u0017\t\u0004", "soggc");
        CustomColorizer.I[0x54 ^ 0x4D] = I("\u0005\u0005(9'\u000b\u000e=*|\u0004\u000f?0'\u0005\u0007(w$\u0007\u00144<", "hfXXS");
        CustomColorizer.I[0x5B ^ 0x41] = I("Z<*2", "tLDUO");
        CustomColorizer.I[0x9A ^ 0x81] = I(",!\"2\f\"*7!W\"-><\no2 <\b$0&:\u001d2", "ABRSx");
        CustomColorizer.I[0x78 ^ 0x64] = I("\u0019\"\u0006\r\";*G", "UMgiK");
        CustomColorizer.I[0x24 ^ 0x39] = I("5\u0011*5 8\u001c", "YxFLP");
        CustomColorizer.I[0xA3 ^ 0xBD] = I("25(-\u0002!8?w\u001c# ?+", "BTZYk");
        CustomColorizer.I[0xB7 ^ 0xA8] = I("-\u00068\u0019l>\u0015#\f0", "ItWiB");
        CustomColorizer.I[0x5F ^ 0x7F] = I("8$\u00177&+)\u0000m?'7\u0011\"#", "HEeCO");
        CustomColorizer.I[0x1D ^ 0x3C] = I("\u001c!-X\f\u001f:\"\u0013\u0010", "zNJvb");
        CustomColorizer.I[0x9B ^ 0xB9] = I("\f+\tm\u001f\u0004 ", "jDnCz");
        CustomColorizer.I[0xAE ^ 0x8D] = I("\u0006*\u0000\\\u000f\u001b%", "uAyrj");
        CustomColorizer.I[0x0 ^ 0x24] = I("%.=\u0011\u0015!*\u007f\u0016\r:,:Z", "UOQta");
        CustomColorizer.I[0x60 ^ 0x45] = I("*4\"'\rH(,(\u0003\u001c,(~F", "hXMDf");
        CustomColorizer.I[0x11 ^ 0x37] = I("wtm", "WIMou");
        CustomColorizer.I[0x6 ^ 0x21] = I("ocI", "OOrRZ");
        CustomColorizer.I[0xA6 ^ 0x8E] = I("j", "PMtFQ");
        CustomColorizer.I[0xA7 ^ 0x8E] = I("K", "qHuBd");
        CustomColorizer.I[0x98 ^ 0xB2] = I("\u000177\u000b\"!=a\b\"':*J#-- \u000e/<8{J", "HYAjN");
        CustomColorizer.I[0x79 ^ 0x52] = I("m\u0004:L3,\u00011\u00187(Wt", "MmTlC");
        CustomColorizer.I[0x4B ^ 0x67] = I(".\u0018/'\u0015\u000e\u0012y$\u0015\b\u00152f\u0010\t\u0012<>CG", "gvYFy");
        CustomColorizer.I[0xBE ^ 0x93] = I("M%\u0006l \f \r8$\bvH", "mLhLP");
        CustomColorizer.I[0x10 ^ 0x3E] = I("+8&,\"\u0005m67!\u0007?ox", "hMUXM");
        CustomColorizer.I[0x22 ^ 0xD] = I("Dqx", "dLXxl");
        CustomColorizer.I[0x48 ^ 0x78] = I(":*0)4\u001a f+-\u00000)%x\u0010+*'*Id", "sDFHX");
        CustomColorizer.I[0x48 ^ 0x79] = I("Zwm", "zJMZB");
        CustomColorizer.I[0x22 ^ 0x10] = I("$\r=\u0000\u0015\u0004\u0007k\u0002\f\u001e\u0017$\fY\u000e\f'\u000e\u000b\u001eC'\u0004\u0017\n\u0017#[Y", "mcKay");
        CustomColorizer.I[0x35 ^ 0x6] = I("[i\u0005\u0000\f\u001fsU", "wIuax");
        CustomColorizer.I[0xB ^ 0x3F] = I("$;\u0011\f\u0013\u00063P\u000b\u000f\u001b \u001f\u0005Z\u000b;\u001c\u0007\b\u001bnP", "hTphz");
        CustomColorizer.I[0x6D ^ 0x58] = I("\u001e;\u00078;>1Q5>0=\u000546'u\u000603#=Ky", "WUqYW");
        CustomColorizer.I[0x69 ^ 0x5F] = I("W?$\u000b{Wv.\u00177\u001e+$\u0017,\u00127?V-\u001e>#\r,\u0016)", "wYKyA");
        CustomColorizer.I[0x85 ^ 0xB2] = I("l2(\u0002", "BBFeI");
    }
    
    private static void getLightMapColumn(final float[][] array, final float n, final int n2, final int n3, final float[][] array2) {
        final int n4 = (int)Math.floor(n);
        final int n5 = (int)Math.ceil(n);
        if (n4 == n5) {
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < (0xB5 ^ 0xA5)) {
                final float[] array3 = array[n2 + i * n3 + n4];
                final float[] array4 = array2[i];
                int j = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                while (j < "   ".length()) {
                    array4[j] = array3[j];
                    ++j;
                }
                ++i;
            }
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            final float n6 = 1.0f - (n - n4);
            final float n7 = 1.0f - (n5 - n);
            int k = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (k < (0x9B ^ 0x8B)) {
                final float[] array5 = array[n2 + k * n3 + n4];
                final float[] array6 = array[n2 + k * n3 + n5];
                final float[] array7 = array2[k];
                int l = "".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
                while (l < "   ".length()) {
                    array7[l] = array5[l] * n6 + array6[l] * n7;
                    ++l;
                }
                ++k;
            }
        }
    }
    
    static {
        I();
        TYPE_NONE = "".length();
        TYPE_FOLIAGE = "  ".length();
        TYPE_GRASS = " ".length();
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
        CustomColorizer.sunRgbs = new float[0x87 ^ 0x97]["   ".length()];
        CustomColorizer.torchRgbs = new float[0x41 ^ 0x51]["   ".length()];
        CustomColorizer.redstoneColors = null;
        CustomColorizer.stemColors = null;
        CustomColorizer.myceliumParticleColors = null;
        CustomColorizer.useDefaultColorMultiplier = (" ".length() != 0);
        CustomColorizer.particleWaterColor = -" ".length();
        CustomColorizer.particlePortalColor = -" ".length();
        CustomColorizer.lilyPadColor = -" ".length();
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.random = new Random();
    }
    
    public static boolean updateLightmap(final World world, final float n, final int[] array, final boolean b) {
        if (world == null) {
            return "".length() != 0;
        }
        if (CustomColorizer.lightMapsColorsRgb == null) {
            return "".length() != 0;
        }
        if (!Config.isCustomColors()) {
            return "".length() != 0;
        }
        final int dimensionId = world.provider.getDimensionId();
        if (dimensionId < -" ".length() || dimensionId > " ".length()) {
            return "".length() != 0;
        }
        final int n2 = dimensionId + " ".length();
        final float[][] array2 = CustomColorizer.lightMapsColorsRgb[n2];
        if (array2 == null) {
            return "".length() != 0;
        }
        final int n3 = CustomColorizer.lightMapsHeight[n2];
        if (b && n3 < (0x3A ^ 0x7A)) {
            return "".length() != 0;
        }
        final int n4 = array2.length / n3;
        if (n4 < (0x94 ^ 0x84)) {
            Config.warn(CustomColorizer.I[0x34 ^ 0x1] + n4 + CustomColorizer.I[0x2F ^ 0x19] + dimensionId + CustomColorizer.I[0x47 ^ 0x70]);
            CustomColorizer.lightMapsColorsRgb[n2] = null;
            return "".length() != 0;
        }
        int length = "".length();
        if (b) {
            length = n4 * (0x21 ^ 0x31) * "  ".length();
        }
        float n5 = 1.1666666f * (world.getSunBrightness(1.0f) - 0.2f);
        if (world.getLastLightningBolt() > 0) {
            n5 = 1.0f;
        }
        final float n6 = Config.limitTo1(n5) * (n4 - " ".length());
        final float n7 = Config.limitTo1(n + 0.5f) * (n4 - " ".length());
        final float limitTo1 = Config.limitTo1(Config.getGameSettings().gammaSetting);
        int n8;
        if (limitTo1 > 1.0E-4f) {
            n8 = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n8 = "".length();
        }
        final int n9 = n8;
        getLightMapColumn(array2, n6, length, n4, CustomColorizer.sunRgbs);
        getLightMapColumn(array2, n7, length + (0x31 ^ 0x21) * n4, n4, CustomColorizer.torchRgbs);
        final float[] array3 = new float["   ".length()];
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < (0x7C ^ 0x6C)) {
            int j = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (j < (0x95 ^ 0x85)) {
                int k = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (k < "   ".length()) {
                    float limitTo2 = Config.limitTo1(CustomColorizer.sunRgbs[i][k] + CustomColorizer.torchRgbs[j][k]);
                    if (n9 != 0) {
                        final float n10 = 1.0f - limitTo2;
                        limitTo2 = limitTo1 * (1.0f - n10 * n10 * n10 * n10) + (1.0f - limitTo1) * limitTo2;
                    }
                    array3[k] = limitTo2;
                    ++k;
                }
                array[i * (0x36 ^ 0x26) + j] = (-(8623572 + 10060074 - 3397156 + 1490726) | (int)(array3["".length()] * 255.0f) << (0x78 ^ 0x68) | (int)(array3[" ".length()] * 255.0f) << (0x2A ^ 0x22) | (int)(array3["  ".length()] * 255.0f));
                ++j;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    private static int[] getCustomColors(final String s, final String[] array, final int n) {
        int i = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i < array.length) {
            final int[] customColors = getCustomColors(String.valueOf(s) + array[i], n);
            if (customColors != null) {
                return customColors;
            }
            ++i;
        }
        return null;
    }
    
    public static void updateMyceliumFX(final EntityFX entityFX) {
        if (CustomColorizer.myceliumParticleColors != null) {
            final int n = CustomColorizer.myceliumParticleColors[CustomColorizer.random.nextInt(CustomColorizer.myceliumParticleColors.length)];
            entityFX.setRBGColorF((n >> (0x66 ^ 0x76) & 77 + 17 + 19 + 142) / 255.0f, (n >> (0x3F ^ 0x37) & 211 + 69 - 93 + 68) / 255.0f, (n & 237 + 21 - 17 + 14) / 255.0f);
        }
    }
    
    public static void updatePortalFX(final EntityFX entityFX) {
        if (CustomColorizer.particlePortalColor >= 0) {
            final int particlePortalColor = CustomColorizer.particlePortalColor;
            entityFX.setRBGColorF((particlePortalColor >> (0xB ^ 0x1B) & 47 + 52 - 70 + 226) / 255.0f, (particlePortalColor >> (0x3 ^ 0xB) & 77 + 224 - 140 + 94) / 255.0f, (particlePortalColor & 137 + 175 - 112 + 55) / 255.0f);
        }
    }
    
    public static Vec3 getFogColorNether(final Vec3 vec3) {
        Vec3 fogColorNether;
        if (CustomColorizer.fogColorNether == null) {
            fogColorNether = vec3;
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            fogColorNether = CustomColorizer.fogColorNether;
        }
        return fogColorNether;
    }
    
    private static void readColorProperties(final String s) {
        try {
            final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
            if (resourceStream == null) {
                return;
            }
            Config.log(CustomColorizer.I[0xA2 ^ 0xBE] + s);
            final Properties properties = new Properties();
            properties.load(resourceStream);
            CustomColorizer.lilyPadColor = readColor(properties, CustomColorizer.I[0x33 ^ 0x2E]);
            final Properties properties2 = properties;
            final String[] array = new String["  ".length()];
            array["".length()] = CustomColorizer.I[0x61 ^ 0x7F];
            array[" ".length()] = CustomColorizer.I[0x18 ^ 0x7];
            CustomColorizer.particleWaterColor = readColor(properties2, array);
            CustomColorizer.particlePortalColor = readColor(properties, CustomColorizer.I[0xA1 ^ 0x81]);
            CustomColorizer.fogColorNether = readColorVec3(properties, CustomColorizer.I[0x62 ^ 0x43]);
            CustomColorizer.fogColorEnd = readColorVec3(properties, CustomColorizer.I[0x30 ^ 0x12]);
            CustomColorizer.skyColorEnd = readColorVec3(properties, CustomColorizer.I[0x5A ^ 0x79]);
            readCustomPalettes(properties, s);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        catch (FileNotFoundException ex2) {}
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getRedstoneColor(final int n) {
        int n2;
        if (CustomColorizer.redstoneColors == null) {
            n2 = -" ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (n >= 0 && n <= (0xB4 ^ 0xBB)) {
            n2 = (CustomColorizer.redstoneColors[n] & 25721 + 9178309 - 309399 + 7882584);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n2 = -" ".length();
        }
        return n2;
    }
    
    public static Vec3 getFogColor(final Vec3 vec3, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.fogColors == null) {
            return vec3;
        }
        final int smoothColor = getSmoothColor(CustomColorizer.fogColors, blockAccess, n, n2, n3, 0x27 ^ 0x20, " ".length());
        return new Vec3((smoothColor >> (0x7D ^ 0x6D) & 193 + 136 - 79 + 5) / 255.0f * ((float)vec3.xCoord / 0.753f), (smoothColor >> (0x4C ^ 0x44) & 185 + 105 - 285 + 250) / 255.0f * ((float)vec3.yCoord / 0.8471f), (smoothColor & 234 + 253 - 409 + 177) / 255.0f * (float)vec3.zCoord);
    }
    
    private static Vec3 readColorVec3(final Properties properties, final String s) {
        final int color = readColor(properties, s);
        if (color < 0) {
            return null;
        }
        return new Vec3((color >> (0x32 ^ 0x22) & 114 + 252 - 306 + 195) / 255.0f, (color >> (0x74 ^ 0x7C) & 192 + 178 - 210 + 95) / 255.0f, (color & 105 + 242 - 308 + 216) / 255.0f);
    }
    
    public static int getLilypadColorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos) {
        int n;
        if (CustomColorizer.lilyPadColor < 0) {
            n = Blocks.waterlily.colorMultiplier(blockAccess, blockPos);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            n = CustomColorizer.lilyPadColor;
        }
        return n;
    }
    
    public static void updateWaterFX(final EntityFX entityFX, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.waterColors != null) {
            final int fluidColor = getFluidColor(Blocks.water, blockAccess, new BlockPos(n, n2, n3));
            final int n4 = fluidColor >> (0xD1 ^ 0xC1) & 78 + 70 + 107 + 0;
            final int n5 = fluidColor >> (0x5F ^ 0x57) & 67 + 114 + 18 + 56;
            final int n6 = fluidColor & 59 + 106 - 6 + 96;
            float n7 = n4 / 255.0f;
            float n8 = n5 / 255.0f;
            float n9 = n6 / 255.0f;
            if (CustomColorizer.particleWaterColor >= 0) {
                final int n10 = CustomColorizer.particleWaterColor >> (0x9 ^ 0x19) & 107 + 102 - 158 + 204;
                final int n11 = CustomColorizer.particleWaterColor >> (0xCE ^ 0xC6) & 71 + 143 - 145 + 186;
                final int n12 = CustomColorizer.particleWaterColor & 28 + 212 - 80 + 95;
                n7 *= n10 / 255.0f;
                n8 *= n11 / 255.0f;
                n9 *= n12 / 255.0f;
            }
            entityFX.setRBGColorF(n7, n8, n9);
        }
    }
    
    public static Vec3 getFogColorEnd(final Vec3 vec3) {
        Vec3 fogColorEnd;
        if (CustomColorizer.fogColorEnd == null) {
            fogColorEnd = vec3;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            fogColorEnd = CustomColorizer.fogColorEnd;
        }
        return fogColorEnd;
    }
    
    public static Vec3 getWorldFogColor(Vec3 vec3, final WorldClient worldClient, final Entity entity, final float n) {
        switch (worldClient.provider.getDimensionId()) {
            case -1: {
                vec3 = getFogColorNether(vec3);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 0: {
                vec3 = getFogColor(vec3, Minecraft.getMinecraft().theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            case 1: {
                vec3 = getFogColorEnd(vec3);
                break;
            }
        }
        return vec3;
    }
    
    public static int getSmoothColor(final int[] array, final IBlockAccess blockAccess, final double n, final double n2, final double n3, final int n4, final int n5) {
        if (array == null) {
            return -" ".length();
        }
        final int floor_double = MathHelper.floor_double(n);
        final int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        final int n6 = n4 * n5 / "  ".length();
        int length = "".length();
        int length2 = "".length();
        int length3 = "".length();
        int length4 = "".length();
        final BlockPosM blockPosM = new BlockPosM("".length(), "".length(), "".length());
        int i = floor_double - n6;
        "".length();
        if (true != true) {
            throw null;
        }
        while (i <= floor_double + n6) {
            int j = floor_double3 - n6;
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (j <= floor_double3 + n6) {
                blockPosM.setXyz(i, floor_double2, j);
                final int customColor = getCustomColor(array, blockAccess, blockPosM);
                length += (customColor >> (0xD7 ^ 0xC7) & 180 + 134 - 303 + 244);
                length2 += (customColor >> (0x66 ^ 0x6E) & 137 + 124 - 153 + 147);
                length3 += (customColor & 107 + 84 - 136 + 200);
                ++length4;
                j += n5;
            }
            i += n5;
        }
        return length / length4 << (0x89 ^ 0x99) | length2 / length4 << (0xB2 ^ 0xBA) | length3 / length4;
    }
    
    public static void updateReddustFX(final EntityFX entityFX, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.redstoneColors != null) {
            final int redstoneColor = getRedstoneColor(getRedstoneLevel(blockAccess.getBlockState(new BlockPos(n, n2, n3)), 0x28 ^ 0x27));
            if (redstoneColor != -" ".length()) {
                entityFX.setRBGColorF((redstoneColor >> (0xD6 ^ 0xC6) & 9 + 246 - 1 + 1) / 255.0f, (redstoneColor >> (0x83 ^ 0x8B) & 47 + 162 + 34 + 12) / 255.0f, (redstoneColor & 210 + 130 - 171 + 86) / 255.0f);
            }
        }
    }
    
    private static void readCustomPalettes(final Properties properties, final String s) {
        CustomColorizer.blockPalettes = new int[72 + 89 - 37 + 132][" ".length()];
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < 49 + 249 - 251 + 209) {
            CustomColorizer.blockPalettes[i]["".length()] = -" ".length();
            ++i;
        }
        final String s2 = CustomColorizer.I[0xA ^ 0x2E];
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final Iterator<String> iterator = ((Hashtable<String, V>)properties).keySet().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String next = iterator.next();
            final String property = properties.getProperty(next);
            if (next.startsWith(s2)) {
                hashMap.put(next, property);
            }
        }
        final String[] array = hashMap.keySet().toArray(new String[hashMap.size()]);
        CustomColorizer.paletteColors = new int[array.length][];
        int j = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (j < array.length) {
            final String s3 = array[j];
            final String property2 = properties.getProperty(s3);
            Config.log(CustomColorizer.I[0x16 ^ 0x33] + s3 + CustomColorizer.I[0xA2 ^ 0x84] + property2);
            CustomColorizer.paletteColors[j] = getCustomColors(TextureUtils.fixResourcePath(s3.substring(s2.length()), TextureUtils.getBasePath(s)), 40312 + 6405 - 24671 + 43490);
            final String[] tokenize = Config.tokenize(property2, CustomColorizer.I[0xB4 ^ 0x93]);
            int k = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (k < tokenize.length) {
                String s4 = tokenize[k];
                int int1 = -" ".length();
                Label_0771: {
                    if (s4.contains(CustomColorizer.I[0x1 ^ 0x29])) {
                        final String[] tokenize2 = Config.tokenize(s4, CustomColorizer.I[0x2A ^ 0x3]);
                        s4 = tokenize2["".length()];
                        int1 = Config.parseInt(tokenize2[" ".length()], -" ".length());
                        if (int1 < 0 || int1 > (0x7E ^ 0x71)) {
                            Config.log(CustomColorizer.I[0xA1 ^ 0x8B] + s4 + CustomColorizer.I[0x1D ^ 0x36] + s3);
                            "".length();
                            if (2 <= -1) {
                                throw null;
                            }
                            break Label_0771;
                        }
                    }
                    final int int2 = Config.parseInt(s4, -" ".length());
                    if (int2 >= 0 && int2 <= 194 + 146 - 235 + 150) {
                        if (int2 != Block.getIdFromBlock(Blocks.grass) && int2 != Block.getIdFromBlock(Blocks.tallgrass) && int2 != Block.getIdFromBlock(Blocks.leaves) && int2 != Block.getIdFromBlock(Blocks.vine)) {
                            if (int1 == -" ".length()) {
                                CustomColorizer.blockPalettes[int2]["".length()] = j;
                                "".length();
                                if (2 >= 3) {
                                    throw null;
                                }
                            }
                            else {
                                if (CustomColorizer.blockPalettes[int2].length < (0x67 ^ 0x77)) {
                                    Arrays.fill(CustomColorizer.blockPalettes[int2] = new int[0x3B ^ 0x2B], -" ".length());
                                }
                                CustomColorizer.blockPalettes[int2][int1] = j;
                                "".length();
                                if (false) {
                                    throw null;
                                }
                            }
                        }
                    }
                    else {
                        Config.log(CustomColorizer.I[0x8C ^ 0xA0] + int2 + CustomColorizer.I[0x55 ^ 0x78] + s3);
                    }
                }
                ++k;
            }
            ++j;
        }
    }
    
    public static int getColorMultiplier(final BakedQuad bakedQuad, final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final RenderEnv renderEnv) {
        if (CustomColorizer.useDefaultColorMultiplier) {
            return -" ".length();
        }
        int[] array = null;
        int[] array2 = null;
        if (CustomColorizer.blockPalettes != null) {
            final int blockId = renderEnv.getBlockId();
            if (blockId >= 0 && blockId < 197 + 128 - 264 + 195) {
                final int[] array3 = CustomColorizer.blockPalettes[blockId];
                final int n = -" ".length();
                int n2;
                if (array3.length > " ".length()) {
                    n2 = array3[renderEnv.getMetadata()];
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                }
                else {
                    n2 = array3["".length()];
                }
                if (n2 >= 0) {
                    array = CustomColorizer.paletteColors[n2];
                }
            }
            if (array != null) {
                if (Config.isSmoothBiomes()) {
                    return getSmoothColorMultiplier(block, blockAccess, blockPos, array, array, "".length(), "".length(), renderEnv);
                }
                return getCustomColor(array, blockAccess, blockPos);
            }
        }
        if (!bakedQuad.hasTintIndex()) {
            return -" ".length();
        }
        if (block == Blocks.waterlily) {
            return getLilypadColorMultiplier(blockAccess, blockPos);
        }
        if (block instanceof BlockStem) {
            return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
        }
        final boolean swampColors = Config.isSwampColors();
        int n3 = "".length();
        int n4 = "".length();
        int n5 = "".length();
        if (block != Blocks.grass && block != Blocks.tallgrass) {
            if (block == Blocks.leaves) {
                n4 = "  ".length();
                n3 = (Config.isSmoothBiomes() ? 1 : 0);
                n5 = renderEnv.getMetadata();
                if ((n5 & "   ".length()) == " ".length()) {
                    array = CustomColorizer.foliagePineColors;
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                }
                else if ((n5 & "   ".length()) == "  ".length()) {
                    array = CustomColorizer.foliageBirchColors;
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                else {
                    array = CustomColorizer.foliageColors;
                    if (swampColors) {
                        array2 = CustomColorizer.swampFoliageColors;
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                    }
                    else {
                        array2 = array;
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                }
            }
            else if (block == Blocks.vine) {
                n4 = "  ".length();
                n3 = (Config.isSmoothBiomes() ? 1 : 0);
                array = CustomColorizer.foliageColors;
                if (swampColors) {
                    array2 = CustomColorizer.swampFoliageColors;
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else {
                    array2 = array;
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
            }
        }
        else {
            n4 = " ".length();
            n3 = (Config.isSmoothBiomes() ? 1 : 0);
            array = CustomColorizer.grassColors;
            if (swampColors) {
                array2 = CustomColorizer.swampGrassColors;
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                array2 = array;
            }
        }
        if (n3 != 0) {
            return getSmoothColorMultiplier(block, blockAccess, blockPos, array, array2, n4, n5, renderEnv);
        }
        if (array2 != array && blockAccess.getBiomeGenForCoords(blockPos) == BiomeGenBase.swampland) {
            array = array2;
        }
        int customColor;
        if (array != null) {
            customColor = getCustomColor(array, blockAccess, blockPos);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            customColor = -" ".length();
        }
        return customColor;
    }
    
    private static int averageColor(final int n, final int n2) {
        return ((n >> (0x63 ^ 0x73) & 12 + 253 - 39 + 29) + (n2 >> (0x64 ^ 0x74) & 67 + 231 - 165 + 122)) / "  ".length() << (0x28 ^ 0x38) | ((n >> (0x58 ^ 0x50) & 103 + 151 - 16 + 17) + (n2 >> (0x2 ^ 0xA) & 27 + 37 + 76 + 115)) / "  ".length() << (0xCC ^ 0xC4) | ((n & 19 + 143 + 42 + 51) + (n2 & 49 + 161 - 26 + 71)) / "  ".length();
    }
    
    private static int getCustomColor(final int[] array, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final BiomeGenBase biomeGenForCoords = blockAccess.getBiomeGenForCoords(blockPos);
        final double n = MathHelper.clamp_float(biomeGenForCoords.getFloatTemperature(blockPos), 0.0f, 1.0f);
        return array[(int)((1.0 - MathHelper.clamp_float(biomeGenForCoords.getFloatRainfall(), 0.0f, 1.0f) * n) * 255.0) << (0xBA ^ 0xB2) | (int)((1.0 - n) * 255.0)] & 8351330 + 15925707 - 21999138 + 14499316;
    }
    
    private static int readColor(final Properties properties, final String[] array) {
        int i = "".length();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (i < array.length) {
            final int color = readColor(properties, array[i]);
            if (color >= 0) {
                return color;
            }
            ++i;
        }
        return -" ".length();
    }
    
    private static int getTextureHeight(final String s, final int n) {
        try {
            final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
            if (resourceStream == null) {
                return n;
            }
            final BufferedImage read = ImageIO.read(resourceStream);
            int height;
            if (read == null) {
                height = n;
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                height = read.getHeight();
            }
            return height;
        }
        catch (IOException ex) {
            return n;
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static int getRedstoneLevel(final IBlockState blockState, final int n) {
        if (!(blockState.getBlock() instanceof BlockRedstoneWire)) {
            return n;
        }
        final Integer value = blockState.getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
        if (!(value instanceof Integer)) {
            return n;
        }
        return value;
    }
    
    public static void updateUseDefaultColorMultiplier() {
        int useDefaultColorMultiplier;
        if (CustomColorizer.foliageBirchColors == null && CustomColorizer.foliagePineColors == null && CustomColorizer.swampGrassColors == null && CustomColorizer.swampFoliageColors == null && CustomColorizer.blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes()) {
            useDefaultColorMultiplier = " ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            useDefaultColorMultiplier = "".length();
        }
        CustomColorizer.useDefaultColorMultiplier = (useDefaultColorMultiplier != 0);
    }
    
    public static Vec3 getUnderwaterColor(final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.underwaterColors == null) {
            return null;
        }
        final int smoothColor = getSmoothColor(CustomColorizer.underwaterColors, blockAccess, n, n2, n3, 0x59 ^ 0x5E, " ".length());
        return new Vec3((smoothColor >> (0x3 ^ 0x13) & 129 + 177 - 60 + 9) / 255.0f, (smoothColor >> (0x3C ^ 0x34) & 103 + 231 - 97 + 18) / 255.0f, (smoothColor & 126 + 182 - 53 + 0) / 255.0f);
    }
    
    public static int getFluidColor(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos) {
        int n;
        if (block.getMaterial() != Material.water) {
            n = block.colorMultiplier(blockAccess, blockPos);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (CustomColorizer.waterColors != null) {
            if (Config.isSmoothBiomes()) {
                n = getSmoothColor(CustomColorizer.waterColors, blockAccess, blockPos.getX(), blockPos.getY(), blockPos.getZ(), "   ".length(), " ".length());
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                n = getCustomColor(CustomColorizer.waterColors, blockAccess, blockPos);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
        }
        else if (!Config.isSwampColors()) {
            n = 6624015 + 6531712 - 9881118 + 13502606;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n = block.colorMultiplier(blockAccess, blockPos);
        }
        return n;
    }
    
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
        CustomColorizer.lilyPadColor = -" ".length();
        CustomColorizer.particleWaterColor = -" ".length();
        CustomColorizer.particlePortalColor = -" ".length();
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.blockPalettes = null;
        CustomColorizer.paletteColors = null;
        CustomColorizer.useDefaultColorMultiplier = (" ".length() != 0);
        final String s = CustomColorizer.I["".length()];
        CustomColorizer.grassColors = getCustomColors(CustomColorizer.I[" ".length()], 26220 + 9997 - 8239 + 37558);
        CustomColorizer.foliageColors = getCustomColors(CustomColorizer.I["  ".length()], 9395 + 33127 + 14052 + 8962);
        final String[] array = new String["  ".length()];
        array["".length()] = CustomColorizer.I["   ".length()];
        array[" ".length()] = CustomColorizer.I[0x59 ^ 0x5D];
        CustomColorizer.waterColors = getCustomColors(s, array, 39024 + 1313 - 16969 + 42168);
        if (Config.isCustomColors()) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CustomColorizer.I[0xB9 ^ 0xBC];
            array2[" ".length()] = CustomColorizer.I[0x3E ^ 0x38];
            CustomColorizer.foliagePineColors = getCustomColors(s, array2, 55275 + 15484 - 60335 + 55112);
            final String[] array3 = new String["  ".length()];
            array3["".length()] = CustomColorizer.I[0x5D ^ 0x5A];
            array3[" ".length()] = CustomColorizer.I[0x8A ^ 0x82];
            CustomColorizer.foliageBirchColors = getCustomColors(s, array3, 28610 + 38396 - 7621 + 6151);
            final String[] array4 = new String["  ".length()];
            array4["".length()] = CustomColorizer.I[0x12 ^ 0x1B];
            array4[" ".length()] = CustomColorizer.I[0x3 ^ 0x9];
            CustomColorizer.swampGrassColors = getCustomColors(s, array4, 1133 + 48730 - 10160 + 25833);
            final String[] array5 = new String["  ".length()];
            array5["".length()] = CustomColorizer.I[0x1C ^ 0x17];
            array5[" ".length()] = CustomColorizer.I[0x15 ^ 0x19];
            CustomColorizer.swampFoliageColors = getCustomColors(s, array5, 46832 + 35011 - 27350 + 11043);
            final String[] array6 = new String["  ".length()];
            array6["".length()] = CustomColorizer.I[0x4F ^ 0x42];
            array6[" ".length()] = CustomColorizer.I[0x4B ^ 0x45];
            CustomColorizer.skyColors = getCustomColors(s, array6, 23071 + 19992 + 18655 + 3818);
            final String[] array7 = new String["  ".length()];
            array7["".length()] = CustomColorizer.I[0x49 ^ 0x46];
            array7[" ".length()] = CustomColorizer.I[0x9A ^ 0x8A];
            CustomColorizer.fogColors = getCustomColors(s, array7, 32157 + 11106 - 19981 + 42254);
            final String[] array8 = new String["  ".length()];
            array8["".length()] = CustomColorizer.I[0x49 ^ 0x58];
            array8[" ".length()] = CustomColorizer.I[0xBB ^ 0xA9];
            CustomColorizer.underwaterColors = getCustomColors(s, array8, 53721 + 14788 - 18928 + 15955);
            final String[] array9 = new String["  ".length()];
            array9["".length()] = CustomColorizer.I[0x99 ^ 0x8A];
            array9[" ".length()] = CustomColorizer.I[0x9A ^ 0x8E];
            CustomColorizer.redstoneColors = getCustomColors(s, array9, 0x41 ^ 0x51);
            final String[] array10 = new String["  ".length()];
            array10["".length()] = CustomColorizer.I[0x6A ^ 0x7F];
            array10[" ".length()] = CustomColorizer.I[0x40 ^ 0x56];
            CustomColorizer.stemColors = getCustomColors(s, array10, 0x60 ^ 0x68);
            final String[] array11 = new String["  ".length()];
            array11["".length()] = CustomColorizer.I[0xD7 ^ 0xC0];
            array11[" ".length()] = CustomColorizer.I[0x31 ^ 0x29];
            CustomColorizer.myceliumParticleColors = getCustomColors(s, array11, -" ".length());
            final int[][] array12 = new int["   ".length()][];
            CustomColorizer.lightMapsColorsRgb = new float["   ".length()][][];
            CustomColorizer.lightMapsHeight = new int["   ".length()];
            int i = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
            while (i < array12.length) {
                final String string = CustomColorizer.I[0xAC ^ 0xB5] + (i - " ".length()) + CustomColorizer.I[0x1A ^ 0x0];
                array12[i] = getCustomColors(string, -" ".length());
                if (array12[i] != null) {
                    CustomColorizer.lightMapsColorsRgb[i] = toRgb(array12[i]);
                }
                CustomColorizer.lightMapsHeight[i] = getTextureHeight(string, 0x67 ^ 0x47);
                ++i;
            }
            readColorProperties(CustomColorizer.I[0xC ^ 0x17]);
            updateUseDefaultColorMultiplier();
        }
    }
    
    private static int[] getCustomColors(final String s, final int n) {
        try {
            final ResourceLocation resourceLocation = new ResourceLocation(s);
            if (Config.getResourceStream(resourceLocation) == null) {
                return null;
            }
            final int[] imageData = TextureUtil.readImageData(Config.getResourceManager(), resourceLocation);
            if (imageData == null) {
                return null;
            }
            if (n > 0 && imageData.length != n) {
                Config.log(CustomColorizer.I[0x3A ^ 0x8] + imageData.length + CustomColorizer.I[0x60 ^ 0x53] + s);
                return null;
            }
            Config.log(CustomColorizer.I[0xB1 ^ 0x85] + s);
            return imageData;
        }
        catch (FileNotFoundException ex2) {
            return null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static int readColor(final Properties properties, final String s) {
        final String property = properties.getProperty(s);
        if (property == null) {
            return -" ".length();
        }
        try {
            final int n = Integer.parseInt(property, 0x43 ^ 0x53) & 1142645 + 12320166 - 11744950 + 15059354;
            Config.log(CustomColorizer.I[0x38 ^ 0x16] + s + CustomColorizer.I[0x16 ^ 0x39] + property);
            return n;
        }
        catch (NumberFormatException ex) {
            Config.log(CustomColorizer.I[0x25 ^ 0x15] + s + CustomColorizer.I[0xAF ^ 0x9E] + property);
            return -" ".length();
        }
    }
    
    public static Vec3 getSkyColor(final Vec3 vec3, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.skyColors == null) {
            return vec3;
        }
        final int smoothColor = getSmoothColor(CustomColorizer.skyColors, blockAccess, n, n2, n3, 0x35 ^ 0x32, " ".length());
        return new Vec3((smoothColor >> (0x3F ^ 0x2F) & 208 + 190 - 156 + 13) / 255.0f * ((float)vec3.xCoord / 0.5f), (smoothColor >> (0x27 ^ 0x2F) & 88 + 190 - 165 + 142) / 255.0f * ((float)vec3.yCoord / 0.66275f), (smoothColor & 217 + 216 - 247 + 69) / 255.0f * (float)vec3.zCoord);
    }
    
    private static int getSmoothColorMultiplier(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final int[] array, final int[] array2, final int n, final int n2, final RenderEnv renderEnv) {
        int length = "".length();
        int length2 = "".length();
        int length3 = "".length();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final BlockPosM colorizerBlockPos = renderEnv.getColorizerBlockPos();
        int i = x - " ".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i <= x + " ".length()) {
            int j = z - " ".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (j <= z + " ".length()) {
                colorizerBlockPos.setXyz(i, y, j);
                int[] array3 = array;
                if (array2 != array && blockAccess.getBiomeGenForCoords(colorizerBlockPos) == BiomeGenBase.swampland) {
                    array3 = array2;
                }
                "".length();
                int n3 = 0;
                if (array3 == null) {
                    switch (n) {
                        case 1: {
                            n3 = blockAccess.getBiomeGenForCoords(colorizerBlockPos).getGrassColorAtPos(colorizerBlockPos);
                            "".length();
                            if (1 < 0) {
                                throw null;
                            }
                            break;
                        }
                        case 2: {
                            if ((n2 & "   ".length()) == " ".length()) {
                                n3 = ColorizerFoliage.getFoliageColorPine();
                                "".length();
                                if (4 < 4) {
                                    throw null;
                                }
                                break;
                            }
                            else if ((n2 & "   ".length()) == "  ".length()) {
                                n3 = ColorizerFoliage.getFoliageColorBirch();
                                "".length();
                                if (-1 >= 1) {
                                    throw null;
                                }
                                break;
                            }
                            else {
                                n3 = blockAccess.getBiomeGenForCoords(colorizerBlockPos).getFoliageColorAtPos(colorizerBlockPos);
                                "".length();
                                if (-1 >= 3) {
                                    throw null;
                                }
                                break;
                            }
                            break;
                        }
                        default: {
                            n3 = block.colorMultiplier(blockAccess, colorizerBlockPos);
                            "".length();
                            if (-1 >= 1) {
                                throw null;
                            }
                            break;
                        }
                    }
                }
                else {
                    n3 = getCustomColor(array3, blockAccess, colorizerBlockPos);
                }
                length += (n3 >> (0x5F ^ 0x4F) & 45 + 228 - 90 + 72);
                length2 += (n3 >> (0xAC ^ 0xA4) & 124 + 42 - 1 + 90);
                length3 += (n3 & 141 + 14 - 150 + 250);
                ++j;
            }
            ++i;
        }
        return length / (0x55 ^ 0x5C) << (0xA ^ 0x1A) | length2 / (0x18 ^ 0x11) << (0x7F ^ 0x77) | length3 / (0x12 ^ 0x1B);
    }
    
    public static Vec3 getSkyColorEnd(final Vec3 vec3) {
        Vec3 skyColorEnd;
        if (CustomColorizer.skyColorEnd == null) {
            skyColorEnd = vec3;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            skyColorEnd = CustomColorizer.skyColorEnd;
        }
        return skyColorEnd;
    }
    
    public static int mixColors(final int n, final int n2, final float n3) {
        if (n3 <= 0.0f) {
            return n2;
        }
        if (n3 >= 1.0f) {
            return n;
        }
        final float n4 = 1.0f - n3;
        return (int)((n >> (0x10 ^ 0x0) & 249 + 95 - 107 + 18) * n3 + (n2 >> (0x1E ^ 0xE) & 16 + 152 - 151 + 238) * n4) << (0x95 ^ 0x85) | (int)((n >> (0x7A ^ 0x72) & 189 + 98 - 150 + 118) * n3 + (n2 >> (0x25 ^ 0x2D) & 215 + 99 - 82 + 23) * n4) << (0x7D ^ 0x75) | (int)((n & 199 + 85 - 232 + 203) * n3 + (n2 & 76 + 82 - 51 + 148) * n4);
    }
}

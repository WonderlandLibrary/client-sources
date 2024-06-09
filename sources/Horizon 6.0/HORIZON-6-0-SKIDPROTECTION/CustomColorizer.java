package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class CustomColorizer
{
    private static int[] HorizonCode_Horizon_È;
    private static int[] Â;
    private static int[] Ý;
    private static int[] Ø­áŒŠá;
    private static int[] Âµá€;
    private static int[] Ó;
    private static int[] à;
    private static int[][] Ø;
    private static int[][] áŒŠÆ;
    private static int[] áˆºÑ¢Õ;
    private static int[] ÂµÈ;
    private static int[] á;
    private static float[][][] ˆÏ­;
    private static int[] £á;
    private static float[][] Å;
    private static float[][] £à;
    private static int[] µà;
    private static int[] ˆà;
    private static int[] ¥Æ;
    private static boolean Ø­à;
    private static int µÕ;
    private static int Æ;
    private static int Šáƒ;
    private static Vec3 Ï­Ðƒà;
    private static Vec3 áŒŠà;
    private static Vec3 ŠÄ;
    private static final int Ñ¢á = 0;
    private static final int ŒÏ = 1;
    private static final int Çªà¢ = 2;
    private static Random Ê;
    
    static {
        CustomColorizer.HorizonCode_Horizon_È = null;
        CustomColorizer.Â = null;
        CustomColorizer.Ý = null;
        CustomColorizer.Ø­áŒŠá = null;
        CustomColorizer.Âµá€ = null;
        CustomColorizer.Ó = null;
        CustomColorizer.à = null;
        CustomColorizer.Ø = null;
        CustomColorizer.áŒŠÆ = null;
        CustomColorizer.áˆºÑ¢Õ = null;
        CustomColorizer.ÂµÈ = null;
        CustomColorizer.á = null;
        CustomColorizer.ˆÏ­ = null;
        CustomColorizer.£á = null;
        CustomColorizer.Å = new float[16][3];
        CustomColorizer.£à = new float[16][3];
        CustomColorizer.µà = null;
        CustomColorizer.ˆà = null;
        CustomColorizer.¥Æ = null;
        CustomColorizer.Ø­à = true;
        CustomColorizer.µÕ = -1;
        CustomColorizer.Æ = -1;
        CustomColorizer.Šáƒ = -1;
        CustomColorizer.Ï­Ðƒà = null;
        CustomColorizer.áŒŠà = null;
        CustomColorizer.ŠÄ = null;
        CustomColorizer.Ê = new Random();
    }
    
    public static void HorizonCode_Horizon_È() {
        CustomColorizer.HorizonCode_Horizon_È = null;
        CustomColorizer.Â = null;
        CustomColorizer.Ý = null;
        CustomColorizer.Âµá€ = null;
        CustomColorizer.Ø­áŒŠá = null;
        CustomColorizer.à = null;
        CustomColorizer.Ó = null;
        CustomColorizer.áˆºÑ¢Õ = null;
        CustomColorizer.ÂµÈ = null;
        CustomColorizer.á = null;
        CustomColorizer.µà = null;
        CustomColorizer.ˆà = null;
        CustomColorizer.¥Æ = null;
        CustomColorizer.ˆÏ­ = null;
        CustomColorizer.£á = null;
        CustomColorizer.Šáƒ = -1;
        CustomColorizer.µÕ = -1;
        CustomColorizer.Æ = -1;
        CustomColorizer.Ï­Ðƒà = null;
        CustomColorizer.áŒŠà = null;
        CustomColorizer.ŠÄ = null;
        CustomColorizer.Ø = null;
        CustomColorizer.áŒŠÆ = null;
        CustomColorizer.Ø­à = true;
        final String mcpColormap = "mcpatcher/colormap/";
        CustomColorizer.HorizonCode_Horizon_È = Â("textures/colormap/grass.png", 65536);
        CustomColorizer.Ý = Â("textures/colormap/foliage.png", 65536);
        final String[] waterPaths = { "water.png", "watercolorX.png" };
        CustomColorizer.Â = HorizonCode_Horizon_È(mcpColormap, waterPaths, 65536);
        if (Config.áŒŠÏ()) {
            final String[] pinePaths = { "pine.png", "pinecolor.png" };
            CustomColorizer.Ø­áŒŠá = HorizonCode_Horizon_È(mcpColormap, pinePaths, 65536);
            final String[] birchPaths = { "birch.png", "birchcolor.png" };
            CustomColorizer.Âµá€ = HorizonCode_Horizon_È(mcpColormap, birchPaths, 65536);
            final String[] swampGrassPaths = { "swampgrass.png", "swampgrasscolor.png" };
            CustomColorizer.à = HorizonCode_Horizon_È(mcpColormap, swampGrassPaths, 65536);
            final String[] swampFoliagePaths = { "swampfoliage.png", "swampfoliagecolor.png" };
            CustomColorizer.Ó = HorizonCode_Horizon_È(mcpColormap, swampFoliagePaths, 65536);
            final String[] sky0Paths = { "sky0.png", "skycolor0.png" };
            CustomColorizer.áˆºÑ¢Õ = HorizonCode_Horizon_È(mcpColormap, sky0Paths, 65536);
            final String[] fog0Paths = { "fog0.png", "fogcolor0.png" };
            CustomColorizer.ÂµÈ = HorizonCode_Horizon_È(mcpColormap, fog0Paths, 65536);
            final String[] underwaterPaths = { "underwater.png", "underwatercolor.png" };
            CustomColorizer.á = HorizonCode_Horizon_È(mcpColormap, underwaterPaths, 65536);
            final String[] redstonePaths = { "redstone.png", "redstonecolor.png" };
            CustomColorizer.µà = HorizonCode_Horizon_È(mcpColormap, redstonePaths, 16);
            final String[] stemPaths = { "stem.png", "stemcolor.png" };
            CustomColorizer.ˆà = HorizonCode_Horizon_È(mcpColormap, stemPaths, 8);
            final String[] myceliumPaths = { "myceliumparticle.png", "myceliumparticlecolor.png" };
            CustomColorizer.¥Æ = HorizonCode_Horizon_È(mcpColormap, myceliumPaths, -1);
            final int[][] lightMapsColors = new int[3][];
            CustomColorizer.ˆÏ­ = new float[3][][];
            CustomColorizer.£á = new int[3];
            for (int i = 0; i < lightMapsColors.length; ++i) {
                final String path = "mcpatcher/lightmap/world" + (i - 1) + ".png";
                lightMapsColors[i] = Â(path, -1);
                if (lightMapsColors[i] != null) {
                    CustomColorizer.ˆÏ­[i] = HorizonCode_Horizon_È(lightMapsColors[i]);
                }
                CustomColorizer.£á[i] = HorizonCode_Horizon_È(path, 32);
            }
            HorizonCode_Horizon_È("mcpatcher/color.properties");
            Â();
        }
    }
    
    private static int HorizonCode_Horizon_È(final String path, final int defHeight) {
        try {
            final InputStream e = Config.HorizonCode_Horizon_È(new ResourceLocation_1975012498(path));
            if (e == null) {
                return defHeight;
            }
            final BufferedImage bi = ImageIO.read(e);
            return (bi == null) ? defHeight : bi.getHeight();
        }
        catch (IOException var4) {
            return defHeight;
        }
    }
    
    private static float[][] HorizonCode_Horizon_È(final int[] cols) {
        final float[][] colsRgb = new float[cols.length][3];
        for (int i = 0; i < cols.length; ++i) {
            final int col = cols[i];
            final float rf = (col >> 16 & 0xFF) / 255.0f;
            final float gf = (col >> 8 & 0xFF) / 255.0f;
            final float bf = (col & 0xFF) / 255.0f;
            final float[] colRgb = colsRgb[i];
            colRgb[0] = rf;
            colRgb[1] = gf;
            colRgb[2] = bf;
        }
        return colsRgb;
    }
    
    private static void HorizonCode_Horizon_È(final String fileName) {
        try {
            final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(fileName);
            final InputStream in = Config.HorizonCode_Horizon_È(e);
            if (in == null) {
                return;
            }
            Config.Ø­áŒŠá("Loading " + fileName);
            final Properties props = new Properties();
            props.load(in);
            CustomColorizer.Šáƒ = Â(props, "lilypad");
            CustomColorizer.µÕ = HorizonCode_Horizon_È(props, new String[] { "particle.water", "drop.water" });
            CustomColorizer.Æ = Â(props, "particle.portal");
            CustomColorizer.Ï­Ðƒà = Ý(props, "fog.nether");
            CustomColorizer.áŒŠà = Ý(props, "fog.end");
            CustomColorizer.ŠÄ = Ý(props, "sky.end");
            HorizonCode_Horizon_È(props, fileName);
        }
        catch (FileNotFoundException var6) {}
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }
    
    private static void HorizonCode_Horizon_È(final Properties props, final String fileName) {
        CustomColorizer.Ø = new int[256][1];
        for (int palettePrefix = 0; palettePrefix < 256; ++palettePrefix) {
            CustomColorizer.Ø[palettePrefix][0] = -1;
        }
        final String var18 = "palette.block.";
        final HashMap map = new HashMap();
        final Set keys = props.keySet();
        for (final String i : keys) {
            final String name = props.getProperty(i);
            if (i.startsWith(var18)) {
                map.put(i, name);
            }
        }
        final String[] var19 = (String[])map.keySet().toArray(new String[map.size()]);
        CustomColorizer.áŒŠÆ = new int[var19.length][];
        for (int var20 = 0; var20 < var19.length; ++var20) {
            final String name = var19[var20];
            final String value = props.getProperty(name);
            Config.Ø­áŒŠá("Block palette: " + name + " = " + value);
            String path = name.substring(var18.length());
            final String basePath = TextureUtils.Â(fileName);
            path = TextureUtils.HorizonCode_Horizon_È(path, basePath);
            final int[] colors = Â(path, 65536);
            CustomColorizer.áŒŠÆ[var20] = colors;
            final String[] indexStrs = Config.HorizonCode_Horizon_È(value, " ,;");
            for (int ix = 0; ix < indexStrs.length; ++ix) {
                String blockStr = indexStrs[ix];
                int metadata = -1;
                if (blockStr.contains(":")) {
                    final String[] blockIndex = Config.HorizonCode_Horizon_È(blockStr, ":");
                    blockStr = blockIndex[0];
                    final String metadataStr = blockIndex[1];
                    metadata = Config.HorizonCode_Horizon_È(metadataStr, -1);
                    if (metadata < 0 || metadata > 15) {
                        Config.Ø­áŒŠá("Invalid block metadata: " + blockStr + " in palette: " + name);
                        continue;
                    }
                }
                final int var21 = Config.HorizonCode_Horizon_È(blockStr, -1);
                if (var21 >= 0 && var21 <= 255) {
                    if (var21 != Block.HorizonCode_Horizon_È(Blocks.Ø­áŒŠá) && var21 != Block.HorizonCode_Horizon_È(Blocks.áƒ) && var21 != Block.HorizonCode_Horizon_È(Blocks.µÕ) && var21 != Block.HorizonCode_Horizon_È(Blocks.ÇŽà)) {
                        if (metadata == -1) {
                            CustomColorizer.Ø[var21][0] = var20;
                        }
                        else {
                            if (CustomColorizer.Ø[var21].length < 16) {
                                Arrays.fill(CustomColorizer.Ø[var21] = new int[16], -1);
                            }
                            CustomColorizer.Ø[var21][metadata] = var20;
                        }
                    }
                }
                else {
                    Config.Ø­áŒŠá("Invalid block index: " + var21 + " in palette: " + name);
                }
            }
        }
    }
    
    private static int HorizonCode_Horizon_È(final Properties props, final String[] names) {
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            final int col = Â(props, name);
            if (col >= 0) {
                return col;
            }
        }
        return -1;
    }
    
    private static int Â(final Properties props, final String name) {
        final String str = props.getProperty(name);
        if (str == null) {
            return -1;
        }
        try {
            final int e = Integer.parseInt(str, 16) & 0xFFFFFF;
            Config.Ø­áŒŠá("Custom color: " + name + " = " + str);
            return e;
        }
        catch (NumberFormatException var4) {
            Config.Ø­áŒŠá("Invalid custom color: " + name + " = " + str);
            return -1;
        }
    }
    
    private static Vec3 Ý(final Properties props, final String name) {
        final int col = Â(props, name);
        if (col < 0) {
            return null;
        }
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        return new Vec3(redF, greenF, blueF);
    }
    
    private static int[] HorizonCode_Horizon_È(final String basePath, final String[] paths, final int length) {
        for (int i = 0; i < paths.length; ++i) {
            String path = paths[i];
            path = String.valueOf(basePath) + path;
            final int[] cols = Â(path, length);
            if (cols != null) {
                return cols;
            }
        }
        return null;
    }
    
    private static int[] Â(final String path, final int length) {
        try {
            final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(path);
            final InputStream in = Config.HorizonCode_Horizon_È(e);
            if (in == null) {
                return null;
            }
            final int[] colors = TextureUtil.HorizonCode_Horizon_È(Config.ˆáŠ(), e);
            if (colors == null) {
                return null;
            }
            if (length > 0 && colors.length != length) {
                Config.Ø­áŒŠá("Invalid custom colors length: " + colors.length + ", path: " + path);
                return null;
            }
            Config.Ø­áŒŠá("Loading custom colors: " + path);
            return colors;
        }
        catch (FileNotFoundException var7) {
            return null;
        }
        catch (IOException var6) {
            var6.printStackTrace();
            return null;
        }
    }
    
    public static void Â() {
        CustomColorizer.Ø­à = (CustomColorizer.Âµá€ == null && CustomColorizer.Ø­áŒŠá == null && CustomColorizer.à == null && CustomColorizer.Ó == null && CustomColorizer.Ø == null && Config.ˆÏ() && Config.ÇªÂµÕ());
    }
    
    public static int HorizonCode_Horizon_È(final BakedQuad quad, final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final RenderEnv renderEnv) {
        if (CustomColorizer.Ø­à) {
            return -1;
        }
        int[] colors = null;
        int[] swampColors = null;
        if (CustomColorizer.Ø != null) {
            final int useSwampColors = renderEnv.HorizonCode_Horizon_È();
            if (useSwampColors >= 0 && useSwampColors < 256) {
                final int[] smoothColors = CustomColorizer.Ø[useSwampColors];
                final boolean type = true;
                int type2;
                if (smoothColors.length > 1) {
                    final int metadata = renderEnv.Â();
                    type2 = smoothColors[metadata];
                }
                else {
                    type2 = smoothColors[0];
                }
                if (type2 >= 0) {
                    colors = CustomColorizer.áŒŠÆ[type2];
                }
            }
            if (colors != null) {
                if (Config.ÇªÂµÕ()) {
                    return HorizonCode_Horizon_È(block, blockAccess, blockPos, colors, colors, 0, 0, renderEnv);
                }
                return HorizonCode_Horizon_È(colors, blockAccess, blockPos);
            }
        }
        if (!quad.Ý()) {
            return -1;
        }
        if (block == Blocks.Œá) {
            return HorizonCode_Horizon_È(blockAccess, blockPos);
        }
        if (block instanceof BlockStem) {
            return HorizonCode_Horizon_È(block, blockAccess, blockPos, renderEnv);
        }
        final boolean useSwampColors2 = Config.ˆÏ();
        boolean smoothColors2 = false;
        byte type3 = 0;
        int metadata = 0;
        if (block != Blocks.Ø­áŒŠá && block != Blocks.áƒ) {
            if (block == Blocks.µÕ) {
                type3 = 2;
                smoothColors2 = Config.ÇªÂµÕ();
                metadata = renderEnv.Â();
                if ((metadata & 0x3) == 0x1) {
                    colors = CustomColorizer.Ø­áŒŠá;
                }
                else if ((metadata & 0x3) == 0x2) {
                    colors = CustomColorizer.Âµá€;
                }
                else {
                    colors = CustomColorizer.Ý;
                    if (useSwampColors2) {
                        swampColors = CustomColorizer.Ó;
                    }
                    else {
                        swampColors = colors;
                    }
                }
            }
            else if (block == Blocks.ÇŽà) {
                type3 = 2;
                smoothColors2 = Config.ÇªÂµÕ();
                colors = CustomColorizer.Ý;
                if (useSwampColors2) {
                    swampColors = CustomColorizer.Ó;
                }
                else {
                    swampColors = colors;
                }
            }
        }
        else {
            type3 = 1;
            smoothColors2 = Config.ÇªÂµÕ();
            colors = CustomColorizer.HorizonCode_Horizon_È;
            if (useSwampColors2) {
                swampColors = CustomColorizer.à;
            }
            else {
                swampColors = colors;
            }
        }
        if (smoothColors2) {
            return HorizonCode_Horizon_È(block, blockAccess, blockPos, colors, swampColors, type3, metadata, renderEnv);
        }
        if (swampColors != colors && blockAccess.Ý(blockPos) == BiomeGenBase.Æ) {
            colors = swampColors;
        }
        return (colors != null) ? HorizonCode_Horizon_È(colors, blockAccess, blockPos) : -1;
    }
    
    private static int HorizonCode_Horizon_È(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final int[] colors, final int[] swampColors, final int type, final int metadata, final RenderEnv renderEnv) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        final int x = blockPos.HorizonCode_Horizon_È();
        final int y = blockPos.Â();
        final int z = blockPos.Ý();
        final BlockPosM posM = renderEnv.Ø();
        for (int r = x - 1; r <= x + 1; ++r) {
            for (int g = z - 1; g <= z + 1; ++g) {
                posM.HorizonCode_Horizon_È(r, y, g);
                int[] b = colors;
                if (swampColors != colors && blockAccess.Ý(posM) == BiomeGenBase.Æ) {
                    b = swampColors;
                }
                final boolean col = false;
                int var20 = 0;
                if (b == null) {
                    switch (type) {
                        case 1: {
                            var20 = blockAccess.Ý(posM).Â(posM);
                            break;
                        }
                        case 2: {
                            if ((metadata & 0x3) == 0x1) {
                                var20 = ColorizerFoliage.HorizonCode_Horizon_È();
                                break;
                            }
                            if ((metadata & 0x3) == 0x2) {
                                var20 = ColorizerFoliage.Â();
                                break;
                            }
                            var20 = blockAccess.Ý(posM).Ý(posM);
                            break;
                        }
                        default: {
                            var20 = block.Ø­áŒŠá(blockAccess, posM);
                            break;
                        }
                    }
                }
                else {
                    var20 = HorizonCode_Horizon_È(b, blockAccess, posM);
                }
                sumRed += (var20 >> 16 & 0xFF);
                sumGreen += (var20 >> 8 & 0xFF);
                sumBlue += (var20 & 0xFF);
            }
        }
        int r = sumRed / 9;
        int g = sumGreen / 9;
        final int var21 = sumBlue / 9;
        return r << 16 | g << 8 | var21;
    }
    
    public static int HorizonCode_Horizon_È(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (block.Ó() != Material.Ø) ? block.Ø­áŒŠá(blockAccess, blockPos) : ((CustomColorizer.Â != null) ? (Config.ÇªÂµÕ() ? HorizonCode_Horizon_È(CustomColorizer.Â, blockAccess, blockPos.HorizonCode_Horizon_È(), blockPos.Â(), blockPos.Ý(), 3, 1) : HorizonCode_Horizon_È(CustomColorizer.Â, blockAccess, blockPos)) : (Config.ˆÏ() ? block.Ø­áŒŠá(blockAccess, blockPos) : 16777215));
    }
    
    private static int HorizonCode_Horizon_È(final int[] colors, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final BiomeGenBase bgb = blockAccess.Ý(blockPos);
        final double temperature = MathHelper.HorizonCode_Horizon_È(bgb.HorizonCode_Horizon_È(blockPos), 0.0f, 1.0f);
        double rainfall = MathHelper.HorizonCode_Horizon_È(bgb.áŒŠÆ(), 0.0f, 1.0f);
        rainfall *= temperature;
        final int cx = (int)((1.0 - temperature) * 255.0);
        final int cy = (int)((1.0 - rainfall) * 255.0);
        return colors[cy << 8 | cx] & 0xFFFFFF;
    }
    
    public static void HorizonCode_Horizon_È(final EntityFX fx) {
        if (CustomColorizer.Æ >= 0) {
            final int col = CustomColorizer.Æ;
            final int red = col >> 16 & 0xFF;
            final int green = col >> 8 & 0xFF;
            final int blue = col & 0xFF;
            final float redF = red / 255.0f;
            final float greenF = green / 255.0f;
            final float blueF = blue / 255.0f;
            fx.HorizonCode_Horizon_È(redF, greenF, blueF);
        }
    }
    
    public static void Â(final EntityFX fx) {
        if (CustomColorizer.¥Æ != null) {
            final int col = CustomColorizer.¥Æ[CustomColorizer.Ê.nextInt(CustomColorizer.¥Æ.length)];
            final int red = col >> 16 & 0xFF;
            final int green = col >> 8 & 0xFF;
            final int blue = col & 0xFF;
            final float redF = red / 255.0f;
            final float greenF = green / 255.0f;
            final float blueF = blue / 255.0f;
            fx.HorizonCode_Horizon_È(redF, greenF, blueF);
        }
    }
    
    public static void HorizonCode_Horizon_È(final EntityFX fx, final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.µà != null) {
            final IBlockState state = blockAccess.Â(new BlockPos(x, y, z));
            final int level = HorizonCode_Horizon_È(state, 15);
            final int col = HorizonCode_Horizon_È(level);
            if (col != -1) {
                final int red = col >> 16 & 0xFF;
                final int green = col >> 8 & 0xFF;
                final int blue = col & 0xFF;
                final float redF = red / 255.0f;
                final float greenF = green / 255.0f;
                final float blueF = blue / 255.0f;
                fx.HorizonCode_Horizon_È(redF, greenF, blueF);
            }
        }
    }
    
    private static int HorizonCode_Horizon_È(final IBlockState state, final int def) {
        final Block block = state.Ý();
        if (!(block instanceof BlockRedstoneWire)) {
            return def;
        }
        final Comparable val = state.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà);
        if (!(val instanceof Integer)) {
            return def;
        }
        final Integer valInt = (Integer)val;
        return valInt;
    }
    
    public static int HorizonCode_Horizon_È(final int level) {
        return (CustomColorizer.µà == null) ? -1 : ((level >= 0 && level <= 15) ? (CustomColorizer.µà[level] & 0xFFFFFF) : -1);
    }
    
    public static void Â(final EntityFX fx, final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.Â != null) {
            final int col = HorizonCode_Horizon_È(Blocks.ÂµÈ, blockAccess, new BlockPos(x, y, z));
            final int red = col >> 16 & 0xFF;
            final int green = col >> 8 & 0xFF;
            final int blue = col & 0xFF;
            float redF = red / 255.0f;
            float greenF = green / 255.0f;
            float blueF = blue / 255.0f;
            if (CustomColorizer.µÕ >= 0) {
                final int redDrop = CustomColorizer.µÕ >> 16 & 0xFF;
                final int greenDrop = CustomColorizer.µÕ >> 8 & 0xFF;
                final int blueDrop = CustomColorizer.µÕ & 0xFF;
                redF *= redDrop / 255.0f;
                greenF *= greenDrop / 255.0f;
                blueF *= blueDrop / 255.0f;
            }
            fx.HorizonCode_Horizon_È(redF, greenF, blueF);
        }
    }
    
    public static int HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (CustomColorizer.Šáƒ < 0) ? Blocks.Œá.Ø­áŒŠá(blockAccess, blockPos) : CustomColorizer.Šáƒ;
    }
    
    public static Vec3 HorizonCode_Horizon_È(final Vec3 col) {
        return (CustomColorizer.Ï­Ðƒà == null) ? col : CustomColorizer.Ï­Ðƒà;
    }
    
    public static Vec3 Â(final Vec3 col) {
        return (CustomColorizer.áŒŠà == null) ? col : CustomColorizer.áŒŠà;
    }
    
    public static Vec3 Ý(final Vec3 col) {
        return (CustomColorizer.ŠÄ == null) ? col : CustomColorizer.ŠÄ;
    }
    
    public static Vec3 HorizonCode_Horizon_È(final Vec3 skyColor3d, final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.áˆºÑ¢Õ == null) {
            return skyColor3d;
        }
        final int col = HorizonCode_Horizon_È(CustomColorizer.áˆºÑ¢Õ, blockAccess, x, y, z, 7, 1);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        float redF = red / 255.0f;
        float greenF = green / 255.0f;
        float blueF = blue / 255.0f;
        final float cRed = (float)skyColor3d.HorizonCode_Horizon_È / 0.5f;
        final float cGreen = (float)skyColor3d.Â / 0.66275f;
        final float cBlue = (float)skyColor3d.Ý;
        redF *= cRed;
        greenF *= cGreen;
        blueF *= cBlue;
        return new Vec3(redF, greenF, blueF);
    }
    
    public static Vec3 Â(final Vec3 fogColor3d, final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.ÂµÈ == null) {
            return fogColor3d;
        }
        final int col = HorizonCode_Horizon_È(CustomColorizer.ÂµÈ, blockAccess, x, y, z, 7, 1);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        float redF = red / 255.0f;
        float greenF = green / 255.0f;
        float blueF = blue / 255.0f;
        final float cRed = (float)fogColor3d.HorizonCode_Horizon_È / 0.753f;
        final float cGreen = (float)fogColor3d.Â / 0.8471f;
        final float cBlue = (float)fogColor3d.Ý;
        redF *= cRed;
        greenF *= cGreen;
        blueF *= cBlue;
        return new Vec3(redF, greenF, blueF);
    }
    
    public static Vec3 HorizonCode_Horizon_È(final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.á == null) {
            return null;
        }
        final int col = HorizonCode_Horizon_È(CustomColorizer.á, blockAccess, x, y, z, 7, 1);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        return new Vec3(redF, greenF, blueF);
    }
    
    public static int HorizonCode_Horizon_È(final int[] colors, final IBlockAccess blockAccess, final double x, final double y, final double z, final int samples, final int step) {
        if (colors == null) {
            return -1;
        }
        final int x2 = MathHelper.Ý(x);
        final int y2 = MathHelper.Ý(y);
        final int z2 = MathHelper.Ý(z);
        final int n = samples * step / 2;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = 0;
        final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        for (int r = x2 - n; r <= x2 + n; r += step) {
            for (int g = z2 - n; g <= z2 + n; g += step) {
                blockPosM.HorizonCode_Horizon_È(r, y2, g);
                final int b = HorizonCode_Horizon_È(colors, blockAccess, blockPosM);
                sumRed += (b >> 16 & 0xFF);
                sumGreen += (b >> 8 & 0xFF);
                sumBlue += (b & 0xFF);
                ++count;
            }
        }
        int r = sumRed / count;
        int g = sumGreen / count;
        final int b = sumBlue / count;
        return r << 16 | g << 8 | b;
    }
    
    public static int HorizonCode_Horizon_È(final int c1, final int c2, final float w1) {
        if (w1 <= 0.0f) {
            return c2;
        }
        if (w1 >= 1.0f) {
            return c1;
        }
        final float w2 = 1.0f - w1;
        final int r1 = c1 >> 16 & 0xFF;
        final int g1 = c1 >> 8 & 0xFF;
        final int b1 = c1 & 0xFF;
        final int r2 = c2 >> 16 & 0xFF;
        final int g2 = c2 >> 8 & 0xFF;
        final int b2 = c2 & 0xFF;
        final int r3 = (int)(r1 * w1 + r2 * w2);
        final int g3 = (int)(g1 * w1 + g2 * w2);
        final int b3 = (int)(b1 * w1 + b2 * w2);
        return r3 << 16 | g3 << 8 | b3;
    }
    
    private static int HorizonCode_Horizon_È(final int c1, final int c2) {
        final int r1 = c1 >> 16 & 0xFF;
        final int g1 = c1 >> 8 & 0xFF;
        final int b1 = c1 & 0xFF;
        final int r2 = c2 >> 16 & 0xFF;
        final int g2 = c2 >> 8 & 0xFF;
        final int b2 = c2 & 0xFF;
        final int r3 = (r1 + r2) / 2;
        final int g3 = (g1 + g2) / 2;
        final int b3 = (b1 + b2) / 2;
        return r3 << 16 | g3 << 8 | b3;
    }
    
    public static int HorizonCode_Horizon_È(final Block blockStem, final IBlockAccess blockAccess, final BlockPos blockPos, final RenderEnv renderEnv) {
        if (CustomColorizer.ˆà == null) {
            return blockStem.Ø­áŒŠá(blockAccess, blockPos);
        }
        int level = renderEnv.Â();
        if (level < 0) {
            level = 0;
        }
        if (level >= CustomColorizer.ˆà.length) {
            level = CustomColorizer.ˆà.length - 1;
        }
        return CustomColorizer.ˆà[level];
    }
    
    public static boolean HorizonCode_Horizon_È(final World world, final float torchFlickerX, final int[] lmColors, final boolean nightvision) {
        if (world == null) {
            return false;
        }
        if (CustomColorizer.ˆÏ­ == null) {
            return false;
        }
        if (!Config.áŒŠÏ()) {
            return false;
        }
        final int worldType = world.£à.µà();
        if (worldType < -1 || worldType > 1) {
            return false;
        }
        final int lightMapIndex = worldType + 1;
        final float[][] lightMapRgb = CustomColorizer.ˆÏ­[lightMapIndex];
        if (lightMapRgb == null) {
            return false;
        }
        final int height = CustomColorizer.£á[lightMapIndex];
        if (nightvision && height < 64) {
            return false;
        }
        final int width = lightMapRgb.length / height;
        if (width < 16) {
            Config.Â("Invalid lightmap width: " + width + " for: /environment/lightmap" + worldType + ".png");
            CustomColorizer.ˆÏ­[lightMapIndex] = null;
            return false;
        }
        int startIndex = 0;
        if (nightvision) {
            startIndex = width * 16 * 2;
        }
        float sun = 1.1666666f * (world.Â(1.0f) - 0.2f);
        if (world.Âµà() > 0) {
            sun = 1.0f;
        }
        sun = Config.HorizonCode_Horizon_È(sun);
        final float sunX = sun * (width - 1);
        final float torchX = Config.HorizonCode_Horizon_È(torchFlickerX + 0.5f) * (width - 1);
        final float gamma = Config.HorizonCode_Horizon_È(Config.ÇªØ­().ŠÏ);
        final boolean hasGamma = gamma > 1.0E-4f;
        HorizonCode_Horizon_È(lightMapRgb, sunX, startIndex, width, CustomColorizer.Å);
        HorizonCode_Horizon_È(lightMapRgb, torchX, startIndex + 16 * width, width, CustomColorizer.£à);
        final float[] rgb = new float[3];
        for (int is = 0; is < 16; ++is) {
            for (int it = 0; it < 16; ++it) {
                for (int r = 0; r < 3; ++r) {
                    float g = Config.HorizonCode_Horizon_È(CustomColorizer.Å[is][r] + CustomColorizer.£à[it][r]);
                    if (hasGamma) {
                        float b = 1.0f - g;
                        b = 1.0f - b * b * b * b;
                        g = gamma * b + (1.0f - gamma) * g;
                    }
                    rgb[r] = g;
                }
                int r = (int)(rgb[0] * 255.0f);
                final int var21 = (int)(rgb[1] * 255.0f);
                final int var22 = (int)(rgb[2] * 255.0f);
                lmColors[is * 16 + it] = (0xFF000000 | r << 16 | var21 << 8 | var22);
            }
        }
        return true;
    }
    
    private static void HorizonCode_Horizon_È(final float[][] origMap, final float x, final int offset, final int width, final float[][] colRgb) {
        final int xLow = (int)Math.floor(x);
        final int xHigh = (int)Math.ceil(x);
        if (xLow == xHigh) {
            for (int var14 = 0; var14 < 16; ++var14) {
                final float[] var15 = origMap[offset + var14 * width + xLow];
                final float[] var16 = colRgb[var14];
                for (int var17 = 0; var17 < 3; ++var17) {
                    var16[var17] = var15[var17];
                }
            }
        }
        else {
            final float dLow = 1.0f - (x - xLow);
            final float dHigh = 1.0f - (xHigh - x);
            for (int y = 0; y < 16; ++y) {
                final float[] rgbLow = origMap[offset + y * width + xLow];
                final float[] rgbHigh = origMap[offset + y * width + xHigh];
                final float[] rgb = colRgb[y];
                for (int i = 0; i < 3; ++i) {
                    rgb[i] = rgbLow[i] * dLow + rgbHigh[i] * dHigh;
                }
            }
        }
    }
    
    public static Vec3 HorizonCode_Horizon_È(Vec3 fogVec, final WorldClient world, final Entity renderViewEntity, final float partialTicks) {
        final int worldType = world.£à.µà();
        switch (worldType) {
            case -1: {
                fogVec = HorizonCode_Horizon_È(fogVec);
                break;
            }
            case 0: {
                final Minecraft mc = Minecraft.áŒŠà();
                fogVec = Â(fogVec, mc.áŒŠÆ, renderViewEntity.ŒÏ, renderViewEntity.Çªà¢ + 1.0, renderViewEntity.Ê);
                break;
            }
            case 1: {
                fogVec = Â(fogVec);
                break;
            }
        }
        return fogVec;
    }
    
    public static Vec3 Â(Vec3 skyVec, final WorldClient world, final Entity renderViewEntity, final float partialTicks) {
        final int worldType = world.£à.µà();
        switch (worldType) {
            case 0: {
                final Minecraft mc = Minecraft.áŒŠà();
                skyVec = HorizonCode_Horizon_È(skyVec, mc.áŒŠÆ, renderViewEntity.ŒÏ, renderViewEntity.Çªà¢ + 1.0, renderViewEntity.Ê);
                break;
            }
            case 1: {
                skyVec = Ý(skyVec);
                break;
            }
        }
        return skyVec;
    }
}

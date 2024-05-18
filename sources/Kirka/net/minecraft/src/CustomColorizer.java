/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.src.BlockPosM;
import net.minecraft.src.Config;
import net.minecraft.src.RenderEnv;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public class CustomColorizer {
    private static int[] grassColors = null;
    private static int[] waterColors = null;
    private static int[] foliageColors = null;
    private static int[] foliagePineColors = null;
    private static int[] foliageBirchColors = null;
    private static int[] swampFoliageColors = null;
    private static int[] swampGrassColors = null;
    private static int[][] blockPalettes = null;
    private static int[][] paletteColors = null;
    private static int[] skyColors = null;
    private static int[] fogColors = null;
    private static int[] underwaterColors = null;
    private static float[][][] lightMapsColorsRgb = null;
    private static int[] lightMapsHeight = null;
    private static float[][] sunRgbs = new float[16][3];
    private static float[][] torchRgbs = new float[16][3];
    private static int[] redstoneColors = null;
    private static int[] stemColors = null;
    private static int[] myceliumParticleColors = null;
    private static boolean useDefaultColorMultiplier = true;
    private static int particleWaterColor = -1;
    private static int particlePortalColor = -1;
    private static int lilyPadColor = -1;
    private static Vec3 fogColorNether = null;
    private static Vec3 fogColorEnd = null;
    private static Vec3 skyColorEnd = null;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    private static Random random = new Random();

    public static void update() {
        grassColors = null;
        waterColors = null;
        foliageColors = null;
        foliageBirchColors = null;
        foliagePineColors = null;
        swampGrassColors = null;
        swampFoliageColors = null;
        skyColors = null;
        fogColors = null;
        underwaterColors = null;
        redstoneColors = null;
        stemColors = null;
        myceliumParticleColors = null;
        lightMapsColorsRgb = null;
        lightMapsHeight = null;
        lilyPadColor = -1;
        particleWaterColor = -1;
        particlePortalColor = -1;
        fogColorNether = null;
        fogColorEnd = null;
        skyColorEnd = null;
        blockPalettes = null;
        paletteColors = null;
        useDefaultColorMultiplier = true;
        String mcpColormap = "mcpatcher/colormap/";
        grassColors = CustomColorizer.getCustomColors("textures/colormap/grass.png", 65536);
        foliageColors = CustomColorizer.getCustomColors("textures/colormap/foliage.png", 65536);
        String[] waterPaths = new String[]{"water.png", "watercolorX.png"};
        waterColors = CustomColorizer.getCustomColors(mcpColormap, waterPaths, 65536);
        if (Config.isCustomColors()) {
            String[] pinePaths = new String[]{"pine.png", "pinecolor.png"};
            foliagePineColors = CustomColorizer.getCustomColors(mcpColormap, pinePaths, 65536);
            String[] birchPaths = new String[]{"birch.png", "birchcolor.png"};
            foliageBirchColors = CustomColorizer.getCustomColors(mcpColormap, birchPaths, 65536);
            String[] swampGrassPaths = new String[]{"swampgrass.png", "swampgrasscolor.png"};
            swampGrassColors = CustomColorizer.getCustomColors(mcpColormap, swampGrassPaths, 65536);
            String[] swampFoliagePaths = new String[]{"swampfoliage.png", "swampfoliagecolor.png"};
            swampFoliageColors = CustomColorizer.getCustomColors(mcpColormap, swampFoliagePaths, 65536);
            String[] sky0Paths = new String[]{"sky0.png", "skycolor0.png"};
            skyColors = CustomColorizer.getCustomColors(mcpColormap, sky0Paths, 65536);
            String[] fog0Paths = new String[]{"fog0.png", "fogcolor0.png"};
            fogColors = CustomColorizer.getCustomColors(mcpColormap, fog0Paths, 65536);
            String[] underwaterPaths = new String[]{"underwater.png", "underwatercolor.png"};
            underwaterColors = CustomColorizer.getCustomColors(mcpColormap, underwaterPaths, 65536);
            String[] redstonePaths = new String[]{"redstone.png", "redstonecolor.png"};
            redstoneColors = CustomColorizer.getCustomColors(mcpColormap, redstonePaths, 16);
            String[] stemPaths = new String[]{"stem.png", "stemcolor.png"};
            stemColors = CustomColorizer.getCustomColors(mcpColormap, stemPaths, 8);
            String[] myceliumPaths = new String[]{"myceliumparticle.png", "myceliumparticlecolor.png"};
            myceliumParticleColors = CustomColorizer.getCustomColors(mcpColormap, myceliumPaths, -1);
            int[][] lightMapsColors = new int[3][];
            lightMapsColorsRgb = new float[3][][];
            lightMapsHeight = new int[3];
            for (int i = 0; i < lightMapsColors.length; ++i) {
                String path = "mcpatcher/lightmap/world" + (i - 1) + ".png";
                lightMapsColors[i] = CustomColorizer.getCustomColors(path, -1);
                if (lightMapsColors[i] != null) {
                    CustomColorizer.lightMapsColorsRgb[i] = CustomColorizer.toRgb(lightMapsColors[i]);
                }
                CustomColorizer.lightMapsHeight[i] = CustomColorizer.getTextureHeight(path, 32);
            }
            CustomColorizer.readColorProperties("mcpatcher/color.properties");
            CustomColorizer.updateUseDefaultColorMultiplier();
        }
    }

    private static int getTextureHeight(String path, int defHeight) {
        try {
            InputStream e = Config.getResourceStream(new ResourceLocation(path));
            if (e == null) {
                return defHeight;
            }
            BufferedImage bi = ImageIO.read(e);
            return bi == null ? defHeight : bi.getHeight();
        }
        catch (IOException var4) {
            return defHeight;
        }
    }

    private static float[][] toRgb(int[] cols) {
        float[][] colsRgb = new float[cols.length][3];
        for (int i = 0; i < cols.length; ++i) {
            int col = cols[i];
            float rf = (float)(col >> 16 & 255) / 255.0f;
            float gf = (float)(col >> 8 & 255) / 255.0f;
            float bf = (float)(col & 255) / 255.0f;
            float[] colRgb = colsRgb[i];
            colRgb[0] = rf;
            colRgb[1] = gf;
            colRgb[2] = bf;
        }
        return colsRgb;
    }

    private static void readColorProperties(String fileName) {
        try {
            ResourceLocation e = new ResourceLocation(fileName);
            InputStream in = Config.getResourceStream(e);
            if (in == null) {
                return;
            }
            Config.log("Loading " + fileName);
            Properties props = new Properties();
            props.load(in);
            lilyPadColor = CustomColorizer.readColor(props, "lilypad");
            particleWaterColor = CustomColorizer.readColor(props, new String[]{"particle.water", "drop.water"});
            particlePortalColor = CustomColorizer.readColor(props, "particle.portal");
            fogColorNether = CustomColorizer.readColorVec3(props, "fog.nether");
            fogColorEnd = CustomColorizer.readColorVec3(props, "fog.end");
            skyColorEnd = CustomColorizer.readColorVec3(props, "sky.end");
            CustomColorizer.readCustomPalettes(props, fileName);
        }
        catch (FileNotFoundException var4) {
            return;
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    private static void readCustomPalettes(Properties props, String fileName) {
        String name;
        blockPalettes = new int[256][1];
        for (int palettePrefix = 0; palettePrefix < 256; ++palettePrefix) {
            CustomColorizer.blockPalettes[palettePrefix][0] = -1;
        }
        String var18 = "palette.block.";
        HashMap<String, String> map = new HashMap<String, String>();
        Set<Object> keys = props.keySet();
        for (String i : keys) {
            name = props.getProperty(i);
            if (!i.startsWith(var18)) continue;
            map.put(i, name);
        }
        String[] var19 = map.keySet().toArray(new String[map.size()]);
        paletteColors = new int[var19.length][];
        for (int var20 = 0; var20 < var19.length; ++var20) {
            name = var19[var20];
            String value = props.getProperty(name);
            Config.log("Block palette: " + name + " = " + value);
            String path = name.substring(var18.length());
            String basePath = TextureUtils.getBasePath(fileName);
            path = TextureUtils.fixResourcePath(path, basePath);
            int[] colors = CustomColorizer.getCustomColors(path, 65536);
            CustomColorizer.paletteColors[var20] = colors;
            String[] indexStrs = Config.tokenize(value, " ,;");
            for (int ix = 0; ix < indexStrs.length; ++ix) {
                int var21;
                String blockStr = indexStrs[ix];
                int metadata = -1;
                if (blockStr.contains(":")) {
                    String[] blockIndex = Config.tokenize(blockStr, ":");
                    blockStr = blockIndex[0];
                    String metadataStr = blockIndex[1];
                    metadata = Config.parseInt(metadataStr, -1);
                    if (metadata < 0 || metadata > 15) {
                        Config.log("Invalid block metadata: " + blockStr + " in palette: " + name);
                        continue;
                    }
                }
                if ((var21 = Config.parseInt(blockStr, -1)) >= 0 && var21 <= 255) {
                    if (var21 == Block.getIdFromBlock(Blocks.grass) || var21 == Block.getIdFromBlock(Blocks.tallgrass) || var21 == Block.getIdFromBlock(Blocks.leaves) || var21 == Block.getIdFromBlock(Blocks.vine)) continue;
                    if (metadata == -1) {
                        CustomColorizer.blockPalettes[var21][0] = var20;
                        continue;
                    }
                    if (blockPalettes[var21].length < 16) {
                        CustomColorizer.blockPalettes[var21] = new int[16];
                        Arrays.fill(blockPalettes[var21], -1);
                    }
                    CustomColorizer.blockPalettes[var21][metadata] = var20;
                    continue;
                }
                Config.log("Invalid block index: " + var21 + " in palette: " + name);
            }
        }
    }

    private static int readColor(Properties props, String[] names) {
        for (int i = 0; i < names.length; ++i) {
            String name = names[i];
            int col = CustomColorizer.readColor(props, name);
            if (col < 0) continue;
            return col;
        }
        return -1;
    }

    private static int readColor(Properties props, String name) {
        String str = props.getProperty(name);
        if (str == null) {
            return -1;
        }
        try {
            int e = Integer.parseInt(str, 16) & 16777215;
            Config.log("Custom color: " + name + " = " + str);
            return e;
        }
        catch (NumberFormatException var4) {
            Config.log("Invalid custom color: " + name + " = " + str);
            return -1;
        }
    }

    private static Vec3 readColorVec3(Properties props, String name) {
        int col = CustomColorizer.readColor(props, name);
        if (col < 0) {
            return null;
        }
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        return new Vec3(redF, greenF, blueF);
    }

    private static int[] getCustomColors(String basePath, String[] paths, int length) {
        for (int i = 0; i < paths.length; ++i) {
            String path = paths[i];
            path = String.valueOf(basePath) + path;
            int[] cols = CustomColorizer.getCustomColors(path, length);
            if (cols == null) continue;
            return cols;
        }
        return null;
    }

    private static int[] getCustomColors(String path, int length) {
        int[] colors;
        block8 : {
            block7 : {
                ResourceLocation e;
                block6 : {
                    e = new ResourceLocation(path);
                    InputStream in = Config.getResourceStream(e);
                    if (in != null) break block6;
                    return null;
                }
                colors = TextureUtil.readImageData(Config.getResourceManager(), e);
                if (colors != null) break block7;
                return null;
            }
            if (length <= 0 || colors.length == length) break block8;
            Config.log("Invalid custom colors length: " + colors.length + ", path: " + path);
            return null;
        }
        try {
            Config.log("Loading custom colors: " + path);
            return colors;
        }
        catch (FileNotFoundException var5) {
            return null;
        }
        catch (IOException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static void updateUseDefaultColorMultiplier() {
        useDefaultColorMultiplier = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes();
    }

    public static int getColorMultiplier(BakedQuad quad, Block block, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
        int metadata;
        if (useDefaultColorMultiplier) {
            return -1;
        }
        int[] colors = null;
        int[] swampColors = null;
        if (blockPalettes != null) {
            int useSwampColors = renderEnv.getBlockId();
            if (useSwampColors >= 0 && useSwampColors < 256) {
                int type1;
                int[] smoothColors = blockPalettes[useSwampColors];
                boolean type = true;
                if (smoothColors.length > 1) {
                    metadata = renderEnv.getMetadata();
                    type1 = smoothColors[metadata];
                } else {
                    type1 = smoothColors[0];
                }
                if (type1 >= 0) {
                    colors = paletteColors[type1];
                }
            }
            if (colors != null) {
                if (Config.isSmoothBiomes()) {
                    return CustomColorizer.getSmoothColorMultiplier(block, blockAccess, blockPos, colors, colors, 0, 0, renderEnv);
                }
                return CustomColorizer.getCustomColor(colors, blockAccess, blockPos);
            }
        }
        if (!quad.func_178212_b()) {
            return -1;
        }
        if (block == Blocks.waterlily) {
            return CustomColorizer.getLilypadColorMultiplier(blockAccess, blockPos);
        }
        if (block instanceof BlockStem) {
            return CustomColorizer.getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
        }
        boolean useSwampColors1 = Config.isSwampColors();
        boolean smoothColors1 = false;
        int type2 = 0;
        metadata = 0;
        if (block != Blocks.grass && block != Blocks.tallgrass) {
            if (block == Blocks.leaves) {
                type2 = 2;
                smoothColors1 = Config.isSmoothBiomes();
                metadata = renderEnv.getMetadata();
                if ((metadata & 3) == 1) {
                    colors = foliagePineColors;
                } else if ((metadata & 3) == 2) {
                    colors = foliageBirchColors;
                } else {
                    colors = foliageColors;
                    swampColors = useSwampColors1 ? swampFoliageColors : colors;
                }
            } else if (block == Blocks.vine) {
                type2 = 2;
                smoothColors1 = Config.isSmoothBiomes();
                colors = foliageColors;
                swampColors = useSwampColors1 ? swampFoliageColors : colors;
            }
        } else {
            type2 = 1;
            smoothColors1 = Config.isSmoothBiomes();
            colors = grassColors;
            swampColors = useSwampColors1 ? swampGrassColors : colors;
        }
        if (smoothColors1) {
            return CustomColorizer.getSmoothColorMultiplier(block, blockAccess, blockPos, colors, swampColors, type2, metadata, renderEnv);
        }
        if (swampColors != colors && blockAccess.getBiomeGenForCoords(blockPos) == BiomeGenBase.swampland) {
            colors = swampColors;
        }
        return colors != null ? CustomColorizer.getCustomColor(colors, blockAccess, blockPos) : -1;
    }

    private static int getSmoothColorMultiplier(Block block, IBlockAccess blockAccess, BlockPos blockPos, int[] colors, int[] swampColors, int type, int metadata, RenderEnv renderEnv) {
        int g;
        int r;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        BlockPosM posM = renderEnv.getColorizerBlockPos();
        for (r = x - 1; r <= x + 1; ++r) {
            for (g = z - 1; g <= z + 1; ++g) {
                int var20;
                posM.setXyz(r, y, g);
                int[] b = colors;
                if (swampColors != colors && blockAccess.getBiomeGenForCoords(posM) == BiomeGenBase.swampland) {
                    b = swampColors;
                }
                boolean col = false;
                if (b == null) {
                    switch (type) {
                        case 1: {
                            var20 = blockAccess.getBiomeGenForCoords(posM).func_180627_b(posM);
                            break;
                        }
                        case 2: {
                            if ((metadata & 3) == 1) {
                                var20 = ColorizerFoliage.getFoliageColorPine();
                                break;
                            }
                            if ((metadata & 3) == 2) {
                                var20 = ColorizerFoliage.getFoliageColorBirch();
                                break;
                            }
                            var20 = blockAccess.getBiomeGenForCoords(posM).func_180625_c(posM);
                            break;
                        }
                        default: {
                            var20 = block.colorMultiplier(blockAccess, posM);
                            break;
                        }
                    }
                } else {
                    var20 = CustomColorizer.getCustomColor(b, blockAccess, posM);
                }
                sumRed += var20 >> 16 & 255;
                sumGreen += var20 >> 8 & 255;
                sumBlue += var20 & 255;
            }
        }
        r = sumRed / 9;
        g = sumGreen / 9;
        int var19 = sumBlue / 9;
        return r << 16 | g << 8 | var19;
    }

    public static int getFluidColor(Block block, IBlockAccess blockAccess, BlockPos blockPos) {
        return block.getMaterial() != Material.water ? block.colorMultiplier(blockAccess, blockPos) : (waterColors != null ? (Config.isSmoothBiomes() ? CustomColorizer.getSmoothColor(waterColors, blockAccess, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3, 1) : CustomColorizer.getCustomColor(waterColors, blockAccess, blockPos)) : (!Config.isSwampColors() ? 16777215 : block.colorMultiplier(blockAccess, blockPos)));
    }

    private static int getCustomColor(int[] colors, IBlockAccess blockAccess, BlockPos blockPos) {
        BiomeGenBase bgb = blockAccess.getBiomeGenForCoords(blockPos);
        double temperature = MathHelper.clamp_float(bgb.func_180626_a(blockPos), 0.0f, 1.0f);
        double rainfall = MathHelper.clamp_float(bgb.getFloatRainfall(), 0.0f, 1.0f);
        int cx = (int)((1.0 - temperature) * 255.0);
        int cy = (int)((1.0 - (rainfall *= temperature)) * 255.0);
        return colors[cy << 8 | cx] & 16777215;
    }

    public static void updatePortalFX(EntityFX fx) {
        if (particlePortalColor >= 0) {
            int col = particlePortalColor;
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    public static void updateMyceliumFX(EntityFX fx) {
        if (myceliumParticleColors != null) {
            int col = myceliumParticleColors[random.nextInt(myceliumParticleColors.length)];
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    public static void updateReddustFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z) {
        int level;
        int col;
        IBlockState state;
        if (redstoneColors != null && (col = CustomColorizer.getRedstoneColor(level = CustomColorizer.getRedstoneLevel(state = blockAccess.getBlockState(new BlockPos(x, y, z)), 15))) != -1) {
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    private static int getRedstoneLevel(IBlockState state, int def) {
        Block block = state.getBlock();
        if (!(block instanceof BlockRedstoneWire)) {
            return def;
        }
        Comparable val = state.getValue(BlockRedstoneWire.POWER);
        if (!(val instanceof Integer)) {
            return def;
        }
        Integer valInt = (Integer)val;
        return valInt;
    }

    public static int getRedstoneColor(int level) {
        return redstoneColors == null ? -1 : (level >= 0 && level <= 15 ? redstoneColors[level] & 16777215 : -1);
    }

    public static void updateWaterFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z) {
        if (waterColors != null) {
            int col = CustomColorizer.getFluidColor(Blocks.water, blockAccess, new BlockPos(x, y, z));
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            if (particleWaterColor >= 0) {
                int redDrop = particleWaterColor >> 16 & 255;
                int greenDrop = particleWaterColor >> 8 & 255;
                int blueDrop = particleWaterColor & 255;
                redF *= (float)redDrop / 255.0f;
                greenF *= (float)greenDrop / 255.0f;
                blueF *= (float)blueDrop / 255.0f;
            }
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    public static int getLilypadColorMultiplier(IBlockAccess blockAccess, BlockPos blockPos) {
        return lilyPadColor < 0 ? Blocks.waterlily.colorMultiplier(blockAccess, blockPos) : lilyPadColor;
    }

    public static Vec3 getFogColorNether(Vec3 col) {
        return fogColorNether == null ? col : fogColorNether;
    }

    public static Vec3 getFogColorEnd(Vec3 col) {
        return fogColorEnd == null ? col : fogColorEnd;
    }

    public static Vec3 getSkyColorEnd(Vec3 col) {
        return skyColorEnd == null ? col : skyColorEnd;
    }

    public static Vec3 getSkyColor(Vec3 skyColor3d, IBlockAccess blockAccess, double x, double y, double z) {
        if (skyColors == null) {
            return skyColor3d;
        }
        int col = CustomColorizer.getSmoothColor(skyColors, blockAccess, x, y, z, 7, 1);
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        float cRed = (float)skyColor3d.xCoord / 0.5f;
        float cGreen = (float)skyColor3d.yCoord / 0.66275f;
        float cBlue = (float)skyColor3d.zCoord;
        return new Vec3(redF *= cRed, greenF *= cGreen, blueF *= cBlue);
    }

    public static Vec3 getFogColor(Vec3 fogColor3d, IBlockAccess blockAccess, double x, double y, double z) {
        if (fogColors == null) {
            return fogColor3d;
        }
        int col = CustomColorizer.getSmoothColor(fogColors, blockAccess, x, y, z, 7, 1);
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        float cRed = (float)fogColor3d.xCoord / 0.753f;
        float cGreen = (float)fogColor3d.yCoord / 0.8471f;
        float cBlue = (float)fogColor3d.zCoord;
        return new Vec3(redF *= cRed, greenF *= cGreen, blueF *= cBlue);
    }

    public static Vec3 getUnderwaterColor(IBlockAccess blockAccess, double x, double y, double z) {
        if (underwaterColors == null) {
            return null;
        }
        int col = CustomColorizer.getSmoothColor(underwaterColors, blockAccess, x, y, z, 7, 1);
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        return new Vec3(redF, greenF, blueF);
    }

    public static int getSmoothColor(int[] colors, IBlockAccess blockAccess, double x, double y, double z, int samples, int step) {
        int g;
        int b;
        int r;
        if (colors == null) {
            return -1;
        }
        int x0 = MathHelper.floor_double(x);
        int y0 = MathHelper.floor_double(y);
        int z0 = MathHelper.floor_double(z);
        int n = samples * step / 2;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = 0;
        BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        for (r = x0 - n; r <= x0 + n; r += step) {
            for (g = z0 - n; g <= z0 + n; g += step) {
                blockPosM.setXyz(r, y0, g);
                b = CustomColorizer.getCustomColor(colors, blockAccess, blockPosM);
                sumRed += b >> 16 & 255;
                sumGreen += b >> 8 & 255;
                sumBlue += b & 255;
                ++count;
            }
        }
        r = sumRed / count;
        g = sumGreen / count;
        b = sumBlue / count;
        return r << 16 | g << 8 | b;
    }

    public static int mixColors(int c1, int c2, float w1) {
        if (w1 <= 0.0f) {
            return c2;
        }
        if (w1 >= 1.0f) {
            return c1;
        }
        float w2 = 1.0f - w1;
        int r1 = c1 >> 16 & 255;
        int g1 = c1 >> 8 & 255;
        int b1 = c1 & 255;
        int r2 = c2 >> 16 & 255;
        int g2 = c2 >> 8 & 255;
        int b2 = c2 & 255;
        int r = (int)((float)r1 * w1 + (float)r2 * w2);
        int g = (int)((float)g1 * w1 + (float)g2 * w2);
        int b = (int)((float)b1 * w1 + (float)b2 * w2);
        return r << 16 | g << 8 | b;
    }

    private static int averageColor(int c1, int c2) {
        int r1 = c1 >> 16 & 255;
        int g1 = c1 >> 8 & 255;
        int b1 = c1 & 255;
        int r2 = c2 >> 16 & 255;
        int g2 = c2 >> 8 & 255;
        int b2 = c2 & 255;
        int r = (r1 + r2) / 2;
        int g = (g1 + g2) / 2;
        int b = (b1 + b2) / 2;
        return r << 16 | g << 8 | b;
    }

    public static int getStemColorMultiplier(Block blockStem, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
        if (stemColors == null) {
            return blockStem.colorMultiplier(blockAccess, blockPos);
        }
        int level = renderEnv.getMetadata();
        if (level < 0) {
            level = 0;
        }
        if (level >= stemColors.length) {
            level = stemColors.length - 1;
        }
        return stemColors[level];
    }

    public static boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision) {
        if (world == null) {
            return false;
        }
        if (lightMapsColorsRgb == null) {
            return false;
        }
        if (!Config.isCustomColors()) {
            return false;
        }
        int worldType = world.provider.getDimensionId();
        if (worldType >= -1 && worldType <= 1) {
            int lightMapIndex = worldType + 1;
            float[][] lightMapRgb = lightMapsColorsRgb[lightMapIndex];
            if (lightMapRgb == null) {
                return false;
            }
            int height = lightMapsHeight[lightMapIndex];
            if (nightvision && height < 64) {
                return false;
            }
            int width = lightMapRgb.length / height;
            if (width < 16) {
                Config.warn("Invalid lightmap width: " + width + " for: /environment/lightmap" + worldType + ".png");
                CustomColorizer.lightMapsColorsRgb[lightMapIndex] = null;
                return false;
            }
            int startIndex = 0;
            if (nightvision) {
                startIndex = width * 16 * 2;
            }
            float sun = 1.1666666f * (world.getSunBrightness(1.0f) - 0.2f);
            if (world.func_175658_ac() > 0) {
                sun = 1.0f;
            }
            sun = Config.limitTo1(sun);
            float sunX = sun * (float)(width - 1);
            float torchX = Config.limitTo1(torchFlickerX + 0.5f) * (float)(width - 1);
            float gamma = Config.limitTo1(Config.getGameSettings().gammaSetting);
            boolean hasGamma = gamma > 1.0E-4f;
            CustomColorizer.getLightMapColumn(lightMapRgb, sunX, startIndex, width, sunRgbs);
            CustomColorizer.getLightMapColumn(lightMapRgb, torchX, startIndex + 16 * width, width, torchRgbs);
            float[] rgb = new float[3];
            for (int is = 0; is < 16; ++is) {
                for (int it = 0; it < 16; ++it) {
                    int r;
                    for (r = 0; r < 3; ++r) {
                        float g = Config.limitTo1(sunRgbs[is][r] + torchRgbs[it][r]);
                        if (hasGamma) {
                            float b = 1.0f - g;
                            b = 1.0f - b * b * b * b;
                            g = gamma * b + (1.0f - gamma) * g;
                        }
                        rgb[r] = g;
                    }
                    r = (int)(rgb[0] * 255.0f);
                    int var21 = (int)(rgb[1] * 255.0f);
                    int var22 = (int)(rgb[2] * 255.0f);
                    lmColors[is * 16 + it] = -16777216 | r << 16 | var21 << 8 | var22;
                }
            }
            return true;
        }
        return false;
    }

    private static void getLightMapColumn(float[][] origMap, float x, int offset, int width, float[][] colRgb) {
        int xHigh;
        int xLow = (int)Math.floor(x);
        if (xLow == (xHigh = (int)Math.ceil(x))) {
            for (int var14 = 0; var14 < 16; ++var14) {
                float[] var15 = origMap[offset + var14 * width + xLow];
                float[] var16 = colRgb[var14];
                for (int var17 = 0; var17 < 3; ++var17) {
                    var16[var17] = var15[var17];
                }
            }
        } else {
            float dLow = 1.0f - (x - (float)xLow);
            float dHigh = 1.0f - ((float)xHigh - x);
            for (int y = 0; y < 16; ++y) {
                float[] rgbLow = origMap[offset + y * width + xLow];
                float[] rgbHigh = origMap[offset + y * width + xHigh];
                float[] rgb = colRgb[y];
                for (int i = 0; i < 3; ++i) {
                    rgb[i] = rgbLow[i] * dLow + rgbHigh[i] * dHigh;
                }
            }
        }
    }

    public static Vec3 getWorldFogColor(Vec3 fogVec, WorldClient world, Entity renderViewEntity, float partialTicks) {
        int worldType = world.provider.getDimensionId();
        switch (worldType) {
            case -1: {
                fogVec = CustomColorizer.getFogColorNether(fogVec);
                break;
            }
            case 0: {
                Minecraft mc = Minecraft.getMinecraft();
                fogVec = CustomColorizer.getFogColor(fogVec, Minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0, renderViewEntity.posZ);
                break;
            }
            case 1: {
                fogVec = CustomColorizer.getFogColorEnd(fogVec);
            }
        }
        return fogVec;
    }

    public static Vec3 getWorldSkyColor(Vec3 skyVec, WorldClient world, Entity renderViewEntity, float partialTicks) {
        int worldType = world.provider.getDimensionId();
        switch (worldType) {
            case 0: {
                Minecraft mc = Minecraft.getMinecraft();
                skyVec = CustomColorizer.getSkyColor(skyVec, Minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0, renderViewEntity.posZ);
                break;
            }
            case 1: {
                skyVec = CustomColorizer.getSkyColorEnd(skyVec);
            }
        }
        return skyVec;
    }
}


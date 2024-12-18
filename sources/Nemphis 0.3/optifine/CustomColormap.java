/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.BlockPosM;
import optifine.Config;
import optifine.ConnectedParser;
import optifine.CustomColors;
import optifine.MatchBlock;
import optifine.Matches;
import optifine.TextureUtils;

public class CustomColormap
implements CustomColors.IColorizer {
    public String name = null;
    public String basePath = null;
    private int format = -1;
    private MatchBlock[] matchBlocks = null;
    private String source = null;
    private int color = -1;
    private int yVariance = 0;
    private int yOffset = 0;
    private int width = 0;
    private int height = 0;
    private int[] colors = null;
    private float[][] colorsRgb = null;
    private static final int FORMAT_UNKNOWN = -1;
    private static final int FORMAT_VANILLA = 0;
    private static final int FORMAT_GRID = 1;
    private static final int FORMAT_FIXED = 2;
    public static final String KEY_FORMAT = "format";
    public static final String KEY_BLOCKS = "blocks";
    public static final String KEY_SOURCE = "source";
    public static final String KEY_COLOR = "color";
    public static final String KEY_Y_VARIANCE = "yVariance";
    public static final String KEY_Y_OFFSET = "yOffset";

    public CustomColormap(Properties props, String path, int width, int height) {
        ConnectedParser cp = new ConnectedParser("Colormap");
        this.name = cp.parseName(path);
        this.basePath = cp.parseBasePath(path);
        this.format = this.parseFormat(props.getProperty("format"));
        this.matchBlocks = cp.parseMatchBlocks(props.getProperty("blocks"));
        this.source = CustomColormap.parseTexture(props.getProperty("source"), path, this.basePath);
        this.color = ConnectedParser.parseColor(props.getProperty("color"), -1);
        this.yVariance = cp.parseInt(props.getProperty("yVariance"), 0);
        this.yOffset = cp.parseInt(props.getProperty("yOffset"), 0);
        this.width = width;
        this.height = height;
    }

    private int parseFormat(String str) {
        if (str == null) {
            return 0;
        }
        if (str.equals("vanilla")) {
            return 0;
        }
        if (str.equals("grid")) {
            return 1;
        }
        if (str.equals("fixed")) {
            return 2;
        }
        CustomColormap.warn("Unknown format: " + str);
        return -1;
    }

    public boolean isValid(String path) {
        if (this.format != 0 && this.format != 1) {
            if (this.format != 2) {
                return false;
            }
            if (this.color < 0) {
                this.color = 16777215;
            }
        } else {
            if (this.source == null) {
                CustomColormap.warn("Source not defined: " + path);
                return false;
            }
            this.readColors();
            if (this.colors == null) {
                return false;
            }
            if (this.color < 0) {
                if (this.format == 0) {
                    this.color = this.getColor(127, 127);
                }
                if (this.format == 1) {
                    this.color = this.getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
                }
            }
        }
        return true;
    }

    public boolean isValidMatchBlocks(String path) {
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
            if (this.matchBlocks == null) {
                CustomColormap.warn("Match blocks not defined: " + path);
                return false;
            }
        }
        return true;
    }

    private MatchBlock[] detectMatchBlocks() {
        int mbs;
        String cp;
        Block block = Block.getBlockFromName(this.name);
        if (block != null) {
            return new MatchBlock[]{new MatchBlock(Block.getIdFromBlock(block))};
        }
        Pattern p = Pattern.compile("^block([0-9]+).*$");
        Matcher m = p.matcher(this.name);
        if (m.matches() && (mbs = Config.parseInt(cp = m.group(1), -1)) >= 0) {
            return new MatchBlock[]{new MatchBlock(mbs)};
        }
        ConnectedParser cp1 = new ConnectedParser("Colormap");
        MatchBlock[] mbs1 = cp1.parseMatchBlock(this.name);
        return mbs1 != null ? mbs1 : null;
    }

    private void readColors() {
        try {
            boolean heightOk;
            this.colors = null;
            if (this.source == null) {
                return;
            }
            String e = String.valueOf(this.source) + ".png";
            ResourceLocation loc = new ResourceLocation(e);
            InputStream is = Config.getResourceStream(loc);
            if (is == null) {
                return;
            }
            BufferedImage img = TextureUtil.func_177053_a(is);
            if (img == null) {
                return;
            }
            int imgWidth = img.getWidth();
            int imgHeight = img.getHeight();
            boolean widthOk = this.width < 0 || this.width == imgWidth;
            boolean bl = heightOk = this.height < 0 || this.height == imgHeight;
            if (!widthOk || !heightOk) {
                CustomColormap.dbg("Non-standard palette size: " + imgWidth + "x" + imgHeight + ", should be: " + this.width + "x" + this.height + ", path: " + e);
            }
            this.width = imgWidth;
            this.height = imgHeight;
            if (this.width <= 0 || this.height <= 0) {
                CustomColormap.warn("Invalid palette size: " + imgWidth + "x" + imgHeight + ", path: " + e);
                return;
            }
            this.colors = new int[imgWidth * imgHeight];
            img.getRGB(0, 0, imgWidth, imgHeight, this.colors, 0, imgWidth);
        }
        catch (IOException var9) {
            var9.printStackTrace();
        }
    }

    private static void dbg(String str) {
        Config.dbg("CustomColors: " + str);
    }

    private static void warn(String str) {
        Config.warn("CustomColors: " + str);
    }

    private static String parseTexture(String texStr, String path, String basePath) {
        int pos2;
        if (texStr != null) {
            String str = ".png";
            if (texStr.endsWith(str)) {
                texStr = texStr.substring(0, texStr.length() - str.length());
            }
            texStr = CustomColormap.fixTextureName(texStr, basePath);
            return texStr;
        }
        String str = path;
        int pos = path.lastIndexOf(47);
        if (pos >= 0) {
            str = path.substring(pos + 1);
        }
        if ((pos2 = str.lastIndexOf(46)) >= 0) {
            str = str.substring(0, pos2);
        }
        str = CustomColormap.fixTextureName(str, basePath);
        return str;
    }

    private static String fixTextureName(String iconName, String basePath) {
        String pathBlocks;
        if (!((iconName = TextureUtils.fixResourcePath(iconName, basePath)).startsWith(basePath) || iconName.startsWith("textures/") || iconName.startsWith("mcpatcher/"))) {
            iconName = String.valueOf(basePath) + "/" + iconName;
        }
        if (iconName.endsWith(".png")) {
            iconName = iconName.substring(0, iconName.length() - 4);
        }
        if (iconName.startsWith(pathBlocks = "textures/blocks/")) {
            iconName = iconName.substring(pathBlocks.length());
        }
        if (iconName.startsWith("/")) {
            iconName = iconName.substring(1);
        }
        return iconName;
    }

    public boolean matchesBlock(BlockStateBase blockState) {
        return Matches.block(blockState, this.matchBlocks);
    }

    public int getColorRandom() {
        if (this.format == 2) {
            return this.color;
        }
        int index = CustomColors.random.nextInt(this.colors.length);
        return this.colors[index];
    }

    public int getColor(int index) {
        index = Config.limit(index, 0, this.colors.length);
        return this.colors[index] & 16777215;
    }

    public int getColor(int cx, int cy) {
        cx = Config.limit(cx, 0, this.width - 1);
        cy = Config.limit(cy, 0, this.height - 1);
        return this.colors[cy * this.width + cx] & 16777215;
    }

    public float[][] getColorsRgb() {
        if (this.colorsRgb == null) {
            this.colorsRgb = CustomColormap.toRgb(this.colors);
        }
        return this.colorsRgb;
    }

    @Override
    public int getColor(IBlockAccess blockAccess, BlockPos blockPos) {
        BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
        return this.getColor(biome, blockPos);
    }

    @Override
    public boolean isColorConstant() {
        if (this.format == 2) {
            return true;
        }
        return false;
    }

    public int getColor(BiomeGenBase biome, BlockPos blockPos) {
        return this.format == 0 ? this.getColorVanilla(biome, blockPos) : (this.format == 1 ? this.getColorGrid(biome, blockPos) : this.color);
    }

    public int getColorSmooth(IBlockAccess blockAccess, double x, double y, double z, int radius) {
        int g;
        int b;
        if (this.format == 2) {
            return this.color;
        }
        int x0 = MathHelper.floor_double(x);
        int y0 = MathHelper.floor_double(y);
        int z0 = MathHelper.floor_double(z);
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = 0;
        BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        int r = x0 - radius;
        while (r <= x0 + radius) {
            g = z0 - radius;
            while (g <= z0 + radius) {
                blockPosM.setXyz(r, y0, g);
                b = this.getColor(blockAccess, (BlockPos)blockPosM);
                sumRed += b >> 16 & 255;
                sumGreen += b >> 8 & 255;
                sumBlue += b & 255;
                ++count;
                ++g;
            }
            ++r;
        }
        r = sumRed / count;
        g = sumGreen / count;
        b = sumBlue / count;
        return r << 16 | g << 8 | b;
    }

    private int getColorVanilla(BiomeGenBase biome, BlockPos blockPos) {
        double temperature = MathHelper.clamp_float(biome.func_180626_a(blockPos), 0.0f, 1.0f);
        double rainfall = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0f, 1.0f);
        int cx = (int)((1.0 - temperature) * (double)(this.width - 1));
        int cy = (int)((1.0 - (rainfall *= temperature)) * (double)(this.height - 1));
        return this.getColor(cx, cy);
    }

    private int getColorGrid(BiomeGenBase biome, BlockPos blockPos) {
        int cx = biome.biomeID;
        int cy = blockPos.getY() - this.yOffset;
        if (this.yVariance > 0) {
            int seed = blockPos.getX() << 16 + blockPos.getZ();
            int rand = Config.intHash(seed);
            int range = this.yVariance * 2 + 1;
            int diff = (rand & 255) % range - this.yVariance;
            cy += diff;
        }
        return this.getColor(cx, cy);
    }

    public int getLength() {
        return this.format == 2 ? 1 : this.colors.length;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private static float[][] toRgb(int[] cols) {
        float[][] colsRgb = new float[cols.length][3];
        int i = 0;
        while (i < cols.length) {
            int col = cols[i];
            float rf = (float)(col >> 16 & 255) / 255.0f;
            float gf = (float)(col >> 8 & 255) / 255.0f;
            float bf = (float)(col & 255) / 255.0f;
            float[] colRgb = colsRgb[i];
            colRgb[0] = rf;
            colRgb[1] = gf;
            colRgb[2] = bf;
            ++i;
        }
        return colsRgb;
    }

    public void addMatchBlock(MatchBlock mb) {
        if (this.matchBlocks == null) {
            this.matchBlocks = new MatchBlock[0];
        }
        this.matchBlocks = (MatchBlock[])Config.addObjectToArray(this.matchBlocks, mb);
    }

    public void addMatchBlock(int blockId, int metadata) {
        MatchBlock mb = this.getMatchBlock(blockId);
        if (mb != null) {
            if (metadata >= 0) {
                mb.addMetadata(metadata);
            }
        } else {
            this.addMatchBlock(new MatchBlock(blockId, metadata));
        }
    }

    private MatchBlock getMatchBlock(int blockId) {
        if (this.matchBlocks == null) {
            return null;
        }
        int i = 0;
        while (i < this.matchBlocks.length) {
            MatchBlock mb = this.matchBlocks[i];
            if (mb.getBlockId() == blockId) {
                return mb;
            }
            ++i;
        }
        return null;
    }

    public int[] getMatchBlockIds() {
        if (this.matchBlocks == null) {
            return null;
        }
        HashSet<Integer> setIds = new HashSet<Integer>();
        int ints = 0;
        while (ints < this.matchBlocks.length) {
            MatchBlock ids = this.matchBlocks[ints];
            if (ids.getBlockId() >= 0) {
                setIds.add(ids.getBlockId());
            }
            ++ints;
        }
        Integer[] var5 = setIds.toArray(new Integer[setIds.size()]);
        int[] var6 = new int[var5.length];
        int i = 0;
        while (i < var5.length) {
            var6[i] = var5[i];
            ++i;
        }
        return var6;
    }

    public String toString() {
        return this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString(this.matchBlocks) + ", source: " + this.source;
    }
}


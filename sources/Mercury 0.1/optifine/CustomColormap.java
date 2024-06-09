/*
 * Decompiled with CFR 0.145.
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
        ConnectedParser cp2 = new ConnectedParser("Colormap");
        this.name = cp2.parseName(path);
        this.basePath = cp2.parseBasePath(path);
        this.format = this.parseFormat(props.getProperty(KEY_FORMAT));
        this.matchBlocks = cp2.parseMatchBlocks(props.getProperty(KEY_BLOCKS));
        this.source = CustomColormap.parseTexture(props.getProperty(KEY_SOURCE), path, this.basePath);
        this.color = ConnectedParser.parseColor(props.getProperty(KEY_COLOR), -1);
        this.yVariance = cp2.parseInt(props.getProperty(KEY_Y_VARIANCE), 0);
        this.yOffset = cp2.parseInt(props.getProperty(KEY_Y_OFFSET), 0);
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
        String cp2;
        Block block = Block.getBlockFromName(this.name);
        if (block != null) {
            return new MatchBlock[]{new MatchBlock(Block.getIdFromBlock(block))};
        }
        Pattern p2 = Pattern.compile("^block([0-9]+).*$");
        Matcher m2 = p2.matcher(this.name);
        if (m2.matches() && (mbs = Config.parseInt(cp2 = m2.group(1), -1)) >= 0) {
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
            String e2 = String.valueOf(this.source) + ".png";
            ResourceLocation loc = new ResourceLocation(e2);
            InputStream is2 = Config.getResourceStream(loc);
            if (is2 == null) {
                return;
            }
            BufferedImage img = TextureUtil.func_177053_a(is2);
            if (img == null) {
                return;
            }
            int imgWidth = img.getWidth();
            int imgHeight = img.getHeight();
            boolean widthOk = this.width < 0 || this.width == imgWidth;
            boolean bl2 = heightOk = this.height < 0 || this.height == imgHeight;
            if (!widthOk || !heightOk) {
                CustomColormap.dbg("Non-standard palette size: " + imgWidth + "x" + imgHeight + ", should be: " + this.width + "x" + this.height + ", path: " + e2);
            }
            this.width = imgWidth;
            this.height = imgHeight;
            if (this.width <= 0 || this.height <= 0) {
                CustomColormap.warn("Invalid palette size: " + imgWidth + "x" + imgHeight + ", path: " + e2);
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

    public int getColor(int cx2, int cy2) {
        cx2 = Config.limit(cx2, 0, this.width - 1);
        cy2 = Config.limit(cy2, 0, this.height - 1);
        return this.colors[cy2 * this.width + cx2] & 16777215;
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
        return this.format == 2;
    }

    public int getColor(BiomeGenBase biome, BlockPos blockPos) {
        return this.format == 0 ? this.getColorVanilla(biome, blockPos) : (this.format == 1 ? this.getColorGrid(biome, blockPos) : this.color);
    }

    public int getColorSmooth(IBlockAccess blockAccess, double x2, double y2, double z2, int radius) {
        int b2;
        int g2;
        int r2;
        if (this.format == 2) {
            return this.color;
        }
        int x0 = MathHelper.floor_double(x2);
        int y0 = MathHelper.floor_double(y2);
        int z0 = MathHelper.floor_double(z2);
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = 0;
        BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        for (r2 = x0 - radius; r2 <= x0 + radius; ++r2) {
            for (g2 = z0 - radius; g2 <= z0 + radius; ++g2) {
                blockPosM.setXyz(r2, y0, g2);
                b2 = this.getColor(blockAccess, (BlockPos)blockPosM);
                sumRed += b2 >> 16 & 255;
                sumGreen += b2 >> 8 & 255;
                sumBlue += b2 & 255;
                ++count;
            }
        }
        r2 = sumRed / count;
        g2 = sumGreen / count;
        b2 = sumBlue / count;
        return r2 << 16 | g2 << 8 | b2;
    }

    private int getColorVanilla(BiomeGenBase biome, BlockPos blockPos) {
        double temperature = MathHelper.clamp_float(biome.func_180626_a(blockPos), 0.0f, 1.0f);
        double rainfall = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0f, 1.0f);
        int cx2 = (int)((1.0 - temperature) * (double)(this.width - 1));
        int cy2 = (int)((1.0 - (rainfall *= temperature)) * (double)(this.height - 1));
        return this.getColor(cx2, cy2);
    }

    private int getColorGrid(BiomeGenBase biome, BlockPos blockPos) {
        int cx2 = biome.biomeID;
        int cy2 = blockPos.getY() - this.yOffset;
        if (this.yVariance > 0) {
            int seed = blockPos.getX() << 16 + blockPos.getZ();
            int rand = Config.intHash(seed);
            int range = this.yVariance * 2 + 1;
            int diff = (rand & 255) % range - this.yVariance;
            cy2 += diff;
        }
        return this.getColor(cx2, cy2);
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
        for (int i2 = 0; i2 < cols.length; ++i2) {
            int col = cols[i2];
            float rf2 = (float)(col >> 16 & 255) / 255.0f;
            float gf2 = (float)(col >> 8 & 255) / 255.0f;
            float bf2 = (float)(col & 255) / 255.0f;
            float[] colRgb = colsRgb[i2];
            colRgb[0] = rf2;
            colRgb[1] = gf2;
            colRgb[2] = bf2;
        }
        return colsRgb;
    }

    public void addMatchBlock(MatchBlock mb2) {
        if (this.matchBlocks == null) {
            this.matchBlocks = new MatchBlock[0];
        }
        this.matchBlocks = (MatchBlock[])Config.addObjectToArray(this.matchBlocks, mb2);
    }

    public void addMatchBlock(int blockId, int metadata) {
        MatchBlock mb2 = this.getMatchBlock(blockId);
        if (mb2 != null) {
            if (metadata >= 0) {
                mb2.addMetadata(metadata);
            }
        } else {
            this.addMatchBlock(new MatchBlock(blockId, metadata));
        }
    }

    private MatchBlock getMatchBlock(int blockId) {
        if (this.matchBlocks == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.matchBlocks.length; ++i2) {
            MatchBlock mb2 = this.matchBlocks[i2];
            if (mb2.getBlockId() != blockId) continue;
            return mb2;
        }
        return null;
    }

    public int[] getMatchBlockIds() {
        if (this.matchBlocks == null) {
            return null;
        }
        HashSet<Integer> setIds = new HashSet<Integer>();
        for (int ints = 0; ints < this.matchBlocks.length; ++ints) {
            MatchBlock ids = this.matchBlocks[ints];
            if (ids.getBlockId() < 0) continue;
            setIds.add(ids.getBlockId());
        }
        Integer[] var5 = setIds.toArray(new Integer[setIds.size()]);
        int[] var6 = new int[var5.length];
        for (int i2 = 0; i2 < var5.length; ++i2) {
            var6[i2] = var5[i2];
        }
        return var6;
    }

    public String toString() {
        return this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString(this.matchBlocks) + ", source: " + this.source;
    }
}


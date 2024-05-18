package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

public class CustomColormap
{
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
    private float[][] colorsRgb = (float[][])null;
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

    public CustomColormap(Properties p_i39_1_, String p_i39_2_, int p_i39_3_, int p_i39_4_)
    {
        ConnectedParser connectedparser = new ConnectedParser("Colormap");
        this.name = connectedparser.parseName(p_i39_2_);
        this.basePath = connectedparser.parseBasePath(p_i39_2_);
        this.format = this.parseFormet(p_i39_1_.getProperty("format"));
        this.matchBlocks = connectedparser.parseMatchBlocks(p_i39_1_.getProperty("blocks"));
        this.source = parseTexture(p_i39_1_.getProperty("source"), p_i39_2_, this.basePath);
        this.color = ConnectedParser.parseColor(p_i39_1_.getProperty("color"), -1);
        this.yVariance = connectedparser.parseInt(p_i39_1_.getProperty("yVariance"), 0);
        this.yOffset = connectedparser.parseInt(p_i39_1_.getProperty("yOffset"), 0);
        this.width = p_i39_3_;
        this.height = p_i39_4_;
    }

    private int parseFormet(String p_parseFormet_1_)
    {
        if (p_parseFormet_1_ == null)
        {
            return 0;
        }
        else if (p_parseFormet_1_.equals("vanilla"))
        {
            return 0;
        }
        else if (p_parseFormet_1_.equals("grid"))
        {
            return 1;
        }
        else if (p_parseFormet_1_.equals("fixed"))
        {
            return 2;
        }
        else
        {
            warn("Unknown format: " + p_parseFormet_1_);
            return -1;
        }
    }

    public boolean isValid(String p_isValid_1_)
    {
        if (this.format != 0 && this.format != 1)
        {
            if (this.format != 2)
            {
                return false;
            }

            if (this.color < 0)
            {
                warn("Color not defined: " + p_isValid_1_);
                return false;
            }
        }
        else
        {
            if (this.source == null)
            {
                warn("Source not defined: " + p_isValid_1_);
                return false;
            }

            this.readColors();

            if (this.colors == null)
            {
                return false;
            }
        }

        return true;
    }

    private void readColors()
    {
        try
        {
            this.colors = null;

            if (this.source == null)
            {
                return;
            }

            String s = this.source + ".png";
            ResourceLocation resourcelocation = new ResourceLocation(s);
            BufferedImage bufferedimage = TextureUtil.readBufferedImage(Config.getResourceStream(resourcelocation));
            int i = bufferedimage.getWidth();
            int j = bufferedimage.getHeight();
            boolean flag = this.width < 0 || this.width == i;
            boolean flag1 = this.height < 0 || this.height == j;

            if (!flag || !flag1)
            {
                warn("Invalid palette size: " + i + "x" + j + ", should be: " + this.width + "x" + this.height + ", path: " + s);
                return;
            }

            if (this.width < 0)
            {
                this.width = i;
            }

            if (this.height < 0)
            {
                this.height = j;
            }

            this.colors = new int[i * j];
            bufferedimage.getRGB(0, 0, i, j, this.colors, 0, i);
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private static void dbg(String p_dbg_0_)
    {
        Config.dbg("CustomColors: " + p_dbg_0_);
    }

    private static void warn(String p_warn_0_)
    {
        Config.warn("CustomColors: " + p_warn_0_);
    }

    private static String parseTexture(String p_parseTexture_0_, String p_parseTexture_1_, String p_parseTexture_2_)
    {
        if (p_parseTexture_0_ != null)
        {
            String s1 = ".png";

            if (p_parseTexture_0_.endsWith(s1))
            {
                p_parseTexture_0_ = p_parseTexture_0_.substring(0, p_parseTexture_0_.length() - s1.length());
            }

            p_parseTexture_0_ = fixTextureName(p_parseTexture_0_, p_parseTexture_2_);
            return p_parseTexture_0_;
        }
        else
        {
            String s = p_parseTexture_1_;
            int i = p_parseTexture_1_.lastIndexOf(47);

            if (i >= 0)
            {
                s = p_parseTexture_1_.substring(i + 1);
            }

            int j = s.lastIndexOf(46);

            if (j >= 0)
            {
                s = s.substring(0, j);
            }

            s = fixTextureName(s, p_parseTexture_2_);
            return s;
        }
    }

    private static String fixTextureName(String p_fixTextureName_0_, String p_fixTextureName_1_)
    {
        p_fixTextureName_0_ = TextureUtils.fixResourcePath(p_fixTextureName_0_, p_fixTextureName_1_);

        if (!p_fixTextureName_0_.startsWith(p_fixTextureName_1_) && !p_fixTextureName_0_.startsWith("textures/") && !p_fixTextureName_0_.startsWith("mcpatcher/"))
        {
            p_fixTextureName_0_ = p_fixTextureName_1_ + "/" + p_fixTextureName_0_;
        }

        if (p_fixTextureName_0_.endsWith(".png"))
        {
            p_fixTextureName_0_ = p_fixTextureName_0_.substring(0, p_fixTextureName_0_.length() - 4);
        }

        String s = "textures/blocks/";

        if (p_fixTextureName_0_.startsWith(s))
        {
            p_fixTextureName_0_ = p_fixTextureName_0_.substring(s.length());
        }

        if (p_fixTextureName_0_.startsWith("/"))
        {
            p_fixTextureName_0_ = p_fixTextureName_0_.substring(1);
        }

        return p_fixTextureName_0_;
    }

    public boolean matchesBlock(BlockStateBase p_matchesBlock_1_)
    {
        if (this.matchBlocks == null)
        {
            return true;
        }
        else
        {
            for (int i = 0; i < this.matchBlocks.length; ++i)
            {
                MatchBlock matchblock = this.matchBlocks[i];

                if (matchblock.matches(p_matchesBlock_1_))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public int getColorRandom()
    {
        if (this.format == 2)
        {
            return this.color;
        }
        else
        {
            int i = CustomColorizer.random.nextInt(this.colors.length);
            return this.colors[i];
        }
    }

    public int getColor(int p_getColor_1_)
    {
        p_getColor_1_ = Config.limit(p_getColor_1_, 0, this.colors.length);
        return this.colors[p_getColor_1_] & 16777215;
    }

    public int getColor(int p_getColor_1_, int p_getColor_2_)
    {
        p_getColor_1_ = Config.limit(p_getColor_1_, 0, this.width);
        p_getColor_2_ = Config.limit(p_getColor_2_, 0, this.height);
        return this.colors[p_getColor_2_ * this.width + p_getColor_1_] & 16777215;
    }

    public float[][] getColorsRgb()
    {
        if (this.colorsRgb == null)
        {
            this.colorsRgb = toRgb(this.colors);
        }

        return this.colorsRgb;
    }

    public int getColor(IBlockAccess p_getColor_1_, BlockPos p_getColor_2_)
    {
        return this.format == 0 ? this.getColorVanilla(p_getColor_1_, p_getColor_2_) : (this.format == 1 ? this.getColorGrid(p_getColor_1_, p_getColor_2_) : this.color);
    }

    public int getColorSmooth(IBlockAccess p_getColorSmooth_1_, double p_getColorSmooth_2_, double p_getColorSmooth_4_, double p_getColorSmooth_6_, int p_getColorSmooth_8_, int p_getColorSmooth_9_)
    {
        if (this.format == 2)
        {
            return this.color;
        }
        else
        {
            int i = MathHelper.floor_double(p_getColorSmooth_2_);
            int j = MathHelper.floor_double(p_getColorSmooth_4_);
            int k = MathHelper.floor_double(p_getColorSmooth_6_);
            int l = p_getColorSmooth_8_ * p_getColorSmooth_9_ / 2;
            int i1 = 0;
            int j1 = 0;
            int k1 = 0;
            int l1 = 0;
            BlockPosM blockposm = new BlockPosM(0, 0, 0);

            for (int i2 = i - l; i2 <= i + l; i2 += p_getColorSmooth_9_)
            {
                for (int j2 = k - l; j2 <= k + l; j2 += p_getColorSmooth_9_)
                {
                    blockposm.setXyz(i2, j, j2);
                    int k2 = this.getColor(p_getColorSmooth_1_, blockposm);
                    i1 += k2 >> 16 & 255;
                    j1 += k2 >> 8 & 255;
                    k1 += k2 & 255;
                    ++l1;
                }
            }

            int l2 = i1 / l1;
            int i3 = j1 / l1;
            int j3 = k1 / l1;
            return l2 << 16 | i3 << 8 | j3;
        }
    }

    private int getColorVanilla(IBlockAccess p_getColorVanilla_1_, BlockPos p_getColorVanilla_2_)
    {
        BiomeGenBase biomegenbase = p_getColorVanilla_1_.getBiomeGenForCoords(p_getColorVanilla_2_);
        double d0 = (double)MathHelper.clamp_float(biomegenbase.getFloatTemperature(p_getColorVanilla_2_), 0.0F, 1.0F);
        double d1 = (double)MathHelper.clamp_float(biomegenbase.getFloatRainfall(), 0.0F, 1.0F);
        d1 = d1 * d0;
        int i = (int)((1.0D - d0) * (double)(this.width - 1));
        int j = (int)((1.0D - d1) * (double)(this.height - 1));
        return this.getColor(i, j);
    }

    private int getColorGrid(IBlockAccess p_getColorGrid_1_, BlockPos p_getColorGrid_2_)
    {
        BiomeGenBase biomegenbase = p_getColorGrid_1_.getBiomeGenForCoords(p_getColorGrid_2_);
        int i = biomegenbase.biomeID;
        int j = p_getColorGrid_2_.getY() - this.yOffset;

        if (this.yVariance > 0)
        {
            int k = p_getColorGrid_2_.getX() << 16 + p_getColorGrid_2_.getZ();
            int l = Config.intHash(k);
            int i1 = this.yVariance * 2 + 1;
            int j1 = (l & 255) % i1 - this.yVariance;
            j += j1;
        }

        return this.getColor(i, j);
    }

    public int getLength()
    {
        return this.format == 2 ? 1 : this.colors.length;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    private static float[][] toRgb(int[] p_toRgb_0_)
    {
        float[][] afloat = new float[p_toRgb_0_.length][3];

        for (int i = 0; i < p_toRgb_0_.length; ++i)
        {
            int j = p_toRgb_0_[i];
            float f = (float)(j >> 16 & 255) / 255.0F;
            float f1 = (float)(j >> 8 & 255) / 255.0F;
            float f2 = (float)(j & 255) / 255.0F;
            float[] afloat1 = afloat[i];
            afloat1[0] = f;
            afloat1[1] = f1;
            afloat1[2] = f2;
        }

        return afloat;
    }
}

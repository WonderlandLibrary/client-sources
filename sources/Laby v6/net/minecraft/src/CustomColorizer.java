package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomColorizer
{
    private static CustomColormap grassColors = null;
    private static CustomColormap waterColors = null;
    private static CustomColormap foliageColors = null;
    private static CustomColormap foliagePineColors = null;
    private static CustomColormap foliageBirchColors = null;
    private static CustomColormap swampFoliageColors = null;
    private static CustomColormap swampGrassColors = null;
    private static int[][] blockPalettes = (int[][])null;
    private static CustomColormap[] paletteColors = null;
    private static CustomColormap skyColors = null;
    private static CustomColormap fogColors = null;
    private static CustomColormap underwaterColors = null;
    private static CustomColormap[] lightMapsColorsRgb = null;
    private static int lightmapMinDimensionId = 0;
    private static float[][] sunRgbs = new float[16][3];
    private static float[][] torchRgbs = new float[16][3];
    private static CustomColormap redstoneColors = null;
    private static CustomColormap xpOrbColors = null;
    private static CustomColormap stemColors = null;
    private static CustomColormap stemMelonColors = null;
    private static CustomColormap stemPumpkinColors = null;
    private static CustomColormap myceliumParticleColors = null;
    private static boolean useDefaultColorMultiplier = true;
    private static int particleWaterColor = -1;
    private static int particlePortalColor = -1;
    private static int lilyPadColor = -1;
    private static int expBarTextColor = -1;
    private static int bossTextColor = -1;
    private static int signTextColor = -1;
    private static Vec3 fogColorNether = null;
    private static Vec3 fogColorEnd = null;
    private static Vec3 skyColorEnd = null;
    private static int[] spawnEggPrimaryColors = null;
    private static int[] spawnEggSecondaryColors = null;
    private static float[][] wolfCollarColors = (float[][])null;
    private static float[][] sheepColors = (float[][])null;
    private static int[] textColors = null;
    private static int[] mapColorsOriginal = null;
    private static int[] potionColors = null;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    public static Random random = new Random();

    public static void update()
    {
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
        xpOrbColors = null;
        stemColors = null;
        myceliumParticleColors = null;
        lightMapsColorsRgb = null;
        particleWaterColor = -1;
        particlePortalColor = -1;
        lilyPadColor = -1;
        expBarTextColor = -1;
        bossTextColor = -1;
        signTextColor = -1;
        fogColorNether = null;
        fogColorEnd = null;
        skyColorEnd = null;
        blockPalettes = (int[][])null;
        paletteColors = null;
        useDefaultColorMultiplier = true;
        spawnEggPrimaryColors = null;
        spawnEggSecondaryColors = null;
        wolfCollarColors = (float[][])null;
        sheepColors = (float[][])null;
        textColors = null;
        setMapColors(mapColorsOriginal);
        potionColors = null;
        PotionHelper.clearPotionColorCache();
        String s = "mcpatcher/colormap/";
        grassColors = getCustomColors("textures/colormap/grass.png", 256, 256);
        foliageColors = getCustomColors("textures/colormap/foliage.png", 256, 256);
        String[] astring = new String[] {"water.png", "watercolorX.png"};
        waterColors = getCustomColors(s, astring, 256, 256);

        if (Config.isCustomColors())
        {
            String[] astring1 = new String[] {"pine.png", "pinecolor.png"};
            foliagePineColors = getCustomColors(s, astring1, 256, 256);
            String[] astring2 = new String[] {"birch.png", "birchcolor.png"};
            foliageBirchColors = getCustomColors(s, astring2, 256, 256);
            String[] astring3 = new String[] {"swampgrass.png", "swampgrasscolor.png"};
            swampGrassColors = getCustomColors(s, astring3, 256, 256);
            String[] astring4 = new String[] {"swampfoliage.png", "swampfoliagecolor.png"};
            swampFoliageColors = getCustomColors(s, astring4, 256, 256);
            String[] astring5 = new String[] {"sky0.png", "skycolor0.png"};
            skyColors = getCustomColors(s, astring5, 256, 256);
            String[] astring6 = new String[] {"fog0.png", "fogcolor0.png"};
            fogColors = getCustomColors(s, astring6, 256, 256);
            String[] astring7 = new String[] {"underwater.png", "underwatercolor.png"};
            underwaterColors = getCustomColors(s, astring7, 256, 256);
            String[] astring8 = new String[] {"redstone.png", "redstonecolor.png"};
            redstoneColors = getCustomColors(s, astring8, 16, 1);
            xpOrbColors = getCustomColors(s + "xporb.png", -1, -1);
            String[] astring9 = new String[] {"stem.png", "stemcolor.png"};
            stemColors = getCustomColors(s, astring9, 8, 1);
            stemPumpkinColors = getCustomColors(s + "pumpkinstem.png", 8, 1);
            stemMelonColors = getCustomColors(s + "melonstem.png", 8, 1);
            String[] astring10 = new String[] {"myceliumparticle.png", "myceliumparticlecolor.png"};
            myceliumParticleColors = getCustomColors(s, astring10, -1, -1);
            Pair<CustomColormap[], Integer> pair = parseLightmapsRgb();
            lightMapsColorsRgb = (CustomColormap[])pair.getLeft();
            lightmapMinDimensionId = ((Integer)pair.getRight()).intValue();
            readColorProperties("mcpatcher/color.properties");
            updateUseDefaultColorMultiplier();
        }
    }

    private static Pair<CustomColormap[], Integer> parseLightmapsRgb()
    {
        String s = "mcpatcher/lightmap/world";
        String s1 = ".png";
        String[] astring = ResUtils.collectFiles(s, s1);
        Map<Integer, String> map = new HashMap();

        for (int i = 0; i < astring.length; ++i)
        {
            String s2 = astring[i];
            String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
            int j = Config.parseInt(s3, Integer.MIN_VALUE);

            if (j == Integer.MIN_VALUE)
            {
                warn("Invalid dimension ID: " + s3 + ", path: " + s2);
            }
            else
            {
                map.put(Integer.valueOf(j), s2);
            }
        }

        Set<Integer> set = map.keySet();
        Integer[] ainteger = (Integer[])set.toArray(new Integer[set.size()]);
        Arrays.sort((Object[])ainteger);

        if (ainteger.length <= 0)
        {
            return new ImmutablePair((Object)null, Integer.valueOf(0));
        }
        else
        {
            int j1 = ainteger[0].intValue();
            int k1 = ainteger[ainteger.length - 1].intValue();
            int k = k1 - j1 + 1;
            CustomColormap[] acustomcolormap = new CustomColormap[k];

            for (int l = 0; l < ainteger.length; ++l)
            {
                Integer integer = ainteger[l];
                String s4 = (String)map.get(integer);
                CustomColormap customcolormap = getCustomColors(s4, -1, -1);

                if (customcolormap != null)
                {
                    if (customcolormap.getWidth() < 16)
                    {
                        warn("Invalid lightmap width: " + customcolormap.getWidth() + ", path: " + s4);
                    }
                    else
                    {
                        int i1 = integer.intValue() - j1;
                        acustomcolormap[i1] = customcolormap;
                    }
                }
            }

            return new ImmutablePair(acustomcolormap, Integer.valueOf(j1));
        }
    }

    private static int getTextureHeight(String p_getTextureHeight_0_, int p_getTextureHeight_1_)
    {
        try
        {
            InputStream inputstream = Config.getResourceStream(new ResourceLocation(p_getTextureHeight_0_));

            if (inputstream == null)
            {
                return p_getTextureHeight_1_;
            }
            else
            {
                BufferedImage bufferedimage = ImageIO.read(inputstream);
                inputstream.close();
                return bufferedimage == null ? p_getTextureHeight_1_ : bufferedimage.getHeight();
            }
        }
        catch (IOException var4)
        {
            return p_getTextureHeight_1_;
        }
    }

    private static void readColorProperties(String p_readColorProperties_0_)
    {
        try
        {
            ResourceLocation resourcelocation = new ResourceLocation(p_readColorProperties_0_);
            InputStream inputstream = Config.getResourceStream(resourcelocation);

            if (inputstream == null)
            {
                return;
            }

            dbg("Loading " + p_readColorProperties_0_);
            Properties properties = new Properties();
            properties.load(inputstream);
            inputstream.close();
            particleWaterColor = readColor(properties, new String[] {"particle.water", "drop.water"});
            particlePortalColor = readColor(properties, "particle.portal");
            lilyPadColor = readColor(properties, "lilypad");
            expBarTextColor = readColor(properties, "text.xpbar");
            bossTextColor = readColor(properties, "text.boss");
            signTextColor = readColor(properties, "text.sign");
            fogColorNether = readColorVec3(properties, "fog.nether");
            fogColorEnd = readColorVec3(properties, "fog.end");
            skyColorEnd = readColorVec3(properties, "sky.end");
            readCustomPalettes(properties, p_readColorProperties_0_);
            spawnEggPrimaryColors = readSpawnEggColors(properties, p_readColorProperties_0_, "egg.shell.", "Spawn egg shell");
            spawnEggSecondaryColors = readSpawnEggColors(properties, p_readColorProperties_0_, "egg.spots.", "Spawn egg spot");
            wolfCollarColors = readDyeColors(properties, p_readColorProperties_0_, "collar.", "Wolf collar");
            sheepColors = readDyeColors(properties, p_readColorProperties_0_, "sheep.", "Sheep");
            textColors = readTextColors(properties, p_readColorProperties_0_, "text.code.", "Text");
            int[] aint = readMapColors(properties, p_readColorProperties_0_, "map.", "Map");

            if (aint != null)
            {
                if (mapColorsOriginal == null)
                {
                    mapColorsOriginal = getMapColors();
                }

                setMapColors(aint);
            }

            potionColors = readPotionColors(properties, p_readColorProperties_0_, "potion.", "Potion");
        }
        catch (FileNotFoundException var5)
        {
            return;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private static void readCustomPalettes(Properties p_readCustomPalettes_0_, String p_readCustomPalettes_1_)
    {
        blockPalettes = new int[256][1];

        for (int i = 0; i < 256; ++i)
        {
            blockPalettes[i][0] = -1;
        }

        String s7 = "palette.block.";
        Map map = new HashMap();

        for (Object s : p_readCustomPalettes_0_.keySet())
        {
            String s1 = p_readCustomPalettes_0_.getProperty((String) s);

            if (((String) s).startsWith(s7))
            {
                map.put(s, s1);
            }
        }

        String[] astring2 = (String[])((String[])map.keySet().toArray(new String[map.size()]));
        paletteColors = new CustomColormap[astring2.length];

        for (int l = 0; l < astring2.length; ++l)
        {
            String s8 = astring2[l];
            String s2 = p_readCustomPalettes_0_.getProperty(s8);
            dbg("Block palette: " + s8 + " = " + s2);
            String s3 = s8.substring(s7.length());
            String s4 = TextureUtils.getBasePath(p_readCustomPalettes_1_);
            s3 = TextureUtils.fixResourcePath(s3, s4);
            CustomColormap customcolormap = getCustomColors(s3, 256, 256);
            paletteColors[l] = customcolormap;
            String[] astring = Config.tokenize(s2, " ,;");

            for (int j = 0; j < astring.length; ++j)
            {
                String s5 = astring[j];
                int k = -1;

                if (s5.contains(":"))
                {
                    String[] astring1 = Config.tokenize(s5, ":");
                    s5 = astring1[0];
                    String s6 = astring1[1];
                    k = Config.parseInt(s6, -1);

                    if (k < 0 || k > 15)
                    {
                        warn("Invalid block metadata: " + s5 + " in palette: " + s8);
                        continue;
                    }
                }

                int i1 = Config.parseInt(s5, -1);

                if (i1 >= 0 && i1 <= 255)
                {
                    if (i1 != Block.getIdFromBlock(Blocks.grass) && i1 != Block.getIdFromBlock(Blocks.tallgrass) && i1 != Block.getIdFromBlock(Blocks.leaves) && i1 != Block.getIdFromBlock(Blocks.vine))
                    {
                        if (k == -1)
                        {
                            blockPalettes[i1][0] = l;
                        }
                        else
                        {
                            if (blockPalettes[i1].length < 16)
                            {
                                blockPalettes[i1] = new int[16];
                                Arrays.fill((int[])blockPalettes[i1], (int) - 1);
                            }

                            blockPalettes[i1][k] = l;
                        }
                    }
                }
                else
                {
                    warn("Invalid block index: " + i1 + " in palette: " + s8);
                }
            }
        }
    }

    private static int readColor(Properties p_readColor_0_, String[] p_readColor_1_)
    {
        for (int i = 0; i < p_readColor_1_.length; ++i)
        {
            String s = p_readColor_1_[i];
            int j = readColor(p_readColor_0_, s);

            if (j >= 0)
            {
                return j;
            }
        }

        return -1;
    }

    private static int readColor(Properties p_readColor_0_, String p_readColor_1_)
    {
        String s = p_readColor_0_.getProperty(p_readColor_1_);

        if (s == null)
        {
            return -1;
        }
        else
        {
            s = s.trim();
            int i = parseColor(s);

            if (i < 0)
            {
                warn("Invalid color: " + p_readColor_1_ + " = " + s);
                return i;
            }
            else
            {
                dbg(p_readColor_1_ + " = " + s);
                return i;
            }
        }
    }

    private static int parseColor(String p_parseColor_0_)
    {
        if (p_parseColor_0_ == null)
        {
            return -1;
        }
        else
        {
            p_parseColor_0_ = p_parseColor_0_.trim();

            try
            {
                int i = Integer.parseInt(p_parseColor_0_, 16) & 16777215;
                return i;
            }
            catch (NumberFormatException var2)
            {
                return -1;
            }
        }
    }

    private static Vec3 readColorVec3(Properties p_readColorVec3_0_, String p_readColorVec3_1_)
    {
        int i = readColor(p_readColorVec3_0_, p_readColorVec3_1_);

        if (i < 0)
        {
            return null;
        }
        else
        {
            int j = i >> 16 & 255;
            int k = i >> 8 & 255;
            int l = i & 255;
            float f = (float)j / 255.0F;
            float f1 = (float)k / 255.0F;
            float f2 = (float)l / 255.0F;
            return new Vec3((double)f, (double)f1, (double)f2);
        }
    }

    private static CustomColormap getCustomColors(String p_getCustomColors_0_, String[] p_getCustomColors_1_, int p_getCustomColors_2_, int p_getCustomColors_3_)
    {
        for (int i = 0; i < p_getCustomColors_1_.length; ++i)
        {
            String s = p_getCustomColors_1_[i];
            s = p_getCustomColors_0_ + s;
            CustomColormap customcolormap = getCustomColors(s, p_getCustomColors_2_, p_getCustomColors_3_);

            if (customcolormap != null)
            {
                return customcolormap;
            }
        }

        return null;
    }

    public static CustomColormap getCustomColors(String p_getCustomColors_0_, int p_getCustomColors_1_, int p_getCustomColors_2_)
    {
        try
        {
            ResourceLocation resourcelocation = new ResourceLocation(p_getCustomColors_0_);

            if (!Config.hasResource(resourcelocation))
            {
                return null;
            }
            else
            {
                dbg("Colormap " + p_getCustomColors_0_);
                Properties properties = new Properties();
                String s = StrUtils.replaceSuffix(p_getCustomColors_0_, ".png", ".properties");
                ResourceLocation resourcelocation1 = new ResourceLocation(s);

                if (Config.hasResource(resourcelocation1))
                {
                    InputStream inputstream = Config.getResourceStream(resourcelocation1);
                    properties.load(inputstream);
                    inputstream.close();
                    dbg("Colormap properties: " + s);
                }
                else
                {
                    properties.put("format", "vanilla");
                    properties.put("source", p_getCustomColors_0_);
                    s = p_getCustomColors_0_;
                }

                CustomColormap customcolormap = new CustomColormap(properties, s, p_getCustomColors_1_, p_getCustomColors_2_);
                return !customcolormap.isValid(s) ? null : customcolormap;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    public static void updateUseDefaultColorMultiplier()
    {
        useDefaultColorMultiplier = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes();
    }

    public static int getColorMultiplier(BakedQuad p_getColorMultiplier_0_, Block p_getColorMultiplier_1_, IBlockAccess p_getColorMultiplier_2_, BlockPos p_getColorMultiplier_3_, RenderEnv p_getColorMultiplier_4_)
    {
        if (useDefaultColorMultiplier)
        {
            return -1;
        }
        else
        {
            CustomColormap customcolormap = null;
            CustomColormap customcolormap1 = null;

            if (blockPalettes != null)
            {
                int i = p_getColorMultiplier_4_.getBlockId();

                if (i >= 0 && i < 256)
                {
                    int[] aint = blockPalettes[i];
                    int j = -1;

                    if (aint.length > 1)
                    {
                        int k = p_getColorMultiplier_4_.getMetadata();
                        j = aint[k];
                    }
                    else
                    {
                        j = aint[0];
                    }

                    if (j >= 0)
                    {
                        customcolormap = paletteColors[j];
                    }
                }

                if (customcolormap != null)
                {
                    if (Config.isSmoothBiomes())
                    {
                        return getSmoothColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, customcolormap, customcolormap, 0, 0, p_getColorMultiplier_4_);
                    }

                    return customcolormap.getColor(p_getColorMultiplier_2_, p_getColorMultiplier_3_);
                }
            }

            if (!p_getColorMultiplier_0_.hasTintIndex())
            {
                return -1;
            }
            else if (p_getColorMultiplier_1_ == Blocks.waterlily)
            {
                return getLilypadColorMultiplier(p_getColorMultiplier_2_, p_getColorMultiplier_3_);
            }
            else if (p_getColorMultiplier_1_ instanceof BlockStem)
            {
                return getStemColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, p_getColorMultiplier_4_);
            }
            else
            {
                boolean flag = Config.isSwampColors();
                boolean flag1 = false;
                int l = 0;
                int i1 = 0;

                if (p_getColorMultiplier_1_ != Blocks.grass && p_getColorMultiplier_1_ != Blocks.tallgrass)
                {
                    if (p_getColorMultiplier_1_ == Blocks.leaves)
                    {
                        l = 2;
                        flag1 = Config.isSmoothBiomes();
                        i1 = p_getColorMultiplier_4_.getMetadata();

                        if ((i1 & 3) == 1)
                        {
                            customcolormap = foliagePineColors;
                        }
                        else if ((i1 & 3) == 2)
                        {
                            customcolormap = foliageBirchColors;
                        }
                        else
                        {
                            customcolormap = foliageColors;

                            if (flag)
                            {
                                customcolormap1 = swampFoliageColors;
                            }
                            else
                            {
                                customcolormap1 = customcolormap;
                            }
                        }
                    }
                    else if (p_getColorMultiplier_1_ == Blocks.vine)
                    {
                        l = 2;
                        flag1 = Config.isSmoothBiomes();
                        customcolormap = foliageColors;

                        if (flag)
                        {
                            customcolormap1 = swampFoliageColors;
                        }
                        else
                        {
                            customcolormap1 = customcolormap;
                        }
                    }
                }
                else
                {
                    l = 1;
                    flag1 = Config.isSmoothBiomes();
                    customcolormap = grassColors;

                    if (flag)
                    {
                        customcolormap1 = swampGrassColors;
                    }
                    else
                    {
                        customcolormap1 = customcolormap;
                    }
                }

                if (flag1)
                {
                    return getSmoothColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, customcolormap, customcolormap1, l, i1, p_getColorMultiplier_4_);
                }
                else
                {
                    if (customcolormap1 != customcolormap && p_getColorMultiplier_2_.getBiomeGenForCoords(p_getColorMultiplier_3_) == BiomeGenBase.swampland)
                    {
                        customcolormap = customcolormap1;
                    }

                    return customcolormap != null ? customcolormap.getColor(p_getColorMultiplier_2_, p_getColorMultiplier_3_) : -1;
                }
            }
        }
    }

    private static int getSmoothColorMultiplier(Block p_getSmoothColorMultiplier_0_, IBlockAccess p_getSmoothColorMultiplier_1_, BlockPos p_getSmoothColorMultiplier_2_, CustomColormap p_getSmoothColorMultiplier_3_, CustomColormap p_getSmoothColorMultiplier_4_, int p_getSmoothColorMultiplier_5_, int p_getSmoothColorMultiplier_6_, RenderEnv p_getSmoothColorMultiplier_7_)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = p_getSmoothColorMultiplier_2_.getX();
        int i1 = p_getSmoothColorMultiplier_2_.getY();
        int j1 = p_getSmoothColorMultiplier_2_.getZ();
        BlockPosM blockposm = p_getSmoothColorMultiplier_7_.getColorizerBlockPos();

        for (int k1 = l - 1; k1 <= l + 1; ++k1)
        {
            for (int l1 = j1 - 1; l1 <= j1 + 1; ++l1)
            {
                blockposm.setXyz(k1, i1, l1);
                CustomColormap customcolormap = p_getSmoothColorMultiplier_3_;

                if (p_getSmoothColorMultiplier_4_ != p_getSmoothColorMultiplier_3_ && p_getSmoothColorMultiplier_1_.getBiomeGenForCoords(blockposm) == BiomeGenBase.swampland)
                {
                    customcolormap = p_getSmoothColorMultiplier_4_;
                }

                int i2 = 0;

                if (customcolormap == null)
                {
                    switch (p_getSmoothColorMultiplier_5_)
                    {
                        case 1:
                            i2 = p_getSmoothColorMultiplier_1_.getBiomeGenForCoords(blockposm).getGrassColorAtPos(blockposm);
                            break;

                        case 2:
                            if ((p_getSmoothColorMultiplier_6_ & 3) == 1)
                            {
                                i2 = ColorizerFoliage.getFoliageColorPine();
                            }
                            else if ((p_getSmoothColorMultiplier_6_ & 3) == 2)
                            {
                                i2 = ColorizerFoliage.getFoliageColorBirch();
                            }
                            else
                            {
                                i2 = p_getSmoothColorMultiplier_1_.getBiomeGenForCoords(blockposm).getFoliageColorAtPos(blockposm);
                            }

                            break;

                        default:
                            i2 = p_getSmoothColorMultiplier_0_.colorMultiplier(p_getSmoothColorMultiplier_1_, blockposm);
                    }
                }
                else
                {
                    i2 = customcolormap.getColor(p_getSmoothColorMultiplier_1_, blockposm);
                }

                i += i2 >> 16 & 255;
                j += i2 >> 8 & 255;
                k += i2 & 255;
            }
        }

        int j2 = i / 9;
        int k2 = j / 9;
        int l2 = k / 9;
        return j2 << 16 | k2 << 8 | l2;
    }

    public static int getFluidColor(Block p_getFluidColor_0_, IBlockAccess p_getFluidColor_1_, BlockPos p_getFluidColor_2_)
    {
        return p_getFluidColor_0_.getMaterial() != Material.water ? p_getFluidColor_0_.colorMultiplier(p_getFluidColor_1_, p_getFluidColor_2_) : (waterColors != null ? (Config.isSmoothBiomes() ? waterColors.getColorSmooth(p_getFluidColor_1_, (double)p_getFluidColor_2_.getX(), (double)p_getFluidColor_2_.getY(), (double)p_getFluidColor_2_.getZ(), 3, 1) : waterColors.getColor(p_getFluidColor_1_, p_getFluidColor_2_)) : (!Config.isSwampColors() ? 16777215 : p_getFluidColor_0_.colorMultiplier(p_getFluidColor_1_, p_getFluidColor_2_)));
    }

    public static void updatePortalFX(EntityFX p_updatePortalFX_0_)
    {
        if (particlePortalColor >= 0)
        {
            int i = particlePortalColor;
            int j = i >> 16 & 255;
            int k = i >> 8 & 255;
            int l = i & 255;
            float f = (float)j / 255.0F;
            float f1 = (float)k / 255.0F;
            float f2 = (float)l / 255.0F;
            p_updatePortalFX_0_.setRBGColorF(f, f1, f2);
        }
    }

    public static void updateMyceliumFX(EntityFX p_updateMyceliumFX_0_)
    {
        if (myceliumParticleColors != null)
        {
            int i = myceliumParticleColors.getColorRandom();
            int j = i >> 16 & 255;
            int k = i >> 8 & 255;
            int l = i & 255;
            float f = (float)j / 255.0F;
            float f1 = (float)k / 255.0F;
            float f2 = (float)l / 255.0F;
            p_updateMyceliumFX_0_.setRBGColorF(f, f1, f2);
        }
    }

    public static void updateReddustFX(EntityFX p_updateReddustFX_0_, IBlockAccess p_updateReddustFX_1_, double p_updateReddustFX_2_, double p_updateReddustFX_4_, double p_updateReddustFX_6_)
    {
        if (redstoneColors != null)
        {
            IBlockState iblockstate = p_updateReddustFX_1_.getBlockState(new BlockPos(p_updateReddustFX_2_, p_updateReddustFX_4_, p_updateReddustFX_6_));
            int i = getRedstoneLevel(iblockstate, 15);
            int j = redstoneColors.getColor(i);
            int k = j >> 16 & 255;
            int l = j >> 8 & 255;
            int i1 = j & 255;
            float f = (float)k / 255.0F;
            float f1 = (float)l / 255.0F;
            float f2 = (float)i1 / 255.0F;
            p_updateReddustFX_0_.setRBGColorF(f, f1, f2);
        }
    }

    private static int getRedstoneLevel(IBlockState p_getRedstoneLevel_0_, int p_getRedstoneLevel_1_)
    {
        Block block = p_getRedstoneLevel_0_.getBlock();

        if (!(block instanceof BlockRedstoneWire))
        {
            return p_getRedstoneLevel_1_;
        }
        else
        {
            Object object = p_getRedstoneLevel_0_.getValue(BlockRedstoneWire.POWER);

            if (!(object instanceof Integer))
            {
                return p_getRedstoneLevel_1_;
            }
            else
            {
                Integer integer = (Integer)object;
                return integer.intValue();
            }
        }
    }

    public static int getXpOrbColor(float p_getXpOrbColor_0_)
    {
        if (xpOrbColors == null)
        {
            return -1;
        }
        else
        {
            int i = (int)((double)((MathHelper.sin(p_getXpOrbColor_0_) + 1.0F) * (float)(xpOrbColors.getLength() - 1)) / 2.0D);
            int j = xpOrbColors.getColor(i);
            return j;
        }
    }

    public static void updateWaterFX(EntityFX p_updateWaterFX_0_, IBlockAccess p_updateWaterFX_1_, double p_updateWaterFX_2_, double p_updateWaterFX_4_, double p_updateWaterFX_6_)
    {
        if (waterColors != null)
        {
            int i = getFluidColor(Blocks.water, p_updateWaterFX_1_, new BlockPos(p_updateWaterFX_2_, p_updateWaterFX_4_, p_updateWaterFX_6_));
            int j = i >> 16 & 255;
            int k = i >> 8 & 255;
            int l = i & 255;
            float f = (float)j / 255.0F;
            float f1 = (float)k / 255.0F;
            float f2 = (float)l / 255.0F;

            if (particleWaterColor >= 0)
            {
                int i1 = particleWaterColor >> 16 & 255;
                int j1 = particleWaterColor >> 8 & 255;
                int k1 = particleWaterColor & 255;
                f *= (float)i1 / 255.0F;
                f1 *= (float)j1 / 255.0F;
                f2 *= (float)k1 / 255.0F;
            }

            p_updateWaterFX_0_.setRBGColorF(f, f1, f2);
        }
    }

    private static int getLilypadColorMultiplier(IBlockAccess p_getLilypadColorMultiplier_0_, BlockPos p_getLilypadColorMultiplier_1_)
    {
        return lilyPadColor < 0 ? Blocks.waterlily.colorMultiplier(p_getLilypadColorMultiplier_0_, p_getLilypadColorMultiplier_1_) : lilyPadColor;
    }

    private static Vec3 getFogColorNether(Vec3 p_getFogColorNether_0_)
    {
        return fogColorNether == null ? p_getFogColorNether_0_ : fogColorNether;
    }

    private static Vec3 getFogColorEnd(Vec3 p_getFogColorEnd_0_)
    {
        return fogColorEnd == null ? p_getFogColorEnd_0_ : fogColorEnd;
    }

    private static Vec3 getSkyColorEnd(Vec3 p_getSkyColorEnd_0_)
    {
        return skyColorEnd == null ? p_getSkyColorEnd_0_ : skyColorEnd;
    }

    public static Vec3 getSkyColor(Vec3 p_getSkyColor_0_, IBlockAccess p_getSkyColor_1_, double p_getSkyColor_2_, double p_getSkyColor_4_, double p_getSkyColor_6_)
    {
        if (skyColors == null)
        {
            return p_getSkyColor_0_;
        }
        else
        {
            int i = skyColors.getColorSmooth(p_getSkyColor_1_, p_getSkyColor_2_, p_getSkyColor_4_, p_getSkyColor_6_, 7, 1);
            int j = i >> 16 & 255;
            int k = i >> 8 & 255;
            int l = i & 255;
            float f = (float)j / 255.0F;
            float f1 = (float)k / 255.0F;
            float f2 = (float)l / 255.0F;
            float f3 = (float)p_getSkyColor_0_.xCoord / 0.5F;
            float f4 = (float)p_getSkyColor_0_.yCoord / 0.66275F;
            float f5 = (float)p_getSkyColor_0_.zCoord;
            f = f * f3;
            f1 = f1 * f4;
            f2 = f2 * f5;
            return new Vec3((double)f, (double)f1, (double)f2);
        }
    }

    private static Vec3 getFogColor(Vec3 p_getFogColor_0_, IBlockAccess p_getFogColor_1_, double p_getFogColor_2_, double p_getFogColor_4_, double p_getFogColor_6_)
    {
        if (fogColors == null)
        {
            return p_getFogColor_0_;
        }
        else
        {
            int i = fogColors.getColorSmooth(p_getFogColor_1_, p_getFogColor_2_, p_getFogColor_4_, p_getFogColor_6_, 7, 1);
            int j = i >> 16 & 255;
            int k = i >> 8 & 255;
            int l = i & 255;
            float f = (float)j / 255.0F;
            float f1 = (float)k / 255.0F;
            float f2 = (float)l / 255.0F;
            float f3 = (float)p_getFogColor_0_.xCoord / 0.753F;
            float f4 = (float)p_getFogColor_0_.yCoord / 0.8471F;
            float f5 = (float)p_getFogColor_0_.zCoord;
            f = f * f3;
            f1 = f1 * f4;
            f2 = f2 * f5;
            return new Vec3((double)f, (double)f1, (double)f2);
        }
    }

    public static Vec3 getUnderwaterColor(IBlockAccess p_getUnderwaterColor_0_, double p_getUnderwaterColor_1_, double p_getUnderwaterColor_3_, double p_getUnderwaterColor_5_)
    {
        if (underwaterColors == null)
        {
            return null;
        }
        else
        {
            int i = underwaterColors.getColorSmooth(p_getUnderwaterColor_0_, p_getUnderwaterColor_1_, p_getUnderwaterColor_3_, p_getUnderwaterColor_5_, 7, 1);
            int j = i >> 16 & 255;
            int k = i >> 8 & 255;
            int l = i & 255;
            float f = (float)j / 255.0F;
            float f1 = (float)k / 255.0F;
            float f2 = (float)l / 255.0F;
            return new Vec3((double)f, (double)f1, (double)f2);
        }
    }

    private static int averageColor(int p_averageColor_0_, int p_averageColor_1_)
    {
        int i = p_averageColor_0_ >> 16 & 255;
        int j = p_averageColor_0_ >> 8 & 255;
        int k = p_averageColor_0_ & 255;
        int l = p_averageColor_1_ >> 16 & 255;
        int i1 = p_averageColor_1_ >> 8 & 255;
        int j1 = p_averageColor_1_ & 255;
        int k1 = (i + l) / 2;
        int l1 = (j + i1) / 2;
        int i2 = (k + j1) / 2;
        return k1 << 16 | l1 << 8 | i2;
    }

    private static int getStemColorMultiplier(Block p_getStemColorMultiplier_0_, IBlockAccess p_getStemColorMultiplier_1_, BlockPos p_getStemColorMultiplier_2_, RenderEnv p_getStemColorMultiplier_3_)
    {
        CustomColormap customcolormap = stemColors;

        if (p_getStemColorMultiplier_0_ == Blocks.pumpkin_stem && stemPumpkinColors != null)
        {
            customcolormap = stemPumpkinColors;
        }

        if (p_getStemColorMultiplier_0_ == Blocks.melon_stem && stemMelonColors != null)
        {
            customcolormap = stemMelonColors;
        }

        if (customcolormap == null)
        {
            return p_getStemColorMultiplier_0_.colorMultiplier(p_getStemColorMultiplier_1_, p_getStemColorMultiplier_2_);
        }
        else
        {
            int i = p_getStemColorMultiplier_3_.getMetadata();
            return customcolormap.getColor(i);
        }
    }

    public static boolean updateLightmap(World p_updateLightmap_0_, float p_updateLightmap_1_, int[] p_updateLightmap_2_, boolean p_updateLightmap_3_)
    {
        if (p_updateLightmap_0_ == null)
        {
            return false;
        }
        else if (lightMapsColorsRgb == null)
        {
            return false;
        }
        else if (!Config.isCustomColors())
        {
            return false;
        }
        else
        {
            int i = p_updateLightmap_0_.provider.getDimensionId();
            int j = i - lightmapMinDimensionId;

            if (j >= 0 && j < lightMapsColorsRgb.length)
            {
                CustomColormap customcolormap = lightMapsColorsRgb[j];

                if (customcolormap == null)
                {
                    return false;
                }
                else
                {
                    int k = customcolormap.getHeight();

                    if (p_updateLightmap_3_ && k < 64)
                    {
                        return false;
                    }
                    else
                    {
                        int l = customcolormap.getWidth();

                        if (l < 16)
                        {
                            warn("Invalid lightmap width: " + l + " for dimension: " + i);
                            lightMapsColorsRgb[j] = null;
                            return false;
                        }
                        else
                        {
                            int i1 = 0;

                            if (p_updateLightmap_3_)
                            {
                                i1 = l * 16 * 2;
                            }

                            float f = 1.1666666F * (p_updateLightmap_0_.getSunBrightness(1.0F) - 0.2F);

                            if (p_updateLightmap_0_.getLastLightningBolt() > 0)
                            {
                                f = 1.0F;
                            }

                            f = Config.limitTo1(f);
                            float f1 = f * (float)(l - 1);
                            float f2 = Config.limitTo1(p_updateLightmap_1_ + 0.5F) * (float)(l - 1);
                            float f3 = Config.limitTo1(Config.getGameSettings().gammaSetting);
                            boolean flag = f3 > 1.0E-4F;
                            float[][] afloat = customcolormap.getColorsRgb();
                            getLightMapColumn(afloat, f1, i1, l, sunRgbs);
                            getLightMapColumn(afloat, f2, i1 + 16 * l, l, torchRgbs);
                            float[] afloat1 = new float[3];

                            for (int j1 = 0; j1 < 16; ++j1)
                            {
                                for (int k1 = 0; k1 < 16; ++k1)
                                {
                                    for (int l1 = 0; l1 < 3; ++l1)
                                    {
                                        float f4 = Config.limitTo1(sunRgbs[j1][l1] + torchRgbs[k1][l1]);

                                        if (flag)
                                        {
                                            float f5 = 1.0F - f4;
                                            f5 = 1.0F - f5 * f5 * f5 * f5;
                                            f4 = f3 * f5 + (1.0F - f3) * f4;
                                        }

                                        afloat1[l1] = f4;
                                    }

                                    int i2 = (int)(afloat1[0] * 255.0F);
                                    int j2 = (int)(afloat1[1] * 255.0F);
                                    int k2 = (int)(afloat1[2] * 255.0F);
                                    p_updateLightmap_2_[j1 * 16 + k1] = -16777216 | i2 << 16 | j2 << 8 | k2;
                                }
                            }

                            return true;
                        }
                    }
                }
            }
            else
            {
                return false;
            }
        }
    }

    private static void getLightMapColumn(float[][] p_getLightMapColumn_0_, float p_getLightMapColumn_1_, int p_getLightMapColumn_2_, int p_getLightMapColumn_3_, float[][] p_getLightMapColumn_4_)
    {
        int i = (int)Math.floor((double)p_getLightMapColumn_1_);
        int j = (int)Math.ceil((double)p_getLightMapColumn_1_);

        if (i == j)
        {
            for (int i1 = 0; i1 < 16; ++i1)
            {
                float[] afloat3 = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + i1 * p_getLightMapColumn_3_ + i];
                float[] afloat4 = p_getLightMapColumn_4_[i1];

                for (int j1 = 0; j1 < 3; ++j1)
                {
                    afloat4[j1] = afloat3[j1];
                }
            }
        }
        else
        {
            float f = 1.0F - (p_getLightMapColumn_1_ - (float)i);
            float f1 = 1.0F - ((float)j - p_getLightMapColumn_1_);

            for (int k = 0; k < 16; ++k)
            {
                float[] afloat = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + k * p_getLightMapColumn_3_ + i];
                float[] afloat1 = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + k * p_getLightMapColumn_3_ + j];
                float[] afloat2 = p_getLightMapColumn_4_[k];

                for (int l = 0; l < 3; ++l)
                {
                    afloat2[l] = afloat[l] * f + afloat1[l] * f1;
                }
            }
        }
    }

    public static Vec3 getWorldFogColor(Vec3 p_getWorldFogColor_0_, WorldClient p_getWorldFogColor_1_, Entity p_getWorldFogColor_2_, float p_getWorldFogColor_3_)
    {
        int i = p_getWorldFogColor_1_.provider.getDimensionId();

        switch (i)
        {
            case -1:
                p_getWorldFogColor_0_ = getFogColorNether(p_getWorldFogColor_0_);
                break;

            case 0:
                Minecraft minecraft = Minecraft.getMinecraft();
                p_getWorldFogColor_0_ = getFogColor(p_getWorldFogColor_0_, minecraft.theWorld, p_getWorldFogColor_2_.posX, p_getWorldFogColor_2_.posY + 1.0D, p_getWorldFogColor_2_.posZ);
                break;

            case 1:
                p_getWorldFogColor_0_ = getFogColorEnd(p_getWorldFogColor_0_);
        }

        return p_getWorldFogColor_0_;
    }

    public static Vec3 getWorldSkyColor(Vec3 p_getWorldSkyColor_0_, WorldClient p_getWorldSkyColor_1_, Entity p_getWorldSkyColor_2_, float p_getWorldSkyColor_3_)
    {
        int i = p_getWorldSkyColor_1_.provider.getDimensionId();

        switch (i)
        {
            case 0:
                Minecraft minecraft = Minecraft.getMinecraft();
                p_getWorldSkyColor_0_ = getSkyColor(p_getWorldSkyColor_0_, minecraft.theWorld, p_getWorldSkyColor_2_.posX, p_getWorldSkyColor_2_.posY + 1.0D, p_getWorldSkyColor_2_.posZ);
                break;

            case 1:
                p_getWorldSkyColor_0_ = getSkyColorEnd(p_getWorldSkyColor_0_);
        }

        return p_getWorldSkyColor_0_;
    }

    private static int[] readSpawnEggColors(Properties p_readSpawnEggColors_0_, String p_readSpawnEggColors_1_, String p_readSpawnEggColors_2_, String p_readSpawnEggColors_3_)
    {
        List<Integer> list = new ArrayList();
        Set set = p_readSpawnEggColors_0_.keySet();
        int i = 0;

        for (Object s : set)
        {
            String s1 = p_readSpawnEggColors_0_.getProperty((String) s);

            if (((String) s).startsWith(p_readSpawnEggColors_2_))
            {
                String s2 = StrUtils.removePrefix((String) s, p_readSpawnEggColors_2_);
                int j = getEntityId(s2);
                int k = parseColor(s1);

                if (j >= 0 && k >= 0)
                {
                    while (((List)list).size() <= j)
                    {
                        list.add(Integer.valueOf(-1));
                    }

                    list.set(j, Integer.valueOf(k));
                    ++i;
                }
                else
                {
                    warn("Invalid spawn egg color: " + s + " = " + s1);
                }
            }
        }

        if (i <= 0)
        {
            return null;
        }
        else
        {
            dbg(p_readSpawnEggColors_3_ + " colors: " + i);
            int[] aint = new int[list.size()];

            for (int l = 0; l < aint.length; ++l)
            {
                aint[l] = ((Integer)list.get(l)).intValue();
            }

            return aint;
        }
    }

    private static int getSpawnEggColor(ItemMonsterPlacer p_getSpawnEggColor_0_, ItemStack p_getSpawnEggColor_1_, int p_getSpawnEggColor_2_, int p_getSpawnEggColor_3_)
    {
        int i = p_getSpawnEggColor_1_.getMetadata();
        int[] aint = p_getSpawnEggColor_2_ == 0 ? spawnEggPrimaryColors : spawnEggSecondaryColors;

        if (aint == null)
        {
            return p_getSpawnEggColor_3_;
        }
        else if (i >= 0 && i < aint.length)
        {
            int j = aint[i];
            return j < 0 ? p_getSpawnEggColor_3_ : j;
        }
        else
        {
            return p_getSpawnEggColor_3_;
        }
    }

    public static int getColorFromItemStack(ItemStack p_getColorFromItemStack_0_, int p_getColorFromItemStack_1_, int p_getColorFromItemStack_2_)
    {
        if (p_getColorFromItemStack_0_ == null)
        {
            return p_getColorFromItemStack_2_;
        }
        else
        {
            Item item = p_getColorFromItemStack_0_.getItem();
            return item == null ? p_getColorFromItemStack_2_ : (item instanceof ItemMonsterPlacer ? getSpawnEggColor((ItemMonsterPlacer)item, p_getColorFromItemStack_0_, p_getColorFromItemStack_1_, p_getColorFromItemStack_2_) : p_getColorFromItemStack_2_);
        }
    }

    private static float[][] readDyeColors(Properties p_readDyeColors_0_, String p_readDyeColors_1_, String p_readDyeColors_2_, String p_readDyeColors_3_)
    {
        EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
        Map<String, EnumDyeColor> map = new HashMap();

        for (int i = 0; i < aenumdyecolor.length; ++i)
        {
            EnumDyeColor enumdyecolor = aenumdyecolor[i];
            map.put(enumdyecolor.getName(), enumdyecolor);
        }

        float[][] afloat1 = new float[aenumdyecolor.length][];
        int k = 0;

        for (Object s : p_readDyeColors_0_.keySet())
        {
            String s1 = p_readDyeColors_0_.getProperty((String) s);

            if (((String) s).startsWith(p_readDyeColors_2_))
            {
                String s2 = StrUtils.removePrefix((String) s, p_readDyeColors_2_);

                if (s2.equals("lightBlue"))
                {
                    s2 = "light_blue";
                }

                EnumDyeColor enumdyecolor1 = (EnumDyeColor)map.get(s2);
                int j = parseColor(s1);

                if (enumdyecolor1 != null && j >= 0)
                {
                    float[] afloat = new float[] {(float)(j >> 16 & 255) / 255.0F, (float)(j >> 8 & 255) / 255.0F, (float)(j & 255) / 255.0F};
                    afloat1[enumdyecolor1.ordinal()] = afloat;
                    ++k;
                }
                else
                {
                    warn("Invalid color: " + s + " = " + s1);
                }
            }
        }

        if (k <= 0)
        {
            return (float[][])null;
        }
        else
        {
            dbg(p_readDyeColors_3_ + " colors: " + k);
            return afloat1;
        }
    }

    private static float[] getDyeColors(EnumDyeColor p_getDyeColors_0_, float[][] p_getDyeColors_1_, float[] p_getDyeColors_2_)
    {
        if (p_getDyeColors_1_ == null)
        {
            return p_getDyeColors_2_;
        }
        else if (p_getDyeColors_0_ == null)
        {
            return p_getDyeColors_2_;
        }
        else
        {
            float[] afloat = p_getDyeColors_1_[p_getDyeColors_0_.ordinal()];
            return afloat == null ? p_getDyeColors_2_ : afloat;
        }
    }

    public static float[] getWolfCollarColors(EnumDyeColor p_getWolfCollarColors_0_, float[] p_getWolfCollarColors_1_)
    {
        return getDyeColors(p_getWolfCollarColors_0_, wolfCollarColors, p_getWolfCollarColors_1_);
    }

    public static float[] getSheepColors(EnumDyeColor p_getSheepColors_0_, float[] p_getSheepColors_1_)
    {
        return getDyeColors(p_getSheepColors_0_, sheepColors, p_getSheepColors_1_);
    }

    private static int[] readTextColors(Properties p_readTextColors_0_, String p_readTextColors_1_, String p_readTextColors_2_, String p_readTextColors_3_)
    {
        int[] aint = new int[32];
        Arrays.fill((int[])aint, (int) - 1);
        int i = 0;

        for (Object s : p_readTextColors_0_.keySet())
        {
            String s1 = p_readTextColors_0_.getProperty((String) s);

            if (((String) s).startsWith(p_readTextColors_2_))
            {
                String s2 = StrUtils.removePrefix((String) s, p_readTextColors_2_);
                int j = Config.parseInt(s2, -1);
                int k = parseColor(s1);

                if (j >= 0 && j < aint.length && k >= 0)
                {
                    aint[j] = k;
                    ++i;
                }
                else
                {
                    warn("Invalid color: " + s + " = " + s1);
                }
            }
        }

        if (i <= 0)
        {
            return null;
        }
        else
        {
            dbg(p_readTextColors_3_ + " colors: " + i);
            return aint;
        }
    }

    public static int getTextColor(int p_getTextColor_0_, int p_getTextColor_1_)
    {
        if (textColors == null)
        {
            return p_getTextColor_1_;
        }
        else if (p_getTextColor_0_ >= 0 && p_getTextColor_0_ < textColors.length)
        {
            int i = textColors[p_getTextColor_0_];
            return i < 0 ? p_getTextColor_1_ : i;
        }
        else
        {
            return p_getTextColor_1_;
        }
    }

    private static int[] readMapColors(Properties p_readMapColors_0_, String p_readMapColors_1_, String p_readMapColors_2_, String p_readMapColors_3_)
    {
        int[] aint = new int[MapColor.mapColorArray.length];
        Arrays.fill((int[])aint, (int) - 1);
        int i = 0;

        for (Object s : p_readMapColors_0_.keySet())
        {
            String s1 = p_readMapColors_0_.getProperty((String) s);

            if (((String) s).startsWith(p_readMapColors_2_))
            {
                String s2 = StrUtils.removePrefix((String) s, p_readMapColors_2_);
                int j = getMapColorIndex(s2);
                int k = parseColor(s1);

                if (j >= 0 && j < aint.length && k >= 0)
                {
                    aint[j] = k;
                    ++i;
                }
                else
                {
                    warn("Invalid color: " + s + " = " + s1);
                }
            }
        }

        if (i <= 0)
        {
            return null;
        }
        else
        {
            dbg(p_readMapColors_3_ + " colors: " + i);
            return aint;
        }
    }

    private static int[] readPotionColors(Properties p_readPotionColors_0_, String p_readPotionColors_1_, String p_readPotionColors_2_, String p_readPotionColors_3_)
    {
        int[] aint = new int[Potion.potionTypes.length];
        Arrays.fill((int[])aint, (int) - 1);
        int i = 0;

        for (Object s : p_readPotionColors_0_.keySet())
        {
            String s1 = p_readPotionColors_0_.getProperty((String) s);

            if (((String) s).startsWith(p_readPotionColors_2_))
            {
                int j = getPotionId((String) s);
                int k = parseColor(s1);

                if (j >= 0 && j < aint.length && k >= 0)
                {
                    aint[j] = k;
                    ++i;
                }
                else
                {
                    warn("Invalid color: " + s + " = " + s1);
                }
            }
        }

        if (i <= 0)
        {
            return null;
        }
        else
        {
            dbg(p_readPotionColors_3_ + " colors: " + i);
            return aint;
        }
    }

    private static int getPotionId(String p_getPotionId_0_)
    {
        if (p_getPotionId_0_.equals("potion.water"))
        {
            return 0;
        }
        else
        {
            Potion[] apotion = Potion.potionTypes;

            for (int i = 0; i < apotion.length; ++i)
            {
                Potion potion = apotion[i];

                if (potion != null && potion.getName().equals(p_getPotionId_0_))
                {
                    return potion.getId();
                }
            }

            return -1;
        }
    }

    public static int getPotionColor(int p_getPotionColor_0_, int p_getPotionColor_1_)
    {
        if (potionColors == null)
        {
            return p_getPotionColor_1_;
        }
        else if (p_getPotionColor_0_ >= 0 && p_getPotionColor_0_ < potionColors.length)
        {
            int i = potionColors[p_getPotionColor_0_];
            return i < 0 ? p_getPotionColor_1_ : i;
        }
        else
        {
            return p_getPotionColor_1_;
        }
    }

    private static int getMapColorIndex(String p_getMapColorIndex_0_)
    {
        return p_getMapColorIndex_0_ == null ? -1 : (p_getMapColorIndex_0_.equals("air") ? MapColor.airColor.colorIndex : (p_getMapColorIndex_0_.equals("grass") ? MapColor.grassColor.colorIndex : (p_getMapColorIndex_0_.equals("sand") ? MapColor.sandColor.colorIndex : (p_getMapColorIndex_0_.equals("cloth") ? MapColor.clothColor.colorIndex : (p_getMapColorIndex_0_.equals("tnt") ? MapColor.tntColor.colorIndex : (p_getMapColorIndex_0_.equals("ice") ? MapColor.iceColor.colorIndex : (p_getMapColorIndex_0_.equals("iron") ? MapColor.ironColor.colorIndex : (p_getMapColorIndex_0_.equals("foliage") ? MapColor.foliageColor.colorIndex : (p_getMapColorIndex_0_.equals("snow") ? MapColor.snowColor.colorIndex : (p_getMapColorIndex_0_.equals("clay") ? MapColor.clayColor.colorIndex : (p_getMapColorIndex_0_.equals("dirt") ? MapColor.dirtColor.colorIndex : (p_getMapColorIndex_0_.equals("stone") ? MapColor.stoneColor.colorIndex : (p_getMapColorIndex_0_.equals("water") ? MapColor.waterColor.colorIndex : (p_getMapColorIndex_0_.equals("wood") ? MapColor.woodColor.colorIndex : (p_getMapColorIndex_0_.equals("quartz") ? MapColor.quartzColor.colorIndex : (p_getMapColorIndex_0_.equals("adobe") ? MapColor.adobeColor.colorIndex : (p_getMapColorIndex_0_.equals("magenta") ? MapColor.magentaColor.colorIndex : (p_getMapColorIndex_0_.equals("lightBlue") ? MapColor.lightBlueColor.colorIndex : (p_getMapColorIndex_0_.equals("light_blue") ? MapColor.lightBlueColor.colorIndex : (p_getMapColorIndex_0_.equals("yellow") ? MapColor.yellowColor.colorIndex : (p_getMapColorIndex_0_.equals("lime") ? MapColor.limeColor.colorIndex : (p_getMapColorIndex_0_.equals("pink") ? MapColor.pinkColor.colorIndex : (p_getMapColorIndex_0_.equals("gray") ? MapColor.grayColor.colorIndex : (p_getMapColorIndex_0_.equals("silver") ? MapColor.silverColor.colorIndex : (p_getMapColorIndex_0_.equals("cyan") ? MapColor.cyanColor.colorIndex : (p_getMapColorIndex_0_.equals("purple") ? MapColor.purpleColor.colorIndex : (p_getMapColorIndex_0_.equals("blue") ? MapColor.blueColor.colorIndex : (p_getMapColorIndex_0_.equals("brown") ? MapColor.brownColor.colorIndex : (p_getMapColorIndex_0_.equals("green") ? MapColor.greenColor.colorIndex : (p_getMapColorIndex_0_.equals("red") ? MapColor.redColor.colorIndex : (p_getMapColorIndex_0_.equals("black") ? MapColor.blackColor.colorIndex : (p_getMapColorIndex_0_.equals("gold") ? MapColor.goldColor.colorIndex : (p_getMapColorIndex_0_.equals("diamond") ? MapColor.diamondColor.colorIndex : (p_getMapColorIndex_0_.equals("lapis") ? MapColor.lapisColor.colorIndex : (p_getMapColorIndex_0_.equals("emerald") ? MapColor.emeraldColor.colorIndex : (p_getMapColorIndex_0_.equals("obsidian") ? MapColor.obsidianColor.colorIndex : (p_getMapColorIndex_0_.equals("netherrack") ? MapColor.netherrackColor.colorIndex : -1)))))))))))))))))))))))))))))))))))));
    }

    private static int[] getMapColors()
    {
        MapColor[] amapcolor = MapColor.mapColorArray;
        int[] aint = new int[amapcolor.length];
        Arrays.fill((int[])aint, (int) - 1);

        for (int i = 0; i < amapcolor.length && i < aint.length; ++i)
        {
            MapColor mapcolor = amapcolor[i];

            if (mapcolor != null)
            {
                aint[i] = mapcolor.colorValue;
            }
        }

        return aint;
    }

    private static void setMapColors(int[] p_setMapColors_0_)
    {
        if (p_setMapColors_0_ != null)
        {
            MapColor[] amapcolor = MapColor.mapColorArray;

            for (int i = 0; i < amapcolor.length && i < p_setMapColors_0_.length; ++i)
            {
                MapColor mapcolor = amapcolor[i];

                if (mapcolor != null)
                {
                    int j = p_setMapColors_0_[i];

                    if (j >= 0)
                    {
                        mapcolor.colorValue = j;
                    }
                }
            }
        }
    }

    private static int getEntityId(String p_getEntityId_0_)
    {
        if (p_getEntityId_0_ == null)
        {
            return -1;
        }
        else
        {
            int i = EntityList.getIDFromString(p_getEntityId_0_);

            if (i < 0)
            {
                return -1;
            }
            else
            {
                String s = EntityList.getStringFromID(i);
                return !Config.equals(p_getEntityId_0_, s) ? -1 : i;
            }
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

    public static int getExpBarTextColor(int p_getExpBarTextColor_0_)
    {
        return expBarTextColor < 0 ? p_getExpBarTextColor_0_ : expBarTextColor;
    }

    public static int getBossTextColor(int p_getBossTextColor_0_)
    {
        return bossTextColor < 0 ? p_getBossTextColor_0_ : bossTextColor;
    }

    public static int getSignTextColor(int p_getSignTextColor_0_)
    {
        return signTextColor < 0 ? p_getSignTextColor_0_ : signTextColor;
    }
}

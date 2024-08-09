/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Effect;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColorFader;
import net.optifine.CustomColormap;
import net.optifine.LightMap;
import net.optifine.LightMapPack;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.render.RenderEnv;
import net.optifine.util.BiomeUtils;
import net.optifine.util.EntityUtils;
import net.optifine.util.PotionUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.WorldUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomColors {
    private static String paletteFormatDefault = "vanilla";
    private static CustomColormap waterColors = null;
    private static CustomColormap foliagePineColors = null;
    private static CustomColormap foliageBirchColors = null;
    private static CustomColormap swampFoliageColors = null;
    private static CustomColormap swampGrassColors = null;
    private static CustomColormap[] colorsBlockColormaps = null;
    private static CustomColormap[][] blockColormaps = null;
    private static CustomColormap skyColors = null;
    private static CustomColorFader skyColorFader = new CustomColorFader();
    private static CustomColormap fogColors = null;
    private static CustomColorFader fogColorFader = new CustomColorFader();
    private static CustomColormap underwaterColors = null;
    private static CustomColorFader underwaterColorFader = new CustomColorFader();
    private static CustomColormap underlavaColors = null;
    private static CustomColorFader underlavaColorFader = new CustomColorFader();
    private static LightMapPack[] lightMapPacks = null;
    private static int lightmapMinDimensionId = 0;
    private static CustomColormap redstoneColors = null;
    private static CustomColormap xpOrbColors = null;
    private static int xpOrbTime = -1;
    private static CustomColormap durabilityColors = null;
    private static CustomColormap stemColors = null;
    private static CustomColormap stemMelonColors = null;
    private static CustomColormap stemPumpkinColors = null;
    private static CustomColormap myceliumParticleColors = null;
    private static boolean useDefaultGrassFoliageColors = true;
    private static int particleWaterColor = -1;
    private static int particlePortalColor = -1;
    private static int lilyPadColor = -1;
    private static int expBarTextColor = -1;
    private static int bossTextColor = -1;
    private static int signTextColor = -1;
    private static Vector3d fogColorNether = null;
    private static Vector3d fogColorEnd = null;
    private static Vector3d skyColorEnd = null;
    private static int[] spawnEggPrimaryColors = null;
    private static int[] spawnEggSecondaryColors = null;
    private static float[][] wolfCollarColors = null;
    private static float[][] sheepColors = null;
    private static int[] textColors = null;
    private static int[] mapColorsOriginal = null;
    private static float[][] dyeColorsOriginal = null;
    private static int[] potionColors = null;
    private static final BlockState BLOCK_STATE_DIRT = Blocks.DIRT.getDefaultState();
    private static final BlockState BLOCK_STATE_WATER = Blocks.WATER.getDefaultState();
    public static Random random = new Random();
    private static final IColorizer COLORIZER_GRASS = new IColorizer(){

        @Override
        public int getColor(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
            Biome biome = CustomColors.getColorBiome(iBlockDisplayReader, blockPos);
            return swampGrassColors != null && biome == BiomeUtils.SWAMP ? swampGrassColors.getColor(biome, blockPos) : biome.getGrassColor(blockPos.getX(), blockPos.getZ());
        }

        @Override
        public boolean isColorConstant() {
            return true;
        }
    };
    private static final IColorizer COLORIZER_FOLIAGE = new IColorizer(){

        @Override
        public int getColor(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
            Biome biome = CustomColors.getColorBiome(iBlockDisplayReader, blockPos);
            return swampFoliageColors != null && biome == BiomeUtils.SWAMP ? swampFoliageColors.getColor(biome, blockPos) : biome.getFoliageColor();
        }

        @Override
        public boolean isColorConstant() {
            return true;
        }
    };
    private static final IColorizer COLORIZER_FOLIAGE_PINE = new IColorizer(){

        @Override
        public int getColor(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
            return foliagePineColors != null ? foliagePineColors.getColor(iBlockDisplayReader, blockPos) : FoliageColors.getSpruce();
        }

        @Override
        public boolean isColorConstant() {
            return foliagePineColors == null;
        }
    };
    private static final IColorizer COLORIZER_FOLIAGE_BIRCH = new IColorizer(){

        @Override
        public int getColor(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
            return foliageBirchColors != null ? foliageBirchColors.getColor(iBlockDisplayReader, blockPos) : FoliageColors.getBirch();
        }

        @Override
        public boolean isColorConstant() {
            return foliageBirchColors == null;
        }
    };
    private static final IColorizer COLORIZER_WATER = new IColorizer(){

        @Override
        public int getColor(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
            Biome biome = CustomColors.getColorBiome(iBlockDisplayReader, blockPos);
            return waterColors != null ? waterColors.getColor(biome, blockPos) : biome.getWaterColor();
        }

        @Override
        public boolean isColorConstant() {
            return true;
        }
    };

    public static void update() {
        paletteFormatDefault = "vanilla";
        waterColors = null;
        foliageBirchColors = null;
        foliagePineColors = null;
        swampGrassColors = null;
        swampFoliageColors = null;
        skyColors = null;
        fogColors = null;
        underwaterColors = null;
        underlavaColors = null;
        redstoneColors = null;
        xpOrbColors = null;
        xpOrbTime = -1;
        durabilityColors = null;
        stemColors = null;
        myceliumParticleColors = null;
        lightMapPacks = null;
        particleWaterColor = -1;
        particlePortalColor = -1;
        lilyPadColor = -1;
        expBarTextColor = -1;
        bossTextColor = -1;
        signTextColor = -1;
        fogColorNether = null;
        fogColorEnd = null;
        skyColorEnd = null;
        colorsBlockColormaps = null;
        blockColormaps = null;
        useDefaultGrassFoliageColors = true;
        spawnEggPrimaryColors = null;
        spawnEggSecondaryColors = null;
        wolfCollarColors = null;
        sheepColors = null;
        textColors = null;
        CustomColors.setMapColors(mapColorsOriginal);
        CustomColors.setDyeColors(dyeColorsOriginal);
        potionColors = null;
        paletteFormatDefault = CustomColors.getValidProperty("optifine/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
        String string = "optifine/colormap/";
        String[] stringArray = new String[]{"water.png", "watercolorx.png"};
        waterColors = CustomColors.getCustomColors(string, stringArray, 256, 256);
        CustomColors.updateUseDefaultGrassFoliageColors();
        if (Config.isCustomColors()) {
            String[] stringArray2 = new String[]{"pine.png", "pinecolor.png"};
            foliagePineColors = CustomColors.getCustomColors(string, stringArray2, 256, 256);
            String[] stringArray3 = new String[]{"birch.png", "birchcolor.png"};
            foliageBirchColors = CustomColors.getCustomColors(string, stringArray3, 256, 256);
            String[] stringArray4 = new String[]{"swampgrass.png", "swampgrasscolor.png"};
            swampGrassColors = CustomColors.getCustomColors(string, stringArray4, 256, 256);
            String[] stringArray5 = new String[]{"swampfoliage.png", "swampfoliagecolor.png"};
            swampFoliageColors = CustomColors.getCustomColors(string, stringArray5, 256, 256);
            String[] stringArray6 = new String[]{"sky0.png", "skycolor0.png"};
            skyColors = CustomColors.getCustomColors(string, stringArray6, 256, 256);
            String[] stringArray7 = new String[]{"fog0.png", "fogcolor0.png"};
            fogColors = CustomColors.getCustomColors(string, stringArray7, 256, 256);
            String[] stringArray8 = new String[]{"underwater.png", "underwatercolor.png"};
            underwaterColors = CustomColors.getCustomColors(string, stringArray8, 256, 256);
            String[] stringArray9 = new String[]{"underlava.png", "underlavacolor.png"};
            underlavaColors = CustomColors.getCustomColors(string, stringArray9, 256, 256);
            String[] stringArray10 = new String[]{"redstone.png", "redstonecolor.png"};
            redstoneColors = CustomColors.getCustomColors(string, stringArray10, 16, 1);
            xpOrbColors = CustomColors.getCustomColors(string + "xporb.png", -1, -1);
            durabilityColors = CustomColors.getCustomColors(string + "durability.png", -1, -1);
            String[] stringArray11 = new String[]{"stem.png", "stemcolor.png"};
            stemColors = CustomColors.getCustomColors(string, stringArray11, 8, 1);
            stemPumpkinColors = CustomColors.getCustomColors(string + "pumpkinstem.png", 8, 1);
            stemMelonColors = CustomColors.getCustomColors(string + "melonstem.png", 8, 1);
            String[] stringArray12 = new String[]{"myceliumparticle.png", "myceliumparticlecolor.png"};
            myceliumParticleColors = CustomColors.getCustomColors(string, stringArray12, -1, -1);
            Pair<LightMapPack[], Integer> pair = CustomColors.parseLightMapPacks();
            lightMapPacks = pair.getLeft();
            lightmapMinDimensionId = pair.getRight();
            CustomColors.readColorProperties("optifine/color.properties");
            blockColormaps = CustomColors.readBlockColormaps(new String[]{string + "custom/", string + "blocks/"}, colorsBlockColormaps, 256, 256);
            CustomColors.updateUseDefaultGrassFoliageColors();
        }
    }

    private static String getValidProperty(String string, String string2, String[] stringArray, String string3) {
        try {
            ResourceLocation resourceLocation = new ResourceLocation(string);
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return string3;
            }
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            String string4 = propertiesOrdered.getProperty(string2);
            if (string4 == null) {
                return string3;
            }
            List<String> list = Arrays.asList(stringArray);
            if (!list.contains(string4)) {
                CustomColors.warn("Invalid value: " + string2 + "=" + string4);
                CustomColors.warn("Expected values: " + Config.arrayToString(stringArray));
                return string3;
            }
            CustomColors.dbg(string2 + "=" + string4);
            return string4;
        } catch (FileNotFoundException fileNotFoundException) {
            return string3;
        } catch (IOException iOException) {
            iOException.printStackTrace();
            return string3;
        }
    }

    private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
        Object object;
        Object object2;
        int n;
        Integer[] integerArray;
        String string = "optifine/lightmap/world";
        String string2 = ".png";
        String[] stringArray = ResUtils.collectFiles(string, string2);
        HashMap<Integer, Integer[]> hashMap = new HashMap<Integer, Integer[]>();
        for (int i = 0; i < stringArray.length; ++i) {
            integerArray = stringArray[i];
            String string3 = StrUtils.removePrefixSuffix((String)integerArray, string, string2);
            n = Config.parseInt(string3, Integer.MIN_VALUE);
            if (n == Integer.MIN_VALUE) {
                CustomColors.warn("Invalid dimension ID: " + string3 + ", path: " + (String)integerArray);
                continue;
            }
            hashMap.put(n, integerArray);
        }
        Set set = hashMap.keySet();
        integerArray = set.toArray(new Integer[set.size()]);
        Arrays.sort((Object[])integerArray);
        if (integerArray.length <= 0) {
            return new ImmutablePair<LightMapPack[], Integer>(null, 0);
        }
        int n2 = integerArray[0];
        n = integerArray[integerArray.length - 1];
        int n3 = n - n2 + 1;
        CustomColormap[] customColormapArray = new CustomColormap[n3];
        for (int i = 0; i < integerArray.length; ++i) {
            Integer n4 = integerArray[i];
            object2 = (String)hashMap.get(n4);
            object = CustomColors.getCustomColors((String)object2, -1, -1);
            if (object == null) continue;
            if (((CustomColormap)object).getWidth() < 16) {
                CustomColors.warn("Invalid lightmap width: " + ((CustomColormap)object).getWidth() + ", path: " + (String)object2);
                continue;
            }
            int n5 = n4 - n2;
            customColormapArray[n5] = object;
        }
        LightMapPack[] lightMapPackArray = new LightMapPack[customColormapArray.length];
        for (int i = 0; i < customColormapArray.length; ++i) {
            LightMapPack lightMapPack;
            object2 = customColormapArray[i];
            if (object2 == null) continue;
            object = ((CustomColormap)object2).name;
            String string4 = ((CustomColormap)object2).basePath;
            CustomColormap customColormap = CustomColors.getCustomColors(string4 + "/" + (String)object + "_rain.png", -1, -1);
            CustomColormap customColormap2 = CustomColors.getCustomColors(string4 + "/" + (String)object + "_thunder.png", -1, -1);
            LightMap lightMap = new LightMap((CustomColormap)object2);
            LightMap lightMap2 = customColormap != null ? new LightMap(customColormap) : null;
            LightMap lightMap3 = customColormap2 != null ? new LightMap(customColormap2) : null;
            lightMapPackArray[i] = lightMapPack = new LightMapPack(lightMap, lightMap2, lightMap3);
        }
        return new ImmutablePair<LightMapPack[], Integer>(lightMapPackArray, n2);
    }

    private static int getTextureHeight(String string, int n) {
        try {
            InputStream inputStream = Config.getResourceStream(new ResourceLocation(string));
            if (inputStream == null) {
                return n;
            }
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            inputStream.close();
            return bufferedImage == null ? n : bufferedImage.getHeight();
        } catch (IOException iOException) {
            return n;
        }
    }

    private static void readColorProperties(String string) {
        try {
            float[][] fArray;
            ResourceLocation resourceLocation = new ResourceLocation(string);
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return;
            }
            CustomColors.dbg("Loading " + string);
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            particleWaterColor = CustomColors.readColor((Properties)propertiesOrdered, new String[]{"particle.water", "drop.water"});
            particlePortalColor = CustomColors.readColor((Properties)propertiesOrdered, "particle.portal");
            lilyPadColor = CustomColors.readColor((Properties)propertiesOrdered, "lilypad");
            expBarTextColor = CustomColors.readColor((Properties)propertiesOrdered, "text.xpbar");
            bossTextColor = CustomColors.readColor((Properties)propertiesOrdered, "text.boss");
            signTextColor = CustomColors.readColor((Properties)propertiesOrdered, "text.sign");
            fogColorNether = CustomColors.readColorVec3(propertiesOrdered, "fog.nether");
            fogColorEnd = CustomColors.readColorVec3(propertiesOrdered, "fog.end");
            skyColorEnd = CustomColors.readColorVec3(propertiesOrdered, "sky.end");
            colorsBlockColormaps = CustomColors.readCustomColormaps(propertiesOrdered, string);
            spawnEggPrimaryColors = CustomColors.readSpawnEggColors(propertiesOrdered, string, "egg.shell.", "Spawn egg shell");
            spawnEggSecondaryColors = CustomColors.readSpawnEggColors(propertiesOrdered, string, "egg.spots.", "Spawn egg spot");
            wolfCollarColors = CustomColors.readDyeColors(propertiesOrdered, string, "collar.", "Wolf collar");
            sheepColors = CustomColors.readDyeColors(propertiesOrdered, string, "sheep.", "Sheep");
            textColors = CustomColors.readTextColors(propertiesOrdered, string, "text.code.", "Text");
            int[] nArray = CustomColors.readMapColors(propertiesOrdered, string, "map.", "Map");
            if (nArray != null) {
                if (mapColorsOriginal == null) {
                    mapColorsOriginal = CustomColors.getMapColors();
                }
                CustomColors.setMapColors(nArray);
            }
            if ((fArray = CustomColors.readDyeColors(propertiesOrdered, string, "dye.", "Dye")) != null) {
                if (dyeColorsOriginal == null) {
                    dyeColorsOriginal = CustomColors.getDyeColors();
                }
                CustomColors.setDyeColors(fArray);
            }
            potionColors = CustomColors.readPotionColors(propertiesOrdered, string, "potion.", "Potion");
            xpOrbTime = Config.parseInt(propertiesOrdered.getProperty("xporb.time"), -1);
        } catch (FileNotFoundException fileNotFoundException) {
            return;
        } catch (IOException iOException) {
            Config.warn("Error parsing: " + string);
            Config.warn(iOException.getClass().getName() + ": " + iOException.getMessage());
        }
    }

    private static CustomColormap[] readCustomColormaps(Properties properties, String string) {
        String string2;
        ArrayList<CustomColormap> arrayList = new ArrayList<CustomColormap>();
        String string3 = "palette.block.";
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String string4 : properties.keySet()) {
            string2 = properties.getProperty(string4);
            if (!string4.startsWith(string3)) continue;
            hashMap.put(string4, string2);
        }
        String[] stringArray = hashMap.keySet().toArray(new String[hashMap.size()]);
        for (int i = 0; i < stringArray.length; ++i) {
            string2 = stringArray[i];
            String string5 = properties.getProperty(string2);
            CustomColors.dbg("Block palette: " + string2 + " = " + string5);
            String string6 = string2.substring(string3.length());
            String string7 = TextureUtils.getBasePath(string);
            string6 = TextureUtils.fixResourcePath(string6, string7);
            CustomColormap customColormap = CustomColors.getCustomColors(string6, 256, 256);
            if (customColormap == null) {
                CustomColors.warn("Colormap not found: " + string6);
                continue;
            }
            ConnectedParser connectedParser = new ConnectedParser("CustomColors");
            MatchBlock[] matchBlockArray = connectedParser.parseMatchBlocks(string5);
            if (matchBlockArray != null && matchBlockArray.length > 0) {
                for (int j = 0; j < matchBlockArray.length; ++j) {
                    MatchBlock matchBlock = matchBlockArray[j];
                    customColormap.addMatchBlock(matchBlock);
                }
                arrayList.add(customColormap);
                continue;
            }
            CustomColors.warn("Invalid match blocks: " + string5);
        }
        return arrayList.size() <= 0 ? null : arrayList.toArray(new CustomColormap[arrayList.size()]);
    }

    private static CustomColormap[][] readBlockColormaps(String[] stringArray, CustomColormap[] customColormapArray, int n, int n2) {
        Object object;
        int n3;
        String[] stringArray2 = ResUtils.collectFiles(stringArray, new String[]{".properties"});
        Arrays.sort(stringArray2);
        ArrayList arrayList = new ArrayList();
        for (n3 = 0; n3 < stringArray2.length; ++n3) {
            object = stringArray2[n3];
            CustomColors.dbg("Block colormap: " + (String)object);
            try {
                ResourceLocation resourceLocation = new ResourceLocation("minecraft", (String)object);
                InputStream inputStream = Config.getResourceStream(resourceLocation);
                if (inputStream == null) {
                    CustomColors.warn("File not found: " + (String)object);
                    continue;
                }
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                CustomColormap customColormap = new CustomColormap(propertiesOrdered, (String)object, n, n2, paletteFormatDefault);
                if (!customColormap.isValid((String)object) || !customColormap.isValidMatchBlocks((String)object)) continue;
                CustomColors.addToBlockList(customColormap, arrayList);
                continue;
            } catch (FileNotFoundException fileNotFoundException) {
                CustomColors.warn("File not found: " + (String)object);
                continue;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (customColormapArray != null) {
            for (n3 = 0; n3 < customColormapArray.length; ++n3) {
                object = customColormapArray[n3];
                CustomColors.addToBlockList((CustomColormap)object, arrayList);
            }
        }
        return arrayList.size() <= 0 ? (CustomColormap[][])null : CustomColors.blockListToArray(arrayList);
    }

    private static void addToBlockList(CustomColormap customColormap, List list) {
        int[] nArray = customColormap.getMatchBlockIds();
        if (nArray != null && nArray.length > 0) {
            for (int i = 0; i < nArray.length; ++i) {
                int n = nArray[i];
                if (n < 0) {
                    CustomColors.warn("Invalid block ID: " + n);
                    continue;
                }
                CustomColors.addToList(customColormap, list, n);
            }
        } else {
            CustomColors.warn("No match blocks: " + Config.arrayToString(nArray));
        }
    }

    private static void addToList(CustomColormap customColormap, List list, int n) {
        while (n >= list.size()) {
            list.add(null);
        }
        ArrayList<CustomColormap> arrayList = (ArrayList<CustomColormap>)list.get(n);
        if (arrayList == null) {
            arrayList = new ArrayList<CustomColormap>();
            list.set(n, arrayList);
        }
        arrayList.add(customColormap);
    }

    private static CustomColormap[][] blockListToArray(List list) {
        CustomColormap[][] customColormapArray = new CustomColormap[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            List list2 = (List)list.get(i);
            if (list2 == null) continue;
            CustomColormap[] customColormapArray2 = list2.toArray(new CustomColormap[list2.size()]);
            customColormapArray[i] = customColormapArray2;
        }
        return customColormapArray;
    }

    private static int readColor(Properties properties, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            int n = CustomColors.readColor(properties, string);
            if (n < 0) continue;
            return n;
        }
        return 1;
    }

    private static int readColor(Properties properties, String string) {
        String string2 = properties.getProperty(string);
        if (string2 == null) {
            return 1;
        }
        int n = CustomColors.parseColor(string2 = string2.trim());
        if (n < 0) {
            CustomColors.warn("Invalid color: " + string + " = " + string2);
            return n;
        }
        CustomColors.dbg(string + " = " + string2);
        return n;
    }

    private static int parseColor(String string) {
        if (string == null) {
            return 1;
        }
        string = string.trim();
        try {
            return Integer.parseInt(string, 16) & 0xFFFFFF;
        } catch (NumberFormatException numberFormatException) {
            return 1;
        }
    }

    private static Vector3d readColorVec3(Properties properties, String string) {
        int n = CustomColors.readColor(properties, string);
        if (n < 0) {
            return null;
        }
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        float f = (float)n2 / 255.0f;
        float f2 = (float)n3 / 255.0f;
        float f3 = (float)n4 / 255.0f;
        return new Vector3d(f, f2, f3);
    }

    private static CustomColormap getCustomColors(String string, String[] stringArray, int n, int n2) {
        for (int i = 0; i < stringArray.length; ++i) {
            Object object = stringArray[i];
            CustomColormap customColormap = CustomColors.getCustomColors((String)(object = string + (String)object), n, n2);
            if (customColormap == null) continue;
            return customColormap;
        }
        return null;
    }

    public static CustomColormap getCustomColors(String string, int n, int n2) {
        try {
            Object object;
            ResourceLocation resourceLocation = new ResourceLocation(string);
            if (!Config.hasResource(resourceLocation)) {
                return null;
            }
            CustomColors.dbg("Colormap " + string);
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            String string2 = StrUtils.replaceSuffix(string, ".png", ".properties");
            ResourceLocation resourceLocation2 = new ResourceLocation(string2);
            if (Config.hasResource(resourceLocation2)) {
                object = Config.getResourceStream(resourceLocation2);
                propertiesOrdered.load((InputStream)object);
                ((InputStream)object).close();
                CustomColors.dbg("Colormap properties: " + string2);
            } else {
                ((Hashtable)propertiesOrdered).put("format", paletteFormatDefault);
                ((Hashtable)propertiesOrdered).put("source", string);
                string2 = string;
            }
            object = new CustomColormap(propertiesOrdered, string2, n, n2, paletteFormatDefault);
            return !((CustomColormap)object).isValid(string2) ? null : object;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void updateUseDefaultGrassFoliageColors() {
        useDefaultGrassFoliageColors = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && Config.isSwampColors();
    }

    public static int getColorMultiplier(BakedQuad bakedQuad, BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, RenderEnv renderEnv) {
        IColorizer iColorizer;
        Block block = blockState.getBlock();
        BlockState blockState2 = blockState;
        if (blockColormaps != null) {
            if (!bakedQuad.hasTintIndex()) {
                if (block == Blocks.GRASS_BLOCK) {
                    blockState2 = BLOCK_STATE_DIRT;
                }
                if (block == Blocks.REDSTONE_WIRE) {
                    return 1;
                }
            }
            if (block instanceof DoublePlantBlock && blockState.get(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                blockPos = blockPos.down();
                blockState2 = iBlockDisplayReader.getBlockState(blockPos);
            }
            if ((iColorizer = CustomColors.getBlockColormap(blockState2)) != null) {
                if (Config.isSmoothBiomes() && !((CustomColormap)iColorizer).isColorConstant()) {
                    return CustomColors.getSmoothColorMultiplier(blockState, iBlockDisplayReader, blockPos, iColorizer, renderEnv.getColorizerBlockPosM());
                }
                return ((CustomColormap)iColorizer).getColor(iBlockDisplayReader, blockPos);
            }
        }
        if (!bakedQuad.hasTintIndex()) {
            return 1;
        }
        if (block == Blocks.LILY_PAD) {
            return CustomColors.getLilypadColorMultiplier(iBlockDisplayReader, blockPos);
        }
        if (block == Blocks.REDSTONE_WIRE) {
            return CustomColors.getRedstoneColor(renderEnv.getBlockState());
        }
        if (block instanceof StemBlock) {
            return CustomColors.getStemColorMultiplier(blockState, iBlockDisplayReader, blockPos, renderEnv);
        }
        if (useDefaultGrassFoliageColors) {
            return 1;
        }
        if (block != Blocks.GRASS_BLOCK && block != Blocks.TALL_GRASS && !(block instanceof DoublePlantBlock)) {
            if (block instanceof DoublePlantBlock) {
                iColorizer = COLORIZER_GRASS;
                if (blockState.get(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                    blockPos = blockPos.down();
                }
            } else if (block instanceof LeavesBlock) {
                iColorizer = block == Blocks.OAK_LEAVES ? COLORIZER_FOLIAGE : (block == Blocks.SPRUCE_LEAVES ? COLORIZER_FOLIAGE_PINE : (block == Blocks.BIRCH_LEAVES ? COLORIZER_FOLIAGE_BIRCH : COLORIZER_FOLIAGE));
            } else {
                if (block != Blocks.VINE) {
                    return 1;
                }
                iColorizer = COLORIZER_FOLIAGE;
            }
        } else {
            iColorizer = COLORIZER_GRASS;
        }
        return Config.isSmoothBiomes() && !iColorizer.isColorConstant() ? CustomColors.getSmoothColorMultiplier(blockState, iBlockDisplayReader, blockPos, iColorizer, renderEnv.getColorizerBlockPosM()) : iColorizer.getColor(blockState2, iBlockDisplayReader, blockPos);
    }

    protected static Biome getColorBiome(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        Biome biome = BiomeUtils.getBiome(iBlockDisplayReader, blockPos);
        if (!(biome != BiomeUtils.SWAMP && biome != BiomeUtils.SWAMP_HILLS || Config.isSwampColors())) {
            biome = BiomeUtils.PLAINS;
        }
        return biome;
    }

    private static CustomColormap getBlockColormap(BlockState blockState) {
        if (blockColormaps == null) {
            return null;
        }
        if (!(blockState instanceof BlockState)) {
            return null;
        }
        BlockState blockState2 = blockState;
        int n = blockState.getBlockId();
        if (n >= 0 && n < blockColormaps.length) {
            CustomColormap[] customColormapArray = blockColormaps[n];
            if (customColormapArray == null) {
                return null;
            }
            for (int i = 0; i < customColormapArray.length; ++i) {
                CustomColormap customColormap = customColormapArray[i];
                if (!customColormap.matchesBlock(blockState2)) continue;
                return customColormap;
            }
            return null;
        }
        return null;
    }

    private static int getSmoothColorMultiplier(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, IColorizer iColorizer, BlockPosM blockPosM) {
        int n;
        int n2;
        int n3;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = blockPos.getX();
        int n8 = blockPos.getY();
        int n9 = blockPos.getZ();
        BlockPosM blockPosM2 = blockPosM;
        int n10 = Config.getBiomeBlendRadius();
        int n11 = n10 * 2 + 1;
        int n12 = n11 * n11;
        for (n3 = n7 - n10; n3 <= n7 + n10; ++n3) {
            for (n2 = n9 - n10; n2 <= n9 + n10; ++n2) {
                blockPosM2.setXyz(n3, n8, n2);
                n = iColorizer.getColor(blockState, iBlockDisplayReader, blockPosM2);
                n4 += n >> 16 & 0xFF;
                n5 += n >> 8 & 0xFF;
                n6 += n & 0xFF;
            }
        }
        n3 = n4 / n12;
        n2 = n5 / n12;
        n = n6 / n12;
        return n3 << 16 | n2 << 8 | n;
    }

    public static int getFluidColor(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, RenderEnv renderEnv) {
        Block block = blockState.getBlock();
        IColorizer iColorizer = CustomColors.getBlockColormap(blockState);
        if (iColorizer == null && blockState.getMaterial() == Material.WATER) {
            iColorizer = COLORIZER_WATER;
        }
        if (iColorizer == null) {
            return CustomColors.getBlockColors().getColor(blockState, iBlockDisplayReader, blockPos, 0);
        }
        return Config.isSmoothBiomes() && !iColorizer.isColorConstant() ? CustomColors.getSmoothColorMultiplier(blockState, iBlockDisplayReader, blockPos, iColorizer, renderEnv.getColorizerBlockPosM()) : iColorizer.getColor(blockState, iBlockDisplayReader, blockPos);
    }

    public static BlockColors getBlockColors() {
        return Minecraft.getInstance().getBlockColors();
    }

    public static void updatePortalFX(Particle particle) {
        if (particlePortalColor >= 0) {
            int n = particlePortalColor;
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            float f = (float)n2 / 255.0f;
            float f2 = (float)n3 / 255.0f;
            float f3 = (float)n4 / 255.0f;
            particle.setColor(f, f2, f3);
        }
    }

    public static void updateMyceliumFX(Particle particle) {
        if (myceliumParticleColors != null) {
            int n = myceliumParticleColors.getColorRandom();
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            float f = (float)n2 / 255.0f;
            float f2 = (float)n3 / 255.0f;
            float f3 = (float)n4 / 255.0f;
            particle.setColor(f, f2, f3);
        }
    }

    private static int getRedstoneColor(BlockState blockState) {
        if (redstoneColors == null) {
            return 1;
        }
        int n = CustomColors.getRedstoneLevel(blockState, 15);
        return redstoneColors.getColor(n);
    }

    public static void updateReddustFX(Particle particle, IBlockDisplayReader iBlockDisplayReader, double d, double d2, double d3) {
        if (redstoneColors != null) {
            BlockState blockState = iBlockDisplayReader.getBlockState(new BlockPos(d, d2, d3));
            int n = CustomColors.getRedstoneLevel(blockState, 15);
            int n2 = redstoneColors.getColor(n);
            int n3 = n2 >> 16 & 0xFF;
            int n4 = n2 >> 8 & 0xFF;
            int n5 = n2 & 0xFF;
            float f = (float)n3 / 255.0f;
            float f2 = (float)n4 / 255.0f;
            float f3 = (float)n5 / 255.0f;
            particle.setColor(f, f2, f3);
        }
    }

    private static int getRedstoneLevel(BlockState blockState, int n) {
        Block block = blockState.getBlock();
        if (!(block instanceof RedstoneWireBlock)) {
            return n;
        }
        Integer n2 = blockState.get(RedstoneWireBlock.POWER);
        if (!(n2 instanceof Integer)) {
            return n;
        }
        Integer n3 = n2;
        return n3;
    }

    public static float getXpOrbTimer(float f) {
        if (xpOrbTime <= 0) {
            return f;
        }
        float f2 = 628.0f / (float)xpOrbTime;
        return f * f2;
    }

    public static int getXpOrbColor(float f) {
        if (xpOrbColors == null) {
            return 1;
        }
        int n = (int)Math.round((double)((MathHelper.sin(f) + 1.0f) * (float)(xpOrbColors.getLength() - 1)) / 2.0);
        return xpOrbColors.getColor(n);
    }

    public static int getDurabilityColor(float f, int n) {
        if (durabilityColors == null) {
            return n;
        }
        int n2 = (int)(f * (float)durabilityColors.getLength());
        return durabilityColors.getColor(n2);
    }

    public static void updateWaterFX(Particle particle, IBlockDisplayReader iBlockDisplayReader, double d, double d2, double d3, RenderEnv renderEnv) {
        if (waterColors != null || blockColormaps != null || particleWaterColor >= 0) {
            BlockPos blockPos = new BlockPos(d, d2, d3);
            renderEnv.reset(BLOCK_STATE_WATER, blockPos);
            int n = CustomColors.getFluidColor(iBlockDisplayReader, BLOCK_STATE_WATER, blockPos, renderEnv);
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            float f = (float)n2 / 255.0f;
            float f2 = (float)n3 / 255.0f;
            float f3 = (float)n4 / 255.0f;
            if (particleWaterColor >= 0) {
                int n5 = particleWaterColor >> 16 & 0xFF;
                int n6 = particleWaterColor >> 8 & 0xFF;
                int n7 = particleWaterColor & 0xFF;
                f = (float)n5 / 255.0f;
                f2 = (float)n6 / 255.0f;
                f3 = (float)n7 / 255.0f;
                f *= (float)n5 / 255.0f;
                f2 *= (float)n6 / 255.0f;
                f3 *= (float)n7 / 255.0f;
            }
            particle.setColor(f, f2, f3);
        }
    }

    private static int getLilypadColorMultiplier(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        return lilyPadColor < 0 ? CustomColors.getBlockColors().getColor(Blocks.LILY_PAD.getDefaultState(), iBlockDisplayReader, blockPos, 0) : lilyPadColor;
    }

    private static Vector3d getFogColorNether(Vector3d vector3d) {
        return fogColorNether == null ? vector3d : fogColorNether;
    }

    private static Vector3d getFogColorEnd(Vector3d vector3d) {
        return fogColorEnd == null ? vector3d : fogColorEnd;
    }

    private static Vector3d getSkyColorEnd(Vector3d vector3d) {
        return skyColorEnd == null ? vector3d : skyColorEnd;
    }

    public static Vector3d getSkyColor(Vector3d vector3d, IBlockDisplayReader iBlockDisplayReader, double d, double d2, double d3) {
        if (skyColors == null) {
            return vector3d;
        }
        int n = skyColors.getColorSmooth(iBlockDisplayReader, d, d2, d3, 3);
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        float f = (float)n2 / 255.0f;
        float f2 = (float)n3 / 255.0f;
        float f3 = (float)n4 / 255.0f;
        float f4 = (float)vector3d.x / 0.5f;
        float f5 = (float)vector3d.y / 0.66275f;
        float f6 = (float)vector3d.z;
        return skyColorFader.getColor(f *= f4, f2 *= f5, f3 *= f6);
    }

    private static Vector3d getFogColor(Vector3d vector3d, IBlockDisplayReader iBlockDisplayReader, double d, double d2, double d3) {
        if (fogColors == null) {
            return vector3d;
        }
        int n = fogColors.getColorSmooth(iBlockDisplayReader, d, d2, d3, 3);
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        float f = (float)n2 / 255.0f;
        float f2 = (float)n3 / 255.0f;
        float f3 = (float)n4 / 255.0f;
        float f4 = (float)vector3d.x / 0.753f;
        float f5 = (float)vector3d.y / 0.8471f;
        float f6 = (float)vector3d.z;
        return fogColorFader.getColor(f *= f4, f2 *= f5, f3 *= f6);
    }

    public static Vector3d getUnderwaterColor(IBlockDisplayReader iBlockDisplayReader, double d, double d2, double d3) {
        return CustomColors.getUnderFluidColor(iBlockDisplayReader, d, d2, d3, underwaterColors, underwaterColorFader);
    }

    public static Vector3d getUnderlavaColor(IBlockDisplayReader iBlockDisplayReader, double d, double d2, double d3) {
        return CustomColors.getUnderFluidColor(iBlockDisplayReader, d, d2, d3, underlavaColors, underlavaColorFader);
    }

    public static Vector3d getUnderFluidColor(IBlockDisplayReader iBlockDisplayReader, double d, double d2, double d3, CustomColormap customColormap, CustomColorFader customColorFader) {
        if (customColormap == null) {
            return null;
        }
        int n = customColormap.getColorSmooth(iBlockDisplayReader, d, d2, d3, 3);
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        float f = (float)n2 / 255.0f;
        float f2 = (float)n3 / 255.0f;
        float f3 = (float)n4 / 255.0f;
        return customColorFader.getColor(f, f2, f3);
    }

    private static int getStemColorMultiplier(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, RenderEnv renderEnv) {
        CustomColormap customColormap = stemColors;
        Block block = blockState.getBlock();
        if (block == Blocks.PUMPKIN_STEM && stemPumpkinColors != null) {
            customColormap = stemPumpkinColors;
        }
        if (block == Blocks.MELON_STEM && stemMelonColors != null) {
            customColormap = stemMelonColors;
        }
        if (customColormap == null) {
            return 1;
        }
        if (!(block instanceof StemBlock)) {
            return 1;
        }
        int n = blockState.get(StemBlock.AGE);
        return customColormap.getColor(n);
    }

    public static boolean updateLightmap(ClientWorld clientWorld, float f, NativeImage nativeImage, boolean bl, float f2) {
        if (clientWorld == null) {
            return true;
        }
        if (lightMapPacks == null) {
            return true;
        }
        int n = WorldUtils.getDimensionId(clientWorld);
        int n2 = n - lightmapMinDimensionId;
        if (n2 >= 0 && n2 < lightMapPacks.length) {
            LightMapPack lightMapPack = lightMapPacks[n2];
            return lightMapPack == null ? false : lightMapPack.updateLightmap(clientWorld, f, nativeImage, bl, f2);
        }
        return true;
    }

    public static Vector3d getWorldFogColor(Vector3d vector3d, World world, Entity entity2, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        if (WorldUtils.isNether(world)) {
            return CustomColors.getFogColorNether(vector3d);
        }
        if (WorldUtils.isOverworld(world)) {
            return CustomColors.getFogColor(vector3d, minecraft.world, entity2.getPosX(), entity2.getPosY() + 1.0, entity2.getPosZ());
        }
        return WorldUtils.isEnd(world) ? CustomColors.getFogColorEnd(vector3d) : vector3d;
    }

    public static Vector3d getWorldSkyColor(Vector3d vector3d, World world, Entity entity2, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        if (WorldUtils.isOverworld(world)) {
            return CustomColors.getSkyColor(vector3d, minecraft.world, entity2.getPosX(), entity2.getPosY() + 1.0, entity2.getPosZ());
        }
        return WorldUtils.isEnd(world) ? CustomColors.getSkyColorEnd(vector3d) : vector3d;
    }

    private static int[] readSpawnEggColors(Properties properties, String string, String string2, String string3) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Set set = properties.keySet();
        int n = 0;
        for (String string4 : set) {
            String string5 = properties.getProperty(string4);
            if (!string4.startsWith(string2)) continue;
            String string6 = StrUtils.removePrefix(string4, string2);
            int n2 = EntityUtils.getEntityIdByName(string6);
            try {
                if (n2 < 0) {
                    n2 = EntityUtils.getEntityIdByLocation(new ResourceLocation(string6).toString());
                }
            } catch (ResourceLocationException resourceLocationException) {
                Config.warn("ResourceLocationException: " + resourceLocationException.getMessage());
            }
            if (n2 < 0) {
                CustomColors.warn("Invalid spawn egg name: " + string4);
                continue;
            }
            int n3 = CustomColors.parseColor(string5);
            if (n3 < 0) {
                CustomColors.warn("Invalid spawn egg color: " + string4 + " = " + string5);
                continue;
            }
            while (arrayList.size() <= n2) {
                arrayList.add(-1);
            }
            arrayList.set(n2, n3);
            ++n;
        }
        if (n <= 0) {
            return null;
        }
        CustomColors.dbg(string3 + " colors: " + n);
        Object object = new int[arrayList.size()];
        for (int i = 0; i < ((Object)object).length; ++i) {
            object[i] = (Integer)arrayList.get(i);
        }
        return object;
    }

    private static int getSpawnEggColor(SpawnEggItem spawnEggItem, ItemStack itemStack, int n, int n2) {
        int[] nArray;
        if (spawnEggPrimaryColors == null && spawnEggSecondaryColors == null) {
            return n2;
        }
        EntityType<?> entityType = spawnEggItem.getType(itemStack.getTag());
        if (entityType == null) {
            return n2;
        }
        int n3 = Registry.ENTITY_TYPE.getId(entityType);
        if (n3 < 0) {
            return n2;
        }
        int[] nArray2 = nArray = n == 0 ? spawnEggPrimaryColors : spawnEggSecondaryColors;
        if (nArray == null) {
            return n2;
        }
        if (n3 >= 0 && n3 < nArray.length) {
            int n4 = nArray[n3];
            return n4 < 0 ? n2 : n4;
        }
        return n2;
    }

    public static int getColorFromItemStack(ItemStack itemStack, int n, int n2) {
        if (itemStack == null) {
            return n2;
        }
        Item item = itemStack.getItem();
        if (item == null) {
            return n2;
        }
        return item instanceof SpawnEggItem ? CustomColors.getSpawnEggColor((SpawnEggItem)item, itemStack, n, n2) : n2;
    }

    private static float[][] readDyeColors(Properties properties, String string, String string2, String string3) {
        DyeColor[] dyeColorArray = DyeColor.values();
        HashMap<String, DyeColor> hashMap = new HashMap<String, DyeColor>();
        for (int i = 0; i < dyeColorArray.length; ++i) {
            DyeColor dyeColor = dyeColorArray[i];
            hashMap.put(dyeColor.getString(), dyeColor);
        }
        hashMap.put("lightBlue", DyeColor.LIGHT_BLUE);
        hashMap.put("silver", DyeColor.LIGHT_GRAY);
        float[][] fArrayArray = new float[dyeColorArray.length][];
        int n = 0;
        for (String string4 : properties.keySet()) {
            String string5 = properties.getProperty(string4);
            if (!string4.startsWith(string2)) continue;
            String string6 = StrUtils.removePrefix(string4, string2);
            DyeColor dyeColor = (DyeColor)hashMap.get(string6);
            int n2 = CustomColors.parseColor(string5);
            if (dyeColor != null && n2 >= 0) {
                float[] fArray = new float[]{(float)(n2 >> 16 & 0xFF) / 255.0f, (float)(n2 >> 8 & 0xFF) / 255.0f, (float)(n2 & 0xFF) / 255.0f};
                fArrayArray[dyeColor.ordinal()] = fArray;
                ++n;
                continue;
            }
            CustomColors.warn("Invalid color: " + string4 + " = " + string5);
        }
        if (n <= 0) {
            return null;
        }
        CustomColors.dbg(string3 + " colors: " + n);
        return fArrayArray;
    }

    private static float[] getDyeColors(DyeColor dyeColor, float[][] fArray, float[] fArray2) {
        if (fArray == null) {
            return fArray2;
        }
        if (dyeColor == null) {
            return fArray2;
        }
        float[] fArray3 = fArray[dyeColor.ordinal()];
        return fArray3 == null ? fArray2 : fArray3;
    }

    public static float[] getWolfCollarColors(DyeColor dyeColor, float[] fArray) {
        return CustomColors.getDyeColors(dyeColor, wolfCollarColors, fArray);
    }

    public static float[] getSheepColors(DyeColor dyeColor, float[] fArray) {
        return CustomColors.getDyeColors(dyeColor, sheepColors, fArray);
    }

    private static int[] readTextColors(Properties properties, String string, String string2, String string3) {
        int[] nArray = new int[32];
        Arrays.fill(nArray, -1);
        int n = 0;
        for (String string4 : properties.keySet()) {
            String string5 = properties.getProperty(string4);
            if (!string4.startsWith(string2)) continue;
            String string6 = StrUtils.removePrefix(string4, string2);
            int n2 = Config.parseInt(string6, -1);
            int n3 = CustomColors.parseColor(string5);
            if (n2 >= 0 && n2 < nArray.length && n3 >= 0) {
                nArray[n2] = n3;
                ++n;
                continue;
            }
            CustomColors.warn("Invalid color: " + string4 + " = " + string5);
        }
        if (n <= 0) {
            return null;
        }
        CustomColors.dbg(string3 + " colors: " + n);
        return nArray;
    }

    public static int getTextColor(int n, int n2) {
        if (textColors == null) {
            return n2;
        }
        if (n >= 0 && n < textColors.length) {
            int n3 = textColors[n];
            return n3 < 0 ? n2 : n3;
        }
        return n2;
    }

    private static int[] readMapColors(Properties properties, String string, String string2, String string3) {
        int[] nArray = new int[MaterialColor.COLORS.length];
        Arrays.fill(nArray, -1);
        int n = 0;
        for (String string4 : properties.keySet()) {
            String string5 = properties.getProperty(string4);
            if (!string4.startsWith(string2)) continue;
            String string6 = StrUtils.removePrefix(string4, string2);
            int n2 = CustomColors.getMapColorIndex(string6);
            int n3 = CustomColors.parseColor(string5);
            if (n2 >= 0 && n2 < nArray.length && n3 >= 0) {
                nArray[n2] = n3;
                ++n;
                continue;
            }
            CustomColors.warn("Invalid color: " + string4 + " = " + string5);
        }
        if (n <= 0) {
            return null;
        }
        CustomColors.dbg(string3 + " colors: " + n);
        return nArray;
    }

    private static int[] readPotionColors(Properties properties, String string, String string2, String string3) {
        int[] nArray = new int[CustomColors.getMaxPotionId()];
        Arrays.fill(nArray, -1);
        int n = 0;
        for (String string4 : properties.keySet()) {
            String string5 = properties.getProperty(string4);
            if (!string4.startsWith(string2)) continue;
            int n2 = CustomColors.getPotionId(string4);
            int n3 = CustomColors.parseColor(string5);
            if (n2 >= 0 && n2 < nArray.length && n3 >= 0) {
                nArray[n2] = n3;
                ++n;
                continue;
            }
            CustomColors.warn("Invalid color: " + string4 + " = " + string5);
        }
        if (n <= 0) {
            return null;
        }
        CustomColors.dbg(string3 + " colors: " + n);
        return nArray;
    }

    private static int getMaxPotionId() {
        int n = 0;
        for (ResourceLocation resourceLocation : Registry.EFFECTS.keySet()) {
            Effect effect = PotionUtils.getPotion(resourceLocation);
            int n2 = Effect.getId(effect);
            if (n2 <= n) continue;
            n = n2;
        }
        return n;
    }

    private static int getPotionId(String string) {
        if (string.equals("potion.water")) {
            return 1;
        }
        string = StrUtils.replacePrefix(string, "potion.", "effect.");
        String string2 = StrUtils.replacePrefix(string, "effect.", "effect.minecraft.");
        for (ResourceLocation resourceLocation : Registry.EFFECTS.keySet()) {
            Effect effect = PotionUtils.getPotion(resourceLocation);
            if (effect.getName().equals(string)) {
                return Effect.getId(effect);
            }
            if (!effect.getName().equals(string2)) continue;
            return Effect.getId(effect);
        }
        return 1;
    }

    public static int getPotionColor(Effect effect, int n) {
        int n2 = 0;
        if (effect != null) {
            n2 = Effect.getId(effect);
        }
        return CustomColors.getPotionColor(n2, n);
    }

    public static int getPotionColor(int n, int n2) {
        if (potionColors == null) {
            return n2;
        }
        if (n >= 0 && n < potionColors.length) {
            int n3 = potionColors[n];
            return n3 < 0 ? n2 : n3;
        }
        return n2;
    }

    private static int getMapColorIndex(String string) {
        if (string == null) {
            return 1;
        }
        if (string.equals("air")) {
            return MaterialColor.AIR.colorIndex;
        }
        if (string.equals("grass")) {
            return MaterialColor.GRASS.colorIndex;
        }
        if (string.equals("sand")) {
            return MaterialColor.SAND.colorIndex;
        }
        if (string.equals("cloth")) {
            return MaterialColor.WOOL.colorIndex;
        }
        if (string.equals("tnt")) {
            return MaterialColor.TNT.colorIndex;
        }
        if (string.equals("ice")) {
            return MaterialColor.ICE.colorIndex;
        }
        if (string.equals("iron")) {
            return MaterialColor.IRON.colorIndex;
        }
        if (string.equals("foliage")) {
            return MaterialColor.FOLIAGE.colorIndex;
        }
        if (string.equals("clay")) {
            return MaterialColor.CLAY.colorIndex;
        }
        if (string.equals("dirt")) {
            return MaterialColor.DIRT.colorIndex;
        }
        if (string.equals("stone")) {
            return MaterialColor.STONE.colorIndex;
        }
        if (string.equals("water")) {
            return MaterialColor.WATER.colorIndex;
        }
        if (string.equals("wood")) {
            return MaterialColor.WOOD.colorIndex;
        }
        if (string.equals("quartz")) {
            return MaterialColor.QUARTZ.colorIndex;
        }
        if (string.equals("gold")) {
            return MaterialColor.GOLD.colorIndex;
        }
        if (string.equals("diamond")) {
            return MaterialColor.DIAMOND.colorIndex;
        }
        if (string.equals("lapis")) {
            return MaterialColor.LAPIS.colorIndex;
        }
        if (string.equals("emerald")) {
            return MaterialColor.EMERALD.colorIndex;
        }
        if (string.equals("obsidian")) {
            return MaterialColor.OBSIDIAN.colorIndex;
        }
        if (string.equals("netherrack")) {
            return MaterialColor.NETHERRACK.colorIndex;
        }
        if (!string.equals("snow") && !string.equals("white")) {
            if (!string.equals("adobe") && !string.equals("orange")) {
                if (string.equals("magenta")) {
                    return MaterialColor.MAGENTA.colorIndex;
                }
                if (!string.equals("light_blue") && !string.equals("lightBlue")) {
                    if (string.equals("yellow")) {
                        return MaterialColor.YELLOW.colorIndex;
                    }
                    if (string.equals("lime")) {
                        return MaterialColor.LIME.colorIndex;
                    }
                    if (string.equals("pink")) {
                        return MaterialColor.PINK.colorIndex;
                    }
                    if (string.equals("gray")) {
                        return MaterialColor.GRAY.colorIndex;
                    }
                    if (!string.equals("silver") && !string.equals("light_gray")) {
                        if (string.equals("cyan")) {
                            return MaterialColor.CYAN.colorIndex;
                        }
                        if (string.equals("purple")) {
                            return MaterialColor.PURPLE.colorIndex;
                        }
                        if (string.equals("blue")) {
                            return MaterialColor.BLUE.colorIndex;
                        }
                        if (string.equals("brown")) {
                            return MaterialColor.BROWN.colorIndex;
                        }
                        if (string.equals("green")) {
                            return MaterialColor.GREEN.colorIndex;
                        }
                        if (string.equals("red")) {
                            return MaterialColor.RED.colorIndex;
                        }
                        if (string.equals("black")) {
                            return MaterialColor.BLACK.colorIndex;
                        }
                        if (string.equals("white_terracotta")) {
                            return MaterialColor.WHITE_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("orange_terracotta")) {
                            return MaterialColor.ORANGE_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("magenta_terracotta")) {
                            return MaterialColor.MAGENTA_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("light_blue_terracotta")) {
                            return MaterialColor.LIGHT_BLUE_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("yellow_terracotta")) {
                            return MaterialColor.YELLOW_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("lime_terracotta")) {
                            return MaterialColor.LIME_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("pink_terracotta")) {
                            return MaterialColor.PINK_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("gray_terracotta")) {
                            return MaterialColor.GRAY_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("light_gray_terracotta")) {
                            return MaterialColor.LIGHT_GRAY_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("cyan_terracotta")) {
                            return MaterialColor.CYAN_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("purple_terracotta")) {
                            return MaterialColor.PURPLE_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("blue_terracotta")) {
                            return MaterialColor.BLUE_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("brown_terracotta")) {
                            return MaterialColor.BROWN_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("green_terracotta")) {
                            return MaterialColor.GREEN_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("red_terracotta")) {
                            return MaterialColor.RED_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("black_terracotta")) {
                            return MaterialColor.BLACK_TERRACOTTA.colorIndex;
                        }
                        if (string.equals("crimson_nylium")) {
                            return MaterialColor.CRIMSON_NYLIUM.colorIndex;
                        }
                        if (string.equals("crimson_stem")) {
                            return MaterialColor.CRIMSON_STEM.colorIndex;
                        }
                        if (string.equals("crimson_hyphae")) {
                            return MaterialColor.CRIMSON_HYPHAE.colorIndex;
                        }
                        if (string.equals("warped_nylium")) {
                            return MaterialColor.WARPED_NYLIUM.colorIndex;
                        }
                        if (string.equals("warped_stem")) {
                            return MaterialColor.WARPED_STEM.colorIndex;
                        }
                        if (string.equals("warped_hyphae")) {
                            return MaterialColor.WARPED_HYPHAE.colorIndex;
                        }
                        return string.equals("warped_wart_block") ? MaterialColor.WARPED_WART.colorIndex : -1;
                    }
                    return MaterialColor.LIGHT_GRAY.colorIndex;
                }
                return MaterialColor.LIGHT_BLUE.colorIndex;
            }
            return MaterialColor.ADOBE.colorIndex;
        }
        return MaterialColor.SNOW.colorIndex;
    }

    private static int[] getMapColors() {
        MaterialColor[] materialColorArray = MaterialColor.COLORS;
        int[] nArray = new int[materialColorArray.length];
        Arrays.fill(nArray, -1);
        for (int i = 0; i < materialColorArray.length && i < nArray.length; ++i) {
            MaterialColor materialColor = materialColorArray[i];
            if (materialColor == null) continue;
            nArray[i] = materialColor.colorValue;
        }
        return nArray;
    }

    private static void setMapColors(int[] nArray) {
        if (nArray != null) {
            MaterialColor[] materialColorArray = MaterialColor.COLORS;
            for (int i = 0; i < materialColorArray.length && i < nArray.length; ++i) {
                int n;
                MaterialColor materialColor = materialColorArray[i];
                if (materialColor == null || (n = nArray[i]) < 0 || materialColor.colorValue == n) continue;
                materialColor.colorValue = n;
            }
        }
    }

    private static float[][] getDyeColors() {
        DyeColor[] dyeColorArray = DyeColor.values();
        float[][] fArrayArray = new float[dyeColorArray.length][];
        for (int i = 0; i < dyeColorArray.length && i < fArrayArray.length; ++i) {
            DyeColor dyeColor = dyeColorArray[i];
            if (dyeColor == null) continue;
            fArrayArray[i] = dyeColor.getColorComponentValues();
        }
        return fArrayArray;
    }

    private static void setDyeColors(float[][] fArray) {
        if (fArray != null) {
            DyeColor[] dyeColorArray = DyeColor.values();
            for (int i = 0; i < dyeColorArray.length && i < fArray.length; ++i) {
                float[] fArray2;
                DyeColor dyeColor = dyeColorArray[i];
                if (dyeColor == null || (fArray2 = fArray[i]) == null || dyeColor.getColorComponentValues().equals(fArray2)) continue;
                dyeColor.setColorComponentValues(fArray2);
            }
        }
    }

    private static void dbg(String string) {
        Config.dbg("CustomColors: " + string);
    }

    private static void warn(String string) {
        Config.warn("CustomColors: " + string);
    }

    public static int getExpBarTextColor(int n) {
        return expBarTextColor < 0 ? n : expBarTextColor;
    }

    public static int getBossTextColor(int n) {
        return bossTextColor < 0 ? n : bossTextColor;
    }

    public static int getSignTextColor(int n) {
        if (n != 0) {
            return n;
        }
        return signTextColor < 0 ? n : signTextColor;
    }

    public static interface IColorizer {
        public int getColor(BlockState var1, IBlockDisplayReader var2, BlockPos var3);

        public boolean isColorConstant();
    }
}


package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateBase;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.ConnectedParser;
import net.optifine.CustomColorFader;
import net.optifine.CustomColormap;
import net.optifine.CustomColors$1;
import net.optifine.CustomColors$2;
import net.optifine.CustomColors$3;
import net.optifine.CustomColors$4;
import net.optifine.CustomColors$5;
import net.optifine.CustomColors$IColorizer;
import net.optifine.MatchBlock;
import net.optifine.RenderEnv;
import net.optifine.ResUtils;
import net.optifine.StrUtils;
import net.optifine.TextureUtils;
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
   private static CustomColormap[][] blockColormaps = (CustomColormap[][])((CustomColormap[][])null);
   private static CustomColormap skyColors = null;
   private static CustomColorFader skyColorFader = new CustomColorFader();
   private static CustomColormap fogColors = null;
   private static CustomColorFader fogColorFader = new CustomColorFader();
   private static CustomColormap underwaterColors = null;
   private static CustomColorFader underwaterColorFader = new CustomColorFader();
   private static CustomColormap[] x = null;
   private static int l = 0;
   private static float[][] H = new float[16][3];
   private static float[][] j = new float[16][3];
   private static CustomColormap redstoneColors = null;
   private static CustomColormap xpOrbColors = null;
   private static int xpOrbTime = -1;
   private static CustomColormap durabilityColors = null;
   private static CustomColormap stemColors = null;
   private static CustomColormap stemMelonColors = null;
   private static CustomColormap stemPumpkinColors = null;
   private static CustomColormap myceliumParticleColors = null;
   private static boolean useDefaultGrassFoliageColors = true;
   private static int Q = -1;
   private static int q = -1;
   private static int lilyPadColor = -1;
   private static int k = -1;
   private static int F = -1;
   private static int L = -1;
   private static Vec3 M = null;
   private static Vec3 b = null;
   private static Vec3 skyColorEnd = null;
   private static int[] spawnEggPrimaryColors = null;
   private static int[] a = null;
   private static float[][] wolfCollarColors = (float[][])((float[][])null);
   private static float[][] sheepColors = (float[][])((float[][])null);
   private static int[] textColors = null;
   private static int[] h = null;
   private static int[] potionColors = null;
   private static final IBlockState BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
   private static final IBlockState BLOCK_STATE_WATER = Blocks.water.getDefaultState();
   public static Random random = new Random();
   private static final CustomColors$IColorizer COLORIZER_GRASS = new CustomColors$1();
   private static final CustomColors$IColorizer COLORIZER_FOLIAGE = new CustomColors$2();
   private static final CustomColors$IColorizer COLORIZER_FOLIAGE_PINE = new CustomColors$3();
   private static final CustomColors$IColorizer COLORIZER_FOLIAGE_BIRCH = new CustomColors$4();
   private static final CustomColors$IColorizer COLORIZER_WATER = new CustomColors$5();

   public static void update() {
      MatchBlock.b();
      paletteFormatDefault = "vanilla";
      waterColors = null;
      foliageBirchColors = null;
      foliagePineColors = null;
      swampGrassColors = null;
      swampFoliageColors = null;
      skyColors = null;
      fogColors = null;
      underwaterColors = null;
      redstoneColors = null;
      xpOrbColors = null;
      xpOrbTime = -1;
      durabilityColors = null;
      stemColors = null;
      myceliumParticleColors = null;
      x = null;
      Q = -1;
      q = -1;
      lilyPadColor = -1;
      k = -1;
      F = -1;
      L = -1;
      M = null;
      b = null;
      skyColorEnd = null;
      colorsBlockColormaps = null;
      blockColormaps = (CustomColormap[][])((CustomColormap[][])null);
      useDefaultGrassFoliageColors = true;
      spawnEggPrimaryColors = null;
      a = null;
      wolfCollarColors = (float[][])((float[][])null);
      sheepColors = (float[][])((float[][])null);
      textColors = null;
      setMapColors(h);
      potionColors = null;
      PotionHelper.clearPotionColorCache();
      paletteFormatDefault = a("mcpatcher/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
      String var1 = "mcpatcher/colormap/";
      String[] var2 = new String[]{"water.png", "watercolorX.png"};
      waterColors = getCustomColors(var1, var2, 256, 256);
      updateUseDefaultGrassFoliageColors();
      if(Config.isCustomColors()) {
         String[] var3 = new String[]{"pine.png", "pinecolor.png"};
         foliagePineColors = getCustomColors(var1, var3, 256, 256);
         String[] var4 = new String[]{"birch.png", "birchcolor.png"};
         foliageBirchColors = getCustomColors(var1, var4, 256, 256);
         String[] var5 = new String[]{"swampgrass.png", "swampgrasscolor.png"};
         swampGrassColors = getCustomColors(var1, var5, 256, 256);
         String[] var6 = new String[]{"swampfoliage.png", "swampfoliagecolor.png"};
         swampFoliageColors = getCustomColors(var1, var6, 256, 256);
         String[] var7 = new String[]{"sky0.png", "skycolor0.png"};
         skyColors = getCustomColors(var1, var7, 256, 256);
         String[] var8 = new String[]{"fog0.png", "fogcolor0.png"};
         fogColors = getCustomColors(var1, var8, 256, 256);
         String[] var9 = new String[]{"underwater.png", "underwatercolor.png"};
         underwaterColors = getCustomColors(var1, var9, 256, 256);
         String[] var10 = new String[]{"redstone.png", "redstonecolor.png"};
         redstoneColors = getCustomColors(var1, var10, 16, 1);
         xpOrbColors = getCustomColors(var1 + "xporb.png", -1, -1);
         durabilityColors = getCustomColors(var1 + "durability.png", -1, -1);
         String[] var11 = new String[]{"stem.png", "stemcolor.png"};
         stemColors = getCustomColors(var1, var11, 8, 1);
         stemPumpkinColors = getCustomColors(var1 + "pumpkinstem.png", 8, 1);
         stemMelonColors = getCustomColors(var1 + "melonstem.png", 8, 1);
         String[] var12 = new String[]{"myceliumparticle.png", "myceliumparticlecolor.png"};
         myceliumParticleColors = getCustomColors(var1, var12, -1, -1);
         Pair var13 = parseLightmapsRgb();
         x = (CustomColormap[])((CustomColormap[])var13.getLeft());
         l = ((Integer)var13.getRight()).intValue();
         f("mcpatcher/color.properties");
         blockColormaps = readBlockColormaps(new String[]{var1 + "custom/", var1 + "blocks/"}, colorsBlockColormaps, 256, 256);
         updateUseDefaultGrassFoliageColors();
      }

   }

   private static String a(String var0, String var1, String[] var2, String var3) {
      acE[] var4 = MatchBlock.b();

      try {
         ResourceLocation var5 = new ResourceLocation(var0);
         InputStream var6 = Config.getResourceStream(var5);
         return var3;
      } catch (FileNotFoundException var7) {
         return var3;
      } catch (IOException var8) {
         var8.printStackTrace();
         return var3;
      }
   }

   private static Pair parseLightmapsRgb() {
      MatchBlock.b();
      String var1 = "mcpatcher/lightmap/world";
      String var2 = ".png";
      String[] var3 = ResUtils.collectFiles(var1, var2);
      HashMap var4 = new HashMap();
      int var5 = 0;
      if(var5 < var3.length) {
         String var6 = var3[var5];
         String var7 = StrUtils.removePrefixSuffix(var6, var1, var2);
         int var8 = Config.parseInt(var7, Integer.MIN_VALUE);
         if(var8 == Integer.MIN_VALUE) {
            warn("Invalid dimension ID: " + var7 + ", path: " + var6);
         }

         var4.put(Integer.valueOf(var8), var6);
         ++var5;
      }

      Set var17 = var4.keySet();
      Integer[] var18 = (Integer[])((Integer[])var17.toArray(new Integer[var17.size()]));
      Arrays.sort((Object[])var18);
      if(var18.length <= 0) {
         return new ImmutablePair((Object)null, Integer.valueOf(0));
      } else {
         int var19 = var18[0].intValue();
         int var20 = var18[var18.length - 1].intValue();
         int var9 = var20 - var19 + 1;
         CustomColormap[] var10 = new CustomColormap[var9];
         int var11 = 0;
         if(var11 < var18.length) {
            Integer var12 = var18[var11];
            String var13 = (String)var4.get(var12);
            CustomColormap var14 = getCustomColors(var13, -1, -1);
            if(var14.getWidth() < 16) {
               warn("Invalid lightmap width: " + var14.getWidth() + ", path: " + var13);
            }

            int var15 = var12.intValue() - var19;
            var10[var15] = var14;
            ++var11;
         }

         return new ImmutablePair(var10, Integer.valueOf(var19));
      }
   }

   private static int getTextureHeight(String param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static void f(String var0) {
      acE[] var1 = MatchBlock.b();

      try {
         ResourceLocation var2 = new ResourceLocation(var0);
         InputStream var3 = Config.getResourceStream(var2);
      } catch (FileNotFoundException var4) {
         ;
      } catch (IOException var5) {
         var5.printStackTrace();
      }
   }

   private static CustomColormap[] readCustomColormaps(Properties var0, String var1) {
      MatchBlock.b();
      ArrayList var3 = new ArrayList();
      String var4 = "palette.block.";
      HashMap var5 = new HashMap();
      Iterator var6 = var0.keySet().iterator();
      if(var6.hasNext()) {
         Object var7 = var6.next();
         String var8 = var0.getProperty((String)var7);
         if(((String)var7).startsWith(var4)) {
            var5.put(var7, var8);
         }
      }

      String[] var17 = (String[])((String[])((String[])var5.keySet().toArray(new String[var5.size()])));
      int var18 = 0;
      if(var18 < var17.length) {
         String var21 = var17[var18];
         String var9 = var0.getProperty(var21);
         dbg("Block palette: " + var21 + " = " + var9);
         String var10 = var21.substring(var4.length());
         String var11 = TextureUtils.getBasePath(var1);
         var10 = TextureUtils.fixResourcePath(var10, var11);
         CustomColormap var12 = getCustomColors(var10, 256, 256);
         warn("Colormap not found: " + var10);
         ConnectedParser var13 = new ConnectedParser("CustomColors");
         MatchBlock[] var14 = var13.c(var9);
         if(var14 != null && var14.length > 0) {
            int var15 = 0;
            if(var15 < var14.length) {
               MatchBlock var16 = var14[var15];
               var12.addMatchBlock(var16);
               ++var15;
            }

            var3.add(var12);
         }

         warn("Invalid match blocks: " + var9);
         ++var18;
      }

      if(var3.size() <= 0) {
         return null;
      } else {
         CustomColormap[] var20 = (CustomColormap[])((CustomColormap[])((CustomColormap[])var3.toArray(new CustomColormap[var3.size()])));
         return var20;
      }
   }

   private static CustomColormap[][] readBlockColormaps(String[] var0, CustomColormap[] var1, int var2, int var3) {
      String[] var5 = ResUtils.collectFiles(var0, new String[]{".properties"});
      Arrays.sort((Object[])var5);
      MatchBlock.b();
      ArrayList var6 = new ArrayList();
      int var7 = 0;
      if(var7 < var5.length) {
         String var8 = var5[var7];
         dbg("Block colormap: " + var8);

         try {
            ResourceLocation var9 = new ResourceLocation("minecraft", var8);
            InputStream var10 = Config.getResourceStream(var9);
            warn("File not found: " + var8);
            Properties var11 = new Properties();
            var11.load(var10);
            CustomColormap var12 = new CustomColormap(var11, var8, var2, var3, paletteFormatDefault);
            if(var12.isValid(var8) && var12.isValidMatchBlocks(var8)) {
               addToBlockList(var12, var6);
            }
         } catch (FileNotFoundException var13) {
            warn("File not found: " + var8);
         } catch (Exception var14) {
            var14.printStackTrace();
         }

         ++var7;
      }

      var7 = 0;
      if(var7 < var1.length) {
         CustomColormap var19 = var1[var7];
         addToBlockList(var19, var6);
         ++var7;
      }

      if(var6.size() <= 0) {
         return (CustomColormap[][])((CustomColormap[][])null);
      } else {
         CustomColormap[][] var18 = blockListToArray(var6);
         return var18;
      }
   }

   private static void addToBlockList(CustomColormap var0, List var1) {
      MatchBlock.b();
      int[] var3 = var0.getMatchBlockIds();
      if(var3 != null && var3.length > 0) {
         int var4 = 0;
         if(var4 < var3.length) {
            int var5 = var3[var4];
            warn("Invalid block ID: " + var5);
            addToList(var0, var1, var5);
            ++var4;
         }
      }

      warn("No match blocks: " + Config.a(var3));
   }

   private static void addToList(CustomColormap var0, List var1, int var2) {
      acE[] var3 = MatchBlock.b();
      if(var2 >= var1.size()) {
         var1.add((Object)null);
      }

      Object var4 = (List)var1.get(var2);
      if(var4 == null) {
         var4 = new ArrayList();
         var1.set(var2, var4);
      }

      ((List)var4).add(var0);
   }

   private static CustomColormap[][] blockListToArray(List var0) {
      CustomColormap[][] var2 = new CustomColormap[var0.size()][];
      MatchBlock.b();
      int var3 = 0;
      if(var3 < var0.size()) {
         List var4 = (List)var0.get(var3);
         CustomColormap[] var5 = (CustomColormap[])((CustomColormap[])((CustomColormap[])var4.toArray(new CustomColormap[var4.size()])));
         var2[var3] = var5;
         ++var3;
      }

      return var2;
   }

   private static int a(Properties var0, String[] var1) {
      MatchBlock.b();
      int var3 = 0;
      if(var3 < var1.length) {
         String var4 = var1[var3];
         int var5 = readColor(var0, var4);
         if(var5 >= 0) {
            return var5;
         }

         ++var3;
      }

      return -1;
   }

   private static int readColor(Properties var0, String var1) {
      MatchBlock.b();
      String var3 = var0.getProperty(var1);
      if(var3 == null) {
         return -1;
      } else {
         var3 = var3.trim();
         int var4 = parseColor(var3);
         if(var4 < 0) {
            warn("Invalid color: " + var1 + " = " + var3);
            return var4;
         } else {
            dbg(var1 + " = " + var3);
            return var4;
         }
      }
   }

   private static int parseColor(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return -1;
      } else {
         var0 = var0.trim();
         String var10000 = var0;
         byte var10001 = 16;

         try {
            int var2 = Integer.parseInt(var10000, var10001) & 16777215;
            return var2;
         } catch (NumberFormatException var3) {
            return -1;
         }
      }
   }

   private static Vec3 a(Properties var0, String var1) {
      MatchBlock.b();
      int var3 = readColor(var0, var1);
      if(var3 < 0) {
         return null;
      } else {
         int var4 = var3 >> 16 & 255;
         int var5 = var3 >> 8 & 255;
         int var6 = var3 & 255;
         float var7 = (float)var4 / 255.0F;
         float var8 = (float)var5 / 255.0F;
         float var9 = (float)var6 / 255.0F;
         return new Vec3((double)var7, (double)var8, (double)var9);
      }
   }

   private static CustomColormap getCustomColors(String var0, String[] var1, int var2, int var3) {
      MatchBlock.b();
      byte var5 = 0;
      if(var5 < var1.length) {
         String var6 = var1[var5];
         var6 = var0 + var6;
         CustomColormap var7 = getCustomColors(var6, var2, var3);
         return var7;
      } else {
         return null;
      }
   }

   public static CustomColormap getCustomColors(String var0, int var1, int var2) {
      acE[] var3 = MatchBlock.b();

      try {
         ResourceLocation var4 = new ResourceLocation(var0);
         if(!Config.hasResource(var4)) {
            return null;
         } else {
            dbg("Colormap " + var0);
            Properties var5 = new Properties();
            String var6 = StrUtils.replaceSuffix(var0, ".png", ".properties");
            ResourceLocation var7 = new ResourceLocation(var6);
            if(Config.hasResource(var7)) {
               InputStream var8 = Config.getResourceStream(var7);
               var5.load(var8);
               var8.close();
               dbg("Colormap properties: " + var6);
            }

            var5.put("format", paletteFormatDefault);
            var5.put("source", var0);
            CustomColormap var10 = new CustomColormap(var5, var0, var1, var2, paletteFormatDefault);
            return !var10.isValid(var0)?null:var10;
         }
      } catch (Exception var9) {
         var9.printStackTrace();
         return null;
      }
   }

   public static void updateUseDefaultGrassFoliageColors() {
      acE[] var0 = MatchBlock.b();
      useDefaultGrassFoliageColors = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && Config.isSwampColors() && Config.isSmoothBiomes();
   }

   public static int getColorMultiplier(BakedQuad var0, Block var1, IBlockAccess var2, BlockPos var3, RenderEnv var4) {
      acE[] var5 = MatchBlock.b();
      if(blockColormaps != null) {
         IBlockState var8 = var4.getBlockState();
         if(!var0.hasTintIndex()) {
            if(var1 == Blocks.grass) {
               var8 = BLOCK_STATE_DIRT;
            }

            if(var1 == Blocks.redstone_wire) {
               return -1;
            }
         }

         if(var1 == Blocks.double_plant && var4.getMetadata() >= 8) {
            var3 = var3.down();
            var8 = var2.getBlockState(var3);
         }

         CustomColormap var15 = getBlockColormap(var8);
         return Config.isSmoothBiomes() && !var15.isColorConstant()?getSmoothColorMultiplier(var2, var3, var15, var4.getColorizerBlockPosM()):var15.getColor(var2, var3);
      } else if(!var0.hasTintIndex()) {
         return -1;
      } else if(var1 == Blocks.waterlily) {
         return getLilypadColorMultiplier(var2, var3);
      } else if(var1 == Blocks.redstone_wire) {
         return getRedstoneColor(var4.getBlockState());
      } else if(var1 instanceof BlockStem) {
         return getStemColorMultiplier(var1, var2, var3, var4);
      } else if(useDefaultGrassFoliageColors) {
         return -1;
      } else {
         int var6 = var4.getMetadata();
         CustomColors$IColorizer var7;
         if(var1 != Blocks.grass && var1 != Blocks.tallgrass && var1 != Blocks.double_plant) {
            if(var1 == Blocks.double_plant) {
               var7 = COLORIZER_GRASS;
               if(var6 < 8) {
                  return Config.isSmoothBiomes() && !var7.isColorConstant()?getSmoothColorMultiplier(var2, var3, var7, var4.getColorizerBlockPosM()):var7.getColor(var2, var3);
               }

               var3 = var3.down();
            }

            if(var1 == Blocks.leaves) {
               switch(var6 & 3) {
               case 0:
                  var7 = COLORIZER_FOLIAGE;
               case 1:
                  var7 = COLORIZER_FOLIAGE_PINE;
               case 2:
                  var7 = COLORIZER_FOLIAGE_BIRCH;
               default:
                  var7 = COLORIZER_FOLIAGE;
               }
            }

            if(var1 == Blocks.leaves2) {
               var7 = COLORIZER_FOLIAGE;
            }

            if(var1 != Blocks.vine) {
               return -1;
            }

            var7 = COLORIZER_FOLIAGE;
         }

         var7 = COLORIZER_GRASS;
         return Config.isSmoothBiomes() && !var7.isColorConstant()?getSmoothColorMultiplier(var2, var3, var7, var4.getColorizerBlockPosM()):var7.getColor(var2, var3);
      }
   }

   protected static BiomeGenBase getColorBiome(IBlockAccess var0, BlockPos var1) {
      MatchBlock.b();
      BiomeGenBase var3 = var0.getBiomeGenForCoords(var1);
      if(var3 == BiomeGenBase.swampland && !Config.isSwampColors()) {
         var3 = BiomeGenBase.plains;
      }

      return var3;
   }

   private static CustomColormap getBlockColormap(IBlockState var0) {
      acE[] var1 = MatchBlock.b();
      if(blockColormaps == null) {
         return null;
      } else if(!(var0 instanceof BlockStateBase)) {
         return null;
      } else {
         BlockStateBase var2 = (BlockStateBase)var0;
         int var3 = var2.getBlockId();
         if(var3 >= 0 && var3 < blockColormaps.length) {
            CustomColormap[] var4 = blockColormaps[var3];
            return null;
         } else {
            return null;
         }
      }
   }

   private static int getSmoothColorMultiplier(IBlockAccess var0, BlockPos var1, CustomColors$IColorizer var2, BlockPosM var3) {
      int var5 = 0;
      int var6 = 0;
      MatchBlock.b();
      int var7 = 0;
      int var8 = var1.getX();
      int var9 = var1.getY();
      int var10 = var1.getZ();
      int var12 = var8 - 1;
      if(var12 <= var8 + 1) {
         int var13 = var10 - 1;
         if(var13 <= var10 + 1) {
            var3.setXyz(var12, var9, var13);
            int var14 = var2.getColor(var0, var3);
            var5 += var14 >> 16 & 255;
            var6 += var14 >> 8 & 255;
            var7 += var14 & 255;
            ++var13;
         }

         ++var12;
      }

      var12 = var5 / 9;
      int var18 = var6 / 9;
      int var19 = var7 / 9;
      return var12 << 16 | var18 << 8 | var19;
   }

   public static int getFluidColor(IBlockAccess var0, IBlockState var1, BlockPos var2, RenderEnv var3) {
      MatchBlock.b();
      Block var5 = var1.getBlock();
      Object var6 = getBlockColormap(var1);
      if(var6 == null && var5.getMaterial() == Material.water) {
         var6 = COLORIZER_WATER;
      }

      return var6 == null?var5.colorMultiplier(var0, var2):(Config.isSmoothBiomes() && !((CustomColors$IColorizer)var6).isColorConstant()?getSmoothColorMultiplier(var0, var2, (CustomColors$IColorizer)var6, var3.getColorizerBlockPosM()):((CustomColors$IColorizer)var6).getColor(var0, var2));
   }

   public static void b(EntityFX var0) {
      acE[] var1 = MatchBlock.b();
      if(q >= 0) {
         int var2 = q;
         int var3 = var2 >> 16 & 255;
         int var4 = var2 >> 8 & 255;
         int var5 = var2 & 255;
         float var6 = (float)var3 / 255.0F;
         float var7 = (float)var4 / 255.0F;
         float var8 = (float)var5 / 255.0F;
         var0.setRBGColorF(var6, var7, var8);
      }

   }

   public static void a(EntityFX var0) {
      acE[] var1 = MatchBlock.b();
      if(myceliumParticleColors != null) {
         int var2 = myceliumParticleColors.getColorRandom();
         int var3 = var2 >> 16 & 255;
         int var4 = var2 >> 8 & 255;
         int var5 = var2 & 255;
         float var6 = (float)var3 / 255.0F;
         float var7 = (float)var4 / 255.0F;
         float var8 = (float)var5 / 255.0F;
         var0.setRBGColorF(var6, var7, var8);
      }

   }

   private static int getRedstoneColor(IBlockState var0) {
      if(redstoneColors == null) {
         return -1;
      } else {
         int var1 = getRedstoneLevel(var0, 15);
         int var2 = redstoneColors.getColor(var1);
         return var2;
      }
   }

   public static void updateReddustFX(EntityFX var0, IBlockAccess var1, double var2, double var4, double var6) {
      if(redstoneColors != null) {
         IBlockState var8 = var1.getBlockState(new BlockPos(var2, var4, var6));
         int var9 = getRedstoneLevel(var8, 15);
         int var10 = redstoneColors.getColor(var9);
         int var11 = var10 >> 16 & 255;
         int var12 = var10 >> 8 & 255;
         int var13 = var10 & 255;
         float var14 = (float)var11 / 255.0F;
         float var15 = (float)var12 / 255.0F;
         float var16 = (float)var13 / 255.0F;
         var0.setRBGColorF(var14, var15, var16);
      }

   }

   private static int getRedstoneLevel(IBlockState var0, int var1) {
      MatchBlock.b();
      Block var3 = var0.getBlock();
      if(!(var3 instanceof BlockRedstoneWire)) {
         return var1;
      } else {
         Comparable var4 = var0.getValue(BlockRedstoneWire.P);
         if(!(var4 instanceof Integer)) {
            return var1;
         } else {
            Integer var5 = (Integer)var4;
            return var5.intValue();
         }
      }
   }

   public static float getXpOrbTimer(float var0) {
      if(xpOrbTime <= 0) {
         return var0;
      } else {
         float var1 = 628.0F / (float)xpOrbTime;
         return var0 * var1;
      }
   }

   public static int getXpOrbColor(float var0) {
      if(xpOrbColors == null) {
         return -1;
      } else {
         int var1 = (int)Math.round((double)((MathHelper.sin(var0) + 1.0F) * (float)(xpOrbColors.e() - 1)) / 2.0D);
         int var2 = xpOrbColors.getColor(var1);
         return var2;
      }
   }

   public static int getDurabilityColor(int var0) {
      if(durabilityColors == null) {
         return -1;
      } else {
         int var1 = var0 * durabilityColors.e() / 255;
         int var2 = durabilityColors.getColor(var1);
         return var2;
      }
   }

   public static void b(EntityFX var0, IBlockAccess var1, double var2, double var4, double var6) {
      acE[] var8 = MatchBlock.b();
      if(waterColors != null || blockColormaps != null) {
         BlockPos var9 = new BlockPos(var2, var4, var6);
         RenderEnv var10 = RenderEnv.getInstance(var1, BLOCK_STATE_WATER, var9);
         int var11 = getFluidColor(var1, BLOCK_STATE_WATER, var9, var10);
         int var12 = var11 >> 16 & 255;
         int var13 = var11 >> 8 & 255;
         int var14 = var11 & 255;
         float var15 = (float)var12 / 255.0F;
         float var16 = (float)var13 / 255.0F;
         float var17 = (float)var14 / 255.0F;
         if(Q >= 0) {
            int var18 = Q >> 16 & 255;
            int var19 = Q >> 8 & 255;
            int var20 = Q & 255;
            var15 *= (float)var18 / 255.0F;
            var16 *= (float)var19 / 255.0F;
            var17 *= (float)var20 / 255.0F;
         }

         var0.setRBGColorF(var15, var16, var17);
      }

   }

   private static int getLilypadColorMultiplier(IBlockAccess var0, BlockPos var1) {
      acE[] var2 = MatchBlock.b();
      return lilyPadColor < 0?Blocks.waterlily.colorMultiplier(var0, var1):lilyPadColor;
   }

   private static Vec3 b(Vec3 var0) {
      acE[] var1 = MatchBlock.b();
      return M == null?var0:M;
   }

   private static Vec3 a(Vec3 var0) {
      acE[] var1 = MatchBlock.b();
      return b == null?var0:b;
   }

   private static Vec3 getSkyColorEnd(Vec3 var0) {
      acE[] var1 = MatchBlock.b();
      return skyColorEnd == null?var0:skyColorEnd;
   }

   public static Vec3 getSkyColor(Vec3 var0, IBlockAccess var1, double var2, double var4, double var6) {
      acE[] var8 = MatchBlock.b();
      if(skyColors == null) {
         return var0;
      } else {
         int var9 = skyColors.getColorSmooth(var1, var2, var4, var6, 3);
         int var10 = var9 >> 16 & 255;
         int var11 = var9 >> 8 & 255;
         int var12 = var9 & 255;
         float var13 = (float)var10 / 255.0F;
         float var14 = (float)var11 / 255.0F;
         float var15 = (float)var12 / 255.0F;
         float var16 = (float)var0.xCoord / 0.5F;
         float var17 = (float)var0.yCoord / 0.66275F;
         float var18 = (float)var0.zCoord;
         var13 = var13 * var16;
         var14 = var14 * var17;
         var15 = var15 * var18;
         Vec3 var19 = skyColorFader.getColor((double)var13, (double)var14, (double)var15);
         return var19;
      }
   }

   private static Vec3 getFogColor(Vec3 var0, IBlockAccess var1, double var2, double var4, double var6) {
      acE[] var8 = MatchBlock.b();
      if(fogColors == null) {
         return var0;
      } else {
         int var9 = fogColors.getColorSmooth(var1, var2, var4, var6, 3);
         int var10 = var9 >> 16 & 255;
         int var11 = var9 >> 8 & 255;
         int var12 = var9 & 255;
         float var13 = (float)var10 / 255.0F;
         float var14 = (float)var11 / 255.0F;
         float var15 = (float)var12 / 255.0F;
         float var16 = (float)var0.xCoord / 0.753F;
         float var17 = (float)var0.yCoord / 0.8471F;
         float var18 = (float)var0.zCoord;
         var13 = var13 * var16;
         var14 = var14 * var17;
         var15 = var15 * var18;
         Vec3 var19 = fogColorFader.getColor((double)var13, (double)var14, (double)var15);
         return var19;
      }
   }

   public static Vec3 getUnderwaterColor(IBlockAccess var0, double var1, double var3, double var5) {
      acE[] var7 = MatchBlock.b();
      if(underwaterColors == null) {
         return null;
      } else {
         int var8 = underwaterColors.getColorSmooth(var0, var1, var3, var5, 3);
         int var9 = var8 >> 16 & 255;
         int var10 = var8 >> 8 & 255;
         int var11 = var8 & 255;
         float var12 = (float)var9 / 255.0F;
         float var13 = (float)var10 / 255.0F;
         float var14 = (float)var11 / 255.0F;
         Vec3 var15 = underwaterColorFader.getColor((double)var12, (double)var13, (double)var14);
         return var15;
      }
   }

   private static int getStemColorMultiplier(Block var0, IBlockAccess var1, BlockPos var2, RenderEnv var3) {
      MatchBlock.b();
      CustomColormap var5 = stemColors;
      if(var0 == Blocks.pumpkin_stem && stemPumpkinColors != null) {
         var5 = stemPumpkinColors;
      }

      if(var0 == Blocks.melon_stem && stemMelonColors != null) {
         var5 = stemMelonColors;
      }

      if(var5 == null) {
         return -1;
      } else {
         int var6 = var3.getMetadata();
         return var5.getColor(var6);
      }
   }

   public static boolean a(World var0, float var1, int[] var2, boolean var3) {
      acE[] var4 = MatchBlock.b();
      return false;
   }

   private static void a(float[][] var0, float var1, int var2, int var3, float[][] var4) {
      int var6 = (int)Math.floor((double)var1);
      MatchBlock.b();
      int var7 = (int)Math.ceil((double)var1);
      if(var6 == var7) {
         int var8 = 0;
         if(var8 < 16) {
            float[] var9 = var0[var2 + var8 * var3 + var6];
            float[] var10 = var4[var8];
            int var11 = 0;
            if(var11 < 3) {
               var10[var11] = var9[var11];
               ++var11;
            }

            ++var8;
         }
      }

      float var16 = 1.0F - (var1 - (float)var6);
      float var17 = 1.0F - ((float)var7 - var1);
      int var18 = 0;
      if(var18 < 16) {
         float[] var21 = var0[var2 + var18 * var3 + var6];
         float[] var12 = var0[var2 + var18 * var3 + var7];
         float[] var13 = var4[var18];
         int var14 = 0;
         if(var14 < 3) {
            var13[var14] = var21[var14] * var16 + var12[var14] * var17;
            ++var14;
         }

         ++var18;
      }

   }

   public static Vec3 getWorldFogColor(Vec3 var0, WorldClient var1, Entity var2, float var3) {
      MatchBlock.b();
      int var5 = var1.provider.getDimensionId();
      switch(var5) {
      case -1:
         var0 = b(var0);
      case 0:
         Minecraft var6 = Minecraft.getInstance();
         var0 = getFogColor(var0, var6.world, var2.posX, var2.posY + 1.0D, var2.posZ);
      case 1:
         var0 = a(var0);
      default:
         return var0;
      }
   }

   public static Vec3 getWorldSkyColor(Vec3 var0, World var1, Entity var2, float var3) {
      MatchBlock.b();
      int var5 = var1.provider.getDimensionId();
      switch(var5) {
      case 0:
         Minecraft var6 = Minecraft.getInstance();
         var0 = getSkyColor(var0, var6.world, var2.posX, var2.posY + 1.0D, var2.posZ);
      case 1:
         var0 = getSkyColorEnd(var0);
      default:
         return var0;
      }
   }

   private static int[] readSpawnEggColors(Properties var0, String var1, String var2, String var3) {
      ArrayList var5 = new ArrayList();
      MatchBlock.b();
      Set var6 = var0.keySet();
      int var7 = 0;
      Iterator var8 = var6.iterator();
      if(var8.hasNext()) {
         Object var9 = var8.next();
         String var10 = var0.getProperty((String)var9);
         if(((String)var9).startsWith(var2)) {
            String var11 = StrUtils.removePrefix((String)var9, var2);
            int var12 = getEntityId(var11);
            int var13 = parseColor(var10);
            if(var12 >= 0 && var13 >= 0) {
               if(var5.size() <= var12) {
                  var5.add(Integer.valueOf(-1));
               }

               var5.set(var12, Integer.valueOf(var13));
               ++var7;
            }

            warn("Invalid spawn egg color: " + var9 + " = " + var10);
         }
      }

      if(var7 <= 0) {
         return null;
      } else {
         dbg(var3 + " colors: " + var7);
         int[] var14 = new int[var5.size()];
         int var15 = 0;
         if(var15 < var14.length) {
            var14[var15] = ((Integer)var5.get(var15)).intValue();
            ++var15;
         }

         return var14;
      }
   }

   private static int getSpawnEggColor(ItemMonsterPlacer var0, ItemStack var1, int var2, int var3) {
      MatchBlock.b();
      int var5 = var1.getMetadata();
      int[] var6 = spawnEggPrimaryColors;
      return var3;
   }

   public static int getColorFromItemStack(ItemStack var0, int var1, int var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 == null) {
         return var2;
      } else {
         Item var4 = var0.getItem();
         return var4 == null?var2:(var4 instanceof ItemMonsterPlacer?getSpawnEggColor((ItemMonsterPlacer)var4, var0, var1, var2):var2);
      }
   }

   private static float[][] readDyeColors(Properties var0, String var1, String var2, String var3) {
      EnumDyeColor[] var5 = EnumDyeColor.values();
      MatchBlock.b();
      HashMap var6 = new HashMap();
      int var7 = 0;
      if(var7 < var5.length) {
         EnumDyeColor var8 = var5[var7];
         var6.put(var8.getName(), var8);
         ++var7;
      }

      float[][] var17 = new float[var5.length][];
      int var18 = 0;
      Iterator var9 = var0.keySet().iterator();
      if(var9.hasNext()) {
         Object var10 = var9.next();
         String var11 = var0.getProperty((String)var10);
         if(((String)var10).startsWith(var2)) {
            String var12 = StrUtils.removePrefix((String)var10, var2);
            if(var12.equals("lightBlue")) {
               var12 = "light_blue";
            }

            EnumDyeColor var13 = (EnumDyeColor)var6.get(var12);
            int var14 = parseColor(var11);
            if(var14 >= 0) {
               float[] var15 = new float[]{(float)(var14 >> 16 & 255) / 255.0F, (float)(var14 >> 8 & 255) / 255.0F, (float)(var14 & 255) / 255.0F};
               var17[var13.ordinal()] = var15;
               ++var18;
            }

            warn("Invalid color: " + var10 + " = " + var11);
         }
      }

      if(var18 <= 0) {
         return (float[][])((float[][])null);
      } else {
         dbg(var3 + " colors: " + var18);
         return var17;
      }
   }

   private static float[] a(EnumDyeColor var0, float[][] var1, float[] var2) {
      acE[] var3 = MatchBlock.b();
      return var2;
   }

   public static float[] getWolfCollarColors(EnumDyeColor var0, float[] var1) {
      return a(var0, wolfCollarColors, var1);
   }

   public static float[] getSheepColors(EnumDyeColor var0, float[] var1) {
      return a(var0, sheepColors, var1);
   }

   private static int[] readTextColors(Properties var0, String var1, String var2, String var3) {
      MatchBlock.b();
      int[] var5 = new int[32];
      Arrays.fill((int[])var5, -1);
      int var6 = 0;
      Iterator var7 = var0.keySet().iterator();
      if(var7.hasNext()) {
         Object var8 = var7.next();
         String var9 = var0.getProperty((String)var8);
         if(((String)var8).startsWith(var2)) {
            String var10 = StrUtils.removePrefix((String)var8, var2);
            int var11 = Config.parseInt(var10, -1);
            int var12 = parseColor(var9);
            if(var11 >= 0 && var11 < var5.length && var12 >= 0) {
               var5[var11] = var12;
               ++var6;
            }

            warn("Invalid color: " + var8 + " = " + var9);
         }
      }

      if(var6 <= 0) {
         return null;
      } else {
         dbg(var3 + " colors: " + var6);
         return var5;
      }
   }

   public static int a(int var0, int var1) {
      acE[] var2 = MatchBlock.b();
      if(textColors == null) {
         return var1;
      } else if(var0 >= 0 && var0 < textColors.length) {
         int var3 = textColors[var0];
         return var3 < 0?var1:var3;
      } else {
         return var1;
      }
   }

   private static int[] readMapColors(Properties var0, String var1, String var2, String var3) {
      MatchBlock.b();
      int[] var5 = new int[MapColor.mapColorArray.length];
      Arrays.fill((int[])var5, -1);
      int var6 = 0;
      Iterator var7 = var0.keySet().iterator();
      if(var7.hasNext()) {
         Object var8 = var7.next();
         String var9 = var0.getProperty((String)var8);
         if(((String)var8).startsWith(var2)) {
            String var10 = StrUtils.removePrefix((String)var8, var2);
            int var11 = getMapColorIndex(var10);
            int var12 = parseColor(var9);
            if(var11 >= 0 && var11 < var5.length && var12 >= 0) {
               var5[var11] = var12;
               ++var6;
            }

            warn("Invalid color: " + var8 + " = " + var9);
         }
      }

      if(var6 <= 0) {
         return null;
      } else {
         dbg(var3 + " colors: " + var6);
         return var5;
      }
   }

   private static int[] readPotionColors(Properties var0, String var1, String var2, String var3) {
      MatchBlock.b();
      int[] var5 = new int[Potion.potionTypes.length];
      Arrays.fill((int[])var5, -1);
      int var6 = 0;
      Iterator var7 = var0.keySet().iterator();
      if(var7.hasNext()) {
         Object var8 = var7.next();
         String var9 = var0.getProperty((String)var8);
         if(((String)var8).startsWith(var2)) {
            int var10 = getPotionId((String)var8);
            int var11 = parseColor(var9);
            if(var10 >= 0 && var10 < var5.length && var11 >= 0) {
               var5[var10] = var11;
               ++var6;
            }

            warn("Invalid color: " + var8 + " = " + var9);
         }
      }

      if(var6 <= 0) {
         return null;
      } else {
         dbg(var3 + " colors: " + var6);
         return var5;
      }
   }

   private static int getPotionId(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0.equals("potion.water")) {
         return 0;
      } else {
         Potion[] var2 = Potion.potionTypes;
         int var3 = 0;
         if(var3 < var2.length) {
            Potion var4 = var2[var3];
            if(var4.getName().equals(var0)) {
               return var4.getId();
            }

            ++var3;
         }

         return -1;
      }
   }

   public static int b(int var0, int var1) {
      acE[] var2 = MatchBlock.b();
      if(potionColors == null) {
         return var1;
      } else if(var0 >= 0 && var0 < potionColors.length) {
         int var3 = potionColors[var0];
         return var3 < 0?var1:var3;
      } else {
         return var1;
      }
   }

   private static int getMapColorIndex(String var0) {
      acE[] var1 = MatchBlock.b();
      return var0 == null?-1:(var0.equals("air")?MapColor.airColor.colorIndex:(var0.equals("grass")?MapColor.grassColor.colorIndex:(var0.equals("sand")?MapColor.sandColor.colorIndex:(var0.equals("cloth")?MapColor.clothColor.colorIndex:(var0.equals("tnt")?MapColor.tntColor.colorIndex:(var0.equals("ice")?MapColor.iceColor.colorIndex:(var0.equals("iron")?MapColor.ironColor.colorIndex:(var0.equals("foliage")?MapColor.foliageColor.colorIndex:(var0.equals("clay")?MapColor.clayColor.colorIndex:(var0.equals("dirt")?MapColor.dirtColor.colorIndex:(var0.equals("stone")?MapColor.stoneColor.colorIndex:(var0.equals("water")?MapColor.waterColor.colorIndex:(var0.equals("wood")?MapColor.woodColor.colorIndex:(var0.equals("quartz")?MapColor.quartzColor.colorIndex:(var0.equals("gold")?MapColor.goldColor.colorIndex:(var0.equals("diamond")?MapColor.diamondColor.colorIndex:(var0.equals("lapis")?MapColor.lapisColor.colorIndex:(var0.equals("emerald")?MapColor.emeraldColor.colorIndex:(var0.equals("podzol")?MapColor.obsidianColor.colorIndex:(var0.equals("netherrack")?MapColor.netherrackColor.colorIndex:(!var0.equals("snow") && !var0.equals("white")?(!var0.equals("adobe") && !var0.equals("orange")?(var0.equals("magenta")?MapColor.magentaColor.colorIndex:(!var0.equals("light_blue") && !var0.equals("lightBlue")?(var0.equals("yellow")?MapColor.yellowColor.colorIndex:(var0.equals("lime")?MapColor.limeColor.colorIndex:(var0.equals("pink")?MapColor.pinkColor.colorIndex:(var0.equals("gray")?MapColor.grayColor.colorIndex:(var0.equals("silver")?MapColor.silverColor.colorIndex:(var0.equals("cyan")?MapColor.cyanColor.colorIndex:(var0.equals("purple")?MapColor.purpleColor.colorIndex:(var0.equals("blue")?MapColor.blueColor.colorIndex:(var0.equals("brown")?MapColor.brownColor.colorIndex:(var0.equals("green")?MapColor.greenColor.colorIndex:(var0.equals("red")?MapColor.redColor.colorIndex:(var0.equals("black")?MapColor.blackColor.colorIndex:-1)))))))))))):MapColor.lightBlueColor.colorIndex)):MapColor.adobeColor.colorIndex):MapColor.snowColor.colorIndex)))))))))))))))))))));
   }

   private static int[] h() {
      MapColor[] var1 = MapColor.mapColorArray;
      int[] var2 = new int[var1.length];
      MatchBlock.b();
      Arrays.fill((int[])var2, -1);
      int var3 = 0;
      if(var3 < var1.length && var3 < var2.length) {
         MapColor var4 = var1[var3];
         var2[var3] = var4.colorValue;
         ++var3;
      }

      return var2;
   }

   private static void setMapColors(int[] var0) {
      acE[] var1 = MatchBlock.b();
      MapColor[] var2 = MapColor.mapColorArray;
      boolean var3 = false;
      int var4 = 0;
      if(var4 < var2.length && var4 < var0.length) {
         MapColor var5 = var2[var4];
         int var6 = var0[var4];
         if(var5.colorValue != var6) {
            var5.colorValue = var6;
            var3 = true;
         }

         ++var4;
      }

      if(var3) {
         Minecraft.getInstance().getTextureManager().reloadBannerTextures();
      }

   }

   private static int getEntityId(String var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return -1;
      } else {
         int var2 = EntityList.getIDFromString(var0);
         if(var2 < 0) {
            return -1;
         } else {
            String var3 = EntityList.getStringFromID(var2);
            return !Config.equals(var0, var3)?-1:var2;
         }
      }
   }

   private static void dbg(String var0) {
      Config.dbg("CustomColors: " + var0);
   }

   private static void warn(String var0) {
      Config.warn("CustomColors: " + var0);
   }

   public static int a(int var0) {
      acE[] var1 = MatchBlock.b();
      return k < 0?var0:k;
   }

   public static int c(int var0) {
      acE[] var1 = MatchBlock.b();
      return F < 0?var0:F;
   }

   public static int d(int var0) {
      acE[] var1 = MatchBlock.b();
      return L < 0?var0:L;
   }

   static CustomColormap access$000() {
      return swampGrassColors;
   }

   static CustomColormap access$100() {
      return swampFoliageColors;
   }

   static CustomColormap access$200() {
      return foliagePineColors;
   }

   static CustomColormap access$300() {
      return foliageBirchColors;
   }

   static CustomColormap access$400() {
      return waterColors;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}

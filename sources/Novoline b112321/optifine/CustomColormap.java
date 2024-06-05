package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.BlockPosM;
import optifine.Config;
import optifine.ConnectedParser;
import optifine.CustomColors;
import optifine.CustomColors$IColorizer;
import optifine.MatchBlock;
import optifine.Matches;
import optifine.TextureUtils;

public class CustomColormap implements CustomColors$IColorizer {
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
   private float[][] colorsRgb = (float[][])((float[][])null);
   private static final int FORMAT_UNKNOWN = -1;
   private static final int FORMAT_VANILLA = 0;
   private static final int FORMAT_GRID = 1;
   private static final int FORMAT_FIXED = 2;
   public static final String e = "vanilla";
   public static final String h = "grid";
   public static final String y = "fixed";
   public static final String[] FORMAT_STRINGS = new String[]{"vanilla", "grid", "fixed"};
   public static final String g = "format";
   public static final String z = "blocks";
   public static final String v = "source";
   public static final String k = "color";
   public static final String u = "yVariance";
   public static final String i = "yOffset";

   public CustomColormap(Properties var1, String var2, int var3, int var4, String var5) {
      ConnectedParser var6 = new ConnectedParser("Colormap");
      this.name = var6.parseName(var2);
      this.basePath = var6.parseBasePath(var2);
      this.format = this.parseFormat(var1.getProperty("format", var5));
      this.matchBlocks = var6.c(var1.getProperty("blocks"));
      this.source = parseTexture(var1.getProperty("source"), var2, this.basePath);
      this.color = ConnectedParser.parseColor(var1.getProperty("color"), -1);
      this.yVariance = var6.parseInt(var1.getProperty("yVariance"), 0);
      this.yOffset = var6.parseInt(var1.getProperty("yOffset"), 0);
      this.width = var3;
      this.height = var4;
   }

   private int parseFormat(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return 0;
      } else if(var1.equals("vanilla")) {
         return 0;
      } else if(var1.equals("grid")) {
         return 1;
      } else if(var1.equals("fixed")) {
         return 2;
      } else {
         warn("Unknown format: " + var1);
         return -1;
      }
   }

   public boolean isValid(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.format != 0 && this.format != 1) {
         if(this.format != 2) {
            return false;
         }

         if(this.color >= 0) {
            return true;
         }

         this.color = 16777215;
      }

      if(this.source == null) {
         warn("Source not defined: " + var1);
         return false;
      } else {
         this.readColors();
         if(this.colors == null) {
            return false;
         } else {
            if(this.color < 0) {
               if(this.format == 0) {
                  this.color = this.getColor(127, 127);
               }

               if(this.format == 1) {
                  this.color = this.getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
               }
            }

            return true;
         }
      }
   }

   public boolean isValidMatchBlocks(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.matchBlocks == null) {
         this.matchBlocks = this.d();
         if(this.matchBlocks == null) {
            warn("Match blocks not defined: " + var1);
            return false;
         }
      }

      return true;
   }

   private MatchBlock[] d() {
      MatchBlock.b();
      Block var2 = Block.getBlockFromName(this.name);
      return new MatchBlock[]{new MatchBlock(Block.getIdFromBlock(var2))};
   }

   private void readColors() {
      // $FF: Couldn't be decompiled
   }

   private static void dbg(String var0) {
      Config.dbg("CustomColors: " + var0);
   }

   private static void warn(String var0) {
      Config.warn("CustomColors: " + var0);
   }

   private static String parseTexture(String var0, String var1, String var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var0 != null) {
         String var10 = ".png";
         if(var0.endsWith(var10)) {
            var0 = var0.substring(0, var0.length() - var10.length());
         }

         var0 = fixTextureName(var0, var2);
         return var0;
      } else {
         String var4 = var1;
         int var5 = var1.lastIndexOf(47);
         if(var5 >= 0) {
            var4 = var1.substring(var5 + 1);
         }

         int var6 = var4.lastIndexOf(46);
         var4 = var4.substring(0, var6);
         var4 = fixTextureName(var4, var2);
         return var4;
      }
   }

   private static String fixTextureName(String var0, String var1) {
      MatchBlock.b();
      var0 = TextureUtils.fixResourcePath(var0, var1);
      if(!var0.startsWith(var1) && !var0.startsWith("textures/") && !var0.startsWith("mcpatcher/")) {
         var0 = var1 + "/" + var0;
      }

      if(var0.endsWith(".png")) {
         var0 = var0.substring(0, var0.length() - 4);
      }

      String var3 = "textures/blocks/";
      if(var0.startsWith(var3)) {
         var0 = var0.substring(var3.length());
      }

      if(var0.startsWith("/")) {
         var0 = var0.substring(1);
      }

      return var0;
   }

   public boolean matchesBlock(BlockStateBase var1) {
      return Matches.block(var1, this.matchBlocks);
   }

   public int getColorRandom() {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.format == 2) {
         return this.color;
      } else {
         int var2 = CustomColors.random.nextInt(this.colors.length);
         return this.colors[var2];
      }
   }

   public int getColor(int var1) {
      var1 = Config.b(var1, 0, this.colors.length - 1);
      return this.colors[var1] & 16777215;
   }

   public int getColor(int var1, int var2) {
      var1 = Config.b(var1, 0, this.width - 1);
      var2 = Config.b(var2, 0, this.height - 1);
      return this.colors[var2 * this.width + var1] & 16777215;
   }

   public float[][] getColorsRgb() {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.colorsRgb == null) {
         this.colorsRgb = toRgb(this.colors);
      }

      return this.colorsRgb;
   }

   public int getColor(IBlockAccess var1, BlockPos var2) {
      BiomeGenBase var3 = CustomColors.getColorBiome(var1, var2);
      return this.getColor(var3, var2);
   }

   public boolean isColorConstant() {
      PacketRemapper[] var1 = MatchBlock.b();
      return this.format == 2;
   }

   public int getColor(BiomeGenBase var1, BlockPos var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      return this.format == 0?this.getColorVanilla(var1, var2):(this.format == 1?this.getColorGrid(var1, var2):this.color);
   }

   public int getColorSmooth(IBlockAccess var1, double var2, double var4, double var6, int var8) {
      PacketRemapper[] var9 = MatchBlock.b();
      if(this.format == 2) {
         return this.color;
      } else {
         int var10 = MathHelper.floor_double(var2);
         int var11 = MathHelper.floor_double(var4);
         int var12 = MathHelper.floor_double(var6);
         int var13 = 0;
         int var14 = 0;
         int var15 = 0;
         int var16 = 0;
         BlockPosM var17 = new BlockPosM(0, 0, 0);
         int var18 = var10 - var8;
         if(var18 <= var10 + var8) {
            int var19 = var12 - var8;
            if(var19 <= var12 + var8) {
               var17.setXyz(var18, var11, var19);
               int var20 = this.getColor((IBlockAccess)var1, var17);
               var13 += var20 >> 16 & 255;
               var14 += var20 >> 8 & 255;
               var15 += var20 & 255;
               ++var16;
               ++var19;
            }

            ++var18;
         }

         var18 = var13 / var16;
         int var24 = var14 / var16;
         int var25 = var15 / var16;
         return var18 << 16 | var24 << 8 | var25;
      }
   }

   private int getColorVanilla(BiomeGenBase var1, BlockPos var2) {
      double var3 = (double)MathHelper.a(var1.getFloatTemperature(var2), 0.0F, 1.0F);
      double var5 = (double)MathHelper.a(var1.getFloatRainfall(), 0.0F, 1.0F);
      var5 = var5 * var3;
      int var7 = (int)((1.0D - var3) * (double)(this.width - 1));
      int var8 = (int)((1.0D - var5) * (double)(this.height - 1));
      return this.getColor(var7, var8);
   }

   private int getColorGrid(BiomeGenBase var1, BlockPos var2) {
      int var4 = var1.biomeID;
      MatchBlock.b();
      int var5 = var2.getY() - this.yOffset;
      if(this.yVariance > 0) {
         int var6 = var2.getX() << 16 + var2.getZ();
         int var7 = Config.intHash(var6);
         int var8 = this.yVariance * 2 + 1;
         int var9 = (var7 & 255) % var8 - this.yVariance;
         var5 += var9;
      }

      return this.getColor(var4, var5);
   }

   public int e() {
      PacketRemapper[] var1 = MatchBlock.b();
      return this.format == 2?1:this.colors.length;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   private static float[][] toRgb(int[] var0) {
      MatchBlock.b();
      float[][] var2 = new float[var0.length][3];
      int var3 = 0;
      if(var3 < var0.length) {
         int var4 = var0[var3];
         float var5 = (float)(var4 >> 16 & 255) / 255.0F;
         float var6 = (float)(var4 >> 8 & 255) / 255.0F;
         float var7 = (float)(var4 & 255) / 255.0F;
         float[] var8 = var2[var3];
         var8[0] = var5;
         var8[1] = var6;
         var8[2] = var7;
         ++var3;
      }

      return var2;
   }

   public void addMatchBlock(MatchBlock var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.matchBlocks == null) {
         this.matchBlocks = new MatchBlock[0];
      }

      this.matchBlocks = (MatchBlock[])((MatchBlock[])((MatchBlock[])Config.addObjectToArray(this.matchBlocks, var1)));
   }

   public void addMatchBlock(int var1, int var2) {
      MatchBlock.b();
      MatchBlock var4 = this.getMatchBlock(var1);
      var4.addMetadata(var2);
      this.addMatchBlock(new MatchBlock(var1, var2));
   }

   private MatchBlock getMatchBlock(int var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.matchBlocks == null) {
         return null;
      } else {
         int var3 = 0;
         if(var3 < this.matchBlocks.length) {
            MatchBlock var4 = this.matchBlocks[var3];
            if(var4.getBlockId() == var1) {
               return var4;
            }

            ++var3;
         }

         return null;
      }
   }

   public int[] getMatchBlockIds() {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.matchBlocks == null) {
         return null;
      } else {
         HashSet var2 = new HashSet();
         int var3 = 0;
         if(var3 < this.matchBlocks.length) {
            MatchBlock var4 = this.matchBlocks[var3];
            if(var4.getBlockId() >= 0) {
               var2.add(Integer.valueOf(var4.getBlockId()));
            }

            ++var3;
         }

         Integer[] var7 = (Integer[])((Integer[])((Integer[])var2.toArray(new Integer[var2.size()])));
         int[] var8 = new int[var7.length];
         int var5 = 0;
         if(var5 < var7.length) {
            var8[var5] = var7[var5].intValue();
            ++var5;
         }

         return var8;
      }
   }

   public String toString() {
      return "" + this.basePath + "/" + this.name + ", blocks: " + Config.a((Object[])this.matchBlocks) + ", source: " + this.source;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

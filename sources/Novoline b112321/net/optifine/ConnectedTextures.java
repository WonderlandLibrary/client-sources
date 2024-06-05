package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.MatchBlock;
import net.optifine.RenderEnv;
import net.optifine.ResUtils;

public class ConnectedTextures {
   private static Map[] spriteQuadMaps = null;
   private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])((ConnectedProperties[][])null);
   private static ConnectedProperties[][] tileProperties = (ConnectedProperties[][])((ConnectedProperties[][])null);
   private static boolean multipass = false;
   private static final int g = 0;
   private static final int o = 1;
   private static final int q = 2;
   private static final int Z_POS_SOUTH = 3;
   private static final int X_NEG_WEST = 4;
   private static final int X_POS_EAST = 5;
   private static final int h = 0;
   private static final int d = 1;
   private static final int l = 2;
   private static final String[] propSuffixes = new String[]{"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
   private static final int[] ctmIndexes = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0};
   public static final IBlockState AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
   private static TextureAtlasSprite a = null;

   public static synchronized BakedQuad a(IBlockAccess var0, IBlockState var1, BlockPos var2, BakedQuad var3, RenderEnv var4) {
      MatchBlock.b();
      TextureAtlasSprite var6 = var3.getSprite();
      return var3;
   }

   private static BakedQuad a(TextureAtlasSprite var0, Block var1, IBlockState var2, BakedQuad var3) {
      acE[] var4 = MatchBlock.b();
      if(spriteQuadMaps == null) {
         return var3;
      } else {
         int var5 = var0.getIndexInMap();
         if(var5 >= 0 && var5 < spriteQuadMaps.length) {
            Object var6 = spriteQuadMaps[var5];
            if(var6 == null) {
               var6 = new IdentityHashMap(1);
               spriteQuadMaps[var5] = (Map)var6;
            }

            BakedQuad var7 = (BakedQuad)((Map)var6).get(var3);
            if(var7 == null) {
               var7 = makeSpriteQuad(var3, var0);
               ((Map)var6).put(var3, var7);
            }

            return var7;
         } else {
            return var3;
         }
      }
   }

   private static BakedQuad makeSpriteQuad(BakedQuad var0, TextureAtlasSprite var1) {
      MatchBlock.b();
      int[] var3 = (int[])((int[])var0.getVertexData().clone());
      TextureAtlasSprite var4 = var0.getSprite();
      int var5 = 0;
      if(var5 < 4) {
         fixVertex(var3, var5, var4, var1);
         ++var5;
      }

      BakedQuad var7 = new BakedQuad(var3, var0.getTintIndex(), var0.getFace(), var1);
      return var7;
   }

   private static void fixVertex(int[] var0, int var1, TextureAtlasSprite var2, TextureAtlasSprite var3) {
      int var4 = var0.length / 4;
      int var5 = var4 * var1;
      float var6 = Float.intBitsToFloat(var0[var5 + 4]);
      float var7 = Float.intBitsToFloat(var0[var5 + 4 + 1]);
      double var8 = var2.getSpriteU16(var6);
      double var10 = var2.getSpriteV16(var7);
      var0[var5 + 4] = Float.floatToRawIntBits(var3.getInterpolatedU(var8));
      var0[var5 + 4 + 1] = Float.floatToRawIntBits(var3.getInterpolatedV(var10));
   }

   private static TextureAtlasSprite a(IBlockAccess var0, IBlockState var1, BlockPos var2, EnumFacing var3, TextureAtlasSprite var4, RenderEnv var5) {
      MatchBlock.b();
      TextureAtlasSprite var7 = getConnectedTextureSingle(var0, var1, var2, var3, var4, true, var5);
      if(!multipass) {
         return var7;
      } else if(var7 == var4) {
         return var7;
      } else {
         TextureAtlasSprite var8 = var7;
         int var9 = 0;
         if(var9 < 3) {
            TextureAtlasSprite var10 = getConnectedTextureSingle(var0, var1, var2, var3, var7, false, var5);
            if(var10 == var7) {
               ;
            }

            var8 = var10;
            ++var9;
         }

         return var8;
      }
   }

   public static TextureAtlasSprite getConnectedTextureSingle(IBlockAccess var0, IBlockState var1, BlockPos var2, EnumFacing var3, TextureAtlasSprite var4, boolean var5, RenderEnv var6) {
      MatchBlock.b();
      Block var8 = var1.getBlock();
      if(!(var1 instanceof BlockStateBase)) {
         return var4;
      } else {
         BlockStateBase var9 = (BlockStateBase)var1;
         if(tileProperties != null) {
            int var10 = var4.getIndexInMap();
            if(var10 >= 0 && var10 < tileProperties.length) {
               ConnectedProperties[] var11 = tileProperties[var10];
               int var12 = a(var3);
               int var13 = 0;
               if(var13 < var11.length) {
                  ConnectedProperties var14 = var11[var13];
                  if(var14 != null && var14.matchesBlockId(var9.getBlockId())) {
                     TextureAtlasSprite var23 = getConnectedTexture(var14, var0, var9, var2, var12, var4, var6);
                     return var23;
                  }

                  ++var13;
               }
            }
         }

         if(blockProperties != null && var5) {
            int var16 = var6.getBlockId();
            if(var16 >= 0 && var16 < blockProperties.length) {
               ConnectedProperties[] var17 = blockProperties[var16];
               int var18 = a(var3);
               int var20 = 0;
               if(var20 < var17.length) {
                  ConnectedProperties var22 = var17[var20];
                  if(var22 != null && var22.matchesIcon(var4)) {
                     TextureAtlasSprite var15 = getConnectedTexture(var22, var0, var9, var2, var18, var4, var6);
                     return var15;
                  }

                  ++var20;
               }
            }
         }

         return var4;
      }
   }

   public static int a(EnumFacing var0) {
      acE[] var1 = MatchBlock.b();
      return -1;
   }

   private static EnumFacing getFacing(int var0) {
      switch(var0) {
      case 0:
         return EnumFacing.DOWN;
      case 1:
         return EnumFacing.UP;
      case 2:
         return EnumFacing.NORTH;
      case 3:
         return EnumFacing.SOUTH;
      case 4:
         return EnumFacing.WEST;
      case 5:
         return EnumFacing.EAST;
      default:
         return EnumFacing.UP;
      }
   }

   private static TextureAtlasSprite getConnectedTexture(ConnectedProperties var0, IBlockAccess var1, BlockStateBase var2, BlockPos var3, int var4, TextureAtlasSprite var5, RenderEnv var6) {
      int var8 = 0;
      MatchBlock.b();
      int var9 = var2.getMetadata();
      int var10 = var9;
      Block var11 = var2.getBlock();
      if(var11 instanceof BlockRotatedPillar) {
         var8 = b(var4, var9);
         if(var0.getMetadataMax() <= 3) {
            var10 = var9 & 3;
         }
      }

      if(var11 instanceof BlockQuartz) {
         var8 = a(var4, var9);
         if(var0.getMetadataMax() <= 2 && var10 > 2) {
            var10 = 2;
         }
      }

      if(!var0.matchesBlock(var2.getBlockId(), var10)) {
         return null;
      } else {
         if(var4 >= 0 && var0.faces != 63) {
            int var12 = var4;
            if(var8 != 0) {
               var12 = c(var4, var8);
            }

            if((1 << var12 & var0.faces) == 0) {
               return null;
            }
         }

         int var14 = var3.getY();
         if(var14 >= var0.minHeight && var14 <= var0.maxHeight) {
            if(var0.biomes != null) {
               BiomeGenBase var13 = var1.getBiomeGenForCoords(var3);
               if(!var0.matchesBiome(var13)) {
                  return null;
               }
            }

            switch(var0.method) {
            case 1:
               return getConnectedTextureCtm(var0, var1, var2, var3, var8, var4, var5, var9, var6);
            case 2:
               return getConnectedTextureHorizontal(var0, var1, var2, var3, var8, var4, var5, var9);
            case 3:
               return getConnectedTextureTop(var0, var1, var2, var3, var8, var4, var5, var9);
            case 4:
               return getConnectedTextureRandom(var0, var3, var4);
            case 5:
               return getConnectedTextureRepeat(var0, var3, var4);
            case 6:
               return getConnectedTextureVertical(var0, var1, var2, var3, var8, var4, var5, var9);
            case 7:
               return getConnectedTextureFixed(var0);
            case 8:
               return getConnectedTextureHorizontalVertical(var0, var1, var2, var3, var8, var4, var5, var9);
            case 9:
               return getConnectedTextureVerticalHorizontal(var0, var1, var2, var3, var8, var4, var5, var9);
            default:
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private static int c(int var0, int var1) {
      acE[] var2 = MatchBlock.b();
      switch(var1) {
      case 0:
         return var0;
      case 1:
         switch(var0) {
         case 0:
            return 2;
         case 1:
            return 3;
         case 2:
            return 1;
         case 3:
            return 0;
         default:
            return var0;
         }
      case 2:
         switch(var0) {
         case 0:
            return 4;
         case 1:
            return 5;
         case 2:
         case 3:
         default:
            return var0;
         case 4:
            return 1;
         case 5:
            return 0;
         }
      default:
         return var0;
      }
   }

   private static int b(int var0, int var1) {
      MatchBlock.b();
      int var3 = (var1 & 12) >> 2;
      switch(var3) {
      case 1:
         return 2;
      case 2:
         return 1;
      default:
         return 0;
      }
   }

   private static int a(int var0, int var1) {
      acE[] var2 = MatchBlock.b();
      switch(var1) {
      case 3:
         return 2;
      case 4:
         return 1;
      default:
         return 0;
      }
   }

   private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties var0, BlockPos var1, int var2) {
      acE[] var3 = MatchBlock.b();
      if(var0.tileIcons.length == 1) {
         return var0.tileIcons[0];
      } else {
         int var4 = var2 / var0.symmetry * var0.symmetry;
         int var5 = Config.getRandom(var1, var4) & Integer.MAX_VALUE;
         int var6 = 0;
         if(var0.weights == null) {
            var6 = var5 % var0.tileIcons.length;
         }

         int var7 = var5 % var0.sumAllWeights;
         int[] var8 = var0.sumWeights;
         int var9 = 0;
         if(var9 < var8.length) {
            if(var7 < var8[var9]) {
               var6 = var9;
            }

            ++var9;
         }

         return var0.tileIcons[var6];
      }
   }

   private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties var0) {
      return var0.tileIcons[0];
   }

   private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties var0, BlockPos var1, int var2) {
      acE[] var3 = MatchBlock.b();
      if(var0.tileIcons.length == 1) {
         return var0.tileIcons[0];
      } else {
         int var4 = var1.getX();
         int var5 = var1.getY();
         int var6 = var1.getZ();
         int var7 = 0;
         int var8 = 0;
         switch(var2) {
         case 0:
         case 1:
         case 2:
            var7 = -var4 - 1;
            var8 = -var5;
         case 3:
            var8 = -var5;
         case 4:
            var8 = -var5;
         case 5:
            var7 = -var6 - 1;
            var8 = -var5;
         default:
            var7 = var7 % var0.width;
            var8 = var8 % var0.height;
            if(var7 < 0) {
               var7 += var0.width;
            }

            if(var8 < 0) {
               var8 += var0.height;
            }

            int var9 = var8 * var0.width + var7;
            return var0.tileIcons[var9];
         }
      }
   }

   private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties var0, IBlockAccess var1, IBlockState var2, BlockPos var3, int var4, int var5, TextureAtlasSprite var6, int var7, RenderEnv var8) {
      MatchBlock.b();
      boolean[] var10 = var8.h();
      switch(var5) {
      case 0:
         var10[0] = isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
         var10[1] = isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         var10[2] = isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
         var10[3] = isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
      case 1:
         var10[0] = isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
         var10[1] = isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         var10[2] = isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
         var10[3] = isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
      case 2:
         var10[0] = isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         var10[1] = isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
         var10[2] = isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
         var10[3] = isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
      case 3:
         var10[0] = isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
         var10[1] = isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         var10[2] = isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
         var10[3] = isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
      case 4:
         var10[0] = isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
         var10[1] = isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
         var10[2] = isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
         var10[3] = isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
      case 5:
         var10[0] = isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
         var10[1] = isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
         var10[2] = isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
         var10[3] = isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
      default:
         byte var11 = 0;
         if(var10[0] & !var10[1] & !var10[2] & !var10[3]) {
            var11 = 3;
         }

         if(!var10[0] & var10[1] & !var10[2] & !var10[3]) {
            var11 = 1;
         }

         if(!var10[0]) {
            boolean var10000 = true;
         } else {
            boolean var12 = false;
         }

         if(!var10[1]) {
            boolean var10001 = true;
         } else {
            boolean var13 = false;
         }

         if(false & var10[2] & !var10[3]) {
            var11 = 12;
         }

         if(!var10[0]) {
            boolean var14 = true;
         } else {
            boolean var15 = false;
         }

         if(!var10[1]) {
            boolean var10002 = true;
         } else {
            boolean var16 = false;
         }

         boolean var17 = false;
         if(!var10[2]) {
            boolean var10003 = true;
         } else {
            boolean var18 = false;
         }

         if(false & var10[3]) {
            var11 = 36;
         }

         if(var10[0] & var10[1] & !var10[2] & !var10[3]) {
            var11 = 2;
         }

         if(!var10[0]) {
            boolean var19 = true;
         } else {
            boolean var20 = false;
         }

         if(!var10[1]) {
            boolean var10004 = true;
         } else {
            boolean var21 = false;
         }

         if(false & var10[2] & var10[3]) {
            var11 = 24;
         }

         if(var10[0] & !var10[1] & var10[2] & !var10[3]) {
            var11 = 15;
         }

         if(var10[0] & !var10[1] & !var10[2] & var10[3]) {
            var11 = 39;
         }

         if(!var10[0] & var10[1] & var10[2] & !var10[3]) {
            var11 = 13;
         }

         if(!var10[0] & var10[1] & !var10[2] & var10[3]) {
            var11 = 37;
         }

         if(!var10[0] & var10[1] & var10[2] & var10[3]) {
            var11 = 25;
         }

         if(var10[0] & !var10[1] & var10[2] & var10[3]) {
            var11 = 27;
         }

         if(var10[0] & var10[1] & !var10[2] & var10[3]) {
            var11 = 38;
         }

         if(var10[0] & var10[1] & var10[2] & !var10[3]) {
            var11 = 14;
         }

         if(var10[0] & var10[1] & var10[2] & var10[3]) {
            var11 = 26;
         }

         if(var11 == 0) {
            return var0.tileIcons[var11];
         } else if(!Config.B()) {
            return var0.tileIcons[var11];
         } else {
            switch(var5) {
            case 0:
               var10[0] = !isNeighbour(var0, var1, var2, var3.east().north(), var5, var6, var7);
               var10[1] = !isNeighbour(var0, var1, var2, var3.west().north(), var5, var6, var7);
               var10[2] = !isNeighbour(var0, var1, var2, var3.east().south(), var5, var6, var7);
               var10[3] = !isNeighbour(var0, var1, var2, var3.west().south(), var5, var6, var7);
            case 1:
               var10[0] = !isNeighbour(var0, var1, var2, var3.east().south(), var5, var6, var7);
               var10[1] = !isNeighbour(var0, var1, var2, var3.west().south(), var5, var6, var7);
               var10[2] = !isNeighbour(var0, var1, var2, var3.east().north(), var5, var6, var7);
               var10[3] = !isNeighbour(var0, var1, var2, var3.west().north(), var5, var6, var7);
            case 2:
               var10[0] = !isNeighbour(var0, var1, var2, var3.west().down(), var5, var6, var7);
               var10[1] = !isNeighbour(var0, var1, var2, var3.east().down(), var5, var6, var7);
               var10[2] = !isNeighbour(var0, var1, var2, var3.west().up(), var5, var6, var7);
               var10[3] = !isNeighbour(var0, var1, var2, var3.east().up(), var5, var6, var7);
            case 3:
               var10[0] = !isNeighbour(var0, var1, var2, var3.east().down(), var5, var6, var7);
               var10[1] = !isNeighbour(var0, var1, var2, var3.west().down(), var5, var6, var7);
               var10[2] = !isNeighbour(var0, var1, var2, var3.east().up(), var5, var6, var7);
               var10[3] = !isNeighbour(var0, var1, var2, var3.west().up(), var5, var6, var7);
            case 4:
               var10[0] = !isNeighbour(var0, var1, var2, var3.down().south(), var5, var6, var7);
               var10[1] = !isNeighbour(var0, var1, var2, var3.down().north(), var5, var6, var7);
               var10[2] = !isNeighbour(var0, var1, var2, var3.up().south(), var5, var6, var7);
               var10[3] = !isNeighbour(var0, var1, var2, var3.up().north(), var5, var6, var7);
            case 5:
               var10[0] = !isNeighbour(var0, var1, var2, var3.down().north(), var5, var6, var7);
               var10[1] = !isNeighbour(var0, var1, var2, var3.down().south(), var5, var6, var7);
               var10[2] = !isNeighbour(var0, var1, var2, var3.up().north(), var5, var6, var7);
               var10[3] = !isNeighbour(var0, var1, var2, var3.up().south(), var5, var6, var7);
            default:
               if(var11 == 13 && var10[0]) {
                  var11 = 4;
               }

               if(var11 == 15 && var10[1]) {
                  var11 = 5;
               }

               if(var11 == 37 && var10[2]) {
                  var11 = 16;
               }

               if(var11 == 39 && var10[3]) {
                  var11 = 17;
               }

               if(var11 == 14 && var10[0] && var10[1]) {
                  var11 = 7;
               }

               if(var11 == 25 && var10[0] && var10[2]) {
                  var11 = 6;
               }

               if(var11 == 27 && var10[3] && var10[1]) {
                  var11 = 19;
               }

               if(var11 == 38 && var10[3] && var10[2]) {
                  var11 = 18;
               }

               if(var11 == 14 && !var10[0] && var10[1]) {
                  var11 = 31;
               }

               if(var11 == 25 && var10[0] && !var10[2]) {
                  var11 = 30;
               }

               if(var11 == 27 && !var10[3] && var10[1]) {
                  var11 = 41;
               }

               if(var11 == 38 && var10[3] && !var10[2]) {
                  var11 = 40;
               }

               if(var11 == 14 && var10[0] && !var10[1]) {
                  var11 = 29;
               }

               if(var11 == 25 && !var10[0] && var10[2]) {
                  var11 = 28;
               }

               if(var11 == 27 && var10[3] && !var10[1]) {
                  var11 = 43;
               }

               if(var11 == 38 && !var10[3] && var10[2]) {
                  var11 = 42;
               }

               if(var11 == 26 && var10[0] && var10[1] && var10[2] && var10[3]) {
                  var11 = 46;
               }

               if(var11 == 26 && !var10[0] && var10[1] && var10[2] && var10[3]) {
                  var11 = 9;
               }

               if(var11 == 26 && var10[0] && !var10[1] && var10[2] && var10[3]) {
                  var11 = 21;
               }

               if(var11 == 26 && var10[0] && var10[1] && !var10[2] && var10[3]) {
                  var11 = 8;
               }

               if(var11 == 26 && var10[0] && var10[1] && var10[2] && !var10[3]) {
                  var11 = 20;
               }

               if(var11 == 26 && var10[0] && var10[1] && !var10[2] && !var10[3]) {
                  var11 = 11;
               }

               if(var11 == 26 && !var10[0] && !var10[1] && var10[2] && var10[3]) {
                  var11 = 22;
               }

               if(var11 == 26 && !var10[0] && var10[1] && !var10[2] && var10[3]) {
                  var11 = 23;
               }

               if(var11 == 26 && var10[0] && !var10[1] && var10[2] && !var10[3]) {
                  var11 = 10;
               }

               if(var11 == 26 && var10[0] && !var10[1] && !var10[2] && var10[3]) {
                  var11 = 34;
               }

               if(var11 == 26 && !var10[0] && var10[1] && var10[2] && !var10[3]) {
                  var11 = 35;
               }

               if(var11 == 26 && var10[0] && !var10[1] && !var10[2] && !var10[3]) {
                  var11 = 32;
               }

               if(var11 == 26 && !var10[0] && var10[1] && !var10[2] && !var10[3]) {
                  var11 = 33;
               }

               if(var11 == 26 && !var10[0] && !var10[1] && var10[2] && !var10[3]) {
                  var11 = 44;
               }

               if(var11 == 26 && !var10[0] && !var10[1] && !var10[2] && var10[3]) {
                  var11 = 45;
               }

               return var0.tileIcons[var11];
            }
         }
      }
   }

   private static boolean isNeighbour(ConnectedProperties var0, IBlockAccess var1, IBlockState var2, BlockPos var3, int var4, TextureAtlasSprite var5, int var6) {
      MatchBlock.b();
      IBlockState var8 = var1.getBlockState(var3);
      if(var2 == var8) {
         return true;
      } else if(var0.connect == 2) {
         if(var8 == null) {
            return false;
         } else if(var8 == AIR_DEFAULT_STATE) {
            return false;
         } else {
            TextureAtlasSprite var9 = getNeighbourIcon(var1, var3, var8, var4);
            return var9 == var5;
         }
      } else {
         return var0.connect == 3?(var8 == null?false:(var8 == AIR_DEFAULT_STATE?false:var8.getBlock().getMaterial() == var2.getBlock().getMaterial())):false;
      }
   }

   private static TextureAtlasSprite getNeighbourIcon(IBlockAccess var0, BlockPos var1, IBlockState var2, int var3) {
      MatchBlock.b();
      var2 = var2.getBlock().getActualState(var2, var0, var1);
      IBakedModel var5 = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(var2);
      return null;
   }

   private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties var0, IBlockAccess var1, IBlockState var2, BlockPos var3, int var4, int var5, TextureAtlasSprite var6, int var7) {
      MatchBlock.b();
      boolean var9 = false;
      boolean var10 = false;
      switch(var4) {
      case 0:
         switch(var5) {
         case 0:
         case 1:
            return null;
         case 2:
            isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
         case 3:
            isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         case 4:
            isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
         case 5:
            var9 = isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
            var10 = isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
         }
      case 1:
         switch(var5) {
         case 0:
            isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         case 1:
            isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         case 2:
         case 3:
            return null;
         case 4:
            isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
         case 5:
            var9 = isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
            var10 = isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
         }
      case 2:
         switch(var5) {
         case 0:
            isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
         case 1:
            isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
         case 2:
            isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
         case 3:
            isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
         case 4:
         case 5:
            return null;
         }
      default:
         byte var11 = 3;
         if(var9) {
            if(var10) {
               var11 = (boolean)1;
            }

            var11 = (boolean)2;
         }

         if(var10) {
            var11 = (boolean)0;
         }

         var11 = 3;
         return var0.tileIcons[var11];
      }
   }

   private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties var0, IBlockAccess var1, IBlockState var2, BlockPos var3, int var4, int var5, TextureAtlasSprite var6, int var7) {
      MatchBlock.b();
      boolean var9 = false;
      boolean var10 = false;
      switch(var4) {
      case 0:
         if(var5 == 1 || var5 == 0) {
            return null;
         } else {
            isNeighbour(var0, var1, var2, var3.down(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
         }
      case 1:
         if(var5 == 3 || var5 == 2) {
            return null;
         } else {
            isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
            isNeighbour(var0, var1, var2, var3.north(), var5, var6, var7);
         }
      case 2:
         if(var5 == 5 || var5 == 4) {
            return null;
         } else {
            var9 = isNeighbour(var0, var1, var2, var3.west(), var5, var6, var7);
            var10 = isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         }
      default:
         byte var11 = 3;
         if(var9) {
            if(var10) {
               var11 = (boolean)1;
            }

            var11 = (boolean)2;
         }

         if(var10) {
            var11 = (boolean)0;
         }

         var11 = 3;
         return var0.tileIcons[var11];
      }
   }

   private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties var0, IBlockAccess var1, IBlockState var2, BlockPos var3, int var4, int var5, TextureAtlasSprite var6, int var7) {
      MatchBlock.b();
      TextureAtlasSprite[] var9 = var0.tileIcons;
      TextureAtlasSprite var10 = getConnectedTextureHorizontal(var0, var1, var2, var3, var4, var5, var6, var7);
      if(var10 != null && var10 != var6 && var10 != var9[3]) {
         return var10;
      } else {
         TextureAtlasSprite var11 = getConnectedTextureVertical(var0, var1, var2, var3, var4, var5, var6, var7);
         return var11 == var9[0]?var9[4]:(var11 == var9[1]?var9[5]:(var11 == var9[2]?var9[6]:var11));
      }
   }

   private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties var0, IBlockAccess var1, IBlockState var2, BlockPos var3, int var4, int var5, TextureAtlasSprite var6, int var7) {
      MatchBlock.b();
      TextureAtlasSprite[] var9 = var0.tileIcons;
      TextureAtlasSprite var10 = getConnectedTextureVertical(var0, var1, var2, var3, var4, var5, var6, var7);
      if(var10 != null && var10 != var6 && var10 != var9[3]) {
         return var10;
      } else {
         TextureAtlasSprite var11 = getConnectedTextureHorizontal(var0, var1, var2, var3, var4, var5, var6, var7);
         return var11 == var9[0]?var9[4]:(var11 == var9[1]?var9[5]:(var11 == var9[2]?var9[6]:var11));
      }
   }

   private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties var0, IBlockAccess var1, IBlockState var2, BlockPos var3, int var4, int var5, TextureAtlasSprite var6, int var7) {
      MatchBlock.b();
      boolean var9 = false;
      switch(var4) {
      case 0:
         if(var5 == 1 || var5 == 0) {
            return null;
         } else {
            isNeighbour(var0, var1, var2, var3.up(), var5, var6, var7);
         }
      case 1:
         if(var5 == 3 || var5 == 2) {
            return null;
         } else {
            isNeighbour(var0, var1, var2, var3.south(), var5, var6, var7);
         }
      case 2:
         if(var5 == 5 || var5 == 4) {
            return null;
         } else {
            var9 = isNeighbour(var0, var1, var2, var3.east(), var5, var6, var7);
         }
      default:
         if(var9) {
            return var0.tileIcons[0];
         } else {
            return null;
         }
      }
   }

   public static void updateIcons(TextureMap var0) {
      MatchBlock.b();
      blockProperties = (ConnectedProperties[][])((ConnectedProperties[][])null);
      tileProperties = (ConnectedProperties[][])((ConnectedProperties[][])null);
      spriteQuadMaps = null;
      if(Config.k()) {
         IResourcePack[] var2 = Config.getResourcePacks();
         int var3 = var2.length - 1;
         IResourcePack var4 = var2[var3];
         updateIcons(var0, var4);
         --var3;
         updateIcons(var0, Config.getDefaultResourcePack());
         ResourceLocation var6 = new ResourceLocation("mcpatcher/ctm/default/empty");
         a = var0.b(var6);
         spriteQuadMaps = new Map[var0.getCountRegisteredSprites() + 1];
         if(blockProperties.length <= 0) {
            blockProperties = (ConnectedProperties[][])((ConnectedProperties[][])null);
         }

         if(tileProperties.length <= 0) {
            tileProperties = (ConnectedProperties[][])((ConnectedProperties[][])null);
         }
      }

   }

   private static void updateIconEmpty(TextureMap var0) {
   }

   public static void updateIcons(TextureMap var0, IResourcePack var1) {
      String[] var3 = ResUtils.collectFiles(var1, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
      MatchBlock.b();
      Arrays.sort((Object[])var3);
      List var4 = makePropertyList(tileProperties);
      List var5 = makePropertyList(blockProperties);
      int var6 = 0;
      if(var6 < var3.length) {
         String var7 = var3[var6];
         Config.dbg("ConnectedTextures: " + var7);

         try {
            ResourceLocation var8 = new ResourceLocation(var7);
            InputStream var9 = var1.getInputStream(var8);
            Config.warn("ConnectedTextures file not found: " + var7);
            Properties var10 = new Properties();
            var10.load(var9);
            ConnectedProperties var11 = new ConnectedProperties(var10, var7);
            if(var11.isValid(var7)) {
               var11.updateIcons(var0);
               addToTileList(var11, var4);
               addToBlockList(var11, var5);
            }
         } catch (FileNotFoundException var12) {
            Config.warn("ConnectedTextures file not found: " + var7);
         } catch (Exception var13) {
            var13.printStackTrace();
         }

         ++var6;
      }

      blockProperties = propertyListToArray(var5);
      tileProperties = propertyListToArray(var4);
      multipass = detectMultipass();
      Config.dbg("Multipass connected textures: " + multipass);
   }

   private static List makePropertyList(ConnectedProperties[][] var0) {
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      int var3 = 0;
      if(var3 < var0.length) {
         ConnectedProperties[] var4 = var0[var3];
         ArrayList var5 = null;
         var5 = new ArrayList(Arrays.asList(var4));
         var2.add(var5);
         ++var3;
      }

      return var2;
   }

   private static boolean detectMultipass() {
      MatchBlock.b();
      ArrayList var1 = new ArrayList();
      int var2 = 0;
      if(var2 < tileProperties.length) {
         ConnectedProperties[] var3 = tileProperties[var2];
         if(var3 != null) {
            var1.addAll(Arrays.asList(var3));
         }

         ++var2;
      }

      var2 = 0;
      if(var2 < blockProperties.length) {
         ConnectedProperties[] var11 = blockProperties[var2];
         if(var11 != null) {
            var1.addAll(Arrays.asList(var11));
         }

         ++var2;
      }

      ConnectedProperties[] var10 = (ConnectedProperties[])((ConnectedProperties[])((ConnectedProperties[])var1.toArray(new ConnectedProperties[var1.size()])));
      HashSet var12 = new HashSet();
      HashSet var4 = new HashSet();
      int var5 = 0;
      if(var5 < var10.length) {
         ConnectedProperties var6 = var10[var5];
         if(var6.matchTileIcons != null) {
            var12.addAll(Arrays.asList(var6.matchTileIcons));
         }

         if(var6.tileIcons != null) {
            var4.addAll(Arrays.asList(var6.tileIcons));
         }

         ++var5;
      }

      var12.retainAll(var4);
      return !var12.isEmpty();
   }

   private static ConnectedProperties[][] propertyListToArray(List var0) {
      MatchBlock.b();
      ConnectedProperties[][] var2 = new ConnectedProperties[var0.size()][];
      int var3 = 0;
      if(var3 < var0.size()) {
         List var4 = (List)var0.get(var3);
         ConnectedProperties[] var5 = (ConnectedProperties[])((ConnectedProperties[])((ConnectedProperties[])var4.toArray(new ConnectedProperties[var4.size()])));
         var2[var3] = var5;
         ++var3;
      }

      return var2;
   }

   private static void addToTileList(ConnectedProperties var0, List var1) {
      acE[] var2 = MatchBlock.b();
      if(var0.matchTileIcons != null) {
         int var3 = 0;
         if(var3 < var0.matchTileIcons.length) {
            TextureAtlasSprite var4 = var0.matchTileIcons[var3];
            if(!(var4 instanceof TextureAtlasSprite)) {
               Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + var4 + ", name: " + var4.getIconName());
            }

            int var5 = var4.getIndexInMap();
            Config.warn("Invalid tile ID: " + var5 + ", icon: " + var4.getIconName());
            addToList(var0, var1, var5);
            ++var3;
         }
      }

   }

   private static void addToBlockList(ConnectedProperties var0, List var1) {
      acE[] var2 = MatchBlock.b();
      if(var0.matchBlocks != null) {
         int var3 = 0;
         if(var3 < var0.matchBlocks.length) {
            int var4 = var0.matchBlocks[var3].getBlockId();
            Config.warn("Invalid block ID: " + var4);
            addToList(var0, var1, var4);
            ++var3;
         }
      }

   }

   private static void addToList(ConnectedProperties var0, List var1, int var2) {
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

   private static String[] getDefaultCtmPaths() {
      MatchBlock.b();
      ArrayList var1 = new ArrayList();
      String var2 = "mcpatcher/ctm/default/";
      if(Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
         var1.add(var2 + "glass.properties");
         var1.add(var2 + "glasspane.properties");
      }

      if(Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
         var1.add(var2 + "bookshelf.properties");
      }

      if(Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
         var1.add(var2 + "sandstone.properties");
      }

      String[] var3 = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
      int var4 = 0;
      if(var4 < var3.length) {
         String var5 = var3[var4];
         if(Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + var5 + ".png"))) {
            var1.add(var2 + var4 + "_glass_" + var5 + "/glass_" + var5 + ".properties");
            var1.add(var2 + var4 + "_glass_" + var5 + "/glass_pane_" + var5 + ".properties");
         }

         ++var4;
      }

      String[] var7 = (String[])((String[])((String[])var1.toArray(new String[var1.size()])));
      return var7;
   }

   public static int getPaneTextureIndex(boolean var0, boolean var1, boolean var2, boolean var3) {
      acE[] var4 = MatchBlock.b();
      return var1 && var0?(var2?(var3?34:50):(var3?18:2)):(var1 && !var0?(var2?(var3?35:51):(var3?19:3)):(!var1 && var0?(var2?(var3?33:49):(var3?17:1)):(var2?(var3?32:48):(var3?16:0))));
   }

   public static int a(int var0) {
      MatchBlock.b();
      int var2 = var0 % 16;
      return var2 == 1?var0 + 2:(var2 == 3?var0 - 2:var0);
   }

   public static TextureAtlasSprite getCtmTexture(ConnectedProperties var0, int var1, TextureAtlasSprite var2) {
      acE[] var3 = MatchBlock.b();
      if(var0.method != 1) {
         return var2;
      } else if(var1 >= 0 && var1 < ctmIndexes.length) {
         int var4 = ctmIndexes[var1];
         TextureAtlasSprite[] var5 = var0.tileIcons;
         return var4 >= 0 && var4 < var5.length?var5[var4]:var2;
      } else {
         return var2;
      }
   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}

package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

public class ItemMap extends ItemMapBase {
   private static final String __OBFID = "CL_00000047";

   public void updateMapData(World var1, Entity var2, MapData var3) {
      if (var1.provider.getDimensionId() == var3.dimension && var2 instanceof EntityPlayer) {
         int var4 = 1 << var3.scale;
         int var5 = var3.xCenter;
         int var6 = var3.zCenter;
         int var7 = MathHelper.floor_double(var2.posX - (double)var5) / var4 + 64;
         int var8 = MathHelper.floor_double(var2.posZ - (double)var6) / var4 + 64;
         int var9 = 128 / var4;
         if (var1.provider.getHasNoSky()) {
            var9 /= 2;
         }

         MapData.MapInfo var10 = var3.func_82568_a((EntityPlayer)var2);
         ++var10.field_82569_d;
         boolean var11 = false;

         for(int var12 = var7 - var9 + 1; var12 < var7 + var9; ++var12) {
            if ((var12 & 15) == (var10.field_82569_d & 15) || var11) {
               var11 = false;
               double var13 = 0.0D;

               for(int var15 = var8 - var9 - 1; var15 < var8 + var9; ++var15) {
                  if (var12 >= 0 && var15 >= -1 && var12 < 128 && var15 < 128) {
                     int var16 = var12 - var7;
                     int var17 = var15 - var8;
                     boolean var18 = var16 * var16 + var17 * var17 > (var9 - 2) * (var9 - 2);
                     int var19 = (var5 / var4 + var12 - 64) * var4;
                     int var20 = (var6 / var4 + var15 - 64) * var4;
                     HashMultiset var21 = HashMultiset.create();
                     Chunk var22 = var1.getChunkFromBlockCoords(new BlockPos(var19, 0, var20));
                     if (!var22.isEmpty()) {
                        int var23 = var19 & 15;
                        int var24 = var20 & 15;
                        int var25 = 0;
                        double var26 = 0.0D;
                        int var28;
                        if (var1.provider.getHasNoSky()) {
                           var28 = var19 + var20 * 231871;
                           var28 = var28 * var28 * 31287121 + var28 * 11;
                           if ((var28 >> 20 & 1) == 0) {
                              var21.add(Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)), 10);
                           } else {
                              var21.add(Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE)), 100);
                           }

                           var26 = 100.0D;
                        } else {
                           for(var28 = 0; var28 < var4; ++var28) {
                              for(int var29 = 0; var29 < var4; ++var29) {
                                 int var30 = var22.getHeight(var28 + var23, var29 + var24) + 1;
                                 IBlockState var31 = Blocks.air.getDefaultState();
                                 if (var30 > 1) {
                                    do {
                                       --var30;
                                       var31 = var22.getBlockState(new BlockPos(var28 + var23, var30, var29 + var24));
                                    } while(var31.getBlock().getMapColor(var31) == MapColor.airColor && var30 > 0);

                                    if (var30 > 0 && var31.getBlock().getMaterial().isLiquid()) {
                                       int var32 = var30 - 1;

                                       Block var33;
                                       do {
                                          var33 = var22.getBlock(var28 + var23, var32--, var29 + var24);
                                          ++var25;
                                       } while(var32 > 0 && var33.getMaterial().isLiquid());
                                    }
                                 }

                                 var26 += (double)var30 / (double)(var4 * var4);
                                 var21.add(var31.getBlock().getMapColor(var31));
                              }
                           }
                        }

                        var25 /= var4 * var4;
                        double var35 = (var26 - var13) * 4.0D / (double)(var4 + 4) + ((double)(var12 + var15 & 1) - 0.5D) * 0.4D;
                        byte var36 = 1;
                        if (var35 > 0.6D) {
                           var36 = 2;
                        }

                        if (var35 < -0.6D) {
                           var36 = 0;
                        }

                        MapColor var37 = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(var21), MapColor.airColor);
                        if (var37 == MapColor.waterColor) {
                           var35 = (double)var25 * 0.1D + (double)(var12 + var15 & 1) * 0.2D;
                           var36 = 1;
                           if (var35 < 0.5D) {
                              var36 = 2;
                           }

                           if (var35 > 0.9D) {
                              var36 = 0;
                           }
                        }

                        var13 = var26;
                        if (var15 >= 0 && var16 * var16 + var17 * var17 < var9 * var9 && (!var18 || (var12 + var15 & 1) != 0)) {
                           byte var38 = var3.colors[var12 + var15 * 128];
                           byte var34 = (byte)(var37.colorIndex * 4 + var36);
                           if (var38 != var34) {
                              var3.colors[var12 + var15 * 128] = var34;
                              var3.func_176053_a(var12, var15);
                              var11 = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public static MapData loadMapData(int var0, World var1) {
      String var2 = String.valueOf((new StringBuilder("map_")).append(var0));
      MapData var3 = (MapData)var1.loadItemData(MapData.class, var2);
      if (var3 == null) {
         var3 = new MapData(var2);
         var1.setItemData(var2, var3);
      }

      return var3;
   }

   public void onCreated(ItemStack var1, World var2, EntityPlayer var3) {
      if (var1.hasTagCompound() && var1.getTagCompound().getBoolean("map_is_scaling")) {
         MapData var4 = Items.filled_map.getMapData(var1, var2);
         var1.setItemDamage(var2.getUniqueDataId("map"));
         MapData var5 = new MapData(String.valueOf((new StringBuilder("map_")).append(var1.getMetadata())));
         var5.scale = (byte)(var4.scale + 1);
         if (var5.scale > 4) {
            var5.scale = 4;
         }

         var5.func_176054_a((double)var4.xCenter, (double)var4.zCenter, var5.scale);
         var5.dimension = var4.dimension;
         var5.markDirty();
         var2.setItemData(String.valueOf((new StringBuilder("map_")).append(var1.getMetadata())), var5);
      }

   }

   protected ItemMap() {
      this.setHasSubtypes(true);
   }

   public Packet createMapDataPacket(ItemStack var1, World var2, EntityPlayer var3) {
      return this.getMapData(var1, var2).func_176052_a(var1, var2, var3);
   }

   public MapData getMapData(ItemStack var1, World var2) {
      String var3 = String.valueOf((new StringBuilder("map_")).append(var1.getMetadata()));
      MapData var4 = (MapData)var2.loadItemData(MapData.class, var3);
      if (var4 == null && !var2.isRemote) {
         var1.setItemDamage(var2.getUniqueDataId("map"));
         var3 = String.valueOf((new StringBuilder("map_")).append(var1.getMetadata()));
         var4 = new MapData(var3);
         var4.scale = 3;
         var4.func_176054_a((double)var2.getWorldInfo().getSpawnX(), (double)var2.getWorldInfo().getSpawnZ(), var4.scale);
         var4.dimension = (byte)var2.provider.getDimensionId();
         var4.markDirty();
         var2.setItemData(var3, var4);
      }

      return var4;
   }

   public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4) {
      MapData var5 = this.getMapData(var1, var2.worldObj);
      if (var4) {
         if (var5 == null) {
            var3.add("Unknown map");
         } else {
            var3.add(String.valueOf((new StringBuilder("Scaling at 1:")).append(1 << var5.scale)));
            var3.add(String.valueOf((new StringBuilder("(Level ")).append(var5.scale).append("/").append(4).append(")")));
         }
      }

   }

   public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
      if (!var2.isRemote) {
         MapData var6 = this.getMapData(var1, var2);
         if (var3 instanceof EntityPlayer) {
            EntityPlayer var7 = (EntityPlayer)var3;
            var6.updateVisiblePlayers(var7, var1);
         }

         if (var5) {
            this.updateMapData(var2, var3, var6);
         }
      }

   }
}

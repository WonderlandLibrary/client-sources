package net.minecraft.world;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public final class SpawnerAnimals {
   private static final int field_180268_a = (int)Math.pow(17.0D, 2.0D);
   private static final String __OBFID = "CL_00000152";
   private final Set eligibleChunksForSpawning = Sets.newHashSet();

   public static void performWorldGenSpawning(World var0, BiomeGenBase var1, int var2, int var3, int var4, int var5, Random var6) {
      List var7 = var1.getSpawnableList(EnumCreatureType.CREATURE);
      if (!var7.isEmpty()) {
         while(var6.nextFloat() < var1.getSpawningChance()) {
            BiomeGenBase.SpawnListEntry var8 = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(var0.rand, var7);
            int var9 = var8.minGroupCount + var6.nextInt(1 + var8.maxGroupCount - var8.minGroupCount);
            IEntityLivingData var10 = null;
            int var11 = var2 + var6.nextInt(var4);
            int var12 = var3 + var6.nextInt(var5);
            int var13 = var11;
            int var14 = var12;

            for(int var15 = 0; var15 < var9; ++var15) {
               boolean var16 = false;

               for(int var17 = 0; !var16 && var17 < 4; ++var17) {
                  BlockPos var18 = var0.func_175672_r(new BlockPos(var11, 0, var12));
                  if (func_180267_a(EntityLiving.SpawnPlacementType.ON_GROUND, var0, var18)) {
                     EntityLiving var19;
                     try {
                        var19 = (EntityLiving)var8.entityClass.getConstructor(World.class).newInstance(var0);
                     } catch (Exception var21) {
                        var21.printStackTrace();
                        continue;
                     }

                     var19.setLocationAndAngles((double)((float)var11 + 0.5F), (double)var18.getY(), (double)((float)var12 + 0.5F), var6.nextFloat() * 360.0F, 0.0F);
                     var0.spawnEntityInWorld(var19);
                     var10 = var19.func_180482_a(var0.getDifficultyForLocation(new BlockPos(var19)), var10);
                     var16 = true;
                  }

                  var11 += var6.nextInt(5) - var6.nextInt(5);

                  for(var12 += var6.nextInt(5) - var6.nextInt(5); var11 < var2 || var11 >= var2 + var4 || var12 < var3 || var12 >= var3 + var4; var12 = var14 + var6.nextInt(5) - var6.nextInt(5)) {
                     var11 = var13 + var6.nextInt(5) - var6.nextInt(5);
                  }
               }
            }
         }
      }

   }

   protected static BlockPos func_180621_a(World var0, int var1, int var2) {
      Chunk var3 = var0.getChunkFromChunkCoords(var1, var2);
      int var4 = var1 * 16 + var0.rand.nextInt(16);
      int var5 = var2 * 16 + var0.rand.nextInt(16);
      int var6 = MathHelper.func_154354_b(var3.getHeight(new BlockPos(var4, 0, var5)) + 1, 16);
      int var7 = var0.rand.nextInt(var6 > 0 ? var6 : var3.getTopFilledSegment() + 16 - 1);
      return new BlockPos(var4, var7, var5);
   }

   public static boolean func_180267_a(EntityLiving.SpawnPlacementType var0, World var1, BlockPos var2) {
      if (!var1.getWorldBorder().contains(var2)) {
         return false;
      } else {
         Block var3 = var1.getBlockState(var2).getBlock();
         if (var0 == EntityLiving.SpawnPlacementType.IN_WATER) {
            return var3.getMaterial().isLiquid() && var1.getBlockState(var2.offsetDown()).getBlock().getMaterial().isLiquid() && !var1.getBlockState(var2.offsetUp()).getBlock().isNormalCube();
         } else {
            BlockPos var4 = var2.offsetDown();
            if (!World.doesBlockHaveSolidTopSurface(var1, var4)) {
               return false;
            } else {
               Block var5 = var1.getBlockState(var4).getBlock();
               boolean var6 = var5 != Blocks.bedrock && var5 != Blocks.barrier;
               return var6 && !var3.isNormalCube() && !var3.getMaterial().isLiquid() && !var1.getBlockState(var2.offsetUp()).getBlock().isNormalCube();
            }
         }
      }
   }

   public int findChunksForSpawning(WorldServer var1, boolean var2, boolean var3, boolean var4) {
      if (!var2 && !var3) {
         return 0;
      } else {
         this.eligibleChunksForSpawning.clear();
         int var5 = 0;
         Iterator var6 = var1.playerEntities.iterator();

         while(true) {
            int var7;
            int var8;
            EntityPlayer var9;
            int var12;
            do {
               if (!var6.hasNext()) {
                  int var37 = 0;
                  BlockPos var38 = var1.getSpawnPoint();
                  EnumCreatureType[] var39 = EnumCreatureType.values();
                  var7 = var39.length;

                  label168:
                  for(var12 = 0; var12 < var7; ++var12) {
                     EnumCreatureType var40 = var39[var12];
                     if ((!var40.getPeacefulCreature() || var3) && (var40.getPeacefulCreature() || var2) && (!var40.getAnimal() || var4)) {
                        var8 = var1.countEntities(var40.getCreatureClass());
                        int var41 = var40.getMaxNumberOfCreature() * var5 / field_180268_a;
                        if (var8 <= var41) {
                           Iterator var15 = this.eligibleChunksForSpawning.iterator();

                           label165:
                           while(true) {
                              int var18;
                              int var19;
                              int var20;
                              Block var21;
                              do {
                                 if (!var15.hasNext()) {
                                    continue label168;
                                 }

                                 ChunkCoordIntPair var16 = (ChunkCoordIntPair)var15.next();
                                 BlockPos var17 = func_180621_a(var1, var16.chunkXPos, var16.chunkZPos);
                                 var18 = var17.getX();
                                 var19 = var17.getY();
                                 var20 = var17.getZ();
                                 var21 = var1.getBlockState(var17).getBlock();
                              } while(var21.isNormalCube());

                              int var22 = 0;

                              for(int var23 = 0; var23 < 3; ++var23) {
                                 int var24 = var18;
                                 int var25 = var19;
                                 int var26 = var20;
                                 byte var27 = 6;
                                 BiomeGenBase.SpawnListEntry var28 = null;
                                 IEntityLivingData var29 = null;

                                 for(int var30 = 0; var30 < 4; ++var30) {
                                    var24 += var1.rand.nextInt(var27) - var1.rand.nextInt(var27);
                                    var25 += var1.rand.nextInt(1) - var1.rand.nextInt(1);
                                    var26 += var1.rand.nextInt(var27) - var1.rand.nextInt(var27);
                                    BlockPos var31 = new BlockPos(var24, var25, var26);
                                    float var32 = (float)var24 + 0.5F;
                                    float var33 = (float)var26 + 0.5F;
                                    if (!var1.func_175636_b((double)var32, (double)var25, (double)var33, 24.0D) && var38.distanceSq((double)var32, (double)var25, (double)var33) >= 576.0D) {
                                       if (var28 == null) {
                                          var28 = var1.func_175734_a(var40, var31);
                                          if (var28 == null) {
                                             break;
                                          }
                                       }

                                       if (var1.func_175732_a(var40, var28, var31) && func_180267_a(EntitySpawnPlacementRegistry.func_180109_a(var28.entityClass), var1, var31)) {
                                          EntityLiving var34;
                                          try {
                                             var34 = (EntityLiving)var28.entityClass.getConstructor(World.class).newInstance(var1);
                                          } catch (Exception var36) {
                                             var36.printStackTrace();
                                             return var37;
                                          }

                                          var34.setLocationAndAngles((double)var32, (double)var25, (double)var33, var1.rand.nextFloat() * 360.0F, 0.0F);
                                          if (var34.getCanSpawnHere() && var34.handleLavaMovement()) {
                                             var29 = var34.func_180482_a(var1.getDifficultyForLocation(new BlockPos(var34)), var29);
                                             if (var34.handleLavaMovement()) {
                                                ++var22;
                                                var1.spawnEntityInWorld(var34);
                                             }

                                             if (var22 >= var34.getMaxSpawnedInChunk()) {
                                                continue label165;
                                             }
                                          }

                                          var37 += var22;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return var37;
               }

               var9 = (EntityPlayer)var6.next();
            } while(var9.func_175149_v());

            int var10 = MathHelper.floor_double(var9.posX / 16.0D);
            var7 = MathHelper.floor_double(var9.posZ / 16.0D);
            byte var11 = 8;

            for(var12 = -var11; var12 <= var11; ++var12) {
               for(var8 = -var11; var8 <= var11; ++var8) {
                  boolean var13 = var12 == -var11 || var12 == var11 || var8 == -var11 || var8 == var11;
                  ChunkCoordIntPair var14 = new ChunkCoordIntPair(var12 + var10, var8 + var7);
                  if (!this.eligibleChunksForSpawning.contains(var14)) {
                     ++var5;
                     if (!var13 && var1.getWorldBorder().contains(var14)) {
                        this.eligibleChunksForSpawning.add(var14);
                     }
                  }
               }
            }
         }
      }
   }
}

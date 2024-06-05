package net.minecraft.world;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.nA;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLiving$SpawnPlacementType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public final class SpawnerAnimals {
   private static final int b = (int)Math.pow(17.0D, 2.0D);
   private final Set a = Sets.newHashSet();

   public int a(WorldServer var1, boolean var2, boolean var3, boolean var4) {
      return 0;
   }

   protected static BlockPos getRandomChunkPosition(World var0, int var1, int var2) {
      Chunk var3 = var0.getChunkFromChunkCoords(var1, var2);
      int var4 = var1 * 16 + var0.rand.nextInt(16);
      int var5 = var2 * 16 + var0.rand.nextInt(16);
      int var6 = MathHelper.func_154354_b(var3.getHeight(new BlockPos(var4, 0, var5)) + 1, 16);
      int var7 = var0.rand.nextInt(var6);
      return new BlockPos(var4, var7, var5);
   }

   public static boolean canCreatureTypeSpawnAtLocation(EntityLiving$SpawnPlacementType var0, World var1, BlockPos var2) {
      if(!var1.getWorldBorder().contains(var2)) {
         return false;
      } else {
         Block var3 = var1.getBlockState(var2).getBlock();
         if(var0 == EntityLiving$SpawnPlacementType.IN_WATER) {
            return var3.getMaterial().isLiquid() && var1.getBlockState(var2.down()).getBlock().getMaterial().isLiquid() && !var1.getBlockState(var2.up()).getBlock().isNormalCube();
         } else {
            BlockPos var4 = var2.down();
            if(!World.doesBlockHaveSolidTopSurface(var1, var4)) {
               return false;
            } else {
               Block var5 = var1.getBlockState(var4).getBlock();
               boolean var6 = var5 != Blocks.bedrock && var5 != Blocks.barrier;
               return !var3.isNormalCube() && !var3.getMaterial().isLiquid() && !var1.getBlockState(var2.up()).getBlock().isNormalCube();
            }
         }
      }
   }

   public static void performWorldGenSpawning(World var0, BiomeGenBase var1, int var2, int var3, int var4, int var5, Random var6) {
      List var7 = var1.getSpawnableList(EnumCreatureType.CREATURE);
      if(!var7.isEmpty()) {
         while(var6.nextFloat() < var1.getSpawningChance()) {
            nA var8 = (nA)WeightedRandom.getRandomItem(var0.rand, var7);
            int var9 = var8.d + var6.nextInt(1 + var8.b - var8.d);
            IEntityLivingData var10 = null;
            int var11 = var2 + var6.nextInt(var4);
            int var12 = var3 + var6.nextInt(var5);
            int var13 = var11;
            int var14 = var12;

            for(int var15 = 0; var15 < var9; ++var15) {
               boolean var16 = false;

               for(int var17 = 0; var17 < 4; ++var17) {
                  BlockPos var18 = var0.getTopSolidOrLiquidBlock(new BlockPos(var11, 0, var12));
                  if(canCreatureTypeSpawnAtLocation(EntityLiving$SpawnPlacementType.ON_GROUND, var0, var18)) {
                     EntityLiving var19;
                     try {
                        var19 = (EntityLiving)var8.c.getConstructor(new Class[]{World.class}).newInstance(new Object[]{var0});
                     } catch (Exception var21) {
                        var21.printStackTrace();
                        continue;
                     }

                     var19.setLocationAndAngles((double)((float)var11 + 0.5F), (double)var18.getY(), (double)((float)var12 + 0.5F), var6.nextFloat() * 360.0F, 0.0F);
                     var0.spawnEntityInWorld(var19);
                     var10 = var19.onInitialSpawn(var0.getDifficultyForLocation(new BlockPos(var19)), var10);
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

   private static Exception a(Exception var0) {
      return var0;
   }
}

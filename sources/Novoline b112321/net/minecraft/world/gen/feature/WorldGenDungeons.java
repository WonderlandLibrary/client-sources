package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenDungeons extends WorldGenerator {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String[] SPAWNERTYPES = new String[]{"Skeleton", "Zombie", "Zombie", "Spider"};
   private static final List CHESTCONTENT = Lists.newArrayList(new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)});

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(2) + 2;
      int var5 = -var4 - 1;
      int var6 = var4 + 1;
      int var7 = var2.nextInt(2) + 2;
      int var8 = -var7 - 1;
      int var9 = var7 + 1;
      int var10 = 0;

      for(int var11 = var5; var11 <= var6; ++var11) {
         for(int var12 = -1; var12 <= 4; ++var12) {
            for(int var13 = var8; var13 <= var9; ++var13) {
               BlockPos var14 = var3.a(var11, var12, var13);
               Material var15 = var1.getBlockState(var14).getBlock().getMaterial();
               boolean var16 = var15.isSolid();
               if(var12 == -1) {
                  return false;
               }

               if(var12 == 4) {
                  return false;
               }

               if((var11 == var5 || var11 == var6 || var13 == var8 || var13 == var9) && var1.isAirBlock(var14) && var1.isAirBlock(var14.up())) {
                  ++var10;
               }
            }
         }
      }

      if(var10 >= 1 && var10 <= 5) {
         for(int var20 = var5; var20 <= var6; ++var20) {
            for(int var23 = 3; var23 >= -1; --var23) {
               for(int var25 = var8; var25 <= var9; ++var25) {
                  BlockPos var27 = var3.a(var20, var23, var25);
                  if(var20 != var5 && var23 != -1 && var25 != var8 && var20 != var6 && var23 != 4 && var25 != var9) {
                     if(var1.getBlockState(var27).getBlock() != Blocks.chest) {
                        var1.setBlockToAir(var27);
                     }
                  } else if(var27.getY() >= 0 && !var1.getBlockState(var27.down()).getBlock().getMaterial().isSolid()) {
                     var1.setBlockToAir(var27);
                  } else if(var1.getBlockState(var27).getBlock().getMaterial().isSolid() && var1.getBlockState(var27).getBlock() != Blocks.chest) {
                     if(var23 == -1 && var2.nextInt(4) != 0) {
                        var1.setBlockState(var27, Blocks.mossy_cobblestone.getDefaultState(), 2);
                     } else {
                        var1.setBlockState(var27, Blocks.cobblestone.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         for(int var21 = 0; var21 < 2; ++var21) {
            for(int var24 = 0; var24 < 3; ++var24) {
               int var26 = var3.getX() + var2.nextInt(var4 * 2 + 1) - var4;
               int var28 = var3.getY();
               int var29 = var3.getZ() + var2.nextInt(var7 * 2 + 1) - var7;
               BlockPos var30 = new BlockPos(var26, var28, var29);
               if(var1.isAirBlock(var30)) {
                  int var17 = 0;

                  for(Object var19 : EnumFacing$Plane.HORIZONTAL) {
                     if(var1.getBlockState(var30.offset((EnumFacing)var19)).getBlock().getMaterial().isSolid()) {
                        ++var17;
                     }
                  }

                  if(var17 == 1) {
                     var1.setBlockState(var30, Blocks.chest.correctFacing(var1, var30, Blocks.chest.getDefaultState()), 2);
                     List var31 = WeightedRandomChestContent.func_177629_a(CHESTCONTENT, new WeightedRandomChestContent[]{Items.enchanted_book.getRandom(var2)});
                     TileEntity var32 = var1.getTileEntity(var30);
                     if(var32 instanceof TileEntityChest) {
                        WeightedRandomChestContent.generateChestContents(var2, var31, (TileEntityChest)var32, 8);
                     }
                     break;
                  }
               }
            }
         }

         var1.setBlockState(var3, Blocks.mob_spawner.getDefaultState(), 2);
         TileEntity var22 = var1.getTileEntity(var3);
         if(var22 instanceof TileEntityMobSpawner) {
            ((TileEntityMobSpawner)var22).getSpawnerBaseLogic().setEntityName(this.pickMobSpawner(var2));
         } else {
            LOGGER.error("Failed to fetch mob spawner entity at (" + var3.getX() + ", " + var3.getY() + ", " + var3.getZ() + ")");
         }

         return true;
      } else {
         return false;
      }
   }

   private String pickMobSpawner(Random var1) {
      return SPAWNERTYPES[var1.nextInt(SPAWNERTYPES.length)];
   }
}

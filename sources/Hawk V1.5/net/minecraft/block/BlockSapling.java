package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockSapling extends BlockBush implements IGrowable {
   public static final PropertyInteger STAGE_PROP = PropertyInteger.create("stage", 0, 1);
   private static final String __OBFID = "CL_00000305";
   public static final PropertyEnum TYPE_PROP = PropertyEnum.create("type", BlockPlanks.EnumType.class);

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote) {
         super.updateTick(var1, var2, var3, var4);
         if (var1.getLightFromNeighbors(var2.offsetUp()) >= 9 && var4.nextInt(7) == 0) {
            this.func_176478_d(var1, var2, var3, var4);
         }
      }

   }

   protected BlockSapling() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE_PROP, BlockPlanks.EnumType.OAK).withProperty(STAGE_PROP, 0));
      float var1 = 0.4F;
      this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var1 * 2.0F, 0.5F + var1);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockPlanks.EnumType[] var4 = BlockPlanks.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockPlanks.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176839_a()));
      }

   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return (double)var1.rand.nextFloat() < 0.45D;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.func_176478_d(var1, var3, var4, var2);
   }

   public boolean isStillGrowing(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{TYPE_PROP, STAGE_PROP});
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(TYPE_PROP, BlockPlanks.EnumType.func_176837_a(var1 & 7)).withProperty(STAGE_PROP, (var1 & 8) >> 3);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks.EnumType)var1.getValue(TYPE_PROP)).func_176839_a();
   }

   public void func_176478_d(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if ((Integer)var3.getValue(STAGE_PROP) == 0) {
         var1.setBlockState(var2, var3.cycleProperty(STAGE_PROP), 4);
      } else {
         this.func_176476_e(var1, var2, var3, var4);
      }

   }

   public boolean func_176477_a(World var1, BlockPos var2, BlockPlanks.EnumType var3) {
      IBlockState var4 = var1.getBlockState(var2);
      return var4.getBlock() == this && var4.getValue(TYPE_PROP) == var3;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockPlanks.EnumType)var1.getValue(TYPE_PROP)).func_176839_a();
      var3 |= (Integer)var1.getValue(STAGE_PROP) << 3;
      return var3;
   }

   public void func_176476_e(World var1, BlockPos var2, IBlockState var3, Random var4) {
      Object var5 = var4.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
      int var6 = 0;
      int var7 = 0;
      boolean var8 = false;
      switch((BlockPlanks.EnumType)var3.getValue(TYPE_PROP)) {
      case SPRUCE:
         label89:
         for(var6 = 0; var6 >= -1; --var6) {
            for(var7 = 0; var7 >= -1; --var7) {
               if (this.func_176477_a(var1, var2.add(var6, 0, var7), BlockPlanks.EnumType.SPRUCE) && this.func_176477_a(var1, var2.add(var6 + 1, 0, var7), BlockPlanks.EnumType.SPRUCE) && this.func_176477_a(var1, var2.add(var6, 0, var7 + 1), BlockPlanks.EnumType.SPRUCE) && this.func_176477_a(var1, var2.add(var6 + 1, 0, var7 + 1), BlockPlanks.EnumType.SPRUCE)) {
                  var5 = new WorldGenMegaPineTree(false, var4.nextBoolean());
                  var8 = true;
                  break label89;
               }
            }
         }

         if (!var8) {
            var7 = 0;
            var6 = 0;
            var5 = new WorldGenTaiga2(true);
         }
         break;
      case BIRCH:
         var5 = new WorldGenForest(true, false);
         break;
      case JUNGLE:
         label106:
         for(var6 = 0; var6 >= -1; --var6) {
            for(var7 = 0; var7 >= -1; --var7) {
               if (this.func_176477_a(var1, var2.add(var6, 0, var7), BlockPlanks.EnumType.JUNGLE) && this.func_176477_a(var1, var2.add(var6 + 1, 0, var7), BlockPlanks.EnumType.JUNGLE) && this.func_176477_a(var1, var2.add(var6, 0, var7 + 1), BlockPlanks.EnumType.JUNGLE) && this.func_176477_a(var1, var2.add(var6 + 1, 0, var7 + 1), BlockPlanks.EnumType.JUNGLE)) {
                  var5 = new WorldGenMegaJungle(true, 10, 20, BlockPlanks.EnumType.JUNGLE.func_176839_a(), BlockPlanks.EnumType.JUNGLE.func_176839_a());
                  var8 = true;
                  break label106;
               }
            }
         }

         if (!var8) {
            var7 = 0;
            var6 = 0;
            var5 = new WorldGenTrees(true, 4 + var4.nextInt(7), BlockPlanks.EnumType.JUNGLE.func_176839_a(), BlockPlanks.EnumType.JUNGLE.func_176839_a(), false);
         }
         break;
      case ACACIA:
         var5 = new WorldGenSavannaTree(true);
         break;
      case DARK_OAK:
         label123:
         for(var6 = 0; var6 >= -1; --var6) {
            for(var7 = 0; var7 >= -1; --var7) {
               if (this.func_176477_a(var1, var2.add(var6, 0, var7), BlockPlanks.EnumType.DARK_OAK) && this.func_176477_a(var1, var2.add(var6 + 1, 0, var7), BlockPlanks.EnumType.DARK_OAK) && this.func_176477_a(var1, var2.add(var6, 0, var7 + 1), BlockPlanks.EnumType.DARK_OAK) && this.func_176477_a(var1, var2.add(var6 + 1, 0, var7 + 1), BlockPlanks.EnumType.DARK_OAK)) {
                  var5 = new WorldGenCanopyTree(true);
                  var8 = true;
                  break label123;
               }
            }
         }

         if (!var8) {
            return;
         }
      case OAK:
      }

      IBlockState var9 = Blocks.air.getDefaultState();
      if (var8) {
         var1.setBlockState(var2.add(var6, 0, var7), var9, 4);
         var1.setBlockState(var2.add(var6 + 1, 0, var7), var9, 4);
         var1.setBlockState(var2.add(var6, 0, var7 + 1), var9, 4);
         var1.setBlockState(var2.add(var6 + 1, 0, var7 + 1), var9, 4);
      } else {
         var1.setBlockState(var2, var9, 4);
      }

      if (!((WorldGenerator)var5).generate(var1, var4, var2.add(var6, 0, var7))) {
         if (var8) {
            var1.setBlockState(var2.add(var6, 0, var7), var3, 4);
            var1.setBlockState(var2.add(var6 + 1, 0, var7), var3, 4);
            var1.setBlockState(var2.add(var6, 0, var7 + 1), var3, 4);
            var1.setBlockState(var2.add(var6 + 1, 0, var7 + 1), var3, 4);
         } else {
            var1.setBlockState(var2, var3, 4);
         }
      }

   }

   static final class SwitchEnumType {
      static final int[] field_177065_a = new int[BlockPlanks.EnumType.values().length];
      private static final String __OBFID = "CL_00002067";

      static {
         try {
            field_177065_a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_177065_a[BlockPlanks.EnumType.BIRCH.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_177065_a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177065_a[BlockPlanks.EnumType.ACACIA.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177065_a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177065_a[BlockPlanks.EnumType.OAK.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}

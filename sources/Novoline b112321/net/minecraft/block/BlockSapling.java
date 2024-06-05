package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.iV;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.BlockSapling$1;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
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
   public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockPlanks$EnumType.class);
   public static final iV Q = iV.a("stage", 0, 1);

   protected BlockSapling() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockPlanks$EnumType.OAK).withProperty(Q, Integer.valueOf(0)));
      float var1 = 0.4F;
      this.setBlockBounds(0.099999994F, 0.0F, 0.099999994F, 0.9F, 0.8F, 0.9F);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + BlockPlanks$EnumType.OAK.getUnlocalizedName() + ".name");
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(!var1.isRemote) {
         super.updateTick(var1, var2, var3, var4);
         if(var1.getLightFromNeighbors(var2.up()) >= 9 && var4.nextInt(7) == 0) {
            this.grow(var1, var2, var3, var4);
         }
      }

   }

   public void grow(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(((Integer)var3.getValue(Q)).intValue() == 0) {
         var1.setBlockState(var2, var3.cycleProperty(Q), 4);
      } else {
         this.generateTree(var1, var2, var3, var4);
      }

   }

   public void generateTree(World var1, BlockPos var2, IBlockState var3, Random var4) {
      Object var5 = var4.nextInt(10) == 0?new WorldGenBigTree(true):new WorldGenTrees(true);
      int var6 = 0;
      int var7 = 0;
      boolean var8 = false;
      switch(BlockSapling$1.$SwitchMap$net$minecraft$block$BlockPlanks$EnumType[((BlockPlanks$EnumType)var3.getValue(TYPE)).ordinal()]) {
      case 1:
         label114:
         for(var6 = 0; var6 >= -1; --var6) {
            for(var7 = 0; var7 >= -1; --var7) {
               if(this.func_181624_a(var1, var2, var6, var7, BlockPlanks$EnumType.SPRUCE)) {
                  new WorldGenMegaPineTree(false, var4.nextBoolean());
                  var8 = true;
                  break label114;
               }
            }
         }

         var7 = 0;
         var6 = 0;
         var5 = new WorldGenTaiga2(true);
         break;
      case 2:
         var5 = new WorldGenForest(true, false);
         break;
      case 3:
         IBlockState var9 = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks$EnumType.JUNGLE);
         IBlockState var10 = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks$EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

         label266:
         for(var6 = 0; var6 >= -1; --var6) {
            for(var7 = 0; var7 >= -1; --var7) {
               if(this.func_181624_a(var1, var2, var6, var7, BlockPlanks$EnumType.JUNGLE)) {
                  new WorldGenMegaJungle(true, 10, 20, var9, var10);
                  var8 = true;
                  break label266;
               }
            }
         }

         var7 = 0;
         var6 = 0;
         var5 = new WorldGenTrees(true, 4 + var4.nextInt(7), var9, var10, false);
         break;
      case 4:
         var5 = new WorldGenSavannaTree(true);
         break;
      case 5:
         for(var6 = 0; var6 >= -1; --var6) {
            for(var7 = 0; var7 >= -1; --var7) {
               if(this.func_181624_a(var1, var2, var6, var7, BlockPlanks$EnumType.DARK_OAK)) {
                  new WorldGenCanopyTree(true);
                  var8 = true;
                  return;
               }
            }
         }

         return;
      case 6:
      }

      IBlockState var20 = Blocks.air.getDefaultState();
      var1.setBlockState(var2.a(var6, 0, var7), var20, 4);
      var1.setBlockState(var2.a(var6 + 1, 0, var7), var20, 4);
      var1.setBlockState(var2.a(var6, 0, var7 + 1), var20, 4);
      var1.setBlockState(var2.a(var6 + 1, 0, var7 + 1), var20, 4);
      if(!((WorldGenerator)var5).generate(var1, var4, var2.a(var6, 0, var7))) {
         var1.setBlockState(var2.a(var6, 0, var7), var3, 4);
         var1.setBlockState(var2.a(var6 + 1, 0, var7), var3, 4);
         var1.setBlockState(var2.a(var6, 0, var7 + 1), var3, 4);
         var1.setBlockState(var2.a(var6 + 1, 0, var7 + 1), var3, 4);
      }

   }

   private boolean func_181624_a(World var1, BlockPos var2, int var3, int var4, BlockPlanks$EnumType var5) {
      return this.isTypeAt(var1, var2.a(var3, 0, var4), var5) && this.isTypeAt(var1, var2.a(var3 + 1, 0, var4), var5) && this.isTypeAt(var1, var2.a(var3, 0, var4 + 1), var5) && this.isTypeAt(var1, var2.a(var3 + 1, 0, var4 + 1), var5);
   }

   public boolean isTypeAt(World var1, BlockPos var2, BlockPlanks$EnumType var3) {
      IBlockState var4 = var1.getBlockState(var2);
      return var4.getBlock() == this && var4.getValue(TYPE) == var3;
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks$EnumType)var1.getValue(TYPE)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockPlanks$EnumType var7 : BlockPlanks$EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.getMetadata()));
      }

   }

   public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return (double)var1.rand.nextFloat() < 0.45D;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.grow(var1, var3, var4, var2);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(TYPE, BlockPlanks$EnumType.byMetadata(var1 & 7)).withProperty(Q, Integer.valueOf((var1 & 8) >> 3));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((BlockPlanks$EnumType)var1.getValue(TYPE)).getMetadata();
      var2 = var2 | ((Integer)var1.getValue(Q)).intValue() << 3;
      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{TYPE, Q});
   }
}

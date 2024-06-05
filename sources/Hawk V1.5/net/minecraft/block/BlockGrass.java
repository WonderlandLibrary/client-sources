package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockGrass extends Block implements IGrowable {
   private static final String __OBFID = "CL_00000251";
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      Block var4 = var2.getBlockState(var3.offsetUp()).getBlock();
      return var1.withProperty(SNOWY, var4 == Blocks.snow || var4 == Blocks.snow_layer);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{SNOWY});
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return BiomeColorHelper.func_180286_a(var1, var2);
   }

   public int getMetaFromState(IBlockState var1) {
      return 0;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockPos var5 = var3.offsetUp();

      label44:
      for(int var6 = 0; var6 < 128; ++var6) {
         BlockPos var7 = var5;

         for(int var8 = 0; var8 < var6 / 16; ++var8) {
            var7 = var7.add(var2.nextInt(3) - 1, (var2.nextInt(3) - 1) * var2.nextInt(3) / 2, var2.nextInt(3) - 1);
            if (var1.getBlockState(var7.offsetDown()).getBlock() != Blocks.grass || var1.getBlockState(var7).getBlock().isNormalCube()) {
               continue label44;
            }
         }

         if (var1.getBlockState(var7).getBlock().blockMaterial == Material.air) {
            if (var2.nextInt(8) == 0) {
               BlockFlower.EnumFlowerType var9 = var1.getBiomeGenForCoords(var7).pickRandomFlower(var2, var7);
               BlockFlower var10 = var9.func_176964_a().func_180346_a();
               IBlockState var11 = var10.getDefaultState().withProperty(var10.func_176494_l(), var9);
               if (var10.canBlockStay(var1, var7, var11)) {
                  var1.setBlockState(var7, var11, 3);
               }
            } else {
               IBlockState var12 = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.field_176497_a, BlockTallGrass.EnumType.GRASS);
               if (Blocks.tallgrass.canBlockStay(var1, var7, var12)) {
                  var1.setBlockState(var7, var12, 3);
               }
            }
         }
      }

   }

   protected BlockGrass() {
      super(Material.grass);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SNOWY, false));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int getBlockColor() {
      return ColorizerGrass.getGrassColor(0.5D, 1.0D);
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote) {
         if (var1.getLightFromNeighbors(var2.offsetUp()) < 4 && var1.getBlockState(var2.offsetUp()).getBlock().getLightOpacity() > 2) {
            var1.setBlockState(var2, Blocks.dirt.getDefaultState());
         } else if (var1.getLightFromNeighbors(var2.offsetUp()) >= 9) {
            for(int var5 = 0; var5 < 4; ++var5) {
               BlockPos var6 = var2.add(var4.nextInt(3) - 1, var4.nextInt(5) - 3, var4.nextInt(3) - 1);
               Block var7 = var1.getBlockState(var6.offsetUp()).getBlock();
               IBlockState var8 = var1.getBlockState(var6);
               if (var8.getBlock() == Blocks.dirt && var8.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && var1.getLightFromNeighbors(var6.offsetUp()) >= 4 && var7.getLightOpacity() <= 2) {
                  var1.setBlockState(var6, Blocks.grass.getDefaultState());
               }
            }
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), var2, var3);
   }

   public int getRenderColor(IBlockState var1) {
      return this.getBlockColor();
   }

   public boolean isStillGrowing(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }
}

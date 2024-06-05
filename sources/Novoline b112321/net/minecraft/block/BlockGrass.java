package net.minecraft.block;

import cc.novoline.modules.visual.XRay;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTallGrass$EnumType;
import net.minecraft.block.IGrowable;
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
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");

   protected BlockGrass() {
      super(Material.grass);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SNOWY, Boolean.FALSE));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      Block var4 = var2.getBlockState(var3.up()).getBlock();
      return var1.withProperty(SNOWY, Boolean.valueOf(var4 == Blocks.snow || var4 == Blocks.snow_layer));
   }

   public int getBlockColor() {
      return ColorizerGrass.getGrassColor(0.5D, 1.0D);
   }

   public int getRenderColor(IBlockState var1) {
      return this.getBlockColor();
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return BiomeColorHelper.getGrassColorAtPos(var1, var2);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(!var1.isRemote) {
         if(var1.getLightFromNeighbors(var2.up()) < 4 && var1.getBlockState(var2.up()).getBlock().getLightOpacity() > 2) {
            var1.setBlockState(var2, Blocks.dirt.getDefaultState());
         } else if(var1.getLightFromNeighbors(var2.up()) >= 9) {
            for(int var5 = 0; var5 < 4; ++var5) {
               BlockPos var6 = var2.a(var4.nextInt(3) - 1, var4.nextInt(5) - 3, var4.nextInt(3) - 1);
               Block var7 = var1.getBlockState(var6.up()).getBlock();
               IBlockState var8 = var1.getBlockState(var6);
               if(var8.getBlock() == Blocks.dirt && var8.getValue(BlockDirt.VARIANT) == BlockDirt$DirtType.DIRT && var1.getLightFromNeighbors(var6.up()) >= 4 && var7.getLightOpacity() <= 2) {
                  var1.setBlockState(var6, Blocks.grass.getDefaultState());
               }
            }
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt$DirtType.DIRT), var2, var3);
   }

   public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockPos var5 = var3.up();

      for(int var6 = 0; var6 < 128; ++var6) {
         BlockPos var7 = var5;
         int var8 = 0;

         while(true) {
            if(var8 >= var6 / 16) {
               if(var1.getBlockState(var7).getBlock().blockMaterial == Material.air) {
                  if(var2.nextInt(8) == 0) {
                     BlockFlower$EnumFlowerType var9 = var1.getBiomeGenForCoords(var7).pickRandomFlower(var2, var7);
                     BlockFlower var10 = var9.getBlockType().getBlock();
                     IBlockState var11 = var10.getDefaultState().withProperty(var10.getTypeProperty(), var9);
                     if(var10.canBlockStay(var1, var7, var11)) {
                        var1.setBlockState(var7, var11, 3);
                     }
                  } else {
                     IBlockState var12 = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass$EnumType.GRASS);
                     if(Blocks.tallgrass.canBlockStay(var1, var7, var12)) {
                        var1.setBlockState(var7, var12, 3);
                     }
                  }
               }
               break;
            }

            var7 = var7.a(var2.nextInt(3) - 1, (var2.nextInt(3) - 1) * var2.nextInt(3) / 2, var2.nextInt(3) - 1);
            if(var1.getBlockState(var7.down()).getBlock() != Blocks.grass || var1.getBlockState(var7).getBlock().isNormalCube()) {
               break;
            }

            ++var8;
         }
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return XRay.isEnabled?super.getBlockLayer():EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   public int getMetaFromState(IBlockState var1) {
      return 0;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{SNOWY});
   }
}

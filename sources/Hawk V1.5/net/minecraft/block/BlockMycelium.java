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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMycelium extends Block {
   private static final String __OBFID = "CL_00000273";
   public static final PropertyBool SNOWY_PROP = PropertyBool.create("snowy");

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      Block var4 = var2.getBlockState(var3.offsetUp()).getBlock();
      return var1.withProperty(SNOWY_PROP, var4 == Blocks.snow || var4 == Blocks.snow_layer);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), var2, var3);
   }

   protected BlockMycelium() {
      super(Material.grass);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SNOWY_PROP, false));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{SNOWY_PROP});
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.randomDisplayTick(var1, var2, var3, var4);
      if (var4.nextInt(10) == 0) {
         var1.spawnParticle(EnumParticleTypes.TOWN_AURA, (double)((float)var2.getX() + var4.nextFloat()), (double)((float)var2.getY() + 1.1F), (double)((float)var2.getZ() + var4.nextFloat()), 0.0D, 0.0D, 0.0D);
      }

   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote) {
         if (var1.getLightFromNeighbors(var2.offsetUp()) < 4 && var1.getBlockState(var2.offsetUp()).getBlock().getLightOpacity() > 2) {
            var1.setBlockState(var2, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
         } else if (var1.getLightFromNeighbors(var2.offsetUp()) >= 9) {
            for(int var5 = 0; var5 < 4; ++var5) {
               BlockPos var6 = var2.add(var4.nextInt(3) - 1, var4.nextInt(5) - 3, var4.nextInt(3) - 1);
               IBlockState var7 = var1.getBlockState(var6);
               Block var8 = var1.getBlockState(var6.offsetUp()).getBlock();
               if (var7.getBlock() == Blocks.dirt && var7.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && var1.getLightFromNeighbors(var6.offsetUp()) >= 4 && var8.getLightOpacity() <= 2) {
                  var1.setBlockState(var6, this.getDefaultState());
               }
            }
         }
      }

   }

   public int getMetaFromState(IBlockState var1) {
      return 0;
   }
}

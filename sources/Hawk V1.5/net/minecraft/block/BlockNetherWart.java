package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockNetherWart extends BlockBush {
   private static final String __OBFID = "CL_00000274";
   public static final PropertyInteger AGE_PROP = PropertyInteger.create("age", 0, 3);

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{AGE_PROP});
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.nether_wart;
   }

   protected BlockNetherWart() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE_PROP, 0));
      this.setTickRandomly(true);
      float var1 = 0.5F;
      this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
      this.setCreativeTab((CreativeTabs)null);
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   protected boolean canPlaceBlockOn(Block var1) {
      return var1 == Blocks.soul_sand;
   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(AGE_PROP);
   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      return this.canPlaceBlockOn(var1.getBlockState(var2.offsetDown()).getBlock());
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(AGE_PROP, var1);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = (Integer)var3.getValue(AGE_PROP);
      if (var5 < 3 && var4.nextInt(10) == 0) {
         var3 = var3.withProperty(AGE_PROP, var5 + 1);
         var1.setBlockState(var2, var3, 2);
      }

      super.updateTick(var1, var2, var3, var4);
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.isRemote) {
         int var6 = 1;
         if ((Integer)var3.getValue(AGE_PROP) >= 3) {
            var6 = 2 + var1.rand.nextInt(3);
            if (var5 > 0) {
               var6 += var1.rand.nextInt(var5 + 1);
            }
         }

         for(int var7 = 0; var7 < var6; ++var7) {
            spawnAsEntity(var1, var2, new ItemStack(Items.nether_wart));
         }
      }

   }
}

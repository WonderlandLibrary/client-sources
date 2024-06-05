package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
   public static final iV P = iV.a("age", 0, 3);

   protected BlockNetherWart() {
      super(Material.plants, MapColor.redColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
      this.setTickRandomly(true);
      float var1 = 0.5F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
      this.setCreativeTab((CreativeTabs)null);
   }

   protected boolean canPlaceBlockOn(Block var1) {
      return var1 == Blocks.soul_sand;
   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      return this.canPlaceBlockOn(var1.getBlockState(var2.down()).getBlock());
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = ((Integer)var3.getValue(P)).intValue();
      if(var5 < 3 && var4.nextInt(10) == 0) {
         var3 = var3.withProperty(P, Integer.valueOf(var5 + 1));
         var1.setBlockState(var2, var3, 2);
      }

      super.updateTick(var1, var2, var3, var4);
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if(!var1.isRemote) {
         int var6 = 1;
         if(((Integer)var3.getValue(P)).intValue() >= 3) {
            var6 = 2 + var1.rand.nextInt(3);
            var6 = var6 + var1.rand.nextInt(var5 + 1);
         }

         for(int var7 = 0; var7 < var6; ++var7) {
            spawnAsEntity(var1, var2, new ItemStack(Items.nether_wart));
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.nether_wart;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(P, Integer.valueOf(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{P});
   }
}

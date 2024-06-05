package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockStainedGlassPane extends BlockPane {
   public static final PropertyEnum field_176245_a = PropertyEnum.create("color", EnumDyeColor.class);
   private static final String __OBFID = "CL_00000313";

   public int getMetaFromState(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(field_176245_a)).func_176765_a();
   }

   public int damageDropped(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(field_176245_a)).func_176765_a();
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         BlockBeacon.func_176450_d(var1, var2);
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176245_a, EnumDyeColor.func_176764_b(var1));
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         BlockBeacon.func_176450_d(var1, var2);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{NORTH, EAST, WEST, SOUTH, field_176245_a});
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(int var4 = 0; var4 < EnumDyeColor.values().length; ++var4) {
         var3.add(new ItemStack(var1, 1, var4));
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   public BlockStainedGlassPane() {
      super(Material.glass, false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(field_176245_a, EnumDyeColor.WHITE));
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }
}

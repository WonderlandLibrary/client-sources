package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
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

public class BlockStainedGlass extends BlockBreakable {
   private static final String __OBFID = "CL_00000312";
   public static final PropertyEnum field_176547_a = PropertyEnum.create("color", EnumDyeColor.class);

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176547_a, EnumDyeColor.func_176764_b(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(field_176547_a)).func_176765_a();
   }

   public int damageDropped(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(field_176547_a)).func_176765_a();
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         BlockBeacon.func_176450_d(var1, var2);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176547_a});
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      EnumDyeColor[] var4 = EnumDyeColor.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EnumDyeColor var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176765_a()));
      }

   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public BlockStainedGlass(Material var1) {
      super(var1, false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176547_a, EnumDyeColor.WHITE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public boolean isFullCube() {
      return false;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         BlockBeacon.func_176450_d(var1, var2);
      }

   }

   public MapColor getMapColor(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(field_176547_a)).func_176768_e();
   }
}

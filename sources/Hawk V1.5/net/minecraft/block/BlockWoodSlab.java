package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWoodSlab extends BlockSlab {
   public static final PropertyEnum field_176557_b = PropertyEnum.create("variant", BlockPlanks.EnumType.class);
   private static final String __OBFID = "CL_00000337";

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState().withProperty(field_176557_b, BlockPlanks.EnumType.func_176837_a(var1 & 7));
      if (!this.isDouble()) {
         var2 = var2.withProperty(HALF_PROP, (var1 & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return var2;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockPlanks.EnumType)var1.getValue(field_176557_b)).func_176839_a();
      if (!this.isDouble() && var1.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP) {
         var3 |= 8;
      }

      return var3;
   }

   public Object func_176553_a(ItemStack var1) {
      return BlockPlanks.EnumType.func_176837_a(var1.getMetadata() & 7);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      if (var1 != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
         BlockPlanks.EnumType[] var4 = BlockPlanks.EnumType.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BlockPlanks.EnumType var7 = var4[var6];
            var3.add(new ItemStack(var1, 1, var7.func_176839_a()));
         }
      }

   }

   protected BlockState createBlockState() {
      return this.isDouble() ? new BlockState(this, new IProperty[]{field_176557_b}) : new BlockState(this, new IProperty[]{HALF_PROP, field_176557_b});
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks.EnumType)var1.getValue(field_176557_b)).func_176839_a();
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.wooden_slab);
   }

   public String getFullSlabName(int var1) {
      return String.valueOf((new StringBuilder(String.valueOf(super.getUnlocalizedName()))).append(".").append(BlockPlanks.EnumType.func_176837_a(var1).func_176840_c()));
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.wooden_slab);
   }

   public IProperty func_176551_l() {
      return field_176557_b;
   }

   public BlockWoodSlab() {
      super(Material.wood);
      IBlockState var1 = this.blockState.getBaseState();
      if (!this.isDouble()) {
         var1 = var1.withProperty(HALF_PROP, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.setDefaultState(var1.withProperty(field_176557_b, BlockPlanks.EnumType.OAK));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }
}

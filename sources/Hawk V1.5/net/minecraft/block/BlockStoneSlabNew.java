package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public abstract class BlockStoneSlabNew extends BlockSlab {
   private static final String __OBFID = "CL_00002087";
   public static final PropertyBool field_176558_b = PropertyBool.create("seamless");
   public static final PropertyEnum field_176559_M = PropertyEnum.create("variant", BlockStoneSlabNew.EnumType.class);

   protected BlockState createBlockState() {
      return this.isDouble() ? new BlockState(this, new IProperty[]{field_176558_b, field_176559_M}) : new BlockState(this, new IProperty[]{HALF_PROP, field_176559_M});
   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState().withProperty(field_176559_M, BlockStoneSlabNew.EnumType.func_176916_a(var1 & 7));
      if (this.isDouble()) {
         var2 = var2.withProperty(field_176558_b, (var1 & 8) != 0);
      } else {
         var2 = var2.withProperty(HALF_PROP, (var1 & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return var2;
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockStoneSlabNew.EnumType)var1.getValue(field_176559_M)).func_176915_a();
   }

   public Object func_176553_a(ItemStack var1) {
      return BlockStoneSlabNew.EnumType.func_176916_a(var1.getMetadata() & 7);
   }

   public String getFullSlabName(int var1) {
      return String.valueOf((new StringBuilder(String.valueOf(super.getUnlocalizedName()))).append(".").append(BlockStoneSlabNew.EnumType.func_176916_a(var1).func_176918_c()));
   }

   public BlockStoneSlabNew() {
      super(Material.rock);
      IBlockState var1 = this.blockState.getBaseState();
      if (this.isDouble()) {
         var1 = var1.withProperty(field_176558_b, false);
      } else {
         var1 = var1.withProperty(HALF_PROP, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.setDefaultState(var1.withProperty(field_176559_M, BlockStoneSlabNew.EnumType.RED_SANDSTONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      if (var1 != Item.getItemFromBlock(Blocks.double_stone_slab2)) {
         BlockStoneSlabNew.EnumType[] var4 = BlockStoneSlabNew.EnumType.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BlockStoneSlabNew.EnumType var7 = var4[var6];
            var3.add(new ItemStack(var1, 1, var7.func_176915_a()));
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.stone_slab2);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.stone_slab2);
   }

   public IProperty func_176551_l() {
      return field_176559_M;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockStoneSlabNew.EnumType)var1.getValue(field_176559_M)).func_176915_a();
      if (this.isDouble()) {
         if ((Boolean)var1.getValue(field_176558_b)) {
            var3 |= 8;
         }
      } else if (var1.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP) {
         var3 |= 8;
      }

      return var3;
   }

   public static enum EnumType implements IStringSerializable {
      private static final String __OBFID = "CL_00002086";
      private final int field_176922_c;
      RED_SANDSTONE("RED_SANDSTONE", 0, 0, "red_sandstone");

      private static final BlockStoneSlabNew.EnumType[] field_176921_b = new BlockStoneSlabNew.EnumType[values().length];
      private static final BlockStoneSlabNew.EnumType[] ENUM$VALUES = new BlockStoneSlabNew.EnumType[]{RED_SANDSTONE};
      private static final BlockStoneSlabNew.EnumType[] $VALUES = new BlockStoneSlabNew.EnumType[]{RED_SANDSTONE};
      private final String field_176919_d;

      public int func_176915_a() {
         return this.field_176922_c;
      }

      public String getName() {
         return this.field_176919_d;
      }

      public static BlockStoneSlabNew.EnumType func_176916_a(int var0) {
         if (var0 < 0 || var0 >= field_176921_b.length) {
            var0 = 0;
         }

         return field_176921_b[var0];
      }

      public String toString() {
         return this.field_176919_d;
      }

      private EnumType(String var3, int var4, int var5, String var6) {
         this.field_176922_c = var5;
         this.field_176919_d = var6;
      }

      static {
         BlockStoneSlabNew.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockStoneSlabNew.EnumType var3 = var0[var2];
            field_176921_b[var3.func_176915_a()] = var3;
         }

      }

      public String func_176918_c() {
         return this.field_176919_d;
      }
   }
}

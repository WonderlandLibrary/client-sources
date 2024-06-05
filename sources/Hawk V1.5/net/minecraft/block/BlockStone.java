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
import net.minecraft.util.IStringSerializable;

public class BlockStone extends Block {
   private static final String __OBFID = "CL_00000317";
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockStone.EnumType.class);

   public int getMetaFromState(IBlockState var1) {
      return ((BlockStone.EnumType)var1.getValue(VARIANT_PROP)).getMetaFromState();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT_PROP, BlockStone.EnumType.getStateFromMeta(var1));
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP});
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockStone.EnumType)var1.getValue(VARIANT_PROP)).getMetaFromState();
   }

   public BlockStone() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockStone.EnumType.STONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockStone.EnumType[] var4 = BlockStone.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockStone.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.getMetaFromState()));
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return var1.getValue(VARIANT_PROP) == BlockStone.EnumType.STONE ? Item.getItemFromBlock(Blocks.cobblestone) : Item.getItemFromBlock(Blocks.stone);
   }

   public static enum EnumType implements IStringSerializable {
      DIORITE("DIORITE", 3, 3, "diorite");

      private final String name;
      STONE("STONE", 0, 0, "stone");

      private final String field_176654_k;
      private static final BlockStone.EnumType[] $VALUES = new BlockStone.EnumType[]{STONE, GRANITE, GRANITE_SMOOTH, DIORITE, DIORITE_SMOOTH, ANDESITE, ANDESITE_SMOOTH};
      private static final BlockStone.EnumType[] ENUM$VALUES = new BlockStone.EnumType[]{STONE, GRANITE, GRANITE_SMOOTH, DIORITE, DIORITE_SMOOTH, ANDESITE, ANDESITE_SMOOTH};
      DIORITE_SMOOTH("DIORITE_SMOOTH", 4, 4, "smooth_diorite", "dioriteSmooth"),
      GRANITE_SMOOTH("GRANITE_SMOOTH", 2, 2, "smooth_granite", "graniteSmooth");

      private final int meta;
      ANDESITE_SMOOTH("ANDESITE_SMOOTH", 6, 6, "smooth_andesite", "andesiteSmooth"),
      ANDESITE("ANDESITE", 5, 5, "andesite");

      private static final String __OBFID = "CL_00002058";
      GRANITE("GRANITE", 1, 1, "granite");

      private static final BlockStone.EnumType[] BLOCKSTATES = new BlockStone.EnumType[values().length];

      static {
         BlockStone.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockStone.EnumType var3 = var0[var2];
            BLOCKSTATES[var3.getMetaFromState()] = var3;
         }

      }

      public String getName() {
         return this.name;
      }

      public int getMetaFromState() {
         return this.meta;
      }

      public static BlockStone.EnumType getStateFromMeta(int var0) {
         if (var0 < 0 || var0 >= BLOCKSTATES.length) {
            var0 = 0;
         }

         return BLOCKSTATES[var0];
      }

      private EnumType(String var3, int var4, int var5, String var6) {
         this(var3, var4, var5, var6, var6);
      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.meta = var5;
         this.name = var6;
         this.field_176654_k = var7;
      }

      public String func_176644_c() {
         return this.field_176654_k;
      }

      public String toString() {
         return this.name;
      }
   }
}

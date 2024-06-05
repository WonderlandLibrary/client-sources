package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockStoneBrick extends Block {
   private static final String __OBFID = "CL_00000318";
   public static final int CHISELED_META;
   public static final int DEFAULT_META;
   public static final int MOSSY_META;
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockStoneBrick.EnumType.class);
   public static final int CRACKED_META;

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT_PROP, BlockStoneBrick.EnumType.getStateFromMeta(var1));
   }

   static {
      DEFAULT_META = BlockStoneBrick.EnumType.DEFAULT.getMetaFromState();
      MOSSY_META = BlockStoneBrick.EnumType.MOSSY.getMetaFromState();
      CRACKED_META = BlockStoneBrick.EnumType.CRACKED.getMetaFromState();
      CHISELED_META = BlockStoneBrick.EnumType.CHISELED.getMetaFromState();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP});
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockStoneBrick.EnumType[] var4 = BlockStoneBrick.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockStoneBrick.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.getMetaFromState()));
      }

   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockStoneBrick.EnumType)var1.getValue(VARIANT_PROP)).getMetaFromState();
   }

   public BlockStoneBrick() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockStoneBrick.EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockStoneBrick.EnumType)var1.getValue(VARIANT_PROP)).getMetaFromState();
   }

   public static enum EnumType implements IStringSerializable {
      CRACKED("CRACKED", 2, 2, "cracked_stonebrick", "cracked"),
      DEFAULT("DEFAULT", 0, 0, "stonebrick", "default");

      private static final BlockStoneBrick.EnumType[] TYPES_ARRAY = new BlockStoneBrick.EnumType[values().length];
      private final String field_176616_g;
      private final String field_176622_h;
      CHISELED("CHISELED", 3, 3, "chiseled_stonebrick", "chiseled"),
      MOSSY("MOSSY", 1, 1, "mossy_stonebrick", "mossy");

      private static final String __OBFID = "CL_00002057";
      private final int field_176615_f;
      private static final BlockStoneBrick.EnumType[] ENUM$VALUES = new BlockStoneBrick.EnumType[]{DEFAULT, MOSSY, CRACKED, CHISELED};
      private static final BlockStoneBrick.EnumType[] $VALUES = new BlockStoneBrick.EnumType[]{DEFAULT, MOSSY, CRACKED, CHISELED};

      public String toString() {
         return this.field_176616_g;
      }

      public static BlockStoneBrick.EnumType getStateFromMeta(int var0) {
         if (var0 < 0 || var0 >= TYPES_ARRAY.length) {
            var0 = 0;
         }

         return TYPES_ARRAY[var0];
      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176615_f = var5;
         this.field_176616_g = var6;
         this.field_176622_h = var7;
      }

      public String getName() {
         return this.field_176616_g;
      }

      public String getVariantName() {
         return this.field_176622_h;
      }

      public int getMetaFromState() {
         return this.field_176615_f;
      }

      static {
         BlockStoneBrick.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockStoneBrick.EnumType var3 = var0[var2];
            TYPES_ARRAY[var3.getMetaFromState()] = var3;
         }

      }
   }
}

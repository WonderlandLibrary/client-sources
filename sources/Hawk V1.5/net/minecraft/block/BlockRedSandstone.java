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

public class BlockRedSandstone extends Block {
   private static final String __OBFID = "CL_00002072";
   public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockRedSandstone.EnumType.class);

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockRedSandstone.EnumType[] var4 = BlockRedSandstone.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockRedSandstone.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.getMetaFromState()));
      }

   }

   public BlockRedSandstone() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockRedSandstone.EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockRedSandstone.EnumType)var1.getValue(TYPE)).getMetaFromState();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(TYPE, BlockRedSandstone.EnumType.func_176825_a(var1));
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{TYPE});
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockRedSandstone.EnumType)var1.getValue(TYPE)).getMetaFromState();
   }

   public static enum EnumType implements IStringSerializable {
      private final String field_176830_g;
      SMOOTH("SMOOTH", 2, 2, "smooth_red_sandstone", "smooth"),
      CHISELED("CHISELED", 1, 1, "chiseled_red_sandstone", "chiseled");

      private final String field_176829_f;
      private static final BlockRedSandstone.EnumType[] field_176831_d = new BlockRedSandstone.EnumType[values().length];
      DEFAULT("DEFAULT", 0, 0, "red_sandstone", "default");

      private final int field_176832_e;
      private static final BlockRedSandstone.EnumType[] $VALUES = new BlockRedSandstone.EnumType[]{DEFAULT, CHISELED, SMOOTH};
      private static final String __OBFID = "CL_00002071";
      private static final BlockRedSandstone.EnumType[] ENUM$VALUES = new BlockRedSandstone.EnumType[]{DEFAULT, CHISELED, SMOOTH};

      public String toString() {
         return this.field_176829_f;
      }

      public static BlockRedSandstone.EnumType func_176825_a(int var0) {
         if (var0 < 0 || var0 >= field_176831_d.length) {
            var0 = 0;
         }

         return field_176831_d[var0];
      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176832_e = var5;
         this.field_176829_f = var6;
         this.field_176830_g = var7;
      }

      public String getName() {
         return this.field_176829_f;
      }

      public int getMetaFromState() {
         return this.field_176832_e;
      }

      static {
         BlockRedSandstone.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockRedSandstone.EnumType var3 = var0[var2];
            field_176831_d[var3.getMetaFromState()] = var3;
         }

      }

      public String func_176828_c() {
         return this.field_176830_g;
      }
   }
}

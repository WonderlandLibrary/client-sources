package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockSand extends BlockFalling {
   private static final String __OBFID = "CL_00000303";
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockSand.EnumType.class);

   public BlockSand() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockSand.EnumType.SAND));
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT_PROP, BlockSand.EnumType.func_176686_a(var1));
   }

   public MapColor getMapColor(IBlockState var1) {
      return ((BlockSand.EnumType)var1.getValue(VARIANT_PROP)).func_176687_c();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP});
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockSand.EnumType)var1.getValue(VARIANT_PROP)).func_176688_a();
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockSand.EnumType)var1.getValue(VARIANT_PROP)).func_176688_a();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockSand.EnumType[] var4 = BlockSand.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockSand.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176688_a()));
      }

   }

   public static enum EnumType implements IStringSerializable {
      SAND("SAND", 0, 0, "sand", "default", MapColor.sandColor);

      private final String field_176693_e;
      private final String field_176691_g;
      private final int field_176692_d;
      private static final String __OBFID = "CL_00002069";
      private static final BlockSand.EnumType[] field_176695_c = new BlockSand.EnumType[values().length];
      private static final BlockSand.EnumType[] ENUM$VALUES = new BlockSand.EnumType[]{SAND, RED_SAND};
      private static final BlockSand.EnumType[] $VALUES = new BlockSand.EnumType[]{SAND, RED_SAND};
      RED_SAND("RED_SAND", 1, 1, "red_sand", "red", MapColor.dirtColor);

      private final MapColor field_176690_f;

      private EnumType(String var3, int var4, int var5, String var6, String var7, MapColor var8) {
         this.field_176692_d = var5;
         this.field_176693_e = var6;
         this.field_176690_f = var8;
         this.field_176691_g = var7;
      }

      public static BlockSand.EnumType func_176686_a(int var0) {
         if (var0 < 0 || var0 >= field_176695_c.length) {
            var0 = 0;
         }

         return field_176695_c[var0];
      }

      public String toString() {
         return this.field_176693_e;
      }

      public String func_176685_d() {
         return this.field_176691_g;
      }

      public MapColor func_176687_c() {
         return this.field_176690_f;
      }

      static {
         BlockSand.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockSand.EnumType var3 = var0[var2];
            field_176695_c[var3.func_176688_a()] = var3;
         }

      }

      public String getName() {
         return this.field_176693_e;
      }

      public int func_176688_a() {
         return this.field_176692_d;
      }
   }
}

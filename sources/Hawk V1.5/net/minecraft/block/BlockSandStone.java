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

public class BlockSandStone extends Block {
   private static final String __OBFID = "CL_00000304";
   public static final PropertyEnum field_176297_a = PropertyEnum.create("type", BlockSandStone.EnumType.class);

   public int getMetaFromState(IBlockState var1) {
      return ((BlockSandStone.EnumType)var1.getValue(field_176297_a)).func_176675_a();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176297_a, BlockSandStone.EnumType.func_176673_a(var1));
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockSandStone.EnumType)var1.getValue(field_176297_a)).func_176675_a();
   }

   public BlockSandStone() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176297_a, BlockSandStone.EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockSandStone.EnumType[] var4 = BlockSandStone.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockSandStone.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176675_a()));
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176297_a});
   }

   public static enum EnumType implements IStringSerializable {
      private final String field_176678_g;
      private static final BlockSandStone.EnumType[] $VALUES = new BlockSandStone.EnumType[]{DEFAULT, CHISELED, SMOOTH};
      CHISELED("CHISELED", 1, 1, "chiseled_sandstone", "chiseled");

      private static final String __OBFID = "CL_00002068";
      private final int field_176680_e;
      DEFAULT("DEFAULT", 0, 0, "sandstone", "default"),
      SMOOTH("SMOOTH", 2, 2, "smooth_sandstone", "smooth");

      private static final BlockSandStone.EnumType[] field_176679_d = new BlockSandStone.EnumType[values().length];
      private static final BlockSandStone.EnumType[] ENUM$VALUES = new BlockSandStone.EnumType[]{DEFAULT, CHISELED, SMOOTH};
      private final String field_176677_f;

      public String toString() {
         return this.field_176677_f;
      }

      public static BlockSandStone.EnumType func_176673_a(int var0) {
         if (var0 < 0 || var0 >= field_176679_d.length) {
            var0 = 0;
         }

         return field_176679_d[var0];
      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176680_e = var5;
         this.field_176677_f = var6;
         this.field_176678_g = var7;
      }

      public String func_176676_c() {
         return this.field_176678_g;
      }

      public int func_176675_a() {
         return this.field_176680_e;
      }

      public String getName() {
         return this.field_176677_f;
      }

      static {
         BlockSandStone.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockSandStone.EnumType var3 = var0[var2];
            field_176679_d[var3.func_176675_a()] = var3;
         }

      }
   }
}

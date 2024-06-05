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

public class BlockPlanks extends Block {
   private static final String __OBFID = "CL_00002082";
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockPlanks.EnumType.class);

   public int getMetaFromState(IBlockState var1) {
      return ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockPlanks.EnumType[] var4 = BlockPlanks.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockPlanks.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176839_a()));
      }

   }

   public BlockPlanks() {
      super(Material.wood);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockPlanks.EnumType.OAK));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT_PROP, BlockPlanks.EnumType.func_176837_a(var1));
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP});
   }

   public static enum EnumType implements IStringSerializable {
      private final int field_176850_h;
      DARK_OAK("DARK_OAK", 5, 5, "dark_oak", "big_oak");

      private final String field_176851_i;
      private static final BlockPlanks.EnumType[] $VALUES = new BlockPlanks.EnumType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};
      SPRUCE("SPRUCE", 1, 1, "spruce"),
      ACACIA("ACACIA", 4, 4, "acacia");

      private final String field_176848_j;
      BIRCH("BIRCH", 2, 2, "birch"),
      JUNGLE("JUNGLE", 3, 3, "jungle");

      private static final BlockPlanks.EnumType[] ENUM$VALUES = new BlockPlanks.EnumType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};
      private static final BlockPlanks.EnumType[] field_176842_g = new BlockPlanks.EnumType[values().length];
      OAK("OAK", 0, 0, "oak");

      private static final String __OBFID = "CL_00002081";

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176850_h = var5;
         this.field_176851_i = var6;
         this.field_176848_j = var7;
      }

      public String func_176840_c() {
         return this.field_176848_j;
      }

      private EnumType(String var3, int var4, int var5, String var6) {
         this(var3, var4, var5, var6, var6);
      }

      public String toString() {
         return this.field_176851_i;
      }

      public int func_176839_a() {
         return this.field_176850_h;
      }

      static {
         BlockPlanks.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockPlanks.EnumType var3 = var0[var2];
            field_176842_g[var3.func_176839_a()] = var3;
         }

      }

      public static BlockPlanks.EnumType func_176837_a(int var0) {
         if (var0 < 0 || var0 >= field_176842_g.length) {
            var0 = 0;
         }

         return field_176842_g[var0];
      }

      public String getName() {
         return this.field_176851_i;
      }
   }
}

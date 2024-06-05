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

public class BlockPrismarine extends Block {
   public static final PropertyEnum VARIANTS = PropertyEnum.create("variant", BlockPrismarine.EnumType.class);
   private static final String __OBFID = "CL_00002077";
   public static final int ROUGHMETA;
   public static final int DARKMETA;
   public static final int BRICKSMETA;

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANTS, BlockPrismarine.EnumType.func_176810_a(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockPrismarine.EnumType)var1.getValue(VARIANTS)).getMetadata();
   }

   public BlockPrismarine() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANTS, BlockPrismarine.EnumType.ROUGH));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANTS});
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPrismarine.EnumType)var1.getValue(VARIANTS)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, ROUGHMETA));
      var3.add(new ItemStack(var1, 1, BRICKSMETA));
      var3.add(new ItemStack(var1, 1, DARKMETA));
   }

   static {
      ROUGHMETA = BlockPrismarine.EnumType.ROUGH.getMetadata();
      BRICKSMETA = BlockPrismarine.EnumType.BRICKS.getMetadata();
      DARKMETA = BlockPrismarine.EnumType.DARK.getMetadata();
   }

   public static enum EnumType implements IStringSerializable {
      private static final BlockPrismarine.EnumType[] $VALUES = new BlockPrismarine.EnumType[]{ROUGH, BRICKS, DARK};
      private static final BlockPrismarine.EnumType[] ENUM$VALUES = new BlockPrismarine.EnumType[]{ROUGH, BRICKS, DARK};
      DARK("DARK", 2, 2, "dark_prismarine", "dark"),
      BRICKS("BRICKS", 1, 1, "prismarine_bricks", "bricks");

      private static final BlockPrismarine.EnumType[] field_176813_d = new BlockPrismarine.EnumType[values().length];
      private final int meta;
      private final String field_176812_g;
      ROUGH("ROUGH", 0, 0, "prismarine", "rough");

      private final String field_176811_f;
      private static final String __OBFID = "CL_00002076";

      public String toString() {
         return this.field_176811_f;
      }

      public String getName() {
         return this.field_176811_f;
      }

      public static BlockPrismarine.EnumType func_176810_a(int var0) {
         if (var0 < 0 || var0 >= field_176813_d.length) {
            var0 = 0;
         }

         return field_176813_d[var0];
      }

      static {
         BlockPrismarine.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockPrismarine.EnumType var3 = var0[var2];
            field_176813_d[var3.getMetadata()] = var3;
         }

      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.meta = var5;
         this.field_176811_f = var6;
         this.field_176812_g = var7;
      }

      public String func_176809_c() {
         return this.field_176812_g;
      }

      public int getMetadata() {
         return this.meta;
      }
   }
}

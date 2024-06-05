package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockHugeMushroom extends Block {
   public static final PropertyEnum field_176380_a = PropertyEnum.create("variant", BlockHugeMushroom.EnumType.class);
   private static final String __OBFID = "CL_00000258";
   private final Block field_176379_b;

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(this.field_176379_b);
   }

   public int quantityDropped(Random var1) {
      return Math.max(0, var1.nextInt(10) - 7);
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockHugeMushroom.EnumType)var1.getValue(field_176380_a)).func_176896_a();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176380_a});
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176380_a, BlockHugeMushroom.EnumType.func_176895_a(var1));
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(this.field_176379_b);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState();
   }

   public BlockHugeMushroom(Material var1, Block var2) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176380_a, BlockHugeMushroom.EnumType.ALL_OUTSIDE));
      this.field_176379_b = var2;
   }

   public static enum EnumType implements IStringSerializable {
      SOUTH("SOUTH", 7, 8, "south"),
      NORTH_WEST("NORTH_WEST", 0, 1, "north_west"),
      ALL_OUTSIDE("ALL_OUTSIDE", 11, 14, "all_outside"),
      ALL_STEM("ALL_STEM", 12, 15, "all_stem"),
      STEM("STEM", 9, 10, "stem"),
      SOUTH_WEST("SOUTH_WEST", 6, 7, "south_west");

      private final int field_176906_o;
      private static final BlockHugeMushroom.EnumType[] $VALUES = new BlockHugeMushroom.EnumType[]{NORTH_WEST, NORTH, NORTH_EAST, WEST, CENTER, EAST, SOUTH_WEST, SOUTH, SOUTH_EAST, STEM, ALL_INSIDE, ALL_OUTSIDE, ALL_STEM};
      private final String field_176914_p;
      private static final BlockHugeMushroom.EnumType[] ENUM$VALUES = new BlockHugeMushroom.EnumType[]{NORTH_WEST, NORTH, NORTH_EAST, WEST, CENTER, EAST, SOUTH_WEST, SOUTH, SOUTH_EAST, STEM, ALL_INSIDE, ALL_OUTSIDE, ALL_STEM};
      private static final BlockHugeMushroom.EnumType[] field_176905_n = new BlockHugeMushroom.EnumType[16];
      CENTER("CENTER", 4, 5, "center"),
      NORTH_EAST("NORTH_EAST", 2, 3, "north_east"),
      ALL_INSIDE("ALL_INSIDE", 10, 0, "all_inside"),
      SOUTH_EAST("SOUTH_EAST", 8, 9, "south_east"),
      NORTH("NORTH", 1, 2, "north"),
      EAST("EAST", 5, 6, "east"),
      WEST("WEST", 3, 4, "west");

      private static final String __OBFID = "CL_00002105";

      private EnumType(String var3, int var4, int var5, String var6) {
         this.field_176906_o = var5;
         this.field_176914_p = var6;
      }

      static {
         BlockHugeMushroom.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockHugeMushroom.EnumType var3 = var0[var2];
            field_176905_n[var3.func_176896_a()] = var3;
         }

      }

      public String getName() {
         return this.field_176914_p;
      }

      public int func_176896_a() {
         return this.field_176906_o;
      }

      public static BlockHugeMushroom.EnumType func_176895_a(int var0) {
         if (var0 < 0 || var0 >= field_176905_n.length) {
            var0 = 0;
         }

         BlockHugeMushroom.EnumType var1 = field_176905_n[var0];
         return var1 == null ? field_176905_n[0] : var1;
      }

      public String toString() {
         return this.field_176914_p;
      }
   }
}

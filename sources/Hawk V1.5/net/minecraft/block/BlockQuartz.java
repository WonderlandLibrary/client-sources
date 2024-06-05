package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockQuartz extends Block {
   private static final String __OBFID = "CL_00000292";
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockQuartz.EnumType.class);

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, BlockQuartz.EnumType.DEFAULT.getMetaFromState()));
      var3.add(new ItemStack(var1, 1, BlockQuartz.EnumType.CHISELED.getMetaFromState()));
      var3.add(new ItemStack(var1, 1, BlockQuartz.EnumType.LINES_Y.getMetaFromState()));
   }

   public BlockQuartz() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockQuartz.EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState var1) {
      BlockQuartz.EnumType var2 = (BlockQuartz.EnumType)var1.getValue(VARIANT_PROP);
      return var2 != BlockQuartz.EnumType.LINES_X && var2 != BlockQuartz.EnumType.LINES_Z ? var2.getMetaFromState() : BlockQuartz.EnumType.LINES_Y.getMetaFromState();
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if (var7 == BlockQuartz.EnumType.LINES_Y.getMetaFromState()) {
         switch(var3.getAxis()) {
         case Z:
            return this.getDefaultState().withProperty(VARIANT_PROP, BlockQuartz.EnumType.LINES_Z);
         case X:
            return this.getDefaultState().withProperty(VARIANT_PROP, BlockQuartz.EnumType.LINES_X);
         case Y:
         default:
            return this.getDefaultState().withProperty(VARIANT_PROP, BlockQuartz.EnumType.LINES_Y);
         }
      } else {
         return var7 == BlockQuartz.EnumType.CHISELED.getMetaFromState() ? this.getDefaultState().withProperty(VARIANT_PROP, BlockQuartz.EnumType.CHISELED) : this.getDefaultState().withProperty(VARIANT_PROP, BlockQuartz.EnumType.DEFAULT);
      }
   }

   public MapColor getMapColor(IBlockState var1) {
      return MapColor.quartzColor;
   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      BlockQuartz.EnumType var2 = (BlockQuartz.EnumType)var1.getValue(VARIANT_PROP);
      return var2 != BlockQuartz.EnumType.LINES_X && var2 != BlockQuartz.EnumType.LINES_Z ? super.createStackedBlock(var1) : new ItemStack(Item.getItemFromBlock(this), 1, BlockQuartz.EnumType.LINES_Y.getMetaFromState());
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT_PROP, BlockQuartz.EnumType.func_176794_a(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockQuartz.EnumType)var1.getValue(VARIANT_PROP)).getMetaFromState();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP});
   }

   static final class SwitchAxis {
      static final int[] field_180101_a = new int[EnumFacing.Axis.values().length];
      private static final String __OBFID = "CL_00002075";

      static {
         try {
            field_180101_a[EnumFacing.Axis.Z.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180101_a[EnumFacing.Axis.X.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180101_a[EnumFacing.Axis.Y.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static enum EnumType implements IStringSerializable {
      private static final BlockQuartz.EnumType[] $VALUES = new BlockQuartz.EnumType[]{DEFAULT, CHISELED, LINES_Y, LINES_X, LINES_Z};
      private static final BlockQuartz.EnumType[] ENUM$VALUES = new BlockQuartz.EnumType[]{DEFAULT, CHISELED, LINES_Y, LINES_X, LINES_Z};
      private static final String __OBFID = "CL_00002074";
      LINES_Y("LINES_Y", 2, 2, "lines_y", "lines"),
      LINES_Z("LINES_Z", 4, 4, "lines_z", "lines");

      private final String field_176806_i;
      private final String field_176805_h;
      DEFAULT("DEFAULT", 0, 0, "default", "default"),
      LINES_X("LINES_X", 3, 3, "lines_x", "lines");

      private static final BlockQuartz.EnumType[] TYPES_ARRAY = new BlockQuartz.EnumType[values().length];
      private final int field_176798_g;
      CHISELED("CHISELED", 1, 1, "chiseled", "chiseled");

      public static BlockQuartz.EnumType func_176794_a(int var0) {
         if (var0 < 0 || var0 >= TYPES_ARRAY.length) {
            var0 = 0;
         }

         return TYPES_ARRAY[var0];
      }

      public int getMetaFromState() {
         return this.field_176798_g;
      }

      public String getName() {
         return this.field_176805_h;
      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176798_g = var5;
         this.field_176805_h = var6;
         this.field_176806_i = var7;
      }

      public String toString() {
         return this.field_176806_i;
      }

      static {
         BlockQuartz.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockQuartz.EnumType var3 = var0[var2];
            TYPES_ARRAY[var3.getMetaFromState()] = var3;
         }

      }
   }
}

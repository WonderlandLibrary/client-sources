package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockOldLog extends BlockLog {
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate() {
      private static final String __OBFID = "CL_00002084";

      public boolean func_180200_a(BlockPlanks.EnumType var1) {
         return var1.func_176839_a() < 4;
      }

      public boolean apply(Object var1) {
         return this.func_180200_a((BlockPlanks.EnumType)var1);
      }
   });
   private static final String __OBFID = "CL_00000281";

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState().withProperty(VARIANT_PROP, BlockPlanks.EnumType.func_176837_a((var1 & 3) % 4));
      switch(var1 & 12) {
      case 0:
         var2 = var2.withProperty(AXIS_PROP, BlockLog.EnumAxis.Y);
         break;
      case 4:
         var2 = var2.withProperty(AXIS_PROP, BlockLog.EnumAxis.X);
         break;
      case 8:
         var2 = var2.withProperty(AXIS_PROP, BlockLog.EnumAxis.Z);
         break;
      default:
         var2 = var2.withProperty(AXIS_PROP, BlockLog.EnumAxis.NONE);
      }

      return var2;
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a();
   }

   public BlockOldLog() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockPlanks.EnumType.OAK).withProperty(AXIS_PROP, BlockLog.EnumAxis.Y));
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.OAK.func_176839_a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()));
   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a());
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a();
      switch((BlockLog.EnumAxis)var1.getValue(AXIS_PROP)) {
      case X:
         var3 |= 4;
         break;
      case Z:
         var3 |= 8;
         break;
      case NONE:
         var3 |= 12;
      }

      return var3;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP, AXIS_PROP});
   }

   static final class SwitchEnumAxis {
      static final int[] field_180203_a = new int[BlockLog.EnumAxis.values().length];
      private static final String __OBFID = "CL_00002083";

      static {
         try {
            field_180203_a[BlockLog.EnumAxis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180203_a[BlockLog.EnumAxis.Z.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180203_a[BlockLog.EnumAxis.NONE.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}

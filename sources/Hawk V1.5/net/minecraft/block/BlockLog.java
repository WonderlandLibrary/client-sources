package net.minecraft.block;

import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public abstract class BlockLog extends BlockRotatedPillar {
   public static final PropertyEnum AXIS_PROP = PropertyEnum.create("axis", BlockLog.EnumAxis.class);
   private static final String __OBFID = "CL_00000266";

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      byte var4 = 4;
      int var5 = var4 + 1;
      if (var1.isAreaLoaded(var2.add(-var5, -var5, -var5), var2.add(var5, var5, var5))) {
         Iterator var6 = BlockPos.getAllInBox(var2.add(-var4, -var4, -var4), var2.add(var4, var4, var4)).iterator();

         while(var6.hasNext()) {
            BlockPos var7 = (BlockPos)var6.next();
            IBlockState var8 = var1.getBlockState(var7);
            if (var8.getBlock().getMaterial() == Material.leaves && !(Boolean)var8.getValue(BlockLeaves.field_176236_b)) {
               var1.setBlockState(var7, var8.withProperty(BlockLeaves.field_176236_b, true), 4);
            }
         }
      }

   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return super.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8).withProperty(AXIS_PROP, BlockLog.EnumAxis.func_176870_a(var3.getAxis()));
   }

   public BlockLog() {
      super(Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
      this.setHardness(2.0F);
      this.setStepSound(soundTypeWood);
   }

   public static enum EnumAxis implements IStringSerializable {
      private static final String __OBFID = "CL_00002100";
      NONE("NONE", 3, "none"),
      X("X", 0, "x");

      private static final BlockLog.EnumAxis[] $VALUES = new BlockLog.EnumAxis[]{X, Y, Z, NONE};
      private static final BlockLog.EnumAxis[] ENUM$VALUES = new BlockLog.EnumAxis[]{X, Y, Z, NONE};
      private final String field_176874_e;
      Y("Y", 1, "y"),
      Z("Z", 2, "z");

      public String toString() {
         return this.field_176874_e;
      }

      public static BlockLog.EnumAxis func_176870_a(EnumFacing.Axis var0) {
         switch(var0) {
         case X:
            return X;
         case Y:
            return Y;
         case Z:
            return Z;
         default:
            return NONE;
         }
      }

      public String getName() {
         return this.field_176874_e;
      }

      private EnumAxis(String var3, int var4, String var5) {
         this.field_176874_e = var5;
      }
   }

   static final class SwitchAxis {
      static final int[] field_180167_a = new int[EnumFacing.Axis.values().length];
      private static final String __OBFID = "CL_00002101";

      static {
         try {
            field_180167_a[EnumFacing.Axis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180167_a[EnumFacing.Axis.Y.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180167_a[EnumFacing.Axis.Z.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}

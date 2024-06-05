package net.minecraft.block;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPressurePlate extends BlockBasePressurePlate {
   private final BlockPressurePlate.Sensitivity sensitivity;
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   private static final String __OBFID = "CL_00000289";

   protected BlockPressurePlate(Material var1, BlockPressurePlate.Sensitivity var2) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, false));
      this.sensitivity = var2;
   }

   protected int computeRedstoneStrength(World var1, BlockPos var2) {
      AxisAlignedBB var3 = this.getSensitiveAABB(var2);
      List var4;
      switch(this.sensitivity) {
      case EVERYTHING:
         var4 = var1.getEntitiesWithinAABBExcludingEntity((Entity)null, var3);
         break;
      case MOBS:
         var4 = var1.getEntitiesWithinAABB(EntityLivingBase.class, var3);
         break;
      default:
         return 0;
      }

      if (!var4.isEmpty()) {
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            Entity var6 = (Entity)var5.next();
            if (!var6.doesEntityNotTriggerPressurePlate()) {
               return 15;
            }
         }
      }

      return 0;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(POWERED, var1 == 1);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{POWERED});
   }

   public int getMetaFromState(IBlockState var1) {
      return (Boolean)var1.getValue(POWERED) ? 1 : 0;
   }

   protected IBlockState setRedstoneStrength(IBlockState var1, int var2) {
      return var1.withProperty(POWERED, var2 > 0);
   }

   protected int getRedstoneStrength(IBlockState var1) {
      return (Boolean)var1.getValue(POWERED) ? 15 : 0;
   }

   public static enum Sensitivity {
      MOBS("MOBS", 1),
      EVERYTHING("EVERYTHING", 0);

      private static final BlockPressurePlate.Sensitivity[] ENUM$VALUES = new BlockPressurePlate.Sensitivity[]{EVERYTHING, MOBS};
      private static final String __OBFID = "CL_00000290";
      private static final BlockPressurePlate.Sensitivity[] $VALUES = new BlockPressurePlate.Sensitivity[]{EVERYTHING, MOBS};

      private Sensitivity(String var3, int var4) {
      }
   }

   static final class SwitchSensitivity {
      private static final String __OBFID = "CL_00002078";
      static final int[] SENSITIVITY_ARRAY = new int[BlockPressurePlate.Sensitivity.values().length];

      static {
         try {
            SENSITIVITY_ARRAY[BlockPressurePlate.Sensitivity.EVERYTHING.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            SENSITIVITY_ARRAY[BlockPressurePlate.Sensitivity.MOBS.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}

package net.minecraft.block;

import java.util.List;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockPressurePlate$1;
import net.minecraft.block.BlockPressurePlate$Sensitivity;
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
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   private final BlockPressurePlate$Sensitivity sensitivity;

   protected BlockPressurePlate(Material var1, BlockPressurePlate$Sensitivity var2) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.FALSE));
      this.sensitivity = var2;
   }

   protected int getRedstoneStrength(IBlockState var1) {
      return ((Boolean)var1.getValue(POWERED)).booleanValue()?15:0;
   }

   protected IBlockState setRedstoneStrength(IBlockState var1, int var2) {
      return var1.withProperty(POWERED, Boolean.valueOf(true));
   }

   protected int computeRedstoneStrength(World var1, BlockPos var2) {
      AxisAlignedBB var3 = this.getSensitiveAABB(var2);
      List var4;
      switch(BlockPressurePlate$1.$SwitchMap$net$minecraft$block$BlockPressurePlate$Sensitivity[this.sensitivity.ordinal()]) {
      case 1:
         var4 = var1.getEntitiesWithinAABBExcludingEntity((Entity)null, var3);
         break;
      case 2:
         var4 = var1.getEntitiesWithinAABB(EntityLivingBase.class, var3);
         break;
      default:
         return 0;
      }

      if(!var4.isEmpty()) {
         for(Entity var6 : var4) {
            if(!var6.doesEntityNotTriggerPressurePlate()) {
               return 15;
            }
         }
      }

      return 0;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(POWERED, Boolean.valueOf(var1 == 1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Boolean)var1.getValue(POWERED)).booleanValue()?1:0;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{POWERED});
   }
}

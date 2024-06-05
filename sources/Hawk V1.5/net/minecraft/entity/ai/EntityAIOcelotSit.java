package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIOcelotSit extends EntityAIMoveToBlock {
   private static final String __OBFID = "CL_00001601";
   private final EntityOcelot field_151493_a;

   public boolean continueExecuting() {
      return super.continueExecuting();
   }

   public EntityAIOcelotSit(EntityOcelot var1, double var2) {
      super(var1, var2, 8);
      this.field_151493_a = var1;
   }

   public void startExecuting() {
      super.startExecuting();
      this.field_151493_a.getAISit().setSitting(false);
   }

   public boolean shouldExecute() {
      return this.field_151493_a.isTamed() && !this.field_151493_a.isSitting() && super.shouldExecute();
   }

   protected boolean func_179488_a(World var1, BlockPos var2) {
      if (!var1.isAirBlock(var2.offsetUp())) {
         return false;
      } else {
         IBlockState var3 = var1.getBlockState(var2);
         Block var4 = var3.getBlock();
         if (var4 == Blocks.chest) {
            TileEntity var5 = var1.getTileEntity(var2);
            if (var5 instanceof TileEntityChest && ((TileEntityChest)var5).numPlayersUsing < 1) {
               return true;
            }
         } else {
            if (var4 == Blocks.lit_furnace) {
               return true;
            }

            if (var4 == Blocks.bed && var3.getValue(BlockBed.PART_PROP) != BlockBed.EnumPartType.HEAD) {
               return true;
            }
         }

         return false;
      }
   }

   public void resetTask() {
      super.resetTask();
      this.field_151493_a.setSitting(false);
   }

   public void updateTask() {
      super.updateTask();
      this.field_151493_a.getAISit().setSitting(false);
      if (!this.func_179487_f()) {
         this.field_151493_a.setSitting(false);
      } else if (!this.field_151493_a.isSitting()) {
         this.field_151493_a.setSitting(true);
      }

   }
}

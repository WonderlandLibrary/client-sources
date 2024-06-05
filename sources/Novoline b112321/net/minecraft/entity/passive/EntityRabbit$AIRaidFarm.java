package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class EntityRabbit$AIRaidFarm extends EntityAIMoveToBlock {
   private final EntityRabbit field_179500_c;
   private boolean field_179498_d;
   private boolean field_179499_e = false;

   public EntityRabbit$AIRaidFarm(EntityRabbit var1) {
      super(var1, 0.699999988079071D, 16);
      this.field_179500_c = var1;
   }

   public boolean shouldExecute() {
      if(this.runDelay <= 0) {
         if(!this.field_179500_c.worldObj.getGameRules().getBoolean("mobGriefing")) {
            return false;
         }

         this.field_179499_e = false;
         this.field_179498_d = EntityRabbit.access$000(this.field_179500_c);
      }

      return super.shouldExecute();
   }

   public boolean continueExecuting() {
      return this.field_179499_e && super.continueExecuting();
   }

   public void startExecuting() {
      super.startExecuting();
   }

   public void resetTask() {
      super.resetTask();
   }

   public void updateTask() {
      super.updateTask();
      this.field_179500_c.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.field_179500_c.getVerticalFaceSpeed());
      if(this.getIsAboveDestination()) {
         World var1 = this.field_179500_c.worldObj;
         BlockPos var2 = this.destinationBlock.up();
         IBlockState var3 = var1.getBlockState(var2);
         Block var4 = var3.getBlock();
         if(this.field_179499_e && var4 instanceof BlockCarrot && ((Integer)var3.getValue(BlockCarrot.P)).intValue() == 7) {
            var1.setBlockState(var2, Blocks.air.getDefaultState(), 2);
            var1.destroyBlock(var2, true);
            this.field_179500_c.createEatingParticles();
         }

         this.field_179499_e = false;
         this.runDelay = 10;
      }

   }

   protected boolean shouldMoveTo(World var1, BlockPos var2) {
      Block var3 = var1.getBlockState(var2).getBlock();
      if(var3 == Blocks.farmland) {
         var2 = var2.up();
         IBlockState var4 = var1.getBlockState(var2);
         var3 = var4.getBlock();
         if(var3 instanceof BlockCarrot && ((Integer)var4.getValue(BlockCarrot.P)).intValue() == 7 && this.field_179498_d && !this.field_179499_e) {
            this.field_179499_e = true;
            return true;
         }
      }

      return false;
   }
}

package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;

class EntityGuardian$AIGuardianAttack extends EntityAIBase {
   private EntityGuardian theEntity;
   private int tickCounter;

   public EntityGuardian$AIGuardianAttack(EntityGuardian var1) {
      this.theEntity = var1;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.theEntity.getAttackTarget();
      return var1.isEntityAlive();
   }

   public boolean continueExecuting() {
      return super.continueExecuting() && (this.theEntity.isElder() || this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget()) > 9.0D);
   }

   public void startExecuting() {
      this.tickCounter = -10;
      this.theEntity.getNavigator().clearPathEntity();
      this.theEntity.getLookHelper().setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0F, 90.0F);
      this.theEntity.isAirBorne = true;
   }

   public void resetTask() {
      EntityGuardian.access$000(this.theEntity, 0);
      this.theEntity.setAttackTarget((EntityLivingBase)null);
      EntityGuardian.access$100(this.theEntity).makeUpdate();
   }

   public void updateTask() {
      EntityLivingBase var1 = this.theEntity.getAttackTarget();
      this.theEntity.getNavigator().clearPathEntity();
      this.theEntity.getLookHelper().setLookPositionWithEntity(var1, 90.0F, 90.0F);
      if(!this.theEntity.canEntityBeSeen(var1)) {
         this.theEntity.setAttackTarget((EntityLivingBase)null);
      } else {
         ++this.tickCounter;
         if(this.tickCounter == 0) {
            EntityGuardian.access$000(this.theEntity, this.theEntity.getAttackTarget().getEntityID());
            this.theEntity.worldObj.setEntityState(this.theEntity, (byte)21);
         } else if(this.tickCounter >= this.theEntity.func_175464_ck()) {
            float var2 = 1.0F;
            if(this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
               var2 += 2.0F;
            }

            if(this.theEntity.isElder()) {
               var2 += 2.0F;
            }

            var1.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.theEntity, this.theEntity), var2);
            var1.attackEntityFrom(DamageSource.causeMobDamage(this.theEntity), (float)this.theEntity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
            this.theEntity.setAttackTarget((EntityLivingBase)null);
         } else if(this.tickCounter >= 60 && this.tickCounter % 20 == 0) {
            ;
         }

         super.updateTask();
      }

   }
}

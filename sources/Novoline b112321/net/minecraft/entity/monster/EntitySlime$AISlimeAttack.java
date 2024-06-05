package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySlime$SlimeMoveHelper;
import net.minecraft.entity.player.EntityPlayer;

class EntitySlime$AISlimeAttack extends EntityAIBase {
   private EntitySlime slime;
   private int field_179465_b;

   public EntitySlime$AISlimeAttack(EntitySlime var1) {
      this.slime = var1;
      this.setMutexBits(2);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.slime.getAttackTarget();
      return var1.isEntityAlive() && (!(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).abilities.isDisabledDamage());
   }

   public void startExecuting() {
      this.field_179465_b = 300;
      super.startExecuting();
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.slime.getAttackTarget();
      return var1.isEntityAlive() && (!(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).abilities.isDisabledDamage()) && --this.field_179465_b > 0;
   }

   public void updateTask() {
      this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
      ((EntitySlime$SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.slime.rotationYaw, this.slime.canDamagePlayer());
   }
}

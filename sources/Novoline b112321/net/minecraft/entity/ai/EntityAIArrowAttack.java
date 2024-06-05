package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttack extends EntityAIBase {
   private final EntityLiving entityHost;
   private final IRangedAttackMob d;
   private EntityLivingBase g;
   private int rangedAttackTime;
   private double entityMoveSpeed;
   private int field_75318_f;
   private int field_96561_g;
   private int maxRangedAttackTime;
   private float field_96562_i;
   private float maxAttackDistance;

   public EntityAIArrowAttack(IRangedAttackMob var1, double var2, int var4, float var5) {
      this(var1, var2, var4, var4, var5);
   }

   public EntityAIArrowAttack(IRangedAttackMob var1, double var2, int var4, int var5, float var6) {
      this.rangedAttackTime = -1;
      if(!(var1 instanceof EntityLivingBase)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.d = var1;
         this.entityHost = (EntityLiving)var1;
         this.entityMoveSpeed = var2;
         this.field_96561_g = var4;
         this.maxRangedAttackTime = var5;
         this.field_96562_i = var6;
         this.maxAttackDistance = var6 * var6;
         this.setMutexBits(3);
      }
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.entityHost.getAttackTarget();
      return false;
   }

   public boolean continueExecuting() {
      return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
   }

   public void resetTask() {
      this.g = null;
      this.field_75318_f = 0;
      this.rangedAttackTime = -1;
   }

   public void updateTask() {
      double var1 = this.entityHost.getDistanceSq(this.g.posX, this.g.getEntityBoundingBox().minY, this.g.posZ);
      boolean var3 = this.entityHost.getEntitySenses().canSee(this.g);
      ++this.field_75318_f;
      if(var1 <= (double)this.maxAttackDistance && this.field_75318_f >= 20) {
         this.entityHost.getNavigator().clearPathEntity();
      } else {
         this.entityHost.getNavigator().tryMoveToEntityLiving(this.g, this.entityMoveSpeed);
      }

      this.entityHost.getLookHelper().setLookPositionWithEntity(this.g, 30.0F, 30.0F);
      if(--this.rangedAttackTime == 0) {
         if(var1 <= (double)this.maxAttackDistance) {
            ;
         }

      } else {
         if(this.rangedAttackTime < 0) {
            float var4 = MathHelper.sqrt_double(var1) / this.field_96562_i;
            this.rangedAttackTime = MathHelper.floor_float(var4 * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
         }

      }
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}

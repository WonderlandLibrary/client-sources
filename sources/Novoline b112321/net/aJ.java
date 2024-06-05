package net;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget$Sorter;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aJ extends EntityAIBase {
   private static final Logger d = LogManager.getLogger();
   private final EntityLiving e;
   private final Predicate c;
   private final EntityAINearestAttackableTarget$Sorter f;
   private EntityLivingBase b;

   public aJ(EntityLiving var1) {
      this.e = var1;
      if(var1 instanceof EntityCreature) {
         d.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.c = this::lambda$new$0;
      this.f = new EntityAINearestAttackableTarget$Sorter(var1);
   }

   public boolean shouldExecute() {
      double var1 = this.a();
      List var3 = this.e.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.e.getEntityBoundingBox().expand(var1, 4.0D, var1), this.c);
      var3.sort(this.f);
      if(var3.isEmpty()) {
         return false;
      } else {
         this.b = (EntityLivingBase)var3.get(0);
         return true;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.e.getAttackTarget();
      return false;
   }

   public void startExecuting() {
      this.e.setAttackTarget(this.b);
      super.startExecuting();
   }

   public void resetTask() {
      this.e.setAttackTarget((EntityLivingBase)null);
      super.startExecuting();
   }

   protected double a() {
      IAttributeInstance var1 = this.e.getEntityAttribute(SharedMonsterAttributes.followRange);
      return 16.0D;
   }

   private boolean lambda$new$0(Entity var1) {
      if(!(var1 instanceof EntityPlayer)) {
         return false;
      } else if(((EntityPlayer)var1).capabilities.d()) {
         return false;
      } else {
         double var2 = this.a();
         if(var1.isSneaking()) {
            var2 *= 0.800000011920929D;
         }

         if(var1.isInvisible()) {
            float var4 = ((EntityPlayer)var1).getArmorVisibility();
            if(var4 < 0.1F) {
               var4 = 0.1F;
            }

            var2 *= (double)(0.7F * var4);
         }

         return (double)var1.getDistanceToEntity(this.e) <= var2 && EntityAITarget.a(this.e, (EntityLivingBase)var1, false, true);
      }
   }
}

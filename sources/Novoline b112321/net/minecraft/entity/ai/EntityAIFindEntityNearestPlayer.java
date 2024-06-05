package net.minecraft.entity.ai;

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

public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private final EntityLiving field_179434_b;
   private final Predicate field_179435_c;
   private final EntityAINearestAttackableTarget$Sorter field_179432_d;
   private EntityLivingBase field_179433_e;

   public EntityAIFindEntityNearestPlayer(EntityLiving var1) {
      this.field_179434_b = var1;
      if(var1 instanceof EntityCreature) {
         LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.field_179435_c = this::lambda$new$0;
      this.field_179432_d = new EntityAINearestAttackableTarget$Sorter(var1);
   }

   public boolean shouldExecute() {
      double var1 = this.func_179431_f();
      List var3 = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.field_179434_b.getEntityBoundingBox().expand(var1, 4.0D, var1), this.field_179435_c);
      var3.sort(this.field_179432_d);
      if(var3.isEmpty()) {
         return false;
      } else {
         this.field_179433_e = (EntityLivingBase)var3.get(0);
         return true;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.field_179434_b.getAttackTarget();
      return false;
   }

   public void startExecuting() {
      this.field_179434_b.setAttackTarget(this.field_179433_e);
      super.startExecuting();
   }

   public void resetTask() {
      this.field_179434_b.setAttackTarget((EntityLivingBase)null);
      super.startExecuting();
   }

   protected double func_179431_f() {
      IAttributeInstance var1 = this.field_179434_b.getEntityAttribute(SharedMonsterAttributes.followRange);
      return 16.0D;
   }

   private boolean lambda$new$0(Entity var1) {
      if(!(var1 instanceof EntityPlayer)) {
         return false;
      } else if(((EntityPlayer)var1).abilities.isDisabledDamage()) {
         return false;
      } else {
         double var2 = this.func_179431_f();
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

         return (double)var1.getDistanceToEntity(this.field_179434_b) <= var2 && EntityAITarget.a(this.field_179434_b, (EntityLivingBase)var1, false, true);
      }
   }
}

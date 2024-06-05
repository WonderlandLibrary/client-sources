package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget$Sorter;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearest extends EntityAIBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private final EntityLiving field_179442_b;
   private final Predicate field_179443_c;
   private final EntityAINearestAttackableTarget$Sorter field_179440_d;
   private EntityLivingBase field_179441_e;
   private final Class field_179439_f;

   public EntityAIFindEntityNearest(EntityLiving var1, Class var2) {
      this.field_179442_b = var1;
      this.field_179439_f = var2;
      if(var1 instanceof EntityCreature) {
         LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.field_179443_c = this::lambda$new$0;
      this.field_179440_d = new EntityAINearestAttackableTarget$Sorter(var1);
   }

   public boolean shouldExecute() {
      double var1 = this.func_179438_f();
      List var3 = this.field_179442_b.worldObj.getEntitiesWithinAABB(this.field_179439_f, this.field_179442_b.getEntityBoundingBox().expand(var1, 4.0D, var1), this.field_179443_c);
      var3.sort(this.field_179440_d);
      if(var3.isEmpty()) {
         return false;
      } else {
         this.field_179441_e = (EntityLivingBase)var3.get(0);
         return true;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.field_179442_b.getAttackTarget();
      return false;
   }

   public void startExecuting() {
      this.field_179442_b.setAttackTarget(this.field_179441_e);
      super.startExecuting();
   }

   public void resetTask() {
      this.field_179442_b.setAttackTarget((EntityLivingBase)null);
      super.startExecuting();
   }

   protected double func_179438_f() {
      IAttributeInstance var1 = this.field_179442_b.getEntityAttribute(SharedMonsterAttributes.followRange);
      return 16.0D;
   }

   private boolean lambda$new$0(EntityLivingBase var1) {
      double var2 = this.func_179438_f();
      if(var1.isSneaking()) {
         var2 *= 0.800000011920929D;
      }

      return !var1.isInvisible() && (double)var1.getDistanceToEntity(this.field_179442_b) <= var2 && EntityAITarget.a(this.field_179442_b, var1, false, true);
   }
}

package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget$1;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget$Sorter;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.EntitySelectors;

public class EntityAINearestAttackableTarget extends EntityAITarget {
   protected final Class targetClass;
   private final int targetChance;
   protected final EntityAINearestAttackableTarget$Sorter theNearestAttackableTargetSorter;
   protected Predicate targetEntitySelector;
   protected EntityLivingBase targetEntity;

   public EntityAINearestAttackableTarget(EntityCreature var1, Class var2, boolean var3) {
      this(var1, var2, var3, false);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class var2, boolean var3, boolean var4) {
      this(var1, var2, 10, var3, var4, (Predicate)null);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class var2, int var3, boolean var4, boolean var5, Predicate var6) {
      super(var1, var4, var5);
      this.targetClass = var2;
      this.targetChance = var3;
      this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget$Sorter(var1);
      this.setMutexBits(1);
      this.targetEntitySelector = new EntityAINearestAttackableTarget$1(this, var6);
   }

   public boolean shouldExecute() {
      if(this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
         return false;
      } else {
         double var1 = this.getTargetDistance();
         List var3 = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(var1, 4.0D, var1), Predicates.and(this.targetEntitySelector, EntitySelectors.NOT_SPECTATING));
         Collections.sort(var3, this.theNearestAttackableTargetSorter);
         if(var3.isEmpty()) {
            return false;
         } else {
            this.targetEntity = (EntityLivingBase)var3.get(0);
            return true;
         }
      }
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.targetEntity);
      super.startExecuting();
   }
}

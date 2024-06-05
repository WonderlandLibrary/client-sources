package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity$1;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.Vec3;

public class EntityAIAvoidEntity extends EntityAIBase {
   private final Predicate canBeSeenSelector;
   protected EntityCreature theEntity;
   private double farSpeed;
   private double nearSpeed;
   protected Entity closestLivingEntity;
   private float avoidDistance;
   private PathEntity d;
   private PathNavigate entityPathNavigate;
   private Class field_181064_i;
   private Predicate avoidTargetSelector;

   public EntityAIAvoidEntity(EntityCreature var1, Class var2, float var3, double var4, double var6) {
      this(var1, var2, Predicates.alwaysTrue(), var3, var4, var6);
   }

   public EntityAIAvoidEntity(EntityCreature var1, Class var2, Predicate var3, float var4, double var5, double var7) {
      this.canBeSeenSelector = new EntityAIAvoidEntity$1(this);
      this.theEntity = var1;
      this.field_181064_i = var2;
      this.avoidTargetSelector = var3;
      this.avoidDistance = var4;
      this.farSpeed = var5;
      this.nearSpeed = var7;
      this.entityPathNavigate = var1.getNavigator();
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      List var1 = this.theEntity.worldObj.getEntitiesWithinAABB(this.field_181064_i, this.theEntity.getEntityBoundingBox().expand((double)this.avoidDistance, 3.0D, (double)this.avoidDistance), Predicates.and(new Predicate[]{EntitySelectors.NOT_SPECTATING, this.canBeSeenSelector, this.avoidTargetSelector}));
      if(var1.isEmpty()) {
         return false;
      } else {
         this.closestLivingEntity = (Entity)var1.get(0);
         Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
         return false;
      }
   }

   public boolean continueExecuting() {
      return !this.entityPathNavigate.noPath();
   }

   public void startExecuting() {
      this.entityPathNavigate.a(this.d, this.farSpeed);
   }

   public void resetTask() {
      this.closestLivingEntity = null;
   }

   public void updateTask() {
      if(this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D) {
         this.theEntity.getNavigator().setSpeed(this.nearSpeed);
      } else {
         this.theEntity.getNavigator().setSpeed(this.farSpeed);
      }

   }
}

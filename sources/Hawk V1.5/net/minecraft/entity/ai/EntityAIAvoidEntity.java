package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIAvoidEntity extends EntityAIBase {
   private float field_179508_f;
   public final Predicate field_179509_a = new Predicate(this) {
      private static final String __OBFID = "CL_00001575";
      final EntityAIAvoidEntity this$0;

      public boolean func_180419_a(Entity var1) {
         return var1.isEntityAlive() && this.this$0.theEntity.getEntitySenses().canSee(var1);
      }

      {
         this.this$0 = var1;
      }

      public boolean apply(Object var1) {
         return this.func_180419_a((Entity)var1);
      }
   };
   private Predicate field_179510_i;
   private double farSpeed;
   private static final String __OBFID = "CL_00001574";
   protected EntityCreature theEntity;
   protected Entity closestLivingEntity;
   private PathEntity entityPathEntity;
   private PathNavigate entityPathNavigate;
   private double nearSpeed;

   public void startExecuting() {
      this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
   }

   public void resetTask() {
      this.closestLivingEntity = null;
   }

   public EntityAIAvoidEntity(EntityCreature var1, Predicate var2, float var3, double var4, double var6) {
      this.theEntity = var1;
      this.field_179510_i = var2;
      this.field_179508_f = var3;
      this.farSpeed = var4;
      this.nearSpeed = var6;
      this.entityPathNavigate = var1.getNavigator();
      this.setMutexBits(1);
   }

   public void updateTask() {
      if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D) {
         this.theEntity.getNavigator().setSpeed(this.nearSpeed);
      } else {
         this.theEntity.getNavigator().setSpeed(this.farSpeed);
      }

   }

   public boolean shouldExecute() {
      List var1 = this.theEntity.worldObj.func_175674_a(this.theEntity, this.theEntity.getEntityBoundingBox().expand((double)this.field_179508_f, 3.0D, (double)this.field_179508_f), Predicates.and(new Predicate[]{IEntitySelector.field_180132_d, this.field_179509_a, this.field_179510_i}));
      if (var1.isEmpty()) {
         return false;
      } else {
         this.closestLivingEntity = (Entity)var1.get(0);
         Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
         if (var2 == null) {
            return false;
         } else if (this.closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
            return false;
         } else {
            this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
            return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(var2);
         }
      }
   }

   public boolean continueExecuting() {
      return !this.entityPathNavigate.noPath();
   }
}

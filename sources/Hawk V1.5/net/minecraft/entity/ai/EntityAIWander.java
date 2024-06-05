package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIWander extends EntityAIBase {
   private double yPosition;
   private boolean field_179482_g;
   private int field_179481_f;
   private static final String __OBFID = "CL_00001608";
   private double zPosition;
   private double speed;
   private double xPosition;
   private EntityCreature entity;

   public EntityAIWander(EntityCreature var1, double var2, int var4) {
      this.entity = var1;
      this.speed = var2;
      this.field_179481_f = var4;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if (!this.field_179482_g) {
         if (this.entity.getAge() >= 100) {
            return false;
         }

         if (this.entity.getRNG().nextInt(this.field_179481_f) != 0) {
            return false;
         }
      }

      Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
      if (var1 == null) {
         return false;
      } else {
         this.xPosition = var1.xCoord;
         this.yPosition = var1.yCoord;
         this.zPosition = var1.zCoord;
         this.field_179482_g = false;
         return true;
      }
   }

   public void startExecuting() {
      this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
   }

   public boolean continueExecuting() {
      return !this.entity.getNavigator().noPath();
   }

   public EntityAIWander(EntityCreature var1, double var2) {
      this(var1, var2, 120);
   }

   public void func_179480_f() {
      this.field_179482_g = true;
   }

   public void func_179479_b(int var1) {
      this.field_179481_f = var1;
   }
}

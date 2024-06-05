package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityRabbit$EnumMoveType;

public class EntityRabbit$RabbitJumpHelper extends EntityJumpHelper {
   private EntityRabbit theEntity;
   private boolean field_180068_d;
   final EntityRabbit this$0;

   public EntityRabbit$RabbitJumpHelper(EntityRabbit var1, EntityRabbit var2) {
      super(var2);
      this.this$0 = var1;
      this.field_180068_d = false;
      this.theEntity = var2;
   }

   public boolean getIsJumping() {
      return this.isJumping;
   }

   public boolean func_180065_d() {
      return this.field_180068_d;
   }

   public void func_180066_a(boolean var1) {
      this.field_180068_d = var1;
   }

   public void doJump() {
      if(this.isJumping) {
         this.theEntity.doMovementAction(EntityRabbit$EnumMoveType.STEP);
         this.isJumping = false;
      }

   }
}

package net.minecraft.client.renderer;

import com.google.common.base.Predicate;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;

class EntityRenderer1 implements Predicate {
   final EntityRenderer field_90032_a;

   EntityRenderer1(EntityRenderer var1) {
      this.field_90032_a = var1;
   }

   public boolean apply(Entity var1) {
      return var1.canBeCollidedWith();
   }

   public boolean apply(Object var1) {
      return this.apply((Entity)var1);
   }
}

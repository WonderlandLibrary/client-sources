package com.example.editme.events;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

public class RenderEvent extends EditmeEvent {
   private final Vec3d renderPos;
   private final Tessellator tessellator;

   public void setTranslation(Vec3d var1) {
      this.getBuffer().func_178969_c(-var1.field_72450_a, -var1.field_72448_b, -var1.field_72449_c);
   }

   public BufferBuilder getBuffer() {
      return this.tessellator.func_178180_c();
   }

   public RenderEvent(Tessellator var1, Vec3d var2) {
      this.tessellator = var1;
      this.renderPos = var2;
   }

   public Vec3d getRenderPos() {
      return this.renderPos;
   }

   public Tessellator getTessellator() {
      return this.tessellator;
   }

   public void resetTranslation() {
      this.setTranslation(this.renderPos);
   }
}

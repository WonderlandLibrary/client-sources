package rina.turok.bope.bopemod.events;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;
import rina.turok.bope.external.BopeEventCancellable;

public class BopeEventRender extends BopeEventCancellable {
   private final Tessellator tessellator;
   private final Vec3d render_pos;

   public BopeEventRender(Tessellator tessellator, Vec3d pos) {
      this.tessellator = tessellator;
      this.render_pos = pos;
   }

   public Tessellator get_tessellator() {
      return this.tessellator;
   }

   public Vec3d get_render_pos() {
      return this.render_pos;
   }

   public BufferBuilder get_buffer_build() {
      return this.tessellator.getBuffer();
   }

   public void set_translation(Vec3d pos) {
      this.get_buffer_build().setTranslation(-pos.x, -pos.y, -pos.z);
   }

   public void reset_translation() {
      this.set_translation(this.render_pos);
   }
}

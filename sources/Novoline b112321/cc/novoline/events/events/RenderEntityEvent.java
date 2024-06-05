package cc.novoline.events.events;

import cc.novoline.events.events.Cancellable;
import cc.novoline.events.events.Event;
import cc.novoline.events.events.MotionUpdateEvent$State;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;

public class RenderEntityEvent implements Event, Cancellable {
   private Entity d;
   private MotionUpdateEvent$State f;
   private double a;
   private double c;
   private double e;
   private RendererLivingEntity b;
   private boolean g;

   public RenderEntityEvent(Entity var1, MotionUpdateEvent$State var2) {
      this.d = var1;
      this.f = var2;
   }

   public RenderEntityEvent(RendererLivingEntity var1, Entity var2, double var3, double var5, double var7, MotionUpdateEvent$State var9) {
      this.d = var2;
      this.f = var9;
      this.a = var3;
      this.c = var5;
      this.e = var7;
      this.b = var1;
   }

   public MotionUpdateEvent$State f() {
      return this.f;
   }

   public Entity a() {
      return this.d;
   }

   public double c() {
      return this.a;
   }

   public double e() {
      return this.c;
   }

   public double d() {
      return this.e;
   }

   public RendererLivingEntity b() {
      return this.b;
   }

   public boolean isCancelled() {
      return this.g;
   }

   public void setCancelled(boolean var1) {
      this.g = var1;
   }
}

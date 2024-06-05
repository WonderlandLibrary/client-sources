package hawk.events.listeners;

import hawk.events.Event;

public class EventRenderPlayer extends Event<EventRenderPlayer> {
   public float yaw;
   private float partialTicks;
   public float pitch;
   public float yawChange;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public float getPitch() {
      return this.pitch;
   }

   public EventRenderPlayer(float var1, float var2, float var3, float var4) {
      this.yaw = var1;
      this.pitch = var2;
      this.yawChange = var3;
      this.partialTicks = var4;
   }
}

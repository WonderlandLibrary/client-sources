package hawk.modules.render;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventRenderPlayer;
import hawk.modules.Module;
import hawk.util.Timer;

public class HeadRotations extends Module {
   public Timer timer = new Timer();

   public void onDisable() {
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion && var1.isPre()) {
         this.mc.thePlayer.rotationYawHead = ((EventMotion)var1).getYaw();
         this.mc.thePlayer.renderYawOffset = ((EventMotion)var1).getYaw();
      }

      if (var1 instanceof EventRenderPlayer && var1.isPre()) {
         ((EventRenderPlayer)var1).setPitch(0.0F);
      }

   }

   public HeadRotations() {
      super("HeadRotations", 0, Module.Category.RENDER);
   }

   public void onEnable() {
   }
}

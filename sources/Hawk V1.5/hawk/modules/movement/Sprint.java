package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;

public class Sprint extends Module {
   public boolean isSprintToggled = false;

   public void onEnable() {
   }

   public void onDisable() {
      this.mc.thePlayer.setSprinting(this.mc.gameSettings.keyBindSprint.getIsKeyPressed());
   }

   public Sprint() {
      super("Sprint", 0, Module.Category.MOVEMENT);
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate && var1.isPre() && this.mc.thePlayer.moveForward > 0.0F && !this.mc.thePlayer.isSneaking() && !this.mc.thePlayer.isCollidedHorizontally && (!this.mc.thePlayer.isUsingItem() || Noslow.isnoslow)) {
         this.mc.thePlayer.setSprinting(true);
         this.isSprintToggled = true;
      }

   }
}

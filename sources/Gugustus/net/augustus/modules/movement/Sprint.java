package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventSilentMove;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends Module {
   public BooleanValue allDirection = new BooleanValue(1, "AllDirection", this, false);
   public boolean allSprint;

   public Sprint() {
      super("Sprint", new Color(170, 181, 182, 255), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      KeyBinding.setKeyBindState(
         mc.gameSettings.keyBindSprint.getKeyCode(),
         /*!mm.scaffoldWalk.isToggled() && !mm.newScaffold.isToggled() && */(!mm.blockFly.isToggled() || mm.blockFly.sprint.getBoolean())
      );
      if (!this.allDirection.getBoolean()
         || mc.thePlayer.isUsingItem()
         || mc.thePlayer.movementInput.moveForward == 0.0F
         || mc.thePlayer.isCollidedHorizontally) {
         this.allSprint = false;
      } else if (!mm.blockFly.isToggled() || mm.blockFly.sprint.getBoolean()) {
         this.allSprint = true;
      }
   }

   @EventTarget
   public void onEventUpdate(EventSilentMove eventSilentMove) {
      if (mc.thePlayer.isSprinting() && mm.blockFly.isToggled() && !mm.blockFly.sprint.getBoolean()) {
         mc.thePlayer.movementInput.moveForward = 0.0F;
      }
   }
}

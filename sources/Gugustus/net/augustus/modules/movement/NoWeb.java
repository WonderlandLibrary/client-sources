package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventSilentMove;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.util.MathHelper;

public class NoWeb extends Module {
   public final StringValue mode = new StringValue(1, "Mode", this, "Intave", new String[]{"Intave", "Ignore"});
   private int counter = 0;
   private boolean wasInWeb;

   public NoWeb() {
      super("NoWeb", new Color(179, 252, 255), Categorys.MOVEMENT);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (mc.thePlayer != null) {
         mc.thePlayer.jumpMovementFactor = 0.02F;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (mc.thePlayer != null) {
         mc.thePlayer.jumpMovementFactor = 0.02F;
      }
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      if (mc.thePlayer.isInWeb()) {
         mc.thePlayer.onGround = false;
         String selected = this.mode.getSelected();
         switch(selected) {
            case "Intave":
               if (!mc.thePlayer.isCollidedVertically) {
                  mc.thePlayer.jumpMovementFactor = 0.8F;
               } else if (mc.thePlayer.movementInput.moveStrafe == 0.0F && mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.isCollidedVertically) {
                  mc.thePlayer.jumpMovementFactor = 0.74F;
               } else {
                  mc.thePlayer.jumpMovementFactor = 0.2F;
                  mc.thePlayer.onGround = true;
               }
         }

         this.wasInWeb = mc.thePlayer.isInWeb();
      } else if ((double)mc.thePlayer.jumpMovementFactor > 0.03 && this.wasInWeb && !mc.thePlayer.isInWeb()) {
         this.wasInWeb = mc.thePlayer.isInWeb();
         mc.thePlayer.jumpMovementFactor = 0.02F;
      }
   }

   @EventTarget
   public void onEventSilentMove(EventSilentMove eventSilentMove) {
      String selected = this.mode.getSelected();
      byte var4 = -1;
      switch(selected.hashCode()) {
         case -2099899231:
            if (selected.equals("Intave")) {
               var4 = 0;
            }
         default:
            switch(var4) {
               case 0:
                  if (mc.thePlayer.isCollidedVertically) {
                     if (mc.thePlayer.isInWeb() && mc.thePlayer.movementInput.moveStrafe == 0.0F && mc.gameSettings.keyBindForward.isKeyDown()) {
                        mc.thePlayer.movementInput.moveForward = this.counter % 5 == 0 ? 0.0F : 1.0F;
                        ++this.counter;
                     }
                  } else if (mc.thePlayer.isInWeb()) {
                     if (mc.thePlayer.isSprinting()) {
                        mc.thePlayer.movementInput.moveForward = 0.0F;
                     }

                     mc.thePlayer.movementInput.sneak = true;
                     mc.thePlayer.movementInput.moveForward = (float)MathHelper.clamp_double((double)mc.thePlayer.movementInput.moveForward, -0.3, 0.3);
                     mc.thePlayer.movementInput.moveStrafe = (float)MathHelper.clamp_double(
                        mc.thePlayer.movementInput.moveForward == 0.0F ? (double)mc.thePlayer.movementInput.moveStrafe : 0.0, -0.3, 0.3
                     );
                  }
            }
      }
   }
}

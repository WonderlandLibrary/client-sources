package vestige.module.impl.visual;

import org.lwjgl.input.Keyboard;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.EventListenType;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.ModeSetting;

public class Freelook extends Module {
   private boolean wasFreelooking;
   private ModeSetting mode = new ModeSetting("Mode", "Third Person", new String[]{"Third Person", "First Person"});

   public Freelook() {
      super("Freelook", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.mode});
      this.listenType = EventListenType.MANUAL;
      this.startListening();
   }

   @Listener
   public void onTick(TickEvent event) {
      if (mc.thePlayer.ticksExisted < 10) {
         this.stop();
      }

      if (Keyboard.isKeyDown(this.getKey())) {
         this.wasFreelooking = true;
         Flap.instance.getCameraHandler().setFreelooking(true);
         String var2 = this.mode.getMode();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -857011451:
            if (var2.equals("First Person")) {
               var3 = 0;
            }
            break;
         case -86954898:
            if (var2.equals("Third Person")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
         default:
            break;
         case 1:
            mc.gameSettings.thirdPersonView = 1;
         }
      } else if (this.wasFreelooking) {
         this.stop();
      }

   }

   private void stop() {
      this.setEnabled(false);
      Flap.instance.getCameraHandler().setFreelooking(false);
      this.wasFreelooking = false;
      mc.gameSettings.thirdPersonView = 0;
   }
}

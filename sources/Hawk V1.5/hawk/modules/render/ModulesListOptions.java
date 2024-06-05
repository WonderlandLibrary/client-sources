package hawk.modules.render;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.settings.Setting;
import hawk.util.Timer;

public class ModulesListOptions extends Module {
   public ModeSetting Color = new ModeSetting("Color", this, "Colorful", new String[]{"Colorful", "Discord", "Red", "Blue", "Orange", "Green", "White"});
   Timer timer = new Timer();
   public static int ColorOptionInt = 0;
   boolean PlayerEat = false;

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate) {
         if (this.Color.is("Colorful")) {
            ColorOptionInt = 0;
         }

         if (this.Color.is("Red")) {
            ColorOptionInt = 1;
         }

         if (this.Color.is("Blue")) {
            ColorOptionInt = 2;
         }

         if (this.Color.is("Orange")) {
            ColorOptionInt = 3;
         }

         if (this.Color.is("Green")) {
            ColorOptionInt = 4;
         }

         if (this.Color.is("White")) {
            ColorOptionInt = 5;
         }

         if (this.Color.is("Discord")) {
            ColorOptionInt = 11;
         }
      }

   }

   public ModulesListOptions() {
      super("HUD Options", 0, Module.Category.RENDER);
      this.addSettings(new Setting[]{this.Color});
   }
}

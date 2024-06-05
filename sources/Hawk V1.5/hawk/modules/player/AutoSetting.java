package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.settings.Setting;

public class AutoSetting extends Module {
   public static boolean enabled = false;
   public static boolean isOldVerus = false;
   public static boolean isHypixel = false;
   public ModeSetting server = new ModeSetting("Server", this, "Hypixel", new String[]{"Hypixel", "Mineplex", "Redesky", "OldVerus"});
   public static boolean isMineplex = false;
   hawk.util.Timer timer = new hawk.util.Timer();
   public static boolean isRedesky = false;

   public void onDisable() {
      enabled = false;
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate) {
         if (this.server.is("Hypixel")) {
            isHypixel = true;
         } else {
            isHypixel = false;
         }

         if (this.server.is("Mineplex")) {
            isMineplex = true;
         } else {
            isMineplex = false;
         }

         if (this.server.is("Redesky")) {
            isRedesky = true;
         } else {
            isRedesky = false;
         }

         if (this.server.is("OldVerus")) {
            isOldVerus = true;
         } else {
            isOldVerus = false;
         }
      }

   }

   public void onEnable() {
      enabled = true;
   }

   public AutoSetting() {
      super("AutoSetting", 0, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.server});
   }
}

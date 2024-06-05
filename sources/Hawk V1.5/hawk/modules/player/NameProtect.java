package hawk.modules.player;

import hawk.modules.Module;

public class NameProtect extends Module {
   hawk.util.Timer timer = new hawk.util.Timer();
   public static String newname = "Me";
   public static boolean isEnabled = false;

   public void onDisable() {
      isEnabled = false;
   }

   public void onEnable() {
      isEnabled = true;
   }

   public NameProtect() {
      super("NameProtect", 0, Module.Category.PLAYER);
   }

   public static String getNewName() {
      return newname;
   }
}

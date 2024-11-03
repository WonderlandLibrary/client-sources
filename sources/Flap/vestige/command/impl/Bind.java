package vestige.command.impl;

import org.lwjgl.input.Keyboard;
import vestige.Flap;
import vestige.command.Command;
import vestige.module.Module;
import vestige.util.misc.LogUtil;

public class Bind extends Command {
   public Bind() {
      super("Bind", "Changes the keybind of the specified module.");
   }

   public void onCommand(String[] args) {
      if (args.length >= 3) {
         Module module = Flap.instance.getModuleManager().getModuleByNameNoSpace(args[1]);
         if (module != null) {
            String keyName = args[2].toUpperCase();
            module.setKey(Keyboard.getKeyIndex(keyName));
            LogUtil.addChatMessage("&9&l[FLAP]&r &fBound " + module.getName() + " to " + keyName);
         }
      } else {
         LogUtil.addChatMessage("&9&l[FLAP]&r Usage:&l&a .bind module keybind");
      }

   }
}

package vestige.command.impl;

import vestige.Flap;
import vestige.command.Command;
import vestige.module.Module;
import vestige.util.misc.LogUtil;

public class Toggle extends Command {
   public Toggle() {
      super("Toggle", "Turns on or off the specified module.", "t");
   }

   public void onCommand(String[] args) {
      if (args.length >= 2) {
         Module module = Flap.instance.getModuleManager().getModuleByNameNoSpace(args[1]);
         if (module != null) {
            module.toggle();
            LogUtil.addChatMessage((module.isEnabled() ? "&9&l[FLAP]&r &aEnabled " : "&9&l[FLAP]&r &cDisabled ") + module.getName());
         }
      } else {
         LogUtil.addChatMessage("&9&l[FLAP]&r Usage:&l&a .t/toggle modulename");
      }

   }
}

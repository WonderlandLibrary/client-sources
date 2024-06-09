package me.hexxed.mercury.commands;

import java.util.List;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.util.Util;
import org.lwjgl.input.Keyboard;

public class Modules extends Command
{
  public Modules()
  {
    super("modules", "modules");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 0) {
      Util.addChatMessage(getUsage());
      return;
    }
    StringBuilder sbuilder = new StringBuilder();
    String modules = null;
    boolean b = true;
    for (Module m : ModuleManager.moduleList) {
      if (!b) {
        sbuilder.append(" §7," + (m.isEnabled() ? "§6" : "§b") + m.getModuleName() + " §9[" + Keyboard.getKeyName(m.getKeybind()) + "]");
      } else {
        sbuilder.append("Modules(" + ModuleManager.moduleList.size() + "): " + (m.isEnabled() ? "§6" : "§b") + m.getModuleName() + " §9[" + Keyboard.getKeyName(m.getKeybind()) + "]");
        b = false;
      }
    }
    modules = sbuilder.toString();
    Util.sendInfo(modules);
  }
}

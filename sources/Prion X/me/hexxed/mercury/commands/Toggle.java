package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.util.FileUtils;
import me.hexxed.mercury.util.Util;

public class Toggle extends Command
{
  public Toggle()
  {
    super("t", "toggle <Module/'usual'/'alloff'>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    String module = args[0];
    if (module.equalsIgnoreCase("usual")) {
      for (Module m : ModuleManager.moduleList) {
        if (m.isEnabled()) { m.setStateSilent(false);
        }
      }
      













      FileUtils.enableStartupMods();
      Util.sendInfo("Enabled usual setup");
      return;
    }
    if (module.equalsIgnoreCase("alloff")) {
      for (Module m : ModuleManager.moduleList) {
        if (m.isEnabled()) m.setStateSilent(false);
      }
      Util.sendInfo("Disabled all modules!");
      return;
    }
    try {
      ModuleManager.getModByName(module).toggle();
      Util.sendInfo("§b" + ModuleManager.getModByName(module).getModuleName() + " §7was " + (ModuleManager.getModByName(module).isEnabled() ? "enabled" : "disabled"));
    } catch (Exception e) {
      Util.sendInfo("Module not found!");
    }
  }
}

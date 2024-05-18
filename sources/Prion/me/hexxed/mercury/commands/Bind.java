package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.util.Keybinds;
import me.hexxed.mercury.util.Util;
import org.lwjgl.input.Keyboard;

public class Bind extends Command
{
  public Bind()
  {
    super("bind", "bind <mod> <key>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 2) {
      Util.addChatMessage(getUsage());
      return;
    }
    int key = Keyboard.getKeyIndex(args[1].toUpperCase());
    if (args[0].equalsIgnoreCase("all")) {
      for (Module m : ModuleManager.moduleList) {
        Keybinds.bindKey(m, key);
      }
      Util.sendInfo("§bAll modules §7were bound to §b" + Keyboard.getKeyName(key));
      return;
    }
    Module mod = ModuleManager.getModByName(args[0]);
    if (mod == null) {
      Util.sendInfo("Module not found!");
      return;
    }
    Keybinds.bindKey(mod, key);
    Util.sendInfo("§b" + mod.getModuleName() + " §7was bound to §b" + Keyboard.getKeyName(mod.getKeybind()));
  }
}

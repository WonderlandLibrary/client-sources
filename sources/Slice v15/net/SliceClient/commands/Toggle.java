package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;
import net.SliceClient.commandbase.Command;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;

public class Toggle extends Command
{
  public Toggle()
  {
    super("t", "t");
  }
  
  public void execute(String[] args)
  {
    if (args.length == 0) {
      Util.addChatMessage(Slice.prefix + "§a.t §8[Module] ");
      return;
    }
    if (args.length == 1) {
      for (Module m : ModuleManager.getModules()) {
        if (m.getName().equalsIgnoreCase(args[0])) {
          m.toggleModule();
          String state = m.getState() ? " §aEnabled!" : " §cDisabled!";
          Util.addChatMessage(Slice.prefix + "This Module is" + state);
          return;
        }
      }
      Util.addChatMessage(Slice.prefix + "§cModule wurde nicht gefunden!");
    }
    super.execute(args);
  }
}

package net.SliceClient.commands;

import java.util.ArrayList;
import net.SliceClient.Utils.Util;
import net.SliceClient.commandbase.Command;
import net.SliceClient.module.ModuleManager;

public class Modules
  extends Command
{
  public Modules()
  {
    super("modules", "modules");
  }
  
  public void execute(String[] args)
  {
    Util.addChatMessage("§3()===========(§6Alive b5§3)===========()");
    Util.addChatMessage("§b there are " + ModuleManager.activeModules.size() + " Modules");
    Util.addChatMessage("§3()===========(§6Alive b5§3)===========()");
  }
}

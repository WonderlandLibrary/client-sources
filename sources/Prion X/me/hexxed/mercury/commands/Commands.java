package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.commandbase.CommandManager;
import me.hexxed.mercury.util.Util;

public class Commands extends Command
{
  public Commands()
  {
    super("commands", "commands");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 0) {
      Util.addChatMessage(getUsage());
      return;
    }
    StringBuilder sbuilder = new StringBuilder();
    String commands = null;
    boolean b = true;
    for (Command cmd : CommandManager.commandList) {
      if (!b) {
        sbuilder.append(" §7,§b" + cmd.getName());
      } else {
        sbuilder.append("Commands(" + CommandManager.commandList.size() + "): §b" + cmd.getName());
        b = false;
      }
    }
    commands = sbuilder.toString();
    Util.sendInfo(commands);
  }
}

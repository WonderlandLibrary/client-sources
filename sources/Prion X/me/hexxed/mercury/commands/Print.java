package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;

public class Print extends Command
{
  public Print()
  {
    super("print", "print <message>");
  }
  
  public void execute(String[] args)
  {
    if (args.length < 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (String s : args) {
      s = me.hexxed.mercury.util.ChatColor.translateAlternateColorCodes('&', s);
      sb.append(s + " ");
    }
    sb.trimToSize();
    Util.addChatMessage(sb.toString().trim());
  }
}

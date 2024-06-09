package me.hexxed.mercury.commands;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;





public class Help
  extends Command
{
  public Help()
  {
    super("help", "help");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 0) {
      Util.addChatMessage(getUsage());
      return;
    }
    Util.addChatMessage("§3" + Mercury.getInstance().getName() + " " + Mercury.getInstance().getVersion() + " by " + Mercury.getInstance().getAuthor());
    Util.addChatMessage("§b.modules §7for a list of modules.");
    Util.addChatMessage("§b.commands §7for a list of commands.");
  }
}

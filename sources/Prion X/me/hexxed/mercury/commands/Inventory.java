package me.hexxed.mercury.commands;

import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.Util;

public class Inventory extends me.hexxed.mercury.commandbase.Command
{
  public Inventory()
  {
    super("inv", "inv <more/move>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage()); return;
    }
    String str;
    switch ((str = args[0]).hashCode()) {case 3357525:  if (str.equals("more")) break; break; case 3357649:  if (!str.equals("move"))
      {
        break label196;
        getValuesinvmore = (!getValuesinvmore);
        Util.sendInfo("Inventory " + (getValuesinvmore ? "will now " : "no longer ") + "carry additional items");
        return;
      }
      else {
        getValuesinvmove = (!getValuesinvmove);
        Util.sendInfo("Inventory " + (getValuesinvmove ? "will now " : "no longer ") + "let you move with GUI open"); }
      break;
    }
    label196:
    Util.addChatMessage(getUsage());
  }
}

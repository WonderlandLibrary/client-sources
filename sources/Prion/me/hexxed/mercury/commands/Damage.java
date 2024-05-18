package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;

public class Damage extends Command
{
  public Damage()
  {
    super("d", "damage <amount>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    Integer amount = null;
    try {
      amount = Integer.valueOf(Integer.parseInt(args[0]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    if (amount.intValue() <= 0) {
      Util.addChatMessage("§4Damage must be greater than 0");
      return;
    }
    Util.damagePlayer(amount.intValue());
    Util.sendInfo("Damaged you for §b" + amount + "HP§7.");
  }
}

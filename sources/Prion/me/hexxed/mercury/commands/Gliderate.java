package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;

public class Gliderate extends Command
{
  public Gliderate()
  {
    super("gr", ".gliderate <rate>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    Double rate = null;
    try {
      rate = Double.valueOf(Double.parseDouble(args[0]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    getValuesglidespeed = rate.doubleValue();
    Util.sendInfo("Glide rate set to §b" + String.valueOf(rate));
  }
}

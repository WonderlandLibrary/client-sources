package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;

public class Timer extends Command
{
  public Timer()
  {
    super("timer", "timer <multiplier>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    Float amount = null;
    try {
      amount = Float.valueOf(Float.parseFloat(args[0]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    getMinecrafttimer.timerSpeed = amount.floatValue();
    Util.sendInfo("Timer speed set to §b" + String.valueOf(amount));
  }
}

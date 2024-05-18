package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;
import net.SliceClient.commandbase.Command;

public class Timer extends Command
{
  public Timer()
  {
    super("timer", "timer");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(Slice.prefix + ".Timer <amount>");
      Util.addChatMessage(Slice.prefix + "Default Time is 1.0");
      return;
    }
    Float amount = null;
    try {
      amount = Float.valueOf(Float.parseFloat(args[0]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    net.minecraft.util.Timer.timerSpeed = amount.floatValue();
    Util.addChatMessage(Slice.prefix + "Timer speed set to §b" + String.valueOf(amount));
  }
}

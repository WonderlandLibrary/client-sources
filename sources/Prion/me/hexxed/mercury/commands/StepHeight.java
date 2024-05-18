package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;

public class StepHeight extends Command
{
  public StepHeight()
  {
    super("sh", "sh <height>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    Double height = null;
    try {
      height = Double.valueOf(Double.parseDouble(args[0]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    getValuesStepHeight = height.doubleValue();
    Util.sendInfo("Step height set to §b" + String.valueOf(height));
  }
}

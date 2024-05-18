package me.hexxed.mercury.commands;

import me.hexxed.mercury.util.Util;

public class Calculate extends me.hexxed.mercury.commandbase.Command
{
  public Calculate()
  {
    super("calc", "calt <calculation>");
  }
  
  public void execute(String[] args)
  {
    if (args.length < 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (String s : args) {
      sb.append(s);
    }
    String calc = sb.toString().trim();
    double result = Util.eval(calc);
    Util.sendInfo("Result of calculation is §b" + String.valueOf(result));
  }
}

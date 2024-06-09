package me.hexxed.mercury.commands;

import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.Util;

public class Speedrate extends me.hexxed.mercury.commandbase.Command
{
  public Speedrate()
  {
    super("s", "swift <<onlymotion>/<s> <value>>");
  }
  
  public void execute(String[] args)
  {
    if (args.length < 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    if (args.length != 2) { String str;
      switch ((str = args[0]).hashCode()) {case 895708770:  if (str.equals("onlymotion"))
        {

          getValuesonlymotion = (!getValuesonlymotion);
          Util.sendInfo("Swift " + (getValuesonlymotion ? "will no longer" : "will now") + " use timer"); }
        break;
      }
      
      Util.addChatMessage(getUsage());
      


      return;
    }
    Float rate = null;
    try {
      rate = Float.valueOf(Float.parseFloat(args[1]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    switch ((e = args[0]).hashCode()) {case 115:  if (e.equals("s"))
      {

        getValuessprintspeed = rate.floatValue();
        getValueswalkspeed = rate.floatValue();
        Util.sendInfo("Speed set to §b" + String.valueOf(rate)); }
      break;
    }
    
    Util.addChatMessage(getUsage());
  }
}

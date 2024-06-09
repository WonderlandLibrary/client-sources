package me.hexxed.mercury.commands;

import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.Util;

public class Killaurarate extends me.hexxed.mercury.commandbase.Command
{
  public Killaurarate()
  {
    super("ka", "killaura <range(r)/speed(s)/ticks(t)/autoblock(b)/invisibles(i)>");
  }
  
  public void execute(String[] args)
  {
    if ((args.length < 1) || (args.length > 2)) {
      Util.addChatMessage(getUsage());
      return;
    }
    if ((!args[0].equalsIgnoreCase("r")) && (!args[0].equalsIgnoreCase("s")) && (!args[0].equalsIgnoreCase("b")) && (!args[0].equalsIgnoreCase("i")) && (!args[0].equalsIgnoreCase("t"))) {
      Util.addChatMessage(getUsage());
      return;
    }
    if (args[0].equalsIgnoreCase("r")) {
      if (args.length != 2) {
        Util.addChatMessage(getUsage());
        return;
      }
      Float distance = null;
      try {
        distance = Float.valueOf(Float.parseFloat(args[1]));
      } catch (NumberFormatException e) {
        Util.addChatMessage(getUsage());
        return;
      }
      getValuesdistance = distance.floatValue();
      Util.sendInfo("Distance now set to §b" + String.valueOf(distance));
    }
    if (args[0].equalsIgnoreCase("s")) {
      if (args.length != 2) {
        Util.addChatMessage(getUsage());
        return;
      }
      Integer speed = null;
      try {
        speed = Integer.valueOf(Integer.parseInt(args[1]));
      } catch (NumberFormatException e) {
        Util.addChatMessage(getUsage());
        return;
      }
      getValuescps = speed.intValue();
      Util.sendInfo("Auraspeed now set to §b" + String.valueOf(speed));
    }
    if (args[0].equalsIgnoreCase("b")) {
      if (args.length != 1) {
        Util.addChatMessage(getUsage());
        return;
      }
      getValuesautoblock = (!getValuesautoblock);
      Util.sendInfo((getValuesautoblock ? "Now" : "No longer") + " autoblocking for you.");
    }
    if (args[0].equalsIgnoreCase("i")) {
      if (args.length != 1) {
        Util.addChatMessage(getUsage());
        return;
      }
      getValuesinvisibles = (!getValuesinvisibles);
      Util.sendInfo((getValuesinvisibles ? "Now" : "No longer") + " attack invisible players.");
    }
    if (args[0].equalsIgnoreCase("t")) {
      if (args.length != 2) {
        Util.addChatMessage(getUsage());
        return;
      }
      Integer ticks = null;
      try {
        ticks = Integer.valueOf(Integer.parseInt(args[1]));
      } catch (NumberFormatException e) {
        Util.addChatMessage(getUsage());
        return;
      }
      getValuesticks = ticks.intValue();
      Util.sendInfo("Auraticks now set to §b" + String.valueOf(ticks));
    }
  }
}

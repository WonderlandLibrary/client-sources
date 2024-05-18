package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;
import net.SliceClient.Values.Values;

public class KillAura extends net.SliceClient.commandbase.Command
{
  public KillAura()
  {
    super("KillAura", "Manage the KillAura");
  }
  
  Integer ticks = null;
  
  public void execute(String[] args)
  {
    if (args.length == 0) {
      Util.addChatMessage(Slice.prefix + "§a.KillAura Range <Blocks>");
      Util.addChatMessage(Slice.prefix + "§a.KillAura Speed <Speed>");
      Util.addChatMessage(Slice.prefix + "§a.KillAura Ticks <Ticks>");
      Util.addChatMessage(Slice.prefix + "§a.KillAura Invisibles");
      Util.addChatMessage(Slice.prefix + "§a.KillAura AutoBlock");
      
      return;
    }
    if (args.length == 2) {
      if (args[0].equalsIgnoreCase("range")) {
        Values.distance = Double.valueOf(args[1]).doubleValue();
        Util.addChatMessage(Slice.prefix + "Range is now: §6" + Values.distance);
        return;
      }
      if (args[0].equalsIgnoreCase("speed")) {
        if (Integer.valueOf(args[1]).intValue() >= 19) {
          Util.addChatMessage(Slice.prefix + "§cmaximum is 18!");
          return;
        }
        
        Values.cps = Integer.valueOf(args[1]).intValue();
        Util.addChatMessage(Slice.prefix + "Speed is now: " + Values.cps);
        return;
      }
    }
    

    if (args[0].equalsIgnoreCase("ticks")) {
      Util.addChatMessage(Slice.prefix + "§a.KillAura Ticks <Ticks>");
    }
    
    if (args[0].equalsIgnoreCase("range")) {
      Util.addChatMessage(Slice.prefix + "§a.KillAura Range <Blocks>");
    }
    
    if (args[0].equalsIgnoreCase("speed")) {
      Util.addChatMessage(Slice.prefix + "§a.KillAura Speed <Speed>");
    }
    
    if ((args.length == 2) && 
      (args[0].equalsIgnoreCase("ticks"))) {
      try {
        ticks = Integer.valueOf(Integer.parseInt(args[1]));
      } catch (NumberFormatException e) {
        Util.addChatMessage(getUsage());
        return;
      }
      Values.ticks = ticks.intValue();
      Util.addChatMessage(Slice.prefix + "Ticks set to §6" + Values.ticks);
      return;
    }
    

    if ((args.length == 1) && 
      (args[0].equalsIgnoreCase("autoblock"))) {
      if (Values.autoblock) {
        Util.addChatMessage(Slice.prefix + "§cAutoblock wurde deaktiviert.");
        Values.autoblock = false;
        return;
      }
      Util.addChatMessage(Slice.prefix + "§aAutoblock wurde aktiviert.");
      Values.autoblock = true;
      return;
    }
    
    if ((args.length == 1) && 
      (args[0].equalsIgnoreCase("invisibles"))) {
      if (Values.invisibles) {
        Util.addChatMessage(Slice.prefix + "§cDie KillAura hittet nun keine unsichtbaren Spieler mehr.");
        Values.invisibles = false;
        return;
      }
      Util.addChatMessage(Slice.prefix + "§aDie KillAura hittet nun unsichtbare Spieler.");
      Values.invisibles = true;
      return;
    }
    super.execute(args);
  }
}

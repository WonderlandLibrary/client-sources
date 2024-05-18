package me.hexxed.mercury.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.macros.Macro;
import me.hexxed.mercury.macros.MacroManager;
import me.hexxed.mercury.util.Util;
import org.lwjgl.input.Keyboard;

public class MacroCMD extends Command
{
  public MacroCMD()
  {
    super("macro", "macro <<add/remove> <<key> <cmd1> <...>/<key>> / list>");
  }
  
  public void execute(String[] args) {
    Macro m;
    if (args.length == 1) {
      if (args[0].equalsIgnoreCase("list")) {
        Util.sendInfo("List of Macros:");
        for (Iterator localIterator1 = MacroManager.getManager().getMacros().iterator(); localIterator1.hasNext();) { m = (Macro)localIterator1.next();
          StringBuilder sb = new StringBuilder();
          for (String cmd : m.getCommands()) {
            sb.append(", §6" + cmd + "§7");
          }
          Util.sendInfo("§b" + Keyboard.getKeyName(m.getKeybind()) + "§8: §7" + sb.toString().substring(2).trim());
        }
      } else {
        Util.addChatMessage(getUsage());
      }
      return;
    }
    if (args.length > 1)
    {
      switch ((m = args[0]).hashCode()) {case -934610812:  if (m.equals("remove")) break;  case 96417:  if ((goto 596) && (m.equals("add")))
        {

          if (args.length < 3) {
            Util.addChatMessage(getUsage());
            return;
          }
          int keybind = Keyboard.getKeyIndex(args[1].toUpperCase());
          List<String> commands = new ArrayList();
          String cmdstring = "";
          StringBuilder sb = new StringBuilder();
          for (int i = 2; i < args.length; i++) {
            sb.append(args[i] + " ");
          }
          cmdstring = sb.toString().trim();
          if (!cmdstring.contains(";")) {
            commands.add(cmdstring);
          } else {
            String[] cmds = cmdstring.split(";");
            for (String s : cmds) {
              commands.add(s);
            }
          }
          Macro newm = new Macro(keybind, commands);
          MacroManager.getManager().addMacro(newm);
          Util.sendInfo("New Macro created for §b" + args[1].toUpperCase());
          return;
          

          int kb = Keyboard.getKeyIndex(args[1].toUpperCase());
          Object toremove = new ArrayList();
          for (??? = MacroManager.getManager().getMacros().iterator(); ((Iterator)???).hasNext();) { Macro m = (Macro)((Iterator)???).next();
            if (m.getKeybind() == kb)
              ((List)toremove).add(m);
          }
          Macro mac;
          for (??? = ((List)toremove).iterator(); ((Iterator)???).hasNext(); MacroManager.getManager().removeMacro(mac)) mac = (Macro)((Iterator)???).next();
          Util.sendInfo("Macro removed"); }
        break;
      }
      
      Util.addChatMessage(getUsage());

    }
    else
    {

      Util.addChatMessage(getUsage());
      return;
    }
  }
}

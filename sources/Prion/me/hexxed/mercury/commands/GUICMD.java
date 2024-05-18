package me.hexxed.mercury.commands;

import java.io.File;
import java.util.List;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.overlay.OverlayManager;
import me.hexxed.mercury.util.Util;



public class GUICMD
  extends Command
{
  public GUICMD()
  {
    super("gui", "gui <reload/name/list>");
  }
  
  String curgui = "default";
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage()); return;
    }
    
    String str1;
    switch ((str1 = args[0]).hashCode()) {case -934641255:  if (str1.equals("reload")) break; break; case 3322014:  if (!str1.equals("list"))
      {
        break label254;
        int comps = OverlayManager.getManager().update(curgui);
        Util.sendInfo("Reloaded GUI. Loaded §b" + comps + "§7 GUI components");
        return;
      }
      else {
        StringBuilder sbuilder = new StringBuilder();
        String modules = null;
        boolean b = true;
        for (String s : OverlayManager.getManager().getAllThemes()) {
          if (!b) {
            sbuilder.append(" §7, §b" + s);
          } else {
            sbuilder.append("GUI Themes(" + OverlayManager.getManager().getAllThemes().size() + "): §b" + s);
            b = false;
          }
        }
        modules = sbuilder.toString();
        Util.sendInfo(modules); }
      break;
    }
    label254:
    String filename = args[0];
    File testfile = new File(Mercury.raidriarDir.getAbsolutePath() + "\\gui\\" + filename + ".txt");
    if ((!testfile.exists()) || (!OverlayManager.getManager().getAllThemes().contains(filename))) {
      Util.sendInfo("GUI Theme §b" + filename + " §7not found.");
      return;
    }
    curgui = filename;
    int comps = OverlayManager.getManager().update(filename);
    Util.sendInfo("Loaded new GUI Theme with §b" + comps + "§7 GUI components");
  }
}

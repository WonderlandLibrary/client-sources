package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;

public class Help extends net.SliceClient.commandbase.Command
{
  public Help()
  {
    super("help", "help");
  }
  
  public void execute(String[] args)
  {
    Util.addChatMessage("§3()===========(§6" + Slice.B + "b" + Slice.v + "§3)===========()");
    Util.addChatMessage("§b.Bind §7for a bind System.");
    Util.addChatMessage("§b.t §7for a Toggle a Module.");
    Util.addChatMessage("§b.Modules §7for a list of Modules.");
    Util.addChatMessage("§b.Friend §7for a Friend System.");
    Util.addChatMessage("§b.KillAura §7for a KillAura System.");
    Util.addChatMessage("§b.Damage §7for 0.5 Damage.");
    Util.addChatMessage("§b.Clear §7for clear the Chat.");
    Util.addChatMessage("§b.Say §7for say a Text.");
    Util.addChatMessage("§b.1HitSword §7for a OneHit Sword.");
    Util.addChatMessage("§b.Credits §7for Credits.");
    Util.addChatMessage("§b.Gm1 §7for Creative Mode.");
    Util.addChatMessage("§3()===========(§6" + Slice.B + "b" + Slice.v + "§3)===========()");
  }
}

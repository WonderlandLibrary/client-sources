package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;

public class Credits extends net.SliceClient.commandbase.Command
{
  public Credits()
  {
    super("Credits", "Credits");
  }
  
  public void execute(String[] args)
  {
    Util.addChatMessage(Slice.prefix + "Coder: Quizexh");
    Util.addChatMessage(Slice.prefix + "Happy Hacking!");
    Util.addChatMessage(Slice.prefix + "Current Version: " + Slice.version);
  }
}

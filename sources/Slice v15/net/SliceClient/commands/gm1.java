package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;
import net.SliceClient.commandbase.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class gm1 extends Command
{
  public gm1()
  {
    super("gm1", "gm1");
  }
  

  public void execute(String[] args)
  {
    try
    {
      Minecraft.getMinecraft();Minecraft.thePlayer.sendChatMessage("/gamemode 1");
    }
    catch (Exception localException) {}
    

    Util.addChatMessage(Slice.prefix + "Error!");
  }
}

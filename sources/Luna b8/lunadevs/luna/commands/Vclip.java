package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Vclip
  extends Command
{
  public String getAlias()
  {
    return "vclip";
  }
  
  public String getDescription()
  {
    return "Vclip up and down";
  }
  
  public String getSyntax()
  {
    return "-vclip <blocks>";
  }
  
  public void onCommand(String command, String[] args)
    throws Exception
  {
    String blocks = args[0];
    
    int blocksint = Integer.parseInt(blocks);
    mc.thePlayer.setBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(0.0D, blocksint, 0.0D));
  }
}

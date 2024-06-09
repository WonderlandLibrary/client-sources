package Squad.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public abstract class Command
{
  private String name;
  private String usage;
  
  public Command(String name, String usage)
  {
    this.name = name;
    this.usage = usage;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getUsage()
  {
    return this.usage;
  }
  
  public void execute(String[] args) {}
  
  public static void msg(String msg)
  {
   Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§cSquad§7]§f " + msg));
  }
}

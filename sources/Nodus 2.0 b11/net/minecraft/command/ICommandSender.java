package net.minecraft.command;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public abstract interface ICommandSender
{
  public abstract String getCommandSenderName();
  
  public abstract IChatComponent func_145748_c_();
  
  public abstract void addChatMessage(IChatComponent paramIChatComponent);
  
  public abstract boolean canCommandSenderUseCommand(int paramInt, String paramString);
  
  public abstract ChunkCoordinates getPlayerCoordinates();
  
  public abstract World getEntityWorld();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.ICommandSender
 * JD-Core Version:    0.7.0.1
 */
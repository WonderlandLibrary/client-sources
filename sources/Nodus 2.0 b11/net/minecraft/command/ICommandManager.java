package net.minecraft.command;

import java.util.List;
import java.util.Map;

public abstract interface ICommandManager
{
  public abstract int executeCommand(ICommandSender paramICommandSender, String paramString);
  
  public abstract List getPossibleCommands(ICommandSender paramICommandSender, String paramString);
  
  public abstract List getPossibleCommands(ICommandSender paramICommandSender);
  
  public abstract Map getCommands();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.ICommandManager
 * JD-Core Version:    0.7.0.1
 */
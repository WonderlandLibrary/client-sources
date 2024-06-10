package net.minecraft.command;

import java.util.List;

public abstract interface ICommand
  extends Comparable
{
  public abstract String getCommandName();
  
  public abstract String getCommandUsage(ICommandSender paramICommandSender);
  
  public abstract List getCommandAliases();
  
  public abstract void processCommand(ICommandSender paramICommandSender, String[] paramArrayOfString);
  
  public abstract boolean canCommandSenderUseCommand(ICommandSender paramICommandSender);
  
  public abstract List addTabCompletionOptions(ICommandSender paramICommandSender, String[] paramArrayOfString);
  
  public abstract boolean isUsernameIndex(String[] paramArrayOfString, int paramInt);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.ICommand
 * JD-Core Version:    0.7.0.1
 */
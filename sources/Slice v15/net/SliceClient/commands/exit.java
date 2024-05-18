package net.SliceClient.commands;

import net.SliceClient.commandbase.Command;

public class exit
  extends Command
{
  public exit()
  {
    super("exit", "exit");
  }
  
  public void execute(String[] args)
  {
    System.exit(1);
  }
}

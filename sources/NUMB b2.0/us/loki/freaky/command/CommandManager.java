package us.loki.freaky.command;

import java.util.ArrayList;

import us.loki.legit.Client;
import us.loki.freaky.command.*;
import us.loki.freaky.command.commands.*;

public class CommandManager {

	 private static ArrayList<Command> commands;
	  
	  public CommandManager()
	  {
	    this.commands = new ArrayList();
	    commands.add(new Help());
	    commands.add(new Bind());
	    commands.add(new Toggle());
	    commands.add(new Friend());
	  }
	  
	  public void addCommand(Command c)
	  {
	    this.commands.add(c);
	  }
	  
	  public ArrayList<Command> getCommands()
	  {
	    return this.commands;
	  }
	  
	  public boolean callCommand(String input)
	  {
	    String[] split = input.split(" ");
	    String command = split[0];
	    String args = input.substring(command.length()).trim();
	    for (Command c : getCommands()) {
	      if (c.getAlias().equalsIgnoreCase(command))
	      {
	        try
	        {
	          c.onCommand(args, args.split(" "));
	        }
	        catch (Exception e)
	        {
	          Client.addChatMessage("Invalid Command Usage");
	          Client.addChatMessage(c.getSyntax());
	        }
	        return false;
	      }
	    }
		return false;
	  }
}
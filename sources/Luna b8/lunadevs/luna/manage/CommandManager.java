package lunadevs.luna.manage;

import java.util.ArrayList;

import lunadevs.luna.command.Command;
import lunadevs.luna.commands.Bind;
import lunadevs.luna.commands.ClearChat;
import lunadevs.luna.commands.ClientBuild;
import lunadevs.luna.commands.Friend;
import lunadevs.luna.commands.Hclip;
import lunadevs.luna.commands.Help;
import lunadevs.luna.commands.Killaura;
import lunadevs.luna.commands.Longjump;
import lunadevs.luna.commands.NotACommand;
import lunadevs.luna.commands.Say;
import lunadevs.luna.commands.TimerSpeed;
import lunadevs.luna.commands.Toggle;
import lunadevs.luna.commands.Vclip;
import lunadevs.luna.main.Luna;

public class CommandManager {

	 private ArrayList<Command> commands;
	  
	  public CommandManager()
	  {
	    this.commands = new ArrayList();
	    commands.add(new Help());
	    commands.add(new Bind());
	    commands.add(new Toggle());
	    commands.add(new TimerSpeed());
	    commands.add(new Vclip());
	    commands.add(new Hclip());
	    commands.add(new Killaura());
	    commands.add(new Friend());
	    commands.add(new Longjump());
	    commands.add(new ClearChat());
	    commands.add(new NotACommand());
	    commands.add(new Say());
	    commands.add(new ClientBuild());
	  }
	  
	  public void addCommand(Command c)
	  {
	    this.commands.add(c);
	  }
	  
	  public ArrayList<Command> getCommands()
	  {
	    return this.commands;
	  }
	  
	  public void callCommand(String input)
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
	          Luna.addChatMessage("Invalid Command Usage");
	          Luna.addChatMessage(c.getSyntax());
	        }
	        return;
	      }
	    }
	  }
}
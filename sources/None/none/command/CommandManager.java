package none.command;

import java.util.ArrayList;

import none.command.commands.*;
import none.event.events.EventChat;

public class CommandManager {
	
	private ArrayList<Command> commands;
	
	public CommandManager(){
		commands = new ArrayList();
		commands.add(new Bind());
		commands.add(new Toggle());
		commands.add(new LoginUser());
		commands.add(new Save());
		commands.add(new Load());
		commands.add(new NameProtectCommand());
		commands.add(new HelpCommand());
		commands.add(new Friend());
		commands.add(new AwakeNgineXE());
		commands.add(new Spammer());
		commands.add(new Config());
	}
	
	public ArrayList<Command> getCommands(){
		return commands;
	}
	
	public void callCommand(String input){
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		for(Command c: getCommands()){
			if(c.getAlias().equalsIgnoreCase(command)){
				try{
					c.onCommand(args, args.split(" "));
				}catch(Exception e){
					EventChat.addchatmessage("Invalid Command Usage!");
					EventChat.addchatmessage(c.getSyntax());
				}
				return;
			}
		}
		EventChat.addchatmessage("Command not found!");
	}

}

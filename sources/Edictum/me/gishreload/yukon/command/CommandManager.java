package me.gishreload.yukon.command;

import java.util.ArrayList;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.*;
import me.gishreload.yukon.commands.damage3;
import me.gishreload.yukon.commands.Bind;
import me.gishreload.yukon.commands.ConnectCommand;
import me.gishreload.yukon.commands.Help;
import me.gishreload.yukon.commands.Toggle;
import me.gishreload.yukon.commands.damage;
import me.gishreload.yukon.commands.damage2;
import net.minecraft.client.Minecraft;

public class CommandManager {
	
	private ArrayList<Command> commands;
	
	public CommandManager(){
		commands = new ArrayList();
		addCommand(new Bind());
		addCommand(new Toggle());
		addCommand(new Help());
		addCommand(new damage3());
		addCommand(new damage());
		addCommand(new damage2());
		addCommand(new ConnectCommand());
	}
	
	public void addCommand(Command c){
		commands.add(c);
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
					Edictum.addChatMessage("\u00a7cInvalid Command Usage!");
					Edictum.addChatMessage(c.getSyntax());
				}
				return;
			}
		}
	}

}

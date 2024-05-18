package com.darkcart.xdolf.commands;

import java.util.ArrayList;

import com.darkcart.xdolf.Wrapper;

public class CommandManager
{
	public static ArrayList<Command> commands = new ArrayList<Command>();

	public static char cmdPrefix = '.';

	public CommandManager()
	{
		addCommands();
	}

	public void addCommands()
	{
		commands.clear();
		commands.add(new CmdHelp());
		commands.add(new CmdToggle());
		commands.add(new CmdTimer());
		commands.add(new CmdAllOff());
		commands.add(new CmdSay());
		commands.add(new CmdModList());
		commands.add(new CmdSpam());
		commands.add(new CmdRotation());
		commands.add(new CmdView());
		commands.add(new CmdBind());
		commands.add(new CmdFriend());
		commands.add(new CmdImpersonate());
		commands.add(new CmdXray());
		commands.add(new CmdWaypoint());
		commands.add(new CmdMusic());
		commands.add(new CmdPlayerInfo());
		commands.add(new CmdDeathCoords());
		commands.add(new CmdVClip());
		commands.add( new CmdMacro());
		//commands.add(new CmdCaura()); | Messed up CrystalAura itself.
		//commands.add(new CmdSpeed()); | Didn't change the Speed mode.
	}
	
	public void runCommands(String s)
	{
		if(!s.contains(Character.toString(cmdPrefix)) || !s.startsWith(Character.toString(cmdPrefix))) return;

		boolean commandResolved = false;
		String readString = s.trim().substring(Character.toString(cmdPrefix).length()).trim();
		boolean hasArgs = readString.trim().contains(" ");
		String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
		String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];

		for(Command command: commands)
		{
			if(command.getCommand().trim().equalsIgnoreCase(commandName.trim())) 
			{
				command.runCommand(readString, args);
				commandResolved = true;
				break;
			}
		}

		if(!commandResolved)
		{
			Wrapper.addChatMessage("Error, couldn't find that command. Type .help to list all the commands.");
		}
	}
}
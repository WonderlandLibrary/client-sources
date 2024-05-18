package me.swezedcode.client.manager.managers;

import java.util.ArrayList;
import java.util.List;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.command.commands.CommandAura;
import me.swezedcode.client.command.commands.CommandAutoClicker;
import me.swezedcode.client.command.commands.CommandBanwave;
import me.swezedcode.client.command.commands.CommandBind;
import me.swezedcode.client.command.commands.CommandBlur;
import me.swezedcode.client.command.commands.CommandCrashBlock;
import me.swezedcode.client.command.commands.CommandFriend;
import me.swezedcode.client.command.commands.CommandHelp;
import me.swezedcode.client.command.commands.CommandLoad;
import me.swezedcode.client.command.commands.CommandMods;
import me.swezedcode.client.command.commands.CommandMusic;
import me.swezedcode.client.command.commands.CommandNameProt;
import me.swezedcode.client.command.commands.CommandReach;
import me.swezedcode.client.command.commands.CommandShit;
import me.swezedcode.client.command.commands.CommandTabGui;
import me.swezedcode.client.command.commands.CommandVClip;
import me.swezedcode.client.command.commands.CommandVelocity;
import me.swezedcode.client.module.modules.Fight.AutoClicker;
import me.swezedcode.client.utils.console.ConsoleUtils;
import me.swezedcode.client.utils.events.EventMessage;

public class CommandManager {

	List<Command> commands;

	public CommandManager() {
		commands = new ArrayList<Command>();
		addCommand(new CommandHelp());
		addCommand(new CommandMusic());
		addCommand(new CommandMods());
		addCommand(new CommandFriend());
		addCommand(new CommandBind());
		addCommand(new CommandAura());
		addCommand(new CommandVelocity());
		addCommand(new CommandNameProt());
		addCommand(new CommandVClip());
		addCommand(new CommandBanwave());
		addCommand(new CommandCrashBlock());
		addCommand(new CommandShit());
		addCommand(new CommandAutoClicker());
		addCommand(new CommandLoad());
		addCommand(new CommandTabGui());
		addCommand(new CommandReach());
		addCommand(new CommandBlur());
	}

	private void addCommand(Command command) {
		commands.add(command);
	}

	@EventListener
	public void onChat(EventMessage msg) {
		String message = msg.getMessage();

		if (!message.startsWith("-"))
			return;

		msg.setCanelled(true);

		message = message.substring(1, message.length());

		for (Command cmd : this.commands) {
			String[] arguments = message.split(" ");

			if (arguments[0].equalsIgnoreCase(cmd.getName())) {
				String[] args = new String[arguments.length - 1];
				System.arraycopy(arguments, 1, args, 0, args.length);
				cmd.executeMsg(args);
				return;
			}
		}
		ConsoleUtils.logChat("Command not found.");
	}

}

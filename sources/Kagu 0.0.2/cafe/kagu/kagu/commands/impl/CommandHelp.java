/**
 * 
 */
package cafe.kagu.kagu.commands.impl;

import cafe.kagu.kagu.commands.Command;
import cafe.kagu.kagu.commands.CommandAction;
import cafe.kagu.kagu.commands.CommandManager;
import cafe.kagu.kagu.utils.ChatUtils;

/**
 * @author lavaflowglow
 *
 */
public class CommandHelp extends Command {
	
	private static ActionRequirement help = new ActionRequirement((CommandAction)args -> {
		ChatUtils.addChatMessage("Commands: ");
		for (Command command : CommandManager.getCommands()) {
			ChatUtils.addChatMessage("    " + command.getName() + " - ." + command.getName() + " " + command.getUsage());
		}
		return true;
	});
	
	public CommandHelp() {
		super("help", "", help);
	}
	
}

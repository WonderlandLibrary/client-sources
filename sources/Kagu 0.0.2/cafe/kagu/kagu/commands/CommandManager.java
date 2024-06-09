/**
 * 
 */
package cafe.kagu.kagu.commands;

import org.lwjgl.input.Keyboard;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.commands.Command.ActionRequirement;
import cafe.kagu.kagu.commands.impl.CommandBind;
import cafe.kagu.kagu.commands.impl.CommandConfig;
import cafe.kagu.kagu.commands.impl.CommandHelp;
import cafe.kagu.kagu.commands.impl.CommandKeybinds;
import cafe.kagu.kagu.commands.impl.CommandSay;
import cafe.kagu.kagu.commands.impl.CommandSpoofSkin;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventChatSendMessage;
import cafe.kagu.kagu.eventBus.impl.EventKeyUpdate;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

/**
 * @author lavaflowglow
 *
 */
public class CommandManager {
	
	/**
	 * Called when the client starts, creates a new command manager object and hooks it onto the event bus
	 */
	public static void start() {
		Kagu.getEventBus().subscribe(new CommandManager());
	}
	
	private static final Command[] COMMANDS = new Command[] {
			new CommandHelp(),
			new CommandKeybinds(),
			new CommandConfig(),
			new CommandBind(),
			new CommandSay(),
			new CommandSpoofSkin()
	};
	
	/**
	 * Handles chat messages
	 */
	@EventHandler
	public Handler<EventChatSendMessage> chatEventListener = e -> {
		
		// Is the event is post than return, we only want pre events on this
		if (e.isPost()) {
			return;
		}
		
		// If the message starts with a . then it's a command, if not we return
		if (!e.getMessage().startsWith(".")) {
			return;
		}
		
		// Cancel the event
		e.cancel();
		
		// The message split into an array with a space as the delimiter
		String[] args = e.getMessage().split(" ");
		args[0] = args[0].substring(1); // Remove the period from the start of the command name
		
		// Run through all the commands and run any actions it can find
		for (Command command : COMMANDS) {
			
			// Check if this is the command the user typed in chat
			if (!args[0].equalsIgnoreCase(command.getName())) {
				continue;
			}
			
			// Command matches the name of this command, now we have to find all actions that match the args
			for (ActionRequirement actionRequirement : command.getActionRequirements()) {
				
				// Check if the amount of args that the action needs is less than or equal to the length of the args
				if (!(actionRequirement.getRequiredArgs().length <= args.length - 1)) {
					continue;
				}
				
				// No arg comand
				if (actionRequirement.getRequiredArgs().length == 0) {
					String[] commandArgs = new String[args.length - 1 - actionRequirement.getRequiredArgs().length]; // The same as args but without the command or required args
					for (int j = 0; j < args.length - 1 - actionRequirement.getRequiredArgs().length; j++) {
						commandArgs[j] = args[j + 1 + actionRequirement.getRequiredArgs().length];
					}
					if (actionRequirement.getCommandAction().execute(commandArgs)) {
						return;
					}
				}
				
				// Multi arg command
				else for (int i = 0; i < actionRequirement.getRequiredArgs().length; i++) {
					if (!args[i + 1].equalsIgnoreCase(actionRequirement.getRequiredArgs()[i])) {
						break;
					}
					
					// True if all the args matched up, if any were false it would have broken the
					// loop by now
					if (i == actionRequirement.getRequiredArgs().length - 1) {
						String[] commandArgs = new String[args.length - 1 - actionRequirement.getRequiredArgs().length]; // The same as args but without the command or required args
						for (int j = 0; j < args.length - 1 - actionRequirement.getRequiredArgs().length; j++) {
							commandArgs[j] = args[j + 1 + actionRequirement.getRequiredArgs().length];
						}
						if (actionRequirement.getCommandAction().execute(commandArgs)) {
							return;
						}
					}
					
				}

			}
			
			// No command action had the correct usage
			ChatUtils.addChatMessage("Usage: ." + command.getName(), command.getUsage());
			return;
			
		}
		
		// No command has the correct name
		ChatUtils.addChatMessage("Use \".help\" to get a list of client commands");
	};
	
	/**
	 * Used so the chat opens when you click the period key
	 */
	@EventHandler
	public Handler<EventKeyUpdate> onKeyUpdate = e -> {
		if (e.isPre() && Minecraft.getMinecraft().currentScreen == null && e.isPressed() && e.getKeyCode() == Keyboard.KEY_PERIOD) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiChat());
		}
	};

	/**
	 * @return the commands
	 */
	public static Command[] getCommands() {
		return COMMANDS;
	}

}

package xyz.vergoclient.commands.impl;

import xyz.vergoclient.commands.OnCommandInterface;
import xyz.vergoclient.util.main.ChatUtils;

public class CommandHelp implements OnCommandInterface {

	@Override
	public void onCommand(String... args) {
		// Any commands added list them here.
		ChatUtils.addChatMessage("Vergo's Commands", true);
		ChatUtils.addChatMessage(".bind <moduleName> <key> - Bind a module to a key.");
		ChatUtils.addChatMessage(".config <load/save> <configName> - Save or load a config");
		ChatUtils.addChatMessage(".help - Show's all available commands.");


		// Old shitty code
		/*ChatUtils.addChatMessage(" ");
		for (OnCommandInterface command : Vergo.commandManager.commands) {
			ChatStyle style = new ChatStyle();
			style.setChatHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ChatComponentText("Usage: " + command.getUsage())));
			ChatUtils.addChatMessage(CommandManager.prefix + command.getName() + " | " + command.getDescription(), style);
		}
		ChatUtils.addChatMessage(" ");
		ChatUtils.addChatMessage("Hover over the commands to show their usage");*/
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getUsage() {
		return "A list of all valid commands";
	}

	@Override
	public String getDescription() {
		return "help";
	}

}

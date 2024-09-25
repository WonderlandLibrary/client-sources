package none.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.Client;
import none.command.Command;
import none.event.events.EventChat;

public class HelpCommand extends Command{

	@Override
	public String getAlias() {
		return "Help";
	}

	@Override
	public String getDescription() {
		return "Show Command.";
	}

	@Override
	public String getSyntax() {
		return ".Help";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args[0].isEmpty()) {
			for (Command c : Client.instance.commandManager.getCommands()) {
				if (c.getClass().equals(LoginUser.class) || c.getClass().equals(AwakeNgineXE.class)) continue;
					EventChat.addchatmessage(c.getSyntax() + ChatFormatting.GREEN + " " + c.getDescription());
			}
		}else {
			EventChat.addchatmessage(getSyntax());
		}
	}
}

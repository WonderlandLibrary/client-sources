package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import lunadevs.luna.main.Luna;

//Coded by faith.

public class ClearChat extends Command{

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "clearchat";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Clears chat for user.";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "-clearchat";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(Luna.onSendChatMessage(command)) {
			mc.ingameGUI.getChatGUI().clearChatMessages();
		}
		
	}

	
	
}

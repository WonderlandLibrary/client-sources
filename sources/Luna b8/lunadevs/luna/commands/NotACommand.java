package lunadevs.luna.commands;

import com.zCore.Core.zCore;

import lunadevs.luna.command.Command;

public class NotACommand extends Command{

	@Override
	
	public String getAlias() {
		return "";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getSyntax() {
		return "";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		zCore.addErrorChatMessageP("That's not a command, try -help.");
        
	}

}

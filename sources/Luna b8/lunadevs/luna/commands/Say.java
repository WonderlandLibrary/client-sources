package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import lunadevs.luna.main.Luna;

//coded by faith

public class Say extends Command{

	@Override
	public String getAlias() {
		return "say";
	}

	@Override
	public String getDescription() {
		return "obvious :/";
	}

	@Override
	public String getSyntax() {
		return "-say <something>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
	    if (args.length > 1) {
	       Luna.sendChatMessage(command.substring(args[0].length()));
	      } else {
	        Luna.addChatMessage("ERROR! Can't send null messages.");
	      }
	    }
	}

	
	


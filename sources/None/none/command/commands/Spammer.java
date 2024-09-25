package none.command.commands;

import none.command.Command;
import none.module.modules.world.SpammerModule;
import none.utils.ChatUtil;

public class Spammer extends Command{

	@Override
	public String getAlias() {
		return "Spammer";
	}

	@Override
	public String getDescription() {
		return "Set Spammer Message";
	}

	@Override
	public String getSyntax() {
		return ".Spammer <Message>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args[0].isEmpty()) {
			evc(getSyntax());
			return;
		}
		
		if (args.length >= 1) {
           	try{
           		String message ="";
            	for(int i = 0; i < args.length ; i++){
            		String str = args[i];
            		message = message + str + " ";
            	}
            	ChatUtil.printChat("Spammer message set to : §r" + message);         	
            	SpammerModule.MessageToSpammer.setObject(message);
           	}catch(NumberFormatException e){
           			
           	}
           	return;
        }
		evc(getSyntax());
	}

}

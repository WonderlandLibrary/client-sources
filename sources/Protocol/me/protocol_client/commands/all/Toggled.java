package me.protocol_client.commands.all;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;
import me.protocol_client.module.Module;

public class Toggled extends Command{

	@Override
	public String getAlias() {
		return "T";
	}

	@Override
	public String getDescription() {
		return "Toggle modules";
	}

	@Override
	public String getSyntax() {
		return ".t <Mod>";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		for(Module m : Protocol.getModules()){
			if(m.getAlias().equalsIgnoreCase(args[0])){
				m.toggle();
				Wrapper.tellPlayer("§9" + m.getName() + "§7 has been toggled.");
			}
		}
	}

}

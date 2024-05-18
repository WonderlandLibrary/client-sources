package me.protocol_client.commands.all;

import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;

public class VClip extends Command{

	@Override
	public String getAlias() {
		return "VClip";
	}

	@Override
	public String getDescription() {
		return "Teleport yourself vertically.";
	}

	@Override
	public String getSyntax() {
		return ".vclip <Distance>";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		float f = Float.parseFloat(args[0]);
		Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + f, Wrapper.getPlayer().posZ);
		Wrapper.tellPlayer("§7Teleporting you §9" + f + " §7blocks");
		return;
	}

}

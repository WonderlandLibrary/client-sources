package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import net.minecraft.client.Minecraft;

public class CommandVClip extends Command {

	@Override
	public void executeMsg(String[] args) {
		if (args.length == 1) {
			try {
				double amount = Double.parseDouble(args[0]);
				Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + amount,
						Minecraft.thePlayer.posZ);
			} catch (NumberFormatException exception) {
				msg(String.format("\"%s\" is not a valid number!", new Object[] { args[0] }));
			}
			return;
		}
		error("Invalid usage: -vclip <amount>");
	}

	@Override
	public String getName() {
		return "vclip";
	}

	
	
}

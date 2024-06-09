package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;

@Info(name = "help", syntax = { "" }, help = "Displays commands and info about them")
public class HelpCmd extends Cmd {
	@Override
	public void execute(String[] p0) throws Error {
		for (final Cmd cmd : Neptune.getWinter().theCmds.getCmds()) {
			String output = "§o." + cmd.getCmdName() + "§r";
			if (cmd.getSyntax().length != 0) {
				output = output + " " + cmd.getSyntax()[0];
				for (int i = 1; i < this.getSyntax().length; ++i) {
					output = output + "\n    " + cmd.getSyntax()[i];
				}
			}
			String[] split;
			for (int length = (split = output.split("\n")).length, j = 0; j < length; ++j) {
				String line = split[j];
				ChatUtils.sendMessageToPlayer(line + " - " + cmd.getHelp());
			}
		}
	}
}

package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;
import net.minecraft.client.main.neptune.Utils.ModeUtils;

@Info(name = "esp", syntax = { "<players/mobs/animals>" }, help = "Change ESP settings")
public class ESPCmd extends Cmd {
	@Override
	public void execute(final String[] p0) throws Error {
		if (p0.length > 1) {
			this.syntaxError();
		} else if (p0.length == 1) {
			if (p0[0].equalsIgnoreCase("players")) {
				ModeUtils.espP = !ModeUtils.espP;
				ChatUtils.sendMessageToPlayer("Players set to: " + ModeUtils.espP);
			} else if (p0[0].equalsIgnoreCase("mobs")) {
				ModeUtils.espM = !ModeUtils.espM;
				ChatUtils.sendMessageToPlayer("Mobs set to: " + ModeUtils.espM);
			} else if (p0[0].equalsIgnoreCase("animals")) {
				ModeUtils.espA = !ModeUtils.espA;
				ChatUtils.sendMessageToPlayer("Animals set to: " + ModeUtils.espA);
			} else {
				this.syntaxError();
			}
		} else {
			this.syntaxError();
		}
	}
}

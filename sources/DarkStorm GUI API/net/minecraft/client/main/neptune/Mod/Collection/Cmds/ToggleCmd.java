package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;

@Info(name = "t", syntax = { "<mod>" }, help = "Toggles mods")
public class ToggleCmd extends Cmd {

	@Override
	public void execute(String[] p0) throws Error {
		if (p0.length < 1) {
			this.syntaxError();
		} else {
			Mod mod = Neptune.getWinter().theMods.getMod(p0[0]);
			if (mod != null) {
				mod.toggle();
			} else {
				ChatUtils.sendMessageToPlayer("Mod not found! (Hint: Dont include spaces or '.')");
			}
		}
	}

}

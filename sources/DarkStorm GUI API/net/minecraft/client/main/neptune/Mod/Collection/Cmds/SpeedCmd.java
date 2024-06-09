package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;
import net.minecraft.client.main.neptune.Utils.ModeUtils;

@Info(name = "speed", syntax = { "<yport/bhop/ground>" }, help = "Change your speed mode.")
public class SpeedCmd extends Cmd {
	@Override
	public void execute(String[] p0) throws Error {
		if (p0.length > 1) {
			this.syntaxError();
		} else if (p0.length == 1) {
			if (p0[0].equalsIgnoreCase("yport")) {
				ModeUtils.speedMode = "yport";
				ChatUtils.sendMessageToPlayer("Speed mode changed to: §aY-Port!");
			} else if (p0[0].equalsIgnoreCase("bhop")) {
				ChatUtils.sendMessageToPlayer("Speed mode changed to: §aB-hop!");
				ModeUtils.speedMode = "bhop";
			}else if (p0[0].equalsIgnoreCase("ground")) {
				ChatUtils.sendMessageToPlayer("Speed mode changed to: §aGround!");
				ModeUtils.speedMode = "ground";
			} else {
				this.syntaxError();
			}
		} else {
			this.syntaxError();
		}
	}
}

package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;
import net.minecraft.client.main.neptune.Utils.ModeUtils;

@Info(name = "phase", syntax = { "<latest/new/spider/vanilla>" }, help = "Switch phase modes")
public class PhaseCmd extends Cmd {
	@Override
	public void execute(String[] p0) throws Error {
		if (p0.length > 1) {
			this.syntaxError();
		} else if (p0.length == 1) {
			if (p0[0].equalsIgnoreCase("latest")) {
				ModeUtils.phaseMode = "latest";
				ChatUtils.sendMessageToPlayer("Phase mode set to: Latest");
			} else if (p0[0].equalsIgnoreCase("new")) {
				ModeUtils.phaseMode = "new";
				ChatUtils.sendMessageToPlayer("Phase mode set to: New");
			} else if (p0[0].equalsIgnoreCase("spider")) {
				ModeUtils.phaseMode = "spider";
				ChatUtils.sendMessageToPlayer("Phase mode set to: Spider");
			} else if (p0[0].equalsIgnoreCase("vanilla")) {
				ModeUtils.phaseMode = "vanilla";
				ChatUtils.sendMessageToPlayer("Phase mode set to: Vanilla");
			} else {
				this.syntaxError();
			}
		} else {
			this.syntaxError();
		}
	}
}

package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;

@Info(name = "si", syntax = {}, help = "Displays current server info")
public class ServerInfoCmd extends Cmd {
	@Override
	public void execute(String[] p0) throws Error {
		if (!Minecraft.getMinecraft().isSingleplayer()) {
			ChatUtils.sendMessageToPlayer("§6IP:§r " + Minecraft.getMinecraft().getCurrentServerData().serverIP);
			ChatUtils
					.sendMessageToPlayer("§6Version: §r" + Minecraft.getMinecraft().getCurrentServerData().gameVersion);
		} else {
			ChatUtils.sendMessageToPlayer("Multiplayer only!");
		}
	}
}

package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

@Info(name = "connect", syntax = { "<ip:port>" }, help = "Connects you to a server")
public class ConnectCmd extends Cmd {
	@Override
	public void execute(String[] p0) throws Error {
		if (p0.length > 0) {
			final ServerData theServer = new ServerData("server", p0[0]);
			if (!this.mc.isSingleplayer()) {
				this.mc.theWorld.sendQuittingDisconnectingPacket();
			}
			this.mc.displayGuiScreen(new GuiConnecting(null, this.mc, theServer));
		} else {
			this.syntaxError();
		}
	}
}

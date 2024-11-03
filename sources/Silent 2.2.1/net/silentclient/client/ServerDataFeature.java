package net.silentclient.client;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ResourceLocation;

public class ServerDataFeature extends ServerData {
	public static final ResourceLocation STAR_ICON = new ResourceLocation("silentclient/icons/star.png");
	public ServerDataFeature(String serverName, String serverIp) {
		super(serverName, serverIp, false);
	}

	public void resetData() {
		this.field_78841_f = false;
		this.pingToServer = 0;
		this.populationInfo = null;
		this.playerList = null;
		this.serverMOTD = null;
	}
}

package net.silentclient.client.mods.util;

public enum PingSource {
	AUTO,
	MULTIPLAYER_SCREEN,
	TAB_LIST;
	
	public PingSource resolve() {
		if(this == AUTO) {
			if(Server.isHypixel()) {
				return MULTIPLAYER_SCREEN;
			}

			return MULTIPLAYER_SCREEN;
		}

		return this;
	}
}

package net.silentclient.client.mixin.wrappers;

import net.minecraft.client.multiplayer.ServerData;

public class ServerDataWrapper {
    private final ServerData serverData;


    public ServerDataWrapper(ServerData serverData) {
        this.serverData = serverData;
    }

    public ServerData getServerData() {
        return serverData;
    }
}

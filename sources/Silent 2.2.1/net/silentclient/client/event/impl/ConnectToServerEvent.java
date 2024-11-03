package net.silentclient.client.event.impl;

import net.minecraft.client.multiplayer.ServerData;
import net.silentclient.client.event.Event;

public class ConnectToServerEvent extends Event {
    private final ServerData serverData;

    public ConnectToServerEvent(ServerData serverData) {
        this.serverData = serverData;
    }

    public ServerData getServerData() {
        return serverData;
    }
}

package xyz.mathax.mathaxclient.mixininterface;

import net.minecraft.client.network.ServerInfo;

public interface IMultiplayerScreen {
    void connectToServer(ServerInfo server);
}
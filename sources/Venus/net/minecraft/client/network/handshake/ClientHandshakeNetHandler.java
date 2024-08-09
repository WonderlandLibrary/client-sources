/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network.handshake;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.IHandshakeNetHandler;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.ServerLoginNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;

public class ClientHandshakeNetHandler
implements IHandshakeNetHandler {
    private final MinecraftServer server;
    private final NetworkManager networkManager;

    public ClientHandshakeNetHandler(MinecraftServer minecraftServer, NetworkManager networkManager) {
        this.server = minecraftServer;
        this.networkManager = networkManager;
    }

    @Override
    public void processHandshake(CHandshakePacket cHandshakePacket) {
        this.networkManager.setConnectionState(cHandshakePacket.getRequestedState());
        this.networkManager.setNetHandler(new ServerLoginNetHandler(this.server, this.networkManager));
    }

    @Override
    public void onDisconnect(ITextComponent iTextComponent) {
    }

    @Override
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }
}


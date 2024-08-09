/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.status.IServerStatusNetHandler;
import net.minecraft.network.status.client.CPingPacket;
import net.minecraft.network.status.client.CServerQueryPacket;
import net.minecraft.network.status.server.SPongPacket;
import net.minecraft.network.status.server.SServerInfoPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ServerStatusNetHandler
implements IServerStatusNetHandler {
    private static final ITextComponent EXIT_MESSAGE = new TranslationTextComponent("multiplayer.status.request_handled");
    private final MinecraftServer server;
    private final NetworkManager networkManager;
    private boolean handled;

    public ServerStatusNetHandler(MinecraftServer minecraftServer, NetworkManager networkManager) {
        this.server = minecraftServer;
        this.networkManager = networkManager;
    }

    @Override
    public void onDisconnect(ITextComponent iTextComponent) {
    }

    @Override
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    @Override
    public void processServerQuery(CServerQueryPacket cServerQueryPacket) {
        if (this.handled) {
            this.networkManager.closeChannel(EXIT_MESSAGE);
        } else {
            this.handled = true;
            this.networkManager.sendPacket(new SServerInfoPacket(this.server.getServerStatusResponse()));
        }
    }

    @Override
    public void processPing(CPingPacket cPingPacket) {
        this.networkManager.sendPacket(new SPongPacket(cPingPacket.getClientTime()));
        this.networkManager.closeChannel(EXIT_MESSAGE);
    }
}


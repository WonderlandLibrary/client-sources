package net.minecraft.server.network;

import net.minecraft.network.status.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.status.server.*;

public class NetHandlerStatusServer implements INetHandlerStatusServer
{
    private final MinecraftServer server;
    private final NetworkManager networkManager;
    
    public NetHandlerStatusServer(final MinecraftServer serverIn, final NetworkManager netManager) {
        this.server = serverIn;
        this.networkManager = netManager;
    }
    
    @Override
    public void onDisconnect(final IChatComponent reason) {
    }
    
    @Override
    public void processServerQuery(final C00PacketServerQuery packetIn) {
        this.networkManager.sendPacket(new S00PacketServerInfo(this.server.getServerStatusResponse()));
    }
    
    @Override
    public void processPing(final C01PacketPing packetIn) {
        this.networkManager.sendPacket(new S01PacketPong(packetIn.getClientTime()));
    }
}

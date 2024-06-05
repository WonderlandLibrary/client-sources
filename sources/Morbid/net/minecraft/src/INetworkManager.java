package net.minecraft.src;

import java.net.*;

public interface INetworkManager
{
    void setNetHandler(final NetHandler p0);
    
    void addToSendQueue(final Packet p0);
    
    void wakeThreads();
    
    void processReadPackets();
    
    SocketAddress getSocketAddress();
    
    void serverShutdown();
    
    int packetSize();
    
    void networkShutdown(final String p0, final Object... p1);
    
    void closeConnections();
}

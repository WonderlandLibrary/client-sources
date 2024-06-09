// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.IrcPlayer;

public interface IrcClient
{
    IrcApiManager getApiManager();
    
    void connect(final String p0);
    
    void disconnect();
    
    void sendCustomData(final String p0, final byte[] p1);
    
    void sendCustomData(final String p0, final byte[] p1, final IrcPlayer p2);
    
    void setIngameName(final String p0);
    
    void sendChatMessage(final String p0);
    
    void sendLocalChatMessage(final String p0);
    
    void sendWhisperMessage(final String p0, final String p1);
    
    void executeCommand(final String p0);
    
    void setMcServerIp(final String p0);
    
    void leaveMcServer(final String p0);
    
    String getUuid();
    
    ClientType getType();
    
    String getIngameName();
    
    String getMcServerIp();
    
    IrcRank getRank();
    
    boolean isForcedDisconnect();
    
    void setExtra(final String p0);
    
    String getExtra();
    
    String getIrcVersion();
    
    String getClientVersion();
    
    int getProtocolVersion();
    
    String getNickname();
    
    void setCape(final String p0);
}

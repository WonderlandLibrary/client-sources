// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.bots;

import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.SessionFactory;
import org.spacehq.packetlib.packet.PacketProtocol;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;

public class Bot
{
    private String username;
    private String password;
    private final Client client;
    
    public Bot(final String username, final String password, final String host, final int port) throws Exception {
        this.username = username;
        this.password = password;
        this.client = new Client(host, port, (PacketProtocol)new MinecraftProtocol(username, password, false), (SessionFactory)new TcpSessionFactory());
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public Session getSession() {
        return this.client.getSession();
    }
    
    public String getHost() {
        return this.client.getHost();
    }
    
    public void addListener(final SessionListener listener) {
        this.client.getSession().addListener(listener);
    }
}

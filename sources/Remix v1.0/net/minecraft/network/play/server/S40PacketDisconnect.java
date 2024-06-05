package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S40PacketDisconnect implements Packet
{
    private IChatComponent reason;
    
    public S40PacketDisconnect() {
    }
    
    public S40PacketDisconnect(final IChatComponent reasonIn) {
        this.reason = reasonIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.reason = data.readChatComponent();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeChatComponent(this.reason);
    }
    
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleDisconnect(this);
    }
    
    public IChatComponent func_149165_c() {
        return this.reason;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}

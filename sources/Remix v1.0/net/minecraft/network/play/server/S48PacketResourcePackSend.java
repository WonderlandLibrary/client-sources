package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S48PacketResourcePackSend implements Packet
{
    private String url;
    private String hash;
    
    public S48PacketResourcePackSend() {
    }
    
    public S48PacketResourcePackSend(final String url, final String hash) {
        this.url = url;
        this.hash = hash;
        if (hash.length() > 40) {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.url = data.readStringFromBuffer(32767);
        this.hash = data.readStringFromBuffer(40);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeString(this.url);
        data.writeString(this.hash);
    }
    
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.func_175095_a(this);
    }
    
    public String func_179783_a() {
        return this.url;
    }
    
    public String func_179784_b() {
        return this.hash;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}

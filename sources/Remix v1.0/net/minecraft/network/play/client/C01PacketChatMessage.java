package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C01PacketChatMessage implements Packet
{
    private String message;
    
    public C01PacketChatMessage() {
    }
    
    public C01PacketChatMessage(String messageIn) {
        if (messageIn.length() > 100) {
            messageIn = messageIn.substring(0, 100);
        }
        this.message = messageIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.message = data.readStringFromBuffer(100);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeString(this.message);
    }
    
    public void func_180757_a(final INetHandlerPlayServer p_180757_1_) {
        p_180757_1_.processChatMessage(this);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180757_a((INetHandlerPlayServer)handler);
    }
}

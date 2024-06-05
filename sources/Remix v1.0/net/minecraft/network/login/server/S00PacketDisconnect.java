package net.minecraft.network.login.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S00PacketDisconnect implements Packet
{
    private IChatComponent reason;
    
    public S00PacketDisconnect() {
    }
    
    public S00PacketDisconnect(final IChatComponent reasonIn) {
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
    
    public void func_180772_a(final INetHandlerLoginClient p_180772_1_) {
        p_180772_1_.handleDisconnect(this);
    }
    
    public IChatComponent func_149603_c() {
        return this.reason;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180772_a((INetHandlerLoginClient)handler);
    }
}

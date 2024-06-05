package net.minecraft.client.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.server.network.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer
{
    private final MinecraftServer field_147385_a;
    private final NetworkManager field_147384_b;
    
    public NetHandlerHandshakeMemory(final MinecraftServer p_i45287_1_, final NetworkManager p_i45287_2_) {
        this.field_147385_a = p_i45287_1_;
        this.field_147384_b = p_i45287_2_;
    }
    
    @Override
    public void processHandshake(final C00Handshake packetIn) {
        this.field_147384_b.setConnectionState(packetIn.getRequestedState());
        this.field_147384_b.setNetHandler(new NetHandlerLoginServer(this.field_147385_a, this.field_147384_b));
    }
    
    @Override
    public void onDisconnect(final IChatComponent reason) {
    }
}

package net.minecraft.server.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.login.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer
{
    private final MinecraftServer server;
    private final NetworkManager networkManager;
    
    public NetHandlerHandshakeTCP(final MinecraftServer serverIn, final NetworkManager netManager) {
        this.server = serverIn;
        this.networkManager = netManager;
    }
    
    @Override
    public void processHandshake(final C00Handshake packetIn) {
        switch (SwitchEnumConnectionState.VALUES[packetIn.getRequestedState().ordinal()]) {
            case 1: {
                this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
                if (packetIn.getProtocolVersion() > 47) {
                    final ChatComponentText var2 = new ChatComponentText("Outdated server! I'm still on 1.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(var2));
                    this.networkManager.closeChannel(var2);
                    break;
                }
                if (packetIn.getProtocolVersion() < 47) {
                    final ChatComponentText var2 = new ChatComponentText("Outdated client! Please use 1.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(var2));
                    this.networkManager.closeChannel(var2);
                    break;
                }
                this.networkManager.setNetHandler(new NetHandlerLoginServer(this.server, this.networkManager));
                break;
            }
            case 2: {
                this.networkManager.setConnectionState(EnumConnectionState.STATUS);
                this.networkManager.setNetHandler(new NetHandlerStatusServer(this.server, this.networkManager));
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid intention " + packetIn.getRequestedState());
            }
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent reason) {
    }
    
    static final class SwitchEnumConnectionState
    {
        static final int[] VALUES;
        
        static {
            VALUES = new int[EnumConnectionState.values().length];
            try {
                SwitchEnumConnectionState.VALUES[EnumConnectionState.LOGIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumConnectionState.VALUES[EnumConnectionState.STATUS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}

package net.minecraft.client.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.server.network.*;
import net.minecraft.network.*;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer
{
    private final NetworkManager networkManager;
    private final MinecraftServer mcServer;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    @Override
    public void processHandshake(final C00Handshake c00Handshake) {
        this.networkManager.setConnectionState(c00Handshake.getRequestedState());
        this.networkManager.setNetHandler(new NetHandlerLoginServer(this.mcServer, this.networkManager));
    }
    
    public NetHandlerHandshakeMemory(final MinecraftServer mcServer, final NetworkManager networkManager) {
        this.mcServer = mcServer;
        this.networkManager = networkManager;
    }
}

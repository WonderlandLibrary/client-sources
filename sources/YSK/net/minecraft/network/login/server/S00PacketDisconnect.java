package net.minecraft.network.login.server;

import net.minecraft.network.login.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S00PacketDisconnect implements Packet<INetHandlerLoginClient>
{
    private IChatComponent reason;
    
    public S00PacketDisconnect() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.reason = packetBuffer.readChatComponent();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleDisconnect(this);
    }
    
    public IChatComponent func_149603_c() {
        return this.reason;
    }
    
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S00PacketDisconnect(final IChatComponent reason) {
        this.reason = reason;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.reason);
    }
}

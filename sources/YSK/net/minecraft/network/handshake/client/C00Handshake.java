package net.minecraft.network.handshake.client;

import net.minecraft.network.handshake.*;
import net.minecraft.network.*;
import java.io.*;

public class C00Handshake implements Packet<INetHandlerHandshakeServer>
{
    private EnumConnectionState requestedState;
    private int port;
    private int protocolVersion;
    private String ip;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerHandshakeServer)netHandler);
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EnumConnectionState getRequestedState() {
        return this.requestedState;
    }
    
    @Override
    public void processPacket(final INetHandlerHandshakeServer netHandlerHandshakeServer) {
        netHandlerHandshakeServer.processHandshake(this);
    }
    
    public C00Handshake() {
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.protocolVersion);
        packetBuffer.writeString(this.ip);
        packetBuffer.writeShort(this.port);
        packetBuffer.writeVarIntToBuffer(this.requestedState.getId());
    }
    
    public C00Handshake(final int protocolVersion, final String ip, final int port, final EnumConnectionState requestedState) {
        this.protocolVersion = protocolVersion;
        this.ip = ip;
        this.port = port;
        this.requestedState = requestedState;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.protocolVersion = packetBuffer.readVarIntFromBuffer();
        this.ip = packetBuffer.readStringFromBuffer(119 + 75 - 191 + 252);
        this.port = packetBuffer.readUnsignedShort();
        this.requestedState = EnumConnectionState.getById(packetBuffer.readVarIntFromBuffer());
    }
}

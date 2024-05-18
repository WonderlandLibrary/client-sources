package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S3APacketTabComplete implements Packet<INetHandlerPlayClient>
{
    private String[] matches;
    
    public String[] func_149630_c() {
        return this.matches;
    }
    
    public S3APacketTabComplete() {
    }
    
    public S3APacketTabComplete(final String[] matches) {
        this.matches = matches;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTabComplete(this);
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.matches = new String[packetBuffer.readVarIntFromBuffer()];
        int i = "".length();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (i < this.matches.length) {
            this.matches[i] = packetBuffer.readStringFromBuffer(17608 + 30329 - 23397 + 8227);
            ++i;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.matches.length);
        final String[] matches;
        final int length = (matches = this.matches).length;
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < length) {
            packetBuffer.writeString(matches[i]);
            ++i;
        }
    }
}

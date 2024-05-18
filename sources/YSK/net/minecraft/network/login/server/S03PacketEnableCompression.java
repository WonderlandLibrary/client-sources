package net.minecraft.network.login.server;

import net.minecraft.network.login.*;
import java.io.*;
import net.minecraft.network.*;

public class S03PacketEnableCompression implements Packet<INetHandlerLoginClient>
{
    private int compressionTreshold;
    
    public S03PacketEnableCompression() {
    }
    
    public S03PacketEnableCompression(final int compressionTreshold) {
        this.compressionTreshold = compressionTreshold;
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.compressionTreshold = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.compressionTreshold);
    }
    
    @Override
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleEnableCompression(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    public int getCompressionTreshold() {
        return this.compressionTreshold;
    }
}

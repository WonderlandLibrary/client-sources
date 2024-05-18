package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.network.*;

public class S3FPacketCustomPayload implements Packet<INetHandlerPlayClient>
{
    private PacketBuffer data;
    private String channel;
    private static final String[] I;
    
    public PacketBuffer getBufferData() {
        return this.data;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.channel);
        packetBuffer.writeBytes(this.data);
    }
    
    public S3FPacketCustomPayload(final String channel, final PacketBuffer data) {
        this.channel = channel;
        this.data = data;
        if (data.writerIndex() > 742259 + 74500 - 463004 + 694821) {
            throw new IllegalArgumentException(S3FPacketCustomPayload.I["".length()]);
        }
    }
    
    public S3FPacketCustomPayload() {
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCustomPayload(this);
    }
    
    static {
        I();
    }
    
    public String getChannelName() {
        return this.channel;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readStringFromBuffer(0x48 ^ 0x5C);
        final int readableBytes = packetBuffer.readableBytes();
        if (readableBytes < 0 || readableBytes > 588009 + 910163 - 872898 + 423302) {
            throw new IOException(S3FPacketCustomPayload.I[" ".length()]);
        }
        this.data = new PacketBuffer(packetBuffer.readBytes(readableBytes));
        "".length();
        if (3 < 3) {
            throw null;
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001d%\u0000\u000f\u0003, Y\u000e\r4d\u0017\f\u0018m&\u001cC\u0000,6\u001e\u0006\u001em0\u0011\u0002\u0002muIWTxsOC\u000e40\u001c\u0010", "MDycl");
        S3FPacketCustomPayload.I[" ".length()] = I("75?/6\u00060f.8\u001et(,-G6#c5\u0006&!&+G .\"7GevwaRcpc;\u001e #0", "gTFCY");
    }
}

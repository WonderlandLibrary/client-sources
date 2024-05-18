package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import io.netty.buffer.*;
import net.minecraft.network.*;

public class C17PacketCustomPayload implements Packet<INetHandlerPlayServer>
{
    private static final String[] I;
    private PacketBuffer data;
    private String channel;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\"\b\f\u000e\"\u0013\rU\u000f,\u000bI\u001b\r9R\u000b\u0010B!\u0013\u001b\u0012\u0007?R\u001d\u001d\u0003#RZGU{EI\u0017\u001b9\u0017\u001a", "riubM");
        C17PacketCustomPayload.I[" ".length()] = I("\n\r\u0013*<;\bJ+2#L\u0004)'z\u000e\u000ff?;\u001e\r#!z\u0018\u0002'=z_XqemL\b?'?\u001f", "ZljFS");
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readStringFromBuffer(0x2C ^ 0x38);
        final int readableBytes = packetBuffer.readableBytes();
        if (readableBytes < 0 || readableBytes > 17352 + 23243 - 21992 + 14164) {
            throw new IOException(C17PacketCustomPayload.I[" ".length()]);
        }
        this.data = new PacketBuffer(packetBuffer.readBytes(readableBytes));
        "".length();
        if (2 != 2) {
            throw null;
        }
    }
    
    public C17PacketCustomPayload() {
    }
    
    public PacketBuffer getBufferData() {
        return this.data;
    }
    
    public C17PacketCustomPayload(final String channel, final PacketBuffer data) {
        this.channel = channel;
        this.data = data;
        if (data.writerIndex() > 32253 + 19398 - 24763 + 5879) {
            throw new IllegalArgumentException(C17PacketCustomPayload.I["".length()]);
        }
    }
    
    static {
        I();
    }
    
    public String getChannelName() {
        return this.channel;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processVanilla250Packet(this);
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.channel);
        packetBuffer.writeBytes(this.data);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
}

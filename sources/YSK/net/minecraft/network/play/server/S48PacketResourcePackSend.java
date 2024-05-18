package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S48PacketResourcePackSend implements Packet<INetHandlerPlayClient>
{
    private static final String[] I;
    private String hash;
    private String url;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u000f,\u0006)Q.>U5\u001e(m\u0019.\u001f m],\u0010?mAq]g:\u00142Q", "GMuAq");
        S48PacketResourcePackSend.I[" ".length()] = I("A", "hxcZZ");
    }
    
    static {
        I();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.url);
        packetBuffer.writeString(this.hash);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.url = packetBuffer.readStringFromBuffer(27428 + 24760 - 35456 + 16035);
        this.hash = packetBuffer.readStringFromBuffer(0x4B ^ 0x63);
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleResourcePack(this);
    }
    
    public S48PacketResourcePackSend(final String url, final String hash) {
        this.url = url;
        this.hash = hash;
        if (hash.length() > (0x67 ^ 0x4F)) {
            throw new IllegalArgumentException(S48PacketResourcePackSend.I["".length()] + hash.length() + S48PacketResourcePackSend.I[" ".length()]);
        }
    }
    
    public String getURL() {
        return this.url;
    }
    
    public S48PacketResourcePackSend() {
    }
    
    public String getHash() {
        return this.hash;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}

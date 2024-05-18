package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C19PacketResourcePackStatus implements Packet<INetHandlerPlayServer>
{
    private String hash;
    private Action status;
    
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public C19PacketResourcePackStatus() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.hash);
        packetBuffer.writeEnumValue(this.status);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.handleResourcePackStatus(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C19PacketResourcePackStatus(String substring, final Action status) {
        if (substring.length() > (0x8B ^ 0xA3)) {
            substring = substring.substring("".length(), 0x21 ^ 0x9);
        }
        this.hash = substring;
        this.status = status;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.hash = packetBuffer.readStringFromBuffer(0x6F ^ 0x47);
        this.status = packetBuffer.readEnumValue(Action.class);
    }
    
    public enum Action
    {
        FAILED_DOWNLOAD(Action.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
        ACCEPTED(Action.I["   ".length()], "   ".length()), 
        DECLINED(Action.I[" ".length()], " ".length()), 
        SUCCESSFULLY_LOADED(Action.I["".length()], "".length());
        
        private static final Action[] ENUM$VALUES;
        
        static {
            I();
            final Action[] enum$VALUES = new Action[0x6A ^ 0x6E];
            enum$VALUES["".length()] = Action.SUCCESSFULLY_LOADED;
            enum$VALUES[" ".length()] = Action.DECLINED;
            enum$VALUES["  ".length()] = Action.FAILED_DOWNLOAD;
            enum$VALUES["   ".length()] = Action.ACCEPTED;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0x40 ^ 0x44])["".length()] = I("\u0015=\u000b\"(\u0015;\u000e4!\n1\u0017-\"\u0007,\r%", "FhHam");
            Action.I[" ".length()] = I("\u001e\n+.3\u0014\n,", "ZOhbz");
            Action.I["  ".length()] = I("\"\"*?\r <'<\u001f*/,2\f", "dccsH");
            Action.I["   ".length()] = I("\u0005\r\b\u001f\u0006\u0010\u000b\u000f", "DNKZV");
        }
        
        private Action(final String s, final int n) {
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
                if (3 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}

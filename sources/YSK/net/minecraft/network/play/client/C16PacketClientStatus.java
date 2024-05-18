package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class C16PacketClientStatus implements Packet<INetHandlerPlayServer>
{
    private EnumState status;
    
    public C16PacketClientStatus(final EnumState status) {
        this.status = status;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClientStatus(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public EnumState getStatus() {
        return this.status;
    }
    
    public C16PacketClientStatus() {
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.status = packetBuffer.readEnumValue(EnumState.class);
    }
    
    public enum EnumState
    {
        private static final EnumState[] ENUM$VALUES;
        
        PERFORM_RESPAWN(EnumState.I["".length()], "".length());
        
        private static final String[] I;
        
        REQUEST_STATS(EnumState.I[" ".length()], " ".length()), 
        OPEN_INVENTORY_ACHIEVEMENT(EnumState.I["  ".length()], "  ".length());
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u0015+(\u000f\u0000\u0017#%\u001b\n\u0016>;\u001e\u0001", "EnzIO");
            EnumState.I[" ".length()] = I("\u00155\"\u0006=\u0014$,\u0000,\u0006$ ", "GpsSx");
            EnumState.I["  ".length()] = I("\r\u0017\u001f\"'\u000b\t\f)6\u0016\b\b5'\u0003\u0004\u0012%=\u0014\u0002\u0017)6\u0016", "BGZlx");
        }
        
        static {
            I();
            final EnumState[] enum$VALUES = new EnumState["   ".length()];
            enum$VALUES["".length()] = EnumState.PERFORM_RESPAWN;
            enum$VALUES[" ".length()] = EnumState.REQUEST_STATS;
            enum$VALUES["  ".length()] = EnumState.OPEN_INVENTORY_ACHIEVEMENT;
            ENUM$VALUES = enum$VALUES;
        }
        
        private EnumState(final String s, final int n) {
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
                if (0 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}

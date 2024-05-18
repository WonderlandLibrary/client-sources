package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2BPacketChangeGameState implements Packet<INetHandlerPlayClient>
{
    public static final String[] MESSAGE_NAMES;
    private int state;
    private static final String[] I;
    private float field_149141_c;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.state);
        packetBuffer.writeFloat(this.field_149141_c);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChangeGameState(this);
    }
    
    static {
        I();
        final String[] message_NAMES = new String[" ".length()];
        message_NAMES["".length()] = S2BPacketChangeGameState.I["".length()];
        MESSAGE_NAMES = message_NAMES;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.state = packetBuffer.readUnsignedByte();
        this.field_149141_c = packetBuffer.readFloat();
    }
    
    public S2BPacketChangeGameState() {
    }
    
    public int getGameState() {
        return this.state;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S2BPacketChangeGameState(final int state, final float field_149141_c) {
        this.state = state;
        this.field_149141_c = field_149141_c;
    }
    
    public float func_149137_d() {
        return this.field_149141_c;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("%+\u0016\u0016g3'\u001e]'>6,\u0012%8&", "QBzsI");
    }
}

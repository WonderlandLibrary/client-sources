package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.*;
import java.io.*;

public class S3DPacketDisplayScoreboard implements Packet<INetHandlerPlayClient>
{
    private int position;
    private String scoreName;
    private static final String[] I;
    
    public S3DPacketDisplayScoreboard() {
    }
    
    static {
        I();
    }
    
    public S3DPacketDisplayScoreboard(final int position, final ScoreObjective scoreObjective) {
        this.position = position;
        if (scoreObjective == null) {
            this.scoreName = S3DPacketDisplayScoreboard.I["".length()];
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            this.scoreName = scoreObjective.getName();
        }
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDisplayScoreboard(this);
    }
    
    public String func_149370_d() {
        return this.scoreName;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int func_149371_c() {
        return this.position;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.position);
        packetBuffer.writeString(this.scoreName);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("", "PFQAy");
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.position = packetBuffer.readByte();
        this.scoreName = packetBuffer.readStringFromBuffer(0x6D ^ 0x7D);
    }
}

package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.*;

public class S3BPacketScoreboardObjective implements Packet<INetHandlerPlayClient>
{
    private String objectiveValue;
    private int field_149342_c;
    private String objectiveName;
    private IScoreObjectiveCriteria.EnumRenderType type;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.objectiveName = packetBuffer.readStringFromBuffer(0x9 ^ 0x19);
        this.field_149342_c = packetBuffer.readByte();
        if (this.field_149342_c == 0 || this.field_149342_c == "  ".length()) {
            this.objectiveValue = packetBuffer.readStringFromBuffer(0xB1 ^ 0x91);
            this.type = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(packetBuffer.readStringFromBuffer(0x0 ^ 0x10));
        }
    }
    
    public S3BPacketScoreboardObjective(final ScoreObjective scoreObjective, final int field_149342_c) {
        this.objectiveName = scoreObjective.getName();
        this.objectiveValue = scoreObjective.getDisplayName();
        this.type = scoreObjective.getCriteria().getRenderType();
        this.field_149342_c = field_149342_c;
    }
    
    public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
        return this.type;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.objectiveName);
        packetBuffer.writeByte(this.field_149342_c);
        if (this.field_149342_c == 0 || this.field_149342_c == "  ".length()) {
            packetBuffer.writeString(this.objectiveValue);
            packetBuffer.writeString(this.type.func_178796_a());
        }
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleScoreboardObjective(this);
    }
    
    public S3BPacketScoreboardObjective() {
    }
    
    public String func_149337_d() {
        return this.objectiveValue;
    }
    
    public int func_149338_e() {
        return this.field_149342_c;
    }
    
    public String func_149339_c() {
        return this.objectiveName;
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}

package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S1FPacketSetExperience implements Packet<INetHandlerPlayClient>
{
    private int totalExperience;
    private int level;
    private float field_149401_a;
    
    public S1FPacketSetExperience(final float field_149401_a, final int totalExperience, final int level) {
        this.field_149401_a = field_149401_a;
        this.totalExperience = totalExperience;
        this.level = level;
    }
    
    public int getTotalExperience() {
        return this.totalExperience;
    }
    
    public float func_149397_c() {
        return this.field_149401_a;
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSetExperience(this);
    }
    
    public S1FPacketSetExperience() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149401_a = packetBuffer.readFloat();
        this.level = packetBuffer.readVarIntFromBuffer();
        this.totalExperience = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.field_149401_a);
        packetBuffer.writeVarIntToBuffer(this.level);
        packetBuffer.writeVarIntToBuffer(this.totalExperience);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getLevel() {
        return this.level;
    }
}

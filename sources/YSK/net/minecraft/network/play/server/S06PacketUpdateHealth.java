package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S06PacketUpdateHealth implements Packet<INetHandlerPlayClient>
{
    private float health;
    private int foodLevel;
    private float saturationLevel;
    
    public float getHealth() {
        return this.health;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateHealth(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.health);
        packetBuffer.writeVarIntToBuffer(this.foodLevel);
        packetBuffer.writeFloat(this.saturationLevel);
    }
    
    public S06PacketUpdateHealth() {
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
            if (1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.health = packetBuffer.readFloat();
        this.foodLevel = packetBuffer.readVarIntFromBuffer();
        this.saturationLevel = packetBuffer.readFloat();
    }
    
    public S06PacketUpdateHealth(final float health, final int foodLevel, final float saturationLevel) {
        this.health = health;
        this.foodLevel = foodLevel;
        this.saturationLevel = saturationLevel;
    }
    
    public int getFoodLevel() {
        return this.foodLevel;
    }
    
    public float getSaturationLevel() {
        return this.saturationLevel;
    }
}

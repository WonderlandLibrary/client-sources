package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.potion.*;

public class S1DPacketEntityEffect implements Packet<INetHandlerPlayClient>
{
    private byte amplifier;
    private int duration;
    private byte hideParticles;
    private int entityId;
    private byte effectId;
    
    public int getDuration() {
        return this.duration;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.effectId = packetBuffer.readByte();
        this.amplifier = packetBuffer.readByte();
        this.duration = packetBuffer.readVarIntFromBuffer();
        this.hideParticles = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.effectId);
        packetBuffer.writeByte(this.amplifier);
        packetBuffer.writeVarIntToBuffer(this.duration);
        packetBuffer.writeByte(this.hideParticles);
    }
    
    public byte getAmplifier() {
        return this.amplifier;
    }
    
    public boolean func_149429_c() {
        if (this.duration == 31606 + 1537 - 21507 + 21131) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public S1DPacketEntityEffect(final int entityId, final PotionEffect potionEffect) {
        this.entityId = entityId;
        this.effectId = (byte)(potionEffect.getPotionID() & 88 + 232 - 211 + 146);
        this.amplifier = (byte)(potionEffect.getAmplifier() & 89 + 19 + 71 + 76);
        if (potionEffect.getDuration() > 4349 + 21007 - 15169 + 22580) {
            this.duration = 30600 + 13334 - 33813 + 22646;
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            this.duration = potionEffect.getDuration();
        }
        int n;
        if (potionEffect.getIsShowParticles()) {
            n = " ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        this.hideParticles = (byte)n;
    }
    
    public S1DPacketEntityEffect() {
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public byte getEffectId() {
        return this.effectId;
    }
    
    public boolean func_179707_f() {
        if (this.hideParticles != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityEffect(this);
    }
}

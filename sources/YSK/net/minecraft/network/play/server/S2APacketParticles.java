package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.io.*;

public class S2APacketParticles implements Packet<INetHandlerPlayClient>
{
    private int particleCount;
    private boolean longDistance;
    private float xCoord;
    private float yCoord;
    private int[] particleArguments;
    private EnumParticleTypes particleType;
    private float zCoord;
    private float particleSpeed;
    private float yOffset;
    private float zOffset;
    private float xOffset;
    
    public float getParticleSpeed() {
        return this.particleSpeed;
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public double getZCoordinate() {
        return this.zCoord;
    }
    
    public int[] getParticleArgs() {
        return this.particleArguments;
    }
    
    public float getXOffset() {
        return this.xOffset;
    }
    
    public EnumParticleTypes getParticleType() {
        return this.particleType;
    }
    
    public double getXCoordinate() {
        return this.xCoord;
    }
    
    public S2APacketParticles() {
    }
    
    public int getParticleCount() {
        return this.particleCount;
    }
    
    public boolean isLongDistance() {
        return this.longDistance;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleParticles(this);
    }
    
    public S2APacketParticles(final EnumParticleTypes particleType, final boolean longDistance, final float xCoord, final float yCoord, final float zCoord, final float xOffset, final float yOffset, final float zOffset, final float particleSpeed, final int particleCount, final int... particleArguments) {
        this.particleType = particleType;
        this.longDistance = longDistance;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.particleSpeed = particleSpeed;
        this.particleCount = particleCount;
        this.particleArguments = particleArguments;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public double getYCoordinate() {
        return this.yCoord;
    }
    
    public float getYOffset() {
        return this.yOffset;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.particleType = EnumParticleTypes.getParticleFromId(packetBuffer.readInt());
        if (this.particleType == null) {
            this.particleType = EnumParticleTypes.BARRIER;
        }
        this.longDistance = packetBuffer.readBoolean();
        this.xCoord = packetBuffer.readFloat();
        this.yCoord = packetBuffer.readFloat();
        this.zCoord = packetBuffer.readFloat();
        this.xOffset = packetBuffer.readFloat();
        this.yOffset = packetBuffer.readFloat();
        this.zOffset = packetBuffer.readFloat();
        this.particleSpeed = packetBuffer.readFloat();
        this.particleCount = packetBuffer.readInt();
        final int argumentCount = this.particleType.getArgumentCount();
        this.particleArguments = new int[argumentCount];
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < argumentCount) {
            this.particleArguments[i] = packetBuffer.readVarIntFromBuffer();
            ++i;
        }
    }
    
    public float getZOffset() {
        return this.zOffset;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.particleType.getParticleID());
        packetBuffer.writeBoolean(this.longDistance);
        packetBuffer.writeFloat(this.xCoord);
        packetBuffer.writeFloat(this.yCoord);
        packetBuffer.writeFloat(this.zCoord);
        packetBuffer.writeFloat(this.xOffset);
        packetBuffer.writeFloat(this.yOffset);
        packetBuffer.writeFloat(this.zOffset);
        packetBuffer.writeFloat(this.particleSpeed);
        packetBuffer.writeInt(this.particleCount);
        final int argumentCount = this.particleType.getArgumentCount();
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < argumentCount) {
            packetBuffer.writeVarIntToBuffer(this.particleArguments[i]);
            ++i;
        }
    }
}

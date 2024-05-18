package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.io.*;

public class S12PacketEntityVelocity implements Packet<INetHandlerPlayClient>
{
    private int motionY;
    private int motionZ;
    private int motionX;
    private int entityID;
    
    public S12PacketEntityVelocity() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityVelocity(this);
    }
    
    public S12PacketEntityVelocity(final int entityID, double n, double n2, double n3) {
        this.entityID = entityID;
        final double n4 = 3.9;
        if (n < -n4) {
            n = -n4;
        }
        if (n2 < -n4) {
            n2 = -n4;
        }
        if (n3 < -n4) {
            n3 = -n4;
        }
        if (n > n4) {
            n = n4;
        }
        if (n2 > n4) {
            n2 = n4;
        }
        if (n3 > n4) {
            n3 = n4;
        }
        this.motionX = (int)(n * 8000.0);
        this.motionY = (int)(n2 * 8000.0);
        this.motionZ = (int)(n3 * 8000.0);
    }
    
    public int getMotionZ() {
        return this.motionZ;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S12PacketEntityVelocity(final Entity entity) {
        this(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ);
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeShort(this.motionX);
        packetBuffer.writeShort(this.motionY);
        packetBuffer.writeShort(this.motionZ);
    }
    
    public int getMotionX() {
        return this.motionX;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.motionX = packetBuffer.readShort();
        this.motionY = packetBuffer.readShort();
        this.motionZ = packetBuffer.readShort();
    }
    
    public int getEntityID() {
        return this.entityID;
    }
    
    public int getMotionY() {
        return this.motionY;
    }
}

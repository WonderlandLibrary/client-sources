package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class S18PacketEntityTeleport implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private int posX;
    private byte pitch;
    private int posZ;
    private boolean onGround;
    private byte yaw;
    private int posY;
    
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
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityTeleport(this);
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public S18PacketEntityTeleport(final int entityId, final int posX, final int posY, final int posZ, final byte yaw, final byte pitch, final boolean onGround) {
        this.entityId = entityId;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
    
    public int getY() {
        return this.posY;
    }
    
    public int getZ() {
        return this.posZ;
    }
    
    public boolean getOnGround() {
        return this.onGround;
    }
    
    public S18PacketEntityTeleport() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.onGround = packetBuffer.readBoolean();
    }
    
    public int getX() {
        return this.posX;
    }
    
    public S18PacketEntityTeleport(final Entity entity) {
        this.entityId = entity.getEntityId();
        this.posX = MathHelper.floor_double(entity.posX * 32.0);
        this.posY = MathHelper.floor_double(entity.posY * 32.0);
        this.posZ = MathHelper.floor_double(entity.posZ * 32.0);
        this.yaw = (byte)(entity.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entity.rotationPitch * 256.0f / 360.0f);
        this.onGround = entity.onGround;
    }
    
    public byte getYaw() {
        return this.yaw;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeBoolean(this.onGround);
    }
    
    public byte getPitch() {
        return this.pitch;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}

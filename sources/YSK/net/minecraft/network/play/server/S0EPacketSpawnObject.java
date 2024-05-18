package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S0EPacketSpawnObject implements Packet<INetHandlerPlayClient>
{
    private int y;
    private int pitch;
    private int speedZ;
    private int speedX;
    private int yaw;
    private int speedY;
    private int field_149020_k;
    private int z;
    private int type;
    private int x;
    private int entityId;
    
    public void setZ(final int z) {
        this.z = z;
    }
    
    public void func_149002_g(final int field_149020_k) {
        this.field_149020_k = field_149020_k;
    }
    
    public S0EPacketSpawnObject() {
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getSpeedX() {
        return this.speedX;
    }
    
    public int getSpeedY() {
        return this.speedY;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnObject(this);
    }
    
    public int func_149009_m() {
        return this.field_149020_k;
    }
    
    public void setX(final int x) {
        this.x = x;
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getSpeedZ() {
        return this.speedZ;
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    public void setSpeedZ(final int speedZ) {
        this.speedZ = speedZ;
    }
    
    public int getY() {
        return this.y;
    }
    
    public S0EPacketSpawnObject(final Entity entity, final int n) {
        this(entity, n, "".length());
    }
    
    public int getYaw() {
        return this.yaw;
    }
    
    public S0EPacketSpawnObject(final Entity entity, final int type, final int field_149020_k) {
        this.entityId = entity.getEntityId();
        this.x = MathHelper.floor_double(entity.posX * 32.0);
        this.y = MathHelper.floor_double(entity.posY * 32.0);
        this.z = MathHelper.floor_double(entity.posZ * 32.0);
        this.pitch = MathHelper.floor_float(entity.rotationPitch * 256.0f / 360.0f);
        this.yaw = MathHelper.floor_float(entity.rotationYaw * 256.0f / 360.0f);
        this.type = type;
        this.field_149020_k = field_149020_k;
        if (field_149020_k > 0) {
            double motionX = entity.motionX;
            double motionY = entity.motionY;
            double motionZ = entity.motionZ;
            final double n = 3.9;
            if (motionX < -n) {
                motionX = -n;
            }
            if (motionY < -n) {
                motionY = -n;
            }
            if (motionZ < -n) {
                motionZ = -n;
            }
            if (motionX > n) {
                motionX = n;
            }
            if (motionY > n) {
                motionY = n;
            }
            if (motionZ > n) {
                motionZ = n;
            }
            this.speedX = (int)(motionX * 8000.0);
            this.speedY = (int)(motionY * 8000.0);
            this.speedZ = (int)(motionZ * 8000.0);
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readByte();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.pitch = packetBuffer.readByte();
        this.yaw = packetBuffer.readByte();
        this.field_149020_k = packetBuffer.readInt();
        if (this.field_149020_k > 0) {
            this.speedX = packetBuffer.readShort();
            this.speedY = packetBuffer.readShort();
            this.speedZ = packetBuffer.readShort();
        }
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getPitch() {
        return this.pitch;
    }
    
    public int getZ() {
        return this.z;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void setSpeedX(final int speedX) {
        this.speedX = speedX;
    }
    
    public void setSpeedY(final int speedY) {
        this.speedY = speedY;
    }
    
    public int getX() {
        return this.x;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeInt(this.field_149020_k);
        if (this.field_149020_k > 0) {
            packetBuffer.writeShort(this.speedX);
            packetBuffer.writeShort(this.speedY);
            packetBuffer.writeShort(this.speedZ);
        }
    }
}

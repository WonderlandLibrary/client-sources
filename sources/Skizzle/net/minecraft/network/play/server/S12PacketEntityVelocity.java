/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S12PacketEntityVelocity
implements Packet {
    public int entityID;
    public int motionX;
    public int motionY;
    public int motionZ;
    public boolean fakePacket = false;
    private static final String __OBFID = "CL_00001328";

    public S12PacketEntityVelocity() {
    }

    public S12PacketEntityVelocity(Entity p_i45219_1_) {
        this(p_i45219_1_.getEntityId(), p_i45219_1_.motionX, p_i45219_1_.motionY, p_i45219_1_.motionZ);
    }

    public S12PacketEntityVelocity(int entityID, double motionX, double motionY, double motionZ) {
        this.entityID = entityID;
        double var8 = 3.9;
        if (motionX < -var8) {
            motionX = -var8;
        }
        if (motionY < -var8) {
            motionY = -var8;
        }
        if (motionZ < -var8) {
            motionZ = -var8;
        }
        if (motionY > var8) {
            motionX = var8;
        }
        if (motionY > var8) {
            motionY = var8;
        }
        if (motionY > var8) {
            motionZ = var8;
        }
        this.motionX = (int)(motionX * 8000.0);
        this.motionY = (int)(motionY * 8000.0);
        this.motionZ = (int)(motionZ * 8000.0);
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.entityID = data.readVarIntFromBuffer();
        this.motionX = data.readShort();
        this.motionY = data.readShort();
        this.motionZ = data.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.entityID);
        data.writeShort(this.motionX);
        data.writeShort(this.motionY);
        data.writeShort(this.motionZ);
    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleEntityVelocity(this);
    }

    public int func_149412_c() {
        return this.entityID;
    }

    public int func_149411_d() {
        return this.motionX;
    }

    public int func_149410_e() {
        return this.motionY;
    }

    public int func_149409_f() {
        return this.motionZ;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }

    public int getMotionX() {
        return this.motionX;
    }

    public void setMotionX(int motionX) {
        this.motionX = motionX;
    }

    public int getMotionY() {
        return this.motionY;
    }

    public void setMotionY(int motionY) {
        this.motionY = motionY;
    }

    public int getMotionZ() {
        return this.motionZ;
    }

    public void setMotionZ(int motionZ) {
        this.motionZ = motionZ;
    }
}


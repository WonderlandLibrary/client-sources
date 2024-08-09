/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class SEntityVelocityPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityID;
    private int motionX;
    private int motionY;
    private int motionZ;

    public SEntityVelocityPacket() {
    }

    public SEntityVelocityPacket(Entity entity2) {
        this(entity2.getEntityId(), entity2.getMotion());
    }

    public SEntityVelocityPacket(int n, Vector3d vector3d) {
        this.entityID = n;
        double d = 3.9;
        double d2 = MathHelper.clamp(vector3d.x, -3.9, 3.9);
        double d3 = MathHelper.clamp(vector3d.y, -3.9, 3.9);
        double d4 = MathHelper.clamp(vector3d.z, -3.9, 3.9);
        this.motionX = (int)(d2 * 8000.0);
        this.motionY = (int)(d3 * 8000.0);
        this.motionZ = (int)(d4 * 8000.0);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarInt();
        this.motionX = packetBuffer.readShort();
        this.motionY = packetBuffer.readShort();
        this.motionZ = packetBuffer.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityID);
        packetBuffer.writeShort(this.motionX);
        packetBuffer.writeShort(this.motionY);
        packetBuffer.writeShort(this.motionZ);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityVelocity(this);
    }

    public int getEntityID() {
        return this.entityID;
    }

    public int getMotionX() {
        return this.motionX;
    }

    public int getMotionY() {
        return this.motionY;
    }

    public int getMotionZ() {
        return this.motionZ;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}


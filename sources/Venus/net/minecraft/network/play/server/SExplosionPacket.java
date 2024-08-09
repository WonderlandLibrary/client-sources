/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class SExplosionPacket
implements IPacket<IClientPlayNetHandler> {
    private double posX;
    private double posY;
    private double posZ;
    private float strength;
    private List<BlockPos> affectedBlockPositions;
    private float motionX;
    private float motionY;
    private float motionZ;

    public SExplosionPacket() {
    }

    public SExplosionPacket(double d, double d2, double d3, float f, List<BlockPos> list, Vector3d vector3d) {
        this.posX = d;
        this.posY = d2;
        this.posZ = d3;
        this.strength = f;
        this.affectedBlockPositions = Lists.newArrayList(list);
        if (vector3d != null) {
            this.motionX = (float)vector3d.x;
            this.motionY = (float)vector3d.y;
            this.motionZ = (float)vector3d.z;
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.posX = packetBuffer.readFloat();
        this.posY = packetBuffer.readFloat();
        this.posZ = packetBuffer.readFloat();
        this.strength = packetBuffer.readFloat();
        int n = packetBuffer.readInt();
        this.affectedBlockPositions = Lists.newArrayListWithCapacity(n);
        int n2 = MathHelper.floor(this.posX);
        int n3 = MathHelper.floor(this.posY);
        int n4 = MathHelper.floor(this.posZ);
        for (int i = 0; i < n; ++i) {
            int n5 = packetBuffer.readByte() + n2;
            int n6 = packetBuffer.readByte() + n3;
            int n7 = packetBuffer.readByte() + n4;
            this.affectedBlockPositions.add(new BlockPos(n5, n6, n7));
        }
        this.motionX = packetBuffer.readFloat();
        this.motionY = packetBuffer.readFloat();
        this.motionZ = packetBuffer.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat((float)this.posX);
        packetBuffer.writeFloat((float)this.posY);
        packetBuffer.writeFloat((float)this.posZ);
        packetBuffer.writeFloat(this.strength);
        packetBuffer.writeInt(this.affectedBlockPositions.size());
        int n = MathHelper.floor(this.posX);
        int n2 = MathHelper.floor(this.posY);
        int n3 = MathHelper.floor(this.posZ);
        for (BlockPos blockPos : this.affectedBlockPositions) {
            int n4 = blockPos.getX() - n;
            int n5 = blockPos.getY() - n2;
            int n6 = blockPos.getZ() - n3;
            packetBuffer.writeByte(n4);
            packetBuffer.writeByte(n5);
            packetBuffer.writeByte(n6);
        }
        packetBuffer.writeFloat(this.motionX);
        packetBuffer.writeFloat(this.motionY);
        packetBuffer.writeFloat(this.motionZ);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleExplosion(this);
    }

    public float getMotionX() {
        return this.motionX;
    }

    public float getMotionY() {
        return this.motionY;
    }

    public float getMotionZ() {
        return this.motionZ;
    }

    public double getX() {
        return this.posX;
    }

    public double getY() {
        return this.posY;
    }

    public double getZ() {
        return this.posZ;
    }

    public float getStrength() {
        return this.strength;
    }

    public List<BlockPos> getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}


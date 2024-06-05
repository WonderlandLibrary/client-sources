// 
// Decompiled by Procyon v0.6.0
// 

package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import java.util.Iterator;
import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import com.google.common.collect.Lists;
import net.minecraft.util.Vec3;
import net.minecraft.util.BlockPos;
import java.util.List;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.Packet;

public class S27PacketExplosion implements Packet<INetHandlerPlayClient>
{
    private double posX;
    private double posY;
    private double posZ;
    private float strength;
    private List<BlockPos> affectedBlockPositions;
    private float motionX;
    private float motionY;
    private float motionZ;
    
    public S27PacketExplosion() {
    }
    
    public S27PacketExplosion(final double x, final double y, final double z, final float strengthIn, final List<BlockPos> affectedBlocksIn, final Vec3 motion) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.strength = strengthIn;
        this.affectedBlockPositions = Lists.newArrayList((Iterable)affectedBlocksIn);
        if (motion != null) {
            this.motionX = (float)motion.xCoord;
            this.motionY = (float)motion.yCoord;
            this.motionZ = (float)motion.zCoord;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.posX = buf.readFloat();
        this.posY = buf.readFloat();
        this.posZ = buf.readFloat();
        this.strength = buf.readFloat();
        final int i = buf.readInt();
        this.affectedBlockPositions = Lists.newArrayListWithCapacity(i);
        final int j = (int)this.posX;
        final int k = (int)this.posY;
        final int l = (int)this.posZ;
        for (int i2 = 0; i2 < i; ++i2) {
            final int j2 = buf.readByte() + j;
            final int k2 = buf.readByte() + k;
            final int l2 = buf.readByte() + l;
            this.affectedBlockPositions.add(new BlockPos(j2, k2, l2));
        }
        this.motionX = buf.readFloat();
        this.motionY = buf.readFloat();
        this.motionZ = buf.readFloat();
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeFloat((float)this.posX);
        buf.writeFloat((float)this.posY);
        buf.writeFloat((float)this.posZ);
        buf.writeFloat(this.strength);
        buf.writeInt(this.affectedBlockPositions.size());
        final int i = (int)this.posX;
        final int j = (int)this.posY;
        final int k = (int)this.posZ;
        for (final BlockPos blockpos : this.affectedBlockPositions) {
            final int l = blockpos.getX() - i;
            final int i2 = blockpos.getY() - j;
            final int j2 = blockpos.getZ() - k;
            buf.writeByte(l);
            buf.writeByte(i2);
            buf.writeByte(j2);
        }
        buf.writeFloat(this.motionX);
        buf.writeFloat(this.motionY);
        buf.writeFloat(this.motionZ);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleExplosion(this);
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
    
    public void setMotionX(final float motionX) {
        this.motionX = motionX;
    }
    
    public void setMotionY(final float motionY) {
        this.motionY = motionY;
    }
    
    public void setMotionZ(final float motionZ) {
        this.motionZ = motionZ;
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
}

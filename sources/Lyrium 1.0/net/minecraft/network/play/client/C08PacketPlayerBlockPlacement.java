// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import java.io.IOException;
import xyz.niggfaclient.utils.other.ProtocolUtils;
import viamcp.protocols.ProtocolCollection;
import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.Packet;

public class C08PacketPlayerBlockPlacement implements Packet<INetHandlerPlayServer>
{
    public static BlockPos field_179726_a;
    public BlockPos position;
    public int placedBlockDirection;
    public ItemStack stack;
    public float facingX;
    public float facingY;
    public float facingZ;
    
    public C08PacketPlayerBlockPlacement() {
    }
    
    public C08PacketPlayerBlockPlacement(final ItemStack stackIn) {
        this(C08PacketPlayerBlockPlacement.field_179726_a, 255, stackIn, 0.0f, 0.0f, 0.0f);
    }
    
    public C08PacketPlayerBlockPlacement(final BlockPos positionIn, final int placedBlockDirectionIn, final ItemStack stackIn, final float facingXIn, final float facingYIn, final float facingZIn) {
        this.position = positionIn;
        this.placedBlockDirection = placedBlockDirectionIn;
        this.stack = ((stackIn != null) ? stackIn.copy() : null);
        this.facingX = facingXIn;
        this.facingY = facingYIn;
        this.facingZ = facingZIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.position = buf.readBlockPos();
        this.placedBlockDirection = buf.readUnsignedByte();
        this.stack = buf.readItemStackFromBuffer();
        if (ProtocolUtils.isMoreOrEqual(ProtocolCollection.R1_11)) {
            this.facingX = buf.readUnsignedByte();
            this.facingY = buf.readUnsignedByte();
            this.facingZ = buf.readUnsignedByte();
        }
        else {
            this.facingX = buf.readUnsignedByte() / 16.0f;
            this.facingY = buf.readUnsignedByte() / 16.0f;
            this.facingZ = buf.readUnsignedByte() / 16.0f;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeBlockPos(this.position);
        buf.writeByte(this.placedBlockDirection);
        buf.writeItemStackToBuffer(this.stack);
        buf.writeByte((int)(this.facingX * 16.0f));
        buf.writeByte((int)(this.facingY * 16.0f));
        buf.writeByte((int)(this.facingZ * 16.0f));
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processPlayerBlockPlacement(this);
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public int getPlacedBlockDirection() {
        return this.placedBlockDirection;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public float getPlacedBlockOffsetX() {
        return this.facingX;
    }
    
    public float getPlacedBlockOffsetY() {
        return this.facingY;
    }
    
    public float getPlacedBlockOffsetZ() {
        return this.facingZ;
    }
    
    static {
        C08PacketPlayerBlockPlacement.field_179726_a = new BlockPos(-1, -1, -1);
    }
}

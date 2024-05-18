package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;

public class C08PacketPlayerBlockPlacement implements Packet<INetHandlerPlayServer>
{
    private float facingY;
    private int placedBlockDirection;
    private BlockPos position;
    private float facingZ;
    private static final BlockPos field_179726_a;
    private ItemStack stack;
    private float facingX;
    
    public C08PacketPlayerBlockPlacement(final ItemStack itemStack) {
        this(C08PacketPlayerBlockPlacement.field_179726_a, 143 + 59 - 49 + 102, itemStack, 0.0f, 0.0f, 0.0f);
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.position = packetBuffer.readBlockPos();
        this.placedBlockDirection = packetBuffer.readUnsignedByte();
        this.stack = packetBuffer.readItemStackFromBuffer();
        this.facingX = packetBuffer.readUnsignedByte() / 16.0f;
        this.facingY = packetBuffer.readUnsignedByte() / 16.0f;
        this.facingZ = packetBuffer.readUnsignedByte() / 16.0f;
    }
    
    public C08PacketPlayerBlockPlacement() {
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    static {
        field_179726_a = new BlockPos(-" ".length(), -" ".length(), -" ".length());
    }
    
    public float getPlacedBlockOffsetY() {
        return this.facingY;
    }
    
    public C08PacketPlayerBlockPlacement(final BlockPos position, final int placedBlockDirection, final ItemStack itemStack, final float facingX, final float facingY, final float facingZ) {
        this.position = position;
        this.placedBlockDirection = placedBlockDirection;
        ItemStack copy;
        if (itemStack != null) {
            copy = itemStack.copy();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            copy = null;
        }
        this.stack = copy;
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }
    
    public int getPlacedBlockDirection() {
        return this.placedBlockDirection;
    }
    
    public float getPlacedBlockOffsetX() {
        return this.facingX;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerBlockPlacement(this);
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
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.placedBlockDirection);
        packetBuffer.writeItemStackToBuffer(this.stack);
        packetBuffer.writeByte((int)(this.facingX * 16.0f));
        packetBuffer.writeByte((int)(this.facingY * 16.0f));
        packetBuffer.writeByte((int)(this.facingZ * 16.0f));
    }
    
    public float getPlacedBlockOffsetZ() {
        return this.facingZ;
    }
}

package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.network.*;

public class S35PacketUpdateTileEntity implements Packet<INetHandlerPlayClient>
{
    private BlockPos blockPos;
    private int metadata;
    private NBTTagCompound nbt;
    
    public S35PacketUpdateTileEntity(final BlockPos blockPos, final int metadata, final NBTTagCompound nbt) {
        this.blockPos = blockPos;
        this.metadata = metadata;
        this.nbt = nbt;
    }
    
    public S35PacketUpdateTileEntity() {
    }
    
    public NBTTagCompound getNbtCompound() {
        return this.nbt;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPos = packetBuffer.readBlockPos();
        this.metadata = packetBuffer.readUnsignedByte();
        this.nbt = packetBuffer.readNBTTagCompoundFromBuffer();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateTileEntity(this);
    }
    
    public BlockPos getPos() {
        return this.blockPos;
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPos);
        packetBuffer.writeByte((byte)this.metadata);
        packetBuffer.writeNBTTagCompoundToBuffer(this.nbt);
    }
    
    public int getTileEntityType() {
        return this.metadata;
    }
}

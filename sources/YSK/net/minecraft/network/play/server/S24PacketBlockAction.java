package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.network.*;

public class S24PacketBlockAction implements Packet<INetHandlerPlayClient>
{
    private BlockPos blockPosition;
    private Block block;
    private int instrument;
    private int pitch;
    
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Block getBlockType() {
        return this.block;
    }
    
    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }
    
    public int getData2() {
        return this.pitch;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPosition);
        packetBuffer.writeByte(this.instrument);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeVarIntToBuffer(Block.getIdFromBlock(this.block) & 541 + 838 + 2476 + 240);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getData1() {
        return this.instrument;
    }
    
    public S24PacketBlockAction(final BlockPos blockPosition, final Block block, final int instrument, final int pitch) {
        this.blockPosition = blockPosition;
        this.instrument = instrument;
        this.pitch = pitch;
        this.block = block;
    }
    
    public S24PacketBlockAction() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockAction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPosition = packetBuffer.readBlockPos();
        this.instrument = packetBuffer.readUnsignedByte();
        this.pitch = packetBuffer.readUnsignedByte();
        this.block = Block.getBlockById(packetBuffer.readVarIntFromBuffer() & 991 + 588 + 146 + 2370);
    }
}

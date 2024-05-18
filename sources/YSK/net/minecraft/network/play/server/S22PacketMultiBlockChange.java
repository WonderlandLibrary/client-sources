package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.world.chunk.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class S22PacketMultiBlockChange implements Packet<INetHandlerPlayClient>
{
    private BlockUpdateData[] changedBlocks;
    private ChunkCoordIntPair chunkPosCoord;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.chunkPosCoord = new ChunkCoordIntPair(packetBuffer.readInt(), packetBuffer.readInt());
        this.changedBlocks = new BlockUpdateData[packetBuffer.readVarIntFromBuffer()];
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < this.changedBlocks.length) {
            this.changedBlocks[i] = new BlockUpdateData(packetBuffer.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarIntFromBuffer()));
            ++i;
        }
    }
    
    public BlockUpdateData[] getChangedBlocks() {
        return this.changedBlocks;
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static ChunkCoordIntPair access$1(final S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        return s22PacketMultiBlockChange.chunkPosCoord;
    }
    
    public S22PacketMultiBlockChange(final int n, final short[] array, final Chunk chunk) {
        this.chunkPosCoord = new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
        this.changedBlocks = new BlockUpdateData[n];
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < this.changedBlocks.length) {
            this.changedBlocks[i] = new BlockUpdateData(array[i], chunk);
            ++i;
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMultiBlockChange(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.chunkPosCoord.chunkXPos);
        packetBuffer.writeInt(this.chunkPosCoord.chunkZPos);
        packetBuffer.writeVarIntToBuffer(this.changedBlocks.length);
        final BlockUpdateData[] changedBlocks;
        final int length = (changedBlocks = this.changedBlocks).length;
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < length) {
            final BlockUpdateData blockUpdateData = changedBlocks[i];
            packetBuffer.writeShort(blockUpdateData.func_180089_b());
            packetBuffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(blockUpdateData.getBlockState()));
            ++i;
        }
    }
    
    public S22PacketMultiBlockChange() {
    }
    
    public class BlockUpdateData
    {
        final S22PacketMultiBlockChange this$0;
        private final short chunkPosCrammed;
        private final IBlockState blockState;
        
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
                if (3 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public IBlockState getBlockState() {
            return this.blockState;
        }
        
        public BlockUpdateData(final S22PacketMultiBlockChange this$0, final short chunkPosCrammed, final Chunk chunk) {
            this.this$0 = this$0;
            this.chunkPosCrammed = chunkPosCrammed;
            this.blockState = chunk.getBlockState(this.getPos());
        }
        
        public BlockUpdateData(final S22PacketMultiBlockChange this$0, final short chunkPosCrammed, final IBlockState blockState) {
            this.this$0 = this$0;
            this.chunkPosCrammed = chunkPosCrammed;
            this.blockState = blockState;
        }
        
        public short func_180089_b() {
            return this.chunkPosCrammed;
        }
        
        public BlockPos getPos() {
            return new BlockPos(S22PacketMultiBlockChange.access$1(this.this$0).getBlock(this.chunkPosCrammed >> (0x45 ^ 0x49) & (0x27 ^ 0x28), this.chunkPosCrammed & 103 + 2 - 63 + 213, this.chunkPosCrammed >> (0x8E ^ 0x86) & (0x5 ^ 0xA)));
        }
    }
}

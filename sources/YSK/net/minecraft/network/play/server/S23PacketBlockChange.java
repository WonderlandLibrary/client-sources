package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import java.io.*;

public class S23PacketBlockChange implements Packet<INetHandlerPlayClient>
{
    private BlockPos blockPosition;
    private IBlockState blockState;
    
    public S23PacketBlockChange(final World world, final BlockPos blockPosition) {
        this.blockPosition = blockPosition;
        this.blockState = world.getBlockState(blockPosition);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPosition = packetBuffer.readBlockPos();
        this.blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarIntFromBuffer());
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockChange(this);
    }
    
    public IBlockState getBlockState() {
        return this.blockState;
    }
    
    public S23PacketBlockChange() {
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPosition);
        packetBuffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.blockState));
    }
}

package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.io.*;

public class S25PacketBlockBreakAnim implements Packet<INetHandlerPlayClient>
{
    private int breakerId;
    private int progress;
    private BlockPos position;
    
    public S25PacketBlockBreakAnim() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public S25PacketBlockBreakAnim(final int breakerId, final BlockPos position, final int progress) {
        this.breakerId = breakerId;
        this.position = position;
        this.progress = progress;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.breakerId);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.progress);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockBreakAnim(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.breakerId = packetBuffer.readVarIntFromBuffer();
        this.position = packetBuffer.readBlockPos();
        this.progress = packetBuffer.readUnsignedByte();
    }
    
    public int getProgress() {
        return this.progress;
    }
    
    public int getBreakerId() {
        return this.breakerId;
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
            if (0 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}

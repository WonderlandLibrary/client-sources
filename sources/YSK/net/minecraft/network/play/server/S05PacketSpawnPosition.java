package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S05PacketSpawnPosition implements Packet<INetHandlerPlayClient>
{
    private BlockPos spawnBlockPos;
    
    public BlockPos getSpawnPos() {
        return this.spawnBlockPos;
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.spawnBlockPos);
    }
    
    public S05PacketSpawnPosition() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPosition(this);
    }
    
    public S05PacketSpawnPosition(final BlockPos spawnBlockPos) {
        this.spawnBlockPos = spawnBlockPos;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.spawnBlockPos = packetBuffer.readBlockPos();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}

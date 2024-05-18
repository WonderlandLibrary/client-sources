package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.io.*;

public class S28PacketEffect implements Packet<INetHandlerPlayClient>
{
    private boolean serverWide;
    private BlockPos soundPos;
    private int soundData;
    private int soundType;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEffect(this);
    }
    
    public int getSoundType() {
        return this.soundType;
    }
    
    public S28PacketEffect(final int soundType, final BlockPos soundPos, final int soundData, final boolean serverWide) {
        this.soundType = soundType;
        this.soundPos = soundPos;
        this.soundData = soundData;
        this.serverWide = serverWide;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.soundType);
        packetBuffer.writeBlockPos(this.soundPos);
        packetBuffer.writeInt(this.soundData);
        packetBuffer.writeBoolean(this.serverWide);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.soundType = packetBuffer.readInt();
        this.soundPos = packetBuffer.readBlockPos();
        this.soundData = packetBuffer.readInt();
        this.serverWide = packetBuffer.readBoolean();
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S28PacketEffect() {
    }
    
    public int getSoundData() {
        return this.soundData;
    }
    
    public boolean isSoundServerwide() {
        return this.serverWide;
    }
    
    public BlockPos getSoundPos() {
        return this.soundPos;
    }
}

package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.network.*;

public class S26PacketMapChunkBulk implements Packet<INetHandlerPlayClient>
{
    private int[] xPositions;
    private int[] zPositions;
    private S21PacketChunkData.Extracted[] chunksData;
    private boolean isOverworld;
    
    public S26PacketMapChunkBulk() {
    }
    
    public byte[] getChunkBytes(final int n) {
        return this.chunksData[n].data;
    }
    
    public int getChunkSize(final int n) {
        return this.chunksData[n].dataSize;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBoolean(this.isOverworld);
        packetBuffer.writeVarIntToBuffer(this.chunksData.length);
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < this.xPositions.length) {
            packetBuffer.writeInt(this.xPositions[i]);
            packetBuffer.writeInt(this.zPositions[i]);
            packetBuffer.writeShort((short)(this.chunksData[i].dataSize & 5006 + 49437 - 15537 + 26629));
            ++i;
        }
        int j = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (j < this.xPositions.length) {
            packetBuffer.writeBytes(this.chunksData[j].data);
            ++j;
        }
    }
    
    public S26PacketMapChunkBulk(final List<Chunk> list) {
        final int size = list.size();
        this.xPositions = new int[size];
        this.zPositions = new int[size];
        this.chunksData = new S21PacketChunkData.Extracted[size];
        int isOverworld;
        if (list.get("".length()).getWorld().provider.getHasNoSky()) {
            isOverworld = "".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            isOverworld = " ".length();
        }
        this.isOverworld = (isOverworld != 0);
        int i = "".length();
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (i < size) {
            final Chunk chunk = list.get(i);
            final S21PacketChunkData.Extracted func_179756_a = S21PacketChunkData.func_179756_a(chunk, " ".length() != 0, this.isOverworld, 58778 + 50440 - 68463 + 24780);
            this.xPositions[i] = chunk.xPosition;
            this.zPositions[i] = chunk.zPosition;
            this.chunksData[i] = func_179756_a;
            ++i;
        }
    }
    
    public int getChunkX(final int n) {
        return this.xPositions[n];
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getChunkCount() {
        return this.xPositions.length;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getChunkZ(final int n) {
        return this.zPositions[n];
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMapChunkBulk(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.isOverworld = packetBuffer.readBoolean();
        final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
        this.xPositions = new int[varIntFromBuffer];
        this.zPositions = new int[varIntFromBuffer];
        this.chunksData = new S21PacketChunkData.Extracted[varIntFromBuffer];
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < varIntFromBuffer) {
            this.xPositions[i] = packetBuffer.readInt();
            this.zPositions[i] = packetBuffer.readInt();
            this.chunksData[i] = new S21PacketChunkData.Extracted();
            this.chunksData[i].dataSize = (packetBuffer.readShort() & 35693 + 22847 - 26870 + 33865);
            this.chunksData[i].data = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount(this.chunksData[i].dataSize), this.isOverworld, (boolean)(" ".length() != 0))];
            ++i;
        }
        int j = "".length();
        "".length();
        if (0 == 3) {
            throw null;
        }
        while (j < varIntFromBuffer) {
            packetBuffer.readBytes(this.chunksData[j].data);
            ++j;
        }
    }
}

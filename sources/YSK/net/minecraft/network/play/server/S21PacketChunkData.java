package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.world.chunk.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.storage.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S21PacketChunkData implements Packet<INetHandlerPlayClient>
{
    private int chunkX;
    private int chunkZ;
    private boolean field_149279_g;
    private Extracted extractedData;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChunkData(this);
    }
    
    public byte[] func_149272_d() {
        return this.extractedData.data;
    }
    
    private static int func_179757_a(final byte[] array, final byte[] array2, final int n) {
        System.arraycopy(array, "".length(), array2, n, array.length);
        return n + array.length;
    }
    
    protected static int func_180737_a(final int n, final boolean b, final boolean b2) {
        final int n2 = n * "  ".length() * (0x37 ^ 0x27) * (0xA8 ^ 0xB8) * (0x80 ^ 0x90);
        final int n3 = n * (0xAE ^ 0xBE) * (0x5C ^ 0x4C) * (0x63 ^ 0x73) / "  ".length();
        int length;
        if (b) {
            length = n * (0xA0 ^ 0xB0) * (0xAA ^ 0xBA) * (0x93 ^ 0x83) / "  ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        final int n4 = length;
        int length2;
        if (b2) {
            length2 = 67 + 224 - 255 + 220;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            length2 = "".length();
        }
        return n2 + n3 + n4 + length2;
    }
    
    public static Extracted func_179756_a(final Chunk chunk, final boolean b, final boolean b2, final int n) {
        final ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        final Extracted extracted = new Extracted();
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < blockStorageArray.length) {
            final ExtendedBlockStorage extendedBlockStorage = blockStorageArray[i];
            if (extendedBlockStorage != null && (!b || !extendedBlockStorage.isEmpty()) && (n & " ".length() << i) != 0x0) {
                final Extracted extracted2 = extracted;
                extracted2.dataSize |= " ".length() << i;
                arrayList.add(extendedBlockStorage);
            }
            ++i;
        }
        extracted.data = new byte[func_180737_a(Integer.bitCount(extracted.dataSize), b2, b)];
        int n2 = "".length();
        final Iterator<ExtendedBlockStorage> iterator = (Iterator<ExtendedBlockStorage>)arrayList.iterator();
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final char[] data;
            final int length = (data = iterator.next().getData()).length;
            int j = "".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (j < length) {
                final char c = data[j];
                extracted.data[n2++] = (byte)(c & 226 + 29 - 218 + 218);
                extracted.data[n2++] = (byte)(c >> (0x31 ^ 0x39) & 173 + 201 - 279 + 160);
                ++j;
            }
        }
        final Iterator<ExtendedBlockStorage> iterator2 = (Iterator<ExtendedBlockStorage>)arrayList.iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            n2 = func_179757_a(iterator2.next().getBlocklightArray().getData(), extracted.data, n2);
        }
        if (b2) {
            final Iterator<ExtendedBlockStorage> iterator3 = (Iterator<ExtendedBlockStorage>)arrayList.iterator();
            "".length();
            if (false) {
                throw null;
            }
            while (iterator3.hasNext()) {
                n2 = func_179757_a(iterator3.next().getSkylightArray().getData(), extracted.data, n2);
            }
        }
        if (b) {
            func_179757_a(chunk.getBiomeArray(), extracted.data, n2);
        }
        return extracted;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.chunkX = packetBuffer.readInt();
        this.chunkZ = packetBuffer.readInt();
        this.field_149279_g = packetBuffer.readBoolean();
        this.extractedData = new Extracted();
        this.extractedData.dataSize = packetBuffer.readShort();
        this.extractedData.data = packetBuffer.readByteArray();
    }
    
    public boolean func_149274_i() {
        return this.field_149279_g;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.chunkX);
        packetBuffer.writeInt(this.chunkZ);
        packetBuffer.writeBoolean(this.field_149279_g);
        packetBuffer.writeShort((short)(this.extractedData.dataSize & 33174 + 6535 - 38871 + 64697));
        packetBuffer.writeByteArray(this.extractedData.data);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getChunkZ() {
        return this.chunkZ;
    }
    
    public int getChunkX() {
        return this.chunkX;
    }
    
    public S21PacketChunkData() {
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getExtractedSize() {
        return this.extractedData.dataSize;
    }
    
    public S21PacketChunkData(final Chunk chunk, final boolean field_149279_g, final int n) {
        this.chunkX = chunk.xPosition;
        this.chunkZ = chunk.zPosition;
        this.field_149279_g = field_149279_g;
        int n2;
        if (chunk.getWorld().provider.getHasNoSky()) {
            n2 = "".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        this.extractedData = func_179756_a(chunk, field_149279_g, n2 != 0, n);
    }
    
    public static class Extracted
    {
        public int dataSize;
        public byte[] data;
        
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
                if (4 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}

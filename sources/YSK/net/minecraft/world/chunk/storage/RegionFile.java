package net.minecraft.world.chunk.storage;

import java.util.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import java.util.zip.*;
import java.io.*;

public class RegionFile
{
    private long lastModified;
    private final int[] chunkTimestamps;
    private final File fileName;
    private int sizeDelta;
    private static final String[] I;
    private RandomAccessFile dataFile;
    private static final byte[] emptySector;
    private List<Boolean> sectorFree;
    private final int[] offsets;
    
    private void write(final int n, final byte[] array, final int n2) throws IOException {
        this.dataFile.seek(n * (451 + 662 + 2311 + 672));
        this.dataFile.writeInt(n2 + " ".length());
        this.dataFile.writeByte("  ".length());
        this.dataFile.write(array, "".length(), n2);
    }
    
    public boolean isChunkSaved(final int n, final int n2) {
        if (this.getOffset(n, n2) != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void setOffset(final int n, final int n2, final int n3) throws IOException {
        this.offsets[n + n2 * (0x58 ^ 0x78)] = n3;
        this.dataFile.seek((n + n2 * (0xA0 ^ 0x80)) * (0xD ^ 0x9));
        this.dataFile.writeInt(n3);
    }
    
    public void close() throws IOException {
        if (this.dataFile != null) {
            this.dataFile.close();
        }
    }
    
    private boolean outOfBounds(final int n, final int n2) {
        if (n >= 0 && n < (0xBB ^ 0x9B) && n2 >= 0 && n2 < (0x9E ^ 0xBE)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private void setChunkTimestamp(final int n, final int n2, final int n3) throws IOException {
        this.chunkTimestamps[n + n2 * (0x6C ^ 0x4C)] = n3;
        this.dataFile.seek(1622 + 3413 - 4088 + 3149 + (n + n2 * (0xF ^ 0x2F)) * (0x12 ^ 0x16));
        this.dataFile.writeInt(n3);
    }
    
    private int getOffset(final int n, final int n2) {
        return this.offsets[n + n2 * (0x50 ^ 0x70)];
    }
    
    protected synchronized void write(final int n, final int n2, final byte[] array, final int n3) {
        try {
            final int offset = this.getOffset(n, n2);
            final int n4 = offset >> (0x81 ^ 0x89);
            final int n5 = offset & 71 + 129 - 197 + 252;
            final int n6 = (n3 + (0x3A ^ 0x3F)) / (2815 + 1512 - 3482 + 3251) + " ".length();
            if (n6 >= 123 + 89 - 131 + 175) {
                return;
            }
            if (n4 != 0 && n5 == n6) {
                this.write(n4, array, n3);
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            else {
                int i = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
                while (i < n5) {
                    this.sectorFree.set(n4 + i, " ".length() != 0);
                    ++i;
                }
                int index = this.sectorFree.indexOf((boolean)(" ".length() != 0));
                int n7 = "".length();
                if (index != -" ".length()) {
                    int j = index;
                    "".length();
                    if (0 == 3) {
                        throw null;
                    }
                    while (j < this.sectorFree.size()) {
                        if (n7 != 0) {
                            if (this.sectorFree.get(j)) {
                                ++n7;
                                "".length();
                                if (3 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                n7 = "".length();
                                "".length();
                                if (2 == 3) {
                                    throw null;
                                }
                            }
                        }
                        else if (this.sectorFree.get(j)) {
                            index = j;
                            n7 = " ".length();
                        }
                        if (n7 >= n6) {
                            "".length();
                            if (3 <= 0) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++j;
                        }
                    }
                }
                if (n7 >= n6) {
                    final int n8 = index;
                    this.setOffset(n, n2, index << (0xAC ^ 0xA4) | n6);
                    int k = "".length();
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                    while (k < n6) {
                        this.sectorFree.set(n8 + k, "".length() != 0);
                        ++k;
                    }
                    this.write(n8, array, n3);
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else {
                    this.dataFile.seek(this.dataFile.length());
                    final int size = this.sectorFree.size();
                    int l = "".length();
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                    while (l < n6) {
                        this.dataFile.write(RegionFile.emptySector);
                        this.sectorFree.add("".length() != 0);
                        ++l;
                    }
                    this.sizeDelta += (2657 + 1564 - 2792 + 2667) * n6;
                    this.write(size, array, n3);
                    this.setOffset(n, n2, size << (0x97 ^ 0x9F) | n6);
                }
            }
            this.setChunkTimestamp(n, n2, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001d ", "oWHor");
    }
    
    static {
        I();
        emptySector = new byte[210 + 1921 + 385 + 1580];
    }
    
    public RegionFile(final File fileName) {
        this.offsets = new int[292 + 192 - 93 + 633];
        this.chunkTimestamps = new int[179 + 194 - 259 + 910];
        this.fileName = fileName;
        this.sizeDelta = "".length();
        try {
            if (fileName.exists()) {
                this.lastModified = fileName.lastModified();
            }
            this.dataFile = new RandomAccessFile(fileName, RegionFile.I["".length()]);
            if (this.dataFile.length() < 4096L) {
                int i = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                while (i < 65 + 809 - 462 + 612) {
                    this.dataFile.writeInt("".length());
                    ++i;
                }
                int j = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (j < 224 + 706 - 356 + 450) {
                    this.dataFile.writeInt("".length());
                    ++j;
                }
                this.sizeDelta += 6036 + 3584 - 5356 + 3928;
            }
            if ((this.dataFile.length() & 0xFFFL) != 0x0L) {
                int length = "".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (length < (this.dataFile.length() & 0xFFFL)) {
                    this.dataFile.write("".length());
                    ++length;
                }
            }
            final int n = (int)this.dataFile.length() / (1956 + 3163 - 3913 + 2890);
            this.sectorFree = (List<Boolean>)Lists.newArrayListWithCapacity(n);
            int k = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (k < n) {
                this.sectorFree.add(" ".length() != 0);
                ++k;
            }
            this.sectorFree.set("".length(), "".length() != 0);
            this.sectorFree.set(" ".length(), "".length() != 0);
            this.dataFile.seek(0L);
            int l = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (l < 1004 + 1017 - 1778 + 781) {
                final int int1 = this.dataFile.readInt();
                this.offsets[l] = int1;
                if (int1 != 0 && (int1 >> (0x44 ^ 0x4C)) + (int1 & 157 + 137 - 158 + 119) <= this.sectorFree.size()) {
                    int length2 = "".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    while (length2 < (int1 & 223 + 228 - 421 + 225)) {
                        this.sectorFree.set((int1 >> (0x52 ^ 0x5A)) + length2, "".length() != 0);
                        ++length2;
                    }
                }
                ++l;
            }
            int length3 = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (length3 < 823 + 51 - 589 + 739) {
                this.chunkTimestamps[length3] = this.dataFile.readInt();
                ++length3;
            }
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public DataOutputStream getChunkDataOutputStream(final int n, final int n2) {
        DataOutputStream dataOutputStream;
        if (this.outOfBounds(n, n2)) {
            dataOutputStream = null;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            final DeflaterOutputStream deflaterOutputStream;
            dataOutputStream = new DataOutputStream(deflaterOutputStream);
            deflaterOutputStream = new DeflaterOutputStream(new ChunkBuffer(n, n2));
        }
        return dataOutputStream;
    }
    
    public synchronized DataInputStream getChunkDataInputStream(final int n, final int n2) {
        if (this.outOfBounds(n, n2)) {
            return null;
        }
        try {
            final int offset = this.getOffset(n, n2);
            if (offset == 0) {
                return null;
            }
            final int n3 = offset >> (0x3 ^ 0xB);
            final int n4 = offset & 116 + 104 - 39 + 74;
            if (n3 + n4 > this.sectorFree.size()) {
                return null;
            }
            this.dataFile.seek(n3 * (3240 + 3039 - 4676 + 2493));
            final int int1 = this.dataFile.readInt();
            if (int1 > (464 + 1757 + 1722 + 153) * n4) {
                return null;
            }
            if (int1 <= 0) {
                return null;
            }
            final byte byte1 = this.dataFile.readByte();
            if (byte1 == " ".length()) {
                final byte[] array = new byte[int1 - " ".length()];
                this.dataFile.read(array);
                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(array))));
            }
            if (byte1 == "  ".length()) {
                final byte[] array2 = new byte[int1 - " ".length()];
                this.dataFile.read(array2);
                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(array2))));
            }
            return null;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    class ChunkBuffer extends ByteArrayOutputStream
    {
        private int chunkX;
        final RegionFile this$0;
        private int chunkZ;
        
        public ChunkBuffer(final RegionFile this$0, final int chunkX, final int chunkZ) {
            this.this$0 = this$0;
            super(7381 + 49 + 329 + 337);
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }
        
        @Override
        public void close() throws IOException {
            this.this$0.write(this.chunkX, this.chunkZ, this.buf, this.count);
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
                if (1 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}

package net.minecraft.world.chunk.storage;

import net.minecraft.world.chunk.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;

public class ChunkLoader
{
    private static final String[] I;
    
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x21 ^ 0x3D])["".length()] = I(",\b\u00001", "TXoBY");
        ChunkLoader.I[" ".length()] = I("65'=", "LeHNi");
        ChunkLoader.I["  ".length()] = I("$+*\u0011\u001e\u0015", "fGEru");
        ChunkLoader.I["   ".length()] = I("\u000e)\u00123", "JHfRI");
        ChunkLoader.I[0xB1 ^ 0xB5] = I(">\t\u001c*\b\n\n\u0011", "mbefa");
        ChunkLoader.I[0x49 ^ 0x4C] = I("\u0014=8\u001b\u0012\u001a80\u0010\r", "VQWxy");
        ChunkLoader.I[0x69 ^ 0x6F] = I("8+\u0019\u0002\u001c\u0004\u0003\u0011\u0015", "pNpet");
        ChunkLoader.I[0x5F ^ 0x58] = I("=\u0002+5\n\u0000\t\t(\u001b\u001c\u000b83\u000e\r", "igYGk");
        ChunkLoader.I[0x8E ^ 0x86] = I("\u0004\u0005\u001a\b$(\u000e\u001d", "AknaP");
        ChunkLoader.I[0x50 ^ 0x59] = I("\u0013\u001a*\u0004\u0013)\u0007/\u0015?\"\u0000", "GsFaV");
        ChunkLoader.I[0x66 ^ 0x6C] = I("\u001d\u000f\u0007\b- \u0005\u0000\u001e", "Ifkmy");
        ChunkLoader.I[0xB2 ^ 0xB9] = I("\u0014\u0002\u0000\u0011\u001d(\u0007\u0012\u0011-", "XcseH");
        ChunkLoader.I[0x5F ^ 0x53] = I("***\u0013:\u0016/8\u0013\n", "fKYgo");
        ChunkLoader.I[0x5E ^ 0x53] = I(")%\u001c\u0007", "Qusty");
        ChunkLoader.I[0x37 ^ 0x39] = I("\f\u0005,\u0017", "vUCdd");
        ChunkLoader.I[0xC8 ^ 0xC7] = I("\u001e*\u0000!\r\"/\u0012!=", "RKsUX");
        ChunkLoader.I[0x6A ^ 0x7A] = I("\u001b0\u00054/'\u0018\r#", "SUlSG");
        ChunkLoader.I[0x9E ^ 0x8F] = I("\r70\u0010+0<\u0012\r:,>#\u0016/=", "YRBbJ");
        ChunkLoader.I[0x9 ^ 0x1B] = I("\u0014", "MgPGm");
        ChunkLoader.I[0xAF ^ 0xBC] = I("\u0000\u001b\u0000\u000b31", "BwohX");
        ChunkLoader.I[0x2B ^ 0x3F] = I("#'\u0019\u000e", "gFmov");
        ChunkLoader.I[0xB9 ^ 0xAC] = I("=>\u001c\u00008\t=\u0011", "nUeLQ");
        ChunkLoader.I[0x8E ^ 0x98] = I("\u0012\b%+,\u001c\r- 3", "PdJHG");
        ChunkLoader.I[0x9C ^ 0x8B] = I("\u001f\u000e 5\u001e#\u00050", "LkCAw");
        ChunkLoader.I[0x77 ^ 0x6F] = I("\t\u0006!'58", "KoNJP");
        ChunkLoader.I[0x54 ^ 0x4D] = I("\t421\u001b%?5", "LZFXo");
        ChunkLoader.I[0x80 ^ 0x9A] = I("\u00050\u0014\u0000\u0013?-\u0011\u0011?4*", "QYxeV");
        ChunkLoader.I[0x1F ^ 0x4] = I("\u0005\u0010='\u00198\u001a:1", "QyQBM");
    }
    
    static {
        I();
    }
    
    public static AnvilConverterData load(final NBTTagCompound nbtTagCompound) {
        final AnvilConverterData anvilConverterData = new AnvilConverterData(nbtTagCompound.getInteger(ChunkLoader.I["".length()]), nbtTagCompound.getInteger(ChunkLoader.I[" ".length()]));
        anvilConverterData.blocks = nbtTagCompound.getByteArray(ChunkLoader.I["  ".length()]);
        anvilConverterData.data = new NibbleArrayReader(nbtTagCompound.getByteArray(ChunkLoader.I["   ".length()]), 0x8A ^ 0x8D);
        anvilConverterData.skyLight = new NibbleArrayReader(nbtTagCompound.getByteArray(ChunkLoader.I[0x1F ^ 0x1B]), 0xB9 ^ 0xBE);
        anvilConverterData.blockLight = new NibbleArrayReader(nbtTagCompound.getByteArray(ChunkLoader.I[0x35 ^ 0x30]), 0x10 ^ 0x17);
        anvilConverterData.heightmap = nbtTagCompound.getByteArray(ChunkLoader.I[0x78 ^ 0x7E]);
        anvilConverterData.terrainPopulated = nbtTagCompound.getBoolean(ChunkLoader.I[0xD ^ 0xA]);
        anvilConverterData.entities = nbtTagCompound.getTagList(ChunkLoader.I[0x40 ^ 0x48], 0x98 ^ 0x92);
        anvilConverterData.tileEntities = nbtTagCompound.getTagList(ChunkLoader.I[0x86 ^ 0x8F], 0x73 ^ 0x79);
        anvilConverterData.tileTicks = nbtTagCompound.getTagList(ChunkLoader.I[0xCD ^ 0xC7], 0x47 ^ 0x4D);
        try {
            anvilConverterData.lastUpdated = nbtTagCompound.getLong(ChunkLoader.I[0x4 ^ 0xF]);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (ClassCastException ex) {
            anvilConverterData.lastUpdated = nbtTagCompound.getInteger(ChunkLoader.I[0x8A ^ 0x86]);
        }
        return anvilConverterData;
    }
    
    public static void convertToAnvilFormat(final AnvilConverterData anvilConverterData, final NBTTagCompound nbtTagCompound, final WorldChunkManager worldChunkManager) {
        nbtTagCompound.setInteger(ChunkLoader.I[0x29 ^ 0x24], anvilConverterData.x);
        nbtTagCompound.setInteger(ChunkLoader.I[0x6B ^ 0x65], anvilConverterData.z);
        nbtTagCompound.setLong(ChunkLoader.I[0x5D ^ 0x52], anvilConverterData.lastUpdated);
        final int[] array = new int[anvilConverterData.heightmap.length];
        int i = "".length();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (i < anvilConverterData.heightmap.length) {
            array[i] = anvilConverterData.heightmap[i];
            ++i;
        }
        nbtTagCompound.setIntArray(ChunkLoader.I[0x7D ^ 0x6D], array);
        nbtTagCompound.setBoolean(ChunkLoader.I[0x18 ^ 0x9], anvilConverterData.terrainPopulated);
        final NBTTagList list = new NBTTagList();
        int j = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (j < (0x9C ^ 0x94)) {
            int n = " ".length();
            int length = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (length < (0xA ^ 0x1A) && n != 0) {
                int length2 = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (length2 < (0x66 ^ 0x76) && n != 0) {
                    int k = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (k < (0x73 ^ 0x63)) {
                        if (anvilConverterData.blocks[length << (0x48 ^ 0x43) | k << (0x3E ^ 0x39) | length2 + (j << (0x77 ^ 0x73))] != 0) {
                            n = "".length();
                            "".length();
                            if (3 < 1) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++k;
                        }
                    }
                    ++length2;
                }
                ++length;
            }
            if (n == 0) {
                final byte[] array2 = new byte[451 + 1349 - 1518 + 3814];
                final NibbleArray nibbleArray = new NibbleArray();
                final NibbleArray nibbleArray2 = new NibbleArray();
                final NibbleArray nibbleArray3 = new NibbleArray();
                int l = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                while (l < (0xA4 ^ 0xB4)) {
                    int length3 = "".length();
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                    while (length3 < (0x3 ^ 0x13)) {
                        int length4 = "".length();
                        "".length();
                        if (1 == 2) {
                            throw null;
                        }
                        while (length4 < (0x3D ^ 0x2D)) {
                            array2[length3 << (0x20 ^ 0x28) | length4 << (0x6 ^ 0x2) | l] = (byte)(anvilConverterData.blocks[l << (0x39 ^ 0x32) | length4 << (0x1F ^ 0x18) | length3 + (j << (0x0 ^ 0x4))] & 32 + 31 + 59 + 133);
                            nibbleArray.set(l, length3, length4, anvilConverterData.data.get(l, length3 + (j << (0xB ^ 0xF)), length4));
                            nibbleArray2.set(l, length3, length4, anvilConverterData.skyLight.get(l, length3 + (j << (0xBE ^ 0xBA)), length4));
                            nibbleArray3.set(l, length3, length4, anvilConverterData.blockLight.get(l, length3 + (j << (0x6D ^ 0x69)), length4));
                            ++length4;
                        }
                        ++length3;
                    }
                    ++l;
                }
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(ChunkLoader.I[0x8B ^ 0x99], (byte)(j & 137 + 151 - 199 + 166));
                nbtTagCompound2.setByteArray(ChunkLoader.I[0x6F ^ 0x7C], array2);
                nbtTagCompound2.setByteArray(ChunkLoader.I[0x17 ^ 0x3], nibbleArray.getData());
                nbtTagCompound2.setByteArray(ChunkLoader.I[0x8A ^ 0x9F], nibbleArray2.getData());
                nbtTagCompound2.setByteArray(ChunkLoader.I[0x8D ^ 0x9B], nibbleArray3.getData());
                list.appendTag(nbtTagCompound2);
            }
            ++j;
        }
        nbtTagCompound.setTag(ChunkLoader.I[0x14 ^ 0x3], list);
        final byte[] array3 = new byte[206 + 212 - 264 + 102];
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int length5 = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (length5 < (0x6A ^ 0x7A)) {
            int length6 = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (length6 < (0x0 ^ 0x10)) {
                mutableBlockPos.func_181079_c(anvilConverterData.x << (0xF ^ 0xB) | length5, "".length(), anvilConverterData.z << (0x64 ^ 0x60) | length6);
                array3[length6 << (0x58 ^ 0x5C) | length5] = (byte)(worldChunkManager.getBiomeGenerator(mutableBlockPos, BiomeGenBase.field_180279_ad).biomeID & 224 + 212 - 279 + 98);
                ++length6;
            }
            ++length5;
        }
        nbtTagCompound.setByteArray(ChunkLoader.I[0x8E ^ 0x96], array3);
        nbtTagCompound.setTag(ChunkLoader.I[0x64 ^ 0x7D], anvilConverterData.entities);
        nbtTagCompound.setTag(ChunkLoader.I[0xBC ^ 0xA6], anvilConverterData.tileEntities);
        if (anvilConverterData.tileTicks != null) {
            nbtTagCompound.setTag(ChunkLoader.I[0x32 ^ 0x29], anvilConverterData.tileTicks);
        }
    }
    
    public static class AnvilConverterData
    {
        public final int x;
        public byte[] blocks;
        public long lastUpdated;
        public NibbleArrayReader skyLight;
        public NibbleArrayReader blockLight;
        public byte[] heightmap;
        public boolean terrainPopulated;
        public NBTTagList tileEntities;
        public NibbleArrayReader data;
        public NBTTagList tileTicks;
        public final int z;
        public NBTTagList entities;
        
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
                if (4 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public AnvilConverterData(final int x, final int z) {
            this.x = x;
            this.z = z;
        }
    }
}

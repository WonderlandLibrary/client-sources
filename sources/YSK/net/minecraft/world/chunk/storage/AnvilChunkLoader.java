package net.minecraft.world.chunk.storage;

import net.minecraft.world.chunk.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class AnvilChunkLoader implements IThreadedFileIO, IChunkLoader
{
    private Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates;
    private Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove;
    private static final Logger logger;
    private boolean field_183014_e;
    private final File chunkSaveLocation;
    private static final String[] I;
    
    private static void I() {
        (I = new String[0x26 ^ 0x76])["".length()] = I("\r\n9\u000e>", "AoOkR");
        AnvilChunkLoader.I[" ".length()] = I("&.\u00138,E \u000f:\"E'\u0012v", "eFfVG");
        AnvilChunkLoader.I["  ".length()] = I("k", "GpJna");
        AnvilChunkLoader.I["   ".length()] = I("W\u001b\u001bY\u0004\u001e\u0001\u001b\u0010\u0007\u0010R\u0004\u001c\u001f\u0012\u001eH\u001d\b\u0003\u0013DY\u001a\u001c\u001b\u0018\t\u0000\u0019\u0015", "wrhyi");
        AnvilChunkLoader.I[0x64 ^ 0x60] = I(".)< \u001b", "bLJEw");
        AnvilChunkLoader.I[0x45 ^ 0x40] = I("4.$6\u000b\b%4", "gKGBb");
        AnvilChunkLoader.I[0x4F ^ 0x49] = I("5/\u001c\u00148V!\u0000\u00166V&\u001dZ", "vGizS");
        AnvilChunkLoader.I[0xA5 ^ 0xA2] = I("G", "kzRed");
        AnvilChunkLoader.I[0x7C ^ 0x74] = I("R\f\nN\u0001\u001b\u0016\n\u0007\u0002\u0015E\u001b\u0002\u0003\u0011\u000eY\n\r\u0006\u0004UN\u001f\u0019\f\t\u001e\u0005\u001c\u0002", "reynl");
        AnvilChunkLoader.I[0x5A ^ 0x53] = I(",?\u0002\u0005\tO1\u001e\u0007\u0007O6\u0003K", "oWwkb");
        AnvilChunkLoader.I[0x47 ^ 0x4D] = I("|", "PEyxO");
        AnvilChunkLoader.I[0x52 ^ 0x59] = I("w(\u0011f09a\u0016.<w6\u0010)70a\u000e):65\u000b)7la\u0010#58\"\u0003209&Lfq\u00129\u0012#:#$\u0006f", "WAbFY");
        AnvilChunkLoader.I[0x5B ^ 0x57] = I("aC", "McSKg");
        AnvilChunkLoader.I[0xAF ^ 0xA2] = I("@D\u000f+\u000eL", "ldhDz");
        AnvilChunkLoader.I[0x93 ^ 0x9D] = I("Ti", "xIiMK");
        AnvilChunkLoader.I[0x4A ^ 0x45] = I("^", "wBtay");
        AnvilChunkLoader.I[0xB4 ^ 0xA4] = I("9!%\u0016", "AqJeY");
        AnvilChunkLoader.I[0x53 ^ 0x42] = I("\u0011;\u0018\u0015", "kkwfT");
        AnvilChunkLoader.I[0x60 ^ 0x72] = I(".\u00040\u000b(", "baFnD");
        AnvilChunkLoader.I[0x3F ^ 0x2C] = I("\u000f*\r)$-k\u0010*a:*\u0012 a*#\u0011+*", "IKdEA");
        AnvilChunkLoader.I[0x6F ^ 0x7B] = I("\u00121\u0003.*\"<\u0015\n%00\u001d\b#37\u001a\u0018?)+\u0010,.fq\n6b|y0''f:\u0019>%-*Q*9#y\u0002*=#=", "FYqKK");
        AnvilChunkLoader.I[0x7C ^ 0x69] = I("\n\u0007\u001e\u001f\u001c(F\u0003\u001cY?\u0007\u0001\u0016Y/\u000e\u0002\u001d\u0012", "Lfwsy");
        AnvilChunkLoader.I[0x8D ^ 0x9B] = I("\u001c", "JUUou");
        AnvilChunkLoader.I[0x64 ^ 0x73] = I(",2.$", "TbAWr");
        AnvilChunkLoader.I[0x2A ^ 0x32] = I("\u0002\u0006\f5", "xVcFv");
        AnvilChunkLoader.I[0x93 ^ 0x8A] = I("\u001d2\u0005,2!7\u0017,\u0002", "QSvXg");
        AnvilChunkLoader.I[0x9E ^ 0x84] = I("\u0004\u0012\u0019\"\r8:\u00115", "LwpEe");
        AnvilChunkLoader.I[0x53 ^ 0x48] = I("\u0003\u0010\u001a\u0016\u0003>\u001b8\u000b\u0012\"\u0019\t\u0010\u00073", "Wuhdb");
        AnvilChunkLoader.I[0x22 ^ 0x3E] = I("\u0007,\u0010\u0001\u001e\u001b*\u0007\u001c\u0006*1\u0012\r", "KEwij");
        AnvilChunkLoader.I[0xB1 ^ 0xAC] = I("\u0004\u00199\f$$\u00034\t\u0012$\u001a4", "MwQmF");
        AnvilChunkLoader.I[0x80 ^ 0x9E] = I("!", "xiJUw");
        AnvilChunkLoader.I[0xC ^ 0x13] = I("\u000f\u0005\u0015\u0007\b>", "Mizdc");
        AnvilChunkLoader.I[0x57 ^ 0x77] = I("/'6\u0003", "kFBbg");
        AnvilChunkLoader.I[0x36 ^ 0x17] = I("\u0011%\b", "PAlTu");
        AnvilChunkLoader.I[0xBA ^ 0x98] = I("%\"'!\n+'/*\u0015", "gNHBa");
        AnvilChunkLoader.I[0xBC ^ 0x9F] = I("\u00183 ;/,0-", "KXYwF");
        AnvilChunkLoader.I[0xAD ^ 0x89] = I("7\r\u0003\u0016-\u0003\u000e\u000e", "dfzZD");
        AnvilChunkLoader.I[0x5F ^ 0x7A] = I("<\u000f5\u001a(\u0000\u0004%", "ojVnA");
        AnvilChunkLoader.I[0x3D ^ 0x1B] = I(";\u001c\b\u001d\u0016\n", "yugps");
        AnvilChunkLoader.I[0x44 ^ 0x63] = I("#\u001a:\u0001\u0001\u000f\u0011=", "ftNhu");
        AnvilChunkLoader.I[0xEE ^ 0xC6] = I("\u0002\u000f)\t#8\u0012,\u0018\u000f3\u0015", "VfElf");
        AnvilChunkLoader.I[0x52 ^ 0x7B] = I("\u0007", "nvilo");
        AnvilChunkLoader.I[0x59 ^ 0x73] = I("", "nZJKH");
        AnvilChunkLoader.I[0xEB ^ 0xC0] = I("\u0017", "otrBS");
        AnvilChunkLoader.I[0x15 ^ 0x39] = I("\u000e", "wbZBE");
        AnvilChunkLoader.I[0x51 ^ 0x7C] = I("*", "PnJfK");
        AnvilChunkLoader.I[0x77 ^ 0x59] = I("1", "EljXg");
        AnvilChunkLoader.I[0xE8 ^ 0xC7] = I("5", "EjIsG");
        AnvilChunkLoader.I[0x8E ^ 0xBE] = I("\u001a\r46''\u00073 ", "NdXSs");
        AnvilChunkLoader.I[0x57 ^ 0x66] = I("+=:\u0003", "SmUpm");
        AnvilChunkLoader.I[0x2F ^ 0x1D] = I("\u0002\u0001\t\u0004", "xQfwl");
        AnvilChunkLoader.I[0xA2 ^ 0x91] = I("<\u000e\u0006\u0003\u0000\u0000&\u000e\u0014", "tkodh");
        AnvilChunkLoader.I[0xB2 ^ 0x86] = I("\u0019\u001c\u0002\u0003\u0004$\u0017 \u001e\u00158\u0015\u0011\u0005\u0000)", "Mypqe");
        AnvilChunkLoader.I[0x88 ^ 0xBD] = I("\u0018&$\u00007\u0004 3\u001d/5;&\f", "TOChC");
        AnvilChunkLoader.I[0x97 ^ 0xA1] = I("\u0000#\u0005-$ 9\b(\u0012  \b", "IMmLF");
        AnvilChunkLoader.I[0x66 ^ 0x51] = I("2\u00077<\u0002\u000e\f'", "abTHk");
        AnvilChunkLoader.I[0xE ^ 0x36] = I("2", "kQNym");
        AnvilChunkLoader.I[0x61 ^ 0x58] = I("\u0016/#\u0004?'", "TCLgT");
        AnvilChunkLoader.I[0x43 ^ 0x79] = I("\t&=7", "MGIVB");
        AnvilChunkLoader.I[0xAA ^ 0x91] = I("\u00122 ", "SVDwS");
        AnvilChunkLoader.I[0x72 ^ 0x4E] = I("+\u000e\u001e", "jjzOl");
        AnvilChunkLoader.I[0x6E ^ 0x53] = I("\u0004\u0014\n\u0019-\n\u0011\u0002\u00122", "FxezF");
        AnvilChunkLoader.I[0x50 ^ 0x6E] = I("=*+\u001f=\t)&", "nARST");
        AnvilChunkLoader.I[0x3F ^ 0x0] = I(";%\f#&\n", "yLcNC");
        AnvilChunkLoader.I[0x57 ^ 0x17] = I("\u001a+\u0006&\u0011+", "XBiKt");
        AnvilChunkLoader.I[0x62 ^ 0x23] = I("'6\u0002#;\u000b=\u0005", "bXvJO");
        AnvilChunkLoader.I[0x50 ^ 0x12] = I("=.!\u001d\u0007\b", "oGEti");
        AnvilChunkLoader.I[0x26 ^ 0x65] = I("(/\u001e\u0004\u000b\u001d", "zFzme");
        AnvilChunkLoader.I[0x82 ^ 0xC6] = I("4\u001a\u000f\u001e\u0016\u0001", "fskwx");
        AnvilChunkLoader.I[0x50 ^ 0x15] = I("\u00043+77>..&\u001b5)", "PZGRr");
        AnvilChunkLoader.I[0x38 ^ 0x7E] = I("0*\u001973\r \u001e!", "dCuRg");
        AnvilChunkLoader.I[0xC ^ 0x4B] = I("\f<>\u0006\u0018169\u0010", "XURcL");
        AnvilChunkLoader.I[0x7D ^ 0x35] = I("\u001c", "uZwkk");
        AnvilChunkLoader.I[0x62 ^ 0x2B] = I(".", "GhjPC");
        AnvilChunkLoader.I[0xD2 ^ 0x98] = I("\u0000", "iZfZZ");
        AnvilChunkLoader.I[0xE2 ^ 0xA9] = I(">", "FEdNr");
        AnvilChunkLoader.I[0xD ^ 0x41] = I("\u000e", "wYrzo");
        AnvilChunkLoader.I[0x39 ^ 0x74] = I(";", "ASmWp");
        AnvilChunkLoader.I[0x6E ^ 0x20] = I("\u001a", "neoDx");
        AnvilChunkLoader.I[0x62 ^ 0x2D] = I("\u0000", "pEsOX");
    }
    
    @Override
    public boolean writeNextIO() {
        if (this.chunksToRemove.isEmpty()) {
            if (this.field_183014_e) {
                final Logger logger = AnvilChunkLoader.logger;
                final String s = AnvilChunkLoader.I[0x37 ^ 0x23];
                final Object[] array = new Object[" ".length()];
                array["".length()] = this.chunkSaveLocation.getName();
                logger.info(s, array);
            }
            return "".length() != 0;
        }
        final ChunkCoordIntPair chunkCoordIntPair = this.chunksToRemove.keySet().iterator().next();
        int length;
        try {
            this.pendingAnvilChunksCoordinates.add(chunkCoordIntPair);
            final NBTTagCompound nbtTagCompound = this.chunksToRemove.remove(chunkCoordIntPair);
            if (nbtTagCompound != null) {
                try {
                    this.func_183013_b(chunkCoordIntPair, nbtTagCompound);
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                catch (Exception ex) {
                    AnvilChunkLoader.logger.error(AnvilChunkLoader.I[0x32 ^ 0x27], (Throwable)ex);
                }
            }
            length = " ".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        finally {
            this.pendingAnvilChunksCoordinates.remove(chunkCoordIntPair);
        }
        this.pendingAnvilChunksCoordinates.remove(chunkCoordIntPair);
        return length != 0;
    }
    
    private void writeChunkToNBT(final Chunk chunk, final World world, final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte(AnvilChunkLoader.I[0xA7 ^ 0xB1], (byte)" ".length());
        nbtTagCompound.setInteger(AnvilChunkLoader.I[0x67 ^ 0x70], chunk.xPosition);
        nbtTagCompound.setInteger(AnvilChunkLoader.I[0x77 ^ 0x6F], chunk.zPosition);
        nbtTagCompound.setLong(AnvilChunkLoader.I[0x52 ^ 0x4B], world.getTotalWorldTime());
        nbtTagCompound.setIntArray(AnvilChunkLoader.I[0x8E ^ 0x94], chunk.getHeightMap());
        nbtTagCompound.setBoolean(AnvilChunkLoader.I[0x9D ^ 0x86], chunk.isTerrainPopulated());
        nbtTagCompound.setBoolean(AnvilChunkLoader.I[0x63 ^ 0x7F], chunk.isLightPopulated());
        nbtTagCompound.setLong(AnvilChunkLoader.I[0xA9 ^ 0xB4], chunk.getInhabitedTime());
        final ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        final NBTTagList list = new NBTTagList();
        int n;
        if (world.provider.getHasNoSky()) {
            n = "".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        final ExtendedBlockStorage[] array;
        final int length = (array = blockStorageArray).length;
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < length) {
            final ExtendedBlockStorage extendedBlockStorage = array[i];
            if (extendedBlockStorage != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(AnvilChunkLoader.I[0x8F ^ 0x91], (byte)(extendedBlockStorage.getYLocation() >> (0x3B ^ 0x3F) & 186 + 195 - 372 + 246));
                final byte[] array2 = new byte[extendedBlockStorage.getData().length];
                final NibbleArray nibbleArray = new NibbleArray();
                NibbleArray nibbleArray2 = null;
                int j = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (j < extendedBlockStorage.getData().length) {
                    final char c = extendedBlockStorage.getData()[j];
                    final int n3 = j & (0x0 ^ 0xF);
                    final int n4 = j >> (0x72 ^ 0x7A) & (0x82 ^ 0x8D);
                    final int n5 = j >> (0x29 ^ 0x2D) & (0x48 ^ 0x47);
                    if (c >> (0x12 ^ 0x1E) != '\0') {
                        if (nibbleArray2 == null) {
                            nibbleArray2 = new NibbleArray();
                        }
                        nibbleArray2.set(n3, n4, n5, c >> (0x2C ^ 0x20));
                    }
                    array2[j] = (byte)(c >> (0x85 ^ 0x81) & 69 + 28 + 27 + 131);
                    nibbleArray.set(n3, n4, n5, c & (0xCC ^ 0xC3));
                    ++j;
                }
                nbtTagCompound2.setByteArray(AnvilChunkLoader.I[0x37 ^ 0x28], array2);
                nbtTagCompound2.setByteArray(AnvilChunkLoader.I[0xE ^ 0x2E], nibbleArray.getData());
                if (nibbleArray2 != null) {
                    nbtTagCompound2.setByteArray(AnvilChunkLoader.I[0x26 ^ 0x7], nibbleArray2.getData());
                }
                nbtTagCompound2.setByteArray(AnvilChunkLoader.I[0x42 ^ 0x60], extendedBlockStorage.getBlocklightArray().getData());
                if (n2 != 0) {
                    nbtTagCompound2.setByteArray(AnvilChunkLoader.I[0x2 ^ 0x21], extendedBlockStorage.getSkylightArray().getData());
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                }
                else {
                    nbtTagCompound2.setByteArray(AnvilChunkLoader.I[0x21 ^ 0x5], new byte[extendedBlockStorage.getBlocklightArray().getData().length]);
                }
                list.appendTag(nbtTagCompound2);
            }
            ++i;
        }
        nbtTagCompound.setTag(AnvilChunkLoader.I[0x7F ^ 0x5A], list);
        nbtTagCompound.setByteArray(AnvilChunkLoader.I[0x3E ^ 0x18], chunk.getBiomeArray());
        chunk.setHasEntities("".length() != 0);
        final NBTTagList list2 = new NBTTagList();
        int k = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (k < chunk.getEntityLists().length) {
            final Iterator<Entity> iterator = chunk.getEntityLists()[k].iterator();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Entity entity = iterator.next();
                final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
                if (entity.writeToNBTOptional(nbtTagCompound3)) {
                    chunk.setHasEntities(" ".length() != 0);
                    list2.appendTag(nbtTagCompound3);
                }
            }
            ++k;
        }
        nbtTagCompound.setTag(AnvilChunkLoader.I[0xB8 ^ 0x9F], list2);
        final NBTTagList list3 = new NBTTagList();
        final Iterator<TileEntity> iterator2 = chunk.getTileEntityMap().values().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final TileEntity tileEntity = iterator2.next();
            final NBTTagCompound nbtTagCompound4 = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound4);
            list3.appendTag(nbtTagCompound4);
        }
        nbtTagCompound.setTag(AnvilChunkLoader.I[0x90 ^ 0xB8], list3);
        final List<NextTickListEntry> pendingBlockUpdates = world.getPendingBlockUpdates(chunk, "".length() != 0);
        if (pendingBlockUpdates != null) {
            final long totalWorldTime = world.getTotalWorldTime();
            final NBTTagList list4 = new NBTTagList();
            final Iterator<NextTickListEntry> iterator3 = pendingBlockUpdates.iterator();
            "".length();
            if (4 == 3) {
                throw null;
            }
            while (iterator3.hasNext()) {
                final NextTickListEntry nextTickListEntry = iterator3.next();
                final NBTTagCompound nbtTagCompound5 = new NBTTagCompound();
                final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(nextTickListEntry.getBlock());
                final NBTTagCompound nbtTagCompound6 = nbtTagCompound5;
                final String s = AnvilChunkLoader.I[0xEF ^ 0xC6];
                String string;
                if (resourceLocation == null) {
                    string = AnvilChunkLoader.I[0xE8 ^ 0xC2];
                    "".length();
                    if (-1 == 3) {
                        throw null;
                    }
                }
                else {
                    string = resourceLocation.toString();
                }
                nbtTagCompound6.setString(s, string);
                nbtTagCompound5.setInteger(AnvilChunkLoader.I[0xB ^ 0x20], nextTickListEntry.position.getX());
                nbtTagCompound5.setInteger(AnvilChunkLoader.I[0x73 ^ 0x5F], nextTickListEntry.position.getY());
                nbtTagCompound5.setInteger(AnvilChunkLoader.I[0x52 ^ 0x7F], nextTickListEntry.position.getZ());
                nbtTagCompound5.setInteger(AnvilChunkLoader.I[0xA1 ^ 0x8F], (int)(nextTickListEntry.scheduledTime - totalWorldTime));
                nbtTagCompound5.setInteger(AnvilChunkLoader.I[0x80 ^ 0xAF], nextTickListEntry.priority);
                list4.appendTag(nbtTagCompound5);
            }
            nbtTagCompound.setTag(AnvilChunkLoader.I[0x52 ^ 0x62], list4);
        }
    }
    
    @Override
    public void saveChunk(final World world, final Chunk chunk) throws IOException, MinecraftException {
        world.checkSessionLock();
        try {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            nbtTagCompound.setTag(AnvilChunkLoader.I[0x29 ^ 0x3B], nbtTagCompound2);
            this.writeChunkToNBT(chunk, world, nbtTagCompound2);
            this.addChunkToPending(chunk.getChunkCoordIntPair(), nbtTagCompound);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (Exception ex) {
            AnvilChunkLoader.logger.error(AnvilChunkLoader.I[0x4C ^ 0x5F], (Throwable)ex);
        }
    }
    
    @Override
    public void saveExtraData() {
        try {
            this.field_183014_e = (" ".length() != 0);
            while (true) {
                if (this.writeNextIO()) {
                    "".length();
                    if (-1 >= 4) {
                        break;
                    }
                    continue;
                }
            }
            throw null;
        }
        finally {
            this.field_183014_e = ("".length() != 0);
        }
    }
    
    @Override
    public Chunk loadChunk(final World world, final int n, final int n2) throws IOException {
        NBTTagCompound read = this.chunksToRemove.get(new ChunkCoordIntPair(n, n2));
        if (read == null) {
            final DataInputStream chunkInputStream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, n, n2);
            if (chunkInputStream == null) {
                return null;
            }
            read = CompressedStreamTools.read(chunkInputStream);
        }
        return this.checkedReadChunkFromNBT(world, n, n2, read);
    }
    
    public AnvilChunkLoader(final File chunkSaveLocation) {
        this.chunksToRemove = new ConcurrentHashMap<ChunkCoordIntPair, NBTTagCompound>();
        this.pendingAnvilChunksCoordinates = Collections.newSetFromMap(new ConcurrentHashMap<ChunkCoordIntPair, Boolean>());
        this.field_183014_e = ("".length() != 0);
        this.chunkSaveLocation = chunkSaveLocation;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void saveExtraChunkData(final World world, final Chunk chunk) throws IOException {
    }
    
    protected void addChunkToPending(final ChunkCoordIntPair chunkCoordIntPair, final NBTTagCompound nbtTagCompound) {
        if (!this.pendingAnvilChunksCoordinates.contains(chunkCoordIntPair)) {
            this.chunksToRemove.put(chunkCoordIntPair, nbtTagCompound);
        }
        ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
    }
    
    private void func_183013_b(final ChunkCoordIntPair chunkCoordIntPair, final NBTTagCompound nbtTagCompound) throws IOException {
        final DataOutputStream chunkOutputStream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
        CompressedStreamTools.write(nbtTagCompound, chunkOutputStream);
        chunkOutputStream.close();
    }
    
    private Chunk readChunkFromNBT(final World world, final NBTTagCompound nbtTagCompound) {
        final Chunk chunk = new Chunk(world, nbtTagCompound.getInteger(AnvilChunkLoader.I[0x4F ^ 0x7E]), nbtTagCompound.getInteger(AnvilChunkLoader.I[0x24 ^ 0x16]));
        chunk.setHeightMap(nbtTagCompound.getIntArray(AnvilChunkLoader.I[0x65 ^ 0x56]));
        chunk.setTerrainPopulated(nbtTagCompound.getBoolean(AnvilChunkLoader.I[0x18 ^ 0x2C]));
        chunk.setLightPopulated(nbtTagCompound.getBoolean(AnvilChunkLoader.I[0x4B ^ 0x7E]));
        chunk.setInhabitedTime(nbtTagCompound.getLong(AnvilChunkLoader.I[0x69 ^ 0x5F]));
        final NBTTagList tagList = nbtTagCompound.getTagList(AnvilChunkLoader.I[0x15 ^ 0x22], 0x77 ^ 0x7D);
        final ExtendedBlockStorage[] storageArrays = new ExtendedBlockStorage[0x53 ^ 0x43];
        int n;
        if (world.provider.getHasNoSky()) {
            n = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final byte byte1 = compoundTag.getByte(AnvilChunkLoader.I[0x53 ^ 0x6B]);
            final ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(byte1 << (0x9F ^ 0x9B), n2 != 0);
            final byte[] byteArray = compoundTag.getByteArray(AnvilChunkLoader.I[0xF ^ 0x36]);
            final NibbleArray nibbleArray = new NibbleArray(compoundTag.getByteArray(AnvilChunkLoader.I[0xAD ^ 0x97]));
            NibbleArray nibbleArray2;
            if (compoundTag.hasKey(AnvilChunkLoader.I[0xA3 ^ 0x98], 0x98 ^ 0x9F)) {
                nibbleArray2 = new NibbleArray(compoundTag.getByteArray(AnvilChunkLoader.I[0x79 ^ 0x45]));
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                nibbleArray2 = null;
            }
            final NibbleArray nibbleArray3 = nibbleArray2;
            final char[] data = new char[byteArray.length];
            int j = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (j < data.length) {
                final int n3 = j & (0x5 ^ 0xA);
                final int n4 = j >> (0xCB ^ 0xC3) & (0x95 ^ 0x9A);
                final int n5 = j >> (0x9D ^ 0x99) & (0x91 ^ 0x9E);
                int n6;
                if (nibbleArray3 != null) {
                    n6 = nibbleArray3.get(n3, n4, n5);
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                else {
                    n6 = "".length();
                }
                data[j] = (char)(n6 << (0x42 ^ 0x4E) | (byteArray[j] & 30 + 13 + 85 + 127) << (0x35 ^ 0x31) | nibbleArray.get(n3, n4, n5));
                ++j;
            }
            extendedBlockStorage.setData(data);
            extendedBlockStorage.setBlocklightArray(new NibbleArray(compoundTag.getByteArray(AnvilChunkLoader.I[0x13 ^ 0x2E])));
            if (n2 != 0) {
                extendedBlockStorage.setSkylightArray(new NibbleArray(compoundTag.getByteArray(AnvilChunkLoader.I[0x44 ^ 0x7A])));
            }
            extendedBlockStorage.removeInvalidBlocks();
            storageArrays[byte1] = extendedBlockStorage;
            ++i;
        }
        chunk.setStorageArrays(storageArrays);
        if (nbtTagCompound.hasKey(AnvilChunkLoader.I[0x58 ^ 0x67], 0x1B ^ 0x1C)) {
            chunk.setBiomeArray(nbtTagCompound.getByteArray(AnvilChunkLoader.I[0x49 ^ 0x9]));
        }
        final NBTTagList tagList2 = nbtTagCompound.getTagList(AnvilChunkLoader.I[0x7B ^ 0x3A], 0x39 ^ 0x33);
        if (tagList2 != null) {
            int k = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (k < tagList2.tagCount()) {
                final NBTTagCompound compoundTag2 = tagList2.getCompoundTagAt(k);
                final Entity entityFromNBT = EntityList.createEntityFromNBT(compoundTag2, world);
                chunk.setHasEntities(" ".length() != 0);
                if (entityFromNBT != null) {
                    chunk.addEntity(entityFromNBT);
                    Entity entity = entityFromNBT;
                    NBTTagCompound compoundTag3 = compoundTag2;
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                    while (compoundTag3.hasKey(AnvilChunkLoader.I[0x57 ^ 0x13], 0xAB ^ 0xA1)) {
                        final Entity entityFromNBT2 = EntityList.createEntityFromNBT(compoundTag3.getCompoundTag(AnvilChunkLoader.I[0xD1 ^ 0x93]), world);
                        if (entityFromNBT2 != null) {
                            chunk.addEntity(entityFromNBT2);
                            entity.mountEntity(entityFromNBT2);
                        }
                        entity = entityFromNBT2;
                        compoundTag3 = compoundTag3.getCompoundTag(AnvilChunkLoader.I[0xDD ^ 0x9E]);
                    }
                }
                ++k;
            }
        }
        final NBTTagList tagList3 = nbtTagCompound.getTagList(AnvilChunkLoader.I[0xCF ^ 0x8A], 0xAE ^ 0xA4);
        if (tagList3 != null) {
            int l = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (l < tagList3.tagCount()) {
                final TileEntity andLoadEntity = TileEntity.createAndLoadEntity(tagList3.getCompoundTagAt(l));
                if (andLoadEntity != null) {
                    chunk.addTileEntity(andLoadEntity);
                }
                ++l;
            }
        }
        if (nbtTagCompound.hasKey(AnvilChunkLoader.I[0x0 ^ 0x46], 0x93 ^ 0x9A)) {
            final NBTTagList tagList4 = nbtTagCompound.getTagList(AnvilChunkLoader.I[0xC0 ^ 0x87], 0xB0 ^ 0xBA);
            if (tagList4 != null) {
                int length = "".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                while (length < tagList4.tagCount()) {
                    final NBTTagCompound compoundTag4 = tagList4.getCompoundTagAt(length);
                    Block block;
                    if (compoundTag4.hasKey(AnvilChunkLoader.I[0xC ^ 0x44], 0x2D ^ 0x25)) {
                        block = Block.getBlockFromName(compoundTag4.getString(AnvilChunkLoader.I[0xC1 ^ 0x88]));
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        block = Block.getBlockById(compoundTag4.getInteger(AnvilChunkLoader.I[0x48 ^ 0x2]));
                    }
                    world.scheduleBlockUpdate(new BlockPos(compoundTag4.getInteger(AnvilChunkLoader.I[0x2C ^ 0x67]), compoundTag4.getInteger(AnvilChunkLoader.I[0x3 ^ 0x4F]), compoundTag4.getInteger(AnvilChunkLoader.I[0x8C ^ 0xC1])), block, compoundTag4.getInteger(AnvilChunkLoader.I[0xF3 ^ 0xBD]), compoundTag4.getInteger(AnvilChunkLoader.I[0x3C ^ 0x73]));
                    ++length;
                }
            }
        }
        return chunk;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    protected Chunk checkedReadChunkFromNBT(final World world, final int n, final int n2, final NBTTagCompound nbtTagCompound) {
        if (!nbtTagCompound.hasKey(AnvilChunkLoader.I["".length()], 0x3F ^ 0x35)) {
            AnvilChunkLoader.logger.error(AnvilChunkLoader.I[" ".length()] + n + AnvilChunkLoader.I["  ".length()] + n2 + AnvilChunkLoader.I["   ".length()]);
            return null;
        }
        final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(AnvilChunkLoader.I[0x28 ^ 0x2C]);
        if (!compoundTag.hasKey(AnvilChunkLoader.I[0x45 ^ 0x40], 0x73 ^ 0x7A)) {
            AnvilChunkLoader.logger.error(AnvilChunkLoader.I[0x26 ^ 0x20] + n + AnvilChunkLoader.I[0x78 ^ 0x7F] + n2 + AnvilChunkLoader.I[0xB ^ 0x3]);
            return null;
        }
        Chunk chunk = this.readChunkFromNBT(world, compoundTag);
        if (!chunk.isAtLocation(n, n2)) {
            AnvilChunkLoader.logger.error(AnvilChunkLoader.I[0xA6 ^ 0xAF] + n + AnvilChunkLoader.I[0x5F ^ 0x55] + n2 + AnvilChunkLoader.I[0x71 ^ 0x7A] + n + AnvilChunkLoader.I[0xB9 ^ 0xB5] + n2 + AnvilChunkLoader.I[0x30 ^ 0x3D] + chunk.xPosition + AnvilChunkLoader.I[0x33 ^ 0x3D] + chunk.zPosition + AnvilChunkLoader.I[0x6 ^ 0x9]);
            compoundTag.setInteger(AnvilChunkLoader.I[0x2C ^ 0x3C], n);
            compoundTag.setInteger(AnvilChunkLoader.I[0x2A ^ 0x3B], n2);
            chunk = this.readChunkFromNBT(world, compoundTag);
        }
        return chunk;
    }
    
    @Override
    public void chunkTick() {
    }
}

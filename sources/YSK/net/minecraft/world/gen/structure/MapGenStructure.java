package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import optfine.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import java.util.*;
import com.google.common.collect.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public abstract class MapGenStructure extends MapGenBase
{
    private LongHashMap structureLongMap;
    protected Map structureMap;
    private MapGenStructureData structureData;
    private static final String[] I;
    private static final String __OBFID;
    
    public boolean func_175795_b(final BlockPos blockPos) {
        this.func_143027_a(this.worldObj);
        if (this.func_175797_c(blockPos) != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected StructureStart func_175797_c(final BlockPos blockPos) {
        final Iterator<StructureStart> iterator = this.structureMap.values().iterator();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final StructureStart structureStart = iterator.next();
            if (structureStart.isSizeableStructure() && structureStart.getBoundingBox().isVecInside(blockPos)) {
                final Iterator<Object> iterator2 = structureStart.getComponents().iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().getBoundingBox().isVecInside(blockPos)) {
                        return structureStart;
                    }
                }
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                continue;
            }
        }
        return null;
    }
    
    protected abstract StructureStart getStructureStart(final int p0, final int p1);
    
    private static void I() {
        (I = new String[0x8C ^ 0x80])["".length()] = I("(?\u001035\u0019.\u001c8e\u001d5\u0016&$\u001f.\u001d1e\u001e3\u0001#&\u00192\u00013e\u000b\"\u0012\"0\u001f\"", "mGsVE");
        MapGenStructure.I[" ".length()] = I("(77\u0016-\u001c7v\u0000=\u0007<1B(\u001c7&\u0003*\u000b6", "nRVbX");
        MapGenStructure.I["  ".length()] = I("\u0001<Z\u0011\u0006);\u000f\u0005\u0006h,\u0012\u0002\r#", "HOzwc");
        MapGenStructure.I["   ".length()] = I("*\u001f1*.I\u001b+'$\u001d\u001e+*", "iwDDE");
        MapGenStructure.I[0x7 ^ 0x3] = I("C\u0000JV\u000e", "fdfsj");
        MapGenStructure.I[0xB5 ^ 0xB0] = I(" :#=\rC\"9 F\u000b3%;", "cRVSf");
        MapGenStructure.I[0x27 ^ 0x21] = I("6'96;\u0011&9&x\u0011*;&", "eSKCX");
        MapGenStructure.I[0x35 ^ 0x32] = I("*:\"% 1", "iRWKK");
        MapGenStructure.I[0xCD ^ 0xC5] = I("$$\u001e=)=", "gLkSB");
        MapGenStructure.I[0x48 ^ 0x41] = I("\u0004\u00073+\u001f\u001f", "GoFEt");
        MapGenStructure.I[0x58 ^ 0x52] = I("\u000f\u0010,&\f\u0016", "LxYHg");
        MapGenStructure.I[0x4D ^ 0x46] = I("-\u0000\u0018SZ^|wVZ[", "nLGcj");
    }
    
    public boolean func_175796_a(final World world, final BlockPos blockPos) {
        this.func_143027_a(world);
        final Iterator<StructureStart> iterator = this.structureMap.values().iterator();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final StructureStart structureStart = iterator.next();
            if (structureStart.isSizeableStructure() && structureStart.getBoundingBox().isVecInside(blockPos)) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    static {
        I();
        __OBFID = MapGenStructure.I[0xAA ^ 0xA1];
    }
    
    public abstract String getStructureName();
    
    private void func_143026_a(final int n, final int n2, final StructureStart structureStart) {
        this.structureData.writeInstance(structureStart.writeStructureComponentsToNBT(n, n2), n, n2);
        this.structureData.markDirty();
    }
    
    public BlockPos getClosestStrongholdPos(final World worldObj, final BlockPos blockPos) {
        this.func_143027_a(this.worldObj = worldObj);
        this.rand.setSeed(worldObj.getSeed());
        this.rand.setSeed((blockPos.getX() >> (0x15 ^ 0x11)) * this.rand.nextLong() ^ (blockPos.getZ() >> (0x7B ^ 0x7F)) * this.rand.nextLong() ^ worldObj.getSeed());
        this.recursiveGenerate(worldObj, blockPos.getX() >> (0x88 ^ 0x8C), blockPos.getZ() >> (0x7A ^ 0x7E), "".length(), "".length(), null);
        double n = Double.MAX_VALUE;
        BlockPos blockPos2 = null;
        final Iterator<StructureStart> iterator = this.structureMap.values().iterator();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final StructureStart structureStart = iterator.next();
            if (structureStart.isSizeableStructure()) {
                final BlockPos boundingBoxCenter = structureStart.getComponents().get("".length()).getBoundingBoxCenter();
                final double distanceSq = boundingBoxCenter.distanceSq(blockPos);
                if (distanceSq >= n) {
                    continue;
                }
                n = distanceSq;
                blockPos2 = boundingBoxCenter;
            }
        }
        if (blockPos2 != null) {
            return blockPos2;
        }
        final List coordList = this.getCoordList();
        if (coordList == null) {
            return null;
        }
        BlockPos blockPos3 = null;
        final Iterator<BlockPos> iterator2 = coordList.iterator();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final BlockPos next = iterator2.next();
            final double distanceSq2 = next.distanceSq(blockPos);
            if (distanceSq2 < n) {
                n = distanceSq2;
                blockPos3 = next;
            }
        }
        return blockPos3;
    }
    
    protected abstract boolean canSpawnStructureAtCoords(final int p0, final int p1);
    
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_143027_a(final World world) {
        if (this.structureData == null) {
            if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
                this.structureData = (MapGenStructureData)((MapStorage)Reflector.call(world, Reflector.ForgeWorld_getPerWorldStorage, new Object["".length()])).loadData(MapGenStructureData.class, this.getStructureName());
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else {
                this.structureData = (MapGenStructureData)world.loadItemData(MapGenStructureData.class, this.getStructureName());
            }
            if (this.structureData == null) {
                this.structureData = new MapGenStructureData(this.getStructureName());
                if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
                    ((MapStorage)Reflector.call(world, Reflector.ForgeWorld_getPerWorldStorage, new Object["".length()])).setData(this.getStructureName(), this.structureData);
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else {
                    world.setItemData(this.getStructureName(), this.structureData);
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
            }
            else {
                final NBTTagCompound tagCompound = this.structureData.getTagCompound();
                final Iterator<String> iterator = tagCompound.getKeySet().iterator();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final NBTBase tag = tagCompound.getTag(iterator.next());
                    if (tag.getId() == (0x87 ^ 0x8D)) {
                        final NBTTagCompound nbtTagCompound = (NBTTagCompound)tag;
                        if (!nbtTagCompound.hasKey(MapGenStructure.I[0x38 ^ 0x3F]) || !nbtTagCompound.hasKey(MapGenStructure.I[0x93 ^ 0x9B])) {
                            continue;
                        }
                        final int integer = nbtTagCompound.getInteger(MapGenStructure.I[0xBF ^ 0xB6]);
                        final int integer2 = nbtTagCompound.getInteger(MapGenStructure.I[0x9A ^ 0x90]);
                        final StructureStart structureStart = MapGenStructureIO.getStructureStart(nbtTagCompound, world);
                        if (structureStart == null) {
                            continue;
                        }
                        this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(integer, integer2), structureStart);
                        this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(integer, integer2), structureStart);
                    }
                }
            }
        }
    }
    
    public boolean generateStructure(final World world, final Random random, final ChunkCoordIntPair chunkCoordIntPair) {
        this.func_143027_a(world);
        final int n = (chunkCoordIntPair.chunkXPos << (0x3D ^ 0x39)) + (0x9D ^ 0x95);
        final int n2 = (chunkCoordIntPair.chunkZPos << (0x26 ^ 0x22)) + (0x0 ^ 0x8);
        int n3 = "".length();
        final Iterator<StructureStart> iterator = this.structureMap.values().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final StructureStart structureStart = iterator.next();
            if (structureStart.isSizeableStructure() && structureStart.func_175788_a(chunkCoordIntPair) && structureStart.getBoundingBox().intersectsWith(n, n2, n + (0x28 ^ 0x27), n2 + (0x86 ^ 0x89))) {
                structureStart.generateStructure(world, random, new StructureBoundingBox(n, n2, n + (0x7D ^ 0x72), n2 + (0xA5 ^ 0xAA)));
                structureStart.func_175787_b(chunkCoordIntPair);
                n3 = " ".length();
                this.func_143026_a(structureStart.getChunkPosX(), structureStart.getChunkPosZ(), structureStart);
            }
        }
        return n3 != 0;
    }
    
    public MapGenStructure() {
        this.structureMap = Maps.newHashMap();
        this.structureLongMap = new LongHashMap();
    }
    
    @Override
    protected final void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
        this.func_143027_a(world);
        if (!this.structureLongMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(n, n2))) {
            this.rand.nextInt();
            try {
                if (this.canSpawnStructureAtCoords(n, n2)) {
                    final StructureStart structureStart = this.getStructureStart(n, n2);
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(n, n2), structureStart);
                    this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(n, n2), structureStart);
                    this.func_143026_a(n, n2, structureStart);
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, MapGenStructure.I["".length()]);
                final CrashReportCategory category = crashReport.makeCategory(MapGenStructure.I[" ".length()]);
                category.addCrashSectionCallable(MapGenStructure.I["  ".length()], new Callable(this, n, n2) {
                    private static final String[] I;
                    final MapGenStructure this$0;
                    private final int val$chunkZ;
                    private final int val$chunkX;
                    private static final String __OBFID;
                    
                    @Override
                    public String call() throws Exception {
                        String s;
                        if (this.this$0.canSpawnStructureAtCoords(this.val$chunkX, this.val$chunkZ)) {
                            s = MapGenStructure$1.I["".length()];
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                        }
                        else {
                            s = MapGenStructure$1.I[" ".length()];
                        }
                        return s;
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                    
                    static {
                        I();
                        __OBFID = MapGenStructure$1.I["  ".length()];
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
                            if (3 == 4) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    private static void I() {
                        (I = new String["   ".length()])["".length()] = I(">&\u0017\u0010", "jTbuq");
                        MapGenStructure$1.I[" ".length()] = I("0,95\u0000", "vMUFe");
                        MapGenStructure$1.I["  ".length()] = I("2\u00029H^A~VM^G", "qNfxn");
                    }
                });
                final CrashReportCategory crashReportCategory = category;
                final String s = MapGenStructure.I["   ".length()];
                final String s2 = MapGenStructure.I[0x6F ^ 0x6B];
                final Object[] array = new Object["  ".length()];
                array["".length()] = n;
                array[" ".length()] = n2;
                crashReportCategory.addCrashSection(s, String.format(s2, array));
                category.addCrashSectionCallable(MapGenStructure.I[0x91 ^ 0x94], new Callable(this, n, n2) {
                    private final int val$chunkX;
                    private final int val$chunkZ;
                    final MapGenStructure this$0;
                    private static final String[] I;
                    private static final String __OBFID;
                    
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
                            if (-1 == 1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    static {
                        I();
                        __OBFID = MapGenStructure$2.I["".length()];
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                    
                    private static void I() {
                        (I = new String[" ".length()])["".length()] = I(";&+WjHZDRjO", "xjtgZ");
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.val$chunkX, this.val$chunkZ));
                    }
                });
                category.addCrashSectionCallable(MapGenStructure.I[0x33 ^ 0x35], new Callable(this) {
                    private static final String[] I;
                    final MapGenStructure this$0;
                    private static final String __OBFID;
                    
                    @Override
                    public String call() throws Exception {
                        return this.this$0.getClass().getCanonicalName();
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
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
                    
                    private static void I() {
                        (I = new String[" ".length()])["".length()] = I("(\u0001/_s[}@ZsS", "kMpoC");
                    }
                    
                    static {
                        I();
                        __OBFID = MapGenStructure$3.I["".length()];
                    }
                });
                throw new ReportedException(crashReport);
            }
        }
    }
    
    protected List getCoordList() {
        return null;
    }
}

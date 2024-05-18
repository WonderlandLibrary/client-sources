package net.minecraft.world.gen.structure;

import net.minecraft.world.biome.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class StructureOceanMonument extends MapGenStructure
{
    private static final String[] I;
    private int field_175800_f;
    private static final List<BiomeGenBase.SpawnListEntry> field_175803_h;
    public static final List<BiomeGenBase> field_175802_d;
    private int field_175801_g;
    
    public List<BiomeGenBase.SpawnListEntry> func_175799_b() {
        return StructureOceanMonument.field_175803_h;
    }
    
    @Override
    public String getStructureName() {
        return StructureOceanMonument.I["  ".length()];
    }
    
    public StructureOceanMonument() {
        this.field_175800_f = (0x78 ^ 0x58);
        this.field_175801_g = (0x17 ^ 0x12);
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.field_175800_f - " ".length();
        }
        if (n2 < 0) {
            n2 -= this.field_175800_f - " ".length();
        }
        final int n5 = n / this.field_175800_f;
        final int n6 = n2 / this.field_175800_f;
        final Random setRandomSeed = this.worldObj.setRandomSeed(n5, n6, 7585576 + 2060394 - 98907 + 840250);
        final int n7 = n5 * this.field_175800_f;
        final int n8 = n6 * this.field_175800_f;
        final int n9 = n7 + (setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g) + setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g)) / "  ".length();
        final int n10 = n8 + (setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g) + setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g)) / "  ".length();
        if (n3 == n9 && n4 == n10) {
            if (this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(n3 * (0x23 ^ 0x33) + (0x74 ^ 0x7C), 0x25 ^ 0x65, n4 * (0xB8 ^ 0xA8) + (0x99 ^ 0x91)), null) != BiomeGenBase.deepOcean) {
                return "".length() != 0;
            }
            if (this.worldObj.getWorldChunkManager().areBiomesViable(n3 * (0x61 ^ 0x71) + (0x65 ^ 0x6D), n4 * (0x86 ^ 0x96) + (0x8D ^ 0x85), 0x30 ^ 0x2D, StructureOceanMonument.field_175802_d)) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new StartMonument(this.worldObj, this.rand, n, n2);
    }
    
    public StructureOceanMonument(final Map<String, String> map) {
        this();
        final Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(StructureOceanMonument.I["".length()])) {
                this.field_175800_f = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_175800_f, " ".length());
                "".length();
                if (3 < 2) {
                    throw null;
                }
                continue;
            }
            else {
                if (!entry.getKey().equals(StructureOceanMonument.I[" ".length()])) {
                    continue;
                }
                this.field_175801_g = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_175801_g, " ".length());
            }
        }
    }
    
    static {
        I();
        final BiomeGenBase[] array = new BiomeGenBase[0x96 ^ 0x93];
        array["".length()] = BiomeGenBase.ocean;
        array[" ".length()] = BiomeGenBase.deepOcean;
        array["  ".length()] = BiomeGenBase.river;
        array["   ".length()] = BiomeGenBase.frozenOcean;
        array[0xB1 ^ 0xB5] = BiomeGenBase.frozenRiver;
        field_175802_d = Arrays.asList(array);
        (field_175803_h = Lists.newArrayList()).add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, " ".length(), "  ".length(), 0x94 ^ 0x90));
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0003\u00174.*\u001e\u0000", "pgUMC");
        StructureOceanMonument.I[" ".length()] = I(" \u001d>8+2\f'67", "SxNYY");
        StructureOceanMonument.I["  ".length()] = I("'\u000e\u001e \u0002\u000f\u000f\u0004", "japUo");
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static class StartMonument extends StructureStart
    {
        private static final String[] I;
        private boolean field_175790_d;
        private Set<ChunkCoordIntPair> field_175791_c;
        
        static {
            I();
        }
        
        @Override
        public void readFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readFromNBT(nbtTagCompound);
            if (nbtTagCompound.hasKey(StartMonument.I["   ".length()], 0x23 ^ 0x2A)) {
                final NBTTagList tagList = nbtTagCompound.getTagList(StartMonument.I[0x1C ^ 0x18], 0x73 ^ 0x79);
                int i = "".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (i < tagList.tagCount()) {
                    final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
                    this.field_175791_c.add(new ChunkCoordIntPair(compoundTag.getInteger(StartMonument.I[0xAD ^ 0xA8]), compoundTag.getInteger(StartMonument.I[0x8E ^ 0x88])));
                    ++i;
                }
            }
        }
        
        public StartMonument() {
            this.field_175791_c = (Set<ChunkCoordIntPair>)Sets.newHashSet();
        }
        
        private void func_175789_b(final World world, final Random random, final int n, final int n2) {
            random.setSeed(world.getSeed());
            random.setSeed(n * random.nextLong() ^ n2 * random.nextLong() ^ world.getSeed());
            this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(random, n * (0x75 ^ 0x65) + (0x5E ^ 0x56) - (0x89 ^ 0x94), n2 * (0x1A ^ 0xA) + (0x3A ^ 0x32) - (0x5E ^ 0x43), EnumFacing.Plane.HORIZONTAL.random(random)));
            this.updateBoundingBox();
            this.field_175790_d = (" ".length() != 0);
        }
        
        private static void I() {
            (I = new String[0xB6 ^ 0xB1])["".length()] = I("\u0017", "OOBYl");
            StartMonument.I[" ".length()] = I("9", "cFVuq");
            StartMonument.I["  ".length()] = I("&8\u001d&\f\u00059\u0017!", "vJrEi");
            StartMonument.I["   ".length()] = I("6?6\t\u0017\u0015><\u000e", "fMYjr");
            StartMonument.I[0x24 ^ 0x20] = I("$\u001a\u00066\u0006\u0007\u001b\f1", "thiUc");
            StartMonument.I[0x6F ^ 0x6A] = I("\u0015", "MZtdG");
            StartMonument.I[0x31 ^ 0x37] = I("\u0012", "HRjAb");
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeToNBT(nbtTagCompound);
            final NBTTagList list = new NBTTagList();
            final Iterator<ChunkCoordIntPair> iterator = this.field_175791_c.iterator();
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final ChunkCoordIntPair chunkCoordIntPair = iterator.next();
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setInteger(StartMonument.I["".length()], chunkCoordIntPair.chunkXPos);
                nbtTagCompound2.setInteger(StartMonument.I[" ".length()], chunkCoordIntPair.chunkZPos);
                list.appendTag(nbtTagCompound2);
            }
            nbtTagCompound.setTag(StartMonument.I["  ".length()], list);
        }
        
        public StartMonument(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            this.field_175791_c = (Set<ChunkCoordIntPair>)Sets.newHashSet();
            this.func_175789_b(world, random, n, n2);
        }
        
        @Override
        public boolean func_175788_a(final ChunkCoordIntPair chunkCoordIntPair) {
            int n;
            if (this.field_175791_c.contains(chunkCoordIntPair)) {
                n = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                n = (super.func_175788_a(chunkCoordIntPair) ? 1 : 0);
            }
            return n != 0;
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void func_175787_b(final ChunkCoordIntPair chunkCoordIntPair) {
            super.func_175787_b(chunkCoordIntPair);
            this.field_175791_c.add(chunkCoordIntPair);
        }
        
        @Override
        public void generateStructure(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.field_175790_d) {
                this.components.clear();
                this.func_175789_b(world, random, this.getChunkPosX(), this.getChunkPosZ());
            }
            super.generateStructure(world, random, structureBoundingBox);
        }
    }
}

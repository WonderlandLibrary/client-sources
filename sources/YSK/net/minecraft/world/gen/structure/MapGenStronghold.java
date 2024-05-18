package net.minecraft.world.gen.structure;

import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public class MapGenStronghold extends MapGenStructure
{
    private double field_82671_h;
    private boolean ranBiomeCheck;
    private int field_82672_i;
    private List<BiomeGenBase> field_151546_e;
    private static final String[] I;
    private ChunkCoordIntPair[] structureCoords;
    
    @Override
    protected List<BlockPos> getCoordList() {
        final ArrayList arrayList = Lists.newArrayList();
        final ChunkCoordIntPair[] structureCoords;
        final int length = (structureCoords = this.structureCoords).length;
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < length) {
            final ChunkCoordIntPair chunkCoordIntPair = structureCoords[i];
            if (chunkCoordIntPair != null) {
                arrayList.add(chunkCoordIntPair.getCenterBlock(0xEB ^ 0xAB));
            }
            ++i;
        }
        return (List<BlockPos>)arrayList;
    }
    
    public MapGenStronghold(final Map<String, String> map) {
        this();
        final Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(MapGenStronghold.I["".length()])) {
                this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax(entry.getValue(), this.field_82671_h, 1.0);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                continue;
            }
            else if (entry.getKey().equals(MapGenStronghold.I[" ".length()])) {
                this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.structureCoords.length, " ".length())];
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                continue;
            }
            else {
                if (!entry.getKey().equals(MapGenStronghold.I["  ".length()])) {
                    continue;
                }
                this.field_82672_i = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_82672_i, " ".length());
            }
        }
    }
    
    private static void I() {
        (I = new String[0x72 ^ 0x76])["".length()] = I("\u0010\b#$\u0010\u001a\u00025", "taPPq");
        MapGenStronghold.I[" ".length()] = I("\u0012\u0016\u000f\u0001\u0010", "qyzod");
        MapGenStronghold.I["  ".length()] = I("*\"76\u0004=", "YRESe");
        MapGenStronghold.I["   ".length()] = I("9\u00151\u0018\u0005\r\t,\u001b\u000f", "jaCwk");
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int n, final int n2) {
        if (!this.ranBiomeCheck) {
            final Random random = new Random();
            random.setSeed(this.worldObj.getSeed());
            double n3 = random.nextDouble() * 3.141592653589793 * 2.0;
            int length = " ".length();
            int i = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (i < this.structureCoords.length) {
                final double n4 = (1.25 * length + random.nextDouble()) * this.field_82671_h * length;
                int n5 = (int)Math.round(Math.cos(n3) * n4);
                int n6 = (int)Math.round(Math.sin(n3) * n4);
                final BlockPos biomePosition = this.worldObj.getWorldChunkManager().findBiomePosition((n5 << (0x46 ^ 0x42)) + (0x9E ^ 0x96), (n6 << (0x15 ^ 0x11)) + (0x73 ^ 0x7B), 0x69 ^ 0x19, this.field_151546_e, random);
                if (biomePosition != null) {
                    n5 = biomePosition.getX() >> (0xA8 ^ 0xAC);
                    n6 = biomePosition.getZ() >> (0x76 ^ 0x72);
                }
                this.structureCoords[i] = new ChunkCoordIntPair(n5, n6);
                n3 += 6.283185307179586 * length / this.field_82672_i;
                if (i == this.field_82672_i) {
                    length += "  ".length() + random.nextInt(0xB5 ^ 0xB0);
                    this.field_82672_i += " ".length() + random.nextInt("  ".length());
                }
                ++i;
            }
            this.ranBiomeCheck = (" ".length() != 0);
        }
        final ChunkCoordIntPair[] structureCoords;
        final int length2 = (structureCoords = this.structureCoords).length;
        int j = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (j < length2) {
            final ChunkCoordIntPair chunkCoordIntPair = structureCoords[j];
            if (n == chunkCoordIntPair.chunkXPos && n2 == chunkCoordIntPair.chunkZPos) {
                return " ".length() != 0;
            }
            ++j;
        }
        return "".length() != 0;
    }
    
    static {
        I();
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        Start start = new Start(this.worldObj, this.rand, n, n2);
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (start.getComponents().isEmpty() || ((StructureStrongholdPieces.Stairs2)start.getComponents().get("".length())).strongholdPortalRoom == null) {
            start = new Start(this.worldObj, this.rand, n, n2);
        }
        return start;
    }
    
    @Override
    public String getStructureName() {
        return MapGenStronghold.I["   ".length()];
    }
    
    public MapGenStronghold() {
        this.structureCoords = new ChunkCoordIntPair["   ".length()];
        this.field_82671_h = 32.0;
        this.field_82672_i = "   ".length();
        this.field_151546_e = (List<BiomeGenBase>)Lists.newArrayList();
        final BiomeGenBase[] biomeGenArray;
        final int length = (biomeGenArray = BiomeGenBase.getBiomeGenArray()).length;
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < length) {
            final BiomeGenBase biomeGenBase = biomeGenArray[i];
            if (biomeGenBase != null && biomeGenBase.minHeight > 0.0f) {
                this.field_151546_e.add(biomeGenBase);
            }
            ++i;
        }
    }
    
    public static class Start extends StructureStart
    {
        public Start() {
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
                if (4 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Start(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            StructureStrongholdPieces.prepareStructurePieces();
            final StructureStrongholdPieces.Stairs2 stairs2 = new StructureStrongholdPieces.Stairs2("".length(), random, (n << (0x4C ^ 0x48)) + "  ".length(), (n2 << (0x40 ^ 0x44)) + "  ".length());
            this.components.add(stairs2);
            stairs2.buildComponent(stairs2, this.components, random);
            final List<StructureComponent> field_75026_c = stairs2.field_75026_c;
            "".length();
            if (false) {
                throw null;
            }
            while (!field_75026_c.isEmpty()) {
                field_75026_c.remove(random.nextInt(field_75026_c.size())).buildComponent(stairs2, this.components, random);
            }
            this.updateBoundingBox();
            this.markAvailableHeight(world, random, 0x18 ^ 0x12);
        }
    }
}

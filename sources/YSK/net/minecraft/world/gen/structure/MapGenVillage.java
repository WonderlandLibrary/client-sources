package net.minecraft.world.gen.structure;

import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class MapGenVillage extends MapGenStructure
{
    private int field_82665_g;
    public static final List<BiomeGenBase> villageSpawnBiomes;
    private int field_82666_h;
    private int terrainType;
    private static final String[] I;
    
    @Override
    protected boolean canSpawnStructureAtCoords(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.field_82665_g - " ".length();
        }
        if (n2 < 0) {
            n2 -= this.field_82665_g - " ".length();
        }
        final int n5 = n / this.field_82665_g;
        final int n6 = n2 / this.field_82665_g;
        final Random setRandomSeed = this.worldObj.setRandomSeed(n5, n6, 10275578 + 727773 - 4712759 + 4096720);
        final int n7 = n5 * this.field_82665_g;
        final int n8 = n6 * this.field_82665_g;
        final int n9 = n7 + setRandomSeed.nextInt(this.field_82665_g - this.field_82666_h);
        final int n10 = n8 + setRandomSeed.nextInt(this.field_82665_g - this.field_82666_h);
        if (n3 == n9 && n4 == n10 && this.worldObj.getWorldChunkManager().areBiomesViable(n3 * (0xBA ^ 0xAA) + (0x8F ^ 0x87), n4 * (0x83 ^ 0x93) + (0x7A ^ 0x72), "".length(), MapGenVillage.villageSpawnBiomes)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new Start(this.worldObj, this.rand, n, n2, this.terrainType);
    }
    
    @Override
    public String getStructureName() {
        return MapGenVillage.I["  ".length()];
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\n\u0006\r\t", "yowlK");
        MapGenVillage.I[" ".length()] = I("\u000b\u001a;.2\u0001\u0010-", "osHZS");
        MapGenVillage.I["  ".length()] = I("\u0015\u00026\u0016#$\u000e", "CkZzB");
    }
    
    public MapGenVillage() {
        this.field_82665_g = (0xB1 ^ 0x91);
        this.field_82666_h = (0x45 ^ 0x4D);
    }
    
    public MapGenVillage(final Map<String, String> map) {
        this();
        final Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(MapGenVillage.I["".length()])) {
                this.terrainType = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.terrainType, "".length());
                "".length();
                if (true != true) {
                    throw null;
                }
                continue;
            }
            else {
                if (!entry.getKey().equals(MapGenVillage.I[" ".length()])) {
                    continue;
                }
                this.field_82665_g = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_82665_g, this.field_82666_h + " ".length());
            }
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
    
    static {
        I();
        final BiomeGenBase[] array = new BiomeGenBase["   ".length()];
        array["".length()] = BiomeGenBase.plains;
        array[" ".length()] = BiomeGenBase.desert;
        array["  ".length()] = BiomeGenBase.savanna;
        villageSpawnBiomes = Arrays.asList(array);
    }
    
    public static class Start extends StructureStart
    {
        private boolean hasMoreThanTwoComponents;
        private static final String[] I;
        
        static {
            I();
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Start.I["".length()], this.hasMoreThanTwoComponents);
        }
        
        public Start() {
        }
        
        @Override
        public void readFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readFromNBT(nbtTagCompound);
            this.hasMoreThanTwoComponents = nbtTagCompound.getBoolean(Start.I[" ".length()]);
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
                if (0 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean isSizeableStructure() {
            return this.hasMoreThanTwoComponents;
        }
        
        public Start(final World world, final Random random, final int n, final int n2, final int n3) {
            super(n, n2);
            final StructureVillagePieces.Start start = new StructureVillagePieces.Start(world.getWorldChunkManager(), "".length(), random, (n << (0x96 ^ 0x92)) + "  ".length(), (n2 << (0x6E ^ 0x6A)) + "  ".length(), StructureVillagePieces.getStructureVillageWeightedPieceList(random, n3), n3);
            this.components.add(start);
            start.buildComponent(start, this.components, random);
            final List<StructureComponent> field_74930_j = start.field_74930_j;
            final List<StructureComponent> field_74932_i = start.field_74932_i;
            "".length();
            if (0 < -1) {
                throw null;
            }
            while (!field_74930_j.isEmpty() || !field_74932_i.isEmpty()) {
                if (field_74930_j.isEmpty()) {
                    field_74932_i.remove(random.nextInt(field_74932_i.size())).buildComponent(start, this.components, random);
                    "".length();
                    if (false) {
                        throw null;
                    }
                    continue;
                }
                else {
                    field_74930_j.remove(random.nextInt(field_74930_j.size())).buildComponent(start, this.components, random);
                }
            }
            this.updateBoundingBox();
            int length = "".length();
            final Iterator<StructureComponent> iterator = (Iterator<StructureComponent>)this.components.iterator();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (!(iterator.next() instanceof StructureVillagePieces.Road)) {
                    ++length;
                }
            }
            int hasMoreThanTwoComponents;
            if (length > "  ".length()) {
                hasMoreThanTwoComponents = " ".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                hasMoreThanTwoComponents = "".length();
            }
            this.hasMoreThanTwoComponents = (hasMoreThanTwoComponents != 0);
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u000f8\u0000\u001b2", "YYlrV");
            Start.I[" ".length()] = I("$\n\u000e&*", "rkbON");
        }
    }
}

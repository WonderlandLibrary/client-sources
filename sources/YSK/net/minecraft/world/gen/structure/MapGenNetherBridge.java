package net.minecraft.world.gen.structure;

import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import java.util.*;

public class MapGenNetherBridge extends MapGenStructure
{
    private List<BiomeGenBase.SpawnListEntry> spawnList;
    private static final String[] I;
    
    @Override
    public String getStructureName() {
        return MapGenNetherBridge.I["".length()];
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0014\u0004\u001b#+7\u0018\u001a", "RkiWY");
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int n, final int n2) {
        final int n3 = n >> (0x6C ^ 0x68);
        final int n4 = n2 >> (0x8 ^ 0xC);
        this.rand.setSeed(n3 ^ n4 << (0x1A ^ 0x1E) ^ this.worldObj.getSeed());
        this.rand.nextInt();
        int n5;
        if (this.rand.nextInt("   ".length()) != 0) {
            n5 = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (n != (n3 << (0xA7 ^ 0xA3)) + (0x86 ^ 0x82) + this.rand.nextInt(0x75 ^ 0x7D)) {
            n5 = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (n2 == (n4 << (0x30 ^ 0x34)) + (0xC5 ^ 0xC1) + this.rand.nextInt(0x26 ^ 0x2E)) {
            n5 = " ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        return n5 != 0;
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new Start(this.worldObj, this.rand, n, n2);
    }
    
    public List<BiomeGenBase.SpawnListEntry> getSpawnList() {
        return this.spawnList;
    }
    
    static {
        I();
    }
    
    public MapGenNetherBridge() {
        (this.spawnList = (List<BiomeGenBase.SpawnListEntry>)Lists.newArrayList()).add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 0x29 ^ 0x23, "  ".length(), "   ".length()));
        this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 0x61 ^ 0x64, 0xBA ^ 0xBE, 0x4 ^ 0x0));
        this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 0x3E ^ 0x34, 0x24 ^ 0x20, 0xBB ^ 0xBF));
        this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, "   ".length(), 0xAD ^ 0xA9, 0x5F ^ 0x5B));
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static class Start extends StructureStart
    {
        public Start() {
        }
        
        public Start(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            final StructureNetherBridgePieces.Start start = new StructureNetherBridgePieces.Start(random, (n << (0x47 ^ 0x43)) + "  ".length(), (n2 << (0x5E ^ 0x5A)) + "  ".length());
            this.components.add(start);
            start.buildComponent(start, this.components, random);
            final List<StructureComponent> field_74967_d = start.field_74967_d;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
            while (!field_74967_d.isEmpty()) {
                field_74967_d.remove(random.nextInt(field_74967_d.size())).buildComponent(start, this.components, random);
            }
            this.updateBoundingBox();
            this.setRandomHeight(world, random, 0x50 ^ 0x60, 0x37 ^ 0x71);
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
                if (2 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}

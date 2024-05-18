package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class BiomeGenMushroomIsland extends BiomeGenBase
{
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BiomeGenMushroomIsland(final int n) {
        super(n);
        this.theBiomeDecorator.treesPerChunk = -(0x3A ^ 0x5E);
        this.theBiomeDecorator.flowersPerChunk = -(0x33 ^ 0x57);
        this.theBiomeDecorator.grassPerChunk = -(0x75 ^ 0x11);
        this.theBiomeDecorator.mushroomsPerChunk = " ".length();
        this.theBiomeDecorator.bigMushroomsPerChunk = " ".length();
        this.topBlock = Blocks.mycelium.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityMooshroom.class, 0xA6 ^ 0xAE, 0x3 ^ 0x7, 0x5F ^ 0x57));
    }
}

package net.minecraft.world.biome;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public class BiomeGenHell extends BiomeGenBase
{
    public BiomeGenHell(final int n) {
        super(n);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 0x41 ^ 0x73, 0x16 ^ 0x12, 0x1D ^ 0x19));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 0x46 ^ 0x22, 0x22 ^ 0x26, 0x71 ^ 0x75));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, " ".length(), 0x9B ^ 0x9F, 0x1 ^ 0x5));
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}

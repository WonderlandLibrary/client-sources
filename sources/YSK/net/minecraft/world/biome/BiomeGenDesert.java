package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenDesert extends BiomeGenBase
{
    public BiomeGenDesert(final int n) {
        super(n);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState();
        this.fillerBlock = Blocks.sand.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -(667 + 429 - 198 + 101);
        this.theBiomeDecorator.deadBushPerChunk = "  ".length();
        this.theBiomeDecorator.reedsPerChunk = (0xBE ^ 0x8C);
        this.theBiomeDecorator.cactiPerChunk = (0x52 ^ 0x58);
        this.spawnableCreatureList.clear();
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        if (random.nextInt(124 + 574 - 106 + 408) == 0) {
            new WorldGenDesertWells().generate(world, random, world.getHeight(blockPos.add(random.nextInt(0x1C ^ 0xC) + (0x27 ^ 0x2F), "".length(), random.nextInt(0x4C ^ 0x5C) + (0x84 ^ 0x8C))).up());
        }
    }
}

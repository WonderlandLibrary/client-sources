package net.minecraft.world.biome;

import java.util.*;
import net.minecraft.world.gen.feature.*;

class BiomeGenForest$2 extends BiomeGenMutated {
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random p_150567_1_) {
        return p_150567_1_.nextBoolean() ? BiomeGenForest.field_150629_aC : BiomeGenForest.field_150630_aD;
    }
}
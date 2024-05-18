package net.minecraft.world.biome;

import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.chunk.*;
import java.util.*;

public class BiomeGenMutated extends BiomeGenBase
{
    private static final String[] I;
    protected BiomeGenBase baseBiome;
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        this.baseBiome.theBiomeDecorator.decorate(world, random, this, blockPos);
    }
    
    @Override
    public TempCategory getTempCategory() {
        return this.baseBiome.getTempCategory();
    }
    
    public BiomeGenMutated(final int n, final BiomeGenBase baseBiome) {
        super(n);
        this.baseBiome = baseBiome;
        this.func_150557_a(baseBiome.color, " ".length() != 0);
        this.biomeName = String.valueOf(baseBiome.biomeName) + BiomeGenMutated.I["".length()];
        this.topBlock = baseBiome.topBlock;
        this.fillerBlock = baseBiome.fillerBlock;
        this.fillerBlockMetadata = baseBiome.fillerBlockMetadata;
        this.minHeight = baseBiome.minHeight;
        this.maxHeight = baseBiome.maxHeight;
        this.temperature = baseBiome.temperature;
        this.rainfall = baseBiome.rainfall;
        this.waterColorMultiplier = baseBiome.waterColorMultiplier;
        this.enableSnow = baseBiome.enableSnow;
        this.enableRain = baseBiome.enableRain;
        this.spawnableCreatureList = (List<SpawnListEntry>)Lists.newArrayList((Iterable)baseBiome.spawnableCreatureList);
        this.spawnableMonsterList = (List<SpawnListEntry>)Lists.newArrayList((Iterable)baseBiome.spawnableMonsterList);
        this.spawnableCaveCreatureList = (List<SpawnListEntry>)Lists.newArrayList((Iterable)baseBiome.spawnableCaveCreatureList);
        this.spawnableWaterCreatureList = (List<SpawnListEntry>)Lists.newArrayList((Iterable)baseBiome.spawnableWaterCreatureList);
        this.temperature = baseBiome.temperature;
        this.rainfall = baseBiome.rainfall;
        this.minHeight = baseBiome.minHeight + 0.1f;
        this.maxHeight = baseBiome.maxHeight + 0.2f;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("J$", "jiJPr");
    }
    
    @Override
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return this.baseBiome.getFoliageColorAtPos(blockPos);
    }
    
    static {
        I();
    }
    
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        return this.baseBiome.getGrassColorAtPos(blockPos);
    }
    
    @Override
    public boolean isEqualTo(final BiomeGenBase biomeGenBase) {
        return this.baseBiome.isEqualTo(biomeGenBase);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return this.baseBiome.genBigTreeChance(random);
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.baseBiome.genTerrainBlocks(world, random, chunkPrimer, n, n2, n3);
    }
    
    @Override
    public float getSpawningChance() {
        return this.baseBiome.getSpawningChance();
    }
    
    @Override
    public Class<? extends BiomeGenBase> getBiomeClass() {
        return this.baseBiome.getBiomeClass();
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}

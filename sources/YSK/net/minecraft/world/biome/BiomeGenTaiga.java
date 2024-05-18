package net.minecraft.world.biome;

import net.minecraft.init.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class BiomeGenTaiga extends BiomeGenBase
{
    private static final WorldGenTaiga1 field_150639_aC;
    private static final WorldGenBlockBlob field_150643_aG;
    private static final WorldGenTaiga2 field_150640_aD;
    private int field_150644_aH;
    private static final WorldGenMegaPineTree field_150641_aE;
    private static final String[] I;
    private static final WorldGenMegaPineTree field_150642_aF;
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        BiomeGenBase biomeGenBase;
        if (this.biomeID == BiomeGenBase.megaTaiga.biomeID) {
            biomeGenBase = new BiomeGenTaiga(n, "  ".length()).func_150557_a(3701112 + 4063971 - 4607574 + 2701388, " ".length() != 0).setBiomeName(BiomeGenTaiga.I["".length()]).setFillerBlockMetadata(3309054 + 2624827 - 4326525 + 3552117).setTemperatureRainfall(0.25f, 0.8f).setHeight(new Height(this.minHeight, this.maxHeight));
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            biomeGenBase = super.createMutatedBiome(n);
        }
        return biomeGenBase;
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        field_150639_aC = new WorldGenTaiga1();
        field_150640_aD = new WorldGenTaiga2("".length() != 0);
        field_150641_aE = new WorldGenMegaPineTree("".length() != 0, "".length() != 0);
        field_150642_aF = new WorldGenMegaPineTree("".length() != 0, " ".length() != 0);
        field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, "".length());
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        WorldGenAbstractTree worldGenAbstractTree;
        if ((this.field_150644_aH == " ".length() || this.field_150644_aH == "  ".length()) && random.nextInt("   ".length()) == 0) {
            if (this.field_150644_aH != "  ".length() && random.nextInt(0x89 ^ 0x84) != 0) {
                worldGenAbstractTree = BiomeGenTaiga.field_150641_aE;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                worldGenAbstractTree = BiomeGenTaiga.field_150642_aF;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
        }
        else if (random.nextInt("   ".length()) == 0) {
            worldGenAbstractTree = BiomeGenTaiga.field_150639_aC;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            worldGenAbstractTree = BiomeGenTaiga.field_150640_aD;
        }
        return worldGenAbstractTree;
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        if (this.field_150644_aH == " ".length() || this.field_150644_aH == "  ".length()) {
            final int nextInt = random.nextInt("   ".length());
            int i = "".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (i < nextInt) {
                BiomeGenTaiga.field_150643_aG.generate(world, random, world.getHeight(blockPos.add(random.nextInt(0x60 ^ 0x70) + (0x5C ^ 0x54), "".length(), random.nextInt(0xBF ^ 0xAF) + (0x6E ^ 0x66))));
                ++i;
            }
        }
        BiomeGenTaiga.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);
        int j = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (j < (0x2C ^ 0x2B)) {
            final int n = random.nextInt(0x1 ^ 0x11) + (0x69 ^ 0x61);
            final int n2 = random.nextInt(0xB5 ^ 0xA5) + (0x7B ^ 0x73);
            BiomeGenTaiga.DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(n, random.nextInt(world.getHeight(blockPos.add(n, "".length(), n2)).getY() + (0x4A ^ 0x6A)), n2));
            ++j;
        }
        super.decorate(world, random, blockPos);
    }
    
    public BiomeGenTaiga(final int n, final int field_150644_aH) {
        super(n);
        this.field_150644_aH = field_150644_aH;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 0x79 ^ 0x71, 0x71 ^ 0x75, 0x15 ^ 0x11));
        this.theBiomeDecorator.treesPerChunk = (0x93 ^ 0x99);
        if (field_150644_aH != " ".length() && field_150644_aH != "  ".length()) {
            this.theBiomeDecorator.grassPerChunk = " ".length();
            this.theBiomeDecorator.mushroomsPerChunk = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            this.theBiomeDecorator.grassPerChunk = (0x93 ^ 0x94);
            this.theBiomeDecorator.deadBushPerChunk = " ".length();
            this.theBiomeDecorator.mushroomsPerChunk = "   ".length();
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        WorldGenTallGrass worldGenTallGrass;
        if (random.nextInt(0x64 ^ 0x61) > 0) {
            worldGenTallGrass = new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            worldGenTallGrass = new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
        }
        return worldGenTallGrass;
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        if (this.field_150644_aH == " ".length() || this.field_150644_aH == "  ".length()) {
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            if (n3 > 1.75) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else if (n3 > -0.95) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
            }
        }
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0007&\u0011#v\u00193\u000475/c\"#?-\"", "JCvBV");
    }
}

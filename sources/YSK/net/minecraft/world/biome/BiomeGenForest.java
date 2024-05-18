package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;

public class BiomeGenForest extends BiomeGenBase
{
    private int field_150632_aF;
    protected static final WorldGenCanopyTree field_150631_aE;
    protected static final WorldGenForest field_150630_aD;
    private static final String[] I;
    protected static final WorldGenForest field_150629_aC;
    
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        if (this.biomeID == BiomeGenBase.forest.biomeID) {
            final BiomeGenForest biomeGenForest = new BiomeGenForest(n, " ".length());
            biomeGenForest.setHeight(new Height(this.minHeight, this.maxHeight + 0.2f));
            biomeGenForest.setBiomeName(BiomeGenForest.I["".length()]);
            biomeGenForest.func_150557_a(6546908 + 374747 - 5153379 + 5208273, " ".length() != 0);
            biomeGenForest.setFillerBlockMetadata(5685580 + 7921137 - 12267857 + 6894649);
            return biomeGenForest;
        }
        BiomeGenMutated biomeGenMutated;
        if (this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID) {
            biomeGenMutated = new BiomeGenMutated(this, n, this) {
                final BiomeGenForest this$0;
                
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
                        if (1 < -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public void decorate(final World world, final Random random, final BlockPos blockPos) {
                    this.baseBiome.decorate(world, random, blockPos);
                }
            };
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            biomeGenMutated = new BiomeGenMutated(this, n, this) {
                final BiomeGenForest this$0;
                
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
                
                @Override
                public WorldGenAbstractTree genBigTreeChance(final Random random) {
                    WorldGenForest worldGenForest;
                    if (random.nextBoolean()) {
                        worldGenForest = BiomeGenForest.field_150629_aC;
                        "".length();
                        if (0 == 3) {
                            throw null;
                        }
                    }
                    else {
                        worldGenForest = BiomeGenForest.field_150630_aD;
                    }
                    return worldGenForest;
                }
            };
        }
        return biomeGenMutated;
    }
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        if (this.field_150632_aF == " ".length()) {
            final BlockFlower.EnumFlowerType enumFlowerType = BlockFlower.EnumFlowerType.values()[(int)(MathHelper.clamp_double((1.0 + BiomeGenForest.GRASS_COLOR_NOISE.func_151601_a(blockPos.getX() / 48.0, blockPos.getZ() / 48.0)) / 2.0, 0.0, 0.9999) * BlockFlower.EnumFlowerType.values().length)];
            BlockFlower.EnumFlowerType poppy;
            if (enumFlowerType == BlockFlower.EnumFlowerType.BLUE_ORCHID) {
                poppy = BlockFlower.EnumFlowerType.POPPY;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                poppy = enumFlowerType;
            }
            return poppy;
        }
        return super.pickRandomFlower(random, blockPos);
    }
    
    static {
        I();
        field_150629_aC = new WorldGenForest("".length() != 0, " ".length() != 0);
        field_150630_aD = new WorldGenForest("".length() != 0, "".length() != 0);
        field_150631_aE = new WorldGenCanopyTree("".length() != 0);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0002\u0014)\u001a\u00016X\u0000\u0002\u0016!\u000b2", "DxFmd");
    }
    
    public BiomeGenForest(final int n, final int field_150632_aF) {
        super(n);
        this.field_150632_aF = field_150632_aF;
        this.theBiomeDecorator.treesPerChunk = (0xA7 ^ 0xAD);
        this.theBiomeDecorator.grassPerChunk = "  ".length();
        if (this.field_150632_aF == " ".length()) {
            this.theBiomeDecorator.treesPerChunk = (0x8 ^ 0xE);
            this.theBiomeDecorator.flowersPerChunk = (0xCE ^ 0xAA);
            this.theBiomeDecorator.grassPerChunk = " ".length();
        }
        this.setFillerBlockMetadata(2185394 + 471688 + 1324812 + 1177579);
        this.setTemperatureRainfall(0.7f, 0.8f);
        if (this.field_150632_aF == "  ".length()) {
            this.field_150609_ah = 43079 + 179316 + 86430 + 45000;
            this.color = 2425537 + 195970 + 104556 + 449429;
            this.setTemperatureRainfall(0.6f, 0.6f);
        }
        if (this.field_150632_aF == 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 0x94 ^ 0x91, 0x86 ^ 0x82, 0xC2 ^ 0xC6));
        }
        if (this.field_150632_aF == "   ".length()) {
            this.theBiomeDecorator.treesPerChunk = -(793 + 246 - 226 + 186);
        }
    }
    
    @Override
    protected BiomeGenBase func_150557_a(final int color, final boolean b) {
        if (this.field_150632_aF == "  ".length()) {
            this.field_150609_ah = 85952 + 94531 - 70910 + 244252;
            this.color = color;
            if (b) {
                this.field_150609_ah = (this.field_150609_ah & 14414197 + 8095348 - 18240599 + 12442476) >> " ".length();
            }
            return this;
        }
        return super.func_150557_a(color, b);
    }
    
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        final int grassColorAtPos = super.getGrassColorAtPos(blockPos);
        int n;
        if (this.field_150632_aF == "   ".length()) {
            n = (grassColorAtPos & 14083943 + 1623575 - 13288616 + 14292520) + (368641 + 2309693 - 2565173 + 2521601) >> " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = grassColorAtPos;
        }
        return n;
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        if (this.field_150632_aF == "   ".length()) {
            int i = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (i < (0x8E ^ 0x8A)) {
                int j = "".length();
                "".length();
                if (-1 == 3) {
                    throw null;
                }
                while (j < (0x6A ^ 0x6E)) {
                    final BlockPos height = world.getHeight(blockPos.add(i * (0xBC ^ 0xB8) + " ".length() + (0x5D ^ 0x55) + random.nextInt("   ".length()), "".length(), j * (0xD ^ 0x9) + " ".length() + (0xB5 ^ 0xBD) + random.nextInt("   ".length())));
                    if (random.nextInt(0x7A ^ 0x6E) == 0) {
                        new WorldGenBigMushroom().generate(world, random, height);
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        final WorldGenAbstractTree genBigTreeChance = this.genBigTreeChance(random);
                        genBigTreeChance.func_175904_e();
                        if (genBigTreeChance.generate(world, random, height)) {
                            genBigTreeChance.func_180711_a(world, random, height);
                        }
                    }
                    ++j;
                }
                ++i;
            }
        }
        int n = random.nextInt(0x36 ^ 0x33) - "   ".length();
        if (this.field_150632_aF == " ".length()) {
            n += 2;
        }
        int k = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (k < n) {
            final int nextInt = random.nextInt("   ".length());
            if (nextInt == 0) {
                BiomeGenForest.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (nextInt == " ".length()) {
                BiomeGenForest.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else if (nextInt == "  ".length()) {
                BiomeGenForest.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }
            int l = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (l < (0xC0 ^ 0xC5)) {
                final int n2 = random.nextInt(0x13 ^ 0x3) + (0x9F ^ 0x97);
                final int n3 = random.nextInt(0x90 ^ 0x80) + (0x94 ^ 0x9C);
                if (BiomeGenForest.DOUBLE_PLANT_GENERATOR.generate(world, random, new BlockPos(blockPos.getX() + n2, random.nextInt(world.getHeight(blockPos.add(n2, "".length(), n3)).getY() + (0x6B ^ 0x4B)), blockPos.getZ() + n3))) {
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++l;
                }
            }
            ++k;
        }
        super.decorate(world, random, blockPos);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        WorldGenAbstractTree worldGenAbstractTree;
        if (this.field_150632_aF == "   ".length() && random.nextInt("   ".length()) > 0) {
            worldGenAbstractTree = BiomeGenForest.field_150631_aE;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (this.field_150632_aF != "  ".length() && random.nextInt(0x95 ^ 0x90) != 0) {
            worldGenAbstractTree = this.worldGeneratorTrees;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            worldGenAbstractTree = BiomeGenForest.field_150630_aD;
        }
        return worldGenAbstractTree;
    }
}

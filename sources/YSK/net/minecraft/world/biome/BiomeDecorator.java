package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.world.gen.feature.*;

public class BiomeDecorator
{
    protected WorldGenerator gravelGen;
    protected World currentWorld;
    protected Random randomGenerator;
    protected int mushroomsPerChunk;
    protected int grassPerChunk;
    protected WorldGenerator sandGen;
    protected int sandPerChunk2;
    protected int deadBushPerChunk;
    protected int reedsPerChunk;
    protected int bigMushroomsPerChunk;
    public boolean generateLakes;
    protected WorldGenerator mushroomBrownGen;
    protected WorldGenFlowers yellowFlowerGen;
    protected int treesPerChunk;
    protected WorldGenerator diamondGen;
    protected int flowersPerChunk;
    protected WorldGenerator reedGen;
    protected WorldGenerator dioriteGen;
    protected int sandPerChunk;
    private static final String[] I;
    protected WorldGenerator gravelAsSandGen;
    protected WorldGenerator coalGen;
    protected WorldGenerator clayGen;
    protected WorldGenerator dirtGen;
    protected WorldGenerator redstoneGen;
    protected WorldGenerator ironGen;
    protected ChunkProviderSettings chunkProviderSettings;
    protected BlockPos field_180294_c;
    protected WorldGenerator graniteGen;
    protected WorldGenerator lapisGen;
    protected WorldGenerator waterlilyGen;
    protected WorldGenerator mushroomRedGen;
    protected WorldGenerator andesiteGen;
    protected int clayPerChunk;
    protected int waterlilyPerChunk;
    protected WorldGenerator bigMushroomGen;
    protected WorldGenerator cactusGen;
    protected int cactiPerChunk;
    protected WorldGenerator goldGen;
    
    public BiomeDecorator() {
        this.clayGen = new WorldGenClay(0x47 ^ 0x43);
        this.sandGen = new WorldGenSand(Blocks.sand, 0xB5 ^ 0xB2);
        this.gravelAsSandGen = new WorldGenSand(Blocks.gravel, 0x43 ^ 0x45);
        this.yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
        this.mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
        this.mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
        this.bigMushroomGen = new WorldGenBigMushroom();
        this.reedGen = new WorldGenReed();
        this.cactusGen = new WorldGenCactus();
        this.waterlilyGen = new WorldGenWaterlily();
        this.flowersPerChunk = "  ".length();
        this.grassPerChunk = " ".length();
        this.sandPerChunk = " ".length();
        this.sandPerChunk2 = "   ".length();
        this.clayPerChunk = " ".length();
        this.generateLakes = (" ".length() != 0);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0014\u000f;\u000201\u001ai\u000346\f;\u0006%<\r.", "UcIgQ");
        BiomeDecorator.I[" ".length()] = I("", "CPEQl");
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
    
    protected void genStandardOre2(final int n, final WorldGenerator worldGenerator, final int n2, final int n3) {
        int i = "".length();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (i < n) {
            worldGenerator.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(this.randomGenerator.nextInt(0x31 ^ 0x21), this.randomGenerator.nextInt(n3) + this.randomGenerator.nextInt(n3) + n2 - n3, this.randomGenerator.nextInt(0xD4 ^ 0xC4)));
            ++i;
        }
    }
    
    public void decorate(final World currentWorld, final Random randomGenerator, final BiomeGenBase biomeGenBase, final BlockPos field_180294_c) {
        if (this.currentWorld != null) {
            throw new RuntimeException(BiomeDecorator.I["".length()]);
        }
        this.currentWorld = currentWorld;
        final String generatorOptions = currentWorld.getWorldInfo().getGeneratorOptions();
        if (generatorOptions != null) {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(generatorOptions).func_177864_b();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(BiomeDecorator.I[" ".length()]).func_177864_b();
        }
        this.randomGenerator = randomGenerator;
        this.field_180294_c = field_180294_c;
        this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
        this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
        this.graniteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
        this.dioriteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
        this.andesiteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
        this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
        this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
        this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
        this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
        this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
        this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
        this.genDecorations(biomeGenBase);
        this.currentWorld = null;
        this.randomGenerator = null;
    }
    
    protected void genStandardOre1(final int n, final WorldGenerator worldGenerator, int n2, int n3) {
        if (n3 < n2) {
            final int n4 = n2;
            n2 = n3;
            n3 = n4;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else if (n3 == n2) {
            if (n2 < 113 + 202 - 230 + 170) {
                ++n3;
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else {
                --n2;
            }
        }
        int i = "".length();
        "".length();
        if (!true) {
            throw null;
        }
        while (i < n) {
            worldGenerator.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(this.randomGenerator.nextInt(0x75 ^ 0x65), this.randomGenerator.nextInt(n3 - n2) + n2, this.randomGenerator.nextInt(0x2B ^ 0x3B)));
            ++i;
        }
    }
    
    protected void genDecorations(final BiomeGenBase biomeGenBase) {
        this.generateOres();
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < this.sandPerChunk2) {
            this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(this.randomGenerator.nextInt(0x60 ^ 0x70) + (0x3D ^ 0x35), "".length(), this.randomGenerator.nextInt(0x86 ^ 0x96) + (0x12 ^ 0x1A))));
            ++i;
        }
        int j = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (j < this.clayPerChunk) {
            this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(this.randomGenerator.nextInt(0x1F ^ 0xF) + (0x27 ^ 0x2F), "".length(), this.randomGenerator.nextInt(0xD7 ^ 0xC7) + (0x17 ^ 0x1F))));
            ++j;
        }
        int k = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (k < this.sandPerChunk) {
            this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(this.randomGenerator.nextInt(0x8A ^ 0x9A) + (0x58 ^ 0x50), "".length(), this.randomGenerator.nextInt(0x84 ^ 0x94) + (0x47 ^ 0x4F))));
            ++k;
        }
        int treesPerChunk = this.treesPerChunk;
        if (this.randomGenerator.nextInt(0x91 ^ 0x9B) == 0) {
            ++treesPerChunk;
        }
        int l = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (l < treesPerChunk) {
            final int n = this.randomGenerator.nextInt(0x33 ^ 0x23) + (0x14 ^ 0x1C);
            final int n2 = this.randomGenerator.nextInt(0xB4 ^ 0xA4) + (0x1F ^ 0x17);
            final WorldGenAbstractTree genBigTreeChance = biomeGenBase.genBigTreeChance(this.randomGenerator);
            genBigTreeChance.func_175904_e();
            final BlockPos height = this.currentWorld.getHeight(this.field_180294_c.add(n, "".length(), n2));
            if (genBigTreeChance.generate(this.currentWorld, this.randomGenerator, height)) {
                genBigTreeChance.func_180711_a(this.currentWorld, this.randomGenerator, height);
            }
            ++l;
        }
        int length = "".length();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (length < this.bigMushroomsPerChunk) {
            this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.add(this.randomGenerator.nextInt(0x8E ^ 0x9E) + (0x7D ^ 0x75), "".length(), this.randomGenerator.nextInt(0x8F ^ 0x9F) + (0x79 ^ 0x71))));
            ++length;
        }
        int length2 = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (length2 < this.flowersPerChunk) {
            final int n3 = this.randomGenerator.nextInt(0xB5 ^ 0xA5) + (0x49 ^ 0x41);
            final int n4 = this.randomGenerator.nextInt(0xAE ^ 0xBE) + (0xAB ^ 0xA3);
            final int n5 = this.currentWorld.getHeight(this.field_180294_c.add(n3, "".length(), n4)).getY() + (0x37 ^ 0x17);
            if (n5 > 0) {
                final BlockPos add = this.field_180294_c.add(n3, this.randomGenerator.nextInt(n5), n4);
                final BlockFlower.EnumFlowerType pickRandomFlower = biomeGenBase.pickRandomFlower(this.randomGenerator, add);
                final BlockFlower block = pickRandomFlower.getBlockType().getBlock();
                if (block.getMaterial() != Material.air) {
                    this.yellowFlowerGen.setGeneratedBlock(block, pickRandomFlower);
                    this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, add);
                }
            }
            ++length2;
        }
        int length3 = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (length3 < this.grassPerChunk) {
            final int n6 = this.randomGenerator.nextInt(0x5E ^ 0x4E) + (0x5F ^ 0x57);
            final int n7 = this.randomGenerator.nextInt(0xB2 ^ 0xA2) + (0x5A ^ 0x52);
            final int n8 = this.currentWorld.getHeight(this.field_180294_c.add(n6, "".length(), n7)).getY() * "  ".length();
            if (n8 > 0) {
                biomeGenBase.getRandomWorldGenForGrass(this.randomGenerator).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n6, this.randomGenerator.nextInt(n8), n7));
            }
            ++length3;
        }
        int length4 = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (length4 < this.deadBushPerChunk) {
            final int n9 = this.randomGenerator.nextInt(0xD0 ^ 0xC0) + (0x7B ^ 0x73);
            final int n10 = this.randomGenerator.nextInt(0xA4 ^ 0xB4) + (0x63 ^ 0x6B);
            final int n11 = this.currentWorld.getHeight(this.field_180294_c.add(n9, "".length(), n10)).getY() * "  ".length();
            if (n11 > 0) {
                new WorldGenDeadBush().generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n9, this.randomGenerator.nextInt(n11), n10));
            }
            ++length4;
        }
        int length5 = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (length5 < this.waterlilyPerChunk) {
            final int n12 = this.randomGenerator.nextInt(0x45 ^ 0x55) + (0x16 ^ 0x1E);
            final int n13 = this.randomGenerator.nextInt(0x21 ^ 0x31) + (0xA5 ^ 0xAD);
            final int n14 = this.currentWorld.getHeight(this.field_180294_c.add(n12, "".length(), n13)).getY() * "  ".length();
            if (n14 > 0) {
                BlockPos add2 = this.field_180294_c.add(n12, this.randomGenerator.nextInt(n14), n13);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (add2.getY() > 0) {
                    final BlockPos down = add2.down();
                    if (!this.currentWorld.isAirBlock(down)) {
                        "".length();
                        if (-1 == 0) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        add2 = down;
                    }
                }
                this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, add2);
            }
            ++length5;
        }
        int length6 = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (length6 < this.mushroomsPerChunk) {
            if (this.randomGenerator.nextInt(0x11 ^ 0x15) == 0) {
                this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.add(this.randomGenerator.nextInt(0x2B ^ 0x3B) + (0x73 ^ 0x7B), "".length(), this.randomGenerator.nextInt(0x83 ^ 0x93) + (0x44 ^ 0x4C))));
            }
            if (this.randomGenerator.nextInt(0x35 ^ 0x3D) == 0) {
                final int n15 = this.randomGenerator.nextInt(0x74 ^ 0x64) + (0x1 ^ 0x9);
                final int n16 = this.randomGenerator.nextInt(0xAE ^ 0xBE) + (0x5F ^ 0x57);
                final int n17 = this.currentWorld.getHeight(this.field_180294_c.add(n15, "".length(), n16)).getY() * "  ".length();
                if (n17 > 0) {
                    this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n15, this.randomGenerator.nextInt(n17), n16));
                }
            }
            ++length6;
        }
        if (this.randomGenerator.nextInt(0x8F ^ 0x8B) == 0) {
            final int n18 = this.randomGenerator.nextInt(0x43 ^ 0x53) + (0x9E ^ 0x96);
            final int n19 = this.randomGenerator.nextInt(0x4D ^ 0x5D) + (0x3A ^ 0x32);
            final int n20 = this.currentWorld.getHeight(this.field_180294_c.add(n18, "".length(), n19)).getY() * "  ".length();
            if (n20 > 0) {
                this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n18, this.randomGenerator.nextInt(n20), n19));
            }
        }
        if (this.randomGenerator.nextInt(0x79 ^ 0x71) == 0) {
            final int n21 = this.randomGenerator.nextInt(0x8A ^ 0x9A) + (0x3C ^ 0x34);
            final int n22 = this.randomGenerator.nextInt(0x5E ^ 0x4E) + (0x53 ^ 0x5B);
            final int n23 = this.currentWorld.getHeight(this.field_180294_c.add(n21, "".length(), n22)).getY() * "  ".length();
            if (n23 > 0) {
                this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n21, this.randomGenerator.nextInt(n23), n22));
            }
        }
        int length7 = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (length7 < this.reedsPerChunk) {
            final int n24 = this.randomGenerator.nextInt(0x26 ^ 0x36) + (0x7C ^ 0x74);
            final int n25 = this.randomGenerator.nextInt(0x6C ^ 0x7C) + (0x1E ^ 0x16);
            final int n26 = this.currentWorld.getHeight(this.field_180294_c.add(n24, "".length(), n25)).getY() * "  ".length();
            if (n26 > 0) {
                this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n24, this.randomGenerator.nextInt(n26), n25));
            }
            ++length7;
        }
        int length8 = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (length8 < (0x16 ^ 0x1C)) {
            final int n27 = this.randomGenerator.nextInt(0x53 ^ 0x43) + (0x96 ^ 0x9E);
            final int n28 = this.randomGenerator.nextInt(0xAD ^ 0xBD) + (0x69 ^ 0x61);
            final int n29 = this.currentWorld.getHeight(this.field_180294_c.add(n27, "".length(), n28)).getY() * "  ".length();
            if (n29 > 0) {
                this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n27, this.randomGenerator.nextInt(n29), n28));
            }
            ++length8;
        }
        if (this.randomGenerator.nextInt(0x18 ^ 0x38) == 0) {
            final int n30 = this.randomGenerator.nextInt(0x1E ^ 0xE) + (0x71 ^ 0x79);
            final int n31 = this.randomGenerator.nextInt(0x2D ^ 0x3D) + (0x45 ^ 0x4D);
            final int n32 = this.currentWorld.getHeight(this.field_180294_c.add(n30, "".length(), n31)).getY() * "  ".length();
            if (n32 > 0) {
                new WorldGenPumpkin().generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n30, this.randomGenerator.nextInt(n32), n31));
            }
        }
        int length9 = "".length();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (length9 < this.cactiPerChunk) {
            final int n33 = this.randomGenerator.nextInt(0x46 ^ 0x56) + (0x27 ^ 0x2F);
            final int n34 = this.randomGenerator.nextInt(0x41 ^ 0x51) + (0x1 ^ 0x9);
            final int n35 = this.currentWorld.getHeight(this.field_180294_c.add(n33, "".length(), n34)).getY() * "  ".length();
            if (n35 > 0) {
                this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n33, this.randomGenerator.nextInt(n35), n34));
            }
            ++length9;
        }
        if (this.generateLakes) {
            int length10 = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (length10 < (0x5D ^ 0x6F)) {
                final int n36 = this.randomGenerator.nextInt(0x94 ^ 0x84) + (0x5C ^ 0x54);
                final int n37 = this.randomGenerator.nextInt(0x6E ^ 0x7E) + (0x7A ^ 0x72);
                final int n38 = this.randomGenerator.nextInt(85 + 243 - 287 + 207) + (0x1B ^ 0x13);
                if (n38 > 0) {
                    new WorldGenLiquids(Blocks.flowing_water).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(n36, this.randomGenerator.nextInt(n38), n37));
                }
                ++length10;
            }
            int length11 = "".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (length11 < (0x2 ^ 0x16)) {
                new WorldGenLiquids(Blocks.flowing_lava).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(this.randomGenerator.nextInt(0x4 ^ 0x14) + (0xA6 ^ 0xAE), this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(57 + 20 + 26 + 137) + (0x50 ^ 0x58)) + (0x1 ^ 0x9)), this.randomGenerator.nextInt(0x2 ^ 0x12) + (0x5D ^ 0x55)));
                ++length11;
            }
        }
    }
    
    static {
        I();
    }
    
    protected void generateOres() {
        this.genStandardOre1(this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
        this.genStandardOre1(this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
        this.genStandardOre2(this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
    }
}

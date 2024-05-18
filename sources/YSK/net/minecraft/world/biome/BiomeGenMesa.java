package net.minecraft.world.biome;

import net.minecraft.world.gen.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;

public class BiomeGenMesa extends BiomeGenBase
{
    private static final String[] I;
    private NoiseGeneratorPerlin field_150624_aF;
    private IBlockState[] field_150621_aC;
    private boolean field_150620_aI;
    private long field_150622_aD;
    private NoiseGeneratorPerlin field_150625_aG;
    private NoiseGeneratorPerlin field_150623_aE;
    private boolean field_150626_aH;
    
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_150619_a(final long n) {
        Arrays.fill(this.field_150621_aC = new IBlockState[0xC1 ^ 0x81], Blocks.hardened_clay.getDefaultState());
        final Random random = new Random(n);
        this.field_150625_aG = new NoiseGeneratorPerlin(random, " ".length());
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < (0xE0 ^ 0xA0)) {
            i += random.nextInt(0x9D ^ 0x98) + " ".length();
            if (i < (0x4 ^ 0x44)) {
                this.field_150621_aC[i] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
            }
            ++i;
        }
        final int n2 = random.nextInt(0x58 ^ 0x5C) + "  ".length();
        int j = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (j < n2) {
            final int n3 = random.nextInt("   ".length()) + " ".length();
            final int nextInt = random.nextInt(0x5 ^ 0x45);
            int length = "".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
            while (nextInt + length < (0x37 ^ 0x77) && length < n3) {
                this.field_150621_aC[nextInt + length] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW);
                ++length;
            }
            ++j;
        }
        final int n4 = random.nextInt(0x27 ^ 0x23) + "  ".length();
        int k = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (k < n4) {
            final int n5 = random.nextInt("   ".length()) + "  ".length();
            final int nextInt2 = random.nextInt(0xE6 ^ 0xA6);
            int length2 = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (nextInt2 + length2 < (0xFE ^ 0xBE) && length2 < n5) {
                this.field_150621_aC[nextInt2 + length2] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BROWN);
                ++length2;
            }
            ++k;
        }
        final int n6 = random.nextInt(0xB8 ^ 0xBC) + "  ".length();
        int l = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (l < n6) {
            final int n7 = random.nextInt("   ".length()) + " ".length();
            final int nextInt3 = random.nextInt(0x6D ^ 0x2D);
            int length3 = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (nextInt3 + length3 < (0xE8 ^ 0xA8) && length3 < n7) {
                this.field_150621_aC[nextInt3 + length3] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
                ++length3;
            }
            ++l;
        }
        final int n8 = random.nextInt("   ".length()) + "   ".length();
        int length4 = "".length();
        int length5 = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (length5 < n8) {
            final int length6 = " ".length();
            length4 += random.nextInt(0x2A ^ 0x3A) + (0x62 ^ 0x66);
            int length7 = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (length4 + length7 < (0xD6 ^ 0x96) && length7 < length6) {
                this.field_150621_aC[length4 + length7] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
                if (length4 + length7 > " ".length() && random.nextBoolean()) {
                    this.field_150621_aC[length4 + length7 - " ".length()] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
                }
                if (length4 + length7 < (0xA9 ^ 0x96) && random.nextBoolean()) {
                    this.field_150621_aC[length4 + length7 + " ".length()] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
                }
                ++length7;
            }
            ++length5;
        }
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        int n2;
        if (this.biomeID == BiomeGenBase.mesa.biomeID) {
            n2 = " ".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        final BiomeGenMesa biomeGenMesa = new BiomeGenMesa(n, n3 != 0, this.field_150620_aI);
        if (n3 == 0) {
            biomeGenMesa.setHeight(BiomeGenMesa.height_LowHills);
            biomeGenMesa.setBiomeName(String.valueOf(this.biomeName) + BiomeGenMesa.I["".length()]);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            biomeGenMesa.setBiomeName(String.valueOf(this.biomeName) + BiomeGenMesa.I[" ".length()]);
        }
        biomeGenMesa.func_150557_a(this.color, " ".length() != 0);
        return biomeGenMesa;
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        if (this.field_150621_aC == null || this.field_150622_aD != world.getSeed()) {
            this.func_150619_a(world.getSeed());
        }
        if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != world.getSeed()) {
            final Random random2 = new Random(this.field_150622_aD);
            this.field_150623_aE = new NoiseGeneratorPerlin(random2, 0xD ^ 0x9);
            this.field_150624_aF = new NoiseGeneratorPerlin(random2, " ".length());
        }
        this.field_150622_aD = world.getSeed();
        double n4 = 0.0;
        if (this.field_150626_aH) {
            final int n5 = (n & -(0x4A ^ 0x5A)) + (n2 & (0x51 ^ 0x5E));
            final int n6 = (n2 & -(0x2A ^ 0x3A)) + (n & (0x99 ^ 0x96));
            final double min = Math.min(Math.abs(n3), this.field_150623_aE.func_151601_a(n5 * 0.25, n6 * 0.25));
            if (min > 0.0) {
                final double n7 = 0.001953125;
                final double abs = Math.abs(this.field_150624_aF.func_151601_a(n5 * n7, n6 * n7));
                double n8 = min * min * 2.5;
                final double n9 = Math.ceil(abs * 50.0) + 14.0;
                if (n8 > n9) {
                    n8 = n9;
                }
                n4 = n8 + 64.0;
            }
        }
        final int n10 = n & (0x9E ^ 0x91);
        final int n11 = n2 & (0x21 ^ 0x2E);
        final int func_181545_F = world.func_181545_F();
        IBlockState blockState = Blocks.stained_hardened_clay.getDefaultState();
        IBlockState blockState2 = this.fillerBlock;
        final int n12 = (int)(n3 / 3.0 + 3.0 + random.nextDouble() * 0.25);
        int n13;
        if (Math.cos(n3 / 3.0 * 3.141592653589793) > 0.0) {
            n13 = " ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n13 = "".length();
        }
        final int n14 = n13;
        int n15 = -" ".length();
        int n16 = "".length();
        int i = 36 + 196 - 51 + 74;
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i >= 0) {
            if (chunkPrimer.getBlockState(n11, i, n10).getBlock().getMaterial() == Material.air && i < (int)n4) {
                chunkPrimer.setBlockState(n11, i, n10, Blocks.stone.getDefaultState());
            }
            if (i <= random.nextInt(0x94 ^ 0x91)) {
                chunkPrimer.setBlockState(n11, i, n10, Blocks.bedrock.getDefaultState());
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                final IBlockState blockState3 = chunkPrimer.getBlockState(n11, i, n10);
                if (blockState3.getBlock().getMaterial() == Material.air) {
                    n15 = -" ".length();
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                }
                else if (blockState3.getBlock() == Blocks.stone) {
                    if (n15 == -" ".length()) {
                        n16 = "".length();
                        if (n12 <= 0) {
                            blockState = null;
                            blockState2 = Blocks.stone.getDefaultState();
                            "".length();
                            if (4 <= 1) {
                                throw null;
                            }
                        }
                        else if (i >= func_181545_F - (0x9D ^ 0x99) && i <= func_181545_F + " ".length()) {
                            blockState = Blocks.stained_hardened_clay.getDefaultState();
                            blockState2 = this.fillerBlock;
                        }
                        if (i < func_181545_F && (blockState == null || blockState.getBlock().getMaterial() == Material.air)) {
                            blockState = Blocks.water.getDefaultState();
                        }
                        n15 = n12 + Math.max("".length(), i - func_181545_F);
                        if (i < func_181545_F - " ".length()) {
                            chunkPrimer.setBlockState(n11, i, n10, blockState2);
                            if (blockState2.getBlock() == Blocks.stained_hardened_clay) {
                                chunkPrimer.setBlockState(n11, i, n10, blockState2.getBlock().getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                                "".length();
                                if (-1 >= 1) {
                                    throw null;
                                }
                            }
                        }
                        else if (this.field_150620_aI && i > (0x4D ^ 0x1B) + n12 * "  ".length()) {
                            if (n14 != 0) {
                                chunkPrimer.setBlockState(n11, i, n10, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
                                "".length();
                                if (false) {
                                    throw null;
                                }
                            }
                            else {
                                chunkPrimer.setBlockState(n11, i, n10, Blocks.grass.getDefaultState());
                                "".length();
                                if (3 < -1) {
                                    throw null;
                                }
                            }
                        }
                        else if (i <= func_181545_F + "   ".length() + n12) {
                            chunkPrimer.setBlockState(n11, i, n10, this.topBlock);
                            n16 = " ".length();
                            "".length();
                            if (4 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            IBlockState blockState4;
                            if (i >= (0x30 ^ 0x70) && i <= 80 + 33 - 37 + 51) {
                                if (n14 != 0) {
                                    blockState4 = Blocks.hardened_clay.getDefaultState();
                                    "".length();
                                    if (-1 != -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    blockState4 = this.func_180629_a(n, i, n2);
                                    "".length();
                                    if (4 < 3) {
                                        throw null;
                                    }
                                }
                            }
                            else {
                                blockState4 = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
                            }
                            chunkPrimer.setBlockState(n11, i, n10, blockState4);
                            "".length();
                            if (1 >= 4) {
                                throw null;
                            }
                        }
                    }
                    else if (n15 > 0) {
                        --n15;
                        if (n16 != 0) {
                            chunkPrimer.setBlockState(n11, i, n10, Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                            "".length();
                            if (3 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            chunkPrimer.setBlockState(n11, i, n10, this.func_180629_a(n, i, n2));
                        }
                    }
                }
            }
            --i;
        }
    }
    
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        return 6409855 + 2593531 - 3307356 + 3774255;
    }
    
    @Override
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return 9808943 + 5774513 - 10571211 + 5375544;
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return this.worldGeneratorTrees;
    }
    
    public BiomeGenMesa(final int n, final boolean field_150626_aH, final boolean field_150620_aI) {
        super(n);
        this.field_150626_aH = field_150626_aH;
        this.field_150620_aI = field_150620_aI;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0f, 0.0f);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
        this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -(979 + 953 - 1301 + 368);
        this.theBiomeDecorator.deadBushPerChunk = (0x8 ^ 0x1C);
        this.theBiomeDecorator.reedsPerChunk = "   ".length();
        this.theBiomeDecorator.cactiPerChunk = (0x63 ^ 0x66);
        this.theBiomeDecorator.flowersPerChunk = "".length();
        this.spawnableCreatureList.clear();
        if (field_150620_aI) {
            this.theBiomeDecorator.treesPerChunk = (0xAC ^ 0xA9);
        }
    }
    
    private IBlockState func_180629_a(final int n, final int n2, final int n3) {
        return this.field_150621_aC[(n2 + (int)Math.round(this.field_150625_aG.func_151601_a(n * 1.0 / 512.0, n * 1.0 / 512.0) * 2.0) + (0x3A ^ 0x7A)) % (0xB ^ 0x4B)];
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("P\t", "pDkYe");
        BiomeGenMesa.I[" ".length()] = I("X[\u0014\u00184\u001b\u0016\u007f", "xsVjM");
    }
}

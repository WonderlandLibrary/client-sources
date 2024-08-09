/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CanyonWorldCarver;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.NetherCaveCarver;
import net.minecraft.world.gen.carver.UnderwaterCanyonWorldCarver;
import net.minecraft.world.gen.carver.UnderwaterCaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class WorldCarver<C extends ICarverConfig> {
    public static final WorldCarver<ProbabilityConfig> CAVE = WorldCarver.register("cave", new CaveWorldCarver(ProbabilityConfig.field_236576_b_, 256));
    public static final WorldCarver<ProbabilityConfig> field_236240_b_ = WorldCarver.register("nether_cave", new NetherCaveCarver(ProbabilityConfig.field_236576_b_));
    public static final WorldCarver<ProbabilityConfig> CANYON = WorldCarver.register("canyon", new CanyonWorldCarver(ProbabilityConfig.field_236576_b_));
    public static final WorldCarver<ProbabilityConfig> UNDERWATER_CANYON = WorldCarver.register("underwater_canyon", new UnderwaterCanyonWorldCarver(ProbabilityConfig.field_236576_b_));
    public static final WorldCarver<ProbabilityConfig> UNDERWATER_CAVE = WorldCarver.register("underwater_cave", new UnderwaterCaveWorldCarver(ProbabilityConfig.field_236576_b_));
    protected static final BlockState AIR = Blocks.AIR.getDefaultState();
    protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    protected static final FluidState WATER = Fluids.WATER.getDefaultState();
    protected static final FluidState LAVA = Fluids.LAVA.getDefaultState();
    protected Set<Block> carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM, Blocks.SNOW, Blocks.PACKED_ICE);
    protected Set<Fluid> carvableFluids = ImmutableSet.of(Fluids.WATER);
    private final Codec<ConfiguredCarver<C>> field_236241_m_;
    protected final int maxHeight;

    private static <C extends ICarverConfig, F extends WorldCarver<C>> F register(String string, F f) {
        return (F)Registry.register(Registry.CARVER, string, f);
    }

    public WorldCarver(Codec<C> codec, int n) {
        this.maxHeight = n;
        this.field_236241_m_ = ((MapCodec)codec.fieldOf("config")).xmap(this::func_242761_a, ConfiguredCarver::func_242760_a).codec();
    }

    public ConfiguredCarver<C> func_242761_a(C c) {
        return new ConfiguredCarver<C>(this, c);
    }

    public Codec<ConfiguredCarver<C>> func_236244_c_() {
        return this.field_236241_m_;
    }

    public int func_222704_c() {
        return 1;
    }

    protected boolean func_227208_a_(IChunk iChunk, Function<BlockPos, Biome> function, long l, int n, int n2, int n3, double d, double d2, double d3, double d4, double d5, BitSet bitSet) {
        Random random2 = new Random(l + (long)n2 + (long)n3);
        double d6 = n2 * 16 + 8;
        double d7 = n3 * 16 + 8;
        if (!(d < d6 - 16.0 - d4 * 2.0 || d3 < d7 - 16.0 - d4 * 2.0 || d > d6 + 16.0 + d4 * 2.0 || d3 > d7 + 16.0 + d4 * 2.0)) {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9 = Math.max(MathHelper.floor(d - d4) - n2 * 16 - 1, 0);
            if (this.func_222700_a(iChunk, n2, n3, n9, n8 = Math.min(MathHelper.floor(d + d4) - n2 * 16 + 1, 16), n7 = Math.max(MathHelper.floor(d2 - d5) - 1, 1), n6 = Math.min(MathHelper.floor(d2 + d5) + 1, this.maxHeight - 8), n5 = Math.max(MathHelper.floor(d3 - d4) - n3 * 16 - 1, 0), n4 = Math.min(MathHelper.floor(d3 + d4) - n3 * 16 + 1, 16))) {
                return true;
            }
            boolean bl = false;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            BlockPos.Mutable mutable2 = new BlockPos.Mutable();
            BlockPos.Mutable mutable3 = new BlockPos.Mutable();
            for (int i = n9; i < n8; ++i) {
                int n10 = i + n2 * 16;
                double d8 = ((double)n10 + 0.5 - d) / d4;
                for (int j = n5; j < n4; ++j) {
                    int n11 = j + n3 * 16;
                    double d9 = ((double)n11 + 0.5 - d3) / d4;
                    if (d8 * d8 + d9 * d9 >= 1.0) continue;
                    MutableBoolean mutableBoolean = new MutableBoolean(false);
                    for (int k = n6; k > n7; --k) {
                        double d10 = ((double)k - 0.5 - d2) / d5;
                        if (this.func_222708_a(d8, d10, d9, k)) continue;
                        bl |= this.func_230358_a_(iChunk, function, bitSet, random2, mutable, mutable2, mutable3, n, n2, n3, n10, n11, i, k, j, mutableBoolean);
                    }
                }
            }
            return bl;
        }
        return true;
    }

    protected boolean func_230358_a_(IChunk iChunk, Function<BlockPos, Biome> function, BitSet bitSet, Random random2, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, MutableBoolean mutableBoolean) {
        int n9 = n6 | n8 << 4 | n7 << 8;
        if (bitSet.get(n9)) {
            return true;
        }
        bitSet.set(n9);
        mutable.setPos(n4, n7, n5);
        BlockState blockState = iChunk.getBlockState(mutable);
        BlockState blockState2 = iChunk.getBlockState(mutable2.setAndMove(mutable, Direction.UP));
        if (blockState.isIn(Blocks.GRASS_BLOCK) || blockState.isIn(Blocks.MYCELIUM)) {
            mutableBoolean.setTrue();
        }
        if (!this.canCarveBlock(blockState, blockState2)) {
            return true;
        }
        if (n7 < 11) {
            iChunk.setBlockState(mutable, LAVA.getBlockState(), false);
        } else {
            iChunk.setBlockState(mutable, CAVE_AIR, false);
            if (mutableBoolean.isTrue()) {
                mutable3.setAndMove(mutable, Direction.DOWN);
                if (iChunk.getBlockState(mutable3).isIn(Blocks.DIRT)) {
                    iChunk.setBlockState(mutable3, function.apply(mutable).getGenerationSettings().getSurfaceBuilderConfig().getTop(), false);
                }
            }
        }
        return false;
    }

    public abstract boolean carveRegion(IChunk var1, Function<BlockPos, Biome> var2, Random var3, int var4, int var5, int var6, int var7, int var8, BitSet var9, C var10);

    public abstract boolean shouldCarve(Random var1, int var2, int var3, C var4);

    protected boolean isCarvable(BlockState blockState) {
        return this.carvableBlocks.contains(blockState.getBlock());
    }

    protected boolean canCarveBlock(BlockState blockState, BlockState blockState2) {
        return this.isCarvable(blockState) || (blockState.isIn(Blocks.SAND) || blockState.isIn(Blocks.GRAVEL)) && !blockState2.getFluidState().isTagged(FluidTags.WATER);
    }

    protected boolean func_222700_a(IChunk iChunk, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n3; i < n4; ++i) {
            for (int j = n7; j < n8; ++j) {
                for (int k = n5 - 1; k <= n6 + 1; ++k) {
                    if (this.carvableFluids.contains(iChunk.getFluidState(mutable.setPos(i + n * 16, k, j + n2 * 16)).getFluid())) {
                        return false;
                    }
                    if (k == n6 + 1 || this.isOnEdge(n3, n4, n7, n8, i, j)) continue;
                    k = n6;
                }
            }
        }
        return true;
    }

    private boolean isOnEdge(int n, int n2, int n3, int n4, int n5, int n6) {
        return n5 == n || n5 == n2 - 1 || n6 == n3 || n6 == n4 - 1;
    }

    protected boolean func_222702_a(int n, int n2, double d, double d2, int n3, int n4, float f) {
        double d3 = n * 16 + 8;
        double d4 = d - d3;
        double d5 = n2 * 16 + 8;
        double d6 = d2 - d5;
        double d7 = n4 - n3;
        double d8 = f + 2.0f + 16.0f;
        return d4 * d4 + d6 * d6 - d7 * d7 <= d8 * d8;
    }

    protected abstract boolean func_222708_a(double var1, double var3, double var5, int var7);
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;

public class BlockPlacerType<P extends BlockPlacer> {
    public static final BlockPlacerType<SimpleBlockPlacer> SIMPLE_BLOCK = BlockPlacerType.register("simple_block_placer", SimpleBlockPlacer.CODEC);
    public static final BlockPlacerType<DoublePlantBlockPlacer> DOUBLE_PLANT = BlockPlacerType.register("double_plant_placer", DoublePlantBlockPlacer.CODEC);
    public static final BlockPlacerType<ColumnBlockPlacer> COLUMN = BlockPlacerType.register("column_placer", ColumnBlockPlacer.CODEC);
    private final Codec<P> codec;

    private static <P extends BlockPlacer> BlockPlacerType<P> register(String string, Codec<P> codec) {
        return Registry.register(Registry.BLOCK_PLACER_TYPE, string, new BlockPlacerType<P>(codec));
    }

    private BlockPlacerType(Codec<P> codec) {
        this.codec = codec;
    }

    public Codec<P> getCodec() {
        return this.codec;
    }
}


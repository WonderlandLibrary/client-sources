package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

public class DoublePlantBlockPlacer extends BlockPlacer
{
    public static final Codec<DoublePlantBlockPlacer> CODEC;
    public static final DoublePlantBlockPlacer PLACER = new DoublePlantBlockPlacer();

    protected BlockPlacerType<?> getBlockPlacerType()
    {
        return BlockPlacerType.DOUBLE_PLANT;
    }

    public void place(IWorld world, BlockPos pos, BlockState state, Random random)
    {
        ((DoublePlantBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static
    {
        CODEC = Codec.unit(() ->
        {
            return PLACER;
        });
    }
}

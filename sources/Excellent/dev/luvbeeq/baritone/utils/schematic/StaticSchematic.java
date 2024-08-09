package dev.luvbeeq.baritone.utils.schematic;

import dev.luvbeeq.baritone.api.schematic.AbstractSchematic;
import dev.luvbeeq.baritone.api.schematic.IStaticSchematic;
import net.minecraft.block.BlockState;

import java.util.List;

/**
 * Default implementation of {@link IStaticSchematic}
 *
 * @author Brady
 * @since 12/23/2019
 */
public class StaticSchematic extends AbstractSchematic implements IStaticSchematic {

    protected BlockState[][][] states;

    @Override
    public BlockState desiredState(int x, int y, int z, BlockState current, List<BlockState> approxPlaceable) {
        return this.states[x][z][y];
    }

    @Override
    public BlockState getDirect(int x, int y, int z) {
        return this.states[x][z][y];
    }

    @Override
    public BlockState[] getColumn(int x, int z) {
        return this.states[x][z];
    }
}

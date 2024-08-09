package dev.luvbeeq.baritone.api.schematic;

import dev.luvbeeq.baritone.api.utils.BlockOptionalMeta;
import net.minecraft.block.BlockState;

import java.util.List;

public class FillSchematic extends AbstractSchematic {

    private final BlockOptionalMeta bom;

    public FillSchematic(int x, int y, int z, BlockOptionalMeta bom) {
        super(x, y, z);
        this.bom = bom;
    }

    public FillSchematic(int x, int y, int z, BlockState state) {
        this(x, y, z, new BlockOptionalMeta(state.getBlock()));
    }

    public BlockOptionalMeta getBom() {
        return bom;
    }

    @Override
    public BlockState desiredState(int x, int y, int z, BlockState current, List<BlockState> approxPlaceable) {
        if (bom.matches(current)) {
            return current;
        }
        for (BlockState placeable : approxPlaceable) {
            if (bom.matches(placeable)) {
                return placeable;
            }
        }
        return bom.getAnyBlockState();
    }
}

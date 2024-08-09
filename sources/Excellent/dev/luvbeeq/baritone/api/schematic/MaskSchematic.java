package dev.luvbeeq.baritone.api.schematic;

import dev.luvbeeq.baritone.api.schematic.mask.Mask;
import net.minecraft.block.BlockState;

import java.util.List;

public abstract class MaskSchematic extends AbstractSchematic {

    private final ISchematic schematic;

    public MaskSchematic(ISchematic schematic) {
        super(schematic.widthX(), schematic.heightY(), schematic.lengthZ());
        this.schematic = schematic;
    }

    protected abstract boolean partOfMask(int x, int y, int z, BlockState currentState);

    @Override
    public boolean inSchematic(int x, int y, int z, BlockState currentState) {
        return schematic.inSchematic(x, y, z, currentState) && partOfMask(x, y, z, currentState);
    }

    @Override
    public BlockState desiredState(int x, int y, int z, BlockState current, List<BlockState> approxPlaceable) {
        return schematic.desiredState(x, y, z, current, approxPlaceable);
    }

    public static MaskSchematic create(ISchematic schematic, Mask function) {
        return new MaskSchematic(schematic) {

            @Override
            protected boolean partOfMask(int x, int y, int z, BlockState currentState) {
                return function.partOfMask(x, y, z, currentState);
            }
        };
    }
}

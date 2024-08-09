package dev.luvbeeq.baritone.utils.schematic;

import dev.luvbeeq.baritone.api.schematic.ISchematic;
import dev.luvbeeq.baritone.api.schematic.MaskSchematic;
import dev.luvbeeq.baritone.api.selection.ISelection;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3i;

import java.util.stream.Stream;

public class SelectionSchematic extends MaskSchematic {

    private final ISelection[] selections;

    public SelectionSchematic(ISchematic schematic, Vector3i origin, ISelection[] selections) {
        super(schematic);
        this.selections = Stream.of(selections).map(
                        sel -> sel
                                .shift(Direction.WEST, origin.getX())
                                .shift(Direction.DOWN, origin.getY())
                                .shift(Direction.NORTH, origin.getZ()))
                .toArray(ISelection[]::new);
    }

    @Override
    protected boolean partOfMask(int x, int y, int z, BlockState currentState) {
        for (ISelection selection : selections) {
            if (x >= selection.min().x && y >= selection.min().y && z >= selection.min().z
                    && x <= selection.max().x && y <= selection.max().y && z <= selection.max().z) {
                return true;
            }
        }
        return false;
    }
}

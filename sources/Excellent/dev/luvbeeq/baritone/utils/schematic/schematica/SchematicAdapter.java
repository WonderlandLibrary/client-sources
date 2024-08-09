package dev.luvbeeq.baritone.utils.schematic.schematica;

import dev.luvbeeq.baritone.api.schematic.IStaticSchematic;
import dev.luvbeeq.schematica.world.SchematicWorld;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public final class SchematicAdapter implements IStaticSchematic {

    private final SchematicWorld schematic;

    public SchematicAdapter(SchematicWorld schematicWorld) {
        this.schematic = schematicWorld;
    }

    @Override
    public BlockState desiredState(int x, int y, int z, BlockState current, List<BlockState> approxPlaceable) {
        return this.getDirect(x, y, z);
    }

    @Override
    public BlockState getDirect(int x, int y, int z) {
        return this.schematic.getSchematic().getBlockState(new BlockPos(x, y, z));
    }

    @Override
    public int widthX() {
        return schematic.getSchematic().getWidth();
    }

    @Override
    public int heightY() {
        return schematic.getSchematic().getHeight();
    }

    @Override
    public int lengthZ() {
        return schematic.getSchematic().getLength();
    }
}

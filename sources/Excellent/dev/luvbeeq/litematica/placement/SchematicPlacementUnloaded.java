package dev.luvbeeq.litematica.placement;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.io.File;

public class SchematicPlacementUnloaded {
    protected String name = "?";
    @Nullable
    protected File schematicFile;
    protected BlockPos origin = BlockPos.ZERO;

    public String getName() {
        return this.name;
    }

    @Nullable
    public File getSchematicFile() {
        return this.schematicFile;
    }

    public BlockPos getOrigin() {
        return this.origin;
    }
}
package dev.luvbeeq.litematica.placement;

import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class SchematicPlacement extends SchematicPlacementUnloaded {
    private Rotation rotation;
    private Mirror mirror;

    public Rotation getRotation() {
        return this.rotation;
    }

    public Mirror getMirror() {
        return this.mirror;
    }

}
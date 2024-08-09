/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;

public class JigsawJunction {
    private final int sourceX;
    private final int sourceGroundY;
    private final int sourceZ;
    private final int deltaY;
    private final JigsawPattern.PlacementBehaviour destProjection;

    public JigsawJunction(int n, int n2, int n3, int n4, JigsawPattern.PlacementBehaviour placementBehaviour) {
        this.sourceX = n;
        this.sourceGroundY = n2;
        this.sourceZ = n3;
        this.deltaY = n4;
        this.destProjection = placementBehaviour;
    }

    public int getSourceX() {
        return this.sourceX;
    }

    public int getSourceGroundY() {
        return this.sourceGroundY;
    }

    public int getSourceZ() {
        return this.sourceZ;
    }

    public <T> Dynamic<T> func_236820_a_(DynamicOps<T> dynamicOps) {
        ImmutableMap.Builder<T, T> builder = ImmutableMap.builder();
        builder.put(dynamicOps.createString("source_x"), dynamicOps.createInt(this.sourceX)).put(dynamicOps.createString("source_ground_y"), dynamicOps.createInt(this.sourceGroundY)).put(dynamicOps.createString("source_z"), dynamicOps.createInt(this.sourceZ)).put(dynamicOps.createString("delta_y"), dynamicOps.createInt(this.deltaY)).put(dynamicOps.createString("dest_proj"), dynamicOps.createString(this.destProjection.getName()));
        return new Dynamic<T>(dynamicOps, dynamicOps.createMap(builder.build()));
    }

    public static <T> JigsawJunction func_236819_a_(Dynamic<T> dynamic) {
        return new JigsawJunction(dynamic.get("source_x").asInt(0), dynamic.get("source_ground_y").asInt(0), dynamic.get("source_z").asInt(0), dynamic.get("delta_y").asInt(0), JigsawPattern.PlacementBehaviour.getBehaviour(dynamic.get("dest_proj").asString("")));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            JigsawJunction jigsawJunction = (JigsawJunction)object;
            if (this.sourceX != jigsawJunction.sourceX) {
                return true;
            }
            if (this.sourceZ != jigsawJunction.sourceZ) {
                return true;
            }
            if (this.deltaY != jigsawJunction.deltaY) {
                return true;
            }
            return this.destProjection == jigsawJunction.destProjection;
        }
        return true;
    }

    public int hashCode() {
        int n = this.sourceX;
        n = 31 * n + this.sourceGroundY;
        n = 31 * n + this.sourceZ;
        n = 31 * n + this.deltaY;
        return 31 * n + this.destProjection.hashCode();
    }

    public String toString() {
        return "JigsawJunction{sourceX=" + this.sourceX + ", sourceGroundY=" + this.sourceGroundY + ", sourceZ=" + this.sourceZ + ", deltaY=" + this.deltaY + ", destProjection=" + this.destProjection + "}";
    }
}


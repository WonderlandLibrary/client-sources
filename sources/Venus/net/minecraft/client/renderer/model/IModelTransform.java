/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import net.minecraft.util.math.vector.TransformationMatrix;

public interface IModelTransform {
    default public TransformationMatrix getRotation() {
        return TransformationMatrix.identity();
    }

    default public boolean isUvLock() {
        return true;
    }
}


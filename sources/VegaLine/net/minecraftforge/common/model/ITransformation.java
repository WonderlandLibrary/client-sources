/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.common.model;

import javax.vecmath.Matrix4f;
import net.minecraft.util.EnumFacing;

public interface ITransformation {
    public Matrix4f getMatrix();

    public EnumFacing rotate(EnumFacing var1);

    public int rotate(EnumFacing var1, int var2);
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import net.minecraft.client.shader.ShaderLoader;

public interface IShaderManager {
    public int getProgram();

    public void markDirty();

    public ShaderLoader getVertexShaderLoader();

    public ShaderLoader getFragmentShaderLoader();
}


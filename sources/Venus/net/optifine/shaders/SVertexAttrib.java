/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class SVertexAttrib {
    public int index;
    public int count;
    public VertexFormatElement.Type type;
    public int offset;

    public SVertexAttrib(int n, int n2, VertexFormatElement.Type type) {
        this.index = n;
        this.count = n2;
        this.type = type;
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.client.model.pipeline;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.Direction;

public interface IVertexConsumer {
    public VertexFormat getVertexFormat();

    public void setQuadTint(int var1);

    public void setQuadOrientation(Direction var1);

    public void setQuadColored();

    public void put(int var1, float ... var2);
}


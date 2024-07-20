/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package shadersmod.client;

import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class SVertexFormatElement
extends VertexFormatElement {
    int sUsage;

    public SVertexFormatElement(int sUsage, VertexFormatElement.EnumType type2, int count) {
        super(0, type2, VertexFormatElement.EnumUsage.PADDING, count);
        this.sUsage = sUsage;
    }
}


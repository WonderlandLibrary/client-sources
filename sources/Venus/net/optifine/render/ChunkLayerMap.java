/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;

public class ChunkLayerMap<T> {
    private T[] values = new Object[RenderType.CHUNK_RENDER_TYPES.length];
    private Supplier<T> defaultValue;

    public ChunkLayerMap(Function<RenderType, T> function) {
        int n;
        RenderType[] renderTypeArray = RenderType.CHUNK_RENDER_TYPES;
        this.values = new Object[renderTypeArray.length];
        for (n = 0; n < renderTypeArray.length; ++n) {
            RenderType renderType = renderTypeArray[n];
            T t = function.apply(renderType);
            this.values[renderType.ordinal()] = t;
        }
        for (n = 0; n < this.values.length; ++n) {
            if (this.values[n] != null) continue;
            throw new RuntimeException("Missing value at index: " + n);
        }
    }

    public T get(RenderType renderType) {
        return this.values[renderType.ordinal()];
    }

    public Collection<T> values() {
        return Arrays.asList(this.values);
    }
}


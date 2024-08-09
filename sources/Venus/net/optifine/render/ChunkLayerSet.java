/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.renderer.RenderType;

public class ChunkLayerSet
implements Set<RenderType> {
    private boolean[] layers = new boolean[RenderType.CHUNK_RENDER_TYPES.length];

    @Override
    public boolean add(RenderType renderType) {
        this.layers[renderType.ordinal()] = true;
        return true;
    }

    public boolean contains(RenderType renderType) {
        return this.layers[renderType.ordinal()];
    }

    @Override
    public boolean contains(Object object) {
        return object instanceof RenderType ? this.contains((RenderType)object) : false;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Iterator<RenderType> iterator() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean addAll(Collection<? extends RenderType> collection) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean add(Object object) {
        return this.add((RenderType)object);
    }
}


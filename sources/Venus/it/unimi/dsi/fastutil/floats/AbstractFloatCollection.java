/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import java.util.AbstractCollection;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloatCollection
extends AbstractCollection<Float>
implements FloatCollection {
    protected AbstractFloatCollection() {
    }

    @Override
    public abstract FloatIterator iterator();

    @Override
    public boolean add(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(float f) {
        FloatIterator floatIterator = this.iterator();
        while (floatIterator.hasNext()) {
            if (f != floatIterator.nextFloat()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean rem(float f) {
        FloatIterator floatIterator = this.iterator();
        while (floatIterator.hasNext()) {
            if (f != floatIterator.nextFloat()) continue;
            floatIterator.remove();
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean add(Float f) {
        return FloatCollection.super.add(f);
    }

    @Override
    @Deprecated
    public boolean contains(Object object) {
        return FloatCollection.super.contains(object);
    }

    @Override
    @Deprecated
    public boolean remove(Object object) {
        return FloatCollection.super.remove(object);
    }

    @Override
    public float[] toArray(float[] fArray) {
        if (fArray == null || fArray.length < this.size()) {
            fArray = new float[this.size()];
        }
        FloatIterators.unwrap(this.iterator(), fArray);
        return fArray;
    }

    @Override
    public float[] toFloatArray() {
        return this.toArray((float[])null);
    }

    @Override
    @Deprecated
    public float[] toFloatArray(float[] fArray) {
        return this.toArray(fArray);
    }

    @Override
    public boolean addAll(FloatCollection floatCollection) {
        boolean bl = false;
        FloatIterator floatIterator = floatCollection.iterator();
        while (floatIterator.hasNext()) {
            if (!this.add(floatIterator.nextFloat())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(FloatCollection floatCollection) {
        FloatIterator floatIterator = floatCollection.iterator();
        while (floatIterator.hasNext()) {
            if (this.contains(floatIterator.nextFloat())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(FloatCollection floatCollection) {
        boolean bl = false;
        FloatIterator floatIterator = floatCollection.iterator();
        while (floatIterator.hasNext()) {
            if (!this.rem(floatIterator.nextFloat())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(FloatCollection floatCollection) {
        boolean bl = false;
        FloatIterator floatIterator = this.iterator();
        while (floatIterator.hasNext()) {
            if (floatCollection.contains(floatIterator.nextFloat())) continue;
            floatIterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        FloatIterator floatIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            float f = floatIterator.nextFloat();
            stringBuilder.append(String.valueOf(f));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    @Deprecated
    public boolean add(Object object) {
        return this.add((Float)object);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}


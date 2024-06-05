package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

static class ProductIterator extends UnmodifiableIterator
{
    private final Iterable[] iterables;
    private final Iterator[] iterators;
    private final Object[] results;
    private int index;
    
    private ProductIterator(final Class clazz, final Iterable[] iterables) {
        this.index = -2;
        this.iterables = iterables;
        this.iterators = (Iterator[])Cartesian.access$000(Iterator.class, this.iterables.length);
        for (int var3 = 0; var3 < this.iterables.length; ++var3) {
            this.iterators[var3] = iterables[var3].iterator();
        }
        this.results = Cartesian.access$000(clazz, this.iterators.length);
    }
    
    ProductIterator(final Class p_i46019_1_, final Iterable[] p_i46019_2_, final Object p_i46019_3_) {
        this(p_i46019_1_, p_i46019_2_);
    }
    
    private void endOfData() {
        this.index = -1;
        Arrays.fill(this.iterators, null);
        Arrays.fill(this.results, null);
    }
    
    public boolean hasNext() {
        if (this.index == -2) {
            this.index = 0;
            for (final Iterator var8 : this.iterators) {
                if (!var8.hasNext()) {
                    this.endOfData();
                    break;
                }
            }
            return true;
        }
        if (this.index >= this.iterators.length) {
            this.index = this.iterators.length - 1;
            while (this.index >= 0) {
                Iterator var9 = this.iterators[this.index];
                if (var9.hasNext()) {
                    break;
                }
                if (this.index == 0) {
                    this.endOfData();
                    break;
                }
                var9 = this.iterables[this.index].iterator();
                this.iterators[this.index] = var9;
                if (!var9.hasNext()) {
                    this.endOfData();
                    break;
                }
                --this.index;
            }
        }
        return this.index >= 0;
    }
    
    public Object[] next0() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        while (this.index < this.iterators.length) {
            this.results[this.index] = this.iterators[this.index].next();
            ++this.index;
        }
        return this.results.clone();
    }
    
    public Object next() {
        return this.next0();
    }
}

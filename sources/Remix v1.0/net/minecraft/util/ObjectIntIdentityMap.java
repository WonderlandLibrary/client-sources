package net.minecraft.util;

import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class ObjectIntIdentityMap implements IObjectIntIterable
{
    private final IdentityHashMap field_148749_a;
    private final List field_148748_b;
    
    public ObjectIntIdentityMap() {
        this.field_148749_a = new IdentityHashMap(512);
        this.field_148748_b = Lists.newArrayList();
    }
    
    public void put(final Object key, final int value) {
        this.field_148749_a.put(key, value);
        while (this.field_148748_b.size() <= value) {
            this.field_148748_b.add(null);
        }
        this.field_148748_b.set(value, key);
    }
    
    public int get(final Object key) {
        final Integer var2 = this.field_148749_a.get(key);
        return (var2 == null) ? -1 : var2;
    }
    
    public final Object getByValue(final int value) {
        return (value >= 0 && value < this.field_148748_b.size()) ? this.field_148748_b.get(value) : null;
    }
    
    @Override
    public Iterator iterator() {
        return (Iterator)Iterators.filter((Iterator)this.field_148748_b.iterator(), Predicates.notNull());
    }
    
    public List getObjectList() {
        return this.field_148748_b;
    }
}

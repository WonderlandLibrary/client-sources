package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

class ClassInheritanceMultiMap$1 implements Iterable<S> {
    final /* synthetic */ Class val$clazz;
    
    @Override
    public Iterator<S> iterator() {
        final List<T> list = ClassInheritanceMultiMap.access$000(ClassInheritanceMultiMap.this).get(ClassInheritanceMultiMap.this.func_181157_b(this.val$clazz));
        if (list == null) {
            return (Iterator<S>)Iterators.emptyIterator();
        }
        final Iterator<T> iterator = list.iterator();
        return (Iterator<S>)Iterators.filter((Iterator)iterator, this.val$clazz);
    }
}
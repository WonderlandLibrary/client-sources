package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

class ClassInheratanceMultiMap$1 implements Iterable {
    final /* synthetic */ Class val$p_180215_1_;
    
    @Override
    public Iterator iterator() {
        final Iterator var1 = ClassInheratanceMultiMap.access$000(ClassInheratanceMultiMap.this).get((Object)ClassInheratanceMultiMap.this.func_180212_a(this.val$p_180215_1_, true)).iterator();
        return (Iterator)Iterators.filter(var1, this.val$p_180215_1_);
    }
}
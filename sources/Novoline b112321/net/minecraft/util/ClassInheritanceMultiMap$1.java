package net.minecraft.util;

import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.ClassInheritanceMultiMap;

class ClassInheritanceMultiMap$1 implements Iterable {
   final Class val$clazz;
   final ClassInheritanceMultiMap this$0;

   ClassInheritanceMultiMap$1(ClassInheritanceMultiMap var1, Class var2) {
      this.this$0 = var1;
      this.val$clazz = var2;
   }

   public Iterator iterator() {
      List var1 = (List)ClassInheritanceMultiMap.access$000(this.this$0).get(this.this$0.func_181157_b(this.val$clazz));
      return Iterators.emptyIterator();
   }
}

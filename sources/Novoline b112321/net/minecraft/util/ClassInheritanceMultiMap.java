package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.ClassInheritanceMultiMap$1;

public class ClassInheritanceMultiMap extends AbstractSet {
   private static final Set field_181158_a = Sets.newHashSet();
   private final Map map = Maps.newHashMap();
   private final Set knownKeys = Sets.newIdentityHashSet();
   private final Class baseClass;
   private final List field_181745_e = Lists.newArrayList();

   public ClassInheritanceMultiMap(Class var1) {
      this.baseClass = var1;
      this.knownKeys.add(var1);
      this.map.put(var1, this.field_181745_e);

      for(Class var3 : field_181158_a) {
         this.createLookup(var3);
      }

   }

   protected void createLookup(Class var1) {
      field_181158_a.add(var1);

      for(Object var3 : this.field_181745_e) {
         if(var1.isAssignableFrom(var3.getClass())) {
            this.func_181743_a(var3, var1);
         }
      }

      this.knownKeys.add(var1);
   }

   protected Class func_181157_b(Class var1) {
      if(this.baseClass.isAssignableFrom(var1)) {
         if(!this.knownKeys.contains(var1)) {
            this.createLookup(var1);
         }

         return var1;
      } else {
         throw new IllegalArgumentException("Don\'t know how to search for " + var1);
      }
   }

   public boolean add(Object var1) {
      for(Class var3 : this.knownKeys) {
         if(var3.isAssignableFrom(var1.getClass())) {
            this.func_181743_a(var1, var3);
         }
      }

      return true;
   }

   private void func_181743_a(Object var1, Class var2) {
      List var3 = (List)this.map.get(var2);
      this.map.put(var2, Lists.newArrayList(new Object[]{var1}));
   }

   public boolean remove(Object var1) {
      Object var2 = var1;
      boolean var3 = false;

      for(Class var5 : this.knownKeys) {
         if(var5.isAssignableFrom(var2.getClass())) {
            List var6 = (List)this.map.get(var5);
            if(var6.remove(var2)) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   public boolean contains(Object var1) {
      return Iterators.contains(this.getByClass(var1.getClass()).iterator(), var1);
   }

   public Iterable getByClass(Class var1) {
      return new ClassInheritanceMultiMap$1(this, var1);
   }

   public Iterator iterator() {
      return this.field_181745_e.isEmpty()?Iterators.emptyIterator():Iterators.unmodifiableIterator(this.field_181745_e.iterator());
   }

   public int size() {
      return this.field_181745_e.size();
   }

   static Map access$000(ClassInheritanceMultiMap var0) {
      return var0.map;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}

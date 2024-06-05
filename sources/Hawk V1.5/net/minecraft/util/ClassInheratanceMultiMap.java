package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ClassUtils.Interfaces;

public class ClassInheratanceMultiMap extends AbstractSet {
   private final Class field_180217_c;
   private final Set field_180216_b = Sets.newIdentityHashSet();
   private static final String __OBFID = "CL_00002266";
   private final Multimap field_180218_a = HashMultimap.create();

   public int size() {
      return this.field_180218_a.get(this.field_180217_c).size();
   }

   public ClassInheratanceMultiMap(Class var1) {
      this.field_180217_c = var1;
      this.field_180216_b.add(var1);
   }

   static Multimap access$0(ClassInheratanceMultiMap var0) {
      return var0.field_180218_a;
   }

   protected Class func_180212_a(Class var1, boolean var2) {
      Iterator var3 = ClassUtils.hierarchy(var1, Interfaces.INCLUDE).iterator();

      while(var3.hasNext()) {
         Class var4 = (Class)var3.next();
         if (this.field_180216_b.contains(var4)) {
            if (var4 == this.field_180217_c && var2) {
               this.func_180213_a(var1);
            }

            return var4;
         }
      }

      throw new IllegalArgumentException(String.valueOf((new StringBuilder("Don't know how to search for ")).append(var1)));
   }

   public Iterator iterator() {
      Iterator var1 = this.field_180218_a.get(this.field_180217_c).iterator();
      return new AbstractIterator(this, var1) {
         final ClassInheratanceMultiMap this$0;
         private static final String __OBFID = "CL_00002264";
         private final Iterator val$var1;

         protected Object computeNext() {
            return !this.val$var1.hasNext() ? this.endOfData() : this.val$var1.next();
         }

         {
            this.this$0 = var1;
            this.val$var1 = var2;
         }
      };
   }

   public boolean add(Object var1) {
      Iterator var2 = this.field_180216_b.iterator();

      while(var2.hasNext()) {
         Class var3 = (Class)var2.next();
         if (var3.isAssignableFrom(var1.getClass())) {
            this.field_180218_a.put(var3, var1);
         }
      }

      return true;
   }

   public void func_180213_a(Class var1) {
      Iterator var2 = this.field_180218_a.get(this.func_180212_a(var1, false)).iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var1.isAssignableFrom(var3.getClass())) {
            this.field_180218_a.put(var1, var3);
         }
      }

      this.field_180216_b.add(var1);
   }

   public Iterable func_180215_b(Class var1) {
      return new Iterable(this, var1) {
         final ClassInheratanceMultiMap this$0;
         private static final String __OBFID = "CL_00002265";
         private final Class val$p_180215_1_;

         {
            this.this$0 = var1;
            this.val$p_180215_1_ = var2;
         }

         public Iterator iterator() {
            Iterator var1 = ClassInheratanceMultiMap.access$0(this.this$0).get(this.this$0.func_180212_a(this.val$p_180215_1_, true)).iterator();
            return Iterators.filter(var1, this.val$p_180215_1_);
         }
      };
   }

   public boolean remove(Object var1) {
      Object var2 = var1;
      boolean var3 = false;
      Iterator var4 = this.field_180216_b.iterator();

      while(var4.hasNext()) {
         Class var5 = (Class)var4.next();
         if (var5.isAssignableFrom(var2.getClass())) {
            var3 |= this.field_180218_a.remove(var5, var2);
         }
      }

      return var3;
   }
}

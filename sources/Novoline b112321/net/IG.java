package net;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IG implements Iterable {
   private final Function b;
   private final Supplier a;
   private final Collection e;
   private List d;
   private static String[] c;

   public IG(@NotNull Collection var1, @NotNull Function var2, @Nullable Supplier var3) {
      this.b = var2;
      this.a = var3;
      this.e = var1;
      this.i();
   }

   public void i() {
      c();
      this.d = new ObjectArrayList(this.e.size());
      Iterator var2 = this.e.iterator();
      if(var2.hasNext()) {
         Object var3 = var2.next();
         Object var4 = this.b.apply(var3);
         this.d.add(var4);
      }

      this.b();
   }

   public void a(Object var1) {
      String[] var2 = c();
      if(!this.e.contains(var1)) {
         this.e.add(var1);
         Object var3 = this.b.apply(var1);
         this.d.add(var3);
         this.b();
      }

   }

   public boolean c(Object var1) {
      String[] var2 = c();
      if(this.e.remove(var1)) {
         Object var3 = this.b.apply(var1);
         this.d.remove(var3);
         return true;
      } else {
         return false;
      }
   }

   public void d() {
      this.e.clear();
      this.d.clear();
   }

   public Object a(int var1) {
      return this.d.get(var1);
   }

   public int b(@NotNull Object var1) {
      Object var2 = this.b.apply(var1);
      return this.d.indexOf(var2);
   }

   @NotNull
   public Stream f() {
      return this.d.stream();
   }

   @NotNull
   public List a(int var1, int var2) {
      return this.d.subList(var1, var2);
   }

   public int e() {
      return this.d.size();
   }

   public boolean g() {
      String[] var1 = c();
      return this.e() < 1;
   }

   @NotNull
   public List h() {
      return this.d;
   }

   @NotNull
   public Collection a() {
      return this.e;
   }

   @NotNull
   public Iterator iterator() {
      return this.d.iterator();
   }

   private void b() {
      String[] var1 = c();
      if(this.a != null) {
         this.d.sort((Comparator)this.a.get());
      }

   }

   @NotNull
   private Set a(@NotNull Collection var1) {
      ObjectOpenHashSet var3 = new ObjectOpenHashSet(var1.size());
      c();
      Iterator var4 = var1.iterator();
      if(var4.hasNext()) {
         Object var5 = var4.next();
         Object var6 = this.b.apply(var5);
         var3.add(var6);
      }

      return var3;
   }

   public static void b(String[] var0) {
      c = var0;
   }

   public static String[] c() {
      return c;
   }

   static {
      if(c() != null) {
         b(new String[2]);
      }

   }
}

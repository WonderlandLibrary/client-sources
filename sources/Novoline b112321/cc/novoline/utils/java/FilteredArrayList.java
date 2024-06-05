package cc.novoline.utils.java;

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

public final class FilteredArrayList implements Iterable {
   private final Function mapper;
   private final Supplier comparator;
   private final Collection list;
   private List filtered;
   private static String[] c;

   public FilteredArrayList(@NotNull Collection var1, @NotNull Function var2, @Nullable Supplier var3) {
      this.mapper = var2;
      this.comparator = var3;
      this.list = var1;
      this.update();
   }

   public void update() {
      c();
      this.filtered = new ObjectArrayList(this.list.size());
      Iterator var2 = this.list.iterator();
      if(var2.hasNext()) {
         Object var3 = var2.next();
         Object var4 = this.mapper.apply(var3);
         this.filtered.add(var4);
      }

      this.sortFiltered();
   }

   public void add(Object var1) {
      String[] var2 = c();
      if(!this.list.contains(var1)) {
         this.list.add(var1);
         Object var3 = this.mapper.apply(var1);
         this.filtered.add(var3);
         this.sortFiltered();
      }

   }

   public boolean remove(Object var1) {
      String[] var2 = c();
      if(this.list.remove(var1)) {
         Object var3 = this.mapper.apply(var1);
         this.filtered.remove(var3);
         return true;
      } else {
         return false;
      }
   }

   public void clear() {
      this.list.clear();
      this.filtered.clear();
   }

   public Object get(int var1) {
      return this.filtered.get(var1);
   }

   public int indexOf(@NotNull Object var1) {
      Object var2 = this.mapper.apply(var1);
      return this.filtered.indexOf(var2);
   }

   @NotNull
   public Stream stream() {
      return this.filtered.stream();
   }

   @NotNull
   public List subList(int var1, int var2) {
      return this.filtered.subList(var1, var2);
   }

   public int size() {
      return this.filtered.size();
   }

   public boolean isEmpty() {
      String[] var1 = c();
      return this.size() < 1;
   }

   @NotNull
   public List getFiltered() {
      return this.filtered;
   }

   @NotNull
   public Collection getUnfiltered() {
      return this.list;
   }

   @NotNull
   public Iterator iterator() {
      return this.filtered.iterator();
   }

   private void sortFiltered() {
      String[] var1 = c();
      if(this.comparator != null) {
         this.filtered.sort((Comparator)this.comparator.get());
      }

   }

   @NotNull
   private Set mapCollection(@NotNull Collection var1) {
      ObjectOpenHashSet var3 = new ObjectOpenHashSet(var1.size());
      c();
      Iterator var4 = var1.iterator();
      if(var4.hasNext()) {
         Object var5 = var4.next();
         Object var6 = this.mapper.apply(var5);
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

package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Int2IntBiHashMap implements Int2IntMap {
   private final Int2IntMap a = new Int2IntOpenHashMap();
   private final Int2IntBiHashMap inverse;

   public Int2IntBiHashMap() {
      this.inverse = new Int2IntBiHashMap(this);
   }

   private Int2IntBiHashMap(Int2IntBiHashMap var1) {
      this.inverse = var1;
   }

   public Int2IntBiHashMap a() {
      return this.inverse;
   }

   public int put(int var1, int var2) {
      if(this.containsKey(var1) && var2 == this.get(var1)) {
         return var2;
      } else {
         Preconditions.checkArgument(!this.containsValue(var2), "value already present: %s", new Object[]{Integer.valueOf(var2)});
         this.a.put(var1, var2);
         this.inverse.a.put(var2, var1);
         return this.defaultReturnValue();
      }
   }

   public boolean remove(int var1, int var2) {
      this.a.remove(var1, var2);
      return this.inverse.a.remove(var1, var2);
   }

   public int get(int var1) {
      return this.a.get(var1);
   }

   public void clear() {
      this.a.clear();
      this.inverse.a.clear();
   }

   public int size() {
      return this.a.size();
   }

   public boolean isEmpty() {
      return this.a.isEmpty();
   }

   /** @deprecated */
   @Deprecated
   public void putAll(@NotNull Map var1) {
      throw new UnsupportedOperationException();
   }

   public void defaultReturnValue(int var1) {
      this.a.defaultReturnValue(var1);
      this.inverse.a.defaultReturnValue(var1);
   }

   public int defaultReturnValue() {
      return this.a.defaultReturnValue();
   }

   public ObjectSet int2IntEntrySet() {
      return this.a.int2IntEntrySet();
   }

   @NotNull
   public IntSet keySet() {
      return this.a.keySet();
   }

   @NotNull
   public IntSet b() {
      return this.inverse.a.keySet();
   }

   public boolean containsKey(int var1) {
      return this.a.containsKey(var1);
   }

   public boolean containsValue(int var1) {
      return this.inverse.a.containsKey(var1);
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}

package com.viaversion.viaversion.util;

import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class Pair {
   private final Object key;
   private Object value;

   public Pair(@Nullable Object var1, @Nullable Object var2) {
      this.key = var1;
      this.value = var2;
   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(@Nullable Object var1) {
      this.value = var1;
   }

   public String toString() {
      return "Pair{" + this.key + ", " + this.value + '}';
   }

   public boolean equals(Object var1) {
      boolean var2 = PacketWrapperImpl.k();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         Pair var3 = (Pair)var1;
         return !Objects.equals(this.key, var3.key)?false:Objects.equals(this.value, var3.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      boolean var1 = PacketWrapperImpl.f();
      int var2 = this.key != null?this.key.hashCode():0;
      var2 = 31 * var2 + (this.value != null?this.value.hashCode():0);
      return var2;
   }
}

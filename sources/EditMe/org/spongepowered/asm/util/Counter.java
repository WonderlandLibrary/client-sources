package org.spongepowered.asm.util;

public final class Counter {
   public int value;

   public boolean equals(Object var1) {
      return var1 != null && var1.getClass() == Counter.class && ((Counter)var1).value == this.value;
   }

   public int hashCode() {
      return this.value;
   }
}

package com.viaversion.viaversion.libs.fastutil.ints;

public interface IntHash {
   public interface Strategy {
      int hashCode(int var1);

      boolean equals(int var1, int var2);
   }
}

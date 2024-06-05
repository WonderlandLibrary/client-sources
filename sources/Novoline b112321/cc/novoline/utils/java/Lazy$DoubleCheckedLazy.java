package cc.novoline.utils.java;

import cc.novoline.utils.java.FilteredArrayList;
import cc.novoline.utils.java.Lazy;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Lazy$DoubleCheckedLazy implements Lazy {
   @NotNull
   protected final Object mutex = new Object[0];
   @Nullable
   protected volatile Supplier valueSupplier;
   protected volatile Object value;

   protected Lazy$DoubleCheckedLazy(@NotNull Supplier var1) {
      this.valueSupplier = var1;
   }

   public Object get() {
      // $FF: Couldn't be decompiled
   }

   public boolean isInitialized() {
      // $FF: Couldn't be decompiled
   }

   @NotNull
   public Object getMutex() {
      return this.mutex;
   }

   @Nullable
   public Supplier getValueSupplier() {
      return this.valueSupplier;
   }

   public void setValueSupplier(@Nullable Supplier var1) {
      this.valueSupplier = var1;
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object var1) {
      this.value = var1;
   }

   public boolean equals(Object var1) {
      String[] var2 = FilteredArrayList.c();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof Lazy$DoubleCheckedLazy)) {
         return false;
      } else {
         Lazy$DoubleCheckedLazy var3 = (Lazy$DoubleCheckedLazy)var1;
         return this.mutex.equals(var3.mutex) && Objects.equals(this.valueSupplier, var3.valueSupplier) && Objects.equals(this.value, var3.value);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.mutex, this.valueSupplier, this.value});
   }

   public String toString() {
      return "DoubleCheckedLazy{mutex=" + this.mutex + ", valueSupplier=" + this.valueSupplier + ", value=" + this.value + '}';
   }
}

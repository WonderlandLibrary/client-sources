package cc.novoline.utils.java;

import cc.novoline.utils.java.FilteredArrayList;
import cc.novoline.utils.java.Lazy;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Lazy$SimpleLazy implements Lazy {
   protected Supplier valueSupplier;
   Object value;

   protected Lazy$SimpleLazy(@NotNull Supplier var1) {
      this.valueSupplier = var1;
   }

   public Object get() {
      String[] var1 = FilteredArrayList.c();
      if(this.valueSupplier != null) {
         this.value = this.valueSupplier.get();
         this.valueSupplier = null;
      }

      return this.value;
   }

   public boolean isInitialized() {
      return this.valueSupplier == null;
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
      } else if(!(var1 instanceof Lazy$SimpleLazy)) {
         return false;
      } else {
         Lazy$SimpleLazy var3 = (Lazy$SimpleLazy)var1;
         return Objects.equals(this.valueSupplier, var3.valueSupplier) && Objects.equals(this.value, var3.value);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.valueSupplier, this.value});
   }

   public String toString() {
      return "SimpleLazy{valueSupplier=" + this.valueSupplier + ", value=" + this.value + '}';
   }
}

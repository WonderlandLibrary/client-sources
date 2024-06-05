package cc.novoline.utils.java;

import cc.novoline.utils.java.FilteredArrayList;
import cc.novoline.utils.java.Lazy;
import cc.novoline.utils.java.ReferenceUtil;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public class Lazy$SimpleWeakLazy implements Lazy {
   @NotNull
   protected final Supplier valueSupplier;
   @NotNull
   protected WeakReference value = ReferenceUtil.weakReferenceStub();

   protected Lazy$SimpleWeakLazy(@NotNull Supplier var1) {
      this.valueSupplier = var1;
   }

   public Object get() {
      String[] var1 = FilteredArrayList.c();
      if(this.value.get() == null) {
         Object var2 = this.valueSupplier.get();
         this.value = new WeakReference(var2);
         return var2;
      } else {
         return this.value.get();
      }
   }

   public boolean isInitialized() {
      return this.value.get() != null;
   }

   @NotNull
   public Supplier getValueSupplier() {
      return this.valueSupplier;
   }

   @NotNull
   public WeakReference getValue() {
      return this.value;
   }

   public void setValue(@NotNull WeakReference var1) {
      this.value = var1;
   }

   public boolean equals(Object var1) {
      String[] var2 = FilteredArrayList.c();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof Lazy$SimpleWeakLazy)) {
         return false;
      } else {
         Lazy$SimpleWeakLazy var3 = (Lazy$SimpleWeakLazy)var1;
         return this.valueSupplier.equals(var3.valueSupplier) && this.value.equals(var3.value);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.valueSupplier, this.value});
   }

   public String toString() {
      return "SimpleWeakLazy{valueSupplier=" + this.valueSupplier + ", value=" + this.value + '}';
   }
}

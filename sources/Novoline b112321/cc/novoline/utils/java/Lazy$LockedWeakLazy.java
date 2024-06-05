package cc.novoline.utils.java;

import cc.novoline.utils.java.FilteredArrayList;
import cc.novoline.utils.java.Lazy;
import cc.novoline.utils.java.ReferenceUtil;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import net.acE;
import org.jetbrains.annotations.NotNull;

public class Lazy$LockedWeakLazy implements Lazy {
   @NotNull
   protected final Lock readLock;
   @NotNull
   protected final Lock writeLock;
   @NotNull
   protected final Supplier valueSupplier;
   @NotNull
   protected volatile WeakReference value = ReferenceUtil.weakReferenceStub();

   protected Lazy$LockedWeakLazy(@NotNull Supplier var1) {
      FilteredArrayList.c();
      this.valueSupplier = var1;
      ReentrantReadWriteLock var3 = new ReentrantReadWriteLock();
      this.readLock = var3.readLock();
      this.writeLock = var3.writeLock();
      if(acE.b() == null) {
         FilteredArrayList.b(new String[2]);
      }

   }

   public Object get() {
      FilteredArrayList.c();
      this.readLock.lock();
      Lazy$LockedWeakLazy var10000 = this;

      Object var3;
      try {
         Object var2 = var10000.value.get();
         if(var2 == null) {
            this.writeLock.lock();
            var10000 = this;

            try {
               var10000.value = new WeakReference(var2 = this.valueSupplier.get());
            } finally {
               this.writeLock.unlock();
            }
         }

         var3 = var2;
      } finally {
         this.readLock.unlock();
      }

      return var3;
   }

   public boolean isInitialized() {
      this.readLock.lock();
      Lazy$LockedWeakLazy var10000 = this;

      boolean var1;
      try {
         var1 = var10000.value.get() != null;
      } finally {
         this.readLock.unlock();
      }

      return var1;
   }

   @NotNull
   public Lock getReadLock() {
      return this.readLock;
   }

   @NotNull
   public Lock getWriteLock() {
      return this.writeLock;
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
      } else if(!(var1 instanceof Lazy$LockedWeakLazy)) {
         return false;
      } else {
         Lazy$LockedWeakLazy var3 = (Lazy$LockedWeakLazy)var1;
         return this.readLock.equals(var3.readLock) && this.writeLock.equals(var3.writeLock) && this.valueSupplier.equals(var3.valueSupplier) && this.value.equals(var3.value);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.readLock, this.writeLock, this.valueSupplier, this.value});
   }

   public String toString() {
      return "LockedWeakLazy{readLock=" + this.readLock + ", writeLock=" + this.writeLock + ", valueSupplier=" + this.valueSupplier + ", value=" + this.value + '}';
   }
}

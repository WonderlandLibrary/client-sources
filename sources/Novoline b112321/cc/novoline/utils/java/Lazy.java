package cc.novoline.utils.java;

import cc.novoline.utils.java.Lazy$DoubleCheckedLazy;
import cc.novoline.utils.java.Lazy$LockedWeakLazy;
import cc.novoline.utils.java.Lazy$SimpleLazy;
import cc.novoline.utils.java.Lazy$SimpleWeakLazy;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public interface Lazy extends Supplier {
   Object get();

   boolean isInitialized();

   static default Lazy create(@NotNull Supplier var0) {
      return new Lazy$SimpleLazy(var0);
   }

   static default Lazy createThreadSafe(@NotNull Supplier var0) {
      return new Lazy$DoubleCheckedLazy(var0);
   }

   static default Lazy createWeak(@NotNull Supplier var0) {
      return new Lazy$SimpleWeakLazy(var0);
   }

   static default Lazy createWeakThreadSafe(@NotNull Supplier var0) {
      return new Lazy$LockedWeakLazy(var0);
   }
}

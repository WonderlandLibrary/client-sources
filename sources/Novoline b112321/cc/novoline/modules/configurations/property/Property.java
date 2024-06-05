package cc.novoline.modules.configurations.property;

import org.jetbrains.annotations.Nullable;

public interface Property {
   @Nullable
   Object get();

   void set(@Nullable Object var1);
}

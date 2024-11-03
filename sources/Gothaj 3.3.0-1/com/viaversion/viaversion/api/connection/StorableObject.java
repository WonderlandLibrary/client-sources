package com.viaversion.viaversion.api.connection;

public interface StorableObject {
   default boolean clearOnServerSwitch() {
      return true;
   }

   default void onRemove() {
   }
}

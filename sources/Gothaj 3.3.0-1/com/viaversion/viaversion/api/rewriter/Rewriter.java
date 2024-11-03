package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;

public interface Rewriter<T extends Protocol> {
   void register();

   T protocol();

   default void onMappingDataLoaded() {
   }
}

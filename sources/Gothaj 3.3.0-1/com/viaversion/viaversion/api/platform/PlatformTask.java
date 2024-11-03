package com.viaversion.viaversion.api.platform;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface PlatformTask<T> {
   @Deprecated
   @Nullable
   T getObject();

   void cancel();
}

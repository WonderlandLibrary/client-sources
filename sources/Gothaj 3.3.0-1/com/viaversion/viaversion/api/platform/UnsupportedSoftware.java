package com.viaversion.viaversion.api.platform;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface UnsupportedSoftware {
   String getName();

   String getReason();

   @Nullable
   String match();

   default boolean findMatch() {
      return this.match() != null;
   }
}

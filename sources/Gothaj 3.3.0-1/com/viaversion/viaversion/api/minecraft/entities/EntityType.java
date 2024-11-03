package com.viaversion.viaversion.api.minecraft.entities;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityType {
   int getId();

   @Nullable
   EntityType getParent();

   String name();

   String identifier();

   boolean isAbstractType();

   @Deprecated
   default boolean is(EntityType... types) {
      for (EntityType type : types) {
         if (this == type) {
            return true;
         }
      }

      return false;
   }

   default boolean is(EntityType type) {
      return this == type;
   }

   default boolean isOrHasParent(EntityType type) {
      EntityType parent = this;

      while (parent != type) {
         parent = parent.getParent();
         if (parent == null) {
            return false;
         }
      }

      return true;
   }
}

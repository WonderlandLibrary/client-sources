package com.viaversion.viaversion.api.minecraft;

import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public enum RegistryType {
   BLOCK("block"),
   ITEM("item"),
   FLUID("fluid"),
   ENTITY("entity_type"),
   GAME_EVENT("game_event");

   private static final Map<String, RegistryType> MAP = new HashMap<>();
   private static final RegistryType[] VALUES = values();
   private final String resourceLocation;

   public static RegistryType[] getValues() {
      return VALUES;
   }

   @Nullable
   public static RegistryType getByKey(String resourceKey) {
      return MAP.get(resourceKey);
   }

   private RegistryType(String resourceLocation) {
      this.resourceLocation = resourceLocation;
   }

   @Deprecated
   public String getResourceLocation() {
      return this.resourceLocation;
   }

   public String resourceLocation() {
      return this.resourceLocation;
   }

   static {
      for (RegistryType type : getValues()) {
         MAP.put(type.resourceLocation, type);
      }
   }
}

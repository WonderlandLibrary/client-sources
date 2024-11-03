package com.viaversion.viaversion.libs.mcstructs.core;

import java.util.Objects;

public class Identifier {
   public static final String VALID_KEY_CHARS = "[_\\-a-z0-9.]*";
   public static final String VALID_VALUE_CHARS = "[_\\-a-z0-9/.]*";
   private final String key;
   private final String value;

   public static Identifier of(String value) {
      int splitIndex = value.indexOf(58);
      String key = splitIndex <= 0 ? "minecraft" : value.substring(0, splitIndex);
      String val = splitIndex == -1 ? value : value.substring(splitIndex + 1);
      return of(key, val);
   }

   public static Identifier tryOf(String value) {
      try {
         return of(value);
      } catch (Throwable var2) {
         return null;
      }
   }

   public static Identifier of(String key, String value) {
      return new Identifier(key, value);
   }

   private Identifier(String key, String value) {
      if (!key.matches("[_\\-a-z0-9.]*")) {
         throw new IllegalArgumentException("Key contains illegal chars");
      } else if (!value.matches("[_\\-a-z0-9/.]*")) {
         throw new IllegalArgumentException("Value contains illegal chars");
      } else {
         this.key = key;
         this.value = value;
      }
   }

   public String get() {
      return this.key + ":" + this.value;
   }

   public String getKey() {
      return this.key;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Identifier that = (Identifier)o;
         return Objects.equals(this.key, that.key) && Objects.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.key, this.value);
   }

   @Override
   public String toString() {
      return "Identifier{key='" + this.key + '\'' + ", value='" + this.value + '\'' + '}';
   }
}

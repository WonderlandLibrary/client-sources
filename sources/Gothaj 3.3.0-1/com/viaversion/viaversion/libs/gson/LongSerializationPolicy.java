package com.viaversion.viaversion.libs.gson;

public enum LongSerializationPolicy {
   DEFAULT {
      @Override
      public JsonElement serialize(Long value) {
         return (JsonElement)(value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
      }
   },
   STRING {
      @Override
      public JsonElement serialize(Long value) {
         return (JsonElement)(value == null ? JsonNull.INSTANCE : new JsonPrimitive(value.toString()));
      }
   };

   private LongSerializationPolicy() {
   }

   public abstract JsonElement serialize(Long var1);
}

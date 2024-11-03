package com.viaversion.viaversion.libs.gson;

public interface ExclusionStrategy {
   boolean shouldSkipField(FieldAttributes var1);

   boolean shouldSkipClass(Class<?> var1);
}

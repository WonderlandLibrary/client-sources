package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.reflect.TypeToken;

public interface TypeAdapterFactory {
   <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2);
}

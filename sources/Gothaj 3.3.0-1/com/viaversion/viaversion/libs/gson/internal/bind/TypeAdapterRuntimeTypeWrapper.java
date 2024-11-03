package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
   private final Gson context;
   private final TypeAdapter<T> delegate;
   private final Type type;

   TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type) {
      this.context = context;
      this.delegate = delegate;
      this.type = type;
   }

   @Override
   public T read(JsonReader in) throws IOException {
      return this.delegate.read(in);
   }

   @Override
   public void write(JsonWriter out, T value) throws IOException {
      TypeAdapter<T> chosen = this.delegate;
      Type runtimeType = getRuntimeTypeIfMoreSpecific(this.type, value);
      if (runtimeType != this.type) {
         TypeAdapter<T> runtimeTypeAdapter = this.context.getAdapter((TypeToken<T>)TypeToken.get(runtimeType));
         if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
            chosen = runtimeTypeAdapter;
         } else if (!isReflective(this.delegate)) {
            chosen = this.delegate;
         } else {
            chosen = runtimeTypeAdapter;
         }
      }

      chosen.write(out, value);
   }

   private static boolean isReflective(TypeAdapter<?> typeAdapter) {
      while (typeAdapter instanceof SerializationDelegatingTypeAdapter) {
         TypeAdapter<?> delegate = ((SerializationDelegatingTypeAdapter)typeAdapter).getSerializationDelegate();
         if (delegate != typeAdapter) {
            typeAdapter = delegate;
            continue;
         }
         break;
      }

      return typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter;
   }

   private static Type getRuntimeTypeIfMoreSpecific(Type type, Object value) {
      if (value != null && (type instanceof Class || type instanceof TypeVariable)) {
         type = value.getClass();
      }

      return type;
   }
}

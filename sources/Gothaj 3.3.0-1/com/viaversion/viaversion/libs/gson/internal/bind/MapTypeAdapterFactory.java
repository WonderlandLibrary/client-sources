package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Types;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.JsonReaderInternalAccess;
import com.viaversion.viaversion.libs.gson.internal.ObjectConstructor;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class MapTypeAdapterFactory implements TypeAdapterFactory {
   private final ConstructorConstructor constructorConstructor;
   final boolean complexMapKeySerialization;

   public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean complexMapKeySerialization) {
      this.constructorConstructor = constructorConstructor;
      this.complexMapKeySerialization = complexMapKeySerialization;
   }

   @Override
   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
      Type type = typeToken.getType();
      Class<? super T> rawType = typeToken.getRawType();
      if (!Map.class.isAssignableFrom(rawType)) {
         return null;
      } else {
         Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawType);
         TypeAdapter<?> keyAdapter = this.getKeyAdapter(gson, keyAndValueTypes[0]);
         TypeAdapter<?> valueAdapter = gson.getAdapter(TypeToken.get(keyAndValueTypes[1]));
         ObjectConstructor<T> constructor = this.constructorConstructor.get(typeToken);
         TypeAdapter<T> result = new MapTypeAdapterFactory.Adapter<>(gson, keyAndValueTypes[0], keyAdapter, keyAndValueTypes[1], valueAdapter, constructor);
         return result;
      }
   }

   private TypeAdapter<?> getKeyAdapter(Gson context, Type keyType) {
      return keyType != boolean.class && keyType != Boolean.class ? context.getAdapter(TypeToken.get(keyType)) : TypeAdapters.BOOLEAN_AS_STRING;
   }

   private final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {
      private final TypeAdapter<K> keyTypeAdapter;
      private final TypeAdapter<V> valueTypeAdapter;
      private final ObjectConstructor<? extends Map<K, V>> constructor;

      public Adapter(
         Gson context,
         Type keyType,
         TypeAdapter<K> keyTypeAdapter,
         Type valueType,
         TypeAdapter<V> valueTypeAdapter,
         ObjectConstructor<? extends Map<K, V>> constructor
      ) {
         this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(context, keyTypeAdapter, keyType);
         this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(context, valueTypeAdapter, valueType);
         this.constructor = constructor;
      }

      public Map<K, V> read(JsonReader in) throws IOException {
         JsonToken peek = in.peek();
         if (peek == JsonToken.NULL) {
            in.nextNull();
            return null;
         } else {
            Map<K, V> map = (Map<K, V>)this.constructor.construct();
            if (peek == JsonToken.BEGIN_ARRAY) {
               in.beginArray();

               while (in.hasNext()) {
                  in.beginArray();
                  K key = this.keyTypeAdapter.read(in);
                  V value = this.valueTypeAdapter.read(in);
                  V replaced = map.put(key, value);
                  if (replaced != null) {
                     throw new JsonSyntaxException("duplicate key: " + key);
                  }

                  in.endArray();
               }

               in.endArray();
            } else {
               in.beginObject();

               while (in.hasNext()) {
                  JsonReaderInternalAccess.INSTANCE.promoteNameToValue(in);
                  K key = this.keyTypeAdapter.read(in);
                  V value = this.valueTypeAdapter.read(in);
                  V replaced = map.put(key, value);
                  if (replaced != null) {
                     throw new JsonSyntaxException("duplicate key: " + key);
                  }
               }

               in.endObject();
            }

            return map;
         }
      }

      public void write(JsonWriter out, Map<K, V> map) throws IOException {
         if (map == null) {
            out.nullValue();
         } else if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
            out.beginObject();

            for (Entry<K, V> entry : map.entrySet()) {
               out.name(String.valueOf(entry.getKey()));
               this.valueTypeAdapter.write(out, entry.getValue());
            }

            out.endObject();
         } else {
            boolean hasComplexKeys = false;
            List<JsonElement> keys = new ArrayList<>(map.size());
            List<V> values = new ArrayList<>(map.size());

            for (Entry<K, V> entry : map.entrySet()) {
               JsonElement keyElement = this.keyTypeAdapter.toJsonTree(entry.getKey());
               keys.add(keyElement);
               values.add(entry.getValue());
               hasComplexKeys |= keyElement.isJsonArray() || keyElement.isJsonObject();
            }

            if (hasComplexKeys) {
               out.beginArray();
               int i = 0;

               for (int size = keys.size(); i < size; i++) {
                  out.beginArray();
                  Streams.write(keys.get(i), out);
                  this.valueTypeAdapter.write(out, values.get(i));
                  out.endArray();
               }

               out.endArray();
            } else {
               out.beginObject();
               int i = 0;

               for (int size = keys.size(); i < size; i++) {
                  JsonElement keyElement = keys.get(i);
                  out.name(this.keyToString(keyElement));
                  this.valueTypeAdapter.write(out, values.get(i));
               }

               out.endObject();
            }
         }
      }

      private String keyToString(JsonElement keyElement) {
         if (keyElement.isJsonPrimitive()) {
            JsonPrimitive primitive = keyElement.getAsJsonPrimitive();
            if (primitive.isNumber()) {
               return String.valueOf(primitive.getAsNumber());
            } else if (primitive.isBoolean()) {
               return Boolean.toString(primitive.getAsBoolean());
            } else if (primitive.isString()) {
               return primitive.getAsString();
            } else {
               throw new AssertionError();
            }
         } else if (keyElement.isJsonNull()) {
            return "null";
         } else {
            throw new AssertionError();
         }
      }
   }
}

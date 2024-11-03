package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.ToNumberPolicy;
import com.viaversion.viaversion.libs.gson.ToNumberStrategy;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public final class ObjectTypeAdapter extends TypeAdapter<Object> {
   private static final TypeAdapterFactory DOUBLE_FACTORY = newFactory(ToNumberPolicy.DOUBLE);
   private final Gson gson;
   private final ToNumberStrategy toNumberStrategy;

   private ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
      this.gson = gson;
      this.toNumberStrategy = toNumberStrategy;
   }

   private static TypeAdapterFactory newFactory(final ToNumberStrategy toNumberStrategy) {
      return new TypeAdapterFactory() {
         @Override
         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return type.getRawType() == Object.class ? new ObjectTypeAdapter(gson, toNumberStrategy) : null;
         }
      };
   }

   public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
      return toNumberStrategy == ToNumberPolicy.DOUBLE ? DOUBLE_FACTORY : newFactory(toNumberStrategy);
   }

   private Object tryBeginNesting(JsonReader in, JsonToken peeked) throws IOException {
      switch (peeked) {
         case BEGIN_ARRAY:
            in.beginArray();
            return new ArrayList();
         case BEGIN_OBJECT:
            in.beginObject();
            return new LinkedTreeMap();
         default:
            return null;
      }
   }

   private Object readTerminal(JsonReader in, JsonToken peeked) throws IOException {
      switch (peeked) {
         case STRING:
            return in.nextString();
         case NUMBER:
            return this.toNumberStrategy.readNumber(in);
         case BOOLEAN:
            return in.nextBoolean();
         case NULL:
            in.nextNull();
            return null;
         default:
            throw new IllegalStateException("Unexpected token: " + peeked);
      }
   }

   @Override
   public Object read(JsonReader in) throws IOException {
      JsonToken peeked = in.peek();
      Object current = this.tryBeginNesting(in, peeked);
      if (current == null) {
         return this.readTerminal(in, peeked);
      } else {
         Deque<Object> stack = new ArrayDeque<>();

         while (true) {
            while (!in.hasNext()) {
               if (current instanceof List) {
                  in.endArray();
               } else {
                  in.endObject();
               }

               if (stack.isEmpty()) {
                  return current;
               }

               current = stack.removeLast();
            }

            String name = null;
            if (current instanceof Map) {
               name = in.nextName();
            }

            peeked = in.peek();
            Object value = this.tryBeginNesting(in, peeked);
            boolean isNesting = value != null;
            if (value == null) {
               value = this.readTerminal(in, peeked);
            }

            if (current instanceof List) {
               List<Object> list = (List<Object>)current;
               list.add(value);
            } else {
               Map<String, Object> map = (Map<String, Object>)current;
               map.put(name, value);
            }

            if (isNesting) {
               stack.addLast(current);
               current = value;
            }
         }
      }
   }

   @Override
   public void write(JsonWriter out, Object value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         TypeAdapter<Object> typeAdapter = this.gson.getAdapter((Class<Object>)value.getClass());
         if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginObject();
            out.endObject();
         } else {
            typeAdapter.write(out, value);
         }
      }
   }
}

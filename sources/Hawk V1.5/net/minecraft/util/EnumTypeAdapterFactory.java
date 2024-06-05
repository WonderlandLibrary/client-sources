package net.minecraft.util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class EnumTypeAdapterFactory implements TypeAdapterFactory {
   private static final String __OBFID = "CL_00001494";

   static String access$0(EnumTypeAdapterFactory var0, Object var1) {
      return var0.func_151232_a(var1);
   }

   private String func_151232_a(Object var1) {
      return var1 instanceof Enum ? ((Enum)var1).name().toLowerCase(Locale.US) : var1.toString().toLowerCase(Locale.US);
   }

   public TypeAdapter create(Gson var1, TypeToken var2) {
      Class var3 = var2.getRawType();
      if (!var3.isEnum()) {
         return null;
      } else {
         HashMap var4 = Maps.newHashMap();
         Object[] var5 = var3.getEnumConstants();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Object var8 = var5[var7];
            var4.put(this.func_151232_a(var8), var8);
         }

         return new TypeAdapter(this, var4) {
            private final HashMap val$var4;
            private static final String __OBFID = "CL_00001495";
            final EnumTypeAdapterFactory this$0;

            {
               this.this$0 = var1;
               this.val$var4 = var2;
            }

            public void write(JsonWriter var1, Object var2) throws IOException {
               if (var2 == null) {
                  var1.nullValue();
               } else {
                  var1.value(EnumTypeAdapterFactory.access$0(this.this$0, var2));
               }

            }

            public Object read(JsonReader var1) throws IOException {
               if (var1.peek() == JsonToken.NULL) {
                  var1.nextNull();
                  return null;
               } else {
                  return this.val$var4.get(var1.nextString());
               }
            }
         };
      }
   }
}

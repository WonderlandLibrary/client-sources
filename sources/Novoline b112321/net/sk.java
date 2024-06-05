package net;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class sk {
   public static Object a(TypeSerializer var0, TypeToken var1, ConfigurationNode var2) {
      return var0.deserialize(var1, var2);
   }

   public static void a(TypeSerializer var0, TypeToken var1, Object var2, ConfigurationNode var3) {
      var0.serialize(var1, var2, var3);
   }
}

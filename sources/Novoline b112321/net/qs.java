package net;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.SimpleConfigurationNode;

public class qs {
   public static SimpleConfigurationNode a() {
      return SimpleConfigurationNode.root();
   }

   public static SimpleConfigurationNode a(SimpleConfigurationNode var0, Object var1) {
      return var0.setValue(var1);
   }

   public static Object a(SimpleConfigurationNode var0) {
      return var0.getValue();
   }

   public static ConfigurationNode a(SimpleConfigurationNode var0, TypeToken var1, Object var2) {
      return var0.a(var1, var2);
   }

   public static ConfigurationOptions b(SimpleConfigurationNode var0) {
      return var0.getOptions();
   }
}

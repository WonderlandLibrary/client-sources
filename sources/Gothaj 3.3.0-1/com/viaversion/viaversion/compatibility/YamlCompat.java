package com.viaversion.viaversion.compatibility;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

public interface YamlCompat {
   Representer createRepresenter(DumperOptions var1);

   SafeConstructor createSafeConstructor();

   static boolean isVersion1() {
      try {
         SafeConstructor.class.getDeclaredConstructor();
         return true;
      } catch (NoSuchMethodException var1) {
         return false;
      }
   }
}

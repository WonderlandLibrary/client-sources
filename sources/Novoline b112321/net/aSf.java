package net;

import java.util.concurrent.Callable;
import ninja.leaping.configurate.loader.AbstractConfigurationLoader$Builder;
import ninja.leaping.configurate.loader.HeaderMode;

public class aSf {
   public static Callable c(AbstractConfigurationLoader$Builder var0) {
      return var0.getSource();
   }

   public static Callable b(AbstractConfigurationLoader$Builder var0) {
      return var0.getSink();
   }

   public static HeaderMode a(AbstractConfigurationLoader$Builder var0) {
      return var0.getHeaderMode();
   }
}

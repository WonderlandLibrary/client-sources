package net;

import viaversion.viaversion.ViaManager;
import viaversion.viaversion.ViaManager$ViaManagerBuilder;
import viaversion.viaversion.api.platform.ViaInjector;
import viaversion.viaversion.api.platform.ViaPlatform;
import viaversion.viaversion.api.platform.ViaPlatformLoader;

public class a_x {
   public static ViaManager$ViaManagerBuilder a(ViaManager$ViaManagerBuilder var0, ViaInjector var1) {
      return var0.injector(var1);
   }

   public static ViaManager$ViaManagerBuilder a(ViaManager$ViaManagerBuilder var0, ViaPlatformLoader var1) {
      return var0.loader(var1);
   }

   public static ViaManager$ViaManagerBuilder a(ViaManager$ViaManagerBuilder var0, ViaPlatform var1) {
      return var0.platform(var1);
   }

   public static ViaManager a(ViaManager$ViaManagerBuilder var0) {
      return var0.build();
   }
}

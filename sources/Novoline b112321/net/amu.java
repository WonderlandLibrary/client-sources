package net;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionSpecBuilder;

public class amu {
   public static ArgumentAcceptingOptionSpec a(OptionSpecBuilder var0) {
      return var0.withRequiredArg();
   }

   public static ArgumentAcceptingOptionSpec b(OptionSpecBuilder var0) {
      return var0.withOptionalArg();
   }
}

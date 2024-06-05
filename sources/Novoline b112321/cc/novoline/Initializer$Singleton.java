package cc.novoline;

import cc.novoline.Initializer;

enum Initializer$Singleton {
   INSTANCE;

   private final Initializer value = new Initializer();
   private static final Initializer$Singleton[] $VALUES = new Initializer$Singleton[]{INSTANCE};

   static Initializer access$000(Initializer$Singleton var0) {
      return var0.value;
   }
}

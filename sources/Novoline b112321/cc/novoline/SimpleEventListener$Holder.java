package cc.novoline;

import cc.novoline.SimpleEventListener;

enum SimpleEventListener$Holder {
   INSTANCE;

   private final SimpleEventListener value = new SimpleEventListener();
   private static final SimpleEventListener$Holder[] $VALUES = new SimpleEventListener$Holder[]{INSTANCE};

   static SimpleEventListener access$000(SimpleEventListener$Holder var0) {
      return var0.value;
   }
}

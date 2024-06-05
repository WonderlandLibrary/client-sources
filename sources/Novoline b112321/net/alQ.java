package net;

import cc.novoline.modules.binds.MouseKeybind;

public class alQ {
   public static int b(MouseKeybind var0) {
      return var0.getKey();
   }

   public static boolean a(MouseKeybind var0) {
      return var0.click();
   }

   public static MouseKeybind a(int var0) {
      return MouseKeybind.of(var0);
   }
}

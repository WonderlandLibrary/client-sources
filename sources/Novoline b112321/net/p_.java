package net;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class p_ {
   public static Toolkit a() {
      return Toolkit.getDefaultToolkit();
   }

   public static Dimension b(Toolkit var0) {
      return var0.getScreenSize();
   }

   public static Clipboard a(Toolkit var0) {
      return var0.getSystemClipboard();
   }
}

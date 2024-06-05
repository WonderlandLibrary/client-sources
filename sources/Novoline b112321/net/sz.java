package net;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.Wa;
import net.acE;
import net.optifine.MatchBlock;

public class sz {
   public static final int a = 64;

   private sz() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static BufferedImage a(BufferedImage var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return null;
      } else {
         BufferedImage var2 = new BufferedImage(64, 64, 2);
         Graphics var3 = var2.getGraphics();
         Wa.a(var3, var0, 0, 0, 64, 64, (ImageObserver)null);
         var3.dispose();
         return var2;
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}

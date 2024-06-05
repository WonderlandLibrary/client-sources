package net;

import cc.novoline.gui.screen.alt.repository.Alt;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.gui.screen.alt.repository.TokenField;
import cc.novoline.gui.screen.alt.repository.credential.AltCredential;
import java.util.List;

public class me {
   private static boolean b;

   public static List d(AltRepositoryGUI var0) {
      return var0.getAlts();
   }

   public static void a(AltRepositoryGUI var0, Alt var1) {
      var0.setCurrentAlt(var1);
   }

   public static Alt e(AltRepositoryGUI var0) {
      return var0.getCurrentAlt();
   }

   public static float a(AltRepositoryGUI var0) {
      return var0.getAltBoxAlphaStep();
   }

   public static float h(AltRepositoryGUI var0) {
      return var0.getAltBoxAnimationStep();
   }

   public static void g(AltRepositoryGUI var0) {
      var0.saveAlts();
   }

   public static void b(AltRepositoryGUI var0, Alt var1) {
      var0.removeAlt(var1);
   }

   public static Alt b(AltRepositoryGUI var0) {
      return var0.getRandomAlt();
   }

   public static String c(AltRepositoryGUI var0) {
      return var0.getTokenContent();
   }

   public static Alt a(AltRepositoryGUI var0, AltCredential var1) {
      return var0.addAlt(var1);
   }

   public static TokenField f(AltRepositoryGUI var0) {
      return var0.getApiKeyField();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(c()) {
         b(true);
      }

   }
}

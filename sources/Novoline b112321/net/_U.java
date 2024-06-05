package net;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.image.ImageObserver;

public class _U {
   public static void a(Graphics2D var0, Key var1, Object var2) {
      var0.setRenderingHint(var1, var2);
   }

   public static boolean a(Graphics2D var0, Image var1, int var2, int var3, int var4, int var5, ImageObserver var6) {
      return var0.drawImage(var1, var2, var3, var4, var5, var6);
   }

   public static void a(Graphics2D var0, Font var1) {
      var0.setFont(var1);
   }

   public static void a(Graphics2D var0, Color var1) {
      var0.setColor(var1);
   }

   public static void a(Graphics2D var0, int var1, int var2, int var3, int var4) {
      var0.fillRect(var1, var2, var3, var4);
   }

   public static FontMetrics a(Graphics2D var0) {
      return var0.getFontMetrics();
   }

   public static void a(Graphics2D var0, String var1, int var2, int var3) {
      var0.drawString(var1, var2, var3);
   }
}

package net;

import net.minecraft.realms.RealmsClickableScrolledSelectionList;
import net.minecraft.realms.Tezzelator;

public class a6s {
   public static int d(RealmsClickableScrolledSelectionList var0) {
      return var0.getItemCount();
   }

   public static void a(RealmsClickableScrolledSelectionList var0, int var1, boolean var2, int var3, int var4) {
      var0.selectItem(var1, var2, var3, var4);
   }

   public static boolean a(RealmsClickableScrolledSelectionList var0, int var1) {
      return var0.isSelectedItem(var1);
   }

   public static void c(RealmsClickableScrolledSelectionList var0) {
      var0.renderBackground();
   }

   public static void a(RealmsClickableScrolledSelectionList var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var0.renderItem(var1, var2, var3, var4, var5, var6);
   }

   public static int b(RealmsClickableScrolledSelectionList var0) {
      return var0.getMaxPosition();
   }

   public static int a(RealmsClickableScrolledSelectionList var0) {
      return var0.getScrollbarPosition();
   }

   public static void a(RealmsClickableScrolledSelectionList var0, int var1, int var2, int var3, float var4, int var5) {
      var0.customMouseEvent(var1, var2, var3, var4, var5);
   }

   public static void a(RealmsClickableScrolledSelectionList var0, int var1, int var2, int var3, Tezzelator var4) {
      var0.renderSelected(var1, var2, var3, var4);
   }
}

package net;

import net.acE;
import net.axm;
import net.minecraft.realms.RealmsScrolledSelectionList;

public class aEr {
   public static int a(RealmsScrolledSelectionList var0) {
      return var0.getItemCount();
   }

   public static void a(RealmsScrolledSelectionList var0, int var1, boolean var2, int var3, int var4) {
      var0.selectItem(var1, var2, var3, var4);
   }

   public static boolean a(RealmsScrolledSelectionList var0, int var1) {
      return var0.isSelectedItem(var1);
   }

   public static void b(RealmsScrolledSelectionList var0) {
      var0.renderBackground();
   }

   public static void a(RealmsScrolledSelectionList var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      String[] var7 = axm.b();
      var0.renderItem(var1, var2, var3, var4, var5, var6);
      if(acE.b() == null) {
         axm.b(new String[2]);
      }

   }

   public static int d(RealmsScrolledSelectionList var0) {
      return var0.getMaxPosition();
   }

   public static int c(RealmsScrolledSelectionList var0) {
      return var0.getScrollbarPosition();
   }
}

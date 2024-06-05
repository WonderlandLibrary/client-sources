package net;

import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.visual.TabGUI;
import cc.novoline.modules.visual.tabgui.TabModule;
import cc.novoline.modules.visual.tabgui.TabType;
import java.util.List;

public class iq {
   private static int[] b;

   public static boolean a(TabType var0) {
      return var0.isOpened();
   }

   public static TabModule b(TabType var0) {
      return var0.getSelectedModule();
   }

   public static List c(TabType var0) {
      return var0.getModules();
   }

   public static void a(TabType var0, boolean var1) {
      var0.setOpened(var1);
   }

   public static TabGUI d(TabType var0) {
      return var0.getTabGUI();
   }

   public static EnumModuleType e(TabType var0) {
      return var0.getType();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[5]);
      }

   }
}

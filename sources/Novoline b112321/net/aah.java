package net;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.visual.tabgui.TabModule;
import cc.novoline.modules.visual.tabgui.TabSetting;
import cc.novoline.modules.visual.tabgui.TabType;
import java.util.List;

public class aah {
   public static boolean g(TabModule var0) {
      return var0.isOpened();
   }

   public static TabSetting a(TabModule var0) {
      return var0.getSelectedSetting();
   }

   public static AbstractModule e(TabModule var0) {
      return var0.getMod();
   }

   public static List d(TabModule var0) {
      return var0.getSettings();
   }

   public static boolean b(TabModule var0) {
      return var0.areSettingsEmpty();
   }

   public static void a(TabModule var0, boolean var1) {
      var0.setOpened(var1);
   }

   public static int f(TabModule var0) {
      return var0.getLongest();
   }

   public static TabType c(TabModule var0) {
      return var0.getType();
   }
}

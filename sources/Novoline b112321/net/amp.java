package net;

import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.modules.visual.tabgui.TabModule;
import cc.novoline.modules.visual.tabgui.TabSetting;
import cc.novoline.modules.visual.tabgui.TabValue;
import java.util.List;

public class amp {
   public static Setting b(TabSetting var0) {
      return var0.getSetting();
   }

   public static TabValue c(TabSetting var0) {
      return var0.getSelectedValue();
   }

   public static boolean d(TabSetting var0) {
      return var0.isOpened();
   }

   public static List f(TabSetting var0) {
      return var0.getValues();
   }

   public static void a(TabSetting var0, boolean var1) {
      var0.setOpened(var1);
   }

   public static TabModule e(TabSetting var0) {
      return var0.getModule();
   }

   public static int a(TabSetting var0) {
      return var0.getLongestS();
   }
}

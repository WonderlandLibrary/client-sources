package cc.novoline.gui.screen.setting;

import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.modules.AbstractModule;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Manager {
   private static final List settingList = new CopyOnWriteArrayList();

   public static void put(Setting var0) {
      settingList.add(var0);
   }

   public static List getSettingsByMod(AbstractModule var0) {
      return (List)settingList.stream().filter(Manager::lambda$getSettingsByMod$0).collect(Collectors.toCollection(CopyOnWriteArrayList::<init>));
   }

   public static List getSettingList() {
      return settingList;
   }

   private static boolean lambda$getSettingsByMod$0(AbstractModule var0, Setting var1) {
      return var1.getParentModule().equals(var0);
   }
}

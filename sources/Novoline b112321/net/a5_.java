package net;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.ModuleManager$ModuleCreator;
import cc.novoline.modules.configurations.holder.CreatingModuleHolder;
import com.google.common.reflect.TypeToken;
import java.util.Map;

public class a5_ {
   public static Map a(CreatingModuleHolder var0) {
      return var0.getFields();
   }

   public static AbstractModule b(CreatingModuleHolder var0) {
      return var0.getModule();
   }

   public static String c(CreatingModuleHolder var0) {
      return var0.getName();
   }

   public static AbstractModule d(CreatingModuleHolder var0) {
      return var0.createNew();
   }

   public static CreatingModuleHolder a(ModuleManager var0, String var1, ModuleManager$ModuleCreator var2) {
      return CreatingModuleHolder.of(var0, var1, var2);
   }

   public static TypeToken e(CreatingModuleHolder var0) {
      return var0.getTypeToken();
   }
}

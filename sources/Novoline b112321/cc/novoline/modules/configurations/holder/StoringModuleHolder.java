package cc.novoline.modules.configurations.holder;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.utils.java.Checks;

public final class StoringModuleHolder extends ModuleHolder {
   private StoringModuleHolder(String var1, AbstractModule var2) {
      super(var1, var2);
   }

   public static StoringModuleHolder of(String var0, AbstractModule var1) {
      Checks.notBlank(var0, "name");
      Checks.notNull(var1, "module");
      return new StoringModuleHolder(var0, var1);
   }

   public void setModule(AbstractModule var1) {
      Checks.notNull(var1, "module");
      this.module = var1;
   }
}

package cc.novoline.modules.configurations;

import cc.novoline.modules.ModuleArrayMap;
import cc.novoline.modules.configurations.ConfigManager;
import cc.novoline.utils.java.Checks;
import net.WF;
import ninja.leaping.configurate.objectmapping.Setting;

@WF
public final class ClientConfig {
   @Setting("modules")
   private ModuleArrayMap modules;
   @Setting("config-version")
   private int configVersion;

   public ClientConfig() {
   }

   private ClientConfig(ModuleArrayMap var1, int var2) {
      this.modules = var1;
      this.configVersion = var2;
   }

   public static ClientConfig of(String var0, ModuleArrayMap var1, int var2) {
      Checks.notNull(var1, "modules");
      return new ClientConfig(var1, var2);
   }

   public static ClientConfig of(ConfigManager var0, String var1) {
      Checks.notNull(var0, "module manager");
      return new ClientConfig(var0.getModuleManager().getModuleManager(), var0.getConfigVersion());
   }

   public ModuleArrayMap getModules() {
      return this.modules;
   }

   public int getConfigVersion() {
      return this.configVersion;
   }

   public String toString() {
      return "ClientConfig{options=" + this.modules + ", configVersion=" + this.configVersion + '}';
   }
}

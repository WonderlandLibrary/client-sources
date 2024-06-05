package cc.novoline.modules;

import cc.novoline.Novoline;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleArrayMap;
import cc.novoline.modules.ModuleManager$ModuleCreator;
import cc.novoline.modules.binds.BindManager;
import cc.novoline.modules.configurations.ConfigManager;
import cc.novoline.modules.configurations.holder.CreatingModuleHolder;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.exceptions.UnregisteredModuleException;
import cc.novoline.utils.java.Checks;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import net.acE;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ModuleManager {
   private final Novoline novoline;
   private final ConfigManager configManager;
   private final ModuleArrayMap moduleManager;
   private final BindManager bindManager;
   private final List abstractModules;

   public ModuleManager(@NotNull Novoline var1, int var2) {
      AbstractModule.d();
      this.moduleManager = new ModuleArrayMap();
      this.abstractModules = new CopyOnWriteArrayList();
      this.novoline = var1;
      this.configManager = new ConfigManager(this, var2);
      this.bindManager = new BindManager(this, var2);
      if(acE.b() == null) {
         AbstractModule.b(new int[5]);
      }

   }

   @NotNull
   public AbstractModule getModule(Class var1) {
      return this.moduleManager.getByClass(var1);
   }

   @Nullable
   public AbstractModule getByNameIgnoreCase(String var1) {
      return this.moduleManager.getByNameIgnoreCase(var1);
   }

   @NotNull
   public List getModuleListByCategory(EnumModuleType var1) {
      return this.moduleManager.getByCategory(var1);
   }

   public void a(AbstractModule var1) {
      this.moduleManager.values().removeIf(ModuleManager::lambda$removeByModule$0);
   }

   public void a(@NotNull String var1, @NotNull ModuleManager$ModuleCreator var2, boolean var3) {
      AbstractModule.d();
      Checks.notBlank(var1, "name");
      CreatingModuleHolder var5 = CreatingModuleHolder.of(this, var1, var2);
      if(this.isRegistered(var5.getTypeToken().getRawType())) {
         throw new IllegalStateException("module may not be registered twice as holder " + var5.getName() + " exists");
      } else {
         this.moduleManager.put(var1, var5);
      }
   }

   public boolean isRegistered(@NotNull Class var1) {
      try {
         this.moduleManager.getByClass(var1);
         return true;
      } catch (UnregisteredModuleException var3) {
         return false;
      }
   }

   @NotNull
   public Novoline getNovoline() {
      return this.novoline;
   }

   @NotNull
   public ConfigManager getConfigManager() {
      return this.configManager;
   }

   @NotNull
   public BindManager getBindManager() {
      return this.bindManager;
   }

   @NotNull
   public ModuleArrayMap getModuleManager() {
      return this.moduleManager;
   }

   @NotNull
   public List getAbstractModules() {
      return this.abstractModules;
   }

   private static boolean lambda$removeByModule$0(AbstractModule var0, ModuleHolder var1) {
      return var1.getModule() == var0;
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}

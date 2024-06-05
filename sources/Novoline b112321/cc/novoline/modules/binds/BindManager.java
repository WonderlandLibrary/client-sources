package cc.novoline.modules.binds;

import cc.novoline.Novoline;
import cc.novoline.modules.ModuleArrayMap;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.binds.BindManager$2;
import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.serializers.KeybindSerializer;
import cc.novoline.utils.notifications.NotificationType;
import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.X9;
import net.a6t;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BindManager {
   private final ModuleManager moduleManager;
   private final int configVersion;
   private Path bindsFile;
   private static ConfigurationOptions DEFAULT_OPTIONS;

   public BindManager(@NotNull ModuleManager var1, int var2) {
      this.moduleManager = var1;
      this.configVersion = var2;
   }

   public void load() {
      ModuleArrayMap var2 = this.moduleManager.getModuleManager();
      KeyboardKeybind.b();
      Map var3 = this.readBindsFromFile();
      Iterator var4 = var3.entrySet().iterator();
      if(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         String var6 = (String)var5.getKey();
         ModuleKeybind var7 = (ModuleKeybind)var5.getValue();
         ObjectIterator var8 = var2.entrySet().iterator();
         if(var8.hasNext()) {
            Entry var9 = (Entry)var8.next();
            if(((String)var9.getKey()).equalsIgnoreCase(var6)) {
               ((ModuleHolder)var9.getValue()).getModule().setKeyBind(var7);
            }
         }
      }

   }

   public boolean save() {
      KeyboardKeybind.b();
      boolean var2 = false;
      BindManager var10000 = this;

      try {
         Path var3 = var10000.getBindsFileAndCreateIfNotExists();
         return false;
      } catch (Throwable var4) {
         Novoline.getInstance().getNotificationManager().pop("Cannot save binds!", 5000, NotificationType.ERROR);
         if(!var2) {
            Novoline.getLogger().warn("An unexpected error occurred while deleting config file!", var4);
         }

         return false;
      }
   }

   @NotNull
   private Map readBindsFromFile() {
      KeyboardKeybind.b();
      Path var2 = this.getBindsFile();
      if(Files.notExists(var2, new LinkOption[0])) {
         Novoline.getLogger().error("Binds file doesn\'t exist");
         return Collections.emptyMap();
      } else {
         ConfigurationOptions var3 = this.defaultOptions();
         HoconConfigurationLoader var4 = ((a6t)((a6t)HoconConfigurationLoader.b().setDefaultOptions(var3)).setPath(var2)).a();
         HoconConfigurationLoader var10000 = var4;
         ConfigurationOptions var10001 = var3;

         ConfigurationNode var5;
         try {
            var5 = var10000.load(var10001);
         } catch (IOException var9) {
            Novoline.getLogger().error("An I/O error occurred while reading binds", var9);
            return Collections.emptyMap();
         }

         Map var6 = Collections.emptyMap();

         try {
            var6 = (Map)var5.getValue((TypeToken)(new BindManager$2(this)));
            return var6 == null?Collections.emptyMap():var6;
         } catch (X9 var8) {
            Novoline.getLogger().error("An I/O error occurred while deserializing binds", var8);
            return var6;
         }
      }
   }

   @Nullable
   private Path getBindsFileAndCreateIfNotExists() {
      KeyboardKeybind.b();
      Path var2 = this.getBindsFile();
      if(Files.notExists(var2, new LinkOption[0])) {
         try {
            Files.createFile(var2, new FileAttribute[0]);
         } catch (IOException var4) {
            Novoline.getLogger().warn("An I/O error occurred while creating binds file!", var4);
            return null;
         }
      }

      return var2;
   }

   @NotNull
   public Path getBindsFile() {
      String var1 = KeyboardKeybind.b();
      return this.bindsFile == null?(this.bindsFile = this.moduleManager.getNovoline().getDataFolder().resolve("binds.novo")):this.bindsFile;
   }

   @NotNull
   private ConfigurationOptions defaultOptions() {
      String var1 = KeyboardKeybind.b();
      if(DEFAULT_OPTIONS == null) {
         ConfigurationOptions var2 = ConfigurationOptions.defaults();
         var2.f().a((TypeToken)TypeToken.of(ModuleKeybind.class), new KeybindSerializer());
         DEFAULT_OPTIONS = var2;
         return var2;
      } else {
         return DEFAULT_OPTIONS;
      }
   }

   private static ModuleKeybind lambda$save$0(Entry var0) {
      return (ModuleKeybind)((ModuleHolder)var0.getValue()).getModule().getKeybind().get();
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

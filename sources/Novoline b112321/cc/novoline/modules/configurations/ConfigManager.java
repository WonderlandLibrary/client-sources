package cc.novoline.modules.configurations;

import cc.novoline.Novoline;
import cc.novoline.modules.ModuleArrayMap;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.ClientConfig;
import cc.novoline.modules.configurations.ConfigManager$1;
import cc.novoline.modules.configurations.holder.CreatingModuleHolder;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.configurations.property.mapper.PropertyMapperFactory;
import cc.novoline.modules.exceptions.OutdatedConfigException;
import cc.novoline.modules.exceptions.ReadConfigException;
import cc.novoline.modules.serializers.ConfigSerializer;
import cc.novoline.modules.serializers.ModuleMapSerializer;
import cc.novoline.modules.serializers.PropertySerializer;
import cc.novoline.utils.java.Checks;
import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.Ea;
import net.J8;
import net.X9;
import net.a6t;
import net.acE;
import net.ahu;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.jetbrains.annotations.NotNull;

public final class ConfigManager {
   private final ModuleManager moduleManager;
   private final int configVersion;
   private static ConfigurationOptions DEFAULT_OPTIONS;
   private static final String a = ".novo";
   private static int[] c;

   public ConfigManager(@NotNull ModuleManager var1, int var2) {
      this.moduleManager = var1;
      this.configVersion = var2;
   }

   public void load(@NotNull String var1, boolean var2) throws IOException, X9 {
      Path var3 = this.getConfigPath(var1);
      this.load(var3, var2);
   }

   public void load(@NotNull Path var1, boolean var2) throws IOException, X9 {
      int[] var3 = c();
      if(Files.notExists(var1, new LinkOption[0])) {
         throw new FileNotFoundException("file doesn\'t exist");
      } else {
         ConfigurationOptions var4 = this.defaultOptions();
         HoconConfigurationLoader var5 = ((a6t)((a6t)HoconConfigurationLoader.b().setDefaultOptions(var4)).setPath(var1)).a();
         HoconConfigurationLoader var10000 = var5;
         ConfigurationOptions var10001 = var4;

         ConfigurationNode var6;
         try {
            var6 = var10000.load(var10001);
         } catch (IOException var21) {
            throw new ReadConfigException(var21);
         }

         ClientConfig var7 = (ClientConfig)var6.getValue(TypeToken.of(ClientConfig.class));
         if(var7 != null) {
            if(var7.getConfigVersion() != this.configVersion) {
               throw new OutdatedConfigException();
            } else {
               ModuleArrayMap var8 = this.moduleManager.getModuleManager();
               ObjectIterator var9 = var8.values().iterator();
               if(var9.hasNext()) {
                  ModuleHolder var10 = (ModuleHolder)var9.next();
                  var10.getModule().setEnabled(false);
               }

               ObjectIterator var24 = var7.getModules().entrySet().iterator();
               if(var24.hasNext()) {
                  Entry var11 = (Entry)var24.next();
                  String var12 = (String)var11.getKey();
                  ModuleHolder var13 = (ModuleHolder)var11.getValue();
                  CreatingModuleHolder var23;
                  if((var23 = (CreatingModuleHolder)var8.get(var12)) != null) {
                     int var14 = 0;
                     int var15 = 0;
                     Iterator var16 = var23.getFields().entrySet().iterator();
                     if(var16.hasNext()) {
                        Entry var17 = (Entry)var16.next();
                        String var18 = (String)var17.getKey();
                        ahu var19 = (ahu)var17.getValue();
                        ahu var27 = var19;
                        ModuleHolder var28 = var13;

                        try {
                           var27.a(var28.getModule(), var23.getModule(), var2);
                           ++var14;
                        } catch (Throwable var22) {
                           Novoline.getLogger().warn("An error occurred while updating property: value: " + var18 + ", module: " + var23.getName(), var22);
                           ++var15;
                        }
                     }
                  }
               }

            }
         }
      }
   }

   public String saveToString(@NotNull String var1) throws IOException, X9 {
      c();
      Path var3 = this.getConfigPath(var1);
      StringWriter var4 = new StringWriter();
      ConfigurationOptions var5 = this.defaultOptions();
      HoconConfigurationLoader var6 = ((a6t)((a6t)((a6t)HoconConfigurationLoader.b().setDefaultOptions(var5)).setPath(var3)).setSink(ConfigManager::lambda$saveToString$0)).a();
      HoconConfigurationLoader var10000 = var6;
      ConfigurationOptions var10001 = var5;

      ConfigurationNode var7;
      try {
         var7 = var10000.load(var10001);
      } catch (IOException var9) {
         throw new ReadConfigException(var9);
      }

      ClientConfig var8 = ClientConfig.of(this, var1);
      var7.a(TypeToken.of(ClientConfig.class), var8);
      var6.save(var7);
      return var4.toString();
   }

   public boolean save(@NotNull String var1) throws ReadConfigException, IOException, X9 {
      c();
      boolean var3 = false;
      Path var4 = this.getConfigPath(var1);
      if(Files.notExists(var4, new LinkOption[0])) {
         Files.createFile(var4, new FileAttribute[0]);
      }

      ConfigurationOptions var5 = this.defaultOptions();
      HoconConfigurationLoader var6 = ((a6t)((a6t)HoconConfigurationLoader.b().setDefaultOptions(var5)).setPath(var4)).a();
      HoconConfigurationLoader var10000 = var6;
      ConfigurationOptions var10001 = var5;

      ConfigurationNode var7;
      try {
         var7 = var10000.load(var10001);
      } catch (IOException var9) {
         throw new ReadConfigException(var9);
      }

      ClientConfig var8 = ClientConfig.of(this, var1);
      var7.a(TypeToken.of(ClientConfig.class), var8);
      var6.save(var7);
      if(acE.b() == null) {
         b(new int[1]);
      }

      return true;
   }

   public boolean delete(@NotNull String var1) throws IOException {
      c();
      Path var3 = this.getConfigPath(var1);
      if(Files.notExists(var3, new LinkOption[0])) {
         throw new FileNotFoundException();
      } else {
         Files.deleteIfExists(var3);
         return true;
      }
   }

   @NotNull
   public List getConfigs() {
      Stream var1;
      try {
         var1 = Files.walk(this.getConfigsFolder(), new FileVisitOption[0]);
      } catch (IOException var3) {
         Novoline.getLogger().error("An I/O error occurred while getting contents of configs folder", var3);
         return Collections.emptyList();
      }

      return (List)var1.filter(ConfigManager::lambda$getConfigs$1).map(Path::getFileName).map(Path::toString).filter(ConfigManager::lambda$getConfigs$2).map(ConfigManager::lambda$getConfigs$3).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   @NotNull
   public Path getConfigPath(@NotNull String var1) {
      Checks.notBlank(var1, "name");
      return this.getConfigsFolder().resolve(var1 + ".novo");
   }

   @NotNull
   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public int getConfigVersion() {
      return this.configVersion;
   }

   @NotNull
   public Path getConfigsFolder() {
      c();
      Path var2 = this.moduleManager.getNovoline().getDataFolder();
      Path var3 = var2.resolve("configs");
      if(Files.isRegularFile(var3, new LinkOption[0])) {
         boolean var4 = false;
         int var5 = 1;
         if(var5 < Integer.MAX_VALUE) {
            label57: {
               try {
                  Files.move(var3, var2.resolve("configs-" + var5), new CopyOption[0]);
                  var4 = true;
                  break label57;
               } catch (FileAlreadyExistsException var8) {
                  ;
               } catch (IOException var9) {
                  Novoline.getLogger().error("An I/O error occurred while creating configs folder", var9);
               }

               ++var5;
            }
         }

         if(!var4) {
            Novoline.getLogger().error("User created 2.147.483.648 configs-# files. Cannot create configs folder");
         }
      }

      if(Files.notExists(var3, new LinkOption[0])) {
         try {
            Files.createDirectories(var3, new FileAttribute[0]);
         } catch (IOException var7) {
            Novoline.getLogger().error("An I/O error occurred while creating configs folder", var7);
            var7.printStackTrace();
         }
      }

      return var3;
   }

   @NotNull
   private ConfigurationOptions defaultOptions() {
      int[] var1 = c();
      if(DEFAULT_OPTIONS == null) {
         ConfigurationOptions var2 = ConfigurationOptions.defaults().setObjectMapperFactory(new PropertyMapperFactory());
         Ea var3 = TypeSerializers.b();
         var3.a((TypeToken)(new ConfigManager$1(this)), new PropertySerializer());
         var3.a((TypeToken)TypeToken.of(ModuleArrayMap.class), new ModuleMapSerializer(this.moduleManager));
         var3.a((Predicate)(ConfigManager::lambda$defaultOptions$4), new ConfigSerializer(this.moduleManager));
         var3 = TypeSerializers.a().a(var3);
         var2 = var2.a(var3);
         DEFAULT_OPTIONS = var2;
         return var2;
      } else {
         return DEFAULT_OPTIONS;
      }
   }

   public static String getExtension() {
      return ".novo";
   }

   private static boolean lambda$defaultOptions$4(TypeToken var0) {
      c();
      Class var2 = var0.getRawType();

      boolean var3;
      while(true) {
         if(!(var3 = var2.isAnnotationPresent(J8.class))) {
            var2 = var2.getSuperclass();
         }

         if(var3 || var2 == null || var2.getSuperclass() == null) {
            break;
         }
      }

      return var3;
   }

   private static String lambda$getConfigs$3(String var0) {
      return var0.substring(0, var0.length() - ".novo".length());
   }

   private static boolean lambda$getConfigs$2(String var0) {
      return var0.endsWith(".novo");
   }

   private static boolean lambda$getConfigs$1(Path var0) {
      return Files.isRegularFile(var0, new LinkOption[0]);
   }

   private static BufferedWriter lambda$saveToString$0(StringWriter var0) throws Exception {
      return new BufferedWriter(var0);
   }

   public static void b(int[] var0) {
      c = var0;
   }

   public static int[] c() {
      return c;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b(new int[1]);
   }
}

package cc.novoline.modules;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.Config$1;
import cc.novoline.modules.configurations.property.mapper.PropertyMapperFactory;
import cc.novoline.modules.exceptions.LoadConfigException;
import cc.novoline.modules.serializers.PropertySerializer;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Path;
import net.Ea;
import net.a6t;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class Config {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Path file;
   private final HoconConfigurationLoader loader;
   private ConfigurationNode rootNode;

   private Config(@NotNull Path var1) {
      this.file = var1;
      this.loader = ((a6t)((a6t)HoconConfigurationLoader.b().setDefaultOptions(this.defaultOptions())).setPath(this.file)).a();
   }

   @NotNull
   private ConfigurationOptions defaultOptions() {
      ConfigurationOptions var1 = ConfigurationOptions.defaults().setObjectMapperFactory(new PropertyMapperFactory());
      Ea var2 = TypeSerializers.b();
      var2.a((TypeToken)(new Config$1(this)), new PropertySerializer());
      var2 = TypeSerializers.a().a(var2);
      var1 = var1.a(var2);
      return var1;
   }

   @NotNull
   public static Config fromPath(@NotNull Path var0) {
      return new Config(var0);
   }

   @NotNull
   public ConfigurationNode getNode(String var1) {
      AbstractModule.d();
      ConfigurationNode var3 = this.getRootNode();
      if(var1 != null && !var1.trim().isEmpty()) {
         return var3.getNode(new Object[]{var1});
      } else {
         throw new IllegalArgumentException("version may not be blank!");
      }
   }

   public void load() throws LoadConfigException {
      // $FF: Couldn't be decompiled
   }

   public void save() throws IOException {
      try {
         this.loader.save(this.getRootNode());
      } catch (IOException var2) {
         throw new IOException("An error occurred while saving the config!", var2);
      }
   }

   @NotNull
   public HoconConfigurationLoader getLoader() {
      return this.loader;
   }

   @NotNull
   public ConfigurationNode getRootNode() {
      int[] var1 = AbstractModule.d();
      return this.rootNode;
   }

   public void setRootNode(@NotNull ConfigurationNode var1) {
      this.rootNode = var1;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}

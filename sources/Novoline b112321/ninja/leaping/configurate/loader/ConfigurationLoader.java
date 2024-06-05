package ninja.leaping.configurate.loader;

import java.io.IOException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationLoader {
   @NotNull
   ConfigurationOptions getDefaultOptions();

   @NotNull
   default ConfigurationNode load() throws IOException {
      return this.load(this.getDefaultOptions());
   }

   @NotNull
   ConfigurationNode load(@NotNull ConfigurationOptions var1) throws IOException;

   void save(@NotNull ConfigurationNode var1) throws IOException;

   @NotNull
   default ConfigurationNode createEmptyNode() {
      return this.createEmptyNode(this.getDefaultOptions());
   }

   @NotNull
   ConfigurationNode createEmptyNode(@NotNull ConfigurationOptions var1);

   default boolean c() {
      return true;
   }

   default boolean a() {
      return true;
   }
}

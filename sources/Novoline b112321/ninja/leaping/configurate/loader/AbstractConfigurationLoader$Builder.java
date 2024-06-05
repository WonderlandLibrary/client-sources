package ninja.leaping.configurate.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import net.awr;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.AtomicFiles;
import ninja.leaping.configurate.loader.HeaderMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractConfigurationLoader$Builder {
   @NotNull
   protected HeaderMode headerMode = HeaderMode.PRESERVE;
   @Nullable
   protected Callable source;
   @Nullable
   protected Callable sink;
   @NotNull
   protected ConfigurationOptions defaultOptions = ConfigurationOptions.defaults();

   @NotNull
   private AbstractConfigurationLoader$Builder self() {
      return this;
   }

   @NotNull
   public AbstractConfigurationLoader$Builder setFile(@NotNull File var1) {
      return this.setPath(((File)Objects.requireNonNull(var1, "file")).toPath());
   }

   @NotNull
   public AbstractConfigurationLoader$Builder setPath(@NotNull Path var1) {
      Path var2 = ((Path)Objects.requireNonNull(var1, "path")).toAbsolutePath();
      this.source = AbstractConfigurationLoader$Builder::lambda$setPath$0;
      this.sink = AtomicFiles.createAtomicWriterFactory(var2, StandardCharsets.UTF_8);
      return this.self();
   }

   @NotNull
   public AbstractConfigurationLoader$Builder setURL(@NotNull URL var1) {
      Objects.requireNonNull(var1, "url");
      this.source = AbstractConfigurationLoader$Builder::lambda$setURL$1;
      return this.self();
   }

   @Nullable
   public Callable getSource() {
      return this.source;
   }

   @NotNull
   public AbstractConfigurationLoader$Builder setSource(@Nullable Callable var1) {
      this.source = var1;
      return this.self();
   }

   @Nullable
   public Callable getSink() {
      return this.sink;
   }

   @NotNull
   public AbstractConfigurationLoader$Builder setSink(@Nullable Callable var1) {
      this.sink = var1;
      return this.self();
   }

   @NotNull
   public HeaderMode getHeaderMode() {
      return this.headerMode;
   }

   @NotNull
   public AbstractConfigurationLoader$Builder setHeaderMode(@NotNull HeaderMode var1) {
      this.headerMode = (HeaderMode)Objects.requireNonNull(var1, "mode");
      return this.self();
   }

   /** @deprecated */
   @Deprecated
   @NotNull
   public AbstractConfigurationLoader$Builder setPreservesHeader(boolean var1) {
      this.headerMode = HeaderMode.PRESERVE;
      return this.self();
   }

   /** @deprecated */
   @Deprecated
   public boolean preservesHeader() {
      return this.headerMode == HeaderMode.PRESERVE;
   }

   @NotNull
   public ConfigurationOptions getDefaultOptions() {
      return this.defaultOptions;
   }

   @NotNull
   public AbstractConfigurationLoader$Builder setDefaultOptions(@NotNull ConfigurationOptions var1) {
      this.defaultOptions = (ConfigurationOptions)Objects.requireNonNull(var1, "defaultOptions");
      return this.self();
   }

   @NotNull
   public abstract awr g();

   private static BufferedReader lambda$setURL$1(URL var0) throws Exception {
      return new BufferedReader(new InputStreamReader(var0.openConnection().getInputStream(), StandardCharsets.UTF_8));
   }

   private static BufferedReader lambda$setPath$0(Path var0) throws Exception {
      return Files.newBufferedReader(var0, StandardCharsets.UTF_8);
   }
}

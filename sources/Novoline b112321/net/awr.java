package net;

import com.google.common.base.Splitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.Callable;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.AbstractConfigurationLoader$Builder;
import ninja.leaping.configurate.loader.CommentHandler;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.loader.HeaderMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class awr implements ConfigurationLoader {
   public static final String a = "\n";
   protected static final Splitter d = Splitter.on("\n");
   protected static final String c = System.lineSeparator();
   @Nullable
   protected final Callable f;
   @Nullable
   protected final Callable b;
   @NotNull
   private final CommentHandler[] i;
   @NotNull
   private final HeaderMode e;
   @NotNull
   private final ConfigurationOptions g;
   private static boolean h;

   protected awr(@NotNull AbstractConfigurationLoader$Builder var1, @NotNull CommentHandler[] var2) {
      this.f = var1.getSource();
      this.b = var1.getSink();
      this.e = var1.getHeaderMode();
      this.i = var2;
      this.g = var1.getDefaultOptions();
   }

   @NotNull
   public CommentHandler c() {
      return this.i[0];
   }

   @NotNull
   public ConfigurationNode load(@NotNull ConfigurationOptions param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected abstract void loadInternal(ConfigurationNode var1, BufferedReader var2) throws IOException;

   public void save(@NotNull ConfigurationNode param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected void a(Writer var1) throws IOException {
   }

   protected abstract void saveInternal(ConfigurationNode var1, Writer var2) throws IOException;

   @NotNull
   public ConfigurationOptions getDefaultOptions() {
      return this.g;
   }

   public final boolean c() {
      return this.f != null;
   }

   public final boolean a() {
      return this.b != null;
   }

   static {
      b(false);
   }

   public static void b(boolean var0) {
      h = var0;
   }

   public static boolean b() {
      return h;
   }

   public static boolean d() {
      boolean var0 = b();
      return true;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}

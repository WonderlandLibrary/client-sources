package net;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import net.aRk;
import net.agP;
import net.apz;
import net.awr;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class a6O {
   @NotNull
   protected apz b = apz.PRESERVE;
   @Nullable
   protected Callable c;
   @Nullable
   protected Callable d;
   @NotNull
   protected agP a = agP.e();

   @NotNull
   private a6O b() {
      return this;
   }

   @NotNull
   public a6O a(@NotNull File var1) {
      return this.a(((File)Objects.requireNonNull(var1, "file")).toPath());
   }

   @NotNull
   public a6O a(@NotNull Path var1) {
      Path var2 = ((Path)Objects.requireNonNull(var1, "path")).toAbsolutePath();
      this.c = a6O::lambda$setPath$0;
      this.d = aRk.a(var2, StandardCharsets.UTF_8);
      return this.b();
   }

   @NotNull
   public a6O a(@NotNull URL var1) {
      Objects.requireNonNull(var1, "url");
      this.c = a6O::lambda$setURL$1;
      return this.b();
   }

   @Nullable
   public Callable c() {
      return this.c;
   }

   @NotNull
   public a6O a(@Nullable Callable var1) {
      this.c = var1;
      return this.b();
   }

   @Nullable
   public Callable d() {
      return this.d;
   }

   @NotNull
   public a6O b(@Nullable Callable var1) {
      this.d = var1;
      return this.b();
   }

   @NotNull
   public apz a() {
      return this.b;
   }

   @NotNull
   public a6O a(@NotNull apz var1) {
      this.b = (apz)Objects.requireNonNull(var1, "mode");
      return this.b();
   }

   /** @deprecated */
   @Deprecated
   @NotNull
   public a6O a(boolean var1) {
      this.b = apz.PRESERVE;
      return this.b();
   }

   /** @deprecated */
   @Deprecated
   public boolean e() {
      return this.b == apz.PRESERVE;
   }

   @NotNull
   public agP f() {
      return this.a;
   }

   @NotNull
   public a6O a(@NotNull agP var1) {
      this.a = (agP)Objects.requireNonNull(var1, "defaultOptions");
      return this.b();
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

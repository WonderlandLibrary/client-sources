package net;

import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import java.util.Collection;
import java.util.List;
import net.Ql;
import net.aE3;
import net.aE8;
import net.aEB;
import net.aEE;
import net.aEF;
import net.aEl;
import net.aEm;
import net.aEs;
import net.aEu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class axu {
   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   public static aEF a(@NotNull ProtocolPathEntry var0) {
      Ql.a((Object)var0, "Key binding");
      return new aEF(var0);
   }

   @NotNull
   public static aE3 a(@NotNull List var0) {
      return aE3.a(var0);
   }

   @NotNull
   public static aE3 a(@NotNull Collection var0) {
      return aE3.a(var0);
   }

   @SafeVarargs
   @NotNull
   public static aE3 a(@NotNull Object... var0) {
      return aE3.b(var0);
   }

   @NotNull
   public static aE3 a(@Nullable Object var0) {
      return aE3.c(var0);
   }

   @NotNull
   public static aE3 a(@Nullable Object var0, @Nullable Object var1) {
      return aE3.a(var0, var1);
   }

   @NotNull
   public static aE3 a(@Nullable Object var0, @Nullable Object var1, @Nullable Object var2) {
      return aE3.a(var0, var1, var2);
   }

   @NotNull
   public static aEu a(@Nullable Boolean var0) {
      return aEu.a(var0);
   }

   @NotNull
   public static aEu b() {
      return aEu.a(Boolean.valueOf(true));
   }

   @NotNull
   public static aEu g() {
      return aEu.a(Boolean.valueOf(false));
   }

   @NotNull
   public static aEu a() {
      return aEu.f();
   }

   @NotNull
   public static aEu h() {
      return aEu.c();
   }

   @NotNull
   public static aEE a(@Nullable Double var0) {
      return aEE.f(var0);
   }

   @NotNull
   public static aEE e() {
      return aEE.a();
   }

   @NotNull
   public static aEl a(@Nullable Float var0) {
      return aEl.d(var0);
   }

   @NotNull
   public static aEl c() {
      return aEl.a();
   }

   @NotNull
   public static aE8 b(@Nullable Integer var0) {
      return aE8.e(var0);
   }

   @NotNull
   public static aE8 f() {
      return aE8.c();
   }

   @NotNull
   public static aEm a(@Nullable Long var0) {
      return aEm.d(var0);
   }

   @NotNull
   public static aEm d() {
      return aEm.a();
   }

   @NotNull
   public static aEs a(@Nullable String var0) {
      return aEs.d(var0);
   }

   @NotNull
   public static aEs i() {
      return aEs.b();
   }

   @NotNull
   public static aEB a(@Nullable Integer var0) {
      return aEB.b(var0);
   }
}

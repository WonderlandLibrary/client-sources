package net.skidunion;

import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.H;
import net.skidunion.d;
import net.skidunion.e;
import net.skidunion.o;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bJ\u001f\u0010\f\u001a\u00020\u00002\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\r\"\u00020\u000b¢\u0006\u0002\u0010\u000eJ\u0006\u0010\u000f\u001a\u00020\u0006J\u001e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0012H\u0002J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0002J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u0012J\u000e\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u0012R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/ag;", "", "serverURI", "Ljava/net/URI;", "(Ljava/net/URI;)V", "a", "Lnet/skidunion/o;", "a", "()Lnet/skidunion/o;", "a", "listener", "Lnet/skidunion/H;", "a", "", "([Lnet/skidunion/H;)Lnet/skidunion/ag;", "b", "a", "keyStoreResourcePath", "", "storePassword", "keyPassword", "a", "Ljava/io/InputStream;", "resourcePath", "b", "Ljavax/net/SocketFactory;", "a", "hashFunction", "Lnet/skidunion/d;", "b", "secret", "c", "token"}
)
public final class ag {
   @NotNull
   private final o a;

   @NotNull
   public final o a() {
      return this.a;
   }

   @NotNull
   public final ag a(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      Intrinsics.checkParameterIsNotNull(var1, "keyStoreResourcePath");
      Intrinsics.checkParameterIsNotNull(var2, "storePassword");
      Intrinsics.checkParameterIsNotNull(var3, "keyPassword");
      this.a.setSocketFactory(this.b(var1, var2, var3));
      return this;
   }

   @NotNull
   public final ag c(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "token");
      this.a.f(var1);
      return this;
   }

   @NotNull
   public final ag b(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "secret");
      this.a.e(var1);
      return this;
   }

   @NotNull
   public final ag a(@NotNull d var1) {
      Intrinsics.checkParameterIsNotNull(var1, "hashFunction");
      this.a.a(var1);
      return this;
   }

   @NotNull
   public final ag a(@NotNull H var1) {
      Intrinsics.checkParameterIsNotNull(var1, "listener");
      this.a.c().a(var1);
      return this;
   }

   @NotNull
   public final ag a(@NotNull H... var1) {
      Intrinsics.checkParameterIsNotNull(var1, "listener");
      boolean var4 = false;
      o.o();
      int var6 = var1.length;
      int var7 = 0;
      if(var7 < var6) {
         H var8 = var1[var7];
         boolean var10 = false;
         this.a.c().a(var8);
         ++var7;
      }

      return this;
   }

   @NotNull
   public final o b() {
      o var1 = this.a;
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      var1.connectBlocking();
      return var1;
   }

   private final SocketFactory b(String var1, String var2, String var3) {
      KeyStore var4 = KeyStore.getInstance("JKS");
      InputStream var17 = this.a(var1);
      boolean var6 = false;
      if(var2 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         char[] var10000 = var2.toCharArray();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
         char[] var18 = var10000;
         var4.load(var17, var18);
         KeyManagerFactory var19 = KeyManagerFactory.getInstance("SunX509");
         boolean var7 = false;
         boolean var8 = false;
         boolean var10 = false;
         boolean var14 = false;
         if(var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } else {
            var10000 = var3.toCharArray();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
            char[] var15 = var10000;
            var19.init(var4, var15);
            KeyManagerFactory var5 = var19;
            TrustManagerFactory var21 = TrustManagerFactory.getInstance("SunX509");
            var8 = false;
            boolean var9 = false;
            boolean var11 = false;
            var21.init(var4);
            TrustManagerFactory var20 = var21;
            SSLContext var22 = SSLContext.getInstance("TLS");
            var8 = false;
            var9 = false;
            var11 = false;
            Intrinsics.checkExpressionValueIsNotNull(var5, "kmf");
            KeyManager[] var10001 = var5.getKeyManagers();
            Intrinsics.checkExpressionValueIsNotNull(var20, "tmf");
            var22.init(var10001, var20.getTrustManagers(), (SecureRandom)null);
            Intrinsics.checkExpressionValueIsNotNull(var22, "SSLContext.getInstance(\"…mf.trustManagers, null) }");
            SSLSocketFactory var28 = var22.getSocketFactory();
            Intrinsics.checkExpressionValueIsNotNull(var28, "SSLContext.getInstance(\"…rs, null) }.socketFactory");
            return (SocketFactory)var28;
         }
      }
   }

   private final InputStream a(String var1) {
      InputStream var10000 = e.a.getClass().getResourceAsStream(var1);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "{}::class.java.getResourceAsStream(resourcePath)");
      return var10000;
   }

   public ag(@NotNull URI var1) {
      Intrinsics.checkParameterIsNotNull(var1, "serverURI");
      super();
      this.a = new o(var1);
   }

   private static TypeCastException a(TypeCastException var0) {
      return var0;
   }
}

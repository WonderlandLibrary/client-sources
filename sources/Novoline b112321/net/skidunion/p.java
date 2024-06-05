package net.skidunion;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u000e\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u0014\u0010\r\u001a\u00020\n*\u00020\u00042\u0006\u0010\t\u001a\u00020\bH\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lnet/skidunion/p;", "", "()V", "c", "Ljava/security/MessageDigest;", "kotlin.jvm.PlatformType", "a", "a", "", "input", "", "a", "b", "a", "client"}
)
public final class p {
   private static final MessageDigest a = MessageDigest.getInstance("SHA-256");
   private static final MessageDigest c = MessageDigest.getInstance("MD5");
   public static final p b;
   private static int[] d;

   @NotNull
   public final String a(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      MessageDigest var10002 = c;
      Intrinsics.checkExpressionValueIsNotNull(c, "md5");
      return this.a(this.a(var10002, var1));
   }

   @NotNull
   public final String b(@NotNull String var1) {
      b();
      Intrinsics.checkParameterIsNotNull(var1, "input");
      MessageDigest var10002 = a;
      Intrinsics.checkExpressionValueIsNotNull(a, "sha256");
      String var10000 = this.a(this.a(var10002, var1));
      if(acE.b() == null) {
         b(new int[5]);
      }

      return var10000;
   }

   private final byte[] a(@NotNull MessageDigest var1, String var2) {
      Charset var10001 = StandardCharsets.UTF_8;
      Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.UTF_8, "StandardCharsets.UTF_8");
      Charset var4 = var10001;
      boolean var5 = false;
      if(var2 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         byte[] var10000 = var2.getBytes(var4);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
         byte[] var7 = var10000;
         var10000 = var1.digest(var7);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "digest(input.toByteArray(StandardCharsets.UTF_8))");
         return var10000;
      }
   }

   private final String a(byte[] var1) {
      b();
      StringBuffer var3 = new StringBuffer();
      int var4 = 0;
      int var5 = var1.length;
      if(var4 < var5) {
         String var6 = Integer.toHexString(255 & var1[var4]);
         if(var6.length() == 1) {
            var3.append('0');
         }

         var3.append(var6);
         ++var4;
      }

      String var10000 = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "hexString.toString()");
      String var8 = var10000;
      var5 = 0;
      if(var8 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         var10000 = var8.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         return var10000;
      }
   }

   static {
      b((int[])null);
      p var7 = new p();
      b = var7;
   }

   public static void b(int[] var0) {
      d = var0;
   }

   public static int[] b() {
      return d;
   }

   private static TypeCastException a(TypeCastException var0) {
      return var0;
   }
}

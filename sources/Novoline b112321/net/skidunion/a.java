package net.skidunion;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import net.acE;
import net.skidunion.K;
import net.skidunion.aN;
import net.skidunion.c;
import net.skidunion.p;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\f\u0010\u0018\u001a\u00020\u0013*\u00020\u0019H\u0002R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\t0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lnet/skidunion/a;", "Lnet/skidunion/c;", "password", "", "(Ljava/lang/String;)V", "e", "Ljavax/crypto/Cipher;", "kotlin.jvm.PlatformType", "a", "Ljava/util/Base64$Encoder;", "d", "Lcom/google/gson/Gson;", "f", "Ljavax/crypto/Mac;", "b", "Ljavax/crypto/spec/SecretKeySpec;", "c", "Ljava/security/SecureRandom;", "c", "", "data", "b", "d", "a", "a", "Lcom/google/gson/JsonElement;", "client"}
)
public final class a implements c {
   private final SecureRandom c;
   private final Cipher e;
   private final Mac f;
   private final SecretKeySpec b;
   private final Gson d;
   private final Encoder a;

   @NotNull
   public byte[] d(@NotNull String var1) throws K {
      Intrinsics.checkParameterIsNotNull(var1, "data");
      String var2 = this.a(var1);
      Charset var3 = Charsets.UTF_8;
      boolean var4 = false;
      if(var2 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         byte[] var10000 = var2.getBytes(var3);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
         return var10000;
      }
   }

   @NotNull
   public byte[] c(@NotNull String var1) throws K {
      Intrinsics.checkParameterIsNotNull(var1, "data");
      aN.d();
      JsonElement var10001 = (new JsonParser()).parse(var1);
      Intrinsics.checkExpressionValueIsNotNull(var10001, "JsonParser().parse(data)");
      JsonObject var3 = var10001.getAsJsonObject();
      JsonElement var10002 = var3.get("ciphertext");
      Intrinsics.checkExpressionValueIsNotNull(var10002, "json.get(\"ciphertext\")");
      byte[] var4 = this.a(var10002);
      var10001 = var3.get("iv");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "json.get(\"iv\")");
      byte[] var5 = this.a(var10001);
      var10001 = var3.get("hmac");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "json.get(\"hmac\")");
      byte[] var6 = this.a(var10001);

      try {
         this.f.init((Key)this.b);
         if(Arrays.equals(var6, this.f.doFinal(var4))) {
            this.e.init(2, (Key)this.b, (AlgorithmParameterSpec)(new IvParameterSpec(var5)));
            byte[] var10000 = this.e.doFinal(var4);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "cipher.doFinal(ciphertext)");
            return var10000;
         } else {
            throw (Throwable)(new K("HMAC doesn\'t match"));
         }
      } catch (K var8) {
         throw (Throwable)var8;
      } catch (Exception var9) {
         throw (Throwable)(new K("Failed decrypting", (Throwable)var9));
      }
   }

   @NotNull
   public String a(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "data");
      JsonObject var3 = new JsonObject();
      Cipher var10000 = this.e;
      Intrinsics.checkExpressionValueIsNotNull(this.e, "cipher");
      byte[] var5 = new byte[var10000.getBlockSize()];
      boolean var6 = false;
      aN.d();
      boolean var7 = false;
      boolean var9 = false;
      this.c.nextBytes(var5);
      byte[] var4 = var5;

      try {
         this.e.init(1, (Key)this.b, (AlgorithmParameterSpec)(new IvParameterSpec(var4)));
         this.f.init((Key)this.b);
      } catch (Exception var12) {
         throw (Throwable)(new K("Failed instancing the ciphers", (Throwable)var12));
      }

      Cipher var10 = this.e;
      Charset var15 = Charsets.UTF_8;
      boolean var8 = false;
      byte[] var16 = var1.getBytes(var15);
      Intrinsics.checkExpressionValueIsNotNull(var16, "(this as java.lang.String).getBytes(charset)");
      byte[] var11 = var16;
      var5 = var10.doFinal(var11);
      byte[] var14 = this.f.doFinal(var5);
      var3.addProperty("iv", this.a.encodeToString(var4));
      var3.addProperty("hmac", this.a.encodeToString(var14));
      var3.addProperty("ciphertext", this.a.encodeToString(var5));
      String var17 = this.d.toJson((JsonElement)var3);
      Intrinsics.checkExpressionValueIsNotNull(var17, "gson.toJson(json)");
      return var17;
   }

   @NotNull
   public String b(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "data");
      byte[] var2 = this.c(var1);
      boolean var3 = false;
      return new String(var2, Charsets.UTF_8);
   }

   private final byte[] a(@NotNull JsonElement var1) {
      byte[] var10000 = Base64.getDecoder().decode(var1.getAsString());
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Base64.getDecoder().decode(this.asString)");
      return var10000;
   }

   public a(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "password");
      super();
      this.c = new SecureRandom();
      this.e = Cipher.getInstance("AES/CBC/PKCS5Padding");
      Mac var10001 = Mac.getInstance("HmacSHA256");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "Mac.getInstance(\"HmacSHA256\")");
      this.f = var10001;
      int var10000 = aN.a();
      String var3 = p.b.a(var1);
      byte var4 = 0;
      byte var5 = 16;
      int var2 = var10000;
      boolean var6 = false;
      if(var3 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var17 = var3.substring(var4, var5);
         Intrinsics.checkExpressionValueIsNotNull(var17, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         String var10 = var17;
         Charset var14 = Charsets.UTF_8;
         var5 = 0;
         if(var10 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } else {
            byte[] var18 = var10.getBytes(var14);
            Intrinsics.checkExpressionValueIsNotNull(var18, "(this as java.lang.String).getBytes(charset)");
            byte[] var16 = var18;
            String var11 = "AES";
            this.b = new SecretKeySpec(var16, var11);
            this.d = new Gson();
            this.a = Base64.getEncoder();
            if(acE.b() == null) {
               ++var2;
               aN.b(var2);
            }

         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}

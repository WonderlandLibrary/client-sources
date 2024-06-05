package net.skidunion;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.L;
import net.skidunion.o;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006 "},
   d2 = {"Lnet/skidunion/ao;", "", "e", "", "f", "Lnet/skidunion/L;", "(ILnet/skidunion/L;)V", "g", "Lcom/google/gson/Gson;", "f", "()Lcom/google/gson/Gson;", "d", "Lorg/apache/logging/log4j/Logger;", "a", "()Lorg/apache/logging/log4j/Logger;", "b", "()I", "a", "(I)V", "c", "", "d", "()Ljava/lang/Long;", "a", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "e", "()Lnet/skidunion/L;", "a", "(Lnet/skidunion/L;)V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public abstract class ao {
   @NotNull
   private final Logger d;
   @NotNull
   private final Gson g;
   @Nullable
   private Long c;
   private int e;
   @NotNull
   private L f;
   private static String b;

   @NotNull
   protected final Logger a() {
      return this.d;
   }

   @NotNull
   protected final Gson f() {
      return this.g;
   }

   @Nullable
   public final Long d() {
      return this.c;
   }

   public final void a(@Nullable Long var1) {
      this.c = var1;
   }

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "args");
   }

   public final int b() {
      return this.e;
   }

   public final void a(int var1) {
      this.e = var1;
   }

   @NotNull
   public final L e() {
      return this.f;
   }

   public final void a(@NotNull L var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.f = var1;
   }

   public ao(int var1, @NotNull L var2) {
      Intrinsics.checkParameterIsNotNull(var2, "type");
      super();
      this.e = var1;
      this.f = var2;
      Logger var10001 = LogManager.getLogger(this.getClass());
      Intrinsics.checkExpressionValueIsNotNull(var10001, "LogManager.getLogger(javaClass)");
      this.d = var10001;
      this.g = new Gson();
   }

   // $FF: synthetic method
   public ao(int var1, L var2, int var3, DefaultConstructorMarker var4) {
      if((var3 & 2) != 0) {
         var2 = L.SERVERBOUND;
      }

      this(var1, var2);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String c() {
      return b;
   }

   static {
      b("XYlnB");
   }
}

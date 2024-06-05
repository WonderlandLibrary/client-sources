package net.skidunion;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.L;
import net.skidunion.O;
import net.skidunion.Q;
import net.skidunion.W;
import net.skidunion.Z;
import net.skidunion.an;
import net.skidunion.ao;
import net.skidunion.aq;
import net.skidunion.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0016\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0002J\r\u0010%\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0014J(\u0010&\u001a\u00020\"2\u000e\u0010\'\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010(2\u000e\u0010)\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010(H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u0004\u0018\u00018\u0000X\u0080\u000e¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u0006\u0003"},
   d2 = {"Lnet/skidunion/ac;", "T", "Lnet/skidunion/W;", "e", "Lnet/skidunion/o;", "d", "Lnet/skidunion/an;", "i", "Lnet/skidunion/ao;", "(Lnet/skidunion/o;Lnet/skidunion/an;Lnet/skidunion/ao;)V", "a", "", "e", "()Ljava/lang/Throwable;", "a", "(Ljava/lang/Throwable;)V", "f", "Lnet/skidunion/Q;", "b", "d", "()Ljava/lang/Object;", "a", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "g", "", "Ljava/lang/Long;", "h", "Ljava/util/concurrent/CountDownLatch;", "a", "()Ljava/util/concurrent/CountDownLatch;", "a", "(Ljava/util/concurrent/CountDownLatch;)V", "a", "", "request", "Lnet/skidunion/Z;", "c", "a", "successCallback", "Ljava/util/function/Consumer;", "failureCallback"}
)
public class ac implements W {
   private final Q f;
   private Long g;
   @NotNull
   public CountDownLatch h;
   @NotNull
   public Throwable a;
   @Nullable
   private Object b;
   private final o e;
   private an d;
   private ao i;
   private static String c;

   @NotNull
   public final CountDownLatch a() {
      CountDownLatch var10000 = this.h;
      if(this.h == null) {
         Intrinsics.throwUninitializedPropertyAccessException("waitForPacketLatch");
      }

      return var10000;
   }

   public final void a(@NotNull CountDownLatch var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.h = var1;
   }

   @NotNull
   public final Throwable e() {
      Throwable var10000 = this.a;
      if(this.a == null) {
         Intrinsics.throwUninitializedPropertyAccessException("error");
      }

      return var10000;
   }

   public final void a(@NotNull Throwable var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.a = var1;
   }

   @Nullable
   public final Object d() {
      return this.b;
   }

   public final void a(@Nullable Object var1) {
      this.b = var1;
   }

   @Nullable
   public final Object c() throws O {
      String var1 = b();
      if(!this.e.j() && this.d.e() != L.LOGIN) {
         throw (Throwable)this.f;
      } else {
         Z var2 = new Z(this.d, this.i, this);
         this.a(var2);
         this.e.a(this.d);
         this.h = new CountDownLatch(1);
         CountDownLatch var10000 = this.h;
         if(this.h == null) {
            Intrinsics.throwUninitializedPropertyAccessException("waitForPacketLatch");
         }

         var10000.await(5L, TimeUnit.SECONDS);
         if(((ac)this).a != null) {
            Throwable var3 = this.a;
            if(this.a == null) {
               Intrinsics.throwUninitializedPropertyAccessException("error");
            }

            throw var3;
         } else {
            return this.b;
         }
      }
   }

   public void a(@Nullable Consumer var1, @Nullable Consumer var2) {
      String var3 = b();
      if(this.e.j() || this.d.e() == L.LOGIN) {
         Z var4 = new Z(var1, var2, this.d, this.i);
         this.a(var4);
         this.e.a(this.d);
      }

      throw (Throwable)this.f;
   }

   private final void a(Z var1) {
      this.e.d().add(var1);
   }

   public ac(@NotNull o var1, @NotNull an var2, @NotNull ao var3) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      b();
      Intrinsics.checkParameterIsNotNull(var2, "sending");
      Intrinsics.checkParameterIsNotNull(var3, "receiving");
      super();
      this.e = var1;
      this.d = var2;
      this.i = var3;
      this.f = new Q();
      this.g = Long.valueOf(System.nanoTime());
      this.d.a(this.g);
      if(acE.b() == null) {
         b("XAhckc");
      }

   }

   public void a() {
      aq.a(this);
   }

   public void a(@Nullable Consumer var1) {
      aq.a(this, var1);
   }

   public static void b(String var0) {
      c = var0;
   }

   public static String b() {
      return c;
   }

   private static Throwable b(Throwable var0) {
      return var0;
   }

   static {
      b("dVmNIc");
   }
}

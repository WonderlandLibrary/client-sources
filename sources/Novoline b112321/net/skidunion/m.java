package net.skidunion;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.G;
import net.skidunion.T;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR \u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001b"},
   d2 = {"Lnet/skidunion/m;", "", "c", "", "a", "Lnet/skidunion/G;", "b", "Lnet/skidunion/T;", "(Ljava/lang/String;Lnet/skidunion/G;Lnet/skidunion/T;)V", "g", "()Lnet/skidunion/G;", "d", "()Lnet/skidunion/T;", "a", "(Lnet/skidunion/T;)V", "c", "()Ljava/lang/String;", "e", "b", "a", "a", "equals", "", "other", "hashCode", "", "toString", "client"}
)
public final class m {
   @SerializedName("username")
   @NotNull
   private final String c;
   @SerializedName("rank")
   @NotNull
   private final G a;
   @SerializedName("serverData")
   @Nullable
   private T b;
   private static String d;

   @NotNull
   public final String c() {
      return this.c;
   }

   @NotNull
   public final G g() {
      return this.a;
   }

   @Nullable
   public final T d() {
      return this.b;
   }

   public final void a(@Nullable T var1) {
      this.b = var1;
   }

   public m(@NotNull String var1, @NotNull G var2, @Nullable T var3) {
      Intrinsics.checkParameterIsNotNull(var1, "username");
      Intrinsics.checkParameterIsNotNull(var2, "rankEntity");
      super();
      this.c = var1;
      this.a = var2;
      this.b = var3;
   }

   @NotNull
   public final String e() {
      return this.c;
   }

   @NotNull
   public final G b() {
      return this.a;
   }

   @Nullable
   public final T a() {
      return this.b;
   }

   @NotNull
   public final m a(@NotNull String var1, @NotNull G var2, @Nullable T var3) {
      Intrinsics.checkParameterIsNotNull(var1, "username");
      Intrinsics.checkParameterIsNotNull(var2, "rankEntity");
      return new m(var1, var2, var3);
   }

   // $FF: synthetic method
   public static m a(m var0, String var1, G var2, T var3, int var4, Object var5) {
      String var6 = f();
      if((var4 & 1) != 0) {
         var1 = var0.c;
      }

      if((var4 & 2) != 0) {
         var2 = var0.a;
      }

      if((var4 & 4) != 0) {
         var3 = var0.b;
      }

      return var0.a(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "UserEntity(username=" + this.c + ", rankEntity=" + this.a + ", serverEntity=" + this.b + ")";
   }

   public int hashCode() {
      String var1 = f();
      return ((this.c != null?this.c.hashCode():0) * 31 + (this.a != null?this.a.hashCode():0)) * 31 + (this.b != null?this.b.hashCode():0);
   }

   public boolean equals(@Nullable Object var1) {
      String var2 = f();
      if(this != var1) {
         if(var1 instanceof m) {
            m var3 = (m)var1;
            if(Intrinsics.areEqual(this.c, var3.c) && Intrinsics.areEqual(this.a, var3.a) && Intrinsics.areEqual(this.b, var3.b)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static void b(String var0) {
      d = var0;
   }

   public static String f() {
      return d;
   }

   static {
      b("XK38ec");
   }
}

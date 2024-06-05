package net.skidunion;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.aP;
import net.skidunion.m;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J\t\u0010\u0017\u001a\u00020\bHÆ\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006\u001f"},
   d2 = {"Lnet/skidunion/b;", "", "b", "", "a", "c", "Lnet/skidunion/m;", "d", "Lnet/skidunion/aP;", "e", "(Ljava/lang/String;Ljava/lang/String;Lnet/skidunion/m;Lnet/skidunion/aP;Lnet/skidunion/aP;)V", "a", "()Lnet/skidunion/aP;", "c", "()Ljava/lang/String;", "e", "j", "()Lnet/skidunion/m;", "b", "h", "g", "f", "d", "i", "a", "equals", "", "other", "hashCode", "", "toString", "client"}
)
public final class b {
   @SerializedName("name")
   @NotNull
   private final String b;
   @SerializedName("data")
   @NotNull
   private final String a;
   @SerializedName("owner")
   @NotNull
   private final m c;
   @SerializedName("creationDate")
   @NotNull
   private final aP d;
   @SerializedName("updateDate")
   @NotNull
   private final aP e;

   @NotNull
   public final String e() {
      return this.b;
   }

   @NotNull
   public final String c() {
      return this.a;
   }

   @NotNull
   public final m j() {
      return this.c;
   }

   @NotNull
   public final aP a() {
      return this.d;
   }

   @NotNull
   public final aP b() {
      return this.e;
   }

   public b(@NotNull String var1, @NotNull String var2, @NotNull m var3, @NotNull aP var4, @NotNull aP var5) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      m.f();
      Intrinsics.checkParameterIsNotNull(var2, "data");
      Intrinsics.checkParameterIsNotNull(var3, "owner");
      Intrinsics.checkParameterIsNotNull(var4, "creationDate");
      Intrinsics.checkParameterIsNotNull(var5, "updateDate");
      super();
      this.b = var1;
      this.a = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      if(acE.b() == null) {
         m.b("j9F1ic");
      }

   }

   @NotNull
   public final String h() {
      return this.b;
   }

   @NotNull
   public final String g() {
      return this.a;
   }

   @NotNull
   public final m f() {
      return this.c;
   }

   @NotNull
   public final aP d() {
      return this.d;
   }

   @NotNull
   public final aP i() {
      return this.e;
   }

   @NotNull
   public final b a(@NotNull String var1, @NotNull String var2, @NotNull m var3, @NotNull aP var4, @NotNull aP var5) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "data");
      Intrinsics.checkParameterIsNotNull(var3, "owner");
      Intrinsics.checkParameterIsNotNull(var4, "creationDate");
      Intrinsics.checkParameterIsNotNull(var5, "updateDate");
      return new b(var1, var2, var3, var4, var5);
   }

   // $FF: synthetic method
   public static b a(b var0, String var1, String var2, m var3, aP var4, aP var5, int var6, Object var7) {
      String var8 = m.f();
      if((var6 & 1) != 0) {
         var1 = var0.b;
      }

      if((var6 & 2) != 0) {
         var2 = var0.a;
      }

      if((var6 & 4) != 0) {
         var3 = var0.c;
      }

      if((var6 & 8) != 0) {
         var4 = var0.d;
      }

      if((var6 & 16) != 0) {
         var5 = var0.e;
      }

      return var0.a(var1, var2, var3, var4, var5);
   }

   @NotNull
   public String toString() {
      return "ConfigEntity(name=" + this.b + ", data=" + this.a + ", owner=" + this.c + ", creationDate=" + this.d + ", updateDate=" + this.e + ")";
   }

   public int hashCode() {
      String var1 = m.f();
      return ((((this.b != null?this.b.hashCode():0) * 31 + (this.a != null?this.a.hashCode():0)) * 31 + (this.c != null?this.c.hashCode():0)) * 31 + (this.d != null?this.d.hashCode():0)) * 31 + (this.e != null?this.e.hashCode():0);
   }

   public boolean equals(@Nullable Object var1) {
      String var2 = m.f();
      if(this != var1) {
         if(var1 instanceof b) {
            b var3 = (b)var1;
            if(Intrinsics.areEqual(this.b, var3.b) && Intrinsics.areEqual(this.a, var3.a) && Intrinsics.areEqual(this.c, var3.c) && Intrinsics.areEqual(this.d, var3.d) && Intrinsics.areEqual(this.e, var3.e)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}

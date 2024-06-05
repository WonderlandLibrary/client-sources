package net;

import java.util.Objects;
import java.util.function.Supplier;
import net.IG;
import net.aiy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Y9 implements aiy {
   protected Supplier b;
   Object a;

   protected Y9(@NotNull Supplier var1) {
      this.b = var1;
   }

   public Object get() {
      String[] var1 = IG.c();
      if(this.b != null) {
         this.a = this.b.get();
         this.b = null;
      }

      return this.a;
   }

   public boolean a() {
      return this.b == null;
   }

   @Nullable
   public Supplier b() {
      return this.b;
   }

   public void a(@Nullable Supplier var1) {
      this.b = var1;
   }

   public Object a() {
      return this.a;
   }

   public void a(Object var1) {
      this.a = var1;
   }

   public boolean equals(Object var1) {
      String[] var2 = IG.c();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof Y9)) {
         return false;
      } else {
         Y9 var3 = (Y9)var1;
         return Objects.equals(this.b, var3.b) && Objects.equals(this.a, var3.a);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.b, this.a});
   }

   public String toString() {
      return "SimpleLazy{valueSupplier=" + this.b + ", value=" + this.a + '}';
   }
}

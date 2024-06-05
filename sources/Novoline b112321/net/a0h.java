package net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.a_E;

class a0h {
   @Expose
   @SerializedName("name")
   private final String b;
   @Expose
   @SerializedName("changedToAt")
   private final long a;

   public a0h(String var1, long var2) {
      this.b = var1;
      this.a = var2;
   }

   public boolean b() {
      int[] var1 = a_E.b();
      return this.a == 0L;
   }

   public String a() {
      return this.b;
   }

   public long c() {
      return this.a;
   }
}

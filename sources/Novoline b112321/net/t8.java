package net;

import com.google.common.reflect.TypeToken;
import net.as0;
import org.jetbrains.annotations.NotNull;

public abstract class t8 implements Cloneable {
   protected final String d;
   protected as0 a;
   protected TypeToken c;
   private static String[] b;

   public t8(@NotNull String var1, @NotNull as0 var2) {
      this.d = var1;
      this.a = var2;
   }

   @NotNull
   public String a() {
      return this.d;
   }

   @NotNull
   public as0 b() {
      return this.a;
   }

   @NotNull
   public TypeToken c() {
      String[] var1 = d();
      return this.c != null?this.c:(this.c = TypeToken.of(this.a.getClass()));
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public String toString() {
      return "ModuleHolder{name=\'" + this.d + '\'' + ", module=" + this.a + '}';
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] d() {
      return b;
   }

   static {
      b(new String[1]);
   }
}

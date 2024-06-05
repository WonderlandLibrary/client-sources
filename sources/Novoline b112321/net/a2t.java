package net;

public interface a2t {
   float a(CharSequence var1, double var2, double var4, int var6, boolean var7);

   String a(CharSequence var1, int var2, boolean var3);

   int a(CharSequence var1);

   float a(char var1);

   String b();

   int c();

   boolean d();

   boolean a();

   default float b(CharSequence var1, float var2, float var3, int var4) {
      return this.a(var1, (double)var2, (double)var3, var4, false);
   }

   default String a(CharSequence var1, int var2) {
      return this.a(var1, var2, false);
   }

   default float a(CharSequence var1, float var2, float var3, int var4, boolean var5) {
      return this.a(var1, (double)(var2 - (float)this.a(var1) / 2.0F), (double)var3, var4, var5);
   }

   default float a(CharSequence var1, float var2, float var3, int var4) {
      return this.a(var1, var2, var3, var4, false);
   }
}

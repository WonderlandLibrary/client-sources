package net;

import net.auu;
import net.dr;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Nullable;

public class hp {
   private final int c;
   private final short d;
   private final String b;
   private final dr e;
   private auu a;

   public hp(int var1, short var2, @Nullable String var3, boolean var4) {
      this.c = var1;
      this.d = var2;
      this.b = ChatColor.RESET + var3;
      this.e = new dr(var1, var2);
   }

   public int d() {
      return this.c;
   }

   public short f() {
      return this.d;
   }

   public String c() {
      return this.b;
   }

   public boolean a() {
      return this.e != null;
   }

   public dr g() {
      return this.e;
   }

   public boolean e() {
      return this.a != null;
   }

   @Nullable
   public auu b() {
      return this.a;
   }

   public void a(@Nullable auu var1) {
      this.a = var1;
   }
}

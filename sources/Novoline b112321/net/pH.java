package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.nio.file.Path;
import net.Ql;
import net.V9;
import net._5;
import net.aBk;
import net.aHo;
import net.gZ;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;

public class pH {
   private final String c;
   private boolean a;
   private int d = 0;
   private int b = 30;

   private pH(@NotNull String var1) {
      this.c = var1;
   }

   @NotNull
   public static pH a(@NotNull Path var0) {
      Ql.a((Object)var0, "path");
      Ql.a(var0.toString().endsWith(aBk.g()), "not config path:");
      String var1 = var0.getFileName().toString();
      String var2 = var1.substring(0, var1.length() - aBk.g().length());
      return new pH(var2);
   }

   public void c() {
      aHo var1 = gZ.g().s();
      this.d = var1.h() + this.b + var1.d().indexOf(this) * 20;
   }

   public void b(int var1, int var2) {
      V9.b();
      aHo var4 = gZ.g().s();
      int var5 = var4.f();
      int var6 = var4.m();
      if(this.a(var1, var2)) {
         Gui.drawRect(var5 + 45 + 110, this.d - 6, var5 + 45 + 105 + var6, this.d + _5.a.c() + 5, -13684426);
      }

      _5.a.a(this.c.replace(".txt", ""), (float)(var5 + 45 + 110) + (float)var6 / 2.0F, (float)this.d, this.a?-1:-7961722);
      if(PacketRemapper.b() == null) {
         V9.b(new PacketRemapper[4]);
      }

   }

   public boolean a(int var1, int var2) {
      V9.b();
      aHo var4 = gZ.g().s();
      int var5 = var4.f();
      int var6 = var4.m();
      return var1 >= var5 + 45 + 110 && var1 <= var5 + 45 + 110 + var6 && var2 >= this.d - 6 && var2 <= this.d + _5.a.c() + 5;
   }

   public boolean d() {
      aHo var2 = gZ.g().s();
      V9.b();
      int var3 = var2.h();
      int var4 = var3 + var2.a();
      return this.d < var3 + 30 || this.d > var4 - 9;
   }

   public boolean b() {
      return this.a;
   }

   public void a(boolean var1) {
      this.a = var1;
   }

   public int e() {
      return this.b;
   }

   public void a(int var1) {
      this.b = var1;
   }

   public String a() {
      return this.c;
   }

   public int f() {
      return this.d;
   }
}

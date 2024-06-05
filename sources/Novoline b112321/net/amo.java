package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Set;

public class amo {
   private final String b;
   private final String j;
   private final int g;
   private final Set e;
   private final String f;
   private final String c;
   private final String h;
   private final String d;
   private final Set a;
   private static PacketRemapper[] i;

   public amo(String var1, String var2, int var3, Set var4, String var5, String var6, String var7, String var8, Set var9) {
      this.b = var1;
      b();
      this.j = var2;
      this.g = var3;
      this.e = var4;
      this.f = var5;
      this.c = var6;
      this.h = var7;
      this.d = var8;
      this.a = var9;
   }

   public String c() {
      return this.b;
   }

   public String j() {
      return this.j;
   }

   public int g() {
      return this.g;
   }

   public Set a() {
      return this.e;
   }

   public String f() {
      return this.f;
   }

   public String e() {
      return this.c;
   }

   public String d() {
      return this.h;
   }

   public String i() {
      return this.d;
   }

   public Set h() {
      return this.a;
   }

   public static void b(PacketRemapper[] var0) {
      i = var0;
   }

   public static PacketRemapper[] b() {
      return i;
   }

   static {
      if(b() == null) {
         b(new PacketRemapper[5]);
      }

   }
}

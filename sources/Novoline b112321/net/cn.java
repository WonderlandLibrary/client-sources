package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import io.netty.buffer.ByteBuf;
import net.VV;
import net.aRY;
import net.ay_;
import net.bgR;
import net.cA;
import net.md_5.bungee.api.ChatColor;

public class cn extends cA {
   private int d;
   private String e;
   private String c;
   private static int f;

   public cn(bgR var1) {
      super(var1);
   }

   public void e() {
      PacketWrapperImpl var1 = new PacketWrapperImpl(15, (ByteBuf)null, this.d());
      var1.a(Type.p, aRY.b(this.f()));
      var1.a(Type.k, Byte.valueOf((byte)2));
      PacketWrapperImpl var10000 = var1;
      Class var10001 = ay_.class;

      try {
         var10000.a(var10001);
      } catch (Exception var3) {
         VV.d().a().severe("Failed to send the shoulder indication");
         var3.printStackTrace();
      }

   }

   private String f() {
      d();
      StringBuilder var2 = new StringBuilder();
      var2.append("  ");
      if(this.e == null) {
         var2.append(ChatColor.RED).append(ChatColor.BOLD).append("Nothing");
      }

      var2.append(ChatColor.DARK_GREEN).append(ChatColor.BOLD).append(this.b(this.e));
      var2.append(ChatColor.DARK_GRAY).append(ChatColor.BOLD).append(" <- ").append(ChatColor.GRAY).append(ChatColor.BOLD).append("Shoulders").append(ChatColor.DARK_GRAY).append(ChatColor.BOLD).append(" -> ");
      if(this.c == null) {
         var2.append(ChatColor.RED).append(ChatColor.BOLD).append("Nothing");
      }

      var2.append(ChatColor.DARK_GREEN).append(ChatColor.BOLD).append(this.b(this.c));
      return var2.toString();
   }

   private String b(String var1) {
      int var2 = d();
      if(var1.startsWith("minecraft:")) {
         var1 = var1.substring(10);
      }

      String[] var3 = var1.split("_");
      StringBuilder var4 = new StringBuilder();
      int var6 = var3.length;
      int var7 = 0;
      if(var7 < var6) {
         String var8 = var3[var7];
         var4.append(var8.substring(0, 1).toUpperCase()).append(var8.substring(1)).append(" ");
         ++var7;
      }

      return var4.toString();
   }

   public int b() {
      return this.d;
   }

   public void c(int var1) {
      this.d = var1;
   }

   public String g() {
      return this.e;
   }

   public void c(String var1) {
      this.e = var1;
   }

   public String h() {
      return this.c;
   }

   public void a(String var1) {
      this.c = var1;
   }

   public String toString() {
      int var1 = d();
      return "ShoulderTracker{entityId=" + this.d + ", leftShoulder=\'" + this.e + '\'' + ", rightShoulder=\'" + this.c + '\'' + '}';
   }

   public static void b(int var0) {
      f = var0;
   }

   public static int d() {
      return f;
   }

   public static int a() {
      int var0 = d();
      return 65;
   }

   static {
      b(0);
   }
}

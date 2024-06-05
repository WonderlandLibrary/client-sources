package net;

import java.util.Date;
import net.Ql;
import net.bg8;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class afq {
   private static final afq b = a("Default", 1, (String)null, (Integer)null, (Integer)null, (Integer)null, (Long)null, (Long)null, Long.valueOf(System.currentTimeMillis()));
   private final String d;
   private final int f;
   private final String j;
   private final int e;
   private final int i;
   private final int l;
   private final Date g;
   private final Date k;
   private final Date c;
   private bg8 a;
   private static String h;

   public afq(@Nullable String var1, int var2, @Nullable String var3, int var4, int var5, int var6, @Nullable Date var7, @Nullable Date var8, @Nullable Date var9) {
      this.d = var1;
      this.f = var2;
      this.j = var3;
      this.e = var4;
      this.i = var5;
      this.l = var6;
      this.g = var7;
      this.k = var8;
      this.c = var9;
   }

   @NotNull
   static afq a(@Nullable String var0, int var1, @Nullable String var2, @Nullable Integer var3, @Nullable Integer var4, @Nullable Integer var5, @Nullable Long var6, @Nullable Long var7, @Nullable Long var8) {
      Ql.b(var3.intValue(), "achievement points");
      Ql.b(var4.intValue(), "quests");
      Ql.b(var5.intValue(), "friends");
      Ql.a(var6.longValue(), "first login timestamp");
      Ql.a(var7.longValue(), "last login timestamp");
      Ql.a(var8.longValue(), "cached timestamp");
      var0 = a(var0.trim());
      var1 = Math.max(1, var1);
      var2 = var2.trim();
      var3 = Integer.valueOf(0);
      var4 = Integer.valueOf(0);
      var5 = Integer.valueOf(0);
      Date var9 = new Date(var6.longValue());
      Date var10 = new Date(var7.longValue());
      Date var11 = new Date(var8.longValue());
      return new afq(var0, var1, var2, var3.intValue(), var4.intValue(), var5.intValue(), var9, var10, var11);
   }

   public static afq b() {
      return b;
   }

   public void a(@NotNull String var1, long var2) {
      String var4 = i();
      if(this.a != null) {
         throw new IllegalStateException("alt is already marked as banned!");
      } else {
         this.a = bg8.a(var1, var2);
      }
   }

   public boolean a() {
      String var1 = i();
      return StringUtils.isEmpty(this.d) || this.d.equalsIgnoreCase("default");
   }

   public int d() {
      return this.f;
   }

   @Nullable
   public String e() {
      return this.d;
   }

   public int f() {
      return this.l;
   }

   public int j() {
      return this.e;
   }

   @Nullable
   public Date h() {
      return this.g;
   }

   @Nullable
   public Date c() {
      return this.k;
   }

   @NotNull
   private static String a(@NotNull String var0) {
      Ql.a(var0, "rank");
      String var1 = var0.toLowerCase();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -2004703995:
         if(var1.equals("moderator")) {
            var2 = 9;
         }
         break;
      case -1220931666:
         if(var1.equals("helper")) {
            var2 = 8;
         }
         break;
      case -991745245:
         if(var1.equals("youtube")) {
            var2 = 7;
         }
         break;
      case 96426:
         if(var1.equals("adm")) {
            var2 = 12;
         }
         break;
      case 108290:
         if(var1.equals("mod")) {
            var2 = 10;
         }
         break;
      case 108519:
         if(var1.equals("mvp")) {
            var2 = 4;
         }
         break;
      case 116765:
         if(var1.equals("vip")) {
            var2 = 1;
         }
         break;
      case 3364132:
         if(var1.equals("mvp+")) {
            var2 = 5;
         }
         break;
      case 3619758:
         if(var1.equals("vip+")) {
            var2 = 2;
         }
         break;
      case 92668751:
         if(var1.equals("admin")) {
            var2 = 11;
         }
         break;
      case 230944667:
         if(var1.equals("builder")) {
            var2 = 13;
         }
         break;
      case 1489438844:
         if(var1.equals("vip_plus")) {
            var2 = 3;
         }
         break;
      case 1544803905:
         if(var1.equals("default")) {
            var2 = 0;
         }
         break;
      case 1636660978:
         if(var1.equals("mvp_plus")) {
            var2 = 6;
         }
      }

      switch(var2) {
      case 0:
         return "Default";
      case 1:
         return "VIP";
      case 2:
      case 3:
         return "VIP+";
      case 4:
         return "MVP";
      case 5:
      case 6:
         return "MVP+";
      case 7:
         return "YouTube";
      case 8:
         return "Helper";
      case 9:
      case 10:
         return "Moderator";
      case 11:
      case 12:
         return "Admin";
      case 13:
         return "Builder";
      default:
         return var0;
      }
   }

   @NotNull
   public NBTBase g() {
      i();
      NBTTagCompound var2 = new NBTTagCompound();
      if(this.d != null && !this.d.equalsIgnoreCase("default")) {
         var2.setString("rank", this.d);
      }

      var2.setInteger("level", this.f);
      if(this.j != null) {
         var2.setString("guild", this.j);
      }

      if(this.e > 0) {
         var2.setInteger("achievement_points", this.e);
      }

      if(this.i > 0) {
         var2.setInteger("quests", this.i);
      }

      if(this.l > 0) {
         var2.setInteger("friends", this.l);
      }

      if(this.g != null) {
         var2.setLong("first_login", this.g.getTime());
      }

      if(this.k != null) {
         var2.setLong("last_login", this.k.getTime());
      }

      if(this.c != null) {
         var2.setLong("cached", this.c.getTime());
      }

      return var2;
   }

   static {
      b("oUM8cb");
   }

   public static void b(String var0) {
      h = var0;
   }

   public static String i() {
      return h;
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}

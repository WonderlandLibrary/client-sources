package cc.novoline.gui.screen.alt.repository.hypixel;

import cc.novoline.gui.screen.alt.repository.hypixel.HypixelBan;
import cc.novoline.utils.java.Checks;
import java.util.Date;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HypixelProfile {
   private static final HypixelProfile EMPTY = of("Default", 1, (String)null, (Integer)null, (Integer)null, (Integer)null, (Long)null, (Long)null, Long.valueOf(System.currentTimeMillis()));
   private final String rank;
   private final int level;
   private final String guild;
   private final int achievementPoints;
   private final int quests;
   private final int friends;
   private final Date firstLoginDate;
   private final Date lastLoginDate;
   private final Date cachedDate;
   private HypixelBan ban;
   private static String h;

   public HypixelProfile(@Nullable String var1, int var2, @Nullable String var3, int var4, int var5, int var6, @Nullable Date var7, @Nullable Date var8, @Nullable Date var9) {
      this.rank = var1;
      this.level = var2;
      this.guild = var3;
      this.achievementPoints = var4;
      this.quests = var5;
      this.friends = var6;
      this.firstLoginDate = var7;
      this.lastLoginDate = var8;
      this.cachedDate = var9;
   }

   @NotNull
   static HypixelProfile of(@Nullable String var0, int var1, @Nullable String var2, @Nullable Integer var3, @Nullable Integer var4, @Nullable Integer var5, @Nullable Long var6, @Nullable Long var7, @Nullable Long var8) {
      Checks.notNegative(var3.intValue(), "achievement points");
      Checks.notNegative(var4.intValue(), "quests");
      Checks.notNegative(var5.intValue(), "friends");
      Checks.notNegative(var6.longValue(), "first login timestamp");
      Checks.notNegative(var7.longValue(), "last login timestamp");
      Checks.notNegative(var8.longValue(), "cached timestamp");
      var0 = mapRank(var0.trim());
      var1 = Math.max(1, var1);
      var2 = var2.trim();
      var3 = Integer.valueOf(0);
      var4 = Integer.valueOf(0);
      var5 = Integer.valueOf(0);
      Date var9 = new Date(var6.longValue());
      Date var10 = new Date(var7.longValue());
      Date var11 = new Date(var8.longValue());
      return new HypixelProfile(var0, var1, var2, var3.intValue(), var4.intValue(), var5.intValue(), var9, var10, var11);
   }

   public static HypixelProfile empty() {
      return EMPTY;
   }

   public void ban(@NotNull String var1, long var2) {
      String var4 = i();
      if(this.ban != null) {
         throw new IllegalStateException("alt is already marked as banned!");
      } else {
         this.ban = HypixelBan.of(var1, var2);
      }
   }

   public boolean isDefaultRank() {
      String var1 = i();
      return StringUtils.isEmpty(this.rank) || this.rank.equalsIgnoreCase("default");
   }

   public int getLevel() {
      return this.level;
   }

   @Nullable
   public String getRank() {
      return this.rank;
   }

   public int getFriends() {
      return this.friends;
   }

   public int getAchievementPoints() {
      return this.achievementPoints;
   }

   @Nullable
   public Date getFirstLoginDate() {
      return this.firstLoginDate;
   }

   @Nullable
   public Date getLastLoginDate() {
      return this.lastLoginDate;
   }

   @NotNull
   private static String mapRank(@NotNull String var0) {
      Checks.notBlank(var0, "rank");
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
   public NBTBase asNBTCompound() {
      i();
      NBTTagCompound var2 = new NBTTagCompound();
      if(this.rank != null && !this.rank.equalsIgnoreCase("default")) {
         var2.setString("rank", this.rank);
      }

      var2.setInteger("level", this.level);
      if(this.guild != null) {
         var2.setString("guild", this.guild);
      }

      if(this.achievementPoints > 0) {
         var2.setInteger("achievement_points", this.achievementPoints);
      }

      if(this.quests > 0) {
         var2.setInteger("quests", this.quests);
      }

      if(this.friends > 0) {
         var2.setInteger("friends", this.friends);
      }

      if(this.firstLoginDate != null) {
         var2.setLong("first_login", this.firstLoginDate.getTime());
      }

      if(this.lastLoginDate != null) {
         var2.setLong("last_login", this.lastLoginDate.getTime());
      }

      if(this.cachedDate != null) {
         var2.setLong("cached", this.cachedDate.getTime());
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

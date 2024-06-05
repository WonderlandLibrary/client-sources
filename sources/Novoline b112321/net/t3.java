package net;

import java.util.Collection;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.Team$EnumVisible;

public class t3 {
   public static boolean a(Team var0, Team var1) {
      return var0.isSameTeam(var1);
   }

   public static boolean a(Team var0) {
      return var0.getAllowFriendlyFire();
   }

   public static boolean e(Team var0) {
      return var0.getSeeFriendlyInvisiblesEnabled();
   }

   public static String b(Team var0) {
      return var0.getRegisteredName();
   }

   public static Team$EnumVisible f(Team var0) {
      return var0.getNameTagVisibility();
   }

   public static Collection c(Team var0) {
      return var0.getMembershipCollection();
   }

   public static Team$EnumVisible d(Team var0) {
      return var0.getDeathMessageVisibility();
   }
}

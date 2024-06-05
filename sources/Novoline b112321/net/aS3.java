package net;

import java.util.Collection;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.Team$EnumVisible;
import net.minecraft.util.EnumChatFormatting;

public class aS3 {
   public static void b(ScorePlayerTeam var0, String var1) {
      var0.c(var1);
   }

   public static void a(ScorePlayerTeam var0, EnumChatFormatting var1) {
      var0.setChatFormat(var1);
   }

   public static void c(ScorePlayerTeam var0, String var1) {
      var0.a(var1);
   }

   public static void a(ScorePlayerTeam var0, String var1) {
      var0.setNameSuffix(var1);
   }

   public static void b(ScorePlayerTeam var0, boolean var1) {
      var0.setAllowFriendlyFire(var1);
   }

   public static void a(ScorePlayerTeam var0, boolean var1) {
      var0.setSeeFriendlyInvisiblesEnabled(var1);
   }

   public static void a(ScorePlayerTeam var0, Team$EnumVisible var1) {
      var0.setNameTagVisibility(var1);
   }

   public static void b(ScorePlayerTeam var0, Team$EnumVisible var1) {
      var0.setDeathMessageVisibility(var1);
   }

   public static String j(ScorePlayerTeam var0) {
      return var0.getRegisteredName();
   }

   public static String d(ScorePlayerTeam var0) {
      return var0.getTeamName();
   }

   public static EnumChatFormatting f(ScorePlayerTeam var0) {
      return var0.getChatFormat();
   }

   public static String h(ScorePlayerTeam var0) {
      return var0.getColorPrefix();
   }

   public static String a(ScorePlayerTeam var0) {
      return var0.getColorSuffix();
   }

   public static boolean g(ScorePlayerTeam var0) {
      return var0.getAllowFriendlyFire();
   }

   public static boolean c(ScorePlayerTeam var0) {
      return var0.getSeeFriendlyInvisiblesEnabled();
   }

   public static Team$EnumVisible b(ScorePlayerTeam var0) {
      return var0.getNameTagVisibility();
   }

   public static Team$EnumVisible i(ScorePlayerTeam var0) {
      return var0.getDeathMessageVisibility();
   }

   public static Collection e(ScorePlayerTeam var0) {
      return var0.getMembershipCollection();
   }

   public static String a(Team var0, String var1) {
      return ScorePlayerTeam.formatPlayerName(var0, var1);
   }

   public static void a(ScorePlayerTeam var0, int var1) {
      var0.func_98298_a(var1);
   }

   public static int k(ScorePlayerTeam var0) {
      return var0.func_98299_i();
   }
}

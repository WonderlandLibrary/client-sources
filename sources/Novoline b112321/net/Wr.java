package net;

import java.util.HashMap;
import java.util.Optional;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;

public class Wr {
   public static boolean d(Scoreboard var0, String var1) {
      return var0.objectiveExists(var1);
   }

   public static void k(Scoreboard var0, String var1) {
      var0.addObjective(var1);
   }

   public static String b(Scoreboard var0) {
      return var0.getColorIndependentSidebar();
   }

   public static Optional e(Scoreboard var0, String var1) {
      return var0.getPlayerTeamColor(var1);
   }

   public static HashMap a(Scoreboard var0) {
      return var0.getColorDependentSidebar();
   }

   public static void i(Scoreboard var0, String var1) {
      var0.removeObjective(var1);
   }

   public static String j(Scoreboard var0, String var1) {
      return var0.k(var1);
   }

   public static String c(Scoreboard var0, String var1) {
      return var0.sendTeamForScore(var1);
   }

   public static void g(Scoreboard var0, String var1) {
      var0.setColorIndependentSidebar(var1);
   }

   public static boolean b(Scoreboard var0, String var1) {
      return var0.teamExists(var1);
   }

   public static void h(Scoreboard var0, String var1) {
      var0.removeTeam(var1);
   }

   public static void f(Scoreboard var0, String var1) {
      var0.addTeam(var1);
   }

   public static Optional a(Scoreboard var0, String var1) {
      return var0.getTeamColor(var1);
   }

   public static void a(Scoreboard var0, String var1, Byte var2) {
      var0.setTeamColor(var1, var2);
   }

   public static boolean a(Scoreboard var0, String var1, String var2) {
      return var0.isPlayerInTeam(var1, var2);
   }

   public static void c(Scoreboard var0, String var1, String var2) {
      var0.removePlayerFromTeam(var1, var2);
   }

   public static void b(Scoreboard var0, String var1, String var2) {
      var0.addPlayerToTeam(var1, var2);
   }
}

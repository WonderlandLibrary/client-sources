package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Team$EnumVisible {
   ALWAYS("always", 0),
   NEVER("never", 1),
   HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
   HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

   private static Map field_178828_g = Maps.newHashMap();
   public final String field_178830_e;
   public final int field_178827_f;
   private static final Team$EnumVisible[] $VALUES = new Team$EnumVisible[]{ALWAYS, NEVER, HIDE_FOR_OTHER_TEAMS, HIDE_FOR_OWN_TEAM};

   public static String[] func_178825_a() {
      return (String[])field_178828_g.keySet().toArray(new String[0]);
   }

   public static Team$EnumVisible func_178824_a(String var0) {
      return (Team$EnumVisible)field_178828_g.get(var0);
   }

   private Team$EnumVisible(String var3, int var4) {
      this.field_178830_e = var3;
      this.field_178827_f = var4;
   }

   static {
      for(Team$EnumVisible var11 : values()) {
         field_178828_g.put(var11.field_178830_e, var11);
      }

   }
}

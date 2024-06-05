package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;

public abstract class Team {
   private static final String __OBFID = "CL_00000621";

   public abstract boolean getAllowFriendlyFire();

   public abstract String getRegisteredName();

   public abstract Team.EnumVisible func_178771_j();

   public abstract boolean func_98297_h();

   public boolean isSameTeam(Team var1) {
      return var1 == null ? false : this == var1;
   }

   public abstract String formatString(String var1);

   public abstract Collection getMembershipCollection();

   public abstract Team.EnumVisible func_178770_i();

   public static enum EnumVisible {
      HIDE_FOR_OTHER_TEAMS("HIDE_FOR_OTHER_TEAMS", 2, "hideForOtherTeams", 2),
      ALWAYS("ALWAYS", 0, "always", 0),
      NEVER("NEVER", 1, "never", 1);

      private static Map field_178828_g = Maps.newHashMap();
      private static final Team.EnumVisible[] $VALUES = new Team.EnumVisible[]{ALWAYS, NEVER, HIDE_FOR_OTHER_TEAMS, HIDE_FOR_OWN_TEAM};
      private static final String __OBFID = "CL_00001962";
      private static final Team.EnumVisible[] ENUM$VALUES = new Team.EnumVisible[]{ALWAYS, NEVER, HIDE_FOR_OTHER_TEAMS, HIDE_FOR_OWN_TEAM};
      public final int field_178827_f;
      public final String field_178830_e;
      HIDE_FOR_OWN_TEAM("HIDE_FOR_OWN_TEAM", 3, "hideForOwnTeam", 3);

      static {
         Team.EnumVisible[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Team.EnumVisible var3 = var0[var2];
            field_178828_g.put(var3.field_178830_e, var3);
         }

      }

      private EnumVisible(String var3, int var4, String var5, int var6) {
         this.field_178830_e = var5;
         this.field_178827_f = var6;
      }

      public static Team.EnumVisible func_178824_a(String var0) {
         return (Team.EnumVisible)field_178828_g.get(var0);
      }

      public static String[] func_178825_a() {
         return (String[])field_178828_g.keySet().toArray(new String[field_178828_g.size()]);
      }
   }
}

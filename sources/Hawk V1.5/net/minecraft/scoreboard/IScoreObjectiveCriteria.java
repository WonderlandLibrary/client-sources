package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.util.EnumChatFormatting;

public interface IScoreObjectiveCriteria {
   IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
   IScoreObjectiveCriteria[] field_178793_i = new IScoreObjectiveCriteria[]{new GoalColor("killedByTeam.", EnumChatFormatting.BLACK), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_BLUE), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GREEN), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_AQUA), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_RED), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_PURPLE), new GoalColor("killedByTeam.", EnumChatFormatting.GOLD), new GoalColor("killedByTeam.", EnumChatFormatting.GRAY), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GRAY), new GoalColor("killedByTeam.", EnumChatFormatting.BLUE), new GoalColor("killedByTeam.", EnumChatFormatting.GREEN), new GoalColor("killedByTeam.", EnumChatFormatting.AQUA), new GoalColor("killedByTeam.", EnumChatFormatting.RED), new GoalColor("killedByTeam.", EnumChatFormatting.LIGHT_PURPLE), new GoalColor("killedByTeam.", EnumChatFormatting.YELLOW), new GoalColor("killedByTeam.", EnumChatFormatting.WHITE)};
   Map INSTANCES = Maps.newHashMap();
   IScoreObjectiveCriteria field_178791_c = new ScoreDummyCriteria("trigger");
   IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
   IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
   IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
   IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
   IScoreObjectiveCriteria[] field_178792_h = new IScoreObjectiveCriteria[]{new GoalColor("teamkill.", EnumChatFormatting.BLACK), new GoalColor("teamkill.", EnumChatFormatting.DARK_BLUE), new GoalColor("teamkill.", EnumChatFormatting.DARK_GREEN), new GoalColor("teamkill.", EnumChatFormatting.DARK_AQUA), new GoalColor("teamkill.", EnumChatFormatting.DARK_RED), new GoalColor("teamkill.", EnumChatFormatting.DARK_PURPLE), new GoalColor("teamkill.", EnumChatFormatting.GOLD), new GoalColor("teamkill.", EnumChatFormatting.GRAY), new GoalColor("teamkill.", EnumChatFormatting.DARK_GRAY), new GoalColor("teamkill.", EnumChatFormatting.BLUE), new GoalColor("teamkill.", EnumChatFormatting.GREEN), new GoalColor("teamkill.", EnumChatFormatting.AQUA), new GoalColor("teamkill.", EnumChatFormatting.RED), new GoalColor("teamkill.", EnumChatFormatting.LIGHT_PURPLE), new GoalColor("teamkill.", EnumChatFormatting.YELLOW), new GoalColor("teamkill.", EnumChatFormatting.WHITE)};

   String getName();

   IScoreObjectiveCriteria.EnumRenderType func_178790_c();

   boolean isReadOnly();

   int func_96635_a(List var1);

   public static enum EnumRenderType {
      HEARTS("HEARTS", 1, "hearts");

      private static final String __OBFID = "CL_00001960";
      private final String field_178798_d;
      INTEGER("INTEGER", 0, "integer");

      private static final IScoreObjectiveCriteria.EnumRenderType[] ENUM$VALUES = new IScoreObjectiveCriteria.EnumRenderType[]{INTEGER, HEARTS};
      private static final IScoreObjectiveCriteria.EnumRenderType[] $VALUES = new IScoreObjectiveCriteria.EnumRenderType[]{INTEGER, HEARTS};
      private static final Map field_178801_c = Maps.newHashMap();

      public static IScoreObjectiveCriteria.EnumRenderType func_178795_a(String var0) {
         IScoreObjectiveCriteria.EnumRenderType var1 = (IScoreObjectiveCriteria.EnumRenderType)field_178801_c.get(var0);
         return var1 == null ? INTEGER : var1;
      }

      private EnumRenderType(String var3, int var4, String var5) {
         this.field_178798_d = var5;
      }

      static {
         IScoreObjectiveCriteria.EnumRenderType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            IScoreObjectiveCriteria.EnumRenderType var3 = var0[var2];
            field_178801_c.put(var3.func_178796_a(), var3);
         }

      }

      public String func_178796_a() {
         return this.field_178798_d;
      }
   }
}

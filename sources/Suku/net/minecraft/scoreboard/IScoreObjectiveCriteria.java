package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Formatting;

public interface IScoreObjectiveCriteria
{
    Map<String, IScoreObjectiveCriteria> INSTANCES = Maps.<String, IScoreObjectiveCriteria>newHashMap();
    IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
    IScoreObjectiveCriteria TRIGGER = new ScoreDummyCriteria("trigger");
    IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
    IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
    IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
    IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
    IScoreObjectiveCriteria[] field_178792_h = new IScoreObjectiveCriteria[] {new GoalColor("teamkill.", Formatting.BLACK), new GoalColor("teamkill.", Formatting.DARK_BLUE), new GoalColor("teamkill.", Formatting.DARK_GREEN), new GoalColor("teamkill.", Formatting.DARK_AQUA), new GoalColor("teamkill.", Formatting.DARK_RED), new GoalColor("teamkill.", Formatting.DARK_PURPLE), new GoalColor("teamkill.", Formatting.GOLD), new GoalColor("teamkill.", Formatting.GRAY), new GoalColor("teamkill.", Formatting.DARK_GRAY), new GoalColor("teamkill.", Formatting.BLUE), new GoalColor("teamkill.", Formatting.GREEN), new GoalColor("teamkill.", Formatting.AQUA), new GoalColor("teamkill.", Formatting.RED), new GoalColor("teamkill.", Formatting.LIGHT_PURPLE), new GoalColor("teamkill.", Formatting.YELLOW), new GoalColor("teamkill.", Formatting.WHITE)};
    IScoreObjectiveCriteria[] field_178793_i = new IScoreObjectiveCriteria[] {new GoalColor("killedByTeam.", Formatting.BLACK), new GoalColor("killedByTeam.", Formatting.DARK_BLUE), new GoalColor("killedByTeam.", Formatting.DARK_GREEN), new GoalColor("killedByTeam.", Formatting.DARK_AQUA), new GoalColor("killedByTeam.", Formatting.DARK_RED), new GoalColor("killedByTeam.", Formatting.DARK_PURPLE), new GoalColor("killedByTeam.", Formatting.GOLD), new GoalColor("killedByTeam.", Formatting.GRAY), new GoalColor("killedByTeam.", Formatting.DARK_GRAY), new GoalColor("killedByTeam.", Formatting.BLUE), new GoalColor("killedByTeam.", Formatting.GREEN), new GoalColor("killedByTeam.", Formatting.AQUA), new GoalColor("killedByTeam.", Formatting.RED), new GoalColor("killedByTeam.", Formatting.LIGHT_PURPLE), new GoalColor("killedByTeam.", Formatting.YELLOW), new GoalColor("killedByTeam.", Formatting.WHITE)};

    String getName();

    int setScore(List<EntityPlayer> p_96635_1_);

    boolean isReadOnly();

    IScoreObjectiveCriteria.EnumRenderType getRenderType();

    public static enum EnumRenderType
    {
        INTEGER("integer"),
        HEARTS("hearts");

        private static final Map<String, IScoreObjectiveCriteria.EnumRenderType> field_178801_c = Maps.<String, IScoreObjectiveCriteria.EnumRenderType>newHashMap();
        private final String field_178798_d;

        private EnumRenderType(String p_i45548_3_)
        {
            this.field_178798_d = p_i45548_3_;
        }

        public String func_178796_a()
        {
            return this.field_178798_d;
        }

        public static IScoreObjectiveCriteria.EnumRenderType func_178795_a(String p_178795_0_)
        {
            IScoreObjectiveCriteria.EnumRenderType iscoreobjectivecriteria$enumrendertype = (IScoreObjectiveCriteria.EnumRenderType)field_178801_c.get(p_178795_0_);
            return iscoreobjectivecriteria$enumrendertype == null ? INTEGER : iscoreobjectivecriteria$enumrendertype;
        }

        static {
            for (IScoreObjectiveCriteria.EnumRenderType iscoreobjectivecriteria$enumrendertype : values())
            {
                field_178801_c.put(iscoreobjectivecriteria$enumrendertype.func_178796_a(), iscoreobjectivecriteria$enumrendertype);
            }
        }
    }
}

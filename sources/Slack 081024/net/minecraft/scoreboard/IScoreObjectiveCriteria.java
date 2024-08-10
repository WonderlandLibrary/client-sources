package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatFormatting;

public interface IScoreObjectiveCriteria
{
    Map<String, IScoreObjectiveCriteria> INSTANCES = Maps.<String, IScoreObjectiveCriteria>newHashMap();
    IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
    IScoreObjectiveCriteria TRIGGER = new ScoreDummyCriteria("trigger");
    IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
    IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
    IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
    IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
    IScoreObjectiveCriteria[] field_178792_h = new IScoreObjectiveCriteria[] {new GoalColor("teamkill.", ChatFormatting.BLACK), new GoalColor("teamkill.", ChatFormatting.DARK_BLUE), new GoalColor("teamkill.", ChatFormatting.DARK_GREEN), new GoalColor("teamkill.", ChatFormatting.DARK_AQUA), new GoalColor("teamkill.", ChatFormatting.DARK_RED), new GoalColor("teamkill.", ChatFormatting.DARK_PURPLE), new GoalColor("teamkill.", ChatFormatting.GOLD), new GoalColor("teamkill.", ChatFormatting.GRAY), new GoalColor("teamkill.", ChatFormatting.DARK_GRAY), new GoalColor("teamkill.", ChatFormatting.BLUE), new GoalColor("teamkill.", ChatFormatting.GREEN), new GoalColor("teamkill.", ChatFormatting.AQUA), new GoalColor("teamkill.", ChatFormatting.RED), new GoalColor("teamkill.", ChatFormatting.LIGHT_PURPLE), new GoalColor("teamkill.", ChatFormatting.YELLOW), new GoalColor("teamkill.", ChatFormatting.WHITE)};
    IScoreObjectiveCriteria[] field_178793_i = new IScoreObjectiveCriteria[] {new GoalColor("killedByTeam.", ChatFormatting.BLACK), new GoalColor("killedByTeam.", ChatFormatting.DARK_BLUE), new GoalColor("killedByTeam.", ChatFormatting.DARK_GREEN), new GoalColor("killedByTeam.", ChatFormatting.DARK_AQUA), new GoalColor("killedByTeam.", ChatFormatting.DARK_RED), new GoalColor("killedByTeam.", ChatFormatting.DARK_PURPLE), new GoalColor("killedByTeam.", ChatFormatting.GOLD), new GoalColor("killedByTeam.", ChatFormatting.GRAY), new GoalColor("killedByTeam.", ChatFormatting.DARK_GRAY), new GoalColor("killedByTeam.", ChatFormatting.BLUE), new GoalColor("killedByTeam.", ChatFormatting.GREEN), new GoalColor("killedByTeam.", ChatFormatting.AQUA), new GoalColor("killedByTeam.", ChatFormatting.RED), new GoalColor("killedByTeam.", ChatFormatting.LIGHT_PURPLE), new GoalColor("killedByTeam.", ChatFormatting.YELLOW), new GoalColor("killedByTeam.", ChatFormatting.WHITE)};

    String getName();

    int func_96635_a(List<EntityPlayer> p_96635_1_);

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
            IScoreObjectiveCriteria.EnumRenderType iscoreobjectivecriteria$enumrendertype = field_178801_c.get(p_178795_0_);
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

package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.util.EnumChatFormatting;

public abstract interface IScoreObjectiveCriteria
{
  public static final Map INSTANCES = ;
  public static final IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
  public static final IScoreObjectiveCriteria field_178791_c = new ScoreDummyCriteria("trigger");
  public static final IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
  public static final IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
  public static final IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
  public static final IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
  public static final IScoreObjectiveCriteria[] field_178792_h = { new GoalColor("teamkill.", EnumChatFormatting.BLACK), new GoalColor("teamkill.", EnumChatFormatting.DARK_BLUE), new GoalColor("teamkill.", EnumChatFormatting.DARK_GREEN), new GoalColor("teamkill.", EnumChatFormatting.DARK_AQUA), new GoalColor("teamkill.", EnumChatFormatting.DARK_RED), new GoalColor("teamkill.", EnumChatFormatting.DARK_PURPLE), new GoalColor("teamkill.", EnumChatFormatting.GOLD), new GoalColor("teamkill.", EnumChatFormatting.GRAY), new GoalColor("teamkill.", EnumChatFormatting.DARK_GRAY), new GoalColor("teamkill.", EnumChatFormatting.BLUE), new GoalColor("teamkill.", EnumChatFormatting.GREEN), new GoalColor("teamkill.", EnumChatFormatting.AQUA), new GoalColor("teamkill.", EnumChatFormatting.RED), new GoalColor("teamkill.", EnumChatFormatting.LIGHT_PURPLE), new GoalColor("teamkill.", EnumChatFormatting.YELLOW), new GoalColor("teamkill.", EnumChatFormatting.WHITE) };
  public static final IScoreObjectiveCriteria[] field_178793_i = { new GoalColor("killedByTeam.", EnumChatFormatting.BLACK), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_BLUE), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GREEN), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_AQUA), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_RED), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_PURPLE), new GoalColor("killedByTeam.", EnumChatFormatting.GOLD), new GoalColor("killedByTeam.", EnumChatFormatting.GRAY), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GRAY), new GoalColor("killedByTeam.", EnumChatFormatting.BLUE), new GoalColor("killedByTeam.", EnumChatFormatting.GREEN), new GoalColor("killedByTeam.", EnumChatFormatting.AQUA), new GoalColor("killedByTeam.", EnumChatFormatting.RED), new GoalColor("killedByTeam.", EnumChatFormatting.LIGHT_PURPLE), new GoalColor("killedByTeam.", EnumChatFormatting.YELLOW), new GoalColor("killedByTeam.", EnumChatFormatting.WHITE) };
  
  public abstract String getName();
  
  public abstract int func_96635_a(List paramList);
  
  public abstract boolean isReadOnly();
  
  public abstract EnumRenderType func_178790_c();
  
  public static enum EnumRenderType
  {
    private static final Map field_178801_c;
    private final String field_178798_d;
    private static final EnumRenderType[] $VALUES;
    private static final String __OBFID = "CL_00001960";
    
    private EnumRenderType(String p_i45548_1_, int p_i45548_2_, String p_i45548_3_)
    {
      this.field_178798_d = p_i45548_3_;
    }
    
    public String func_178796_a()
    {
      return this.field_178798_d;
    }
    
    public static EnumRenderType func_178795_a(String p_178795_0_)
    {
      EnumRenderType var1 = (EnumRenderType)field_178801_c.get(p_178795_0_);
      return var1 == null ? INTEGER : var1;
    }
    
    static
    {
      field_178801_c = Maps.newHashMap();
      
      $VALUES = new EnumRenderType[] { INTEGER, HEARTS };
      
      EnumRenderType[] var0 = values();
      int var1 = var0.length;
      for (int var2 = 0; var2 < var1; var2++)
      {
        EnumRenderType var3 = var0[var2];
        field_178801_c.put(var3.func_178796_a(), var3);
      }
    }
  }
}

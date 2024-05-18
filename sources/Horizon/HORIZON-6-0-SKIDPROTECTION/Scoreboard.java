package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;
import com.google.common.collect.Maps;
import java.util.Map;

public class Scoreboard
{
    private final Map HorizonCode_Horizon_È;
    private final Map Â;
    private final Map Ý;
    private final ScoreObjective[] Ø­áŒŠá;
    private final Map Âµá€;
    private final Map Ó;
    private static String[] à;
    private static final String Ø = "CL_00000619";
    
    static {
        Scoreboard.à = null;
    }
    
    public Scoreboard() {
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        this.Â = Maps.newHashMap();
        this.Ý = Maps.newHashMap();
        this.Ø­áŒŠá = new ScoreObjective[19];
        this.Âµá€ = Maps.newHashMap();
        this.Ó = Maps.newHashMap();
    }
    
    public ScoreObjective HorizonCode_Horizon_È(final String p_96518_1_) {
        return this.HorizonCode_Horizon_È.get(p_96518_1_);
    }
    
    public ScoreObjective HorizonCode_Horizon_È(final String p_96535_1_, final IScoreObjectiveCriteria p_96535_2_) {
        ScoreObjective var3 = this.HorizonCode_Horizon_È(p_96535_1_);
        if (var3 != null) {
            throw new IllegalArgumentException("An objective with the name '" + p_96535_1_ + "' already exists!");
        }
        var3 = new ScoreObjective(this, p_96535_1_, p_96535_2_);
        Object var4 = this.Â.get(p_96535_2_);
        if (var4 == null) {
            var4 = Lists.newArrayList();
            this.Â.put(p_96535_2_, var4);
        }
        ((List)var4).add(var3);
        this.HorizonCode_Horizon_È.put(p_96535_1_, var3);
        this.Ý(var3);
        return var3;
    }
    
    public Collection HorizonCode_Horizon_È(final IScoreObjectiveCriteria p_96520_1_) {
        final Collection var2 = this.Â.get(p_96520_1_);
        return (var2 == null) ? Lists.newArrayList() : Lists.newArrayList((Iterable)var2);
    }
    
    public boolean HorizonCode_Horizon_È(final String p_178819_1_, final ScoreObjective p_178819_2_) {
        final Map var3 = this.Ý.get(p_178819_1_);
        if (var3 == null) {
            return false;
        }
        final Score var4 = var3.get(p_178819_2_);
        return var4 != null;
    }
    
    public Score Â(final String p_96529_1_, final ScoreObjective p_96529_2_) {
        Object var3 = this.Ý.get(p_96529_1_);
        if (var3 == null) {
            var3 = Maps.newHashMap();
            this.Ý.put(p_96529_1_, var3);
        }
        Score var4 = ((Map)var3).get(p_96529_2_);
        if (var4 == null) {
            var4 = new Score(this, p_96529_2_, p_96529_1_);
            ((Map)var3).put(p_96529_2_, var4);
        }
        return var4;
    }
    
    public Collection HorizonCode_Horizon_È(final ScoreObjective p_96534_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (final Map var4 : this.Ý.values()) {
            final Score var5 = var4.get(p_96534_1_);
            if (var5 != null) {
                var2.add(var5);
            }
        }
        Collections.sort((List<Object>)var2, Score.HorizonCode_Horizon_È);
        return var2;
    }
    
    public Collection HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.values();
    }
    
    public Collection Â() {
        return this.Ý.keySet();
    }
    
    public void Ý(final String p_178822_1_, final ScoreObjective p_178822_2_) {
        if (p_178822_2_ == null) {
            final Map var3 = this.Ý.remove(p_178822_1_);
            if (var3 != null) {
                this.à(p_178822_1_);
            }
        }
        else {
            final Map var3 = this.Ý.get(p_178822_1_);
            if (var3 != null) {
                final Score var4 = var3.remove(p_178822_2_);
                if (var3.size() < 1) {
                    final Map var5 = this.Ý.remove(p_178822_1_);
                    if (var5 != null) {
                        this.à(p_178822_1_);
                    }
                }
                else if (var4 != null) {
                    this.Ø­áŒŠá(p_178822_1_, p_178822_2_);
                }
            }
        }
    }
    
    public Collection Ý() {
        final Collection var1 = this.Ý.values();
        final ArrayList var2 = Lists.newArrayList();
        for (final Map var4 : var1) {
            var2.addAll(var4.values());
        }
        return var2;
    }
    
    public Map Â(final String p_96510_1_) {
        Object var2 = this.Ý.get(p_96510_1_);
        if (var2 == null) {
            var2 = Maps.newHashMap();
        }
        return (Map)var2;
    }
    
    public void Â(final ScoreObjective p_96519_1_) {
        this.HorizonCode_Horizon_È.remove(p_96519_1_.Â());
        for (int var2 = 0; var2 < 19; ++var2) {
            if (this.HorizonCode_Horizon_È(var2) == p_96519_1_) {
                this.HorizonCode_Horizon_È(var2, null);
            }
        }
        final List var3 = this.Â.get(p_96519_1_.Ý());
        if (var3 != null) {
            var3.remove(p_96519_1_);
        }
        for (final Map var5 : this.Ý.values()) {
            var5.remove(p_96519_1_);
        }
        this.Âµá€(p_96519_1_);
    }
    
    public void HorizonCode_Horizon_È(final int p_96530_1_, final ScoreObjective p_96530_2_) {
        this.Ø­áŒŠá[p_96530_1_] = p_96530_2_;
    }
    
    public ScoreObjective HorizonCode_Horizon_È(final int p_96539_1_) {
        return this.Ø­áŒŠá[p_96539_1_];
    }
    
    public ScorePlayerTeam Ý(final String p_96508_1_) {
        return this.Âµá€.get(p_96508_1_);
    }
    
    public ScorePlayerTeam Ø­áŒŠá(final String p_96527_1_) {
        ScorePlayerTeam var2 = this.Ý(p_96527_1_);
        if (var2 != null) {
            throw new IllegalArgumentException("A team with the name '" + p_96527_1_ + "' already exists!");
        }
        var2 = new ScorePlayerTeam(this, p_96527_1_);
        this.Âµá€.put(p_96527_1_, var2);
        this.Â(var2);
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final ScorePlayerTeam p_96511_1_) {
        this.Âµá€.remove(p_96511_1_.HorizonCode_Horizon_È());
        for (final String var3 : p_96511_1_.Ý()) {
            this.Ó.remove(var3);
        }
        this.Ø­áŒŠá(p_96511_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final String p_151392_1_, final String p_151392_2_) {
        if (!this.Âµá€.containsKey(p_151392_2_)) {
            return false;
        }
        final ScorePlayerTeam var3 = this.Ý(p_151392_2_);
        if (this.Ó(p_151392_1_) != null) {
            this.Âµá€(p_151392_1_);
        }
        this.Ó.put(p_151392_1_, var3);
        var3.Ý().add(p_151392_1_);
        return true;
    }
    
    public boolean Âµá€(final String p_96524_1_) {
        final ScorePlayerTeam var2 = this.Ó(p_96524_1_);
        if (var2 != null) {
            this.HorizonCode_Horizon_È(p_96524_1_, var2);
            return true;
        }
        return false;
    }
    
    public void HorizonCode_Horizon_È(final String p_96512_1_, final ScorePlayerTeam p_96512_2_) {
        if (this.Ó(p_96512_1_) != p_96512_2_) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + p_96512_2_.HorizonCode_Horizon_È() + "'.");
        }
        this.Ó.remove(p_96512_1_);
        p_96512_2_.Ý().remove(p_96512_1_);
    }
    
    public Collection Ø­áŒŠá() {
        return this.Âµá€.keySet();
    }
    
    public Collection Âµá€() {
        return this.Âµá€.values();
    }
    
    public ScorePlayerTeam Ó(final String p_96509_1_) {
        return this.Ó.get(p_96509_1_);
    }
    
    public void Ý(final ScoreObjective p_96522_1_) {
    }
    
    public void Ø­áŒŠá(final ScoreObjective p_96532_1_) {
    }
    
    public void Âµá€(final ScoreObjective p_96533_1_) {
    }
    
    public void HorizonCode_Horizon_È(final Score p_96536_1_) {
    }
    
    public void à(final String p_96516_1_) {
    }
    
    public void Ø­áŒŠá(final String p_178820_1_, final ScoreObjective p_178820_2_) {
    }
    
    public void Â(final ScorePlayerTeam p_96523_1_) {
    }
    
    public void Ý(final ScorePlayerTeam p_96538_1_) {
    }
    
    public void Ø­áŒŠá(final ScorePlayerTeam p_96513_1_) {
    }
    
    public static String Â(final int p_96517_0_) {
        switch (p_96517_0_) {
            case 0: {
                return "list";
            }
            case 1: {
                return "sidebar";
            }
            case 2: {
                return "belowName";
            }
            default: {
                if (p_96517_0_ >= 3 && p_96517_0_ <= 18) {
                    final EnumChatFormatting var1 = EnumChatFormatting.HorizonCode_Horizon_È(p_96517_0_ - 3);
                    if (var1 != null && var1 != EnumChatFormatting.Æ) {
                        return "sidebar.team." + var1.Ø­áŒŠá();
                    }
                }
                return null;
            }
        }
    }
    
    public static int Ø(final String p_96537_0_) {
        if (p_96537_0_.equalsIgnoreCase("list")) {
            return 0;
        }
        if (p_96537_0_.equalsIgnoreCase("sidebar")) {
            return 1;
        }
        if (p_96537_0_.equalsIgnoreCase("belowName")) {
            return 2;
        }
        if (p_96537_0_.startsWith("sidebar.team.")) {
            final String var1 = p_96537_0_.substring("sidebar.team.".length());
            final EnumChatFormatting var2 = EnumChatFormatting.Â(var1);
            if (var2 != null && var2.HorizonCode_Horizon_È() >= 0) {
                return var2.HorizonCode_Horizon_È() + 3;
            }
        }
        return -1;
    }
    
    public static String[] Ó() {
        if (Scoreboard.à == null) {
            Scoreboard.à = new String[19];
            for (int var0 = 0; var0 < 19; ++var0) {
                Scoreboard.à[var0] = Â(var0);
            }
        }
        return Scoreboard.à;
    }
}

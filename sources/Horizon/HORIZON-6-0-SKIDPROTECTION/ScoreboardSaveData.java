package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData extends WorldSavedData
{
    private static final Logger Â;
    private Scoreboard Ý;
    private NBTTagCompound Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000620";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public ScoreboardSaveData() {
        this("scoreboard");
    }
    
    public ScoreboardSaveData(final String p_i2310_1_) {
        super(p_i2310_1_);
    }
    
    public void HorizonCode_Horizon_È(final Scoreboard p_96499_1_) {
        this.Ý = p_96499_1_;
        if (this.Ø­áŒŠá != null) {
            this.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        if (this.Ý == null) {
            this.Ø­áŒŠá = nbt;
        }
        else {
            this.Â(nbt.Ý("Objectives", 10));
            this.Ý(nbt.Ý("PlayerScores", 10));
            if (nbt.Â("DisplaySlots", 10)) {
                this.Â(nbt.ˆÏ­("DisplaySlots"));
            }
            if (nbt.Â("Teams", 9)) {
                this.HorizonCode_Horizon_È(nbt.Ý("Teams", 10));
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final NBTTagList p_96498_1_) {
        for (int var2 = 0; var2 < p_96498_1_.Âµá€(); ++var2) {
            final NBTTagCompound var3 = p_96498_1_.Â(var2);
            final ScorePlayerTeam var4 = this.Ý.Ø­áŒŠá(var3.áˆºÑ¢Õ("Name"));
            var4.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("DisplayName"));
            if (var3.Â("TeamColor", 8)) {
                var4.HorizonCode_Horizon_È(EnumChatFormatting.Â(var3.áˆºÑ¢Õ("TeamColor")));
            }
            var4.Â(var3.áˆºÑ¢Õ("Prefix"));
            var4.Ý(var3.áˆºÑ¢Õ("Suffix"));
            if (var3.Â("AllowFriendlyFire", 99)) {
                var4.HorizonCode_Horizon_È(var3.£á("AllowFriendlyFire"));
            }
            if (var3.Â("SeeFriendlyInvisibles", 99)) {
                var4.Â(var3.£á("SeeFriendlyInvisibles"));
            }
            if (var3.Â("NameTagVisibility", 8)) {
                final Team.HorizonCode_Horizon_È var5 = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("NameTagVisibility"));
                if (var5 != null) {
                    var4.HorizonCode_Horizon_È(var5);
                }
            }
            if (var3.Â("DeathMessageVisibility", 8)) {
                final Team.HorizonCode_Horizon_È var5 = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("DeathMessageVisibility"));
                if (var5 != null) {
                    var4.Â(var5);
                }
            }
            this.HorizonCode_Horizon_È(var4, var3.Ý("Players", 8));
        }
    }
    
    protected void HorizonCode_Horizon_È(final ScorePlayerTeam p_96502_1_, final NBTTagList p_96502_2_) {
        for (int var3 = 0; var3 < p_96502_2_.Âµá€(); ++var3) {
            this.Ý.HorizonCode_Horizon_È(p_96502_2_.Ó(var3), p_96502_1_.HorizonCode_Horizon_È());
        }
    }
    
    protected void Â(final NBTTagCompound p_96504_1_) {
        for (int var2 = 0; var2 < 19; ++var2) {
            if (p_96504_1_.Â("slot_" + var2, 8)) {
                final String var3 = p_96504_1_.áˆºÑ¢Õ("slot_" + var2);
                final ScoreObjective var4 = this.Ý.HorizonCode_Horizon_È(var3);
                this.Ý.HorizonCode_Horizon_È(var2, var4);
            }
        }
    }
    
    protected void Â(final NBTTagList p_96501_1_) {
        for (int var2 = 0; var2 < p_96501_1_.Âµá€(); ++var2) {
            final NBTTagCompound var3 = p_96501_1_.Â(var2);
            final IScoreObjectiveCriteria var4 = IScoreObjectiveCriteria.HorizonCode_Horizon_È.get(var3.áˆºÑ¢Õ("CriteriaName"));
            if (var4 != null) {
                final ScoreObjective var5 = this.Ý.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("Name"), var4);
                var5.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("DisplayName"));
                var5.HorizonCode_Horizon_È(IScoreObjectiveCriteria.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("RenderType")));
            }
        }
    }
    
    protected void Ý(final NBTTagList p_96500_1_) {
        for (int var2 = 0; var2 < p_96500_1_.Âµá€(); ++var2) {
            final NBTTagCompound var3 = p_96500_1_.Â(var2);
            final ScoreObjective var4 = this.Ý.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("Objective"));
            final Score var5 = this.Ý.Â(var3.áˆºÑ¢Õ("Name"), var4);
            var5.Ý(var3.Ó("Score"));
            if (var3.Ý("Locked")) {
                var5.HorizonCode_Horizon_È(var3.£á("Locked"));
            }
        }
    }
    
    @Override
    public void Ý(final NBTTagCompound nbt) {
        if (this.Ý == null) {
            ScoreboardSaveData.Â.warn("Tried to save scoreboard without having a scoreboard...");
        }
        else {
            nbt.HorizonCode_Horizon_È("Objectives", this.Â());
            nbt.HorizonCode_Horizon_È("PlayerScores", this.Ý());
            nbt.HorizonCode_Horizon_È("Teams", this.HorizonCode_Horizon_È());
            this.Ø­áŒŠá(nbt);
        }
    }
    
    protected NBTTagList HorizonCode_Horizon_È() {
        final NBTTagList var1 = new NBTTagList();
        final Collection var2 = this.Ý.Âµá€();
        for (final ScorePlayerTeam var4 : var2) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.HorizonCode_Horizon_È("Name", var4.HorizonCode_Horizon_È());
            var5.HorizonCode_Horizon_È("DisplayName", var4.Â());
            if (var4.ÂµÈ().HorizonCode_Horizon_È() >= 0) {
                var5.HorizonCode_Horizon_È("TeamColor", var4.ÂµÈ().Ø­áŒŠá());
            }
            var5.HorizonCode_Horizon_È("Prefix", var4.Ø­áŒŠá());
            var5.HorizonCode_Horizon_È("Suffix", var4.Âµá€());
            var5.HorizonCode_Horizon_È("AllowFriendlyFire", var4.Ó());
            var5.HorizonCode_Horizon_È("SeeFriendlyInvisibles", var4.à());
            var5.HorizonCode_Horizon_È("NameTagVisibility", var4.Ø().Âµá€);
            var5.HorizonCode_Horizon_È("DeathMessageVisibility", var4.áŒŠÆ().Âµá€);
            final NBTTagList var6 = new NBTTagList();
            for (final String var8 : var4.Ý()) {
                var6.HorizonCode_Horizon_È(new NBTTagString(var8));
            }
            var5.HorizonCode_Horizon_È("Players", var6);
            var1.HorizonCode_Horizon_È(var5);
        }
        return var1;
    }
    
    protected void Ø­áŒŠá(final NBTTagCompound p_96497_1_) {
        final NBTTagCompound var2 = new NBTTagCompound();
        boolean var3 = false;
        for (int var4 = 0; var4 < 19; ++var4) {
            final ScoreObjective var5 = this.Ý.HorizonCode_Horizon_È(var4);
            if (var5 != null) {
                var2.HorizonCode_Horizon_È("slot_" + var4, var5.Â());
                var3 = true;
            }
        }
        if (var3) {
            p_96497_1_.HorizonCode_Horizon_È("DisplaySlots", var2);
        }
    }
    
    protected NBTTagList Â() {
        final NBTTagList var1 = new NBTTagList();
        final Collection var2 = this.Ý.HorizonCode_Horizon_È();
        for (final ScoreObjective var4 : var2) {
            if (var4.Ý() != null) {
                final NBTTagCompound var5 = new NBTTagCompound();
                var5.HorizonCode_Horizon_È("Name", var4.Â());
                var5.HorizonCode_Horizon_È("CriteriaName", var4.Ý().HorizonCode_Horizon_È());
                var5.HorizonCode_Horizon_È("DisplayName", var4.Ø­áŒŠá());
                var5.HorizonCode_Horizon_È("RenderType", var4.Âµá€().HorizonCode_Horizon_È());
                var1.HorizonCode_Horizon_È(var5);
            }
        }
        return var1;
    }
    
    protected NBTTagList Ý() {
        final NBTTagList var1 = new NBTTagList();
        final Collection var2 = this.Ý.Ý();
        for (final Score var4 : var2) {
            if (var4.Ý() != null) {
                final NBTTagCompound var5 = new NBTTagCompound();
                var5.HorizonCode_Horizon_È("Name", var4.Ø­áŒŠá());
                var5.HorizonCode_Horizon_È("Objective", var4.Ý().Â());
                var5.HorizonCode_Horizon_È("Score", var4.Â());
                var5.HorizonCode_Horizon_È("Locked", var4.Ó());
                var1.HorizonCode_Horizon_È(var5);
            }
        }
        return var1;
    }
}

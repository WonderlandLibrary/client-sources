package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class ScoreboardSaveData extends WorldSavedData
{
    private Scoreboard field_96507_a;
    private NBTTagCompound field_96506_b;
    
    public ScoreboardSaveData() {
        this("scoreboard");
    }
    
    public ScoreboardSaveData(final String par1Str) {
        super(par1Str);
    }
    
    public void func_96499_a(final Scoreboard par1Scoreboard) {
        this.field_96507_a = par1Scoreboard;
        if (this.field_96506_b != null) {
            this.readFromNBT(this.field_96506_b);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        if (this.field_96507_a == null) {
            this.field_96506_b = par1NBTTagCompound;
        }
        else {
            this.func_96501_b(par1NBTTagCompound.getTagList("Objectives"));
            this.func_96500_c(par1NBTTagCompound.getTagList("PlayerScores"));
            if (par1NBTTagCompound.hasKey("DisplaySlots")) {
                this.func_96504_c(par1NBTTagCompound.getCompoundTag("DisplaySlots"));
            }
            if (par1NBTTagCompound.hasKey("Teams")) {
                this.func_96498_a(par1NBTTagCompound.getTagList("Teams"));
            }
        }
    }
    
    protected void func_96498_a(final NBTTagList par1NBTTagList) {
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2) {
            final NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
            final ScorePlayerTeam var4 = this.field_96507_a.func_96527_f(var3.getString("Name"));
            var4.func_96664_a(var3.getString("DisplayName"));
            var4.func_96666_b(var3.getString("Prefix"));
            var4.func_96662_c(var3.getString("Suffix"));
            if (var3.hasKey("AllowFriendlyFire")) {
                var4.func_96660_a(var3.getBoolean("AllowFriendlyFire"));
            }
            if (var3.hasKey("SeeFriendlyInvisibles")) {
                var4.func_98300_b(var3.getBoolean("SeeFriendlyInvisibles"));
            }
            this.func_96502_a(var4, var3.getTagList("Players"));
        }
    }
    
    protected void func_96502_a(final ScorePlayerTeam par1ScorePlayerTeam, final NBTTagList par2NBTTagList) {
        for (int var3 = 0; var3 < par2NBTTagList.tagCount(); ++var3) {
            this.field_96507_a.func_96521_a(((NBTTagString)par2NBTTagList.tagAt(var3)).data, par1ScorePlayerTeam);
        }
    }
    
    protected void func_96504_c(final NBTTagCompound par1NBTTagCompound) {
        for (int var2 = 0; var2 < 3; ++var2) {
            if (par1NBTTagCompound.hasKey("slot_" + var2)) {
                final String var3 = par1NBTTagCompound.getString("slot_" + var2);
                final ScoreObjective var4 = this.field_96507_a.getObjective(var3);
                this.field_96507_a.func_96530_a(var2, var4);
            }
        }
    }
    
    protected void func_96501_b(final NBTTagList par1NBTTagList) {
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2) {
            final NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
            final ScoreObjectiveCriteria var4 = ScoreObjectiveCriteria.field_96643_a.get(var3.getString("CriteriaName"));
            final ScoreObjective var5 = this.field_96507_a.func_96535_a(var3.getString("Name"), var4);
            var5.setDisplayName(var3.getString("DisplayName"));
        }
    }
    
    protected void func_96500_c(final NBTTagList par1NBTTagList) {
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2) {
            final NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
            final ScoreObjective var4 = this.field_96507_a.getObjective(var3.getString("Objective"));
            final Score var5 = this.field_96507_a.func_96529_a(var3.getString("Name"), var4);
            var5.func_96647_c(var3.getInteger("Score"));
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        if (this.field_96507_a == null) {
            MinecraftServer.getServer().getLogAgent().logWarning("Tried to save scoreboard without having a scoreboard...");
        }
        else {
            par1NBTTagCompound.setTag("Objectives", this.func_96505_b());
            par1NBTTagCompound.setTag("PlayerScores", this.func_96503_e());
            par1NBTTagCompound.setTag("Teams", this.func_96496_a());
            this.func_96497_d(par1NBTTagCompound);
        }
    }
    
    protected NBTTagList func_96496_a() {
        final NBTTagList var1 = new NBTTagList();
        final Collection var2 = this.field_96507_a.func_96525_g();
        for (final ScorePlayerTeam var4 : var2) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.func_96661_b());
            var5.setString("DisplayName", var4.func_96669_c());
            var5.setString("Prefix", var4.func_96668_e());
            var5.setString("Suffix", var4.func_96663_f());
            var5.setBoolean("AllowFriendlyFire", var4.func_96665_g());
            var5.setBoolean("SeeFriendlyInvisibles", var4.func_98297_h());
            final NBTTagList var6 = new NBTTagList();
            for (final String var8 : var4.getMembershipCollection()) {
                var6.appendTag(new NBTTagString("", var8));
            }
            var5.setTag("Players", var6);
            var1.appendTag(var5);
        }
        return var1;
    }
    
    protected void func_96497_d(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagCompound var2 = new NBTTagCompound();
        boolean var3 = false;
        for (int var4 = 0; var4 < 3; ++var4) {
            final ScoreObjective var5 = this.field_96507_a.func_96539_a(var4);
            if (var5 != null) {
                var2.setString("slot_" + var4, var5.getName());
                var3 = true;
            }
        }
        if (var3) {
            par1NBTTagCompound.setCompoundTag("DisplaySlots", var2);
        }
    }
    
    protected NBTTagList func_96505_b() {
        final NBTTagList var1 = new NBTTagList();
        final Collection var2 = this.field_96507_a.getScoreObjectives();
        for (final ScoreObjective var4 : var2) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.getName());
            var5.setString("CriteriaName", var4.getCriteria().func_96636_a());
            var5.setString("DisplayName", var4.getDisplayName());
            var1.appendTag(var5);
        }
        return var1;
    }
    
    protected NBTTagList func_96503_e() {
        final NBTTagList var1 = new NBTTagList();
        final Collection var2 = this.field_96507_a.func_96528_e();
        for (final Score var4 : var2) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.func_96653_e());
            var5.setString("Objective", var4.func_96645_d().getName());
            var5.setInteger("Score", var4.func_96652_c());
            var1.appendTag(var5);
        }
        return var1;
    }
}

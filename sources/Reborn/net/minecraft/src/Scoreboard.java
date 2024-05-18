package net.minecraft.src;

import java.util.*;

public class Scoreboard
{
    private final Map scoreObjectives;
    private final Map field_96543_b;
    private final Map field_96544_c;
    private final ScoreObjective[] field_96541_d;
    private final Map field_96542_e;
    private final Map teamMemberships;
    
    public Scoreboard() {
        this.scoreObjectives = new HashMap();
        this.field_96543_b = new HashMap();
        this.field_96544_c = new HashMap();
        this.field_96541_d = new ScoreObjective[3];
        this.field_96542_e = new HashMap();
        this.teamMemberships = new HashMap();
    }
    
    public ScoreObjective getObjective(final String par1Str) {
        return this.scoreObjectives.get(par1Str);
    }
    
    public ScoreObjective func_96535_a(final String par1Str, final ScoreObjectiveCriteria par2ScoreObjectiveCriteria) {
        ScoreObjective var3 = this.getObjective(par1Str);
        if (var3 != null) {
            throw new IllegalArgumentException("An objective with the name '" + par1Str + "' already exists!");
        }
        var3 = new ScoreObjective(this, par1Str, par2ScoreObjectiveCriteria);
        Object var4 = this.field_96543_b.get(par2ScoreObjectiveCriteria);
        if (var4 == null) {
            var4 = new ArrayList();
            this.field_96543_b.put(par2ScoreObjectiveCriteria, var4);
        }
        ((List)var4).add(var3);
        this.scoreObjectives.put(par1Str, var3);
        this.func_96522_a(var3);
        return var3;
    }
    
    public Collection func_96520_a(final ScoreObjectiveCriteria par1ScoreObjectiveCriteria) {
        final Collection var2 = this.field_96543_b.get(par1ScoreObjectiveCriteria);
        return (var2 == null) ? new ArrayList() : new ArrayList(var2);
    }
    
    public Score func_96529_a(final String par1Str, final ScoreObjective par2ScoreObjective) {
        Object var3 = this.field_96544_c.get(par1Str);
        if (var3 == null) {
            var3 = new HashMap();
            this.field_96544_c.put(par1Str, var3);
        }
        Score var4 = ((Map)var3).get(par2ScoreObjective);
        if (var4 == null) {
            var4 = new Score(this, par2ScoreObjective, par1Str);
            ((Map)var3).put(par2ScoreObjective, var4);
        }
        return var4;
    }
    
    public Collection func_96534_i(final ScoreObjective par1ScoreObjective) {
        final ArrayList var2 = new ArrayList();
        for (final Map var4 : this.field_96544_c.values()) {
            final Score var5 = var4.get(par1ScoreObjective);
            if (var5 != null) {
                var2.add(var5);
            }
        }
        Collections.sort((List<Object>)var2, Score.field_96658_a);
        return var2;
    }
    
    public Collection getScoreObjectives() {
        return this.scoreObjectives.values();
    }
    
    public Collection getObjectiveNames() {
        return this.field_96544_c.keySet();
    }
    
    public void func_96515_c(final String par1Str) {
        final Map var2 = this.field_96544_c.remove(par1Str);
        if (var2 != null) {
            this.func_96516_a(par1Str);
        }
    }
    
    public Collection func_96528_e() {
        final Collection var1 = this.field_96544_c.values();
        final ArrayList var2 = new ArrayList();
        if (var1 != null) {
            for (final Map var4 : var1) {
                var2.addAll(var4.values());
            }
        }
        return var2;
    }
    
    public Map func_96510_d(final String par1Str) {
        Object var2 = this.field_96544_c.get(par1Str);
        if (var2 == null) {
            var2 = new HashMap();
        }
        return (Map)var2;
    }
    
    public void func_96519_k(final ScoreObjective par1ScoreObjective) {
        this.scoreObjectives.remove(par1ScoreObjective.getName());
        for (int var2 = 0; var2 < 3; ++var2) {
            if (this.func_96539_a(var2) == par1ScoreObjective) {
                this.func_96530_a(var2, null);
            }
        }
        final List var3 = this.field_96543_b.get(par1ScoreObjective.getCriteria());
        if (var3 != null) {
            var3.remove(par1ScoreObjective);
        }
        for (final Map var5 : this.field_96544_c.values()) {
            var5.remove(par1ScoreObjective);
        }
        this.func_96533_c(par1ScoreObjective);
    }
    
    public void func_96530_a(final int par1, final ScoreObjective par2ScoreObjective) {
        this.field_96541_d[par1] = par2ScoreObjective;
    }
    
    public ScoreObjective func_96539_a(final int par1) {
        return this.field_96541_d[par1];
    }
    
    public ScorePlayerTeam func_96508_e(final String par1Str) {
        return this.field_96542_e.get(par1Str);
    }
    
    public ScorePlayerTeam func_96527_f(final String par1Str) {
        ScorePlayerTeam var2 = this.func_96508_e(par1Str);
        if (var2 != null) {
            throw new IllegalArgumentException("An objective with the name '" + par1Str + "' already exists!");
        }
        var2 = new ScorePlayerTeam(this, par1Str);
        this.field_96542_e.put(par1Str, var2);
        this.func_96523_a(var2);
        return var2;
    }
    
    public void func_96511_d(final ScorePlayerTeam par1ScorePlayerTeam) {
        this.field_96542_e.remove(par1ScorePlayerTeam.func_96661_b());
        for (final String var3 : par1ScorePlayerTeam.getMembershipCollection()) {
            this.teamMemberships.remove(var3);
        }
        this.func_96513_c(par1ScorePlayerTeam);
    }
    
    public void func_96521_a(final String par1Str, final ScorePlayerTeam par2ScorePlayerTeam) {
        if (this.getPlayersTeam(par1Str) != null) {
            this.func_96524_g(par1Str);
        }
        this.teamMemberships.put(par1Str, par2ScorePlayerTeam);
        par2ScorePlayerTeam.getMembershipCollection().add(par1Str);
    }
    
    public boolean func_96524_g(final String par1Str) {
        final ScorePlayerTeam var2 = this.getPlayersTeam(par1Str);
        if (var2 != null) {
            this.removePlayerFromTeam(par1Str, var2);
            return true;
        }
        return false;
    }
    
    public void removePlayerFromTeam(final String par1Str, final ScorePlayerTeam par2ScorePlayerTeam) {
        if (this.getPlayersTeam(par1Str) != par2ScorePlayerTeam) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + par2ScorePlayerTeam.func_96661_b() + "'.");
        }
        this.teamMemberships.remove(par1Str);
        par2ScorePlayerTeam.getMembershipCollection().remove(par1Str);
    }
    
    public Collection func_96531_f() {
        return this.field_96542_e.keySet();
    }
    
    public Collection func_96525_g() {
        return this.field_96542_e.values();
    }
    
    public ScorePlayerTeam getPlayersTeam(final String par1Str) {
        return this.teamMemberships.get(par1Str);
    }
    
    public void func_96522_a(final ScoreObjective par1ScoreObjective) {
    }
    
    public void func_96532_b(final ScoreObjective par1ScoreObjective) {
    }
    
    public void func_96533_c(final ScoreObjective par1ScoreObjective) {
    }
    
    public void func_96536_a(final Score par1Score) {
    }
    
    public void func_96516_a(final String par1Str) {
    }
    
    public void func_96523_a(final ScorePlayerTeam par1ScorePlayerTeam) {
    }
    
    public void func_96538_b(final ScorePlayerTeam par1ScorePlayerTeam) {
    }
    
    public void func_96513_c(final ScorePlayerTeam par1ScorePlayerTeam) {
    }
    
    public static String getObjectiveDisplaySlot(final int par0) {
        switch (par0) {
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
                return null;
            }
        }
    }
    
    public static int getObjectiveDisplaySlotNumber(final String par0Str) {
        return par0Str.equalsIgnoreCase("list") ? 0 : (par0Str.equalsIgnoreCase("sidebar") ? 1 : (par0Str.equalsIgnoreCase("belowName") ? 2 : -1));
    }
}

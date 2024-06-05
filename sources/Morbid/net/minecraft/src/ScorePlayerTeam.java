package net.minecraft.src;

import java.util.*;

public class ScorePlayerTeam
{
    private final Scoreboard theScoreboard;
    private final String field_96675_b;
    private final Set membershipSet;
    private String field_96673_d;
    private String field_96674_e;
    private String field_96671_f;
    private boolean field_96672_g;
    private boolean field_98301_h;
    
    public ScorePlayerTeam(final Scoreboard par1Scoreboard, final String par2Str) {
        this.membershipSet = new HashSet();
        this.field_96674_e = "";
        this.field_96671_f = "";
        this.field_96672_g = true;
        this.field_98301_h = true;
        this.theScoreboard = par1Scoreboard;
        this.field_96675_b = par2Str;
        this.field_96673_d = par2Str;
    }
    
    public String func_96661_b() {
        return this.field_96675_b;
    }
    
    public String func_96669_c() {
        return this.field_96673_d;
    }
    
    public void func_96664_a(final String par1Str) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.field_96673_d = par1Str;
        this.theScoreboard.func_96538_b(this);
    }
    
    public Collection getMembershipCollection() {
        return this.membershipSet;
    }
    
    public String func_96668_e() {
        return this.field_96674_e;
    }
    
    public void func_96666_b(final String par1Str) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.field_96674_e = par1Str;
        this.theScoreboard.func_96538_b(this);
    }
    
    public String func_96663_f() {
        return this.field_96671_f;
    }
    
    public void func_96662_c(final String par1Str) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Suffix cannot be null");
        }
        this.field_96671_f = par1Str;
        this.theScoreboard.func_96538_b(this);
    }
    
    public static String func_96667_a(final ScorePlayerTeam par0ScorePlayerTeam, final String par1Str) {
        return (par0ScorePlayerTeam == null) ? par1Str : (String.valueOf(par0ScorePlayerTeam.func_96668_e()) + par1Str + par0ScorePlayerTeam.func_96663_f());
    }
    
    public boolean func_96665_g() {
        return this.field_96672_g;
    }
    
    public void func_96660_a(final boolean par1) {
        this.field_96672_g = par1;
        this.theScoreboard.func_96538_b(this);
    }
    
    public boolean func_98297_h() {
        return this.field_98301_h;
    }
    
    public void func_98300_b(final boolean par1) {
        this.field_98301_h = par1;
        this.theScoreboard.func_96538_b(this);
    }
    
    public int func_98299_i() {
        int var1 = 0;
        int var2 = 0;
        if (this.func_96665_g()) {
            var1 |= 1 << var2++;
        }
        if (this.func_98297_h()) {
            var1 |= 1 << var2++;
        }
        return var1;
    }
    
    public void func_98298_a(final int par1) {
        final byte var2 = 0;
        int var3 = var2 + 1;
        this.func_96660_a((par1 & 1 << var2) > 0);
        this.func_98300_b((par1 & 1 << var3++) > 0);
    }
}

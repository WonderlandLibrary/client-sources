package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import com.google.common.collect.Sets;
import java.util.Set;

public class ScorePlayerTeam extends Team
{
    private final Scoreboard HorizonCode_Horizon_È;
    private final String Â;
    private final Set Ý;
    private String Ø­áŒŠá;
    private String Âµá€;
    private String Ó;
    private boolean à;
    private boolean Ø;
    private HorizonCode_Horizon_È áŒŠÆ;
    private HorizonCode_Horizon_È áˆºÑ¢Õ;
    private EnumChatFormatting ÂµÈ;
    private static final String á = "CL_00000616";
    
    public ScorePlayerTeam(final Scoreboard p_i2308_1_, final String p_i2308_2_) {
        this.Ý = Sets.newHashSet();
        this.Âµá€ = "";
        this.Ó = "";
        this.à = true;
        this.Ø = true;
        this.áŒŠÆ = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.áˆºÑ¢Õ = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.ÂµÈ = EnumChatFormatting.Æ;
        this.HorizonCode_Horizon_È = p_i2308_1_;
        this.Â = p_i2308_2_;
        this.Ø­áŒŠá = p_i2308_2_;
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public String Â() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final String p_96664_1_) {
        if (p_96664_1_ == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.Ø­áŒŠá = p_96664_1_;
        this.HorizonCode_Horizon_È.Ý(this);
    }
    
    @Override
    public Collection Ý() {
        return this.Ý;
    }
    
    public String Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public void Â(final String p_96666_1_) {
        if (p_96666_1_ == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.Âµá€ = p_96666_1_;
        this.HorizonCode_Horizon_È.Ý(this);
    }
    
    public String Âµá€() {
        return this.Ó;
    }
    
    public void Ý(final String p_96662_1_) {
        this.Ó = p_96662_1_;
        this.HorizonCode_Horizon_È.Ý(this);
    }
    
    @Override
    public String Ø­áŒŠá(final String input) {
        return String.valueOf(this.Ø­áŒŠá()) + input + this.Âµá€();
    }
    
    public static String HorizonCode_Horizon_È(final Team p_96667_0_, final String p_96667_1_) {
        return (p_96667_0_ == null) ? p_96667_1_ : p_96667_0_.Ø­áŒŠá(p_96667_1_);
    }
    
    @Override
    public boolean Ó() {
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_96660_1_) {
        this.à = p_96660_1_;
        this.HorizonCode_Horizon_È.Ý(this);
    }
    
    @Override
    public boolean à() {
        return this.Ø;
    }
    
    public void Â(final boolean p_98300_1_) {
        this.Ø = p_98300_1_;
        this.HorizonCode_Horizon_È.Ý(this);
    }
    
    @Override
    public HorizonCode_Horizon_È Ø() {
        return this.áŒŠÆ;
    }
    
    @Override
    public HorizonCode_Horizon_È áŒŠÆ() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_178772_1_) {
        this.áŒŠÆ = p_178772_1_;
        this.HorizonCode_Horizon_È.Ý(this);
    }
    
    public void Â(final HorizonCode_Horizon_È p_178773_1_) {
        this.áˆºÑ¢Õ = p_178773_1_;
        this.HorizonCode_Horizon_È.Ý(this);
    }
    
    public int áˆºÑ¢Õ() {
        int var1 = 0;
        if (this.Ó()) {
            var1 |= 0x1;
        }
        if (this.à()) {
            var1 |= 0x2;
        }
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final int p_98298_1_) {
        this.HorizonCode_Horizon_È((p_98298_1_ & 0x1) > 0);
        this.Â((p_98298_1_ & 0x2) > 0);
    }
    
    public void HorizonCode_Horizon_È(final EnumChatFormatting p_178774_1_) {
        this.ÂµÈ = p_178774_1_;
    }
    
    public EnumChatFormatting ÂµÈ() {
        return this.ÂµÈ;
    }
}

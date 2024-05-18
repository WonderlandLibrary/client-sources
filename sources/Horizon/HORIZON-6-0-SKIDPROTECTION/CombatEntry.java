package HORIZON-6-0-SKIDPROTECTION;

public class CombatEntry
{
    private final DamageSource HorizonCode_Horizon_È;
    private final int Â;
    private final float Ý;
    private final float Ø­áŒŠá;
    private final String Âµá€;
    private final float Ó;
    private static final String à = "CL_00001519";
    
    public CombatEntry(final DamageSource p_i1564_1_, final int p_i1564_2_, final float p_i1564_3_, final float p_i1564_4_, final String p_i1564_5_, final float p_i1564_6_) {
        this.HorizonCode_Horizon_È = p_i1564_1_;
        this.Â = p_i1564_2_;
        this.Ý = p_i1564_4_;
        this.Ø­áŒŠá = p_i1564_3_;
        this.Âµá€ = p_i1564_5_;
        this.Ó = p_i1564_6_;
    }
    
    public DamageSource HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public float Â() {
        return this.Ý;
    }
    
    public boolean Ý() {
        return this.HorizonCode_Horizon_È.áˆºÑ¢Õ() instanceof EntityLivingBase;
    }
    
    public String Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public IChatComponent Âµá€() {
        return (this.HorizonCode_Horizon_È().áˆºÑ¢Õ() == null) ? null : this.HorizonCode_Horizon_È().áˆºÑ¢Õ().Ý();
    }
    
    public float Ó() {
        return (this.HorizonCode_Horizon_È == DamageSource.áˆºÑ¢Õ) ? Float.MAX_VALUE : this.Ó;
    }
}

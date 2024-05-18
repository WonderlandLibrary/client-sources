package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIVillagerMate extends EntityAIBase
{
    private EntityVillager Â;
    private EntityVillager Ý;
    private World Ø­áŒŠá;
    private int Âµá€;
    Village HorizonCode_Horizon_È;
    private static final String Ó = "CL_00001594";
    
    public EntityAIVillagerMate(final EntityVillager p_i1634_1_) {
        this.Â = p_i1634_1_;
        this.Ø­áŒŠá = p_i1634_1_.Ï­Ðƒà;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.Â.à() != 0) {
            return false;
        }
        if (this.Â.ˆÐƒØ().nextInt(500) != 0) {
            return false;
        }
        this.HorizonCode_Horizon_È = this.Ø­áŒŠá.È().HorizonCode_Horizon_È(new BlockPos(this.Â), 0);
        if (this.HorizonCode_Horizon_È == null) {
            return false;
        }
        if (!this.Ø() || !this.Â.£á(true)) {
            return false;
        }
        final Entity var1 = this.Ø­áŒŠá.HorizonCode_Horizon_È(EntityVillager.class, this.Â.£É().Â(8.0, 3.0, 8.0), this.Â);
        if (var1 == null) {
            return false;
        }
        this.Ý = (EntityVillager)var1;
        return this.Ý.à() == 0 && this.Ý.£á(true);
    }
    
    @Override
    public void Âµá€() {
        this.Âµá€ = 300;
        this.Â.á(true);
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È = null;
        this.Ý = null;
        this.Â.á(false);
    }
    
    @Override
    public boolean Â() {
        return this.Âµá€ >= 0 && this.Ø() && this.Â.à() == 0 && this.Â.£á(false);
    }
    
    @Override
    public void Ø­áŒŠá() {
        --this.Âµá€;
        this.Â.Ñ¢á().HorizonCode_Horizon_È(this.Ý, 10.0f, 30.0f);
        if (this.Â.Âµá€(this.Ý) > 2.25) {
            this.Â.Š().HorizonCode_Horizon_È(this.Ý, 0.25);
        }
        else if (this.Âµá€ == 0 && this.Ý.ÇŽÅ()) {
            this.áŒŠÆ();
        }
        if (this.Â.ˆÐƒØ().nextInt(35) == 0) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â, (byte)12);
        }
    }
    
    private boolean Ø() {
        if (!this.HorizonCode_Horizon_È.áŒŠÆ()) {
            return false;
        }
        final int var1 = (int)(this.HorizonCode_Horizon_È.Ý() * 0.35);
        return this.HorizonCode_Horizon_È.Âµá€() < var1;
    }
    
    private void áŒŠÆ() {
        final EntityVillager var1 = this.Â.Â(this.Ý);
        this.Ý.Â(6000);
        this.Â.Â(6000);
        this.Ý.Å(false);
        this.Â.Å(false);
        var1.Â(-24000);
        var1.Â(this.Â.ŒÏ, this.Â.Çªà¢, this.Â.Ê, 0.0f, 0.0f);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(var1);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(var1, (byte)12);
    }
}

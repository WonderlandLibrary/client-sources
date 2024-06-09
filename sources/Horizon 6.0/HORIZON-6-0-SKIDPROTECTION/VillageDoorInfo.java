package HORIZON-6-0-SKIDPROTECTION;

public class VillageDoorInfo
{
    private final BlockPos HorizonCode_Horizon_È;
    private final BlockPos Â;
    private final EnumFacing Ý;
    private int Ø­áŒŠá;
    private boolean Âµá€;
    private int Ó;
    private static final String à = "CL_00001630";
    
    public VillageDoorInfo(final BlockPos p_i45871_1_, final int p_i45871_2_, final int p_i45871_3_, final int p_i45871_4_) {
        this(p_i45871_1_, HorizonCode_Horizon_È(p_i45871_2_, p_i45871_3_), p_i45871_4_);
    }
    
    private static EnumFacing HorizonCode_Horizon_È(final int p_179854_0_, final int p_179854_1_) {
        return (p_179854_0_ < 0) ? EnumFacing.Âµá€ : ((p_179854_0_ > 0) ? EnumFacing.Ó : ((p_179854_1_ < 0) ? EnumFacing.Ý : EnumFacing.Ø­áŒŠá));
    }
    
    public VillageDoorInfo(final BlockPos p_i45872_1_, final EnumFacing p_i45872_2_, final int p_i45872_3_) {
        this.HorizonCode_Horizon_È = p_i45872_1_;
        this.Ý = p_i45872_2_;
        this.Â = p_i45872_1_.HorizonCode_Horizon_È(p_i45872_2_, 2);
        this.Ø­áŒŠá = p_i45872_3_;
    }
    
    public int HorizonCode_Horizon_È(final int p_75474_1_, final int p_75474_2_, final int p_75474_3_) {
        return (int)this.HorizonCode_Horizon_È.Ý(p_75474_1_, p_75474_2_, p_75474_3_);
    }
    
    public int HorizonCode_Horizon_È(final BlockPos p_179848_1_) {
        return (int)p_179848_1_.Ó(this.Ø­áŒŠá());
    }
    
    public int Â(final BlockPos p_179846_1_) {
        return (int)this.Â.Ó(p_179846_1_);
    }
    
    public boolean Ý(final BlockPos p_179850_1_) {
        final int var2 = p_179850_1_.HorizonCode_Horizon_È() - this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        final int var3 = p_179850_1_.Ý() - this.HorizonCode_Horizon_È.Â();
        return var2 * this.Ý.Ø() + var3 * this.Ý.áˆºÑ¢Õ() >= 0;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ó = 0;
    }
    
    public void Â() {
        ++this.Ó;
    }
    
    public int Ý() {
        return this.Ó;
    }
    
    public BlockPos Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    public BlockPos Âµá€() {
        return this.Â;
    }
    
    public int Ó() {
        return this.Ý.Ø() * 2;
    }
    
    public int à() {
        return this.Ý.áˆºÑ¢Õ() * 2;
    }
    
    public int Ø() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final int p_179849_1_) {
        this.Ø­áŒŠá = p_179849_1_;
    }
    
    public boolean áŒŠÆ() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_179853_1_) {
        this.Âµá€ = p_179853_1_;
    }
}

package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIMoveIndoors extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private VillageDoorInfo Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001596";
    
    public EntityAIMoveIndoors(final EntityCreature p_i1637_1_) {
        this.Ý = -1;
        this.Ø­áŒŠá = -1;
        this.HorizonCode_Horizon_È = p_i1637_1_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final BlockPos var1 = new BlockPos(this.HorizonCode_Horizon_È);
        if ((this.HorizonCode_Horizon_È.Ï­Ðƒà.ÂµÈ() && (!this.HorizonCode_Horizon_È.Ï­Ðƒà.ˆá() || this.HorizonCode_Horizon_È.Ï­Ðƒà.Ý(var1).Âµá€())) || this.HorizonCode_Horizon_È.Ï­Ðƒà.£à.Å()) {
            return false;
        }
        if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(50) != 0) {
            return false;
        }
        if (this.Ý != -1 && this.HorizonCode_Horizon_È.Âµá€(this.Ý, this.HorizonCode_Horizon_È.Çªà¢, this.Ø­áŒŠá) < 4.0) {
            return false;
        }
        final Village var2 = this.HorizonCode_Horizon_È.Ï­Ðƒà.È().HorizonCode_Horizon_È(var1, 14);
        if (var2 == null) {
            return false;
        }
        this.Â = var2.Ý(var1);
        return this.Â != null;
    }
    
    @Override
    public boolean Â() {
        return !this.HorizonCode_Horizon_È.Š().Ó();
    }
    
    @Override
    public void Âµá€() {
        this.Ý = -1;
        final BlockPos var1 = this.Â.Âµá€();
        final int var2 = var1.HorizonCode_Horizon_È();
        final int var3 = var1.Â();
        final int var4 = var1.Ý();
        if (this.HorizonCode_Horizon_È.Â(var1) > 256.0) {
            final Vec3 var5 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 14, 3, new Vec3(var2 + 0.5, var3, var4 + 0.5));
            if (var5 != null) {
                this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È, var5.Â, var5.Ý, 1.0);
            }
        }
        else {
            this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(var2 + 0.5, var3, var4 + 0.5, 1.0);
        }
    }
    
    @Override
    public void Ý() {
        this.Ý = this.Â.Âµá€().HorizonCode_Horizon_È();
        this.Ø­áŒŠá = this.Â.Âµá€().Ý();
        this.Â = null;
    }
}

package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
    protected EntityLiving HorizonCode_Horizon_È;
    protected BlockPos Â;
    protected BlockDoor Ý;
    boolean Ø­áŒŠá;
    float Âµá€;
    float Ó;
    private static final String à = "CL_00001581";
    
    public EntityAIDoorInteract(final EntityLiving p_i1621_1_) {
        this.Â = BlockPos.HorizonCode_Horizon_È;
        this.HorizonCode_Horizon_È = p_i1621_1_;
        if (!(p_i1621_1_.Š() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.HorizonCode_Horizon_È.¥à) {
            return false;
        }
        final PathNavigateGround var1 = (PathNavigateGround)this.HorizonCode_Horizon_È.Š();
        final PathEntity var2 = var1.Ý();
        if (var2 != null && !var2.Â() && var1.ˆÏ­()) {
            for (int var3 = 0; var3 < Math.min(var2.Âµá€() + 2, var2.Ø­áŒŠá()); ++var3) {
                final PathPoint var4 = var2.HorizonCode_Horizon_È(var3);
                this.Â = new BlockPos(var4.HorizonCode_Horizon_È, var4.Â + 1, var4.Ý);
                if (this.HorizonCode_Horizon_È.Âµá€(this.Â.HorizonCode_Horizon_È(), this.HorizonCode_Horizon_È.Çªà¢, this.Â.Ý()) <= 2.25) {
                    this.Ý = this.HorizonCode_Horizon_È(this.Â);
                    if (this.Ý != null) {
                        return true;
                    }
                }
            }
            this.Â = new BlockPos(this.HorizonCode_Horizon_È).Ø­áŒŠá();
            this.Ý = this.HorizonCode_Horizon_È(this.Â);
            return this.Ý != null;
        }
        return false;
    }
    
    @Override
    public boolean Â() {
        return !this.Ø­áŒŠá;
    }
    
    @Override
    public void Âµá€() {
        this.Ø­áŒŠá = false;
        this.Âµá€ = (float)(this.Â.HorizonCode_Horizon_È() + 0.5f - this.HorizonCode_Horizon_È.ŒÏ);
        this.Ó = (float)(this.Â.Ý() + 0.5f - this.HorizonCode_Horizon_È.Ê);
    }
    
    @Override
    public void Ø­áŒŠá() {
        final float var1 = (float)(this.Â.HorizonCode_Horizon_È() + 0.5f - this.HorizonCode_Horizon_È.ŒÏ);
        final float var2 = (float)(this.Â.Ý() + 0.5f - this.HorizonCode_Horizon_È.Ê);
        final float var3 = this.Âµá€ * var1 + this.Ó * var2;
        if (var3 < 0.0f) {
            this.Ø­áŒŠá = true;
        }
    }
    
    private BlockDoor HorizonCode_Horizon_È(final BlockPos p_179506_1_) {
        final Block var2 = this.HorizonCode_Horizon_È.Ï­Ðƒà.Â(p_179506_1_).Ý();
        return (var2 instanceof BlockDoor && var2.Ó() == Material.Ø­áŒŠá) ? ((BlockDoor)var2) : null;
    }
}

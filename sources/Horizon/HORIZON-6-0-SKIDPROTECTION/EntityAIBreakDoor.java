package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
    private int à;
    private int Ø;
    private static final String áŒŠÆ = "CL_00001577";
    
    public EntityAIBreakDoor(final EntityLiving p_i1618_1_) {
        super(p_i1618_1_);
        this.Ø = -1;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!super.HorizonCode_Horizon_È()) {
            return false;
        }
        if (!this.HorizonCode_Horizon_È.Ï­Ðƒà.Çªà¢().Â("mobGriefing")) {
            return false;
        }
        final BlockDoor var10000 = this.Ý;
        return !BlockDoor.Ó((IBlockAccess)this.HorizonCode_Horizon_È.Ï­Ðƒà, this.Â);
    }
    
    @Override
    public void Âµá€() {
        super.Âµá€();
        this.à = 0;
    }
    
    @Override
    public boolean Â() {
        final double var1 = this.HorizonCode_Horizon_È.Â(this.Â);
        if (this.à <= 240) {
            final BlockDoor var2 = this.Ý;
            if (!BlockDoor.Ó((IBlockAccess)this.HorizonCode_Horizon_È.Ï­Ðƒà, this.Â) && var1 < 4.0) {
                final boolean var3 = true;
                return var3;
            }
        }
        final boolean var3 = false;
        return var3;
    }
    
    @Override
    public void Ý() {
        super.Ý();
        this.HorizonCode_Horizon_È.Ï­Ðƒà.Ý(this.HorizonCode_Horizon_È.ˆá(), this.Â, -1);
    }
    
    @Override
    public void Ø­áŒŠá() {
        super.Ø­áŒŠá();
        if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(20) == 0) {
            this.HorizonCode_Horizon_È.Ï­Ðƒà.Â(1010, this.Â, 0);
        }
        ++this.à;
        final int var1 = (int)(this.à / 240.0f * 10.0f);
        if (var1 != this.Ø) {
            this.HorizonCode_Horizon_È.Ï­Ðƒà.Ý(this.HorizonCode_Horizon_È.ˆá(), this.Â, var1);
            this.Ø = var1;
        }
        if (this.à == 240 && this.HorizonCode_Horizon_È.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) {
            this.HorizonCode_Horizon_È.Ï­Ðƒà.Ø(this.Â);
            this.HorizonCode_Horizon_È.Ï­Ðƒà.Â(1012, this.Â, 0);
            this.HorizonCode_Horizon_È.Ï­Ðƒà.Â(2001, this.Â, Block.HorizonCode_Horizon_È(this.Ý));
        }
    }
}

package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityEnderChest extends TileEntity implements IUpdatePlayerListBox
{
    public float Âµá€;
    public float Ó;
    public int à;
    private int Ø;
    private static final String áŒŠÆ = "CL_00000355";
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (++this.Ø % 20 * 4 == 0) {
            this.HorizonCode_Horizon_È.Ý(this.Â, Blocks.¥áŒŠà, 1, this.à);
        }
        this.Ó = this.Âµá€;
        final int var1 = this.Â.HorizonCode_Horizon_È();
        final int var2 = this.Â.Â();
        final int var3 = this.Â.Ý();
        final float var4 = 0.1f;
        if (this.à > 0 && this.Âµá€ == 0.0f) {
            final double var5 = var1 + 0.5;
            final double var6 = var3 + 0.5;
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5, var2 + 0.5, var6, "random.chestopen", 0.5f, this.HorizonCode_Horizon_È.Å.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.à == 0 && this.Âµá€ > 0.0f) || (this.à > 0 && this.Âµá€ < 1.0f)) {
            final float var7 = this.Âµá€;
            if (this.à > 0) {
                this.Âµá€ += var4;
            }
            else {
                this.Âµá€ -= var4;
            }
            if (this.Âµá€ > 1.0f) {
                this.Âµá€ = 1.0f;
            }
            final float var8 = 0.5f;
            if (this.Âµá€ < var8 && var7 >= var8) {
                final double var6 = var1 + 0.5;
                final double var9 = var3 + 0.5;
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6, var2 + 0.5, var9, "random.chestclosed", 0.5f, this.HorizonCode_Horizon_È.Å.nextFloat() * 0.1f + 0.9f);
            }
            if (this.Âµá€ < 0.0f) {
                this.Âµá€ = 0.0f;
            }
        }
    }
    
    @Override
    public boolean Ý(final int id, final int type) {
        if (id == 1) {
            this.à = type;
            return true;
        }
        return super.Ý(id, type);
    }
    
    @Override
    public void £à() {
        this.ˆà();
        super.£à();
    }
    
    public void Â() {
        ++this.à;
        this.HorizonCode_Horizon_È.Ý(this.Â, Blocks.¥áŒŠà, 1, this.à);
    }
    
    public void Ý() {
        --this.à;
        this.HorizonCode_Horizon_È.Ý(this.Â, Blocks.¥áŒŠà, 1, this.à);
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_145971_1_) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â) == this && p_145971_1_.Âµá€(this.Â.HorizonCode_Horizon_È() + 0.5, this.Â.Â() + 0.5, this.Â.Ý() + 0.5) <= 64.0;
    }
}

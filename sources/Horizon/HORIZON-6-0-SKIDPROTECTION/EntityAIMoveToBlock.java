package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityAIMoveToBlock extends EntityAIBase
{
    private final EntityCreature Ý;
    private final double Ø­áŒŠá;
    protected int HorizonCode_Horizon_È;
    private int Âµá€;
    private int Ó;
    protected BlockPos Â;
    private boolean à;
    private int Ø;
    private static final String áŒŠÆ = "CL_00002252";
    
    public EntityAIMoveToBlock(final EntityCreature p_i45888_1_, final double p_i45888_2_, final int p_i45888_4_) {
        this.Â = BlockPos.HorizonCode_Horizon_È;
        this.Ý = p_i45888_1_;
        this.Ø­áŒŠá = p_i45888_2_;
        this.Ø = p_i45888_4_;
        this.HorizonCode_Horizon_È(5);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È > 0) {
            --this.HorizonCode_Horizon_È;
            return false;
        }
        this.HorizonCode_Horizon_È = 200 + this.Ý.ˆÐƒØ().nextInt(200);
        return this.áŒŠÆ();
    }
    
    @Override
    public boolean Â() {
        return this.Âµá€ >= -this.Ó && this.Âµá€ <= 1200 && this.HorizonCode_Horizon_È(this.Ý.Ï­Ðƒà, this.Â);
    }
    
    @Override
    public void Âµá€() {
        this.Ý.Š().HorizonCode_Horizon_È(this.Â.HorizonCode_Horizon_È() + 0.5, this.Â.Â() + 1, this.Â.Ý() + 0.5, this.Ø­áŒŠá);
        this.Âµá€ = 0;
        this.Ó = this.Ý.ˆÐƒØ().nextInt(this.Ý.ˆÐƒØ().nextInt(1200) + 1200) + 1200;
    }
    
    @Override
    public void Ý() {
    }
    
    @Override
    public void Ø­áŒŠá() {
        if (this.Ý.Ý(this.Â.Ø­áŒŠá()) > 1.0) {
            this.à = false;
            ++this.Âµá€;
            if (this.Âµá€ % 40 == 0) {
                this.Ý.Š().HorizonCode_Horizon_È(this.Â.HorizonCode_Horizon_È() + 0.5, this.Â.Â() + 1, this.Â.Ý() + 0.5, this.Ø­áŒŠá);
            }
        }
        else {
            this.à = true;
            --this.Âµá€;
        }
    }
    
    protected boolean Ø() {
        return this.à;
    }
    
    private boolean áŒŠÆ() {
        final int var1 = this.Ø;
        final boolean var2 = true;
        final BlockPos var3 = new BlockPos(this.Ý);
        for (int var4 = 0; var4 <= 1; var4 = ((var4 > 0) ? (-var4) : (1 - var4))) {
            for (int var5 = 0; var5 < var1; ++var5) {
                for (int var6 = 0; var6 <= var5; var6 = ((var6 > 0) ? (-var6) : (1 - var6))) {
                    for (int var7 = (var6 < var5 && var6 > -var5) ? var5 : 0; var7 <= var5; var7 = ((var7 > 0) ? (-var7) : (1 - var7))) {
                        final BlockPos var8 = var3.Â(var6, var4 - 1, var7);
                        if (this.Ý.Ø­áŒŠá(var8) && this.HorizonCode_Horizon_È(this.Ý.Ï­Ðƒà, var8)) {
                            this.Â = var8;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    protected abstract boolean HorizonCode_Horizon_È(final World p0, final BlockPos p1);
}

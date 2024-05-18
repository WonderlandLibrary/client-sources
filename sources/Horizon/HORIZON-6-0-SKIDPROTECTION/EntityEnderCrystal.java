package HORIZON-6-0-SKIDPROTECTION;

public class EntityEnderCrystal extends Entity
{
    public int HorizonCode_Horizon_È;
    public int Â;
    private static final String Ý = "CL_00001658";
    
    public EntityEnderCrystal(final World worldIn) {
        super(worldIn);
        this.Ø­à = true;
        this.HorizonCode_Horizon_È(2.0f, 2.0f);
        this.Â = 5;
        this.HorizonCode_Horizon_È = this.ˆáƒ.nextInt(100000);
    }
    
    public EntityEnderCrystal(final World worldIn, final double p_i1699_2_, final double p_i1699_4_, final double p_i1699_6_) {
        this(worldIn);
        this.Ý(p_i1699_2_, p_i1699_4_, p_i1699_6_);
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected void ÂµÈ() {
        this.£Ó.HorizonCode_Horizon_È(8, (Object)this.Â);
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        ++this.HorizonCode_Horizon_È;
        this.£Ó.Â(8, this.Â);
        final int var1 = MathHelper.Ý(this.ŒÏ);
        final int var2 = MathHelper.Ý(this.Çªà¢);
        final int var3 = MathHelper.Ý(this.Ê);
        if (this.Ï­Ðƒà.£à instanceof WorldProviderEnd && this.Ï­Ðƒà.Â(new BlockPos(var1, var2, var3)).Ý() != Blocks.Ô) {
            this.Ï­Ðƒà.Â(new BlockPos(var1, var2, var3), Blocks.Ô.¥à());
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
    }
    
    @Override
    public boolean Ô() {
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (!this.ˆáŠ && !this.Ï­Ðƒà.ŠÄ) {
            this.Â = 0;
            if (this.Â <= 0) {
                this.á€();
                if (!this.Ï­Ðƒà.ŠÄ) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(null, this.ŒÏ, this.Çªà¢, this.Ê, 6.0f, true);
                }
            }
        }
        return true;
    }
}

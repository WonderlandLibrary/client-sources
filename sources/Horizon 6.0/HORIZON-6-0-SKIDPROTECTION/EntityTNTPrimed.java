package HORIZON-6-0-SKIDPROTECTION;

public class EntityTNTPrimed extends Entity
{
    public int HorizonCode_Horizon_È;
    private EntityLivingBase Â;
    private static final String Ý = "CL_00001681";
    
    public EntityTNTPrimed(final World worldIn) {
        super(worldIn);
        this.Ø­à = true;
        this.HorizonCode_Horizon_È(0.98f, 0.98f);
    }
    
    public EntityTNTPrimed(final World worldIn, final double p_i1730_2_, final double p_i1730_4_, final double p_i1730_6_, final EntityLivingBase p_i1730_8_) {
        this(worldIn);
        this.Ý(p_i1730_2_, p_i1730_4_, p_i1730_6_);
        final float var9 = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.ÇŽÉ = -(float)Math.sin(var9) * 0.02f;
        this.ˆá = 0.20000000298023224;
        this.ÇŽÕ = -(float)Math.cos(var9) * 0.02f;
        this.HorizonCode_Horizon_È = 80;
        this.áŒŠà = p_i1730_2_;
        this.ŠÄ = p_i1730_4_;
        this.Ñ¢á = p_i1730_6_;
        this.Â = p_i1730_8_;
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Ô() {
        return !this.ˆáŠ;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.ˆá -= 0.03999999910593033;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9800000190734863;
        this.ˆá *= 0.9800000190734863;
        this.ÇŽÕ *= 0.9800000190734863;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
            this.ˆá *= -0.5;
        }
        if (this.HorizonCode_Horizon_È-- <= 0) {
            this.á€();
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.Ø();
            }
        }
        else {
            this.Ø­Âµ();
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.á, this.ŒÏ, this.Çªà¢ + 0.5, this.Ê, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    private void Ø() {
        final float var1 = 4.0f;
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.ŒÏ, this.Çªà¢ + this.£ÂµÄ / 2.0f, this.Ê, var1, true);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("Fuse", (byte)this.HorizonCode_Horizon_È);
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        this.HorizonCode_Horizon_È = tagCompund.Ø­áŒŠá("Fuse");
    }
    
    public EntityLivingBase à() {
        return this.Â;
    }
    
    @Override
    public float Ðƒáƒ() {
        return 0.0f;
    }
}

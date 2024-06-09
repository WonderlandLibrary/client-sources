package HORIZON-6-0-SKIDPROTECTION;

public class EntitySquid extends EntityWaterMob
{
    public float HorizonCode_Horizon_È;
    public float Â;
    public float Ý;
    public float Ø­áŒŠá;
    public float Âµá€;
    public float Ø­Ñ¢Ï­Ø­áˆº;
    public float ŒÂ;
    public float Ï­Ï;
    private float ŠØ;
    private float ˆÐƒØ;
    private float Çªà;
    private float ¥Å;
    private float Œáƒ;
    private float Œá;
    private static final String µÂ = "CL_00001651";
    
    public EntitySquid(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.95f, 0.95f);
        this.ˆáƒ.setSeed(1 + this.ˆá());
        this.ˆÐƒØ = 1.0f / (this.ˆáƒ.nextFloat() + 1.0f) * 0.2f;
        this.ÂµÈ.HorizonCode_Horizon_È(0, new HorizonCode_Horizon_È());
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(10.0);
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.£ÂµÄ * 0.5f;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return null;
    }
    
    @Override
    protected String ¥áŠ() {
        return null;
    }
    
    @Override
    protected String µÊ() {
        return null;
    }
    
    @Override
    protected float ˆÂ() {
        return 0.4f;
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return null;
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(3 + p_70628_2_) + 1, var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(new ItemStack(Items.áŒŠÔ, 1, EnumDyeColor.£à.Ý()), 0.0f);
        }
    }
    
    @Override
    public boolean £ÂµÄ() {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É().Â(0.0, -0.6000000238418579, 0.0), Material.Ø, this);
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        this.Â = this.HorizonCode_Horizon_È;
        this.Ø­áŒŠá = this.Ý;
        this.Ø­Ñ¢Ï­Ø­áˆº = this.Âµá€;
        this.Ï­Ï = this.ŒÂ;
        this.Âµá€ += this.ˆÐƒØ;
        if (this.Âµá€ > 6.283185307179586) {
            if (this.Ï­Ðƒà.ŠÄ) {
                this.Âµá€ = 6.2831855f;
            }
            else {
                this.Âµá€ -= 6.283185307179586;
                if (this.ˆáƒ.nextInt(10) == 0) {
                    this.ˆÐƒØ = 1.0f / (this.ˆáƒ.nextFloat() + 1.0f) * 0.2f;
                }
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)19);
            }
        }
        if (this.Ø­á) {
            if (this.Âµá€ < 3.1415927f) {
                final float var1 = this.Âµá€ / 3.1415927f;
                this.ŒÂ = MathHelper.HorizonCode_Horizon_È(var1 * var1 * 3.1415927f) * 3.1415927f * 0.25f;
                if (var1 > 0.75) {
                    this.ŠØ = 1.0f;
                    this.Çªà = 1.0f;
                }
                else {
                    this.Çªà *= 0.8f;
                }
            }
            else {
                this.ŒÂ = 0.0f;
                this.ŠØ *= 0.9f;
                this.Çªà *= 0.99f;
            }
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.ÇŽÉ = this.¥Å * this.ŠØ;
                this.ˆá = this.Œáƒ * this.ŠØ;
                this.ÇŽÕ = this.Œá * this.ŠØ;
            }
            final float var1 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            this.¥É += (-(float)Math.atan2(this.ÇŽÉ, this.ÇŽÕ) * 180.0f / 3.1415927f - this.¥É) * 0.1f;
            this.É = this.¥É;
            this.Ý += (float)(3.141592653589793 * this.Çªà * 1.5);
            this.HorizonCode_Horizon_È += (-(float)Math.atan2(var1, this.ˆá) * 180.0f / 3.1415927f - this.HorizonCode_Horizon_È) * 0.1f;
        }
        else {
            this.ŒÂ = MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(this.Âµá€)) * 3.1415927f * 0.25f;
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.ÇŽÉ = 0.0;
                this.ˆá -= 0.08;
                this.ˆá *= 0.9800000190734863;
                this.ÇŽÕ = 0.0;
            }
            this.HorizonCode_Horizon_È += (float)((-90.0f - this.HorizonCode_Horizon_È) * 0.02);
        }
    }
    
    @Override
    public void Ó(final float p_70612_1_, final float p_70612_2_) {
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
    }
    
    @Override
    public boolean µà() {
        return this.Çªà¢ > 45.0 && this.Çªà¢ < 63.0 && super.µà();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 19) {
            this.Âµá€ = 0.0f;
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    public void HorizonCode_Horizon_È(final float p_175568_1_, final float p_175568_2_, final float p_175568_3_) {
        this.¥Å = p_175568_1_;
        this.Œáƒ = p_175568_2_;
        this.Œá = p_175568_3_;
    }
    
    public boolean Ø() {
        return this.¥Å != 0.0f || this.Œáƒ != 0.0f || this.Œá != 0.0f;
    }
    
    class HorizonCode_Horizon_È extends EntityAIBase
    {
        private EntitySquid Â;
        private static final String Ý = "CL_00002232";
        
        HorizonCode_Horizon_È() {
            this.Â = EntitySquid.this;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return true;
        }
        
        @Override
        public void Ø­áŒŠá() {
            final int var1 = this.Â.µÂ();
            if (var1 > 100) {
                this.Â.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
            }
            else if (this.Â.ˆÐƒØ().nextInt(50) == 0 || !this.Â.Ø­á || !this.Â.Ø()) {
                final float var2 = this.Â.ˆÐƒØ().nextFloat() * 3.1415927f * 2.0f;
                final float var3 = MathHelper.Â(var2) * 0.2f;
                final float var4 = -0.1f + this.Â.ˆÐƒØ().nextFloat() * 0.2f;
                final float var5 = MathHelper.HorizonCode_Horizon_È(var2) * 0.2f;
                this.Â.HorizonCode_Horizon_È(var3, var4, var5);
            }
        }
    }
}

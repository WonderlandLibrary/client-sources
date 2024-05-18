package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityAgeable extends EntityCreature
{
    protected int c_;
    protected int Â;
    protected int Ý;
    private float Ø­Ñ¢Ï­Ø­áˆº;
    private float ŒÂ;
    private static final String Ï­Ï = "CL_00001530";
    
    public EntityAgeable(final World worldIn) {
        super(worldIn);
        this.Ø­Ñ¢Ï­Ø­áˆº = -1.0f;
    }
    
    public abstract EntityAgeable HorizonCode_Horizon_È(final EntityAgeable p0);
    
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.áˆºáˆºáŠ) {
            if (!this.Ï­Ðƒà.ŠÄ) {
                final Class var3 = EntityList.HorizonCode_Horizon_È(var2.Ø());
                if (var3 != null && this.getClass() == var3) {
                    final EntityAgeable var4 = this.HorizonCode_Horizon_È(this);
                    if (var4 != null) {
                        var4.Â(-24000);
                        var4.Â(this.ŒÏ, this.Çªà¢, this.Ê, 0.0f, 0.0f);
                        this.Ï­Ðƒà.HorizonCode_Horizon_È(var4);
                        if (var2.¥Æ()) {
                            var4.à(var2.µà());
                        }
                        if (!p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                            final ItemStack itemStack = var2;
                            --itemStack.Â;
                            if (var2.Â <= 0) {
                                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(12, (Object)(byte)0);
    }
    
    public int à() {
        return this.Ï­Ðƒà.ŠÄ ? this.£Ó.HorizonCode_Horizon_È(12) : this.c_;
    }
    
    public void Â(final int p_175501_1_, final boolean p_175501_2_) {
        final int var4;
        int var3 = var4 = this.à();
        var3 += p_175501_1_ * 20;
        if (var3 > 0) {
            var3 = 0;
            if (var4 < 0) {
                this.Ø();
            }
        }
        final int var5 = var3 - var4;
        this.Â(var3);
        if (p_175501_2_) {
            this.Â += var5;
            if (this.Ý == 0) {
                this.Ý = 40;
            }
        }
        if (this.à() == 0) {
            this.Â(this.Â);
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_110195_1_) {
        this.Â(p_110195_1_, false);
    }
    
    public void Â(final int p_70873_1_) {
        this.£Ó.Â(12, (byte)MathHelper.HorizonCode_Horizon_È(p_70873_1_, -1, 1));
        this.c_ = p_70873_1_;
        this.HorizonCode_Horizon_È(this.h_());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Age", this.à());
        tagCompound.HorizonCode_Horizon_È("ForcedAge", this.Â);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.Â(tagCompund.Ó("Age"));
        this.Â = tagCompund.Ó("ForcedAge");
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        if (this.Ï­Ðƒà.ŠÄ) {
            if (this.Ý > 0) {
                if (this.Ý % 4 == 0) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Æ, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + 0.5 + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, 0.0, 0.0, 0.0, new int[0]);
                }
                --this.Ý;
            }
            this.HorizonCode_Horizon_È(this.h_());
        }
        else {
            int var1 = this.à();
            if (var1 < 0) {
                ++var1;
                this.Â(var1);
                if (var1 == 0) {
                    this.Ø();
                }
            }
            else if (var1 > 0) {
                --var1;
                this.Â(var1);
            }
        }
    }
    
    protected void Ø() {
    }
    
    @Override
    public boolean h_() {
        return this.à() < 0;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_98054_1_) {
        this.Ý(p_98054_1_ ? 0.5f : 1.0f);
    }
    
    @Override
    protected final void HorizonCode_Horizon_È(final float width, final float height) {
        final boolean var3 = this.Ø­Ñ¢Ï­Ø­áˆº > 0.0f;
        this.Ø­Ñ¢Ï­Ø­áˆº = width;
        this.ŒÂ = height;
        if (!var3) {
            this.Ý(1.0f);
        }
    }
    
    protected final void Ý(final float p_98055_1_) {
        super.HorizonCode_Horizon_È(this.Ø­Ñ¢Ï­Ø­áˆº * p_98055_1_, this.ŒÂ * p_98055_1_);
    }
}

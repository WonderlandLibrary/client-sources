package HORIZON-6-0-SKIDPROTECTION;

public class EntityMinecartTNT extends EntityMinecart
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001680";
    
    public EntityMinecartTNT(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = -1;
    }
    
    public EntityMinecartTNT(final World worldIn, final double p_i1728_2_, final double p_i1728_4_, final double p_i1728_6_) {
        super(worldIn, p_i1728_2_, p_i1728_4_, p_i1728_6_);
        this.HorizonCode_Horizon_È = -1;
    }
    
    @Override
    public HorizonCode_Horizon_È à() {
        return EntityMinecart.HorizonCode_Horizon_È.Ø­áŒŠá;
    }
    
    @Override
    public IBlockState Ø() {
        return Blocks.Ñ¢Â.¥à();
    }
    
    @Override
    public void á() {
        super.á();
        if (this.HorizonCode_Horizon_È > 0) {
            --this.HorizonCode_Horizon_È;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.á, this.ŒÏ, this.Çªà¢ + 0.5, this.Ê, 0.0, 0.0, 0.0, new int[0]);
        }
        else if (this.HorizonCode_Horizon_È == 0) {
            this.Â(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
        }
        if (this.¥à) {
            final double var1 = this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ;
            if (var1 >= 0.009999999776482582) {
                this.Â(var1);
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        final Entity var3 = source.áŒŠÆ();
        if (var3 instanceof EntityArrow) {
            final EntityArrow var4 = (EntityArrow)var3;
            if (var4.ˆÏ()) {
                this.Â(var4.ÇŽÉ * var4.ÇŽÉ + var4.ˆá * var4.ˆá + var4.ÇŽÕ * var4.ÇŽÕ);
            }
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public void Â(final DamageSource p_94095_1_) {
        super.Â(p_94095_1_);
        final double var2 = this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ;
        if (!p_94095_1_.Ý()) {
            this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ñ¢Â, 1), 0.0f);
        }
        if (p_94095_1_.Å() || p_94095_1_.Ý() || var2 >= 0.009999999776482582) {
            this.Â(var2);
        }
    }
    
    protected void Â(final double p_94103_1_) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            double var3 = Math.sqrt(p_94103_1_);
            if (var3 > 5.0) {
                var3 = 5.0;
            }
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.ŒÏ, this.Çªà¢, this.Ê, (float)(4.0 + this.ˆáƒ.nextDouble() * 1.5 * var3), true);
            this.á€();
        }
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        if (distance >= 3.0f) {
            final float var3 = distance / 10.0f;
            this.Â((double)(var3 * var3));
        }
        super.Ø­áŒŠá(distance, damageMultiplier);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        if (p_96095_4_ && this.HorizonCode_Horizon_È < 0) {
            this.áŒŠÆ();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 10) {
            this.áŒŠÆ();
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    public void áŒŠÆ() {
        this.HorizonCode_Horizon_È = 80;
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)10);
            if (!this.áŠ()) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this, "game.tnt.primed", 1.0f, 1.0f);
            }
        }
    }
    
    public int ŠÄ() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Ñ¢á() {
        return this.HorizonCode_Horizon_È > -1;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final Explosion p_180428_1_, final World worldIn, final BlockPos p_180428_3_, final IBlockState p_180428_4_) {
        return (this.Ñ¢á() && (BlockRailBase.áŒŠÆ(p_180428_4_) || BlockRailBase.áŒŠÆ(worldIn, p_180428_3_.Ø­áŒŠá()))) ? 0.0f : super.HorizonCode_Horizon_È(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Explosion p_174816_1_, final World worldIn, final BlockPos p_174816_3_, final IBlockState p_174816_4_, final float p_174816_5_) {
        return (!this.Ñ¢á() || (!BlockRailBase.áŒŠÆ(p_174816_4_) && !BlockRailBase.áŒŠÆ(worldIn, p_174816_3_.Ø­áŒŠá()))) && super.HorizonCode_Horizon_È(p_174816_1_, worldIn, p_174816_3_, p_174816_4_, p_174816_5_);
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("TNTFuse", 99)) {
            this.HorizonCode_Horizon_È = tagCompund.Ó("TNTFuse");
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("TNTFuse", this.HorizonCode_Horizon_È);
    }
}

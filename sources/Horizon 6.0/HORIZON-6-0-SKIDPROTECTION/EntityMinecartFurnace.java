package HORIZON-6-0-SKIDPROTECTION;

public class EntityMinecartFurnace extends EntityMinecart
{
    private int Ý;
    public double HorizonCode_Horizon_È;
    public double Â;
    private static final String Ø­áŒŠá = "CL_00001675";
    
    public EntityMinecartFurnace(final World worldIn) {
        super(worldIn);
    }
    
    public EntityMinecartFurnace(final World worldIn, final double p_i1719_2_, final double p_i1719_4_, final double p_i1719_6_) {
        super(worldIn, p_i1719_2_, p_i1719_4_, p_i1719_6_);
    }
    
    @Override
    public HorizonCode_Horizon_È à() {
        return EntityMinecart.HorizonCode_Horizon_È.Ý;
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, new Byte((byte)0));
    }
    
    @Override
    public void á() {
        super.á();
        if (this.Ý > 0) {
            --this.Ý;
        }
        if (this.Ý <= 0) {
            final double n = 0.0;
            this.Â = n;
            this.HorizonCode_Horizon_È = n;
        }
        this.Ý(this.Ý > 0);
        if (this.áŒŠÆ() && this.ˆáƒ.nextInt(4) == 0) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, this.ŒÏ, this.Çªà¢ + 0.8, this.Ê, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    protected double ˆÏ­() {
        return 0.2;
    }
    
    @Override
    public void Â(final DamageSource p_94095_1_) {
        super.Â(p_94095_1_);
        if (!p_94095_1_.Ý()) {
            this.HorizonCode_Horizon_È(new ItemStack(Blocks.£Ó, 1), 0.0f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180460_1_, final IBlockState p_180460_2_) {
        super.HorizonCode_Horizon_È(p_180460_1_, p_180460_2_);
        double var3 = this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È + this.Â * this.Â;
        if (var3 > 1.0E-4 && this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ > 0.001) {
            var3 = MathHelper.HorizonCode_Horizon_È(var3);
            this.HorizonCode_Horizon_È /= var3;
            this.Â /= var3;
            if (this.HorizonCode_Horizon_È * this.ÇŽÉ + this.Â * this.ÇŽÕ < 0.0) {
                this.HorizonCode_Horizon_È = 0.0;
                this.Â = 0.0;
            }
            else {
                final double var4 = var3 / this.ˆÏ­();
                this.HorizonCode_Horizon_È *= var4;
                this.Â *= var4;
            }
        }
    }
    
    @Override
    protected void ˆà() {
        double var1 = this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È + this.Â * this.Â;
        if (var1 > 1.0E-4) {
            var1 = MathHelper.HorizonCode_Horizon_È(var1);
            this.HorizonCode_Horizon_È /= var1;
            this.Â /= var1;
            final double var2 = 1.0;
            this.ÇŽÉ *= 0.800000011920929;
            this.ˆá *= 0.0;
            this.ÇŽÕ *= 0.800000011920929;
            this.ÇŽÉ += this.HorizonCode_Horizon_È * var2;
            this.ÇŽÕ += this.Â * var2;
        }
        else {
            this.ÇŽÉ *= 0.9800000190734863;
            this.ˆá *= 0.0;
            this.ÇŽÕ *= 0.9800000190734863;
        }
        super.ˆà();
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        final ItemStack var2 = playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.Ø) {
            if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                final ItemStack itemStack = var2;
                if (--itemStack.Â == 0) {
                    playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
                }
            }
            this.Ý += 3600;
        }
        this.HorizonCode_Horizon_È = this.ŒÏ - playerIn.ŒÏ;
        this.Â = this.Ê - playerIn.Ê;
        return true;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("PushX", this.HorizonCode_Horizon_È);
        tagCompound.HorizonCode_Horizon_È("PushZ", this.Â);
        tagCompound.HorizonCode_Horizon_È("Fuel", (short)this.Ý);
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.HorizonCode_Horizon_È = tagCompund.áŒŠÆ("PushX");
        this.Â = tagCompund.áŒŠÆ("PushZ");
        this.Ý = tagCompund.Âµá€("Fuel");
    }
    
    protected boolean áŒŠÆ() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x1) != 0x0;
    }
    
    protected void Ý(final boolean p_94107_1_) {
        if (p_94107_1_) {
            this.£Ó.Â(16, (byte)(this.£Ó.HorizonCode_Horizon_È(16) | 0x1));
        }
        else {
            this.£Ó.Â(16, (byte)(this.£Ó.HorizonCode_Horizon_È(16) & 0xFFFFFFFE));
        }
    }
    
    @Override
    public IBlockState Ø() {
        return (this.áŒŠÆ() ? Blocks.ˆÐƒØ­à : Blocks.£Ó).¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, EnumFacing.Ý);
    }
}

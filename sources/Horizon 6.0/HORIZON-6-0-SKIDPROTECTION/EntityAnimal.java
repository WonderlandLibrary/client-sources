package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
    protected Block Ø­Ñ¢Ï­Ø­áˆº;
    private int ŒÂ;
    private EntityPlayer Ï­Ï;
    private static final String ŠØ = "CL_00001638";
    
    public EntityAnimal(final World worldIn) {
        super(worldIn);
        this.Ø­Ñ¢Ï­Ø­áˆº = Blocks.Ø­áŒŠá;
    }
    
    @Override
    protected void ˆØ() {
        if (this.à() != 0) {
            this.ŒÂ = 0;
        }
        super.ˆØ();
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        if (this.à() != 0) {
            this.ŒÂ = 0;
        }
        if (this.ŒÂ > 0) {
            --this.ŒÂ;
            if (this.ŒÂ % 10 == 0) {
                final double var1 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var2 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var3 = this.ˆáƒ.nextGaussian() * 0.02;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áƒ, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + 0.5 + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, var1, var2, var3, new int[0]);
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        this.ŒÂ = 0;
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public float HorizonCode_Horizon_È(final BlockPos p_180484_1_) {
        return (this.Ï­Ðƒà.Â(p_180484_1_.Âµá€()).Ý() == Blocks.Ø­áŒŠá) ? 10.0f : (this.Ï­Ðƒà.£à(p_180484_1_) - 0.5f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("InLove", this.ŒÂ);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ŒÂ = tagCompund.Ó("InLove");
    }
    
    @Override
    public boolean µà() {
        final int var1 = MathHelper.Ý(this.ŒÏ);
        final int var2 = MathHelper.Ý(this.£É().Â);
        final int var3 = MathHelper.Ý(this.Ê);
        final BlockPos var4 = new BlockPos(var1, var2, var3);
        return this.Ï­Ðƒà.Â(var4.Âµá€()).Ý() == this.Ø­Ñ¢Ï­Ø­áˆº && this.Ï­Ðƒà.á(var4) > 8 && super.µà();
    }
    
    @Override
    public int áŒŠÔ() {
        return 120;
    }
    
    @Override
    protected boolean ÂµÂ() {
        return false;
    }
    
    @Override
    protected int Âµá€(final EntityPlayer p_70693_1_) {
        return 1 + this.Ï­Ðƒà.Å.nextInt(3);
    }
    
    public boolean Ø­áŒŠá(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.HorizonCode_Horizon_È() == Items.Âµà;
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null) {
            if (this.Ø­áŒŠá(var2) && this.à() == 0 && this.ŒÂ <= 0) {
                this.HorizonCode_Horizon_È(p_70085_1_, var2);
                this.Ó(p_70085_1_);
                return true;
            }
            if (this.h_() && this.Ø­áŒŠá(var2)) {
                this.HorizonCode_Horizon_È(p_70085_1_, var2);
                this.Â((int)(-this.à() / 20 * 0.1f), true);
                return true;
            }
        }
        return super.Ø­áŒŠá(p_70085_1_);
    }
    
    protected void HorizonCode_Horizon_È(final EntityPlayer p_175505_1_, final ItemStack p_175505_2_) {
        if (!p_175505_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
            --p_175505_2_.Â;
            if (p_175505_2_.Â <= 0) {
                p_175505_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_175505_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
            }
        }
    }
    
    public void Ó(final EntityPlayer p_146082_1_) {
        this.ŒÂ = 600;
        this.Ï­Ï = p_146082_1_;
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)18);
    }
    
    public EntityPlayer ÇŽ() {
        return this.Ï­Ï;
    }
    
    public boolean ÇŽÅ() {
        return this.ŒÂ > 0;
    }
    
    public void ¥Ðƒá() {
        this.ŒÂ = 0;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityAnimal p_70878_1_) {
        return p_70878_1_ != this && p_70878_1_.getClass() == this.getClass() && (this.ÇŽÅ() && p_70878_1_.ÇŽÅ());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 18) {
            for (int var2 = 0; var2 < 7; ++var2) {
                final double var3 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var4 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var5 = this.ˆáƒ.nextGaussian() * 0.02;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áƒ, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + 0.5 + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, var3, var4, var5, new int[0]);
            }
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
}

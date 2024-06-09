package HORIZON-6-0-SKIDPROTECTION;

public class EntityItemFrame extends EntityHanging
{
    private float Ý;
    private static final String Ø­áŒŠá = "CL_00001547";
    
    public EntityItemFrame(final World worldIn) {
        super(worldIn);
        this.Ý = 1.0f;
    }
    
    public EntityItemFrame(final World worldIn, final BlockPos p_i45852_2_, final EnumFacing p_i45852_3_) {
        super(worldIn, p_i45852_2_);
        this.Ý = 1.0f;
        this.HorizonCode_Horizon_È(p_i45852_3_);
    }
    
    @Override
    protected void ÂµÈ() {
        this.É().HorizonCode_Horizon_È(8, 5);
        this.É().HorizonCode_Horizon_È(9, (Object)(byte)0);
    }
    
    @Override
    public float £Ó() {
        return 0.0f;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (!source.Ý() && this.µà() != null) {
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.HorizonCode_Horizon_È(source.áˆºÑ¢Õ(), false);
                this.HorizonCode_Horizon_È((ItemStack)null);
            }
            return true;
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public int Ø() {
        return 12;
    }
    
    @Override
    public int áŒŠÆ() {
        return 12;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final double distance) {
        double var3 = 16.0;
        var3 *= 64.0 * this.¥Æ;
        return distance < var3 * var3;
    }
    
    @Override
    public void Â(final Entity p_110128_1_) {
        this.HorizonCode_Horizon_È(p_110128_1_, true);
    }
    
    public void HorizonCode_Horizon_È(final Entity p_146065_1_, final boolean p_146065_2_) {
        if (this.Ï­Ðƒà.Çªà¢().Â("doTileDrops")) {
            ItemStack var3 = this.µà();
            if (p_146065_1_ instanceof EntityPlayer) {
                final EntityPlayer var4 = (EntityPlayer)p_146065_1_;
                if (var4.áˆºáˆºáŠ.Ø­áŒŠá) {
                    this.Â(var3);
                    return;
                }
            }
            if (p_146065_2_) {
                this.HorizonCode_Horizon_È(new ItemStack(Items.µÏ), 0.0f);
            }
            if (var3 != null && this.ˆáƒ.nextFloat() < this.Ý) {
                var3 = var3.áˆºÑ¢Õ();
                this.Â(var3);
                this.HorizonCode_Horizon_È(var3, 0.0f);
            }
        }
    }
    
    private void Â(final ItemStack p_110131_1_) {
        if (p_110131_1_ != null) {
            if (p_110131_1_.HorizonCode_Horizon_È() == Items.ˆØ) {
                final MapData var2 = ((ItemMap)p_110131_1_.HorizonCode_Horizon_È()).HorizonCode_Horizon_È(p_110131_1_, this.Ï­Ðƒà);
                var2.Ø.remove("frame-" + this.ˆá());
            }
            p_110131_1_.HorizonCode_Horizon_È((EntityItemFrame)null);
        }
    }
    
    public ItemStack µà() {
        return this.É().Ó(8);
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_82334_1_) {
        this.HorizonCode_Horizon_È(p_82334_1_, true);
    }
    
    private void HorizonCode_Horizon_È(ItemStack p_174864_1_, final boolean p_174864_2_) {
        if (p_174864_1_ != null) {
            p_174864_1_ = p_174864_1_.áˆºÑ¢Õ();
            p_174864_1_.Â = 1;
            p_174864_1_.HorizonCode_Horizon_È(this);
        }
        this.É().Â(8, p_174864_1_);
        this.É().Ø(8);
        if (p_174864_2_ && this.HorizonCode_Horizon_È != null) {
            this.Ï­Ðƒà.Âµá€(this.HorizonCode_Horizon_È, Blocks.Â);
        }
    }
    
    public int ˆà() {
        return this.É().HorizonCode_Horizon_È(9);
    }
    
    public void HorizonCode_Horizon_È(final int p_82336_1_) {
        this.Â(p_82336_1_, true);
    }
    
    private void Â(final int p_174865_1_, final boolean p_174865_2_) {
        this.É().Â(9, (byte)(p_174865_1_ % 8));
        if (p_174865_2_ && this.HorizonCode_Horizon_È != null) {
            this.Ï­Ðƒà.Âµá€(this.HorizonCode_Horizon_È, Blocks.Â);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        if (this.µà() != null) {
            tagCompound.HorizonCode_Horizon_È("Item", this.µà().Â(new NBTTagCompound()));
            tagCompound.HorizonCode_Horizon_È("ItemRotation", (byte)this.ˆà());
            tagCompound.HorizonCode_Horizon_È("ItemDropChance", this.Ý);
        }
        super.HorizonCode_Horizon_È(tagCompound);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        final NBTTagCompound var2 = tagCompund.ˆÏ­("Item");
        if (var2 != null && !var2.Ý()) {
            this.HorizonCode_Horizon_È(ItemStack.HorizonCode_Horizon_È(var2), false);
            this.Â(tagCompund.Ø­áŒŠá("ItemRotation"), false);
            if (tagCompund.Â("ItemDropChance", 99)) {
                this.Ý = tagCompund.Ø("ItemDropChance");
            }
            if (tagCompund.Ý("Direction")) {
                this.Â(this.ˆà() * 2, false);
            }
        }
        super.Â(tagCompund);
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        if (this.µà() == null) {
            final ItemStack var2 = playerIn.Çª();
            if (var2 != null && !this.Ï­Ðƒà.ŠÄ) {
                this.HorizonCode_Horizon_È(var2);
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    final ItemStack itemStack = var2;
                    if (--itemStack.Â <= 0) {
                        playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
                    }
                }
            }
        }
        else if (!this.Ï­Ðƒà.ŠÄ) {
            this.HorizonCode_Horizon_È(this.ˆà() + 1);
        }
        return true;
    }
    
    public int ¥Æ() {
        return (this.µà() == null) ? 0 : (this.ˆà() % 8 + 1);
    }
}

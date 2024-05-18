package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer
{
    private ItemStack[] HorizonCode_Horizon_È;
    private boolean Â;
    private static final String Ý = "CL_00001674";
    
    public EntityMinecartContainer(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = new ItemStack[36];
        this.Â = true;
    }
    
    public EntityMinecartContainer(final World worldIn, final double p_i1717_2_, final double p_i1717_4_, final double p_i1717_6_) {
        super(worldIn, p_i1717_2_, p_i1717_4_, p_i1717_6_);
        this.HorizonCode_Horizon_È = new ItemStack[36];
        this.Â = true;
    }
    
    @Override
    public void Â(final DamageSource p_94095_1_) {
        super.Â(p_94095_1_);
        InventoryHelper.HorizonCode_Horizon_È(this.Ï­Ðƒà, this, this);
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return this.HorizonCode_Horizon_È[slotIn];
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.HorizonCode_Horizon_È[index] == null) {
            return null;
        }
        if (this.HorizonCode_Horizon_È[index].Â <= count) {
            final ItemStack var3 = this.HorizonCode_Horizon_È[index];
            this.HorizonCode_Horizon_È[index] = null;
            return var3;
        }
        final ItemStack var3 = this.HorizonCode_Horizon_È[index].HorizonCode_Horizon_È(count);
        if (this.HorizonCode_Horizon_È[index].Â == 0) {
            this.HorizonCode_Horizon_È[index] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.HorizonCode_Horizon_È[index] != null) {
            final ItemStack var2 = this.HorizonCode_Horizon_È[index];
            this.HorizonCode_Horizon_È[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.HorizonCode_Horizon_È[index] = stack;
        if (stack != null && stack.Â > this.Ñ¢á()) {
            stack.Â = this.Ñ¢á();
        }
    }
    
    @Override
    public void ŠÄ() {
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return !this.ˆáŠ && playerIn.Âµá€(this) <= 64.0;
    }
    
    @Override
    public void Âµá€(final EntityPlayer playerIn) {
    }
    
    @Override
    public void Ó(final EntityPlayer playerIn) {
    }
    
    @Override
    public boolean Ø­áŒŠá(final int index, final ItemStack stack) {
        return true;
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.Šà() : "container.minecart";
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    @Override
    public void áŒŠÆ(final int dimensionId) {
        this.Â = false;
        super.áŒŠÆ(dimensionId);
    }
    
    @Override
    public void á€() {
        if (this.Â) {
            InventoryHelper.HorizonCode_Horizon_È(this.Ï­Ðƒà, this, this);
        }
        super.á€();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.HorizonCode_Horizon_È.length; ++var3) {
            if (this.HorizonCode_Horizon_È[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.HorizonCode_Horizon_È("Slot", (byte)var3);
                this.HorizonCode_Horizon_È[var3].Â(var4);
                var2.HorizonCode_Horizon_È(var4);
            }
        }
        tagCompound.HorizonCode_Horizon_È("Items", var2);
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        final NBTTagList var2 = tagCompund.Ý("Items", 10);
        this.HorizonCode_Horizon_È = new ItemStack[this.áŒŠÆ()];
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final int var5 = var4.Ø­áŒŠá("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.HorizonCode_Horizon_È.length) {
                this.HorizonCode_Horizon_È[var5] = ItemStack.HorizonCode_Horizon_È(var4);
            }
        }
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            playerIn.HorizonCode_Horizon_È((IInventory)this);
        }
        return true;
    }
    
    @Override
    protected void ˆà() {
        final int var1 = 15 - Container.Â(this);
        final float var2 = 0.98f + var1 * 0.001f;
        this.ÇŽÉ *= var2;
        this.ˆá *= 0.0;
        this.ÇŽÕ *= var2;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int id) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final int value) {
    }
    
    @Override
    public int Âµá€() {
        return 0;
    }
    
    @Override
    public boolean Ó() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final LockCode code) {
    }
    
    @Override
    public LockCode x_() {
        return LockCode.HorizonCode_Horizon_È;
    }
    
    @Override
    public void ŒÏ() {
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            this.HorizonCode_Horizon_È[var1] = null;
        }
    }
}

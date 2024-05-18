package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper
{
    private boolean HorizonCode_Horizon_È;
    private int Â;
    private BlockPos Ý;
    private static final String Ø­áŒŠá = "CL_00001676";
    
    public EntityMinecartHopper(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = true;
        this.Â = -1;
        this.Ý = BlockPos.HorizonCode_Horizon_È;
    }
    
    public EntityMinecartHopper(final World worldIn, final double p_i1721_2_, final double p_i1721_4_, final double p_i1721_6_) {
        super(worldIn, p_i1721_2_, p_i1721_4_, p_i1721_6_);
        this.HorizonCode_Horizon_È = true;
        this.Â = -1;
        this.Ý = BlockPos.HorizonCode_Horizon_È;
    }
    
    @Override
    public HorizonCode_Horizon_È à() {
        return EntityMinecart.HorizonCode_Horizon_È.Ó;
    }
    
    @Override
    public IBlockState Ø() {
        return Blocks.áˆºÉ.¥à();
    }
    
    @Override
    public int Ï­Ðƒà() {
        return 1;
    }
    
    @Override
    public int áŒŠÆ() {
        return 5;
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            playerIn.HorizonCode_Horizon_È((IInventory)this);
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        final boolean var5 = !p_96095_4_;
        if (var5 != this.Ê()) {
            this.Ý(var5);
        }
    }
    
    public boolean Ê() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void Ý(final boolean p_96110_1_) {
        this.HorizonCode_Horizon_È = p_96110_1_;
    }
    
    @Override
    public World ÇŽÉ() {
        return this.Ï­Ðƒà;
    }
    
    @Override
    public double Š() {
        return this.ŒÏ;
    }
    
    @Override
    public double Ø­Ñ¢á€() {
        return this.Çªà¢;
    }
    
    @Override
    public double Ñ¢Ó() {
        return this.Ê;
    }
    
    @Override
    public void á() {
        super.á();
        if (!this.Ï­Ðƒà.ŠÄ && this.Œ() && this.Ê()) {
            final BlockPos var1 = new BlockPos(this);
            if (var1.equals(this.Ý)) {
                --this.Â;
            }
            else {
                this.£á(0);
            }
            if (!this.áŒŠÔ()) {
                this.£á(0);
                if (this.Ø­Æ()) {
                    this.£á(4);
                    this.ŠÄ();
                }
            }
        }
    }
    
    public boolean Ø­Æ() {
        if (TileEntityHopper.HorizonCode_Horizon_È(this)) {
            return true;
        }
        final List var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityItem.class, this.£É().Â(0.25, 0.0, 0.25), IEntitySelector.HorizonCode_Horizon_È);
        if (var1.size() > 0) {
            TileEntityHopper.HorizonCode_Horizon_È(this, var1.get(0));
        }
        return false;
    }
    
    @Override
    public void Â(final DamageSource p_94095_1_) {
        super.Â(p_94095_1_);
        this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.áˆºÉ), 1, 0.0f);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("TransferCooldown", this.Â);
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.Â = tagCompund.Ó("TransferCooldown");
    }
    
    public void £á(final int p_98042_1_) {
        this.Â = p_98042_1_;
    }
    
    public boolean áŒŠÔ() {
        return this.Â > 0;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:hopper";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerHopper(playerInventory, this, playerIn);
    }
}

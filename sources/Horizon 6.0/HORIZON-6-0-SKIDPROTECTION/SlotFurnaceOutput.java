package HORIZON-6-0-SKIDPROTECTION;

public class SlotFurnaceOutput extends Slot
{
    private EntityPlayer HorizonCode_Horizon_È;
    private int Ó;
    private static final String à = "CL_00002183";
    
    public SlotFurnaceOutput(final EntityPlayer p_i45793_1_, final IInventory p_i45793_2_, final int p_i45793_3_, final int p_i45793_4_, final int p_i45793_5_) {
        super(p_i45793_2_, p_i45793_3_, p_i45793_4_, p_i45793_5_);
        this.HorizonCode_Horizon_È = p_i45793_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack) {
        return false;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final int p_75209_1_) {
        if (this.Â()) {
            this.Ó += Math.min(p_75209_1_, this.HorizonCode_Horizon_È().Â);
        }
        return super.HorizonCode_Horizon_È(p_75209_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final ItemStack stack) {
        this.Âµá€(stack);
        super.HorizonCode_Horizon_È(playerIn, stack);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final ItemStack p_75210_1_, final int p_75210_2_) {
        this.Ó += p_75210_2_;
        this.Âµá€(p_75210_1_);
    }
    
    @Override
    protected void Âµá€(final ItemStack p_75208_1_) {
        p_75208_1_.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ï­Ðƒà, this.HorizonCode_Horizon_È, this.Ó);
        if (!this.HorizonCode_Horizon_È.Ï­Ðƒà.ŠÄ) {
            int var2 = this.Ó;
            final float var3 = FurnaceRecipes.HorizonCode_Horizon_È().Â(p_75208_1_);
            if (var3 == 0.0f) {
                var2 = 0;
            }
            else if (var3 < 1.0f) {
                int var4 = MathHelper.Ø­áŒŠá(var2 * var3);
                if (var4 < MathHelper.Ó(var2 * var3) && Math.random() < var2 * var3 - var4) {
                    ++var4;
                }
                var2 = var4;
            }
            while (var2 > 0) {
                final int var4 = EntityXPOrb.HorizonCode_Horizon_È(var2);
                var2 -= var4;
                this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityXPOrb(this.HorizonCode_Horizon_È.Ï­Ðƒà, this.HorizonCode_Horizon_È.ŒÏ, this.HorizonCode_Horizon_È.Çªà¢ + 0.5, this.HorizonCode_Horizon_È.Ê + 0.5, var4));
            }
        }
        this.Ó = 0;
        if (p_75208_1_.HorizonCode_Horizon_È() == Items.áˆºÑ¢Õ) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(AchievementList.ÂµÈ);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() == Items.Ø­Æ) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(AchievementList.£à);
        }
    }
}

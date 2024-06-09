package HORIZON-6-0-SKIDPROTECTION;

public class SlotCrafting extends Slot
{
    private final InventoryCrafting HorizonCode_Horizon_È;
    private final EntityPlayer Ó;
    private int à;
    private static final String Ø = "CL_00001761";
    
    public SlotCrafting(final EntityPlayer p_i45790_1_, final InventoryCrafting p_i45790_2_, final IInventory p_i45790_3_, final int p_i45790_4_, final int p_i45790_5_, final int p_i45790_6_) {
        super(p_i45790_3_, p_i45790_4_, p_i45790_5_, p_i45790_6_);
        this.Ó = p_i45790_1_;
        this.HorizonCode_Horizon_È = p_i45790_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack) {
        return false;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final int p_75209_1_) {
        if (this.Â()) {
            this.à += Math.min(p_75209_1_, this.HorizonCode_Horizon_È().Â);
        }
        return super.HorizonCode_Horizon_È(p_75209_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final ItemStack p_75210_1_, final int p_75210_2_) {
        this.à += p_75210_2_;
        this.Âµá€(p_75210_1_);
    }
    
    @Override
    protected void Âµá€(final ItemStack p_75208_1_) {
        if (this.à > 0) {
            p_75208_1_.HorizonCode_Horizon_È(this.Ó.Ï­Ðƒà, this.Ó, this.à);
        }
        this.à = 0;
        if (p_75208_1_.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.ˆÉ)) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.Ø);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() instanceof ItemPickaxe) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.áŒŠÆ);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.£Ó)) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.áˆºÑ¢Õ);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() instanceof ItemHoe) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.á);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() == Items.Ç) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.ˆÏ­);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() == Items.µÐƒáƒ) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.£á);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() instanceof ItemPickaxe && ((ItemPickaxe)p_75208_1_.HorizonCode_Horizon_È()).ˆà() != Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.Å);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() instanceof ItemSword) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.ˆà);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.¥Âµá€)) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.ÇŽÉ);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Ï­à)) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.ÇŽÕ);
        }
        if (p_75208_1_.HorizonCode_Horizon_È() == Items.£Õ && p_75208_1_.Ø() == 1) {
            this.Ó.HorizonCode_Horizon_È(AchievementList.ŠÂµà);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final ItemStack stack) {
        this.Âµá€(stack);
        final ItemStack[] var3 = CraftingManager.HorizonCode_Horizon_È().Â(this.HorizonCode_Horizon_È, playerIn.Ï­Ðƒà);
        for (int var4 = 0; var4 < var3.length; ++var4) {
            final ItemStack var5 = this.HorizonCode_Horizon_È.á(var4);
            final ItemStack var6 = var3[var4];
            if (var5 != null) {
                this.HorizonCode_Horizon_È.Â(var4, 1);
            }
            if (var6 != null) {
                if (this.HorizonCode_Horizon_È.á(var4) == null) {
                    this.HorizonCode_Horizon_È.Ý(var4, var6);
                }
                else if (!this.Ó.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var6)) {
                    this.Ó.HorizonCode_Horizon_È(var6, false);
                }
            }
        }
    }
}

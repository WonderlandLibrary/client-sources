package HORIZON-6-0-SKIDPROTECTION;

public class ItemFishingRod extends Item_1028566121
{
    private static final String à = "CL_00000034";
    
    public ItemFishingRod() {
        this.Ø­áŒŠá(64);
        this.Â(1);
        this.HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ);
    }
    
    @Override
    public boolean Ó() {
        return true;
    }
    
    @Override
    public boolean à() {
        return true;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (playerIn.µÏ != null) {
            final int var4 = playerIn.µÏ.Ø();
            itemStackIn.HorizonCode_Horizon_È(var4, playerIn);
            playerIn.b_();
        }
        else {
            worldIn.HorizonCode_Horizon_È((Entity)playerIn, "random.bow", 0.5f, 0.4f / (ItemFishingRod.Ý.nextFloat() * 0.4f + 0.8f));
            if (!worldIn.ŠÄ) {
                worldIn.HorizonCode_Horizon_È(new EntityFishHook(worldIn, playerIn));
            }
            playerIn.b_();
            playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        }
        return itemStackIn;
    }
    
    @Override
    public boolean áˆºÑ¢Õ(final ItemStack stack) {
        return super.áˆºÑ¢Õ(stack);
    }
    
    @Override
    public int ˆÏ­() {
        return 1;
    }
}

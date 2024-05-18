package HORIZON-6-0-SKIDPROTECTION;

public class ItemBucketMilk extends Item_1028566121
{
    private static final String à = "CL_00000048";
    
    public ItemBucketMilk() {
        this.Â(1);
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public ItemStack Â(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            --stack.Â;
        }
        if (!worldIn.ŠÄ) {
            playerIn.¥Âµá€();
        }
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return (stack.Â <= 0) ? new ItemStack(Items.áŒŠáŠ) : stack;
    }
    
    @Override
    public int Ø­áŒŠá(final ItemStack stack) {
        return 32;
    }
    
    @Override
    public EnumAction Ý(final ItemStack stack) {
        return EnumAction.Ý;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        playerIn.Â(itemStackIn, this.Ø­áŒŠá(itemStackIn));
        return itemStackIn;
    }
}

package HORIZON-6-0-SKIDPROTECTION;

public class ItemEgg extends Item_1028566121
{
    private static final String à = "CL_00000023";
    
    public ItemEgg() {
        this.Ø­áŒŠá = 16;
        this.HorizonCode_Horizon_È(CreativeTabs.á);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            --itemStackIn.Â;
        }
        worldIn.HorizonCode_Horizon_È((Entity)playerIn, "random.bow", 0.5f, 0.4f / (ItemEgg.Ý.nextFloat() * 0.4f + 0.8f));
        if (!worldIn.ŠÄ) {
            worldIn.HorizonCode_Horizon_È(new EntityEgg(worldIn, playerIn));
        }
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return itemStackIn;
    }
}

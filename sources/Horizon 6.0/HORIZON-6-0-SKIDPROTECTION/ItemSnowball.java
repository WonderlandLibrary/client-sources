package HORIZON-6-0-SKIDPROTECTION;

public class ItemSnowball extends Item_1028566121
{
    private static final String à = "CL_00000069";
    
    public ItemSnowball() {
        this.Ø­áŒŠá = 16;
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            --itemStackIn.Â;
        }
        worldIn.HorizonCode_Horizon_È((Entity)playerIn, "random.bow", 0.5f, 0.4f / (ItemSnowball.Ý.nextFloat() * 0.4f + 0.8f));
        if (!worldIn.ŠÄ) {
            worldIn.HorizonCode_Horizon_È(new EntitySnowball(worldIn, playerIn));
        }
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return itemStackIn;
    }
}

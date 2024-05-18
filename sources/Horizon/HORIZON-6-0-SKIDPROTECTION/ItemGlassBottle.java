package HORIZON-6-0-SKIDPROTECTION;

public class ItemGlassBottle extends Item_1028566121
{
    private static final String à = "CL_00001776";
    
    public ItemGlassBottle() {
        this.HorizonCode_Horizon_È(CreativeTabs.ÂµÈ);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final MovingObjectPosition var4 = this.HorizonCode_Horizon_È(worldIn, playerIn, true);
        if (var4 == null) {
            return itemStackIn;
        }
        if (var4.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            final BlockPos var5 = var4.HorizonCode_Horizon_È();
            if (!worldIn.HorizonCode_Horizon_È(playerIn, var5)) {
                return itemStackIn;
            }
            if (!playerIn.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È(var4.Â), var4.Â, itemStackIn)) {
                return itemStackIn;
            }
            if (worldIn.Â(var5).Ý().Ó() == Material.Ø) {
                --itemStackIn.Â;
                playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
                if (itemStackIn.Â <= 0) {
                    return new ItemStack(Items.µÂ);
                }
                if (!playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(new ItemStack(Items.µÂ))) {
                    playerIn.HorizonCode_Horizon_È(new ItemStack(Items.µÂ, 1, 0), false);
                }
            }
        }
        return itemStackIn;
    }
}

package HORIZON-6-0-SKIDPROTECTION;

public class ItemEmptyMap extends ItemMapBase
{
    private static final String à = "CL_00000024";
    
    protected ItemEmptyMap() {
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final ItemStack var4 = new ItemStack(Items.ˆØ, 1, worldIn.Â("map"));
        final String var5 = "map_" + var4.Ø();
        final MapData var6 = new MapData(var5);
        worldIn.HorizonCode_Horizon_È(var5, var6);
        var6.Âµá€ = 0;
        var6.HorizonCode_Horizon_È(playerIn.ŒÏ, playerIn.Ê, var6.Âµá€);
        var6.Ø­áŒŠá = (byte)worldIn.£à.µà();
        var6.Ø­áŒŠá();
        --itemStackIn.Â;
        if (itemStackIn.Â <= 0) {
            return var4;
        }
        if (!playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var4.áˆºÑ¢Õ())) {
            playerIn.HorizonCode_Horizon_È(var4, false);
        }
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return itemStackIn;
    }
}

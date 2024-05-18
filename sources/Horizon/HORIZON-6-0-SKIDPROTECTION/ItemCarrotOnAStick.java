package HORIZON-6-0-SKIDPROTECTION;

public class ItemCarrotOnAStick extends Item_1028566121
{
    private static final String à = "CL_00000001";
    
    public ItemCarrotOnAStick() {
        this.HorizonCode_Horizon_È(CreativeTabs.Âµá€);
        this.Â(1);
        this.Ø­áŒŠá(25);
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
        if (playerIn.áˆºÇŽØ() && playerIn.Æ instanceof EntityPig) {
            final EntityPig var4 = (EntityPig)playerIn.Æ;
            if (var4.¥Ê().áˆºÑ¢Õ() && itemStackIn.áŒŠÆ() - itemStackIn.Ø() >= 7) {
                var4.¥Ê().áŒŠÆ();
                itemStackIn.HorizonCode_Horizon_È(7, playerIn);
                if (itemStackIn.Â == 0) {
                    final ItemStack var5 = new ItemStack(Items.ÂµÕ);
                    var5.Ø­áŒŠá(itemStackIn.Å());
                    return var5;
                }
            }
        }
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return itemStackIn;
    }
}

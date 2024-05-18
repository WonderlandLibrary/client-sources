package HORIZON-6-0-SKIDPROTECTION;

public class ItemWritableBook extends Item_1028566121
{
    private static final String à = "CL_00000076";
    
    public ItemWritableBook() {
        this.Â(1);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        playerIn.HorizonCode_Horizon_È(itemStackIn);
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return itemStackIn;
    }
    
    public static boolean Â(final NBTTagCompound p_150930_0_) {
        if (p_150930_0_ == null) {
            return false;
        }
        if (!p_150930_0_.Â("pages", 9)) {
            return false;
        }
        final NBTTagList var1 = p_150930_0_.Ý("pages", 8);
        for (int var2 = 0; var2 < var1.Âµá€(); ++var2) {
            final String var3 = var1.Ó(var2);
            if (var3 == null) {
                return false;
            }
            if (var3.length() > 32767) {
                return false;
            }
        }
        return true;
    }
}

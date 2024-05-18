package HORIZON-6-0-SKIDPROTECTION;

public class ItemSoup extends ItemFood
{
    private static final String Ø = "CL_00001778";
    
    public ItemSoup(final int p_i45330_1_) {
        super(p_i45330_1_, false);
        this.Â(1);
    }
    
    @Override
    public ItemStack Â(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        super.Â(stack, worldIn, playerIn);
        return new ItemStack(Items.ŠÄ);
    }
}

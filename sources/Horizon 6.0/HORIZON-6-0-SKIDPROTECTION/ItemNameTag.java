package HORIZON-6-0-SKIDPROTECTION;

public class ItemNameTag extends Item_1028566121
{
    private static final String à = "CL_00000052";
    
    public ItemNameTag() {
        this.HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target) {
        if (!stack.¥Æ()) {
            return false;
        }
        if (target instanceof EntityLiving) {
            final EntityLiving var4 = (EntityLiving)target;
            var4.à(stack.µà());
            var4.ˆÈ();
            --stack.Â;
            return true;
        }
        return super.HorizonCode_Horizon_È(stack, playerIn, target);
    }
}

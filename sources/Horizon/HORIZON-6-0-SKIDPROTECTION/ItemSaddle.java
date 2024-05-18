package HORIZON-6-0-SKIDPROTECTION;

public class ItemSaddle extends Item_1028566121
{
    private static final String à = "CL_00000059";
    
    public ItemSaddle() {
        this.Ø­áŒŠá = 1;
        this.HorizonCode_Horizon_È(CreativeTabs.Âµá€);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target) {
        if (target instanceof EntityPig) {
            final EntityPig var4 = (EntityPig)target;
            if (!var4.ÐƒÇŽà() && !var4.h_()) {
                var4.á(true);
                var4.Ï­Ðƒà.HorizonCode_Horizon_È(var4, "mob.horse.leather", 0.5f, 1.0f);
                --stack.Â;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        this.HorizonCode_Horizon_È(stack, null, target);
        return true;
    }
}

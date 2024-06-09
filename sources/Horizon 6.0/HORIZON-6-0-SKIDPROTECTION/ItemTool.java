package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Multimap;
import java.util.Set;

public class ItemTool extends Item_1028566121
{
    private Set áŒŠÆ;
    protected float à;
    private float áˆºÑ¢Õ;
    protected HorizonCode_Horizon_È Ø;
    private static final String ÂµÈ = "CL_00000019";
    
    protected ItemTool(final float p_i45333_1_, final HorizonCode_Horizon_È p_i45333_2_, final Set p_i45333_3_) {
        this.à = 4.0f;
        this.Ø = p_i45333_2_;
        this.áŒŠÆ = p_i45333_3_;
        this.Ø­áŒŠá = 1;
        this.Ø­áŒŠá(p_i45333_2_.HorizonCode_Horizon_È());
        this.à = p_i45333_2_.Â();
        this.áˆºÑ¢Õ = p_i45333_1_ + p_i45333_2_.Ý();
        this.HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ);
    }
    
    @Override
    public float HorizonCode_Horizon_È(final ItemStack stack, final Block p_150893_2_) {
        return this.áŒŠÆ.contains(p_150893_2_) ? this.à : 1.0f;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        stack.HorizonCode_Horizon_È(2, attacker);
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final Block blockIn, final BlockPos pos, final EntityLivingBase playerIn) {
        if (blockIn.Â(worldIn, pos) != 0.0) {
            stack.HorizonCode_Horizon_È(1, playerIn);
        }
        return true;
    }
    
    @Override
    public boolean Ó() {
        return true;
    }
    
    public HorizonCode_Horizon_È ˆà() {
        return this.Ø;
    }
    
    @Override
    public int ˆÏ­() {
        return this.Ø.Âµá€();
    }
    
    public String ¥Æ() {
        return this.Ø.toString();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack toRepair, final ItemStack repair) {
        return this.Ø.Ó() == repair.HorizonCode_Horizon_È() || super.HorizonCode_Horizon_È(toRepair, repair);
    }
    
    @Override
    public Multimap £à() {
        final Multimap var1 = super.£à();
        var1.put((Object)SharedMonsterAttributes.Âµá€.HorizonCode_Horizon_È(), (Object)new AttributeModifier(ItemTool.Â, "Tool modifier", this.áˆºÑ¢Õ, 0));
        return var1;
    }
}

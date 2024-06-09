package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Multimap;

public class ItemSword extends Item_1028566121
{
    private float à;
    private final HorizonCode_Horizon_È Ø;
    private static final String áŒŠÆ = "CL_00000072";
    
    public ItemSword(final HorizonCode_Horizon_È p_i45356_1_) {
        this.Ø = p_i45356_1_;
        this.Ø­áŒŠá = 1;
        this.Ø­áŒŠá(p_i45356_1_.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(CreativeTabs.áˆºÑ¢Õ);
        this.à = 4.0f + p_i45356_1_.Ý();
    }
    
    public float ˆà() {
        return this.Ø.Ý();
    }
    
    @Override
    public float HorizonCode_Horizon_È(final ItemStack stack, final Block p_150893_2_) {
        if (p_150893_2_ == Blocks.É) {
            return 15.0f;
        }
        final Material var3 = p_150893_2_.Ó();
        return (var3 != Material.ÂµÈ && var3 != Material.á && var3 != Material.Æ && var3 != Material.áˆºÑ¢Õ && var3 != Material.Çªà¢) ? 1.0f : 1.5f;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        stack.HorizonCode_Horizon_È(1, attacker);
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final Block blockIn, final BlockPos pos, final EntityLivingBase playerIn) {
        if (blockIn.Â(worldIn, pos) != 0.0) {
            stack.HorizonCode_Horizon_È(2, playerIn);
        }
        return true;
    }
    
    @Override
    public boolean Ó() {
        return true;
    }
    
    @Override
    public EnumAction Ý(final ItemStack stack) {
        return EnumAction.Ø­áŒŠá;
    }
    
    @Override
    public int Ø­áŒŠá(final ItemStack stack) {
        return 72000;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        playerIn.Â(itemStackIn, this.Ø­áŒŠá(itemStackIn));
        return itemStackIn;
    }
    
    @Override
    public boolean Â(final Block blockIn) {
        return blockIn == Blocks.É;
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
        var1.put((Object)SharedMonsterAttributes.Âµá€.HorizonCode_Horizon_È(), (Object)new AttributeModifier(ItemSword.Â, "Weapon modifier", this.à, 0));
        return var1;
    }
}

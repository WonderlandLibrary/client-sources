package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemAppleGold extends ItemFood
{
    private static final String Ø = "CL_00000037";
    
    public ItemAppleGold(final int p_i45341_1_, final float p_i45341_2_, final boolean p_i45341_3_) {
        super(p_i45341_1_, p_i45341_2_, p_i45341_3_);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public boolean Ø(final ItemStack stack) {
        return stack.Ø() > 0;
    }
    
    @Override
    public EnumRarity áŒŠÆ(final ItemStack stack) {
        return (stack.Ø() == 0) ? EnumRarity.Ý : EnumRarity.Ø­áŒŠá;
    }
    
    @Override
    protected void Ø­áŒŠá(final ItemStack p_77849_1_, final World worldIn, final EntityPlayer p_77849_3_) {
        if (!worldIn.ŠÄ) {
            p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(Potion.Ï­Ðƒà.É, 2400, 0));
        }
        if (p_77849_1_.Ø() > 0) {
            if (!worldIn.ŠÄ) {
                p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(Potion.á.É, 600, 4));
                p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(Potion.ˆÏ­.É, 6000, 0));
                p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(Potion.£á.É, 6000, 0));
            }
        }
        else {
            super.Ø­áŒŠá(p_77849_1_, worldIn, p_77849_3_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        subItems.add(new ItemStack(itemIn, 1, 0));
        subItems.add(new ItemStack(itemIn, 1, 1));
    }
}

package HORIZON-6-0-SKIDPROTECTION;

public class ItemBow extends Item_1028566121
{
    public static final String[] à;
    private static final String Ø = "CL_00001777";
    
    static {
        à = new String[] { "pulling_0", "pulling_1", "pulling_2" };
    }
    
    public ItemBow() {
        this.Ø­áŒŠá = 1;
        this.Ø­áŒŠá(384);
        this.HorizonCode_Horizon_È(CreativeTabs.áˆºÑ¢Õ);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final EntityPlayer playerIn, final int timeLeft) {
        final boolean var5 = playerIn.áˆºáˆºáŠ.Ø­áŒŠá || EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.áŒŠà.ŒÏ, stack) > 0;
        if (var5 || playerIn.Ø­Ñ¢Ï­Ø­áˆº.Â(Items.à)) {
            final int var6 = this.Ø­áŒŠá(stack) - timeLeft;
            float var7 = var6 / 20.0f;
            var7 = (var7 * var7 + var7 * 2.0f) / 3.0f;
            if (var7 < 0.1) {
                return;
            }
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            final EntityArrow var8 = new EntityArrow(worldIn, playerIn, var7 * 2.0f);
            if (var7 == 1.0f) {
                var8.HorizonCode_Horizon_È(true);
            }
            final int var9 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Æ.ŒÏ, stack);
            if (var9 > 0) {
                var8.Â(var8.à() + var9 * 0.5 + 0.5);
            }
            final int var10 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Šáƒ.ŒÏ, stack);
            if (var10 > 0) {
                var8.HorizonCode_Horizon_È(var10);
            }
            if (EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ï­Ðƒà.ŒÏ, stack) > 0) {
                var8.Âµá€(100);
            }
            stack.HorizonCode_Horizon_È(1, playerIn);
            worldIn.HorizonCode_Horizon_È((Entity)playerIn, "random.bow", 1.0f, 1.0f / (ItemBow.Ý.nextFloat() * 0.4f + 1.2f) + var7 * 0.5f);
            if (var5) {
                var8.HorizonCode_Horizon_È = 2;
            }
            else {
                playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(Items.à);
            }
            playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
            if (!worldIn.ŠÄ) {
                worldIn.HorizonCode_Horizon_È(var8);
            }
        }
    }
    
    @Override
    public ItemStack Â(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        return stack;
    }
    
    @Override
    public int Ø­áŒŠá(final ItemStack stack) {
        return 72000;
    }
    
    @Override
    public EnumAction Ý(final ItemStack stack) {
        return EnumAction.Âµá€;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá || playerIn.Ø­Ñ¢Ï­Ø­áˆº.Â(Items.à)) {
            playerIn.Â(itemStackIn, this.Ø­áŒŠá(itemStackIn));
        }
        return itemStackIn;
    }
    
    @Override
    public int ˆÏ­() {
        return 1;
    }
}

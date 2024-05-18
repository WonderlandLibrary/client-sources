package HORIZON-6-0-SKIDPROTECTION;

public class InventoryMerchant implements IInventory
{
    private final IMerchant HorizonCode_Horizon_È;
    private ItemStack[] Â;
    private final EntityPlayer Ý;
    private MerchantRecipe Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001756";
    
    public InventoryMerchant(final EntityPlayer p_i1820_1_, final IMerchant p_i1820_2_) {
        this.Â = new ItemStack[3];
        this.Ý = p_i1820_1_;
        this.HorizonCode_Horizon_È = p_i1820_2_;
    }
    
    @Override
    public int áŒŠÆ() {
        return this.Â.length;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return this.Â[slotIn];
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.Â[index] == null) {
            return null;
        }
        if (index == 2) {
            final ItemStack var3 = this.Â[index];
            this.Â[index] = null;
            return var3;
        }
        if (this.Â[index].Â <= count) {
            final ItemStack var3 = this.Â[index];
            this.Â[index] = null;
            if (this.Ý(index)) {
                this.Ø­áŒŠá();
            }
            return var3;
        }
        final ItemStack var3 = this.Â[index].HorizonCode_Horizon_È(count);
        if (this.Â[index].Â == 0) {
            this.Â[index] = null;
        }
        if (this.Ý(index)) {
            this.Ø­áŒŠá();
        }
        return var3;
    }
    
    private boolean Ý(final int p_70469_1_) {
        return p_70469_1_ == 0 || p_70469_1_ == 1;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.Â[index] != null) {
            final ItemStack var2 = this.Â[index];
            this.Â[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.Â[index] = stack;
        if (stack != null && stack.Â > this.Ñ¢á()) {
            stack.Â = this.Ñ¢á();
        }
        if (this.Ý(index)) {
            this.Ø­áŒŠá();
        }
    }
    
    @Override
    public String v_() {
        return "mob.villager";
    }
    
    @Override
    public boolean j_() {
        return false;
    }
    
    @Override
    public IChatComponent Ý() {
        return this.j_() ? new ChatComponentText(this.v_()) : new ChatComponentTranslation(this.v_(), new Object[0]);
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È() == playerIn;
    }
    
    @Override
    public void Âµá€(final EntityPlayer playerIn) {
    }
    
    @Override
    public void Ó(final EntityPlayer playerIn) {
    }
    
    @Override
    public boolean Ø­áŒŠá(final int index, final ItemStack stack) {
        return true;
    }
    
    @Override
    public void ŠÄ() {
        this.Ø­áŒŠá();
    }
    
    public void Ø­áŒŠá() {
        this.Ø­áŒŠá = null;
        ItemStack var1 = this.Â[0];
        ItemStack var2 = this.Â[1];
        if (var1 == null) {
            var1 = var2;
            var2 = null;
        }
        if (var1 == null) {
            this.Ý(2, null);
        }
        else {
            final MerchantRecipeList var3 = this.HorizonCode_Horizon_È.Â(this.Ý);
            if (var3 != null) {
                MerchantRecipe var4 = var3.HorizonCode_Horizon_È(var1, var2, this.Âµá€);
                if (var4 != null && !var4.Ø()) {
                    this.Ø­áŒŠá = var4;
                    this.Ý(2, var4.Ø­áŒŠá().áˆºÑ¢Õ());
                }
                else if (var2 != null) {
                    var4 = var3.HorizonCode_Horizon_È(var2, var1, this.Âµá€);
                    if (var4 != null && !var4.Ø()) {
                        this.Ø­áŒŠá = var4;
                        this.Ý(2, var4.Ø­áŒŠá().áˆºÑ¢Õ());
                    }
                    else {
                        this.Ý(2, null);
                    }
                }
                else {
                    this.Ý(2, null);
                }
            }
        }
        this.HorizonCode_Horizon_È.a_(this.á(2));
    }
    
    public MerchantRecipe Ó() {
        return this.Ø­áŒŠá;
    }
    
    public void Â(final int p_70471_1_) {
        this.Âµá€ = p_70471_1_;
        this.Ø­áŒŠá();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int id) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final int value) {
    }
    
    @Override
    public int Âµá€() {
        return 0;
    }
    
    @Override
    public void ŒÏ() {
        for (int var1 = 0; var1 < this.Â.length; ++var1) {
            this.Â[var1] = null;
        }
    }
}

package HORIZON-6-0-SKIDPROTECTION;

public class InventoryCraftResult implements IInventory
{
    private ItemStack[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00001760";
    
    public InventoryCraftResult() {
        this.HorizonCode_Horizon_È = new ItemStack[1];
    }
    
    @Override
    public int áŒŠÆ() {
        return 1;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return this.HorizonCode_Horizon_È[0];
    }
    
    @Override
    public String v_() {
        return "Result";
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
    public ItemStack Â(final int index, final int count) {
        if (this.HorizonCode_Horizon_È[0] != null) {
            final ItemStack var3 = this.HorizonCode_Horizon_È[0];
            this.HorizonCode_Horizon_È[0] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.HorizonCode_Horizon_È[0] != null) {
            final ItemStack var2 = this.HorizonCode_Horizon_È[0];
            this.HorizonCode_Horizon_È[0] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.HorizonCode_Horizon_È[0] = stack;
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    @Override
    public void ŠÄ() {
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return true;
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
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            this.HorizonCode_Horizon_È[var1] = null;
        }
    }
}

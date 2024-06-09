package HORIZON-6-0-SKIDPROTECTION;

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest HorizonCode_Horizon_È;
    private static final String Â = "CL_00001759";
    
    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }
    
    public void HorizonCode_Horizon_È(final TileEntityEnderChest chestTileEntity) {
        this.HorizonCode_Horizon_È = chestTileEntity;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagList p_70486_1_) {
        for (int var2 = 0; var2 < this.áŒŠÆ(); ++var2) {
            this.Ý(var2, null);
        }
        for (int var2 = 0; var2 < p_70486_1_.Âµá€(); ++var2) {
            final NBTTagCompound var3 = p_70486_1_.Â(var2);
            final int var4 = var3.Ø­áŒŠá("Slot") & 0xFF;
            if (var4 >= 0 && var4 < this.áŒŠÆ()) {
                this.Ý(var4, ItemStack.HorizonCode_Horizon_È(var3));
            }
        }
    }
    
    public NBTTagList Ø­áŒŠá() {
        final NBTTagList var1 = new NBTTagList();
        for (int var2 = 0; var2 < this.áŒŠÆ(); ++var2) {
            final ItemStack var3 = this.á(var2);
            if (var3 != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.HorizonCode_Horizon_È("Slot", (byte)var2);
                var3.Â(var4);
                var1.HorizonCode_Horizon_È(var4);
            }
        }
        return var1;
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return (this.HorizonCode_Horizon_È == null || this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(playerIn)) && super.Ø­áŒŠá(playerIn);
    }
    
    @Override
    public void Âµá€(final EntityPlayer playerIn) {
        if (this.HorizonCode_Horizon_È != null) {
            this.HorizonCode_Horizon_È.Â();
        }
        super.Âµá€(playerIn);
    }
    
    @Override
    public void Ó(final EntityPlayer playerIn) {
        if (this.HorizonCode_Horizon_È != null) {
            this.HorizonCode_Horizon_È.Ý();
        }
        super.Ó(playerIn);
        this.HorizonCode_Horizon_È = null;
    }
}

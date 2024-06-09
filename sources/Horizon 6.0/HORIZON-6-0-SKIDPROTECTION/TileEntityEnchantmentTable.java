package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class TileEntityEnchantmentTable extends TileEntity implements IUpdatePlayerListBox, IInteractionObject
{
    public int Âµá€;
    public float Ó;
    public float à;
    public float Ø;
    public float áŒŠÆ;
    public float áˆºÑ¢Õ;
    public float ÂµÈ;
    public float á;
    public float ˆÏ­;
    public float £á;
    private static Random Å;
    private String £à;
    private static final String µà = "CL_00000354";
    
    static {
        TileEntityEnchantmentTable.Å = new Random();
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        if (this.j_()) {
            compound.HorizonCode_Horizon_È("CustomName", this.£à);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        if (compound.Â("CustomName", 8)) {
            this.£à = compound.áˆºÑ¢Õ("CustomName");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÂµÈ = this.áˆºÑ¢Õ;
        this.ˆÏ­ = this.á;
        final EntityPlayer var1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â.HorizonCode_Horizon_È() + 0.5f, this.Â.Â() + 0.5f, this.Â.Ý() + 0.5f, 3.0);
        if (var1 != null) {
            final double var2 = var1.ŒÏ - (this.Â.HorizonCode_Horizon_È() + 0.5f);
            final double var3 = var1.Ê - (this.Â.Ý() + 0.5f);
            this.£á = (float)Math.atan2(var3, var2);
            this.áˆºÑ¢Õ += 0.1f;
            if (this.áˆºÑ¢Õ < 0.5f || TileEntityEnchantmentTable.Å.nextInt(40) == 0) {
                final float var4 = this.Ø;
                do {
                    this.Ø += TileEntityEnchantmentTable.Å.nextInt(4) - TileEntityEnchantmentTable.Å.nextInt(4);
                } while (var4 == this.Ø);
            }
        }
        else {
            this.£á += 0.02f;
            this.áˆºÑ¢Õ -= 0.1f;
        }
        while (this.á >= 3.1415927f) {
            this.á -= 6.2831855f;
        }
        while (this.á < -3.1415927f) {
            this.á += 6.2831855f;
        }
        while (this.£á >= 3.1415927f) {
            this.£á -= 6.2831855f;
        }
        while (this.£á < -3.1415927f) {
            this.£á += 6.2831855f;
        }
        float var5;
        for (var5 = this.£á - this.á; var5 >= 3.1415927f; var5 -= 6.2831855f) {}
        while (var5 < -3.1415927f) {
            var5 += 6.2831855f;
        }
        this.á += var5 * 0.4f;
        this.áˆºÑ¢Õ = MathHelper.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, 0.0f, 1.0f);
        ++this.Âµá€;
        this.à = this.Ó;
        float var6 = (this.Ø - this.Ó) * 0.4f;
        final float var7 = 0.2f;
        var6 = MathHelper.HorizonCode_Horizon_È(var6, -var7, var7);
        this.áŒŠÆ += (var6 - this.áŒŠÆ) * 0.9f;
        this.Ó += this.áŒŠÆ;
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.£à : "container.enchant";
    }
    
    @Override
    public boolean j_() {
        return this.£à != null && this.£à.length() > 0;
    }
    
    public void HorizonCode_Horizon_È(final String p_145920_1_) {
        this.£à = p_145920_1_;
    }
    
    @Override
    public IChatComponent Ý() {
        return this.j_() ? new ChatComponentText(this.v_()) : new ChatComponentTranslation(this.v_(), new Object[0]);
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerEnchantment(playerInventory, this.HorizonCode_Horizon_È, this.Â);
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:enchanting_table";
    }
}

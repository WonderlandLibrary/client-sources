package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class TileEntityDispenser extends TileEntityLockable implements IInventory
{
    private static final Random Ó;
    private ItemStack[] à;
    protected String Âµá€;
    private static final String Ø = "CL_00000352";
    
    static {
        Ó = new Random();
    }
    
    public TileEntityDispenser() {
        this.à = new ItemStack[9];
    }
    
    @Override
    public int áŒŠÆ() {
        return 9;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return this.à[slotIn];
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.à[index] == null) {
            return null;
        }
        if (this.à[index].Â <= count) {
            final ItemStack var3 = this.à[index];
            this.à[index] = null;
            this.ŠÄ();
            return var3;
        }
        final ItemStack var3 = this.à[index].HorizonCode_Horizon_È(count);
        if (this.à[index].Â == 0) {
            this.à[index] = null;
        }
        this.ŠÄ();
        return var3;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.à[index] != null) {
            final ItemStack var2 = this.à[index];
            this.à[index] = null;
            return var2;
        }
        return null;
    }
    
    public int Ø­à() {
        int var1 = -1;
        int var2 = 1;
        for (int var3 = 0; var3 < this.à.length; ++var3) {
            if (this.à[var3] != null && TileEntityDispenser.Ó.nextInt(var2++) == 0) {
                var1 = var3;
            }
        }
        return var1;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.à[index] = stack;
        if (stack != null && stack.Â > this.Ñ¢á()) {
            stack.Â = this.Ñ¢á();
        }
        this.ŠÄ();
    }
    
    public int HorizonCode_Horizon_È(final ItemStack p_146019_1_) {
        for (int var2 = 0; var2 < this.à.length; ++var2) {
            if (this.à[var2] == null || this.à[var2].HorizonCode_Horizon_È() == null) {
                this.Ý(var2, p_146019_1_);
                return var2;
            }
        }
        return -1;
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.Âµá€ : "container.dispenser";
    }
    
    public void HorizonCode_Horizon_È(final String p_146018_1_) {
        this.Âµá€ = p_146018_1_;
    }
    
    @Override
    public boolean j_() {
        return this.Âµá€ != null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        final NBTTagList var2 = compound.Ý("Items", 10);
        this.à = new ItemStack[this.áŒŠÆ()];
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final int var5 = var4.Ø­áŒŠá("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.à.length) {
                this.à[var5] = ItemStack.HorizonCode_Horizon_È(var4);
            }
        }
        if (compound.Â("CustomName", 8)) {
            this.Âµá€ = compound.áˆºÑ¢Õ("CustomName");
        }
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.à.length; ++var3) {
            if (this.à[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.HorizonCode_Horizon_È("Slot", (byte)var3);
                this.à[var3].Â(var4);
                var2.HorizonCode_Horizon_È(var4);
            }
        }
        compound.HorizonCode_Horizon_È("Items", var2);
        if (this.j_()) {
            compound.HorizonCode_Horizon_È("CustomName", this.Âµá€);
        }
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â) == this && playerIn.Âµá€(this.Â.HorizonCode_Horizon_È() + 0.5, this.Â.Â() + 0.5, this.Â.Ý() + 0.5) <= 64.0;
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
    public String Ø­áŒŠá() {
        return "minecraft:dispenser";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerDispenser(playerInventory, this);
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
        for (int var1 = 0; var1 < this.à.length; ++var1) {
            this.à[var1] = null;
        }
    }
}

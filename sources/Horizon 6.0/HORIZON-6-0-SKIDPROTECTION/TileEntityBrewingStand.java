package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Arrays;

public class TileEntityBrewingStand extends TileEntityLockable implements ISidedInventory, IUpdatePlayerListBox
{
    private static final int[] Âµá€;
    private static final int[] Ó;
    private ItemStack[] à;
    private int Ø;
    private boolean[] áŒŠÆ;
    private Item_1028566121 áˆºÑ¢Õ;
    private String ÂµÈ;
    private static final String á = "CL_00000345";
    
    static {
        Âµá€ = new int[] { 3 };
        Ó = new int[] { 0, 1, 2 };
    }
    
    public TileEntityBrewingStand() {
        this.à = new ItemStack[4];
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.ÂµÈ : "container.brewing";
    }
    
    @Override
    public boolean j_() {
        return this.ÂµÈ != null && this.ÂµÈ.length() > 0;
    }
    
    public void HorizonCode_Horizon_È(final String p_145937_1_) {
        this.ÂµÈ = p_145937_1_;
    }
    
    @Override
    public int áŒŠÆ() {
        return this.à.length;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Ø > 0) {
            --this.Ø;
            if (this.Ø == 0) {
                this.Æ();
                this.ŠÄ();
            }
            else if (!this.µÕ()) {
                this.Ø = 0;
                this.ŠÄ();
            }
            else if (this.áˆºÑ¢Õ != this.à[3].HorizonCode_Horizon_È()) {
                this.Ø = 0;
                this.ŠÄ();
            }
        }
        else if (this.µÕ()) {
            this.Ø = 400;
            this.áˆºÑ¢Õ = this.à[3].HorizonCode_Horizon_È();
        }
        if (!this.HorizonCode_Horizon_È.ŠÄ) {
            final boolean[] var1 = this.Ø­à();
            if (!Arrays.equals(var1, this.áŒŠÆ)) {
                this.áŒŠÆ = var1;
                IBlockState var2 = this.HorizonCode_Horizon_È.Â(this.á());
                if (!(var2.Ý() instanceof BlockBrewingStand)) {
                    return;
                }
                for (int var3 = 0; var3 < BlockBrewingStand.Õ.length; ++var3) {
                    var2 = var2.HorizonCode_Horizon_È(BlockBrewingStand.Õ[var3], var1[var3]);
                }
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, var2, 2);
            }
        }
    }
    
    private boolean µÕ() {
        if (this.à[3] == null || this.à[3].Â <= 0) {
            return false;
        }
        final ItemStack var1 = this.à[3];
        if (!var1.HorizonCode_Horizon_È().Ó(var1)) {
            return false;
        }
        boolean var2 = false;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.à[var3] != null && this.à[var3].HorizonCode_Horizon_È() == Items.µÂ) {
                final int var4 = this.à[var3].Ø();
                final int var5 = this.HorizonCode_Horizon_È(var4, var1);
                if (!ItemPotion.Ó(var4) && ItemPotion.Ó(var5)) {
                    var2 = true;
                    break;
                }
                final List var6 = Items.µÂ.Âµá€(var4);
                final List var7 = Items.µÂ.Âµá€(var5);
                if ((var4 <= 0 || var6 != var7) && (var6 == null || (!var6.equals(var7) && var7 != null)) && var4 != var5) {
                    var2 = true;
                    break;
                }
            }
        }
        return var2;
    }
    
    private void Æ() {
        if (this.µÕ()) {
            final ItemStack var1 = this.à[3];
            for (int var2 = 0; var2 < 3; ++var2) {
                if (this.à[var2] != null && this.à[var2].HorizonCode_Horizon_È() == Items.µÂ) {
                    final int var3 = this.à[var2].Ø();
                    final int var4 = this.HorizonCode_Horizon_È(var3, var1);
                    final List var5 = Items.µÂ.Âµá€(var3);
                    final List var6 = Items.µÂ.Âµá€(var4);
                    if ((var3 <= 0 || var5 != var6) && (var5 == null || (!var5.equals(var6) && var6 != null))) {
                        if (var3 != var4) {
                            this.à[var2].Â(var4);
                        }
                    }
                    else if (!ItemPotion.Ó(var3) && ItemPotion.Ó(var4)) {
                        this.à[var2].Â(var4);
                    }
                }
            }
            if (var1.HorizonCode_Horizon_È().ÂµÈ()) {
                this.à[3] = new ItemStack(var1.HorizonCode_Horizon_È().áˆºÑ¢Õ());
            }
            else {
                final ItemStack itemStack = this.à[3];
                --itemStack.Â;
                if (this.à[3].Â <= 0) {
                    this.à[3] = null;
                }
            }
        }
    }
    
    private int HorizonCode_Horizon_È(final int p_145936_1_, final ItemStack p_145936_2_) {
        return (p_145936_2_ == null) ? p_145936_1_ : (p_145936_2_.HorizonCode_Horizon_È().Ó(p_145936_2_) ? PotionHelper.HorizonCode_Horizon_È(p_145936_1_, p_145936_2_.HorizonCode_Horizon_È().Âµá€(p_145936_2_)) : p_145936_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        final NBTTagList var2 = compound.Ý("Items", 10);
        this.à = new ItemStack[this.áŒŠÆ()];
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final byte var5 = var4.Ø­áŒŠá("Slot");
            if (var5 >= 0 && var5 < this.à.length) {
                this.à[var5] = ItemStack.HorizonCode_Horizon_È(var4);
            }
        }
        this.Ø = compound.Âµá€("BrewTime");
        if (compound.Â("CustomName", 8)) {
            this.ÂµÈ = compound.áˆºÑ¢Õ("CustomName");
        }
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("BrewTime", (short)this.Ø);
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
            compound.HorizonCode_Horizon_È("CustomName", this.ÂµÈ);
        }
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return (slotIn >= 0 && slotIn < this.à.length) ? this.à[slotIn] : null;
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (index >= 0 && index < this.à.length) {
            final ItemStack var3 = this.à[index];
            this.à[index] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (index >= 0 && index < this.à.length) {
            final ItemStack var2 = this.à[index];
            this.à[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        if (index >= 0 && index < this.à.length) {
            this.à[index] = stack;
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
        return (index == 3) ? stack.HorizonCode_Horizon_È().Ó(stack) : (stack.HorizonCode_Horizon_È() == Items.µÂ || stack.HorizonCode_Horizon_È() == Items.Ñ¢ÇŽÏ);
    }
    
    public boolean[] Ø­à() {
        final boolean[] var1 = new boolean[3];
        for (int var2 = 0; var2 < 3; ++var2) {
            if (this.à[var2] != null) {
                var1[var2] = true;
            }
        }
        return var1;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final EnumFacing side) {
        return (side == EnumFacing.Â) ? TileEntityBrewingStand.Âµá€ : TileEntityBrewingStand.Ó;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int slotIn, final ItemStack itemStackIn, final EnumFacing direction) {
        return this.Ø­áŒŠá(slotIn, itemStackIn);
    }
    
    @Override
    public boolean Â(final int slotId, final ItemStack stack, final EnumFacing direction) {
        return true;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:brewing_stand";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerBrewingStand(playerInventory, this);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int id) {
        switch (id) {
            case 0: {
                return this.Ø;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final int value) {
        switch (id) {
            case 0: {
                this.Ø = value;
                break;
            }
        }
    }
    
    @Override
    public int Âµá€() {
        return 1;
    }
    
    @Override
    public void ŒÏ() {
        for (int var1 = 0; var1 < this.à.length; ++var1) {
            this.à[var1] = null;
        }
    }
}

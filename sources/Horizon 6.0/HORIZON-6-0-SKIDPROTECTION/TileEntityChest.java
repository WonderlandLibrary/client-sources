package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class TileEntityChest extends TileEntityLockable implements IInventory, IUpdatePlayerListBox
{
    private ItemStack[] ˆÏ­;
    public boolean Âµá€;
    public TileEntityChest Ó;
    public TileEntityChest à;
    public TileEntityChest Ø;
    public TileEntityChest áŒŠÆ;
    public float áˆºÑ¢Õ;
    public float ÂµÈ;
    public int á;
    private int £á;
    private int Å;
    private String £à;
    private static final String µà = "CL_00000346";
    
    public TileEntityChest() {
        this.ˆÏ­ = new ItemStack[27];
        this.Å = -1;
    }
    
    public TileEntityChest(final int p_i2350_1_) {
        this.ˆÏ­ = new ItemStack[27];
        this.Å = p_i2350_1_;
    }
    
    @Override
    public int áŒŠÆ() {
        return 27;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return this.ˆÏ­[slotIn];
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.ˆÏ­[index] == null) {
            return null;
        }
        if (this.ˆÏ­[index].Â <= count) {
            final ItemStack var3 = this.ˆÏ­[index];
            this.ˆÏ­[index] = null;
            this.ŠÄ();
            return var3;
        }
        final ItemStack var3 = this.ˆÏ­[index].HorizonCode_Horizon_È(count);
        if (this.ˆÏ­[index].Â == 0) {
            this.ˆÏ­[index] = null;
        }
        this.ŠÄ();
        return var3;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.ˆÏ­[index] != null) {
            final ItemStack var2 = this.ˆÏ­[index];
            this.ˆÏ­[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.ˆÏ­[index] = stack;
        if (stack != null && stack.Â > this.Ñ¢á()) {
            stack.Â = this.Ñ¢á();
        }
        this.ŠÄ();
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.£à : "container.chest";
    }
    
    @Override
    public boolean j_() {
        return this.£à != null && this.£à.length() > 0;
    }
    
    public void HorizonCode_Horizon_È(final String p_145976_1_) {
        this.£à = p_145976_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        final NBTTagList var2 = compound.Ý("Items", 10);
        this.ˆÏ­ = new ItemStack[this.áŒŠÆ()];
        if (compound.Â("CustomName", 8)) {
            this.£à = compound.áˆºÑ¢Õ("CustomName");
        }
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final int var5 = var4.Ø­áŒŠá("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.ˆÏ­.length) {
                this.ˆÏ­[var5] = ItemStack.HorizonCode_Horizon_È(var4);
            }
        }
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.ˆÏ­.length; ++var3) {
            if (this.ˆÏ­[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.HorizonCode_Horizon_È("Slot", (byte)var3);
                this.ˆÏ­[var3].Â(var4);
                var2.HorizonCode_Horizon_È(var4);
            }
        }
        compound.HorizonCode_Horizon_È("Items", var2);
        if (this.j_()) {
            compound.HorizonCode_Horizon_È("CustomName", this.£à);
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
    public void ˆà() {
        super.ˆà();
        this.Âµá€ = false;
    }
    
    private void HorizonCode_Horizon_È(final TileEntityChest p_174910_1_, final EnumFacing p_174910_2_) {
        if (p_174910_1_.Å()) {
            this.Âµá€ = false;
        }
        else if (this.Âµá€) {
            switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_174910_2_.ordinal()]) {
                case 1: {
                    if (this.Ó != p_174910_1_) {
                        this.Âµá€ = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.áŒŠÆ != p_174910_1_) {
                        this.Âµá€ = false;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.à != p_174910_1_) {
                        this.Âµá€ = false;
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.Ø != p_174910_1_) {
                        this.Âµá€ = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void Ø­à() {
        if (!this.Âµá€) {
            this.Âµá€ = true;
            this.Ø = this.HorizonCode_Horizon_È(EnumFacing.Âµá€);
            this.à = this.HorizonCode_Horizon_È(EnumFacing.Ó);
            this.Ó = this.HorizonCode_Horizon_È(EnumFacing.Ý);
            this.áŒŠÆ = this.HorizonCode_Horizon_È(EnumFacing.Ø­áŒŠá);
        }
    }
    
    protected TileEntityChest HorizonCode_Horizon_È(final EnumFacing p_174911_1_) {
        final BlockPos var2 = this.Â.HorizonCode_Horizon_È(p_174911_1_);
        if (this.Â(var2)) {
            final TileEntity var3 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
            if (var3 instanceof TileEntityChest) {
                final TileEntityChest var4 = (TileEntityChest)var3;
                var4.HorizonCode_Horizon_È(this, p_174911_1_.Âµá€());
                return var4;
            }
        }
        return null;
    }
    
    private boolean Â(final BlockPos p_174912_1_) {
        if (this.HorizonCode_Horizon_È == null) {
            return false;
        }
        final Block var2 = this.HorizonCode_Horizon_È.Â(p_174912_1_).Ý();
        return var2 instanceof BlockChest && ((BlockChest)var2).à¢ == this.µÕ();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ø­à();
        final int var1 = this.Â.HorizonCode_Horizon_È();
        final int var2 = this.Â.Â();
        final int var3 = this.Â.Ý();
        ++this.£á;
        if (!this.HorizonCode_Horizon_È.ŠÄ && this.á != 0 && (this.£á + var1 + var2 + var3) % 200 == 0) {
            this.á = 0;
            final float var4 = 5.0f;
            final List var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(EntityPlayer.class, new AxisAlignedBB(var1 - var4, var2 - var4, var3 - var4, var1 + 1 + var4, var2 + 1 + var4, var3 + 1 + var4));
            for (final EntityPlayer var7 : var5) {
                if (var7.Ï­Ï instanceof ContainerChest) {
                    final IInventory var8 = ((ContainerChest)var7.Ï­Ï).HorizonCode_Horizon_È();
                    if (var8 != this && (!(var8 instanceof InventoryLargeChest) || !((InventoryLargeChest)var8).HorizonCode_Horizon_È(this))) {
                        continue;
                    }
                    ++this.á;
                }
            }
        }
        this.ÂµÈ = this.áˆºÑ¢Õ;
        final float var4 = 0.1f;
        if (this.á > 0 && this.áˆºÑ¢Õ == 0.0f && this.Ó == null && this.Ø == null) {
            double var9 = var1 + 0.5;
            double var10 = var3 + 0.5;
            if (this.áŒŠÆ != null) {
                var10 += 0.5;
            }
            if (this.à != null) {
                var9 += 0.5;
            }
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var9, var2 + 0.5, var10, "random.chestopen", 0.5f, this.HorizonCode_Horizon_È.Å.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.á == 0 && this.áˆºÑ¢Õ > 0.0f) || (this.á > 0 && this.áˆºÑ¢Õ < 1.0f)) {
            final float var11 = this.áˆºÑ¢Õ;
            if (this.á > 0) {
                this.áˆºÑ¢Õ += var4;
            }
            else {
                this.áˆºÑ¢Õ -= var4;
            }
            if (this.áˆºÑ¢Õ > 1.0f) {
                this.áˆºÑ¢Õ = 1.0f;
            }
            final float var12 = 0.5f;
            if (this.áˆºÑ¢Õ < var12 && var11 >= var12 && this.Ó == null && this.Ø == null) {
                double var10 = var1 + 0.5;
                double var13 = var3 + 0.5;
                if (this.áŒŠÆ != null) {
                    var13 += 0.5;
                }
                if (this.à != null) {
                    var10 += 0.5;
                }
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var10, var2 + 0.5, var13, "random.chestclosed", 0.5f, this.HorizonCode_Horizon_È.Å.nextFloat() * 0.1f + 0.9f);
            }
            if (this.áˆºÑ¢Õ < 0.0f) {
                this.áˆºÑ¢Õ = 0.0f;
            }
        }
    }
    
    @Override
    public boolean Ý(final int id, final int type) {
        if (id == 1) {
            this.á = type;
            return true;
        }
        return super.Ý(id, type);
    }
    
    @Override
    public void Âµá€(final EntityPlayer playerIn) {
        if (!playerIn.Ø­áŒŠá()) {
            if (this.á < 0) {
                this.á = 0;
            }
            ++this.á;
            this.HorizonCode_Horizon_È.Ý(this.Â, this.ˆÏ­(), 1, this.á);
            this.HorizonCode_Horizon_È.Â(this.Â, this.ˆÏ­());
            this.HorizonCode_Horizon_È.Â(this.Â.Âµá€(), this.ˆÏ­());
        }
    }
    
    @Override
    public void Ó(final EntityPlayer playerIn) {
        if (!playerIn.Ø­áŒŠá() && this.ˆÏ­() instanceof BlockChest) {
            --this.á;
            this.HorizonCode_Horizon_È.Ý(this.Â, this.ˆÏ­(), 1, this.á);
            this.HorizonCode_Horizon_È.Â(this.Â, this.ˆÏ­());
            this.HorizonCode_Horizon_È.Â(this.Â.Âµá€(), this.ˆÏ­());
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final int index, final ItemStack stack) {
        return true;
    }
    
    @Override
    public void £à() {
        super.£à();
        this.ˆà();
        this.Ø­à();
    }
    
    public int µÕ() {
        if (this.Å == -1) {
            if (this.HorizonCode_Horizon_È == null || !(this.ˆÏ­() instanceof BlockChest)) {
                return 0;
            }
            this.Å = ((BlockChest)this.ˆÏ­()).à¢;
        }
        return this.Å;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:chest";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerChest(playerInventory, this, playerIn);
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
        for (int var1 = 0; var1 < this.ˆÏ­.length; ++var1) {
            this.ˆÏ­[var1] = null;
        }
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002041";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                TileEntityChest.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                TileEntityChest.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                TileEntityChest.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                TileEntityChest.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}

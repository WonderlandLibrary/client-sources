package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityFurnace extends TileEntityLockable implements ISidedInventory, IUpdatePlayerListBox
{
    private static final int[] Âµá€;
    private static final int[] Ó;
    private static final int[] à;
    private ItemStack[] Ø;
    private int áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private String ˆÏ­;
    private static final String £á = "CL_00000357";
    
    static {
        Âµá€ = new int[1];
        Ó = new int[] { 2, 1 };
        à = new int[] { 1 };
    }
    
    public TileEntityFurnace() {
        this.Ø = new ItemStack[3];
    }
    
    @Override
    public int áŒŠÆ() {
        return this.Ø.length;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return this.Ø[slotIn];
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.Ø[index] == null) {
            return null;
        }
        if (this.Ø[index].Â <= count) {
            final ItemStack var3 = this.Ø[index];
            this.Ø[index] = null;
            return var3;
        }
        final ItemStack var3 = this.Ø[index].HorizonCode_Horizon_È(count);
        if (this.Ø[index].Â == 0) {
            this.Ø[index] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.Ø[index] != null) {
            final ItemStack var2 = this.Ø[index];
            this.Ø[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        final boolean var3 = stack != null && stack.HorizonCode_Horizon_È(this.Ø[index]) && ItemStack.HorizonCode_Horizon_È(stack, this.Ø[index]);
        this.Ø[index] = stack;
        if (stack != null && stack.Â > this.Ñ¢á()) {
            stack.Â = this.Ñ¢á();
        }
        if (index == 0 && !var3) {
            this.á = this.HorizonCode_Horizon_È(stack);
            this.ÂµÈ = 0;
            this.ŠÄ();
        }
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.ˆÏ­ : "container.furnace";
    }
    
    @Override
    public boolean j_() {
        return this.ˆÏ­ != null && this.ˆÏ­.length() > 0;
    }
    
    public void HorizonCode_Horizon_È(final String p_145951_1_) {
        this.ˆÏ­ = p_145951_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        final NBTTagList var2 = compound.Ý("Items", 10);
        this.Ø = new ItemStack[this.áŒŠÆ()];
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final byte var5 = var4.Ø­áŒŠá("Slot");
            if (var5 >= 0 && var5 < this.Ø.length) {
                this.Ø[var5] = ItemStack.HorizonCode_Horizon_È(var4);
            }
        }
        this.áŒŠÆ = compound.Âµá€("BurnTime");
        this.ÂµÈ = compound.Âµá€("CookTime");
        this.á = compound.Âµá€("CookTimeTotal");
        this.áˆºÑ¢Õ = Â(this.Ø[1]);
        if (compound.Â("CustomName", 8)) {
            this.ˆÏ­ = compound.áˆºÑ¢Õ("CustomName");
        }
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("BurnTime", (short)this.áŒŠÆ);
        compound.HorizonCode_Horizon_È("CookTime", (short)this.ÂµÈ);
        compound.HorizonCode_Horizon_È("CookTimeTotal", (short)this.á);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.Ø.length; ++var3) {
            if (this.Ø[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.HorizonCode_Horizon_È("Slot", (byte)var3);
                this.Ø[var3].Â(var4);
                var2.HorizonCode_Horizon_È(var4);
            }
        }
        compound.HorizonCode_Horizon_È("Items", var2);
        if (this.j_()) {
            compound.HorizonCode_Horizon_È("CustomName", this.ˆÏ­);
        }
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    public boolean Ø­à() {
        return this.áŒŠÆ > 0;
    }
    
    public static boolean HorizonCode_Horizon_È(final IInventory p_174903_0_) {
        return p_174903_0_.HorizonCode_Horizon_È(0) > 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        final boolean var1 = this.Ø­à();
        boolean var2 = false;
        if (this.Ø­à()) {
            --this.áŒŠÆ;
        }
        if (!this.HorizonCode_Horizon_È.ŠÄ) {
            if (!this.Ø­à() && (this.Ø[1] == null || this.Ø[0] == null)) {
                if (!this.Ø­à() && this.ÂµÈ > 0) {
                    this.ÂµÈ = MathHelper.HorizonCode_Horizon_È(this.ÂµÈ - 2, 0, this.á);
                }
            }
            else {
                if (!this.Ø­à() && this.Æ()) {
                    final int â = Â(this.Ø[1]);
                    this.áŒŠÆ = â;
                    this.áˆºÑ¢Õ = â;
                    if (this.Ø­à()) {
                        var2 = true;
                        if (this.Ø[1] != null) {
                            final ItemStack itemStack = this.Ø[1];
                            --itemStack.Â;
                            if (this.Ø[1].Â == 0) {
                                final Item_1028566121 var3 = this.Ø[1].HorizonCode_Horizon_È().áˆºÑ¢Õ();
                                this.Ø[1] = ((var3 != null) ? new ItemStack(var3) : null);
                            }
                        }
                    }
                }
                if (this.Ø­à() && this.Æ()) {
                    ++this.ÂµÈ;
                    if (this.ÂµÈ == this.á) {
                        this.ÂµÈ = 0;
                        this.á = this.HorizonCode_Horizon_È(this.Ø[0]);
                        this.µÕ();
                        var2 = true;
                    }
                }
                else {
                    this.ÂµÈ = 0;
                }
            }
            if (var1 != this.Ø­à()) {
                var2 = true;
                BlockFurnace.HorizonCode_Horizon_È(this.Ø­à(), this.HorizonCode_Horizon_È, this.Â);
            }
        }
        if (var2) {
            this.ŠÄ();
        }
    }
    
    public int HorizonCode_Horizon_È(final ItemStack p_174904_1_) {
        return 200;
    }
    
    private boolean Æ() {
        if (this.Ø[0] == null) {
            return false;
        }
        final ItemStack var1 = FurnaceRecipes.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.Ø[0]);
        return var1 != null && (this.Ø[2] == null || (this.Ø[2].HorizonCode_Horizon_È(var1) && ((this.Ø[2].Â < this.Ñ¢á() && this.Ø[2].Â < this.Ø[2].Â()) || this.Ø[2].Â < var1.Â())));
    }
    
    public void µÕ() {
        if (this.Æ()) {
            final ItemStack var1 = FurnaceRecipes.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.Ø[0]);
            if (this.Ø[2] == null) {
                this.Ø[2] = var1.áˆºÑ¢Õ();
            }
            else if (this.Ø[2].HorizonCode_Horizon_È() == var1.HorizonCode_Horizon_È()) {
                final ItemStack itemStack = this.Ø[2];
                ++itemStack.Â;
            }
            if (this.Ø[0].HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Šáƒ) && this.Ø[0].Ø() == 1 && this.Ø[1] != null && this.Ø[1].HorizonCode_Horizon_È() == Items.áŒŠáŠ) {
                this.Ø[1] = new ItemStack(Items.ˆÓ);
            }
            final ItemStack itemStack2 = this.Ø[0];
            --itemStack2.Â;
            if (this.Ø[0].Â <= 0) {
                this.Ø[0] = null;
            }
        }
    }
    
    public static int Â(final ItemStack p_145952_0_) {
        if (p_145952_0_ == null) {
            return 0;
        }
        final Item_1028566121 var1 = p_145952_0_.HorizonCode_Horizon_È();
        if (var1 instanceof ItemBlock && Block.HorizonCode_Horizon_È(var1) != Blocks.Â) {
            final Block var2 = Block.HorizonCode_Horizon_È(var1);
            if (var2 == Blocks.ÇŽÊ) {
                return 150;
            }
            if (var2.Ó() == Material.Ø­áŒŠá) {
                return 300;
            }
            if (var2 == Blocks.ÐƒÉ) {
                return 16000;
            }
        }
        return (var1 instanceof ItemTool && ((ItemTool)var1).¥Æ().equals("WOOD")) ? 200 : ((var1 instanceof ItemSword && ((ItemSword)var1).¥Æ().equals("WOOD")) ? 200 : ((var1 instanceof ItemHoe && ((ItemHoe)var1).ˆà().equals("WOOD")) ? 200 : ((var1 == Items.áŒŠà) ? 100 : ((var1 == Items.Ø) ? 1600 : ((var1 == Items.¥Ä) ? 20000 : ((var1 == Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø)) ? 100 : ((var1 == Items.Çªà) ? 2400 : 0)))))));
    }
    
    public static boolean Ý(final ItemStack p_145954_0_) {
        return Â(p_145954_0_) > 0;
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
        return index != 2 && (index != 1 || Ý(stack) || SlotFurnaceFuel.Ø­áŒŠá(stack));
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final EnumFacing side) {
        return (side == EnumFacing.HorizonCode_Horizon_È) ? TileEntityFurnace.Ó : ((side == EnumFacing.Â) ? TileEntityFurnace.Âµá€ : TileEntityFurnace.à);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int slotIn, final ItemStack itemStackIn, final EnumFacing direction) {
        return this.Ø­áŒŠá(slotIn, itemStackIn);
    }
    
    @Override
    public boolean Â(final int slotId, final ItemStack stack, final EnumFacing direction) {
        if (direction == EnumFacing.HorizonCode_Horizon_È && slotId == 1) {
            final Item_1028566121 var4 = stack.HorizonCode_Horizon_È();
            if (var4 != Items.ˆÓ && var4 != Items.áŒŠáŠ) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:furnace";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerFurnace(playerInventory, this);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int id) {
        switch (id) {
            case 0: {
                return this.áŒŠÆ;
            }
            case 1: {
                return this.áˆºÑ¢Õ;
            }
            case 2: {
                return this.ÂµÈ;
            }
            case 3: {
                return this.á;
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
                this.áŒŠÆ = value;
                break;
            }
            case 1: {
                this.áˆºÑ¢Õ = value;
                break;
            }
            case 2: {
                this.ÂµÈ = value;
                break;
            }
            case 3: {
                this.á = value;
                break;
            }
        }
    }
    
    @Override
    public int Âµá€() {
        return 4;
    }
    
    @Override
    public void ŒÏ() {
        for (int var1 = 0; var1 < this.Ø.length; ++var1) {
            this.Ø[var1] = null;
        }
    }
}

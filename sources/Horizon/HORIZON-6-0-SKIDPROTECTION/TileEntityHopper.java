package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class TileEntityHopper extends TileEntityLockable implements IUpdatePlayerListBox, IHopper
{
    private ItemStack[] Âµá€;
    private String Ó;
    private int à;
    private static final String Ø = "CL_00000359";
    
    public TileEntityHopper() {
        this.Âµá€ = new ItemStack[5];
        this.à = -1;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        final NBTTagList var2 = compound.Ý("Items", 10);
        this.Âµá€ = new ItemStack[this.áŒŠÆ()];
        if (compound.Â("CustomName", 8)) {
            this.Ó = compound.áˆºÑ¢Õ("CustomName");
        }
        this.à = compound.Ó("TransferCooldown");
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final byte var5 = var4.Ø­áŒŠá("Slot");
            if (var5 >= 0 && var5 < this.Âµá€.length) {
                this.Âµá€[var5] = ItemStack.HorizonCode_Horizon_È(var4);
            }
        }
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.Âµá€.length; ++var3) {
            if (this.Âµá€[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.HorizonCode_Horizon_È("Slot", (byte)var3);
                this.Âµá€[var3].Â(var4);
                var2.HorizonCode_Horizon_È(var4);
            }
        }
        compound.HorizonCode_Horizon_È("Items", var2);
        compound.HorizonCode_Horizon_È("TransferCooldown", this.à);
        if (this.j_()) {
            compound.HorizonCode_Horizon_È("CustomName", this.Ó);
        }
    }
    
    @Override
    public void ŠÄ() {
        super.ŠÄ();
    }
    
    @Override
    public int áŒŠÆ() {
        return this.Âµá€.length;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return this.Âµá€[slotIn];
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.Âµá€[index] == null) {
            return null;
        }
        if (this.Âµá€[index].Â <= count) {
            final ItemStack var3 = this.Âµá€[index];
            this.Âµá€[index] = null;
            return var3;
        }
        final ItemStack var3 = this.Âµá€[index].HorizonCode_Horizon_È(count);
        if (this.Âµá€[index].Â == 0) {
            this.Âµá€[index] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.Âµá€[index] != null) {
            final ItemStack var2 = this.Âµá€[index];
            this.Âµá€[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.Âµá€[index] = stack;
        if (stack != null && stack.Â > this.Ñ¢á()) {
            stack.Â = this.Ñ¢á();
        }
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.Ó : "container.hopper";
    }
    
    @Override
    public boolean j_() {
        return this.Ó != null && this.Ó.length() > 0;
    }
    
    public void HorizonCode_Horizon_È(final String customNameIn) {
        this.Ó = customNameIn;
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
    public void HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È != null && !this.HorizonCode_Horizon_È.ŠÄ) {
            --this.à;
            if (!this.µÕ()) {
                this.Â(0);
                this.Ø­à();
            }
        }
    }
    
    public boolean Ø­à() {
        if (this.HorizonCode_Horizon_È != null && !this.HorizonCode_Horizon_È.ŠÄ) {
            if (!this.µÕ() && BlockHopper.Ó(this.áˆºÑ¢Õ())) {
                boolean var1 = false;
                if (!this.Šáƒ()) {
                    var1 = this.áŒŠà();
                }
                if (!this.Ï­Ðƒà()) {
                    var1 = (HorizonCode_Horizon_È(this) || var1);
                }
                if (var1) {
                    this.Â(8);
                    this.ŠÄ();
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    private boolean Šáƒ() {
        for (final ItemStack var4 : this.Âµá€) {
            if (var4 != null) {
                return false;
            }
        }
        return true;
    }
    
    private boolean Ï­Ðƒà() {
        for (final ItemStack var4 : this.Âµá€) {
            if (var4 == null || var4.Â != var4.Â()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean áŒŠà() {
        final IInventory var1 = this.Çªà¢();
        if (var1 == null) {
            return false;
        }
        final EnumFacing var2 = BlockHopper.Âµá€(this.áˆºÑ¢Õ()).Âµá€();
        if (this.HorizonCode_Horizon_È(var1, var2)) {
            return false;
        }
        for (int var3 = 0; var3 < this.áŒŠÆ(); ++var3) {
            if (this.á(var3) != null) {
                final ItemStack var4 = this.á(var3).áˆºÑ¢Õ();
                final ItemStack var5 = HorizonCode_Horizon_È(var1, this.Â(var3, 1), var2);
                if (var5 == null || var5.Â == 0) {
                    var1.ŠÄ();
                    return true;
                }
                this.Ý(var3, var4);
            }
        }
        return false;
    }
    
    private boolean HorizonCode_Horizon_È(final IInventory p_174919_1_, final EnumFacing p_174919_2_) {
        if (p_174919_1_ instanceof ISidedInventory) {
            final ISidedInventory var3 = (ISidedInventory)p_174919_1_;
            final int[] var4 = var3.HorizonCode_Horizon_È(p_174919_2_);
            for (int var5 = 0; var5 < var4.length; ++var5) {
                final ItemStack var6 = var3.á(var4[var5]);
                if (var6 == null || var6.Â != var6.Â()) {
                    return false;
                }
            }
        }
        else {
            for (int var7 = p_174919_1_.áŒŠÆ(), var8 = 0; var8 < var7; ++var8) {
                final ItemStack var9 = p_174919_1_.á(var8);
                if (var9 == null || var9.Â != var9.Â()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean Â(final IInventory p_174917_0_, final EnumFacing p_174917_1_) {
        if (p_174917_0_ instanceof ISidedInventory) {
            final ISidedInventory var2 = (ISidedInventory)p_174917_0_;
            final int[] var3 = var2.HorizonCode_Horizon_È(p_174917_1_);
            for (int var4 = 0; var4 < var3.length; ++var4) {
                if (var2.á(var3[var4]) != null) {
                    return false;
                }
            }
        }
        else {
            for (int var5 = p_174917_0_.áŒŠÆ(), var6 = 0; var6 < var5; ++var6) {
                if (p_174917_0_.á(var6) != null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean HorizonCode_Horizon_È(final IHopper p_145891_0_) {
        final IInventory var1 = Â(p_145891_0_);
        if (var1 != null) {
            final EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È;
            if (Â(var1, var2)) {
                return false;
            }
            if (var1 instanceof ISidedInventory) {
                final ISidedInventory var3 = (ISidedInventory)var1;
                final int[] var4 = var3.HorizonCode_Horizon_È(var2);
                for (int var5 = 0; var5 < var4.length; ++var5) {
                    if (HorizonCode_Horizon_È(p_145891_0_, var1, var4[var5], var2)) {
                        return true;
                    }
                }
            }
            else {
                for (int var6 = var1.áŒŠÆ(), var7 = 0; var7 < var6; ++var7) {
                    if (HorizonCode_Horizon_È(p_145891_0_, var1, var7, var2)) {
                        return true;
                    }
                }
            }
        }
        else {
            final EntityItem var8 = HorizonCode_Horizon_È(p_145891_0_.ÇŽÉ(), p_145891_0_.Š(), p_145891_0_.Ø­Ñ¢á€() + 1.0, p_145891_0_.Ñ¢Ó());
            if (var8 != null) {
                return HorizonCode_Horizon_È(p_145891_0_, var8);
            }
        }
        return false;
    }
    
    private static boolean HorizonCode_Horizon_È(final IHopper p_174915_0_, final IInventory p_174915_1_, final int p_174915_2_, final EnumFacing p_174915_3_) {
        final ItemStack var4 = p_174915_1_.á(p_174915_2_);
        if (var4 != null && Â(p_174915_1_, var4, p_174915_2_, p_174915_3_)) {
            final ItemStack var5 = var4.áˆºÑ¢Õ();
            final ItemStack var6 = HorizonCode_Horizon_È(p_174915_0_, p_174915_1_.Â(p_174915_2_, 1), null);
            if (var6 == null || var6.Â == 0) {
                p_174915_1_.ŠÄ();
                return true;
            }
            p_174915_1_.Ý(p_174915_2_, var5);
        }
        return false;
    }
    
    public static boolean HorizonCode_Horizon_È(final IInventory p_145898_0_, final EntityItem p_145898_1_) {
        boolean var2 = false;
        if (p_145898_1_ == null) {
            return false;
        }
        final ItemStack var3 = p_145898_1_.Ø().áˆºÑ¢Õ();
        final ItemStack var4 = HorizonCode_Horizon_È(p_145898_0_, var3, null);
        if (var4 != null && var4.Â != 0) {
            p_145898_1_.HorizonCode_Horizon_È(var4);
        }
        else {
            var2 = true;
            p_145898_1_.á€();
        }
        return var2;
    }
    
    public static ItemStack HorizonCode_Horizon_È(final IInventory p_174918_0_, ItemStack p_174918_1_, final EnumFacing p_174918_2_) {
        if (p_174918_0_ instanceof ISidedInventory && p_174918_2_ != null) {
            final ISidedInventory var6 = (ISidedInventory)p_174918_0_;
            final int[] var7 = var6.HorizonCode_Horizon_È(p_174918_2_);
            for (int var8 = 0; var8 < var7.length && p_174918_1_ != null; p_174918_1_ = Ý(p_174918_0_, p_174918_1_, var7[var8], p_174918_2_), ++var8) {
                if (p_174918_1_.Â <= 0) {
                    break;
                }
            }
        }
        else {
            for (int var9 = p_174918_0_.áŒŠÆ(), var10 = 0; var10 < var9 && p_174918_1_ != null && p_174918_1_.Â > 0; p_174918_1_ = Ý(p_174918_0_, p_174918_1_, var10, p_174918_2_), ++var10) {}
        }
        if (p_174918_1_ != null && p_174918_1_.Â == 0) {
            p_174918_1_ = null;
        }
        return p_174918_1_;
    }
    
    private static boolean HorizonCode_Horizon_È(final IInventory p_174920_0_, final ItemStack p_174920_1_, final int p_174920_2_, final EnumFacing p_174920_3_) {
        return p_174920_0_.Ø­áŒŠá(p_174920_2_, p_174920_1_) && (!(p_174920_0_ instanceof ISidedInventory) || ((ISidedInventory)p_174920_0_).HorizonCode_Horizon_È(p_174920_2_, p_174920_1_, p_174920_3_));
    }
    
    private static boolean Â(final IInventory p_174921_0_, final ItemStack p_174921_1_, final int p_174921_2_, final EnumFacing p_174921_3_) {
        return !(p_174921_0_ instanceof ISidedInventory) || ((ISidedInventory)p_174921_0_).Â(p_174921_2_, p_174921_1_, p_174921_3_);
    }
    
    private static ItemStack Ý(final IInventory p_174916_0_, ItemStack p_174916_1_, final int p_174916_2_, final EnumFacing p_174916_3_) {
        final ItemStack var4 = p_174916_0_.á(p_174916_2_);
        if (HorizonCode_Horizon_È(p_174916_0_, p_174916_1_, p_174916_2_, p_174916_3_)) {
            boolean var5 = false;
            if (var4 == null) {
                p_174916_0_.Ý(p_174916_2_, p_174916_1_);
                p_174916_1_ = null;
                var5 = true;
            }
            else if (HorizonCode_Horizon_È(var4, p_174916_1_)) {
                final int var6 = p_174916_1_.Â() - var4.Â;
                final int var7 = Math.min(p_174916_1_.Â, var6);
                final ItemStack itemStack = p_174916_1_;
                itemStack.Â -= var7;
                final ItemStack itemStack2 = var4;
                itemStack2.Â += var7;
                var5 = (var7 > 0);
            }
            if (var5) {
                if (p_174916_0_ instanceof TileEntityHopper) {
                    final TileEntityHopper var8 = (TileEntityHopper)p_174916_0_;
                    if (var8.Æ()) {
                        var8.Â(8);
                    }
                    p_174916_0_.ŠÄ();
                }
                p_174916_0_.ŠÄ();
            }
        }
        return p_174916_1_;
    }
    
    private IInventory Çªà¢() {
        final EnumFacing var1 = BlockHopper.Âµá€(this.áˆºÑ¢Õ());
        return Â(this.ÇŽÉ(), this.Â.HorizonCode_Horizon_È() + var1.Ø(), this.Â.Â() + var1.áŒŠÆ(), this.Â.Ý() + var1.áˆºÑ¢Õ());
    }
    
    public static IInventory Â(final IHopper p_145884_0_) {
        return Â(p_145884_0_.ÇŽÉ(), p_145884_0_.Š(), p_145884_0_.Ø­Ñ¢á€() + 1.0, p_145884_0_.Ñ¢Ó());
    }
    
    public static EntityItem HorizonCode_Horizon_È(final World worldIn, final double p_145897_1_, final double p_145897_3_, final double p_145897_5_) {
        final List var7 = worldIn.HorizonCode_Horizon_È(EntityItem.class, new AxisAlignedBB(p_145897_1_, p_145897_3_, p_145897_5_, p_145897_1_ + 1.0, p_145897_3_ + 1.0, p_145897_5_ + 1.0), IEntitySelector.HorizonCode_Horizon_È);
        return (var7.size() > 0) ? var7.get(0) : null;
    }
    
    public static IInventory Â(final World worldIn, final double p_145893_1_, final double p_145893_3_, final double p_145893_5_) {
        Object var7 = null;
        final int var8 = MathHelper.Ý(p_145893_1_);
        final int var9 = MathHelper.Ý(p_145893_3_);
        final int var10 = MathHelper.Ý(p_145893_5_);
        final BlockPos var11 = new BlockPos(var8, var9, var10);
        final TileEntity var12 = worldIn.HorizonCode_Horizon_È(new BlockPos(var8, var9, var10));
        if (var12 instanceof IInventory) {
            var7 = var12;
            if (var7 instanceof TileEntityChest) {
                final Block var13 = worldIn.Â(new BlockPos(var8, var9, var10)).Ý();
                if (var13 instanceof BlockChest) {
                    var7 = ((BlockChest)var13).áŒŠÆ(worldIn, var11);
                }
            }
        }
        if (var7 == null) {
            final List var14 = worldIn.HorizonCode_Horizon_È((Entity)null, new AxisAlignedBB(p_145893_1_, p_145893_3_, p_145893_5_, p_145893_1_ + 1.0, p_145893_3_ + 1.0, p_145893_5_ + 1.0), IEntitySelector.Ý);
            if (var14.size() > 0) {
                var7 = var14.get(worldIn.Å.nextInt(var14.size()));
            }
        }
        return (IInventory)var7;
    }
    
    private static boolean HorizonCode_Horizon_È(final ItemStack stack1, final ItemStack stack2) {
        return stack1.HorizonCode_Horizon_È() == stack2.HorizonCode_Horizon_È() && stack1.Ø() == stack2.Ø() && stack1.Â <= stack1.Â() && ItemStack.HorizonCode_Horizon_È(stack1, stack2);
    }
    
    @Override
    public double Š() {
        return this.Â.HorizonCode_Horizon_È();
    }
    
    @Override
    public double Ø­Ñ¢á€() {
        return this.Â.Â();
    }
    
    @Override
    public double Ñ¢Ó() {
        return this.Â.Ý();
    }
    
    public void Â(final int ticks) {
        this.à = ticks;
    }
    
    public boolean µÕ() {
        return this.à > 0;
    }
    
    public boolean Æ() {
        return this.à <= 1;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "minecraft:hopper";
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerHopper(playerInventory, this, playerIn);
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
        for (int var1 = 0; var1 < this.Âµá€.length; ++var1) {
            this.Âµá€[var1] = null;
        }
    }
}

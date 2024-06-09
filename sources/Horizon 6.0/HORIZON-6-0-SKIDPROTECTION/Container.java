package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import java.util.Set;
import java.util.List;

public abstract class Container
{
    public List Â;
    public List Ý;
    public int Ø­áŒŠá;
    private short HorizonCode_Horizon_È;
    private int Ó;
    private int à;
    private final Set Ø;
    protected List Âµá€;
    private Set áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001730";
    
    public Container() {
        this.Â = Lists.newArrayList();
        this.Ý = Lists.newArrayList();
        this.Ó = -1;
        this.Ø = Sets.newHashSet();
        this.Âµá€ = Lists.newArrayList();
        this.áŒŠÆ = Sets.newHashSet();
    }
    
    protected Slot Â(final Slot p_75146_1_) {
        p_75146_1_.Ý = this.Ý.size();
        this.Ý.add(p_75146_1_);
        this.Â.add(null);
        return p_75146_1_;
    }
    
    public void HorizonCode_Horizon_È(final ICrafting p_75132_1_) {
        if (this.Âµá€.contains(p_75132_1_)) {
            throw new IllegalArgumentException("Listener already listening");
        }
        this.Âµá€.add(p_75132_1_);
        p_75132_1_.HorizonCode_Horizon_È(this, this.Â());
        this.Ý();
    }
    
    public void Â(final ICrafting p_82847_1_) {
        this.Âµá€.remove(p_82847_1_);
    }
    
    public List Â() {
        final ArrayList var1 = Lists.newArrayList();
        for (int var2 = 0; var2 < this.Ý.size(); ++var2) {
            var1.add(this.Ý.get(var2).HorizonCode_Horizon_È());
        }
        return var1;
    }
    
    public void Ý() {
        for (int var1 = 0; var1 < this.Ý.size(); ++var1) {
            final ItemStack var2 = this.Ý.get(var1).HorizonCode_Horizon_È();
            ItemStack var3 = this.Â.get(var1);
            if (!ItemStack.Â(var3, var2)) {
                var3 = ((var2 == null) ? null : var2.áˆºÑ¢Õ());
                this.Â.set(var1, var3);
                for (int var4 = 0; var4 < this.Âµá€.size(); ++var4) {
                    this.Âµá€.get(var4).HorizonCode_Horizon_È(this, var1, var3);
                }
            }
        }
    }
    
    public boolean Â(final EntityPlayer playerIn, final int id) {
        return false;
    }
    
    public Slot HorizonCode_Horizon_È(final IInventory p_75147_1_, final int p_75147_2_) {
        for (int var3 = 0; var3 < this.Ý.size(); ++var3) {
            final Slot var4 = this.Ý.get(var3);
            if (var4.HorizonCode_Horizon_È(p_75147_1_, p_75147_2_)) {
                return var4;
            }
        }
        return null;
    }
    
    public Slot HorizonCode_Horizon_È(final int p_75139_1_) {
        return this.Ý.get(p_75139_1_);
    }
    
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        final Slot var3 = this.Ý.get(index);
        return (var3 != null) ? var3.HorizonCode_Horizon_È() : null;
    }
    
    public ItemStack HorizonCode_Horizon_È(final int slotId, final int clickedButton, final int mode, final EntityPlayer playerIn) {
        ItemStack var5 = null;
        final InventoryPlayer var6 = playerIn.Ø­Ñ¢Ï­Ø­áˆº;
        if (mode == 5) {
            final int var7 = this.à;
            this.à = Ý(clickedButton);
            if ((var7 != 1 || this.à != 2) && var7 != this.à) {
                this.Ø­áŒŠá();
            }
            else if (var6.á() == null) {
                this.Ø­áŒŠá();
            }
            else if (this.à == 0) {
                this.Ó = Â(clickedButton);
                if (HorizonCode_Horizon_È(this.Ó, playerIn)) {
                    this.à = 1;
                    this.Ø.clear();
                }
                else {
                    this.Ø­áŒŠá();
                }
            }
            else if (this.à == 1) {
                final Slot var8 = this.Ý.get(slotId);
                if (var8 != null && HorizonCode_Horizon_È(var8, var6.á(), true) && var8.HorizonCode_Horizon_È(var6.á()) && var6.á().Â > this.Ø.size() && this.HorizonCode_Horizon_È(var8)) {
                    this.Ø.add(var8);
                }
            }
            else if (this.à == 2) {
                if (!this.Ø.isEmpty()) {
                    ItemStack var9 = var6.á().áˆºÑ¢Õ();
                    int var10 = var6.á().Â;
                    for (final Slot var12 : this.Ø) {
                        if (var12 != null && HorizonCode_Horizon_È(var12, var6.á(), true) && var12.HorizonCode_Horizon_È(var6.á()) && var6.á().Â >= this.Ø.size() && this.HorizonCode_Horizon_È(var12)) {
                            final ItemStack var13 = var9.áˆºÑ¢Õ();
                            final int var14 = var12.Â() ? var12.HorizonCode_Horizon_È().Â : 0;
                            HorizonCode_Horizon_È(this.Ø, this.Ó, var13, var14);
                            if (var13.Â > var13.Â()) {
                                var13.Â = var13.Â();
                            }
                            if (var13.Â > var12.Ý(var13)) {
                                var13.Â = var12.Ý(var13);
                            }
                            var10 -= var13.Â - var14;
                            var12.Â(var13);
                        }
                    }
                    var9.Â = var10;
                    if (var9.Â <= 0) {
                        var9 = null;
                    }
                    var6.Â(var9);
                }
                this.Ø­áŒŠá();
            }
            else {
                this.Ø­áŒŠá();
            }
        }
        else if (this.à != 0) {
            this.Ø­áŒŠá();
        }
        else if ((mode == 0 || mode == 1) && (clickedButton == 0 || clickedButton == 1)) {
            if (slotId == -999) {
                if (var6.á() != null) {
                    if (clickedButton == 0) {
                        playerIn.HorizonCode_Horizon_È(var6.á(), true);
                        var6.Â((ItemStack)null);
                    }
                    if (clickedButton == 1) {
                        playerIn.HorizonCode_Horizon_È(var6.á().HorizonCode_Horizon_È(1), true);
                        if (var6.á().Â == 0) {
                            var6.Â((ItemStack)null);
                        }
                    }
                }
            }
            else if (mode == 1) {
                if (slotId < 0) {
                    return null;
                }
                final Slot var15 = this.Ý.get(slotId);
                if (var15 != null && var15.HorizonCode_Horizon_È(playerIn)) {
                    final ItemStack var9 = this.HorizonCode_Horizon_È(playerIn, slotId);
                    if (var9 != null) {
                        final Item_1028566121 var16 = var9.HorizonCode_Horizon_È();
                        var5 = var9.áˆºÑ¢Õ();
                        if (var15.HorizonCode_Horizon_È() != null && var15.HorizonCode_Horizon_È().HorizonCode_Horizon_È() == var16) {
                            this.HorizonCode_Horizon_È(slotId, clickedButton, true, playerIn);
                        }
                    }
                }
            }
            else {
                if (slotId < 0) {
                    return null;
                }
                final Slot var15 = this.Ý.get(slotId);
                if (var15 != null) {
                    ItemStack var9 = var15.HorizonCode_Horizon_È();
                    final ItemStack var17 = var6.á();
                    if (var9 != null) {
                        var5 = var9.áˆºÑ¢Õ();
                    }
                    if (var9 == null) {
                        if (var17 != null && var15.HorizonCode_Horizon_È(var17)) {
                            int var18 = (clickedButton == 0) ? var17.Â : 1;
                            if (var18 > var15.Ý(var17)) {
                                var18 = var15.Ý(var17);
                            }
                            if (var17.Â >= var18) {
                                var15.Â(var17.HorizonCode_Horizon_È(var18));
                            }
                            if (var17.Â == 0) {
                                var6.Â((ItemStack)null);
                            }
                        }
                    }
                    else if (var15.HorizonCode_Horizon_È(playerIn)) {
                        if (var17 == null) {
                            final int var18 = (clickedButton == 0) ? var9.Â : ((var9.Â + 1) / 2);
                            final ItemStack var19 = var15.HorizonCode_Horizon_È(var18);
                            var6.Â(var19);
                            if (var9.Â == 0) {
                                var15.Â(null);
                            }
                            var15.HorizonCode_Horizon_È(playerIn, var6.á());
                        }
                        else if (var15.HorizonCode_Horizon_È(var17)) {
                            if (var9.HorizonCode_Horizon_È() == var17.HorizonCode_Horizon_È() && var9.Ø() == var17.Ø() && ItemStack.HorizonCode_Horizon_È(var9, var17)) {
                                int var18 = (clickedButton == 0) ? var17.Â : 1;
                                if (var18 > var15.Ý(var17) - var9.Â) {
                                    var18 = var15.Ý(var17) - var9.Â;
                                }
                                if (var18 > var17.Â() - var9.Â) {
                                    var18 = var17.Â() - var9.Â;
                                }
                                var17.HorizonCode_Horizon_È(var18);
                                if (var17.Â == 0) {
                                    var6.Â((ItemStack)null);
                                }
                                final ItemStack itemStack = var9;
                                itemStack.Â += var18;
                            }
                            else if (var17.Â <= var15.Ý(var17)) {
                                var15.Â(var17);
                                var6.Â(var9);
                            }
                        }
                        else if (var9.HorizonCode_Horizon_È() == var17.HorizonCode_Horizon_È() && var17.Â() > 1 && (!var9.Âµá€() || var9.Ø() == var17.Ø()) && ItemStack.HorizonCode_Horizon_È(var9, var17)) {
                            final int var18 = var9.Â;
                            if (var18 > 0 && var18 + var17.Â <= var17.Â()) {
                                final ItemStack itemStack2 = var17;
                                itemStack2.Â += var18;
                                var9 = var15.HorizonCode_Horizon_È(var18);
                                if (var9.Â == 0) {
                                    var15.Â(null);
                                }
                                var15.HorizonCode_Horizon_È(playerIn, var6.á());
                            }
                        }
                    }
                    var15.Ý();
                }
            }
        }
        else if (mode == 2 && clickedButton >= 0 && clickedButton < 9) {
            final Slot var15 = this.Ý.get(slotId);
            if (var15.HorizonCode_Horizon_È(playerIn)) {
                final ItemStack var9 = var6.á(clickedButton);
                boolean var20 = var9 == null || (var15.Â == var6 && var15.HorizonCode_Horizon_È(var9));
                int var18 = -1;
                if (!var20) {
                    var18 = var6.à();
                    var20 |= (var18 > -1);
                }
                if (var15.Â() && var20) {
                    final ItemStack var19 = var15.HorizonCode_Horizon_È();
                    var6.Ý(clickedButton, var19.áˆºÑ¢Õ());
                    if ((var15.Â != var6 || !var15.HorizonCode_Horizon_È(var9)) && var9 != null) {
                        if (var18 > -1) {
                            var6.HorizonCode_Horizon_È(var9);
                            var15.HorizonCode_Horizon_È(var19.Â);
                            var15.Â(null);
                            var15.HorizonCode_Horizon_È(playerIn, var19);
                        }
                    }
                    else {
                        var15.HorizonCode_Horizon_È(var19.Â);
                        var15.Â(var9);
                        var15.HorizonCode_Horizon_È(playerIn, var19);
                    }
                }
                else if (!var15.Â() && var9 != null && var15.HorizonCode_Horizon_È(var9)) {
                    var6.Ý(clickedButton, null);
                    var15.Â(var9);
                }
            }
        }
        else if (mode == 3 && playerIn.áˆºáˆºáŠ.Ø­áŒŠá && var6.á() == null && slotId >= 0) {
            final Slot var15 = this.Ý.get(slotId);
            if (var15 != null && var15.Â()) {
                final ItemStack var9 = var15.HorizonCode_Horizon_È().áˆºÑ¢Õ();
                var9.Â = var9.Â();
                var6.Â(var9);
            }
        }
        else if (mode == 4 && var6.á() == null && slotId >= 0) {
            final Slot var15 = this.Ý.get(slotId);
            if (var15 != null && var15.Â() && var15.HorizonCode_Horizon_È(playerIn)) {
                final ItemStack var9 = var15.HorizonCode_Horizon_È((clickedButton == 0) ? 1 : var15.HorizonCode_Horizon_È().Â);
                var15.HorizonCode_Horizon_È(playerIn, var9);
                playerIn.HorizonCode_Horizon_È(var9, true);
            }
        }
        else if (mode == 6 && slotId >= 0) {
            final Slot var15 = this.Ý.get(slotId);
            final ItemStack var9 = var6.á();
            if (var9 != null && (var15 == null || !var15.Â() || !var15.HorizonCode_Horizon_È(playerIn))) {
                final int var10 = (clickedButton == 0) ? 0 : (this.Ý.size() - 1);
                final int var18 = (clickedButton == 0) ? 1 : -1;
                for (int var21 = 0; var21 < 2; ++var21) {
                    for (int var22 = var10; var22 >= 0 && var22 < this.Ý.size() && var9.Â < var9.Â(); var22 += var18) {
                        final Slot var23 = this.Ý.get(var22);
                        if (var23.Â() && HorizonCode_Horizon_È(var23, var9, true) && var23.HorizonCode_Horizon_È(playerIn) && this.HorizonCode_Horizon_È(var9, var23) && (var21 != 0 || var23.HorizonCode_Horizon_È().Â != var23.HorizonCode_Horizon_È().Â())) {
                            final int var24 = Math.min(var9.Â() - var9.Â, var23.HorizonCode_Horizon_È().Â);
                            final ItemStack var25 = var23.HorizonCode_Horizon_È(var24);
                            final ItemStack itemStack3 = var9;
                            itemStack3.Â += var24;
                            if (var25.Â <= 0) {
                                var23.Â(null);
                            }
                            var23.HorizonCode_Horizon_È(playerIn, var25);
                        }
                    }
                }
            }
            this.Ý();
        }
        return var5;
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return true;
    }
    
    protected void HorizonCode_Horizon_È(final int p_75133_1_, final int p_75133_2_, final boolean p_75133_3_, final EntityPlayer p_75133_4_) {
        this.HorizonCode_Horizon_È(p_75133_1_, p_75133_2_, 1, p_75133_4_);
    }
    
    public void Â(final EntityPlayer p_75134_1_) {
        final InventoryPlayer var2 = p_75134_1_.Ø­Ñ¢Ï­Ø­áˆº;
        if (var2.á() != null) {
            p_75134_1_.HorizonCode_Horizon_È(var2.á(), false);
            var2.Â((ItemStack)null);
        }
    }
    
    public void HorizonCode_Horizon_È(final IInventory p_75130_1_) {
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final int p_75141_1_, final ItemStack p_75141_2_) {
        this.HorizonCode_Horizon_È(p_75141_1_).Â(p_75141_2_);
    }
    
    public void HorizonCode_Horizon_È(final ItemStack[] p_75131_1_) {
        for (int var2 = 0; var2 < p_75131_1_.length; ++var2) {
            this.HorizonCode_Horizon_È(var2).Â(p_75131_1_[var2]);
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_75137_1_, final int p_75137_2_) {
    }
    
    public short HorizonCode_Horizon_È(final InventoryPlayer p_75136_1_) {
        return (short)(++this.HorizonCode_Horizon_È);
    }
    
    public boolean Ý(final EntityPlayer p_75129_1_) {
        return !this.áŒŠÆ.contains(p_75129_1_);
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_75128_1_, final boolean p_75128_2_) {
        if (p_75128_2_) {
            this.áŒŠÆ.remove(p_75128_1_);
        }
        else {
            this.áŒŠÆ.add(p_75128_1_);
        }
    }
    
    public abstract boolean HorizonCode_Horizon_È(final EntityPlayer p0);
    
    protected boolean HorizonCode_Horizon_È(final ItemStack stack, final int startIndex, final int endIndex, final boolean useEndIndex) {
        boolean var5 = false;
        int var6 = startIndex;
        if (useEndIndex) {
            var6 = endIndex - 1;
        }
        if (stack.Ý()) {
            while (stack.Â > 0 && ((!useEndIndex && var6 < endIndex) || (useEndIndex && var6 >= startIndex))) {
                final Slot var7 = this.Ý.get(var6);
                final ItemStack var8 = var7.HorizonCode_Horizon_È();
                if (var8 != null && var8.HorizonCode_Horizon_È() == stack.HorizonCode_Horizon_È() && (!stack.Âµá€() || stack.Ø() == var8.Ø()) && ItemStack.HorizonCode_Horizon_È(stack, var8)) {
                    final int var9 = var8.Â + stack.Â;
                    if (var9 <= stack.Â()) {
                        stack.Â = 0;
                        var8.Â = var9;
                        var7.Ý();
                        var5 = true;
                    }
                    else if (var8.Â < stack.Â()) {
                        stack.Â -= stack.Â() - var8.Â;
                        var8.Â = stack.Â();
                        var7.Ý();
                        var5 = true;
                    }
                }
                if (useEndIndex) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        if (stack.Â > 0) {
            if (useEndIndex) {
                var6 = endIndex - 1;
            }
            else {
                var6 = startIndex;
            }
            while ((!useEndIndex && var6 < endIndex) || (useEndIndex && var6 >= startIndex)) {
                final Slot var7 = this.Ý.get(var6);
                final ItemStack var8 = var7.HorizonCode_Horizon_È();
                if (var8 == null) {
                    var7.Â(stack.áˆºÑ¢Õ());
                    var7.Ý();
                    stack.Â = 0;
                    var5 = true;
                    break;
                }
                if (useEndIndex) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        return var5;
    }
    
    public static int Â(final int p_94529_0_) {
        return p_94529_0_ >> 2 & 0x3;
    }
    
    public static int Ý(final int p_94532_0_) {
        return p_94532_0_ & 0x3;
    }
    
    public static int Â(final int p_94534_0_, final int p_94534_1_) {
        return (p_94534_0_ & 0x3) | (p_94534_1_ & 0x3) << 2;
    }
    
    public static boolean HorizonCode_Horizon_È(final int p_180610_0_, final EntityPlayer p_180610_1_) {
        return p_180610_0_ == 0 || p_180610_0_ == 1 || (p_180610_0_ == 2 && p_180610_1_.áˆºáˆºáŠ.Ø­áŒŠá);
    }
    
    protected void Ø­áŒŠá() {
        this.à = 0;
        this.Ø.clear();
    }
    
    public static boolean HorizonCode_Horizon_È(final Slot slotIn, final ItemStack stack, final boolean stackSizeMatters) {
        boolean var3 = slotIn == null || !slotIn.Â();
        if (slotIn != null && slotIn.Â() && stack != null && stack.HorizonCode_Horizon_È(slotIn.HorizonCode_Horizon_È()) && ItemStack.HorizonCode_Horizon_È(slotIn.HorizonCode_Horizon_È(), stack)) {
            final int var4 = stackSizeMatters ? 0 : stack.Â;
            var3 |= (slotIn.HorizonCode_Horizon_È().Â + var4 <= stack.Â());
        }
        return var3;
    }
    
    public static void HorizonCode_Horizon_È(final Set p_94525_0_, final int p_94525_1_, final ItemStack p_94525_2_, final int p_94525_3_) {
        switch (p_94525_1_) {
            case 0: {
                p_94525_2_.Â = MathHelper.Ø­áŒŠá(p_94525_2_.Â / p_94525_0_.size());
                break;
            }
            case 1: {
                p_94525_2_.Â = 1;
                break;
            }
            case 2: {
                p_94525_2_.Â = p_94525_2_.HorizonCode_Horizon_È().HorizonCode_Horizon_È();
                break;
            }
        }
        p_94525_2_.Â += p_94525_3_;
    }
    
    public boolean HorizonCode_Horizon_È(final Slot p_94531_1_) {
        return true;
    }
    
    public static int HorizonCode_Horizon_È(final TileEntity te) {
        return (te instanceof IInventory) ? Â((IInventory)te) : 0;
    }
    
    public static int Â(final IInventory inv) {
        if (inv == null) {
            return 0;
        }
        int var1 = 0;
        float var2 = 0.0f;
        for (int var3 = 0; var3 < inv.áŒŠÆ(); ++var3) {
            final ItemStack var4 = inv.á(var3);
            if (var4 != null) {
                var2 += var4.Â / Math.min(inv.Ñ¢á(), var4.Â());
                ++var1;
            }
        }
        var2 /= inv.áŒŠÆ();
        return MathHelper.Ø­áŒŠá(var2 * 14.0f) + ((var1 > 0) ? 1 : 0);
    }
}

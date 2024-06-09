package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class ContainerEnchantment extends Container
{
    public IInventory HorizonCode_Horizon_È;
    private World áŒŠÆ;
    private BlockPos áˆºÑ¢Õ;
    private Random ÂµÈ;
    public int Ó;
    public int[] à;
    public int[] Ø;
    private static final String á = "CL_00001745";
    
    public ContainerEnchantment(final InventoryPlayer p_i45797_1_, final World worldIn) {
        this(p_i45797_1_, worldIn, BlockPos.HorizonCode_Horizon_È);
    }
    
    public ContainerEnchantment(final InventoryPlayer p_i45798_1_, final World worldIn, final BlockPos p_i45798_3_) {
        this.HorizonCode_Horizon_È = new InventoryBasic("Enchant", true, 2) {
            private static final String Â = "CL_00001746";
            
            @Override
            public int Ñ¢á() {
                return 64;
            }
            
            @Override
            public void ŠÄ() {
                super.ŠÄ();
                ContainerEnchantment.this.HorizonCode_Horizon_È(this);
            }
        };
        this.ÂµÈ = new Random();
        this.à = new int[3];
        this.Ø = new int[] { -1, -1, -1 };
        this.áŒŠÆ = worldIn;
        this.áˆºÑ¢Õ = p_i45798_3_;
        this.Ó = p_i45798_1_.Ø­áŒŠá.ˆÅ();
        this.Â(new Slot(this.HorizonCode_Horizon_È, 0, 15, 47) {
            private static final String Ó = "CL_00001747";
            
            @Override
            public boolean HorizonCode_Horizon_È(final ItemStack stack) {
                return true;
            }
            
            @Override
            public int Ø­áŒŠá() {
                return 1;
            }
        });
        this.Â(new Slot(this.HorizonCode_Horizon_È, 1, 35, 47) {
            private static final String Ó = "CL_00002185";
            
            @Override
            public boolean HorizonCode_Horizon_È(final ItemStack stack) {
                return stack.HorizonCode_Horizon_È() == Items.áŒŠÔ && EnumDyeColor.HorizonCode_Horizon_È(stack.Ø()) == EnumDyeColor.á;
            }
        });
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.Â(new Slot(p_i45798_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.Â(new Slot(p_i45798_1_, var4, 8 + var4 * 18, 142));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICrafting p_75132_1_) {
        super.HorizonCode_Horizon_È(p_75132_1_);
        p_75132_1_.HorizonCode_Horizon_È(this, 0, this.à[0]);
        p_75132_1_.HorizonCode_Horizon_È(this, 1, this.à[1]);
        p_75132_1_.HorizonCode_Horizon_È(this, 2, this.à[2]);
        p_75132_1_.HorizonCode_Horizon_È(this, 3, this.Ó & 0xFFFFFFF0);
        p_75132_1_.HorizonCode_Horizon_È(this, 4, this.Ø[0]);
        p_75132_1_.HorizonCode_Horizon_È(this, 5, this.Ø[1]);
        p_75132_1_.HorizonCode_Horizon_È(this, 6, this.Ø[2]);
    }
    
    @Override
    public void Ý() {
        super.Ý();
        for (int var1 = 0; var1 < this.Âµá€.size(); ++var1) {
            final ICrafting var2 = this.Âµá€.get(var1);
            var2.HorizonCode_Horizon_È(this, 0, this.à[0]);
            var2.HorizonCode_Horizon_È(this, 1, this.à[1]);
            var2.HorizonCode_Horizon_È(this, 2, this.à[2]);
            var2.HorizonCode_Horizon_È(this, 3, this.Ó & 0xFFFFFFF0);
            var2.HorizonCode_Horizon_È(this, 4, this.Ø[0]);
            var2.HorizonCode_Horizon_È(this, 5, this.Ø[1]);
            var2.HorizonCode_Horizon_È(this, 6, this.Ø[2]);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_75137_1_, final int p_75137_2_) {
        if (p_75137_1_ >= 0 && p_75137_1_ <= 2) {
            this.à[p_75137_1_] = p_75137_2_;
        }
        else if (p_75137_1_ == 3) {
            this.Ó = p_75137_2_;
        }
        else if (p_75137_1_ >= 4 && p_75137_1_ <= 6) {
            this.Ø[p_75137_1_ - 4] = p_75137_2_;
        }
        else {
            super.HorizonCode_Horizon_È(p_75137_1_, p_75137_2_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInventory p_75130_1_) {
        if (p_75130_1_ == this.HorizonCode_Horizon_È) {
            final ItemStack var2 = p_75130_1_.á(0);
            if (var2 != null && var2.Æ()) {
                if (!this.áŒŠÆ.ŠÄ) {
                    int var3 = 0;
                    for (int var4 = -1; var4 <= 1; ++var4) {
                        for (int var5 = -1; var5 <= 1; ++var5) {
                            if ((var4 != 0 || var5 != 0) && this.áŒŠÆ.Ø­áŒŠá(this.áˆºÑ¢Õ.Â(var5, 0, var4)) && this.áŒŠÆ.Ø­áŒŠá(this.áˆºÑ¢Õ.Â(var5, 1, var4))) {
                                if (this.áŒŠÆ.Â(this.áˆºÑ¢Õ.Â(var5 * 2, 0, var4 * 2)).Ý() == Blocks.Ï­à) {
                                    ++var3;
                                }
                                if (this.áŒŠÆ.Â(this.áˆºÑ¢Õ.Â(var5 * 2, 1, var4 * 2)).Ý() == Blocks.Ï­à) {
                                    ++var3;
                                }
                                if (var5 != 0 && var4 != 0) {
                                    if (this.áŒŠÆ.Â(this.áˆºÑ¢Õ.Â(var5 * 2, 0, var4)).Ý() == Blocks.Ï­à) {
                                        ++var3;
                                    }
                                    if (this.áŒŠÆ.Â(this.áˆºÑ¢Õ.Â(var5 * 2, 1, var4)).Ý() == Blocks.Ï­à) {
                                        ++var3;
                                    }
                                    if (this.áŒŠÆ.Â(this.áˆºÑ¢Õ.Â(var5, 0, var4 * 2)).Ý() == Blocks.Ï­à) {
                                        ++var3;
                                    }
                                    if (this.áŒŠÆ.Â(this.áˆºÑ¢Õ.Â(var5, 1, var4 * 2)).Ý() == Blocks.Ï­à) {
                                        ++var3;
                                    }
                                }
                            }
                        }
                    }
                    this.ÂµÈ.setSeed(this.Ó);
                    for (int var4 = 0; var4 < 3; ++var4) {
                        this.à[var4] = EnchantmentHelper.HorizonCode_Horizon_È(this.ÂµÈ, var4, var3, var2);
                        this.Ø[var4] = -1;
                        if (this.à[var4] < var4 + 1) {
                            this.à[var4] = 0;
                        }
                    }
                    for (int var4 = 0; var4 < 3; ++var4) {
                        if (this.à[var4] > 0) {
                            final List var6 = this.HorizonCode_Horizon_È(var2, var4, this.à[var4]);
                            if (var6 != null && !var6.isEmpty()) {
                                final EnchantmentData var7 = var6.get(this.ÂµÈ.nextInt(var6.size()));
                                this.Ø[var4] = (var7.HorizonCode_Horizon_È.ŒÏ | var7.Â << 8);
                            }
                        }
                    }
                    this.Ý();
                }
            }
            else {
                for (int var3 = 0; var3 < 3; ++var3) {
                    this.à[var3] = 0;
                    this.Ø[var3] = -1;
                }
            }
        }
    }
    
    @Override
    public boolean Â(final EntityPlayer playerIn, final int id) {
        final ItemStack var3 = this.HorizonCode_Horizon_È.á(0);
        final ItemStack var4 = this.HorizonCode_Horizon_È.á(1);
        final int var5 = id + 1;
        if ((var4 == null || var4.Â < var5) && !playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            return false;
        }
        if (this.à[id] > 0 && var3 != null && ((playerIn.áŒŠÉ >= var5 && playerIn.áŒŠÉ >= this.à[id]) || playerIn.áˆºáˆºáŠ.Ø­áŒŠá)) {
            if (!this.áŒŠÆ.ŠÄ) {
                final List var6 = this.HorizonCode_Horizon_È(var3, id, this.à[id]);
                final boolean var7 = var3.HorizonCode_Horizon_È() == Items.Ñ¢Ç;
                if (var6 != null) {
                    playerIn.¥Æ(var5);
                    if (var7) {
                        var3.HorizonCode_Horizon_È(Items.Çªáˆºá);
                    }
                    for (int var8 = 0; var8 < var6.size(); ++var8) {
                        final EnchantmentData var9 = var6.get(var8);
                        if (var7) {
                            Items.Çªáˆºá.HorizonCode_Horizon_È(var3, var9);
                        }
                        else {
                            var3.HorizonCode_Horizon_È(var9.HorizonCode_Horizon_È, var9.Â);
                        }
                    }
                    if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                        final ItemStack itemStack = var4;
                        itemStack.Â -= var5;
                        if (var4.Â <= 0) {
                            this.HorizonCode_Horizon_È.Ý(1, null);
                        }
                    }
                    this.HorizonCode_Horizon_È.ŠÄ();
                    this.Ó = playerIn.ˆÅ();
                    this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                }
            }
            return true;
        }
        return false;
    }
    
    private List HorizonCode_Horizon_È(final ItemStack p_178148_1_, final int p_178148_2_, final int p_178148_3_) {
        this.ÂµÈ.setSeed(this.Ó + p_178148_2_);
        final List var4 = EnchantmentHelper.Â(this.ÂµÈ, p_178148_1_, p_178148_3_);
        if (p_178148_1_.HorizonCode_Horizon_È() == Items.Ñ¢Ç && var4 != null && var4.size() > 1) {
            var4.remove(this.ÂµÈ.nextInt(var4.size()));
        }
        return var4;
    }
    
    public int HorizonCode_Horizon_È() {
        final ItemStack var1 = this.HorizonCode_Horizon_È.á(1);
        return (var1 == null) ? 0 : var1.Â;
    }
    
    @Override
    public void Â(final EntityPlayer p_75134_1_) {
        super.Â(p_75134_1_);
        if (!this.áŒŠÆ.ŠÄ) {
            for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.áŒŠÆ(); ++var2) {
                final ItemStack var3 = this.HorizonCode_Horizon_È.ˆÏ­(var2);
                if (var3 != null) {
                    p_75134_1_.HorizonCode_Horizon_È(var3, false);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return this.áŒŠÆ.Â(this.áˆºÑ¢Õ).Ý() == Blocks.¥Âµá€ && playerIn.Âµá€(this.áˆºÑ¢Õ.HorizonCode_Horizon_È() + 0.5, this.áˆºÑ¢Õ.Â() + 0.5, this.áˆºÑ¢Õ.Ý() + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if (index == 0) {
                if (!this.HorizonCode_Horizon_È(var5, 2, 38, true)) {
                    return null;
                }
            }
            else if (index == 1) {
                if (!this.HorizonCode_Horizon_È(var5, 2, 38, true)) {
                    return null;
                }
            }
            else if (var5.HorizonCode_Horizon_È() == Items.áŒŠÔ && EnumDyeColor.HorizonCode_Horizon_È(var5.Ø()) == EnumDyeColor.á) {
                if (!this.HorizonCode_Horizon_È(var5, 1, 2, true)) {
                    return null;
                }
            }
            else {
                if (this.Ý.get(0).Â() || !this.Ý.get(0).HorizonCode_Horizon_È(var5)) {
                    return null;
                }
                if (var5.£á() && var5.Â == 1) {
                    this.Ý.get(0).Â(var5.áˆºÑ¢Õ());
                    var5.Â = 0;
                }
                else if (var5.Â >= 1) {
                    this.Ý.get(0).Â(new ItemStack(var5.HorizonCode_Horizon_È(), 1, var5.Ø()));
                    final ItemStack itemStack = var5;
                    --itemStack.Â;
                }
            }
            if (var5.Â == 0) {
                var4.Â(null);
            }
            else {
                var4.Ý();
            }
            if (var5.Â == var3.Â) {
                return null;
            }
            var4.HorizonCode_Horizon_È(playerIn, var5);
        }
        return var3;
    }
}

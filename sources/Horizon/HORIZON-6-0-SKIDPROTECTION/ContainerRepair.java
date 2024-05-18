package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container
{
    private static final Logger Ó;
    private IInventory à;
    private IInventory Ø;
    private World áŒŠÆ;
    private BlockPos áˆºÑ¢Õ;
    public int HorizonCode_Horizon_È;
    private int ÂµÈ;
    private String á;
    private final EntityPlayer ˆÏ­;
    private static final String £á = "CL_00001732";
    
    static {
        Ó = LogManager.getLogger();
    }
    
    public ContainerRepair(final InventoryPlayer p_i45806_1_, final World worldIn, final EntityPlayer p_i45806_3_) {
        this(p_i45806_1_, worldIn, BlockPos.HorizonCode_Horizon_È, p_i45806_3_);
    }
    
    public ContainerRepair(final InventoryPlayer p_i45807_1_, final World worldIn, final BlockPos p_i45807_3_, final EntityPlayer p_i45807_4_) {
        this.à = new InventoryCraftResult();
        this.Ø = new InventoryBasic("Repair", true, 2) {
            private static final String Â = "CL_00001733";
            
            @Override
            public void ŠÄ() {
                super.ŠÄ();
                ContainerRepair.this.HorizonCode_Horizon_È(this);
            }
        };
        this.áˆºÑ¢Õ = p_i45807_3_;
        this.áŒŠÆ = worldIn;
        this.ˆÏ­ = p_i45807_4_;
        this.Â(new Slot(this.Ø, 0, 27, 47));
        this.Â(new Slot(this.Ø, 1, 76, 47));
        this.Â(new Slot(this.à, 2, 134, 47) {
            private static final String Ó = "CL_00001734";
            
            @Override
            public boolean HorizonCode_Horizon_È(final ItemStack stack) {
                return false;
            }
            
            @Override
            public boolean HorizonCode_Horizon_È(final EntityPlayer p_82869_1_) {
                return (p_82869_1_.áˆºáˆºáŠ.Ø­áŒŠá || p_82869_1_.áŒŠÉ >= ContainerRepair.this.HorizonCode_Horizon_È) && ContainerRepair.this.HorizonCode_Horizon_È > 0 && this.Â();
            }
            
            @Override
            public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final ItemStack stack) {
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    playerIn.Ø­à(-ContainerRepair.this.HorizonCode_Horizon_È);
                }
                ContainerRepair.this.Ø.Ý(0, null);
                if (ContainerRepair.this.ÂµÈ > 0) {
                    final ItemStack var3 = ContainerRepair.this.Ø.á(1);
                    if (var3 != null && var3.Â > ContainerRepair.this.ÂµÈ) {
                        final ItemStack itemStack = var3;
                        itemStack.Â -= ContainerRepair.this.ÂµÈ;
                        ContainerRepair.this.Ø.Ý(1, var3);
                    }
                    else {
                        ContainerRepair.this.Ø.Ý(1, null);
                    }
                }
                else {
                    ContainerRepair.this.Ø.Ý(1, null);
                }
                ContainerRepair.this.HorizonCode_Horizon_È = 0;
                final IBlockState var4 = worldIn.Â(p_i45807_3_);
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá && !worldIn.ŠÄ && var4.Ý() == Blocks.ÇªÅ && playerIn.ˆÐƒØ().nextFloat() < 0.12f) {
                    int var5 = (int)var4.HorizonCode_Horizon_È(BlockAnvil.à¢);
                    if (++var5 > 2) {
                        worldIn.Ø(p_i45807_3_);
                        worldIn.Â(1020, p_i45807_3_, 0);
                    }
                    else {
                        worldIn.HorizonCode_Horizon_È(p_i45807_3_, var4.HorizonCode_Horizon_È(BlockAnvil.à¢, var5), 2);
                        worldIn.Â(1021, p_i45807_3_, 0);
                    }
                }
                else if (!worldIn.ŠÄ) {
                    worldIn.Â(1021, p_i45807_3_, 0);
                }
            }
        });
        for (int var5 = 0; var5 < 3; ++var5) {
            for (int var6 = 0; var6 < 9; ++var6) {
                this.Â(new Slot(p_i45807_1_, var6 + var5 * 9 + 9, 8 + var6 * 18, 84 + var5 * 18));
            }
        }
        for (int var5 = 0; var5 < 9; ++var5) {
            this.Â(new Slot(p_i45807_1_, var5, 8 + var5 * 18, 142));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInventory p_75130_1_) {
        super.HorizonCode_Horizon_È(p_75130_1_);
        if (p_75130_1_ == this.Ø) {
            this.HorizonCode_Horizon_È();
        }
    }
    
    public void HorizonCode_Horizon_È() {
        final boolean var1 = false;
        final boolean var2 = true;
        final boolean var3 = true;
        final boolean var4 = true;
        final boolean var5 = true;
        final boolean var6 = true;
        final boolean var7 = true;
        final ItemStack var8 = this.Ø.á(0);
        this.HorizonCode_Horizon_È = 1;
        int var9 = 0;
        final byte var10 = 0;
        byte var11 = 0;
        if (var8 == null) {
            this.à.Ý(0, null);
            this.HorizonCode_Horizon_È = 0;
        }
        else {
            ItemStack var12 = var8.áˆºÑ¢Õ();
            final ItemStack var13 = this.Ø.á(1);
            final Map var14 = EnchantmentHelper.HorizonCode_Horizon_È(var12);
            boolean var15 = false;
            final int var16 = var10 + var8.Ñ¢á() + ((var13 == null) ? 0 : var13.Ñ¢á());
            this.ÂµÈ = 0;
            if (var13 != null) {
                var15 = (var13.HorizonCode_Horizon_È() == Items.Çªáˆºá && Items.Çªáˆºá.ÂµÈ(var13).Âµá€() > 0);
                if (var12.Ø­áŒŠá() && var12.HorizonCode_Horizon_È().HorizonCode_Horizon_È(var8, var13)) {
                    int var17 = Math.min(var12.à(), var12.áŒŠÆ() / 4);
                    if (var17 <= 0) {
                        this.à.Ý(0, null);
                        this.HorizonCode_Horizon_È = 0;
                        return;
                    }
                    int var18;
                    for (var18 = 0; var17 > 0 && var18 < var13.Â; var17 = Math.min(var12.à(), var12.áŒŠÆ() / 4), ++var18) {
                        final int var19 = var12.à() - var17;
                        var12.Â(var19);
                        ++var9;
                    }
                    this.ÂµÈ = var18;
                }
                else {
                    if (!var15 && (var12.HorizonCode_Horizon_È() != var13.HorizonCode_Horizon_È() || !var12.Ø­áŒŠá())) {
                        this.à.Ý(0, null);
                        this.HorizonCode_Horizon_È = 0;
                        return;
                    }
                    if (var12.Ø­áŒŠá() && !var15) {
                        final int var17 = var8.áŒŠÆ() - var8.à();
                        final int var18 = var13.áŒŠÆ() - var13.à();
                        final int var19 = var18 + var12.áŒŠÆ() * 12 / 100;
                        final int var20 = var17 + var19;
                        int var21 = var12.áŒŠÆ() - var20;
                        if (var21 < 0) {
                            var21 = 0;
                        }
                        if (var21 < var12.Ø()) {
                            var12.Â(var21);
                            var9 += 2;
                        }
                    }
                    final Map var22 = EnchantmentHelper.HorizonCode_Horizon_È(var13);
                    final Iterator var23 = var22.keySet().iterator();
                    while (var23.hasNext()) {
                        final int var19 = var23.next();
                        final Enchantment var24 = Enchantment.HorizonCode_Horizon_È(var19);
                        if (var24 != null) {
                            final int var21 = var14.containsKey(var19) ? var14.get(var19) : 0;
                            int var25 = var22.get(var19);
                            int var26;
                            if (var21 == var25) {
                                var26 = ++var25;
                            }
                            else {
                                var26 = Math.max(var25, var21);
                            }
                            var25 = var26;
                            boolean var27 = var24.HorizonCode_Horizon_È(var8);
                            if (this.ˆÏ­.áˆºáˆºáŠ.Ø­áŒŠá || var8.HorizonCode_Horizon_È() == Items.Çªáˆºá) {
                                var27 = true;
                            }
                            for (final int var29 : var14.keySet()) {
                                if (var29 != var19 && !var24.HorizonCode_Horizon_È(Enchantment.HorizonCode_Horizon_È(var29))) {
                                    var27 = false;
                                    ++var9;
                                }
                            }
                            if (!var27) {
                                continue;
                            }
                            if (var25 > var24.Ø­áŒŠá()) {
                                var25 = var24.Ø­áŒŠá();
                            }
                            var14.put(var19, var25);
                            int var30 = 0;
                            switch (var24.Â()) {
                                case 1: {
                                    var30 = 8;
                                    break;
                                }
                                case 2: {
                                    var30 = 4;
                                    break;
                                }
                                case 5: {
                                    var30 = 2;
                                    break;
                                }
                                case 10: {
                                    var30 = 1;
                                    break;
                                }
                            }
                            if (var15) {
                                var30 = Math.max(1, var30 / 2);
                            }
                            var9 += var30 * var25;
                        }
                    }
                }
            }
            if (StringUtils.isBlank((CharSequence)this.á)) {
                if (var8.¥Æ()) {
                    var11 = 1;
                    var9 += var11;
                    var12.ˆà();
                }
            }
            else if (!this.á.equals(var8.µà())) {
                var11 = 1;
                var9 += var11;
                var12.HorizonCode_Horizon_È(this.á);
            }
            this.HorizonCode_Horizon_È = var16 + var9;
            if (var9 <= 0) {
                var12 = null;
            }
            if (var11 == var9 && var11 > 0 && this.HorizonCode_Horizon_È >= 40) {
                this.HorizonCode_Horizon_È = 39;
            }
            if (this.HorizonCode_Horizon_È >= 40 && !this.ˆÏ­.áˆºáˆºáŠ.Ø­áŒŠá) {
                var12 = null;
            }
            if (var12 != null) {
                int var17 = var12.Ñ¢á();
                if (var13 != null && var17 < var13.Ñ¢á()) {
                    var17 = var13.Ñ¢á();
                }
                var17 = var17 * 2 + 1;
                var12.Ý(var17);
                EnchantmentHelper.HorizonCode_Horizon_È(var14, var12);
            }
            this.à.Ý(0, var12);
            this.Ý();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICrafting p_75132_1_) {
        super.HorizonCode_Horizon_È(p_75132_1_);
        p_75132_1_.HorizonCode_Horizon_È(this, 0, this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_75137_1_, final int p_75137_2_) {
        if (p_75137_1_ == 0) {
            this.HorizonCode_Horizon_È = p_75137_2_;
        }
    }
    
    @Override
    public void Â(final EntityPlayer p_75134_1_) {
        super.Â(p_75134_1_);
        if (!this.áŒŠÆ.ŠÄ) {
            for (int var2 = 0; var2 < this.Ø.áŒŠÆ(); ++var2) {
                final ItemStack var3 = this.Ø.ˆÏ­(var2);
                if (var3 != null) {
                    p_75134_1_.HorizonCode_Horizon_È(var3, false);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return this.áŒŠÆ.Â(this.áˆºÑ¢Õ).Ý() == Blocks.ÇªÅ && playerIn.Âµá€(this.áˆºÑ¢Õ.HorizonCode_Horizon_È() + 0.5, this.áˆºÑ¢Õ.Â() + 0.5, this.áˆºÑ¢Õ.Ý() + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if (index == 2) {
                if (!this.HorizonCode_Horizon_È(var5, 3, 39, true)) {
                    return null;
                }
                var4.HorizonCode_Horizon_È(var5, var3);
            }
            else if (index != 0 && index != 1) {
                if (index >= 3 && index < 39 && !this.HorizonCode_Horizon_È(var5, 0, 2, false)) {
                    return null;
                }
            }
            else if (!this.HorizonCode_Horizon_È(var5, 3, 39, false)) {
                return null;
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
    
    public void HorizonCode_Horizon_È(final String p_82850_1_) {
        this.á = p_82850_1_;
        if (this.HorizonCode_Horizon_È(2).Â()) {
            final ItemStack var2 = this.HorizonCode_Horizon_È(2).HorizonCode_Horizon_È();
            if (StringUtils.isBlank((CharSequence)p_82850_1_)) {
                var2.ˆà();
            }
            else {
                var2.HorizonCode_Horizon_È(this.á);
            }
        }
        this.HorizonCode_Horizon_È();
    }
}

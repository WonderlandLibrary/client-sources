package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class InventoryBasic implements IInventory
{
    private String HorizonCode_Horizon_È;
    private int Â;
    private ItemStack[] Ý;
    private List Ø­áŒŠá;
    private boolean Âµá€;
    private static final String Ó = "CL_00001514";
    
    public InventoryBasic(final String p_i1561_1_, final boolean p_i1561_2_, final int p_i1561_3_) {
        this.HorizonCode_Horizon_È = p_i1561_1_;
        this.Âµá€ = p_i1561_2_;
        this.Â = p_i1561_3_;
        this.Ý = new ItemStack[p_i1561_3_];
    }
    
    public InventoryBasic(final IChatComponent p_i45902_1_, final int p_i45902_2_) {
        this(p_i45902_1_.Ø(), true, p_i45902_2_);
    }
    
    public void HorizonCode_Horizon_È(final IInvBasic p_110134_1_) {
        if (this.Ø­áŒŠá == null) {
            this.Ø­áŒŠá = Lists.newArrayList();
        }
        this.Ø­áŒŠá.add(p_110134_1_);
    }
    
    public void Â(final IInvBasic p_110132_1_) {
        this.Ø­áŒŠá.remove(p_110132_1_);
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return (slotIn >= 0 && slotIn < this.Ý.length) ? this.Ý[slotIn] : null;
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.Ý[index] == null) {
            return null;
        }
        if (this.Ý[index].Â <= count) {
            final ItemStack var3 = this.Ý[index];
            this.Ý[index] = null;
            this.ŠÄ();
            return var3;
        }
        final ItemStack var3 = this.Ý[index].HorizonCode_Horizon_È(count);
        if (this.Ý[index].Â == 0) {
            this.Ý[index] = null;
        }
        this.ŠÄ();
        return var3;
    }
    
    public ItemStack HorizonCode_Horizon_È(final ItemStack p_174894_1_) {
        final ItemStack var2 = p_174894_1_.áˆºÑ¢Õ();
        for (int var3 = 0; var3 < this.Â; ++var3) {
            final ItemStack var4 = this.á(var3);
            if (var4 == null) {
                this.Ý(var3, var2);
                this.ŠÄ();
                return null;
            }
            if (ItemStack.Ý(var4, var2)) {
                final int var5 = Math.min(this.Ñ¢á(), var4.Â());
                final int var6 = Math.min(var2.Â, var5 - var4.Â);
                if (var6 > 0) {
                    final ItemStack itemStack = var4;
                    itemStack.Â += var6;
                    final ItemStack itemStack2 = var2;
                    itemStack2.Â -= var6;
                    if (var2.Â <= 0) {
                        this.ŠÄ();
                        return null;
                    }
                }
            }
        }
        if (var2.Â != p_174894_1_.Â) {
            this.ŠÄ();
        }
        return var2;
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.Ý[index] != null) {
            final ItemStack var2 = this.Ý[index];
            this.Ý[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.Ý[index] = stack;
        if (stack != null && stack.Â > this.Ñ¢á()) {
            stack.Â = this.Ñ¢á();
        }
        this.ŠÄ();
    }
    
    @Override
    public int áŒŠÆ() {
        return this.Â;
    }
    
    @Override
    public String v_() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean j_() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final String p_110133_1_) {
        this.Âµá€ = true;
        this.HorizonCode_Horizon_È = p_110133_1_;
    }
    
    @Override
    public IChatComponent Ý() {
        return this.j_() ? new ChatComponentText(this.v_()) : new ChatComponentTranslation(this.v_(), new Object[0]);
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    @Override
    public void ŠÄ() {
        if (this.Ø­áŒŠá != null) {
            for (int var1 = 0; var1 < this.Ø­áŒŠá.size(); ++var1) {
                this.Ø­áŒŠá.get(var1).HorizonCode_Horizon_È(this);
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return true;
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
        for (int var1 = 0; var1 < this.Ý.length; ++var1) {
            this.Ý[var1] = null;
        }
    }
}

package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;

public class InventoryPlayer implements IInventory
{
    public ItemStack[] HorizonCode_Horizon_È;
    public ItemStack[] Â;
    public int Ý;
    public EntityPlayer Ø­áŒŠá;
    private ItemStack Ó;
    public boolean Âµá€;
    private static final String à = "CL_00001709";
    
    public InventoryPlayer(final EntityPlayer p_i1750_1_) {
        this.HorizonCode_Horizon_È = new ItemStack[36];
        this.Â = new ItemStack[4];
        this.Ø­áŒŠá = p_i1750_1_;
    }
    
    public ItemStack Ø­áŒŠá() {
        return (this.Ý < 9 && this.Ý >= 0) ? this.HorizonCode_Horizon_È[this.Ý] : null;
    }
    
    public static int Ó() {
        return 9;
    }
    
    private int Ý(final Item_1028566121 itemIn) {
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            if (this.HorizonCode_Horizon_È[var2] != null && this.HorizonCode_Horizon_È[var2].HorizonCode_Horizon_È() == itemIn) {
                return var2;
            }
        }
        return -1;
    }
    
    private int HorizonCode_Horizon_È(final Item_1028566121 p_146024_1_, final int p_146024_2_) {
        for (int var3 = 0; var3 < this.HorizonCode_Horizon_È.length; ++var3) {
            if (this.HorizonCode_Horizon_È[var3] != null && this.HorizonCode_Horizon_È[var3].HorizonCode_Horizon_È() == p_146024_1_ && this.HorizonCode_Horizon_È[var3].Ø() == p_146024_2_) {
                return var3;
            }
        }
        return -1;
    }
    
    private int Ø­áŒŠá(final ItemStack p_70432_1_) {
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            if (this.HorizonCode_Horizon_È[var2] != null && this.HorizonCode_Horizon_È[var2].HorizonCode_Horizon_È() == p_70432_1_.HorizonCode_Horizon_È() && this.HorizonCode_Horizon_È[var2].Ý() && this.HorizonCode_Horizon_È[var2].Â < this.HorizonCode_Horizon_È[var2].Â() && this.HorizonCode_Horizon_È[var2].Â < this.Ñ¢á() && (!this.HorizonCode_Horizon_È[var2].Âµá€() || this.HorizonCode_Horizon_È[var2].Ø() == p_70432_1_.Ø()) && ItemStack.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È[var2], p_70432_1_)) {
                return var2;
            }
        }
        return -1;
    }
    
    public int à() {
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            if (this.HorizonCode_Horizon_È[var1] == null) {
                return var1;
            }
        }
        return -1;
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 p_146030_1_, final int p_146030_2_, final boolean p_146030_3_, final boolean p_146030_4_) {
        final ItemStack var5 = this.Ø­áŒŠá();
        final int var6 = p_146030_3_ ? this.HorizonCode_Horizon_È(p_146030_1_, p_146030_2_) : this.Ý(p_146030_1_);
        if (var6 >= 0 && var6 < 9) {
            this.Ý = var6;
        }
        else if (p_146030_4_ && p_146030_1_ != null) {
            final int var7 = this.à();
            if (var7 >= 0 && var7 < 9) {
                this.Ý = var7;
            }
            if (var5 == null || !var5.Æ() || this.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È(), var5.à()) != this.Ý) {
                final int var8 = this.HorizonCode_Horizon_È(p_146030_1_, p_146030_2_);
                int var9;
                if (var8 >= 0) {
                    var9 = this.HorizonCode_Horizon_È[var8].Â;
                    this.HorizonCode_Horizon_È[var8] = this.HorizonCode_Horizon_È[this.Ý];
                }
                else {
                    var9 = 1;
                }
                this.HorizonCode_Horizon_È[this.Ý] = new ItemStack(p_146030_1_, var9, p_146030_2_);
            }
        }
    }
    
    public void Â(int p_70453_1_) {
        if (p_70453_1_ > 0) {
            p_70453_1_ = 1;
        }
        if (p_70453_1_ < 0) {
            p_70453_1_ = -1;
        }
        this.Ý -= p_70453_1_;
        while (this.Ý < 0) {
            this.Ý += 9;
        }
        while (this.Ý >= 9) {
            this.Ý -= 9;
        }
    }
    
    public int HorizonCode_Horizon_È(final Item_1028566121 p_174925_1_, final int p_174925_2_, final int p_174925_3_, final NBTTagCompound p_174925_4_) {
        int var5 = 0;
        for (int var6 = 0; var6 < this.HorizonCode_Horizon_È.length; ++var6) {
            final ItemStack var7 = this.HorizonCode_Horizon_È[var6];
            if (var7 != null && (p_174925_1_ == null || var7.HorizonCode_Horizon_È() == p_174925_1_) && (p_174925_2_ <= -1 || var7.Ø() == p_174925_2_) && (p_174925_4_ == null || CommandTestForBlock.HorizonCode_Horizon_È(p_174925_4_, var7.Å(), true))) {
                final int var8 = (p_174925_3_ <= 0) ? var7.Â : Math.min(p_174925_3_ - var5, var7.Â);
                var5 += var8;
                if (p_174925_3_ != 0) {
                    final ItemStack itemStack = this.HorizonCode_Horizon_È[var6];
                    itemStack.Â -= var8;
                    if (this.HorizonCode_Horizon_È[var6].Â == 0) {
                        this.HorizonCode_Horizon_È[var6] = null;
                    }
                    if (p_174925_3_ > 0 && var5 >= p_174925_3_) {
                        return var5;
                    }
                }
            }
        }
        for (int var6 = 0; var6 < this.Â.length; ++var6) {
            final ItemStack var7 = this.Â[var6];
            if (var7 != null && (p_174925_1_ == null || var7.HorizonCode_Horizon_È() == p_174925_1_) && (p_174925_2_ <= -1 || var7.Ø() == p_174925_2_) && (p_174925_4_ == null || CommandTestForBlock.HorizonCode_Horizon_È(p_174925_4_, var7.Å(), false))) {
                final int var8 = (p_174925_3_ <= 0) ? var7.Â : Math.min(p_174925_3_ - var5, var7.Â);
                var5 += var8;
                if (p_174925_3_ != 0) {
                    final ItemStack itemStack2 = this.Â[var6];
                    itemStack2.Â -= var8;
                    if (this.Â[var6].Â == 0) {
                        this.Â[var6] = null;
                    }
                    if (p_174925_3_ > 0 && var5 >= p_174925_3_) {
                        return var5;
                    }
                }
            }
        }
        if (this.Ó != null) {
            if (p_174925_1_ != null && this.Ó.HorizonCode_Horizon_È() != p_174925_1_) {
                return var5;
            }
            if (p_174925_2_ > -1 && this.Ó.Ø() != p_174925_2_) {
                return var5;
            }
            if (p_174925_4_ != null && !CommandTestForBlock.HorizonCode_Horizon_È(p_174925_4_, this.Ó.Å(), false)) {
                return var5;
            }
            final int var6 = (p_174925_3_ <= 0) ? this.Ó.Â : Math.min(p_174925_3_ - var5, this.Ó.Â);
            var5 += var6;
            if (p_174925_3_ != 0) {
                final ItemStack ó = this.Ó;
                ó.Â -= var6;
                if (this.Ó.Â == 0) {
                    this.Ó = null;
                }
                if (p_174925_3_ > 0 && var5 >= p_174925_3_) {
                    return var5;
                }
            }
        }
        return var5;
    }
    
    private int Âµá€(final ItemStack p_70452_1_) {
        final Item_1028566121 var2 = p_70452_1_.HorizonCode_Horizon_È();
        int var3 = p_70452_1_.Â;
        int var4 = this.Ø­áŒŠá(p_70452_1_);
        if (var4 < 0) {
            var4 = this.à();
        }
        if (var4 < 0) {
            return var3;
        }
        if (this.HorizonCode_Horizon_È[var4] == null) {
            this.HorizonCode_Horizon_È[var4] = new ItemStack(var2, 0, p_70452_1_.Ø());
            if (p_70452_1_.£á()) {
                this.HorizonCode_Horizon_È[var4].Ø­áŒŠá((NBTTagCompound)p_70452_1_.Å().Â());
            }
        }
        int var5;
        if ((var5 = var3) > this.HorizonCode_Horizon_È[var4].Â() - this.HorizonCode_Horizon_È[var4].Â) {
            var5 = this.HorizonCode_Horizon_È[var4].Â() - this.HorizonCode_Horizon_È[var4].Â;
        }
        if (var5 > this.Ñ¢á() - this.HorizonCode_Horizon_È[var4].Â) {
            var5 = this.Ñ¢á() - this.HorizonCode_Horizon_È[var4].Â;
        }
        if (var5 == 0) {
            return var3;
        }
        var3 -= var5;
        final ItemStack itemStack = this.HorizonCode_Horizon_È[var4];
        itemStack.Â += var5;
        this.HorizonCode_Horizon_È[var4].Ý = 5;
        return var3;
    }
    
    public void Ø() {
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            if (this.HorizonCode_Horizon_È[var1] != null) {
                this.HorizonCode_Horizon_È[var1].HorizonCode_Horizon_È(this.Ø­áŒŠá.Ï­Ðƒà, this.Ø­áŒŠá, var1, this.Ý == var1);
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final Item_1028566121 p_146026_1_) {
        final int var2 = this.Ý(p_146026_1_);
        if (var2 < 0) {
            return false;
        }
        final ItemStack itemStack = this.HorizonCode_Horizon_È[var2];
        if (--itemStack.Â <= 0) {
            this.HorizonCode_Horizon_È[var2] = null;
        }
        return true;
    }
    
    public boolean Â(final Item_1028566121 p_146028_1_) {
        final int var2 = this.Ý(p_146028_1_);
        return var2 >= 0;
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack p_70441_1_) {
        if (p_70441_1_ != null && p_70441_1_.Â != 0 && p_70441_1_.HorizonCode_Horizon_È() != null) {
            try {
                if (p_70441_1_.Ó()) {
                    final int var2 = this.à();
                    if (var2 >= 0) {
                        this.HorizonCode_Horizon_È[var2] = ItemStack.Â(p_70441_1_);
                        this.HorizonCode_Horizon_È[var2].Ý = 5;
                        p_70441_1_.Â = 0;
                        return true;
                    }
                    if (this.Ø­áŒŠá.áˆºáˆºáŠ.Ø­áŒŠá) {
                        p_70441_1_.Â = 0;
                        return true;
                    }
                    return false;
                }
                else {
                    int var2;
                    do {
                        var2 = p_70441_1_.Â;
                        p_70441_1_.Â = this.Âµá€(p_70441_1_);
                    } while (p_70441_1_.Â > 0 && p_70441_1_.Â < var2);
                    if (p_70441_1_.Â == var2 && this.Ø­áŒŠá.áˆºáˆºáŠ.Ø­áŒŠá) {
                        p_70441_1_.Â = 0;
                        return true;
                    }
                    return p_70441_1_.Â < var2;
                }
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Adding item to inventory");
                final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Item being added");
                var5.HorizonCode_Horizon_È("Item ID", Item_1028566121.HorizonCode_Horizon_È(p_70441_1_.HorizonCode_Horizon_È()));
                var5.HorizonCode_Horizon_È("Item data", p_70441_1_.Ø());
                var5.HorizonCode_Horizon_È("Item name", new Callable() {
                    private static final String Â = "CL_00001710";
                    
                    public String HorizonCode_Horizon_È() {
                        return p_70441_1_.µà();
                    }
                });
                throw new ReportedException(var3);
            }
        }
        return false;
    }
    
    @Override
    public ItemStack Â(int index, final int count) {
        ItemStack[] var3 = this.HorizonCode_Horizon_È;
        if (index >= this.HorizonCode_Horizon_È.length) {
            var3 = this.Â;
            index -= this.HorizonCode_Horizon_È.length;
        }
        if (var3[index] == null) {
            return null;
        }
        if (var3[index].Â <= count) {
            final ItemStack var4 = var3[index];
            var3[index] = null;
            return var4;
        }
        final ItemStack var4 = var3[index].HorizonCode_Horizon_È(count);
        if (var3[index].Â == 0) {
            var3[index] = null;
        }
        return var4;
    }
    
    @Override
    public ItemStack ˆÏ­(int index) {
        ItemStack[] var2 = this.HorizonCode_Horizon_È;
        if (index >= this.HorizonCode_Horizon_È.length) {
            var2 = this.Â;
            index -= this.HorizonCode_Horizon_È.length;
        }
        if (var2[index] != null) {
            final ItemStack var3 = var2[index];
            var2[index] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public void Ý(int index, final ItemStack stack) {
        ItemStack[] var3 = this.HorizonCode_Horizon_È;
        if (index >= var3.length) {
            index -= var3.length;
            var3 = this.Â;
        }
        var3[index] = stack;
    }
    
    public float HorizonCode_Horizon_È(final Block p_146023_1_) {
        float var2 = 1.0f;
        if (this.HorizonCode_Horizon_È[this.Ý] != null) {
            var2 *= this.HorizonCode_Horizon_È[this.Ý].HorizonCode_Horizon_È(p_146023_1_);
        }
        return var2;
    }
    
    public NBTTagList HorizonCode_Horizon_È(final NBTTagList p_70442_1_) {
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            if (this.HorizonCode_Horizon_È[var2] != null) {
                final NBTTagCompound var3 = new NBTTagCompound();
                var3.HorizonCode_Horizon_È("Slot", (byte)var2);
                this.HorizonCode_Horizon_È[var2].Â(var3);
                p_70442_1_.HorizonCode_Horizon_È(var3);
            }
        }
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            if (this.Â[var2] != null) {
                final NBTTagCompound var3 = new NBTTagCompound();
                var3.HorizonCode_Horizon_È("Slot", (byte)(var2 + 100));
                this.Â[var2].Â(var3);
                p_70442_1_.HorizonCode_Horizon_È(var3);
            }
        }
        return p_70442_1_;
    }
    
    public void Â(final NBTTagList p_70443_1_) {
        this.HorizonCode_Horizon_È = new ItemStack[36];
        this.Â = new ItemStack[4];
        for (int var2 = 0; var2 < p_70443_1_.Âµá€(); ++var2) {
            final NBTTagCompound var3 = p_70443_1_.Â(var2);
            final int var4 = var3.Ø­áŒŠá("Slot") & 0xFF;
            final ItemStack var5 = ItemStack.HorizonCode_Horizon_È(var3);
            if (var5 != null) {
                if (var4 >= 0 && var4 < this.HorizonCode_Horizon_È.length) {
                    this.HorizonCode_Horizon_È[var4] = var5;
                }
                if (var4 >= 100 && var4 < this.Â.length + 100) {
                    this.Â[var4 - 100] = var5;
                }
            }
        }
    }
    
    @Override
    public int áŒŠÆ() {
        return this.HorizonCode_Horizon_È.length + 4;
    }
    
    @Override
    public ItemStack á(int slotIn) {
        ItemStack[] var2 = this.HorizonCode_Horizon_È;
        if (slotIn >= var2.length) {
            slotIn -= var2.length;
            var2 = this.Â;
        }
        return var2[slotIn];
    }
    
    @Override
    public String v_() {
        return "container.inventory";
    }
    
    @Override
    public boolean j_() {
        return false;
    }
    
    @Override
    public IChatComponent Ý() {
        return this.j_() ? new ChatComponentText(this.v_()) : new ChatComponentTranslation(this.v_(), new Object[0]);
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    public boolean Â(final Block p_146025_1_) {
        if (p_146025_1_.Ó().ÂµÈ()) {
            return true;
        }
        final ItemStack var2 = this.á(this.Ý);
        return var2 != null && var2.Â(p_146025_1_);
    }
    
    public ItemStack Ý(final int p_70440_1_) {
        return this.Â[p_70440_1_];
    }
    
    public int áˆºÑ¢Õ() {
        int var1 = 0;
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            if (this.Â[var2] != null && this.Â[var2].HorizonCode_Horizon_È() instanceof ItemArmor) {
                final int var3 = ((ItemArmor)this.Â[var2].HorizonCode_Horizon_È()).áŒŠÆ;
                var1 += var3;
            }
        }
        return var1;
    }
    
    public void HorizonCode_Horizon_È(float p_70449_1_) {
        p_70449_1_ /= 4.0f;
        if (p_70449_1_ < 1.0f) {
            p_70449_1_ = 1.0f;
        }
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            if (this.Â[var2] != null && this.Â[var2].HorizonCode_Horizon_È() instanceof ItemArmor) {
                this.Â[var2].HorizonCode_Horizon_È((int)p_70449_1_, this.Ø­áŒŠá);
                if (this.Â[var2].Â == 0) {
                    this.Â[var2] = null;
                }
            }
        }
    }
    
    public void ÂµÈ() {
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            if (this.HorizonCode_Horizon_È[var1] != null) {
                this.Ø­áŒŠá.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È[var1], true, false);
                this.HorizonCode_Horizon_È[var1] = null;
            }
        }
        for (int var1 = 0; var1 < this.Â.length; ++var1) {
            if (this.Â[var1] != null) {
                this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â[var1], true, false);
                this.Â[var1] = null;
            }
        }
    }
    
    @Override
    public void ŠÄ() {
        this.Âµá€ = true;
    }
    
    public void Â(final ItemStack p_70437_1_) {
        this.Ó = p_70437_1_;
    }
    
    public ItemStack á() {
        return this.Ó;
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return !this.Ø­áŒŠá.ˆáŠ && playerIn.Âµá€(this.Ø­áŒŠá) <= 64.0;
    }
    
    public boolean Ý(final ItemStack p_70431_1_) {
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            if (this.Â[var2] != null && this.Â[var2].HorizonCode_Horizon_È(p_70431_1_)) {
                return true;
            }
        }
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            if (this.HorizonCode_Horizon_È[var2] != null && this.HorizonCode_Horizon_È[var2].HorizonCode_Horizon_È(p_70431_1_)) {
                return true;
            }
        }
        return false;
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
    
    public void HorizonCode_Horizon_È(final InventoryPlayer p_70455_1_) {
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            this.HorizonCode_Horizon_È[var2] = ItemStack.Â(p_70455_1_.HorizonCode_Horizon_È[var2]);
        }
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            this.Â[var2] = ItemStack.Â(p_70455_1_.Â[var2]);
        }
        this.Ý = p_70455_1_.Ý;
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
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            this.HorizonCode_Horizon_È[var1] = null;
        }
        for (int var1 = 0; var1 < this.Â.length; ++var1) {
            this.Â[var1] = null;
        }
    }
}

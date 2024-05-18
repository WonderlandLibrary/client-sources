package HORIZON-6-0-SKIDPROTECTION;

public class MerchantRecipe
{
    private ItemStack HorizonCode_Horizon_È;
    private ItemStack Â;
    private ItemStack Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private boolean Ó;
    private static final String à = "CL_00000126";
    
    public MerchantRecipe(final NBTTagCompound p_i1940_1_) {
        this.HorizonCode_Horizon_È(p_i1940_1_);
    }
    
    public MerchantRecipe(final ItemStack p_i1941_1_, final ItemStack p_i1941_2_, final ItemStack p_i1941_3_) {
        this(p_i1941_1_, p_i1941_2_, p_i1941_3_, 0, 7);
    }
    
    public MerchantRecipe(final ItemStack p_i45760_1_, final ItemStack p_i45760_2_, final ItemStack p_i45760_3_, final int p_i45760_4_, final int p_i45760_5_) {
        this.HorizonCode_Horizon_È = p_i45760_1_;
        this.Â = p_i45760_2_;
        this.Ý = p_i45760_3_;
        this.Ø­áŒŠá = p_i45760_4_;
        this.Âµá€ = p_i45760_5_;
        this.Ó = true;
    }
    
    public MerchantRecipe(final ItemStack p_i1942_1_, final ItemStack p_i1942_2_) {
        this(p_i1942_1_, null, p_i1942_2_);
    }
    
    public MerchantRecipe(final ItemStack p_i1943_1_, final Item_1028566121 p_i1943_2_) {
        this(p_i1943_1_, new ItemStack(p_i1943_2_));
    }
    
    public ItemStack HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public ItemStack Â() {
        return this.Â;
    }
    
    public boolean Ý() {
        return this.Â != null;
    }
    
    public ItemStack Ø­áŒŠá() {
        return this.Ý;
    }
    
    public int Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    public int Ó() {
        return this.Âµá€;
    }
    
    public void à() {
        ++this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final int p_82783_1_) {
        this.Âµá€ += p_82783_1_;
    }
    
    public boolean Ø() {
        return this.Ø­áŒŠá >= this.Âµá€;
    }
    
    public void áŒŠÆ() {
        this.Ø­áŒŠá = this.Âµá€;
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_77390_1_) {
        final NBTTagCompound var2 = p_77390_1_.ˆÏ­("buy");
        this.HorizonCode_Horizon_È = ItemStack.HorizonCode_Horizon_È(var2);
        final NBTTagCompound var3 = p_77390_1_.ˆÏ­("sell");
        this.Ý = ItemStack.HorizonCode_Horizon_È(var3);
        if (p_77390_1_.Â("buyB", 10)) {
            this.Â = ItemStack.HorizonCode_Horizon_È(p_77390_1_.ˆÏ­("buyB"));
        }
        if (p_77390_1_.Â("uses", 99)) {
            this.Ø­áŒŠá = p_77390_1_.Ó("uses");
        }
        if (p_77390_1_.Â("maxUses", 99)) {
            this.Âµá€ = p_77390_1_.Ó("maxUses");
        }
        else {
            this.Âµá€ = 7;
        }
        if (p_77390_1_.Â("rewardExp", 1)) {
            this.Ó = p_77390_1_.£á("rewardExp");
        }
        else {
            this.Ó = true;
        }
    }
    
    public NBTTagCompound ÂµÈ() {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.HorizonCode_Horizon_È("buy", this.HorizonCode_Horizon_È.Â(new NBTTagCompound()));
        var1.HorizonCode_Horizon_È("sell", this.Ý.Â(new NBTTagCompound()));
        if (this.Â != null) {
            var1.HorizonCode_Horizon_È("buyB", this.Â.Â(new NBTTagCompound()));
        }
        var1.HorizonCode_Horizon_È("uses", this.Ø­áŒŠá);
        var1.HorizonCode_Horizon_È("maxUses", this.Âµá€);
        var1.HorizonCode_Horizon_È("rewardExp", this.Ó);
        return var1;
    }
}

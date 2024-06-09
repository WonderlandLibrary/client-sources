package HORIZON-6-0-SKIDPROTECTION;

public class ItemFood extends Item_1028566121
{
    public final int à;
    private final int Ø;
    private final float áŒŠÆ;
    private final boolean áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private int á;
    private int ˆÏ­;
    private int £á;
    private float Å;
    private static final String £à = "CL_00000036";
    
    public ItemFood(final int p_i45339_1_, final float p_i45339_2_, final boolean p_i45339_3_) {
        this.à = 32;
        this.Ø = p_i45339_1_;
        this.áˆºÑ¢Õ = p_i45339_3_;
        this.áŒŠÆ = p_i45339_2_;
        this.HorizonCode_Horizon_È(CreativeTabs.Ø);
    }
    
    public ItemFood(final int p_i45340_1_, final boolean p_i45340_2_) {
        this(p_i45340_1_, 0.6f, p_i45340_2_);
    }
    
    @Override
    public ItemStack Â(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        --stack.Â;
        playerIn.ŠÏ­áˆºá().HorizonCode_Horizon_È(this, stack);
        worldIn.HorizonCode_Horizon_È((Entity)playerIn, "random.burp", 0.5f, worldIn.Å.nextFloat() * 0.1f + 0.9f);
        this.Ø­áŒŠá(stack, worldIn, playerIn);
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        return stack;
    }
    
    protected void Ø­áŒŠá(final ItemStack p_77849_1_, final World worldIn, final EntityPlayer p_77849_3_) {
        if (!worldIn.ŠÄ && this.á > 0 && worldIn.Å.nextFloat() < this.Å) {
            p_77849_3_.HorizonCode_Horizon_È(new PotionEffect(this.á, this.ˆÏ­ * 20, this.£á));
        }
    }
    
    @Override
    public int Ø­áŒŠá(final ItemStack stack) {
        return 32;
    }
    
    @Override
    public EnumAction Ý(final ItemStack stack) {
        return EnumAction.Â;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (playerIn.Ý(this.ÂµÈ)) {
            playerIn.Â(itemStackIn, this.Ø­áŒŠá(itemStackIn));
        }
        return itemStackIn;
    }
    
    public int ÂµÈ(final ItemStack itemStackIn) {
        return this.Ø;
    }
    
    public float á(final ItemStack itemStackIn) {
        return this.áŒŠÆ;
    }
    
    public boolean ˆà() {
        return this.áˆºÑ¢Õ;
    }
    
    public ItemFood HorizonCode_Horizon_È(final int p_77844_1_, final int duration, final int amplifier, final float probability) {
        this.á = p_77844_1_;
        this.ˆÏ­ = duration;
        this.£á = amplifier;
        this.Å = probability;
        return this;
    }
    
    public ItemFood ¥Æ() {
        this.ÂµÈ = true;
        return this;
    }
}

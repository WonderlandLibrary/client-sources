package HORIZON-6-0-SKIDPROTECTION;

public class NpcMerchant implements IMerchant
{
    private InventoryMerchant HorizonCode_Horizon_È;
    private EntityPlayer Â;
    private MerchantRecipeList Ý;
    private IChatComponent Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001705";
    
    public NpcMerchant(final EntityPlayer p_i45817_1_, final IChatComponent p_i45817_2_) {
        this.Â = p_i45817_1_;
        this.Ø­áŒŠá = p_i45817_2_;
        this.HorizonCode_Horizon_È = new InventoryMerchant(p_i45817_1_, this);
    }
    
    @Override
    public EntityPlayer HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public void a_(final EntityPlayer p_70932_1_) {
    }
    
    @Override
    public MerchantRecipeList Â(final EntityPlayer p_70934_1_) {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final MerchantRecipeList p_70930_1_) {
        this.Ý = p_70930_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final MerchantRecipe p_70933_1_) {
        p_70933_1_.à();
    }
    
    @Override
    public void a_(final ItemStack p_110297_1_) {
    }
    
    @Override
    public IChatComponent Ý() {
        return (this.Ø­áŒŠá != null) ? this.Ø­áŒŠá : new ChatComponentTranslation("entity.Villager.name", new Object[0]);
    }
}

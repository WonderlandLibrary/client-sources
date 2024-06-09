package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentDamage extends Enchantment
{
    private static final String[] ˆá;
    private static final int[] ÇŽÕ;
    private static final int[] É;
    private static final int[] áƒ;
    public final int ÇŽÉ;
    private static final String á€ = "CL_00000102";
    
    static {
        ˆá = new String[] { "all", "undead", "arthropods" };
        ÇŽÕ = new int[] { 1, 5, 5 };
        É = new int[] { 11, 8, 8 };
        áƒ = new int[] { 20, 20, 20 };
    }
    
    public EnchantmentDamage(final int p_i45774_1_, final ResourceLocation_1975012498 p_i45774_2_, final int p_i45774_3_, final int p_i45774_4_) {
        super(p_i45774_1_, p_i45774_2_, p_i45774_3_, EnumEnchantmentType.à);
        this.ÇŽÉ = p_i45774_4_;
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return EnchantmentDamage.ÇŽÕ[this.ÇŽÉ] + (p_77321_1_ - 1) * EnchantmentDamage.É[this.ÇŽÉ];
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + EnchantmentDamage.áƒ[this.ÇŽÉ];
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 5;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final int p_152376_1_, final EnumCreatureAttribute p_152376_2_) {
        return (this.ÇŽÉ == 0) ? (p_152376_1_ * 1.25f) : ((this.ÇŽÉ == 1 && p_152376_2_ == EnumCreatureAttribute.Â) ? (p_152376_1_ * 2.5f) : ((this.ÇŽÉ == 2 && p_152376_2_ == EnumCreatureAttribute.Ý) ? (p_152376_1_ * 2.5f) : 0.0f));
    }
    
    @Override
    public String Âµá€() {
        return "enchantment.damage." + EnchantmentDamage.ˆá[this.ÇŽÉ];
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Enchantment p_77326_1_) {
        return !(p_77326_1_ instanceof EnchantmentDamage);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack p_92089_1_) {
        return p_92089_1_.HorizonCode_Horizon_È() instanceof ItemAxe || super.HorizonCode_Horizon_È(p_92089_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_151368_1_, final Entity p_151368_2_, final int p_151368_3_) {
        if (p_151368_2_ instanceof EntityLivingBase) {
            final EntityLivingBase var4 = (EntityLivingBase)p_151368_2_;
            if (this.ÇŽÉ == 2 && var4.¥áŒŠà() == EnumCreatureAttribute.Ý) {
                final int var5 = 20 + p_151368_1_.ˆÐƒØ().nextInt(10 * p_151368_3_);
                var4.HorizonCode_Horizon_È(new PotionEffect(Potion.Ø­áŒŠá.É, var5, 3));
            }
        }
    }
}

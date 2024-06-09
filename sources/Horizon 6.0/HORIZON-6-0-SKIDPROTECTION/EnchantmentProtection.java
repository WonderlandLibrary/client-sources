package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentProtection extends Enchantment
{
    private static final String[] ˆá;
    private static final int[] ÇŽÕ;
    private static final int[] É;
    private static final int[] áƒ;
    public final int ÇŽÉ;
    private static final String á€ = "CL_00000121";
    
    static {
        ˆá = new String[] { "all", "fire", "fall", "explosion", "projectile" };
        ÇŽÕ = new int[] { 1, 10, 5, 5, 3 };
        É = new int[] { 11, 8, 6, 8, 6 };
        áƒ = new int[] { 20, 12, 10, 12, 15 };
    }
    
    public EnchantmentProtection(final int p_i45765_1_, final ResourceLocation_1975012498 p_i45765_2_, final int p_i45765_3_, final int p_i45765_4_) {
        super(p_i45765_1_, p_i45765_2_, p_i45765_3_, EnumEnchantmentType.Â);
        this.ÇŽÉ = p_i45765_4_;
        if (p_i45765_4_ == 2) {
            this.Çªà¢ = EnumEnchantmentType.Ý;
        }
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return EnchantmentProtection.ÇŽÕ[this.ÇŽÉ] + (p_77321_1_ - 1) * EnchantmentProtection.É[this.ÇŽÉ];
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + EnchantmentProtection.áƒ[this.ÇŽÉ];
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 4;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int p_77318_1_, final DamageSource p_77318_2_) {
        if (p_77318_2_.à()) {
            return 0;
        }
        final float var3 = (6 + p_77318_1_ * p_77318_1_) / 3.0f;
        return (this.ÇŽÉ == 0) ? MathHelper.Ø­áŒŠá(var3 * 0.75f) : ((this.ÇŽÉ == 1 && p_77318_2_.Å()) ? MathHelper.Ø­áŒŠá(var3 * 1.25f) : ((this.ÇŽÉ == 2 && p_77318_2_ == DamageSource.áŒŠÆ) ? MathHelper.Ø­áŒŠá(var3 * 2.5f) : ((this.ÇŽÉ == 3 && p_77318_2_.Ý()) ? MathHelper.Ø­áŒŠá(var3 * 1.5f) : ((this.ÇŽÉ == 4 && p_77318_2_.HorizonCode_Horizon_È()) ? MathHelper.Ø­áŒŠá(var3 * 1.5f) : 0))));
    }
    
    @Override
    public String Âµá€() {
        return "enchantment.protect." + EnchantmentProtection.ˆá[this.ÇŽÉ];
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Enchantment p_77326_1_) {
        if (p_77326_1_ instanceof EnchantmentProtection) {
            final EnchantmentProtection var2 = (EnchantmentProtection)p_77326_1_;
            return var2.ÇŽÉ != this.ÇŽÉ && (this.ÇŽÉ == 2 || var2.ÇŽÉ == 2);
        }
        return super.HorizonCode_Horizon_È(p_77326_1_);
    }
    
    public static int HorizonCode_Horizon_È(final Entity p_92093_0_, int p_92093_1_) {
        final int var2 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ø­áŒŠá.ŒÏ, p_92093_0_.Ðƒá());
        if (var2 > 0) {
            p_92093_1_ -= MathHelper.Ø­áŒŠá(p_92093_1_ * var2 * 0.15f);
        }
        return p_92093_1_;
    }
    
    public static double HorizonCode_Horizon_È(final Entity p_92092_0_, double p_92092_1_) {
        final int var3 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ó.ŒÏ, p_92092_0_.Ðƒá());
        if (var3 > 0) {
            p_92092_1_ -= MathHelper.Ý(p_92092_1_ * (var3 * 0.15f));
        }
        return p_92092_1_;
    }
}

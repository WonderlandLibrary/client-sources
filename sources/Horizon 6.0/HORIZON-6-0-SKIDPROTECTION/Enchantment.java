package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Map;

public abstract class Enchantment
{
    public static final Enchantment[] HorizonCode_Horizon_È;
    public static final Enchantment[] Â;
    private static final Map ÇŽÉ;
    public static final Enchantment Ý;
    public static final Enchantment Ø­áŒŠá;
    public static final Enchantment Âµá€;
    public static final Enchantment Ó;
    public static final Enchantment à;
    public static final Enchantment Ø;
    public static final Enchantment áŒŠÆ;
    public static final Enchantment áˆºÑ¢Õ;
    public static final Enchantment ÂµÈ;
    public static final Enchantment á;
    public static final Enchantment ˆÏ­;
    public static final Enchantment £á;
    public static final Enchantment Å;
    public static final Enchantment £à;
    public static final Enchantment µà;
    public static final Enchantment ˆà;
    public static final Enchantment ¥Æ;
    public static final Enchantment Ø­à;
    public static final Enchantment µÕ;
    public static final Enchantment Æ;
    public static final Enchantment Šáƒ;
    public static final Enchantment Ï­Ðƒà;
    public static final Enchantment áŒŠà;
    public static final Enchantment ŠÄ;
    public static final Enchantment Ñ¢á;
    public final int ŒÏ;
    private final int ˆá;
    public EnumEnchantmentType Çªà¢;
    protected String Ê;
    private static final String ÇŽÕ = "CL_00000105";
    
    static {
        HorizonCode_Horizon_È = new Enchantment[256];
        ÇŽÉ = Maps.newHashMap();
        Ý = new EnchantmentProtection(0, new ResourceLocation_1975012498("protection"), 10, 0);
        Ø­áŒŠá = new EnchantmentProtection(1, new ResourceLocation_1975012498("fire_protection"), 5, 1);
        Âµá€ = new EnchantmentProtection(2, new ResourceLocation_1975012498("feather_falling"), 5, 2);
        Ó = new EnchantmentProtection(3, new ResourceLocation_1975012498("blast_protection"), 2, 3);
        à = new EnchantmentProtection(4, new ResourceLocation_1975012498("projectile_protection"), 5, 4);
        Ø = new EnchantmentOxygen(5, new ResourceLocation_1975012498("respiration"), 2);
        áŒŠÆ = new EnchantmentWaterWorker(6, new ResourceLocation_1975012498("aqua_affinity"), 2);
        áˆºÑ¢Õ = new EnchantmentThorns(7, new ResourceLocation_1975012498("thorns"), 1);
        ÂµÈ = new EnchantmentWaterWalker(8, new ResourceLocation_1975012498("depth_strider"), 2);
        á = new EnchantmentDamage(16, new ResourceLocation_1975012498("sharpness"), 10, 0);
        ˆÏ­ = new EnchantmentDamage(17, new ResourceLocation_1975012498("smite"), 5, 1);
        £á = new EnchantmentDamage(18, new ResourceLocation_1975012498("bane_of_arthropods"), 5, 2);
        Å = new EnchantmentKnockback(19, new ResourceLocation_1975012498("knockback"), 5);
        £à = new EnchantmentFireAspect(20, new ResourceLocation_1975012498("fire_aspect"), 2);
        µà = new EnchantmentLootBonus(21, new ResourceLocation_1975012498("looting"), 2, EnumEnchantmentType.à);
        ˆà = new EnchantmentDigging(32, new ResourceLocation_1975012498("efficiency"), 10);
        ¥Æ = new EnchantmentUntouching(33, new ResourceLocation_1975012498("silk_touch"), 1);
        Ø­à = new EnchantmentDurability(34, new ResourceLocation_1975012498("unbreaking"), 5);
        µÕ = new EnchantmentLootBonus(35, new ResourceLocation_1975012498("fortune"), 2, EnumEnchantmentType.Ø);
        Æ = new EnchantmentArrowDamage(48, new ResourceLocation_1975012498("power"), 10);
        Šáƒ = new EnchantmentArrowKnockback(49, new ResourceLocation_1975012498("punch"), 2);
        Ï­Ðƒà = new EnchantmentArrowFire(50, new ResourceLocation_1975012498("flame"), 2);
        áŒŠà = new EnchantmentArrowInfinite(51, new ResourceLocation_1975012498("infinity"), 1);
        ŠÄ = new EnchantmentLootBonus(61, new ResourceLocation_1975012498("luck_of_the_sea"), 2, EnumEnchantmentType.áŒŠÆ);
        Ñ¢á = new EnchantmentFishingSpeed(62, new ResourceLocation_1975012498("lure"), 2, EnumEnchantmentType.áŒŠÆ);
        final ArrayList var0 = Lists.newArrayList();
        for (final Enchantment var5 : Enchantment.HorizonCode_Horizon_È) {
            if (var5 != null) {
                var0.add(var5);
            }
        }
        Â = var0.toArray(new Enchantment[var0.size()]);
    }
    
    public static Enchantment HorizonCode_Horizon_È(final int p_180306_0_) {
        return (p_180306_0_ >= 0 && p_180306_0_ < Enchantment.HorizonCode_Horizon_È.length) ? Enchantment.HorizonCode_Horizon_È[p_180306_0_] : null;
    }
    
    protected Enchantment(final int p_i45771_1_, final ResourceLocation_1975012498 p_i45771_2_, final int p_i45771_3_, final EnumEnchantmentType p_i45771_4_) {
        this.ŒÏ = p_i45771_1_;
        this.ˆá = p_i45771_3_;
        this.Çªà¢ = p_i45771_4_;
        if (Enchantment.HorizonCode_Horizon_È[p_i45771_1_] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        Enchantment.HorizonCode_Horizon_È[p_i45771_1_] = this;
        Enchantment.ÇŽÉ.put(p_i45771_2_, this);
    }
    
    public static Enchantment HorizonCode_Horizon_È(final String p_180305_0_) {
        return Enchantment.ÇŽÉ.get(new ResourceLocation_1975012498(p_180305_0_));
    }
    
    public static String[] HorizonCode_Horizon_È() {
        final String[] var0 = new String[Enchantment.ÇŽÉ.size()];
        int var2 = 0;
        for (final ResourceLocation_1975012498 var4 : Enchantment.ÇŽÉ.keySet()) {
            var0[var2++] = var4.toString();
        }
        return var0;
    }
    
    public int Â() {
        return this.ˆá;
    }
    
    public int Ý() {
        return 1;
    }
    
    public int Ø­áŒŠá() {
        return 1;
    }
    
    public int Â(final int p_77321_1_) {
        return 1 + p_77321_1_ * 10;
    }
    
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + 5;
    }
    
    public int HorizonCode_Horizon_È(final int p_77318_1_, final DamageSource p_77318_2_) {
        return 0;
    }
    
    public float HorizonCode_Horizon_È(final int p_152376_1_, final EnumCreatureAttribute p_152376_2_) {
        return 0.0f;
    }
    
    public boolean HorizonCode_Horizon_È(final Enchantment p_77326_1_) {
        return this != p_77326_1_;
    }
    
    public Enchantment Â(final String p_77322_1_) {
        this.Ê = p_77322_1_;
        return this;
    }
    
    public String Âµá€() {
        return "enchantment." + this.Ê;
    }
    
    public String Ø­áŒŠá(final int p_77316_1_) {
        final String var2 = StatCollector.HorizonCode_Horizon_È(this.Âµá€());
        return String.valueOf(var2) + " " + StatCollector.HorizonCode_Horizon_È("enchantment.level." + p_77316_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack p_92089_1_) {
        return this.Çªà¢.HorizonCode_Horizon_È(p_92089_1_.HorizonCode_Horizon_È());
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase p_151368_1_, final Entity p_151368_2_, final int p_151368_3_) {
    }
    
    public void Â(final EntityLivingBase p_151367_1_, final Entity p_151367_2_, final int p_151367_3_) {
    }
}

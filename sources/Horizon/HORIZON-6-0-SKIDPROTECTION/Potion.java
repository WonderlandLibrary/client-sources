package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public class Potion
{
    public static final Potion[] HorizonCode_Horizon_È;
    private static final Map áƒ;
    public static final Potion Â;
    public static final Potion Ý;
    public static final Potion Ø­áŒŠá;
    public static final Potion Âµá€;
    public static final Potion Ó;
    public static final Potion à;
    public static final Potion Ø;
    public static final Potion áŒŠÆ;
    public static final Potion áˆºÑ¢Õ;
    public static final Potion ÂµÈ;
    public static final Potion á;
    public static final Potion ˆÏ­;
    public static final Potion £á;
    public static final Potion Å;
    public static final Potion £à;
    public static final Potion µà;
    public static final Potion ˆà;
    public static final Potion ¥Æ;
    public static final Potion Ø­à;
    public static final Potion µÕ;
    public static final Potion Æ;
    public static final Potion Šáƒ;
    public static final Potion Ï­Ðƒà;
    public static final Potion áŒŠà;
    public static final Potion ŠÄ;
    public static final Potion Ñ¢á;
    public static final Potion ŒÏ;
    public static final Potion Çªà¢;
    public static final Potion Ê;
    public static final Potion ÇŽÉ;
    public static final Potion ˆá;
    public static final Potion ÇŽÕ;
    public final int É;
    private final Map á€;
    private final boolean Õ;
    private final int à¢;
    private String ŠÂµà;
    private int ¥à;
    private double Âµà;
    private boolean Ç;
    private static final String È = "CL_00001528";
    
    static {
        HorizonCode_Horizon_È = new Potion[32];
        áƒ = Maps.newHashMap();
        Â = null;
        Ý = new Potion(1, new ResourceLocation_1975012498("speed"), false, 8171462).Â("potion.moveSpeed").HorizonCode_Horizon_È(0, 0).HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224, 2);
        Ø­áŒŠá = new Potion(2, new ResourceLocation_1975012498("slowness"), true, 5926017).Â("potion.moveSlowdown").HorizonCode_Horizon_È(1, 0).HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, 2);
        Âµá€ = new Potion(3, new ResourceLocation_1975012498("haste"), false, 14270531).Â("potion.digSpeed").HorizonCode_Horizon_È(2, 0).HorizonCode_Horizon_È(1.5);
        Ó = new Potion(4, new ResourceLocation_1975012498("mining_fatigue"), true, 4866583).Â("potion.digSlowDown").HorizonCode_Horizon_È(3, 0);
        à = new PotionAttackDamage(5, new ResourceLocation_1975012498("strength"), false, 9643043).Â("potion.damageBoost").HorizonCode_Horizon_È(4, 0).HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5, 2);
        Ø = new PotionHealth(6, new ResourceLocation_1975012498("instant_health"), false, 16262179).Â("potion.heal");
        áŒŠÆ = new PotionHealth(7, new ResourceLocation_1975012498("instant_damage"), true, 4393481).Â("potion.harm");
        áˆºÑ¢Õ = new Potion(8, new ResourceLocation_1975012498("jump_boost"), false, 2293580).Â("potion.jump").HorizonCode_Horizon_È(2, 1);
        ÂµÈ = new Potion(9, new ResourceLocation_1975012498("nausea"), true, 5578058).Â("potion.confusion").HorizonCode_Horizon_È(3, 1).HorizonCode_Horizon_È(0.25);
        á = new Potion(10, new ResourceLocation_1975012498("regeneration"), false, 13458603).Â("potion.regeneration").HorizonCode_Horizon_È(7, 0).HorizonCode_Horizon_È(0.25);
        ˆÏ­ = new Potion(11, new ResourceLocation_1975012498("resistance"), false, 10044730).Â("potion.resistance").HorizonCode_Horizon_È(6, 1);
        £á = new Potion(12, new ResourceLocation_1975012498("fire_resistance"), false, 14981690).Â("potion.fireResistance").HorizonCode_Horizon_È(7, 1);
        Å = new Potion(13, new ResourceLocation_1975012498("water_breathing"), false, 3035801).Â("potion.waterBreathing").HorizonCode_Horizon_È(0, 2);
        £à = new Potion(14, new ResourceLocation_1975012498("invisibility"), false, 8356754).Â("potion.invisibility").HorizonCode_Horizon_È(0, 1);
        µà = new Potion(15, new ResourceLocation_1975012498("blindness"), true, 2039587).Â("potion.blindness").HorizonCode_Horizon_È(5, 1).HorizonCode_Horizon_È(0.25);
        ˆà = new Potion(16, new ResourceLocation_1975012498("night_vision"), false, 2039713).Â("potion.nightVision").HorizonCode_Horizon_È(4, 1);
        ¥Æ = new Potion(17, new ResourceLocation_1975012498("hunger"), true, 5797459).Â("potion.hunger").HorizonCode_Horizon_È(1, 1);
        Ø­à = new PotionAttackDamage(18, new ResourceLocation_1975012498("weakness"), true, 4738376).Â("potion.weakness").HorizonCode_Horizon_È(5, 0).HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
        µÕ = new Potion(19, new ResourceLocation_1975012498("poison"), true, 5149489).Â("potion.poison").HorizonCode_Horizon_È(6, 0).HorizonCode_Horizon_È(0.25);
        Æ = new Potion(20, new ResourceLocation_1975012498("wither"), true, 3484199).Â("potion.wither").HorizonCode_Horizon_È(1, 2).HorizonCode_Horizon_È(0.25);
        Šáƒ = new PotionHealthBoost(21, new ResourceLocation_1975012498("health_boost"), false, 16284963).Â("potion.healthBoost").HorizonCode_Horizon_È(2, 2).HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0);
        Ï­Ðƒà = new PotionAbsoption(22, new ResourceLocation_1975012498("absorption"), false, 2445989).Â("potion.absorption").HorizonCode_Horizon_È(2, 2);
        áŒŠà = new PotionHealth(23, new ResourceLocation_1975012498("saturation"), false, 16262179).Â("potion.saturation");
        ŠÄ = null;
        Ñ¢á = null;
        ŒÏ = null;
        Çªà¢ = null;
        Ê = null;
        ÇŽÉ = null;
        ˆá = null;
        ÇŽÕ = null;
    }
    
    protected Potion(final int p_i45897_1_, final ResourceLocation_1975012498 p_i45897_2_, final boolean p_i45897_3_, final int p_i45897_4_) {
        this.á€ = Maps.newHashMap();
        this.ŠÂµà = "";
        this.¥à = -1;
        this.É = p_i45897_1_;
        Potion.HorizonCode_Horizon_È[p_i45897_1_] = this;
        Potion.áƒ.put(p_i45897_2_, this);
        this.Õ = p_i45897_3_;
        if (p_i45897_3_) {
            this.Âµà = 0.5;
        }
        else {
            this.Âµà = 1.0;
        }
        this.à¢ = p_i45897_4_;
    }
    
    public static Potion HorizonCode_Horizon_È(final String p_180142_0_) {
        return Potion.áƒ.get(new ResourceLocation_1975012498(p_180142_0_));
    }
    
    public static String[] HorizonCode_Horizon_È() {
        final String[] var0 = new String[Potion.áƒ.size()];
        int var2 = 0;
        for (final ResourceLocation_1975012498 var4 : Potion.áƒ.keySet()) {
            var0[var2++] = var4.toString();
        }
        return var0;
    }
    
    protected Potion HorizonCode_Horizon_È(final int p_76399_1_, final int p_76399_2_) {
        this.¥à = p_76399_1_ + p_76399_2_ * 8;
        return this;
    }
    
    public int Â() {
        return this.É;
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76394_1_, final int p_76394_2_) {
        if (this.É == Potion.á.É) {
            if (p_76394_1_.Ï­Ä() < p_76394_1_.ÇŽÊ()) {
                p_76394_1_.a_(1.0f);
            }
        }
        else if (this.É == Potion.µÕ.É) {
            if (p_76394_1_.Ï­Ä() > 1.0f) {
                p_76394_1_.HorizonCode_Horizon_È(DamageSource.á, 1.0f);
            }
        }
        else if (this.É == Potion.Æ.É) {
            p_76394_1_.HorizonCode_Horizon_È(DamageSource.ˆÏ­, 1.0f);
        }
        else if (this.É == Potion.¥Æ.É && p_76394_1_ instanceof EntityPlayer) {
            ((EntityPlayer)p_76394_1_).Ý(0.025f * (p_76394_2_ + 1));
        }
        else if (this.É == Potion.áŒŠà.É && p_76394_1_ instanceof EntityPlayer) {
            if (!p_76394_1_.Ï­Ðƒà.ŠÄ) {
                ((EntityPlayer)p_76394_1_).ŠÏ­áˆºá().HorizonCode_Horizon_È(p_76394_2_ + 1, 1.0f);
            }
        }
        else if ((this.É != Potion.Ø.É || p_76394_1_.ÇªáˆºÕ()) && (this.É != Potion.áŒŠÆ.É || !p_76394_1_.ÇªáˆºÕ())) {
            if ((this.É == Potion.áŒŠÆ.É && !p_76394_1_.ÇªáˆºÕ()) || (this.É == Potion.Ø.É && p_76394_1_.ÇªáˆºÕ())) {
                p_76394_1_.HorizonCode_Horizon_È(DamageSource.á, 6 << p_76394_2_);
            }
        }
        else {
            p_76394_1_.a_(Math.max(4 << p_76394_2_, 0));
        }
    }
    
    public void HorizonCode_Horizon_È(final Entity p_180793_1_, final Entity p_180793_2_, final EntityLivingBase p_180793_3_, final int p_180793_4_, final double p_180793_5_) {
        if ((this.É != Potion.Ø.É || p_180793_3_.ÇªáˆºÕ()) && (this.É != Potion.áŒŠÆ.É || !p_180793_3_.ÇªáˆºÕ())) {
            if ((this.É == Potion.áŒŠÆ.É && !p_180793_3_.ÇªáˆºÕ()) || (this.É == Potion.Ø.É && p_180793_3_.ÇªáˆºÕ())) {
                final int var7 = (int)(p_180793_5_ * (6 << p_180793_4_) + 0.5);
                if (p_180793_1_ == null) {
                    p_180793_3_.HorizonCode_Horizon_È(DamageSource.á, var7);
                }
                else {
                    p_180793_3_.HorizonCode_Horizon_È(DamageSource.Â(p_180793_1_, p_180793_2_), var7);
                }
            }
        }
        else {
            final int var7 = (int)(p_180793_5_ * (4 << p_180793_4_) + 0.5);
            p_180793_3_.a_(var7);
        }
    }
    
    public boolean Ý() {
        return false;
    }
    
    public boolean Â(final int p_76397_1_, final int p_76397_2_) {
        if (this.É == Potion.á.É) {
            final int var3 = 50 >> p_76397_2_;
            return var3 <= 0 || p_76397_1_ % var3 == 0;
        }
        if (this.É == Potion.µÕ.É) {
            final int var3 = 25 >> p_76397_2_;
            return var3 <= 0 || p_76397_1_ % var3 == 0;
        }
        if (this.É == Potion.Æ.É) {
            final int var3 = 40 >> p_76397_2_;
            return var3 <= 0 || p_76397_1_ % var3 == 0;
        }
        return this.É == Potion.¥Æ.É;
    }
    
    public Potion Â(final String p_76390_1_) {
        this.ŠÂµà = p_76390_1_;
        return this;
    }
    
    public String Ø­áŒŠá() {
        return this.ŠÂµà;
    }
    
    public boolean Âµá€() {
        return this.¥à >= 0;
    }
    
    public int Ó() {
        return this.¥à;
    }
    
    public boolean à() {
        return this.Õ;
    }
    
    public static String HorizonCode_Horizon_È(final PotionEffect p_76389_0_) {
        if (p_76389_0_.à()) {
            return "**:**";
        }
        final int var1 = p_76389_0_.Â();
        return StringUtils.HorizonCode_Horizon_È(var1);
    }
    
    protected Potion HorizonCode_Horizon_È(final double p_76404_1_) {
        this.Âµà = p_76404_1_;
        return this;
    }
    
    public double Ø() {
        return this.Âµà;
    }
    
    public boolean áŒŠÆ() {
        return this.Ç;
    }
    
    public int áˆºÑ¢Õ() {
        return this.à¢;
    }
    
    public Potion HorizonCode_Horizon_È(final IAttribute p_111184_1_, final String p_111184_2_, final double p_111184_3_, final int p_111184_5_) {
        final AttributeModifier var6 = new AttributeModifier(UUID.fromString(p_111184_2_), this.Ø­áŒŠá(), p_111184_3_, p_111184_5_);
        this.á€.put(p_111184_1_, var6);
        return this;
    }
    
    public Map ÂµÈ() {
        return this.á€;
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase p_111187_1_, final BaseAttributeMap p_111187_2_, final int p_111187_3_) {
        for (final Map.Entry var5 : this.á€.entrySet()) {
            final IAttributeInstance var6 = p_111187_2_.HorizonCode_Horizon_È(var5.getKey());
            if (var6 != null) {
                var6.Ý(var5.getValue());
            }
        }
    }
    
    public void Â(final EntityLivingBase p_111185_1_, final BaseAttributeMap p_111185_2_, final int p_111185_3_) {
        for (final Map.Entry var5 : this.á€.entrySet()) {
            final IAttributeInstance var6 = p_111185_2_.HorizonCode_Horizon_È(var5.getKey());
            if (var6 != null) {
                final AttributeModifier var7 = var5.getValue();
                var6.Ý(var7);
                var6.Â(new AttributeModifier(var7.HorizonCode_Horizon_È(), String.valueOf(this.Ø­áŒŠá()) + " " + p_111185_3_, this.HorizonCode_Horizon_È(p_111185_3_, var7), var7.Ý()));
            }
        }
    }
    
    public double HorizonCode_Horizon_È(final int p_111183_1_, final AttributeModifier p_111183_2_) {
        return p_111183_2_.Ø­áŒŠá() * (p_111183_1_ + 1);
    }
}

package net.minecraft.src;

public class Potion
{
    public static final Potion[] potionTypes;
    public static final Potion field_76423_b;
    public static final Potion moveSpeed;
    public static final Potion moveSlowdown;
    public static final Potion digSpeed;
    public static final Potion digSlowdown;
    public static final Potion damageBoost;
    public static final Potion heal;
    public static final Potion harm;
    public static final Potion jump;
    public static final Potion confusion;
    public static final Potion regeneration;
    public static final Potion resistance;
    public static final Potion fireResistance;
    public static final Potion waterBreathing;
    public static final Potion invisibility;
    public static final Potion blindness;
    public static final Potion nightVision;
    public static final Potion hunger;
    public static final Potion weakness;
    public static final Potion poison;
    public static final Potion wither;
    public static final Potion field_76434_w;
    public static final Potion field_76444_x;
    public static final Potion field_76443_y;
    public static final Potion field_76442_z;
    public static final Potion field_76409_A;
    public static final Potion field_76410_B;
    public static final Potion field_76411_C;
    public static final Potion field_76405_D;
    public static final Potion field_76406_E;
    public static final Potion field_76407_F;
    public static final Potion field_76408_G;
    public final int id;
    private String name;
    private int statusIconIndex;
    private final boolean isBadEffect;
    private double effectiveness;
    private boolean usable;
    private final int liquidColor;
    
    static {
        potionTypes = new Potion[32];
        field_76423_b = null;
        moveSpeed = new Potion(1, false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0);
        moveSlowdown = new Potion(2, true, 5926017).setPotionName("potion.moveSlowdown").setIconIndex(1, 0);
        digSpeed = new Potion(3, false, 14270531).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5);
        digSlowdown = new Potion(4, true, 4866583).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
        damageBoost = new Potion(5, false, 9643043).setPotionName("potion.damageBoost").setIconIndex(4, 0);
        heal = new PotionHealth(6, false, 16262179).setPotionName("potion.heal");
        harm = new PotionHealth(7, true, 4393481).setPotionName("potion.harm");
        jump = new Potion(8, false, 7889559).setPotionName("potion.jump").setIconIndex(2, 1);
        confusion = new Potion(9, true, 5578058).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25);
        regeneration = new Potion(10, false, 13458603).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25);
        resistance = new Potion(11, false, 10044730).setPotionName("potion.resistance").setIconIndex(6, 1);
        fireResistance = new Potion(12, false, 14981690).setPotionName("potion.fireResistance").setIconIndex(7, 1);
        waterBreathing = new Potion(13, false, 3035801).setPotionName("potion.waterBreathing").setIconIndex(0, 2);
        invisibility = new Potion(14, false, 8356754).setPotionName("potion.invisibility").setIconIndex(0, 1);
        blindness = new Potion(15, true, 2039587).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25);
        nightVision = new Potion(16, false, 2039713).setPotionName("potion.nightVision").setIconIndex(4, 1);
        hunger = new Potion(17, true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
        weakness = new Potion(18, true, 4738376).setPotionName("potion.weakness").setIconIndex(5, 0);
        poison = new Potion(19, true, 5149489).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25);
        wither = new Potion(20, true, 3484199).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25);
        field_76434_w = null;
        field_76444_x = null;
        field_76443_y = null;
        field_76442_z = null;
        field_76409_A = null;
        field_76410_B = null;
        field_76411_C = null;
        field_76405_D = null;
        field_76406_E = null;
        field_76407_F = null;
        field_76408_G = null;
    }
    
    protected Potion(final int par1, final boolean par2, final int par3) {
        this.name = "";
        this.statusIconIndex = -1;
        this.id = par1;
        Potion.potionTypes[par1] = this;
        this.isBadEffect = par2;
        if (par2) {
            this.effectiveness = 0.5;
        }
        else {
            this.effectiveness = 1.0;
        }
        this.liquidColor = par3;
    }
    
    protected Potion setIconIndex(final int par1, final int par2) {
        this.statusIconIndex = par1 + par2 * 8;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void performEffect(final EntityLiving par1EntityLiving, final int par2) {
        if (this.id == Potion.regeneration.id) {
            if (par1EntityLiving.getHealth() < par1EntityLiving.getMaxHealth()) {
                par1EntityLiving.heal(1);
            }
        }
        else if (this.id == Potion.poison.id) {
            if (par1EntityLiving.getHealth() > 1) {
                par1EntityLiving.attackEntityFrom(DamageSource.magic, 1);
            }
        }
        else if (this.id == Potion.wither.id) {
            par1EntityLiving.attackEntityFrom(DamageSource.wither, 1);
        }
        else if (this.id == Potion.hunger.id && par1EntityLiving instanceof EntityPlayer) {
            ((EntityPlayer)par1EntityLiving).addExhaustion(0.025f * (par2 + 1));
        }
        else if ((this.id != Potion.heal.id || par1EntityLiving.isEntityUndead()) && (this.id != Potion.harm.id || !par1EntityLiving.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !par1EntityLiving.isEntityUndead()) || (this.id == Potion.heal.id && par1EntityLiving.isEntityUndead())) {
                par1EntityLiving.attackEntityFrom(DamageSource.magic, 6 << par2);
            }
        }
        else {
            par1EntityLiving.heal(6 << par2);
        }
    }
    
    public void affectEntity(final EntityLiving par1EntityLiving, final EntityLiving par2EntityLiving, final int par3, final double par4) {
        if ((this.id != Potion.heal.id || par2EntityLiving.isEntityUndead()) && (this.id != Potion.harm.id || !par2EntityLiving.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !par2EntityLiving.isEntityUndead()) || (this.id == Potion.heal.id && par2EntityLiving.isEntityUndead())) {
                final int var6 = (int)(par4 * (6 << par3) + 0.5);
                if (par1EntityLiving == null) {
                    par2EntityLiving.attackEntityFrom(DamageSource.magic, var6);
                }
                else {
                    par2EntityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(par2EntityLiving, par1EntityLiving), var6);
                }
            }
        }
        else {
            final int var6 = (int)(par4 * (6 << par3) + 0.5);
            par2EntityLiving.heal(var6);
        }
    }
    
    public boolean isInstant() {
        return false;
    }
    
    public boolean isReady(final int par1, final int par2) {
        if (this.id == Potion.regeneration.id || this.id == Potion.poison.id) {
            final int var3 = 25 >> par2;
            return var3 <= 0 || par1 % var3 == 0;
        }
        if (this.id == Potion.wither.id) {
            final int var3 = 40 >> par2;
            return var3 <= 0 || par1 % var3 == 0;
        }
        return this.id == Potion.hunger.id;
    }
    
    public Potion setPotionName(final String par1Str) {
        this.name = par1Str;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean hasStatusIcon() {
        return this.statusIconIndex >= 0;
    }
    
    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }
    
    public boolean isBadEffect() {
        return this.isBadEffect;
    }
    
    public static String getDurationString(final PotionEffect par0PotionEffect) {
        if (par0PotionEffect.getIsPotionDurationMax()) {
            return "**:**";
        }
        final int var1 = par0PotionEffect.getDuration();
        return StringUtils.ticksToElapsedTime(var1);
    }
    
    protected Potion setEffectiveness(final double par1) {
        this.effectiveness = par1;
        return this;
    }
    
    public double getEffectiveness() {
        return this.effectiveness;
    }
    
    public boolean isUsable() {
        return this.usable;
    }
    
    public int getLiquidColor() {
        return this.liquidColor;
    }
}

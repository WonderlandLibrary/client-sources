package net.minecraft.src;

import java.util.*;

public abstract class Enchantment
{
    public static final Enchantment[] enchantmentsList;
    public static final Enchantment[] field_92090_c;
    public static final Enchantment protection;
    public static final Enchantment fireProtection;
    public static final Enchantment featherFalling;
    public static final Enchantment blastProtection;
    public static final Enchantment projectileProtection;
    public static final Enchantment respiration;
    public static final Enchantment aquaAffinity;
    public static final Enchantment thorns;
    public static final Enchantment sharpness;
    public static final Enchantment smite;
    public static final Enchantment baneOfArthropods;
    public static final Enchantment knockback;
    public static final Enchantment fireAspect;
    public static final Enchantment looting;
    public static final Enchantment efficiency;
    public static final Enchantment silkTouch;
    public static final Enchantment unbreaking;
    public static final Enchantment fortune;
    public static final Enchantment power;
    public static final Enchantment punch;
    public static final Enchantment flame;
    public static final Enchantment infinity;
    public final int effectId;
    private final int weight;
    public EnumEnchantmentType type;
    protected String name;
    
    static {
        enchantmentsList = new Enchantment[256];
        protection = new EnchantmentProtection(0, 10, 0);
        fireProtection = new EnchantmentProtection(1, 5, 1);
        featherFalling = new EnchantmentProtection(2, 5, 2);
        blastProtection = new EnchantmentProtection(3, 2, 3);
        projectileProtection = new EnchantmentProtection(4, 5, 4);
        respiration = new EnchantmentOxygen(5, 2);
        aquaAffinity = new EnchantmentWaterWorker(6, 2);
        thorns = new EnchantmentThorns(7, 1);
        sharpness = new EnchantmentDamage(16, 10, 0);
        smite = new EnchantmentDamage(17, 5, 1);
        baneOfArthropods = new EnchantmentDamage(18, 5, 2);
        knockback = new EnchantmentKnockback(19, 5);
        fireAspect = new EnchantmentFireAspect(20, 2);
        looting = new EnchantmentLootBonus(21, 2, EnumEnchantmentType.weapon);
        efficiency = new EnchantmentDigging(32, 10);
        silkTouch = new EnchantmentUntouching(33, 1);
        unbreaking = new EnchantmentDurability(34, 5);
        fortune = new EnchantmentLootBonus(35, 2, EnumEnchantmentType.digger);
        power = new EnchantmentArrowDamage(48, 10);
        punch = new EnchantmentArrowKnockback(49, 2);
        flame = new EnchantmentArrowFire(50, 2);
        infinity = new EnchantmentArrowInfinite(51, 1);
        final ArrayList var0 = new ArrayList();
        for (final Enchantment var5 : Enchantment.enchantmentsList) {
            if (var5 != null) {
                var0.add(var5);
            }
        }
        field_92090_c = var0.toArray(new Enchantment[0]);
    }
    
    protected Enchantment(final int par1, final int par2, final EnumEnchantmentType par3EnumEnchantmentType) {
        this.effectId = par1;
        this.weight = par2;
        this.type = par3EnumEnchantmentType;
        if (Enchantment.enchantmentsList[par1] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        Enchantment.enchantmentsList[par1] = this;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    public int getMinLevel() {
        return 1;
    }
    
    public int getMaxLevel() {
        return 1;
    }
    
    public int getMinEnchantability(final int par1) {
        return 1 + par1 * 10;
    }
    
    public int getMaxEnchantability(final int par1) {
        return this.getMinEnchantability(par1) + 5;
    }
    
    public int calcModifierDamage(final int par1, final DamageSource par2DamageSource) {
        return 0;
    }
    
    public int calcModifierLiving(final int par1, final EntityLiving par2EntityLiving) {
        return 0;
    }
    
    public boolean canApplyTogether(final Enchantment par1Enchantment) {
        return this != par1Enchantment;
    }
    
    public Enchantment setName(final String par1Str) {
        this.name = par1Str;
        return this;
    }
    
    public String getName() {
        return "enchantment." + this.name;
    }
    
    public String getTranslatedName(final int par1) {
        final String var2 = StatCollector.translateToLocal(this.getName());
        return String.valueOf(var2) + " " + StatCollector.translateToLocal("enchantment.level." + par1);
    }
    
    public boolean canApply(final ItemStack par1ItemStack) {
        return this.type.canEnchantItem(par1ItemStack.getItem());
    }
}

package net.minecraft.util;

import net.minecraft.entity.*;

public class CombatEntry
{
    private final float damage;
    private final float health;
    private final String field_94566_e;
    private final DamageSource damageSrc;
    private final float fallDistance;
    private final int field_94567_b;
    
    public boolean isLivingDamageSrc() {
        return this.damageSrc.getEntity() instanceof EntityLivingBase;
    }
    
    public DamageSource getDamageSrc() {
        return this.damageSrc;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public IChatComponent getDamageSrcDisplayName() {
        IChatComponent displayName;
        if (this.getDamageSrc().getEntity() == null) {
            displayName = null;
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            displayName = this.getDamageSrc().getEntity().getDisplayName();
        }
        return displayName;
    }
    
    public float getDamageAmount() {
        float fallDistance;
        if (this.damageSrc == DamageSource.outOfWorld) {
            fallDistance = Float.MAX_VALUE;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            fallDistance = this.fallDistance;
        }
        return fallDistance;
    }
    
    public CombatEntry(final DamageSource damageSrc, final int field_94567_b, final float health, final float damage, final String field_94566_e, final float fallDistance) {
        this.damageSrc = damageSrc;
        this.field_94567_b = field_94567_b;
        this.damage = damage;
        this.health = health;
        this.field_94566_e = field_94566_e;
        this.fallDistance = fallDistance;
    }
    
    public float func_94563_c() {
        return this.damage;
    }
    
    public String func_94562_g() {
        return this.field_94566_e;
    }
}

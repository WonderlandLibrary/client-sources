package net.minecraft.enchantment;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

public class EnchantmentDamage extends Enchantment
{
    private static final String[] protectionName;
    public final int damageType;
    private static final int[] baseEnchantability;
    private static final int[] levelEnchantability;
    private static final String[] I;
    private static final int[] thresholdEnchantability;
    
    private static void I() {
        (I = new String[0x77 ^ 0x73])["".length()] = I("0\u00194", "QuXeS");
        EnchantmentDamage.I[" ".length()] = I("\u0010&\u001d\u001f2\u0001", "eHyzS");
        EnchantmentDamage.I["  ".length()] = I("4\u0007\u001c\u00027:\u0005\u0007\u000e6", "UuhjE");
        EnchantmentDamage.I["   ".length()] = I("\u0001\u0018.&$\n\u0002 ++\u0010X)/(\u0005\u0011(`", "dvMNE");
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        int n;
        if (enchantment instanceof EnchantmentDamage) {
            n = "".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        int n;
        if (itemStack.getItem() instanceof ItemAxe) {
            n = " ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            n = (super.canApply(itemStack) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public String getName() {
        return EnchantmentDamage.I["   ".length()] + EnchantmentDamage.protectionName[this.damageType];
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return EnchantmentDamage.baseEnchantability[this.damageType] + (n - " ".length()) * EnchantmentDamage.levelEnchantability[this.damageType];
    }
    
    @Override
    public void onEntityDamaged(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase2 = (EntityLivingBase)entity;
            if (this.damageType == "  ".length() && entityLivingBase2.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                entityLivingBase2.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, (0x7F ^ 0x6B) + entityLivingBase.getRNG().nextInt((0x5B ^ 0x51) * n), "   ".length()));
            }
        }
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + EnchantmentDamage.thresholdEnchantability[this.damageType];
    }
    
    public EnchantmentDamage(final int n, final ResourceLocation resourceLocation, final int n2, final int damageType) {
        super(n, resourceLocation, n2, EnumEnchantmentType.WEAPON);
        this.damageType = damageType;
    }
    
    static {
        I();
        final String[] protectionName2 = new String["   ".length()];
        protectionName2["".length()] = EnchantmentDamage.I["".length()];
        protectionName2[" ".length()] = EnchantmentDamage.I[" ".length()];
        protectionName2["  ".length()] = EnchantmentDamage.I["  ".length()];
        protectionName = protectionName2;
        final int[] baseEnchantability2 = new int["   ".length()];
        baseEnchantability2["".length()] = " ".length();
        baseEnchantability2[" ".length()] = (0xF ^ 0xA);
        baseEnchantability2["  ".length()] = (0x6C ^ 0x69);
        baseEnchantability = baseEnchantability2;
        final int[] levelEnchantability2 = new int["   ".length()];
        levelEnchantability2["".length()] = (0x93 ^ 0x98);
        levelEnchantability2[" ".length()] = (0x69 ^ 0x61);
        levelEnchantability2["  ".length()] = (0x2C ^ 0x24);
        levelEnchantability = levelEnchantability2;
        final int[] thresholdEnchantability2 = new int["   ".length()];
        thresholdEnchantability2["".length()] = (0xD5 ^ 0xC1);
        thresholdEnchantability2[" ".length()] = (0x75 ^ 0x61);
        thresholdEnchantability2["  ".length()] = (0x51 ^ 0x45);
        thresholdEnchantability = thresholdEnchantability2;
    }
    
    @Override
    public float calcDamageByCreature(final int n, final EnumCreatureAttribute enumCreatureAttribute) {
        float n2;
        if (this.damageType == 0) {
            n2 = n * 1.25f;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (this.damageType == " ".length() && enumCreatureAttribute == EnumCreatureAttribute.UNDEAD) {
            n2 = n * 2.5f;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (this.damageType == "  ".length() && enumCreatureAttribute == EnumCreatureAttribute.ARTHROPOD) {
            n2 = n * 2.5f;
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            n2 = 0.0f;
        }
        return n2;
    }
    
    @Override
    public int getMaxLevel() {
        return 0x43 ^ 0x46;
    }
}

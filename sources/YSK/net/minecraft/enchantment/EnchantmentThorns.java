package net.minecraft.enchantment;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class EnchantmentThorns extends Enchantment
{
    private static final String[] I;
    
    public static boolean func_92094_a(final int n, final Random random) {
        int n2;
        if (n <= 0) {
            n2 = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (random.nextFloat() < 0.15f * n) {
            n2 = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(",9!8,+", "XQNJB");
        EnchantmentThorns.I[" ".length()] = I("\u000e\u0015(8\u001f\u000fZ11\u0017\u0018\u001a6", "jtEYx");
    }
    
    static {
        I();
    }
    
    public EnchantmentThorns(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_TORSO);
        this.setName(EnchantmentThorns.I["".length()]);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0x48 ^ 0x7A);
    }
    
    @Override
    public void onUserHurt(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
        final Random rng = entityLivingBase.getRNG();
        final ItemStack enchantedItem = EnchantmentHelper.getEnchantedItem(Enchantment.thorns, entityLivingBase);
        if (func_92094_a(n, rng)) {
            if (entity != null) {
                entity.attackEntityFrom(DamageSource.causeThornsDamage(entityLivingBase), func_92095_b(n, rng));
                entity.playSound(EnchantmentThorns.I[" ".length()], 0.5f, 1.0f);
            }
            if (enchantedItem != null) {
                enchantedItem.damageItem("   ".length(), entityLivingBase);
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
        }
        else if (enchantedItem != null) {
            enchantedItem.damageItem(" ".length(), entityLivingBase);
        }
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        int n;
        if (itemStack.getItem() instanceof ItemArmor) {
            n = " ".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n = (super.canApply(itemStack) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public int getMaxLevel() {
        return "   ".length();
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return (0xCC ^ 0xC6) + (0x19 ^ 0xD) * (n - " ".length());
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int func_92095_b(final int n, final Random random) {
        int n2;
        if (n > (0x1F ^ 0x15)) {
            n2 = n - (0xAB ^ 0xA1);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n2 = " ".length() + random.nextInt(0x1B ^ 0x1F);
        }
        return n2;
    }
}

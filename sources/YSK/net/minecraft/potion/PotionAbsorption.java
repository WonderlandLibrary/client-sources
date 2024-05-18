package net.minecraft.potion;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;

public class PotionAbsorption extends Potion
{
    @Override
    public void removeAttributesModifiersFromEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() - (0x40 ^ 0x44) * (n + " ".length()));
        super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, n);
    }
    
    @Override
    public void applyAttributesModifiersToEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() + (0xC6 ^ 0xC2) * (n + " ".length()));
        super.applyAttributesModifiersToEntity(entityLivingBase, baseAttributeMap, n);
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected PotionAbsorption(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
}

package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class ItemNameTag extends Item
{
    public ItemNameTag() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        if (!itemStack.hasDisplayName()) {
            return "".length() != 0;
        }
        if (entityLivingBase instanceof EntityLiving) {
            final EntityLiving entityLiving = (EntityLiving)entityLivingBase;
            entityLiving.setCustomNameTag(itemStack.getDisplayName());
            entityLiving.enablePersistence();
            itemStack.stackSize -= " ".length();
            return " ".length() != 0;
        }
        return super.itemInteractionForEntity(itemStack, entityPlayer, entityLivingBase);
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}

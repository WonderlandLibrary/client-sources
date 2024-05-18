package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class ItemSaddle extends Item
{
    private static final String[] I;
    
    public ItemSaddle() {
        this.maxStackSize = " ".length();
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntityPig) {
            final EntityPig entityPig = (EntityPig)entityLivingBase;
            if (!entityPig.getSaddled() && !entityPig.isChild()) {
                entityPig.setSaddled(" ".length() != 0);
                entityPig.worldObj.playSoundAtEntity(entityPig, ItemSaddle.I["".length()], 0.5f, 1.0f);
                itemStack.stackSize -= " ".length();
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        this.itemInteractionForEntity(itemStack, null, entityLivingBase);
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0014%\u0005@2\u00168\u0014\u000bt\u0015/\u0006\u001a2\u001c8", "yJgnZ");
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}

package net.minecraft.client.gui.inventory;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class CreativeCrafting implements ICrafting
{
    private final Minecraft mc;
    
    @Override
    public void updateCraftingInventory(final Container container, final List<ItemStack> list) {
    }
    
    @Override
    public void sendSlotContents(final Container container, final int n, final ItemStack itemStack) {
        this.mc.playerController.sendSlotPacket(itemStack, n);
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void func_175173_a(final Container container, final IInventory inventory) {
    }
    
    @Override
    public void sendProgressBarUpdate(final Container container, final int n, final int n2) {
    }
    
    public CreativeCrafting(final Minecraft mc) {
        this.mc = mc;
    }
}

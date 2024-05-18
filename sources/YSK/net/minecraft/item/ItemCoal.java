package net.minecraft.item;

import net.minecraft.creativetab.*;
import java.util.*;

public class ItemCoal extends Item
{
    private static final String[] I;
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        String s;
        if (itemStack.getMetadata() == " ".length()) {
            s = ItemCoal.I["".length()];
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            s = ItemCoal.I[" ".length()];
        }
        return s;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0011 \"\u001fY\u001b<&\u0000\u0014\u00175+", "xTGrw");
        ItemCoal.I[" ".length()] = I("%\u0006+/F/\u001d/.", "LrNBh");
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), "".length()));
        list.add(new ItemStack(item, " ".length(), " ".length()));
    }
    
    public ItemCoal() {
        this.setHasSubtypes(" ".length() != 0);
        this.setMaxDamage("".length());
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
}

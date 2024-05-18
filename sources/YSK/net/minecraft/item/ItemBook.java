package net.minecraft.item;

public class ItemBook extends Item
{
    @Override
    public int getItemEnchantability() {
        return " ".length();
    }
    
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        if (itemStack.stackSize == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}

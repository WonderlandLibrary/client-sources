package net.minecraft.item;

import net.minecraft.block.*;

public class ItemCloth extends ItemBlock
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("Y", "wmePL");
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    static {
        I();
    }
    
    public ItemCloth(final Block block) {
        super(block);
        this.setMaxDamage("".length());
        this.setHasSubtypes(" ".length() != 0);
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + ItemCloth.I["".length()] + EnumDyeColor.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
    }
}

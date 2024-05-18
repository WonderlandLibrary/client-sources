package net.minecraft.item;

import net.minecraft.block.*;

public class ItemLeaves extends ItemBlock
{
    private static final String[] I;
    private final BlockLeaves leaves;
    
    @Override
    public int getMetadata(final int n) {
        return n | (0xA9 ^ 0xAD);
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
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return this.leaves.getRenderColor(this.leaves.getStateFromMeta(itemStack.getMetadata()));
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("O", "avbcJ");
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + ItemLeaves.I["".length()] + this.leaves.getWoodType(itemStack.getMetadata()).getUnlocalizedName();
    }
    
    public ItemLeaves(final BlockLeaves leaves) {
        super(leaves);
        this.leaves = leaves;
        this.setMaxDamage("".length());
        this.setHasSubtypes(" ".length() != 0);
    }
}

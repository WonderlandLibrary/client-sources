package net.minecraft.item;

import net.minecraft.block.*;

public class ItemColored extends ItemBlock
{
    private String[] subtypeNames;
    private static final String[] I;
    private final Block coloredBlock;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("b", "LvRxe");
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return this.coloredBlock.getRenderColor(this.coloredBlock.getStateFromMeta(itemStack.getMetadata()));
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
    
    static {
        I();
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        if (this.subtypeNames == null) {
            return super.getUnlocalizedName(itemStack);
        }
        final int metadata = itemStack.getMetadata();
        String s;
        if (metadata >= 0 && metadata < this.subtypeNames.length) {
            s = String.valueOf(super.getUnlocalizedName(itemStack)) + ItemColored.I["".length()] + this.subtypeNames[metadata];
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            s = super.getUnlocalizedName(itemStack);
        }
        return s;
    }
    
    public ItemColored setSubtypeNames(final String[] subtypeNames) {
        this.subtypeNames = subtypeNames;
        return this;
    }
    
    public ItemColored(final Block coloredBlock, final boolean b) {
        super(coloredBlock);
        this.coloredBlock = coloredBlock;
        if (b) {
            this.setMaxDamage("".length());
            this.setHasSubtypes(" ".length() != 0);
        }
    }
}

package net.minecraft.item;

import net.minecraft.block.*;

public class ItemAnvilBlock extends ItemMultiTexture
{
    private static final String[] I;
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0018).0-\u0005", "qGZQN");
        ItemAnvilBlock.I[" ".length()] = I(")\u0005\u0006\u0003\u001a.\u0005\u0016 \u00137\b\b\u0001\u0016", "Ziodr");
        ItemAnvilBlock.I["  ".length()] = I("%\u0014\u0013<\u00062\u001c\u0000\"'7", "SqaEB");
    }
    
    public ItemAnvilBlock(final Block block) {
        final String[] array = new String["   ".length()];
        array["".length()] = ItemAnvilBlock.I["".length()];
        array[" ".length()] = ItemAnvilBlock.I[" ".length()];
        array["  ".length()] = ItemAnvilBlock.I["  ".length()];
        super(block, block, array);
    }
    
    static {
        I();
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetadata(final int n) {
        return n << "  ".length();
    }
}

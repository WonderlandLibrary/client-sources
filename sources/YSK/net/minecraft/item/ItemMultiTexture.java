package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;

public class ItemMultiTexture extends ItemBlock
{
    protected final Function<ItemStack, String> nameFunction;
    protected final Block theBlock;
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + ItemMultiTexture.I["".length()] + (String)this.nameFunction.apply((Object)itemStack);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("l", "BsPln");
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    public ItemMultiTexture(final Block block, final Block block2, final String[] array) {
        this(block, block2, (Function<ItemStack, String>)new Function<ItemStack, String>(array) {
            private final String[] val$namesByMeta;
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
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
                    if (0 == 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public String apply(final ItemStack itemStack) {
                int n = itemStack.getMetadata();
                if (n < 0 || n >= this.val$namesByMeta.length) {
                    n = "".length();
                }
                return this.val$namesByMeta[n];
            }
        });
    }
    
    public ItemMultiTexture(final Block block, final Block theBlock, final Function<ItemStack, String> nameFunction) {
        super(block);
        this.theBlock = theBlock;
        this.nameFunction = nameFunction;
        this.setMaxDamage("".length());
        this.setHasSubtypes(" ".length() != 0);
    }
}

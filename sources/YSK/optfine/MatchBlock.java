package optfine;

import net.minecraft.block.state.*;

public class MatchBlock
{
    private int blockId;
    private int[] metadatas;
    
    public int[] getMetadatas() {
        return this.metadatas;
    }
    
    public MatchBlock(final int blockId) {
        this.blockId = -" ".length();
        this.metadatas = null;
        this.blockId = blockId;
    }
    
    public boolean matches(final BlockStateBase blockStateBase) {
        if (blockStateBase.getBlockId() != this.blockId) {
            return "".length() != 0;
        }
        if (this.metadatas != null) {
            int n = "".length();
            final int metadata = blockStateBase.getMetadata();
            int i = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (i < this.metadatas.length) {
                if (this.metadatas[i] == metadata) {
                    n = " ".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            if (n == 0) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MatchBlock(final int blockId, final int[] metadatas) {
        this.blockId = -" ".length();
        this.metadatas = null;
        this.blockId = blockId;
        this.metadatas = metadatas;
    }
    
    public int getBlockId() {
        return this.blockId;
    }
}

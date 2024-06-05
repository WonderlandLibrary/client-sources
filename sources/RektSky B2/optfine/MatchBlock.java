package optfine;

import net.minecraft.block.state.*;

public class MatchBlock
{
    private int blockId;
    private int[] metadatas;
    
    public MatchBlock(final int p_i40_1_) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = p_i40_1_;
    }
    
    public MatchBlock(final int p_i41_1_, final int[] p_i41_2_) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = p_i41_1_;
        this.metadatas = p_i41_2_;
    }
    
    public int getBlockId() {
        return this.blockId;
    }
    
    public int[] getMetadatas() {
        return this.metadatas;
    }
    
    public boolean matches(final BlockStateBase p_matches_1_) {
        if (p_matches_1_.getBlockId() != this.blockId) {
            return false;
        }
        if (this.metadatas != null) {
            boolean flag = false;
            final int i = p_matches_1_.getMetadata();
            for (int j = 0; j < this.metadatas.length; ++j) {
                final int k = this.metadatas[j];
                if (k == i) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }
}

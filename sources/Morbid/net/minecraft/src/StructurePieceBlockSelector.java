package net.minecraft.src;

import java.util.*;

public abstract class StructurePieceBlockSelector
{
    protected int selectedBlockId;
    protected int selectedBlockMetaData;
    
    public abstract void selectBlocks(final Random p0, final int p1, final int p2, final int p3, final boolean p4);
    
    public int getSelectedBlockId() {
        return this.selectedBlockId;
    }
    
    public int getSelectedBlockMetaData() {
        return this.selectedBlockMetaData;
    }
}

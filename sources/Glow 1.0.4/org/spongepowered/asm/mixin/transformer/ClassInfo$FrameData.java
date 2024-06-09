package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.*;

public static class FrameData
{
    private static final String[] FRAMETYPES;
    public final int index;
    public final int type;
    public final int locals;
    
    FrameData(final int index, final int type, final int locals) {
        super();
        this.index = index;
        this.type = type;
        this.locals = locals;
    }
    
    FrameData(final int index, final FrameNode frameNode) {
        super();
        this.index = index;
        this.type = frameNode.type;
        this.locals = ((frameNode.local != null) ? frameNode.local.size() : 0);
    }
    
    @Override
    public String toString() {
        return String.format("FrameData[index=%d, type=%s, locals=%d]", this.index, FrameData.FRAMETYPES[this.type + 1], this.locals);
    }
    
    static {
        FRAMETYPES = new String[] { "NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1" };
    }
}

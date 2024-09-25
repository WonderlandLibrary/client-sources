package eze.util;

import net.minecraft.util.*;

public class BlockInfo
{
    public EnumFacing face;
    public BlockPos position;
    
    public BlockInfo(final BlockPos position, final EnumFacing face) {
        this.position = position;
        this.face = face;
    }
}

package net.minecraft.world;

import net.minecraft.util.*;

public class PortalPosition extends BlockPos
{
    public long lastUpdateTime;
    
    public PortalPosition(final BlockPos p_i45747_2_, final long p_i45747_3_) {
        super(p_i45747_2_.getX(), p_i45747_2_.getY(), p_i45747_2_.getZ());
        this.lastUpdateTime = p_i45747_3_;
    }
}

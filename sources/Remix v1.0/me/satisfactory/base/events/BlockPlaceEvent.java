package me.satisfactory.base.events;

import me.satisfactory.base.events.event.callables.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockPlaceEvent extends EventCancellable
{
    private ItemStack heldStack;
    private BlockPos hitPos;
    private EnumFacing side;
    private Vec3 hitVec;
    
    public BlockPlaceEvent(final ItemStack heldStack, final BlockPos hitPos, final EnumFacing side, final Vec3 hitVec) {
        this.heldStack = heldStack;
        this.hitPos = hitPos;
        this.side = side;
        this.hitVec = hitVec;
    }
}

package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class RightClickBlockEvent extends Event {

    public final BlockPos clickPos;
    public final int side;
    public final ItemStack stack;
    public final Vec3 hitVec;

    public RightClickBlockEvent(BlockPos clickPos, int side, ItemStack stack, Vec3 hitVec) {
        this.clickPos = clickPos;
        this.side = side;
        this.stack = stack;
        this.hitVec = hitVec;
    }

}

package com.alan.clients.module.impl.movement.jesus;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.module.impl.movement.Jesus;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;

public class VanillaJesus extends Mode<Jesus> {

    public VanillaJesus(String name, Jesus parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        if (event.getBlock() instanceof BlockLiquid && !mc.gameSettings.keyBindSneak.isKeyDown()) {
            final int x = event.getBlockPos().getX();
            final int y = event.getBlockPos().getY();
            final int z = event.getBlockPos().getZ();

            event.setBoundingBox(AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 1, z + 1));
        }
    };
}
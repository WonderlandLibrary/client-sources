package com.polarware.module.impl.movement.flight;

import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.BlockAABBEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

public final class AirWalkFlight extends Mode<FlightModule> {

    public AirWalkFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        // Sets The Bounding Box To The Players Y Position.
        if (event.getBlock() instanceof BlockAir && !InstanceAccess.mc.thePlayer.isSneaking()) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < InstanceAccess.mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
            }
        }
    };
}

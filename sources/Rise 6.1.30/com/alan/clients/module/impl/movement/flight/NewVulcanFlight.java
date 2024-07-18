package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.KeyboardInputEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

public class NewVulcanFlight extends Mode<Flight> {

    public NewVulcanFlight(String name, Flight parent) {
        super(name, parent);
    }

    private final NumberValue speed = new NumberValue("Speed", this, 1, 1, 10, 0.1);
    private Vector3d teleport;
    private boolean attempt;
    private int teleports;

    @Override
    public void onEnable() {
        teleport = null;
        attempt = false;
        teleports = 0;
    }

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (teleports > 0 && !attempt) {
            teleports--;
            mc.timer.timerSpeed = speed.getValue().floatValue();
        }
    };

    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        // Sets The Bounding Box To The Players Y Position.
        if (event.getBlock() instanceof BlockAir && !mc.thePlayer.isSneaking()) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
            }
        }
    };

    @EventLink
    public final Listener<TeleportEvent> onTeleport = event -> {
        Vector3d position = new Vector3d(event.getPosX(), event.getPosY(), event.getPosZ());

        if (teleport == null) {
            teleport = position;
            event.setCancelled();
            teleports += 2;
        } else if (!teleport.equals(position)) {
            getParent().toggle();
        } else {
            event.setCancelled();
            teleports += 2;
        }
    };

    @EventLink
    public final Listener<KeyboardInputEvent> onKey = event -> {
        if (event.getKeyCode() == getParent().getKey() && !attempt) {
            event.setCancelled();
            mc.thePlayer.jump();
            attempt = true;
        }
    };
}

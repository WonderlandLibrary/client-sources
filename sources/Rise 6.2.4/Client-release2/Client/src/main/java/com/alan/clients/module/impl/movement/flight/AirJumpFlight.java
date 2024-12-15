package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

public class AirJumpFlight extends Mode<Flight> {
    private double y;

    public AirJumpFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        y = Math.floor(mc.thePlayer.posY);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
            y = mc.thePlayer.posY;
        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };

    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        if (event.getBlock() instanceof BlockAir && !mc.gameSettings.keyBindSneak.isKeyDown() && (mc.thePlayer.posY < y + 1 || mc.gameSettings.keyBindJump.isKeyDown())) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(
                        -15,
                        -1,
                        -15,
                        15,
                        1,
                        15
                ).offset(x, y, z));
            }
        }
    };

    @EventLink
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneak(false);
    };
}
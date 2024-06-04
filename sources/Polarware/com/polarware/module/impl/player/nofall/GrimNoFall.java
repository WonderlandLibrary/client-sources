package com.polarware.module.impl.player.nofall;

import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.BlockAABBEvent;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.module.impl.player.NoFallModule;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author Blazer
 * @since 19.10.2023
 */

public class GrimNoFall extends Mode<NoFallModule> {

    private boolean didFunny = false;

    public GrimNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        ChatUtil.display("Do not move while are you falling, you will take damage if you do");
    }

    @EventLink
    public Listener<BlockAABBEvent> onBlockAAB = event -> {
        if (mc.thePlayer.fallDistance >= 3.0f && !didFunny) {
            if (event.getBlock() instanceof BlockAir && !InstanceAccess.mc.thePlayer.isSneaking()) {
                final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

                if (y < InstanceAccess.mc.thePlayer.posY) {
                    event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
                }
            }
        }
    };

    @EventLink
    public Listener<PreMotionEvent> onPreMotion = event -> {
        float distance = FallDistanceComponent.distance;

        if (PlayerUtil.isBlockUnder()) {
            if (distance > 2) {
                MoveUtil.strafe(0.19);
            }

            if (distance > 3 && MoveUtil.speed() < 0.2) {
                event.setOnGround(true);
                distance = 0;
            }
        }

        FallDistanceComponent.distance = distance;
    };

    @Override
    public void onDisable() {
        didFunny = false;
    }

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        didFunny = true;
    };

}
package com.alan.clients.module.impl.movement.wallclimb;

import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.impl.movement.WallClimb;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class WatchdogWallClimb extends Mode<WallClimb> {

    private boolean active;

    public WatchdogWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mc.thePlayer.isCollidedHorizontally) {
            if (mc.thePlayer.onGround) {
                active = true;
            }

            if (active) {
                int block = SlotUtil.findBlock();

                if (block != -1) {
                    getComponent(Slot.class).setSlot(block);
                    if (getComponent(Slot.class).getItemIndex() == block) {
                        PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
                    }

                    mc.thePlayer.motionY = 0.42f;
                }
            }
        } else {
            active = false;
        }
    };
}
package com.polarware.module.impl.movement.wallclimb;

import com.polarware.component.impl.player.SlotComponent;
import com.polarware.module.impl.movement.WallClimbModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.SlotUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Alan
 * @since 22/3/2022
 */
public class WatchdogWallClimb extends Mode<WallClimbModule> {

    private boolean active;

    public WatchdogWallClimb(String name, WallClimbModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally) {
            if (InstanceAccess.mc.thePlayer.onGround) {
                active = true;
            }

            if (active) {
                int block = SlotUtil.findBlock();

                if (block != -1) {
                    SlotComponent.setSlot(block, false);
                    if (SlotComponent.getItemIndex() == block) {
                        PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(InstanceAccess.mc.thePlayer).down(), EnumFacing.UP.getIndex(), SlotComponent.getItemStack(), 0.0F, 1.0F, 0.0F));
                    }

                    InstanceAccess.mc.thePlayer.motionY = 0.42f;
                }
            }
        } else {
            active = false;
        }
    };
}
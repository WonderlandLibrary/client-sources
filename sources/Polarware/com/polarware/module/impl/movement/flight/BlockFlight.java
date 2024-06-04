package com.polarware.module.impl.movement.flight;

import com.polarware.component.impl.player.RotationComponent;
import com.polarware.component.impl.player.rotationcomponent.MovementFix;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Alan
 * @since 31.03.2022
 */

public class BlockFlight extends Mode<FlightModule>  {

    public BlockFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), 100, MovementFix.NORMAL);
        if (PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof BlockAir) {
            PacketUtil.send(new C0APacketAnimation());

            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                    mc.thePlayer.getCurrentEquippedItem(),
                    new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ),
                    EnumFacing.UP, mc.objectMouseOver.hitVec);
        }
    };
}
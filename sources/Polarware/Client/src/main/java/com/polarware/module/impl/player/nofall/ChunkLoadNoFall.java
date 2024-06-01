package com.polarware.module.impl.player.nofall;

import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.module.impl.player.NoFallModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

/**
 * @author Strikeless
 * @since 12.05.2022
 */
public class ChunkLoadNoFall extends Mode<NoFallModule> {

    private boolean fakeUnloaded;

    public ChunkLoadNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (fakeUnloaded) {
            mc.thePlayer.motionY = 0.0D;

            event.setOnGround(false);
            event.setPosY(event.getPosY() - 0.098F);
            mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, event.getPosY(), mc.thePlayer.posZ);

            return;
        }

        if (mc.thePlayer.motionY > 0.0D || FallDistanceComponent.distance <= 3.0F) {
            return;
        }

        final Block nextBlock = PlayerUtil.block(new BlockPos(
                event.getPosX(),
                event.getPosY() + mc.thePlayer.motionY,
                event.getPosZ()
        ));

        if (nextBlock.getMaterial().isSolid()) {
            FallDistanceComponent.distance = 0.0F;
            fakeUnloaded = true;
        }
    };

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        fakeUnloaded = false;
    };
}

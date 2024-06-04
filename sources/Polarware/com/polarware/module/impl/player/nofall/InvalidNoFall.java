package com.polarware.module.impl.player.nofall;

import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.module.impl.player.NoFallModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

/**
 * @author Strikeless
 * @since 13.03.2022
 */
public class InvalidNoFall extends Mode<NoFallModule> {

    public InvalidNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.motionY > 0) {
            return;
        }

        float distance = FallDistanceComponent.distance;

        if (distance > 3) {
            final Block nextBlock = PlayerUtil.block(new BlockPos(event.getPosX(), event.getPosY() + mc.thePlayer.motionY, event.getPosZ()));

            if (nextBlock.getMaterial().isSolid()) {
                event.setPosY(event.getPosY() - 999);
                distance = 0;
            }
        }

        FallDistanceComponent.distance = distance;
    };
}
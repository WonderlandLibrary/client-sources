package com.boomer.client.module.modules.player;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.module.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.awt.*;

/**
 * made by Xen for BoomerWare
 *
 * @since 6/10/2019
 **/
public class AntiVoid extends Module {
    public AntiVoid() {
        super("AntiVoid", Category.PLAYER, new Color(223, 233, 233).getRGB());
        setDescription("Flags you back when falling into the void");
        setRenderlabel("Anti Void");
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if(event.isPre()) {
            if (mc.thePlayer.fallDistance > 5) {
                if (mc.thePlayer.posY < 0) {
                    event.setY(event.getY() + 8);
                } else {
                    for (int i = (int) Math.ceil(mc.thePlayer.posY); i >= 0; i--) {
                        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ)).getBlock() != Blocks.air) {
                            return;
                        }
                    }

                    event.setY(event.getY() + 8);
                }
            }
        }
    }

}

package com.polarware.module.impl.player.scaffold.tower;

import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class    NCPTower extends Mode<ScaffoldModule> {

    public NCPTower(String name, ScaffoldModule parent) {
        super(name, parent);
    }

    private int tick;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2)) {
            PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(null));
            tick++;
            if(tick > 4) {
                tick = 0;
            }
//            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
            if(tick != 4) {
                if (mc.thePlayer.posY % 1 <= 0.00153598) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                    mc.thePlayer.motionY = 0.42F;
                } else if (mc.thePlayer.posY % 1 < 0.1 && mc.thePlayer.offGroundTicks != 0) {
                    mc.thePlayer.motionY = 0;
                    mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                }
            } else {
                if(mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.33;
                };
             //   mc.thePlayer.motionY = 0.33;
            }

            ChatUtil.display(tick);
        }
    };
}

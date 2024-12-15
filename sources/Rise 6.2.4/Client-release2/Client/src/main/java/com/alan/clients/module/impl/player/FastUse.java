package com.alan.clients.module.impl.player;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(aliases = {"module.player.fastuse.name"}, description = "module.player.fastuse.description", category = Category.PLAYER)
public class FastUse extends Module {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 1, 24, 1);

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.isUsingItem() && mc.thePlayer.itemInUseCount == 31) {
            for (int i = 0; i <= speed.getValue().intValue(); i++) {
                PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
            }
        }

    };
}
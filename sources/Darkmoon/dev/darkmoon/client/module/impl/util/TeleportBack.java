package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import net.minecraft.network.play.client.CPacketPlayer;

@ModuleAnnotation(name = "TeleportBack", category = Category.UTIL)
public class TeleportBack extends Module {
    private final TimerHelper timerHelper = new TimerHelper();

    @Override
    public void onEnable() {
        ChatUtility.addChatMessage("Hold shift, to teleport at start position. You can't jump with TeleportBack.");
        super.onEnable();
    }

    @EventTarget
    public void onPreMotion(EventMotion event) {
        event.setOnGround(false);
        mc.player.motionX *= 0.9f;
        mc.player.motionZ *= 0.9f;
        mc.player.setSprinting(false);
        if (!mc.player.isSneaking()) {
            return;
        }
        if (mc.player.ticksExisted % 5 == 0) {
            ChatUtility.addChatMessage("Initialize Exploit...");
        }
        if (mc.player.onGround) {
            mc.player.jump();
        }
        if (timerHelper.hasReached(30.0)) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, 200.0, mc.player.posZ, true));
            timerHelper.reset();
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 5.0, mc.player.posZ, true));
            super.onDisable();
        }
    }
}
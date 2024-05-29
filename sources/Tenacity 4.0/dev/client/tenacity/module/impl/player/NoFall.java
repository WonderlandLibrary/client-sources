package dev.client.tenacity.module.impl.player;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.Wrapper;
import dev.client.tenacity.utils.player.ChatUtils;
import dev.event.EventListener;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.utils.network.PacketUtils;
import io.netty.util.NetUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@SuppressWarnings("unused")
public final class NoFall extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Packet", "Edit");

    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        if (event.isPre()) {
            if (mc.thePlayer.fallDistance > 3.0) {
                switch (mode.getMode()) {
                    case "Vanilla":
                        event.setOnGround(true);
                        break;
                    case "Packet":
                        PacketUtils.sendPacket(new C03PacketPlayer(true));
                        break;
                }
                mc.thePlayer.fallDistance = 0;
            }
        }
    };

    private final EventListener<PacketSendEvent> packetSendEventEventListener = event -> {
    };

    public NoFall() {
        super("NoFall", Category.PLAYER, "pervents fall damage");
        this.addSettings(mode);
    }
}

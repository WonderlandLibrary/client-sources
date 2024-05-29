package dev.client.tenacity.module.impl.player;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.movement.Flight;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import dev.utils.misc.MathUtils;
import dev.utils.network.PacketUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiVoid extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog");

    public AntiVoid() {
        super("AntiVoid", Category.PLAYER, "saves you from the void");
        this.addSettings(mode);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        this.setSuffix(mode.getMode());
        if (Tenacity.INSTANCE.isToggled(Flight.class) || mc.thePlayer.isDead) return;
        if (e.isPre()) {
            switch (mode.getMode()) {
                case "Watchdog":
                    PacketUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + MathUtils.getRandomInRange(10, 12), mc.thePlayer.posZ, false));
                    break;
            }
        }
    };

}

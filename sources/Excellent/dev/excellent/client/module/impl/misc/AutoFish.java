package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.time.TimerUtil;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.Hand;

@ModuleInfo(name = "Auto Fish", description = "Автоматически ловит рыбку", category = Category.MISC)
public class AutoFish extends Module {
    private final TimerUtil delay = TimerUtil.create();
    private boolean isHooked = false;
    private boolean needToHook = false;

    @Override
    protected void onDisable() {
        super.onDisable();
        delay.reset();
        isHooked = false;
        needToHook = false;
    }

    private final Listener<PacketEvent> onPacket = event -> {
        if (mc.player == null || mc.world == null) return;
        if (event.getPacket() instanceof SPlaySoundEffectPacket wrapper) {
            if (wrapper.getSound().getName().getPath().equals("entity.fishing_bobber.splash")) {
                isHooked = true;
                delay.reset();
            }
        }
    };
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (delay.hasReached(600) && isHooked) {
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            isHooked = false;
            needToHook = true;
            delay.reset();
        }

        if (delay.hasReached(300) && needToHook) {
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            needToHook = false;
            delay.reset();
        }
    };
}

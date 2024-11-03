package dev.stephen.nexus.module.modules.other;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.utils.mc.PlayerUtil;

public class Timer extends Module {
    public static final NumberSetting dhauohfeidbf = new NumberSetting("Timer", 0.1, 10, 1, 0.001);

    public Timer() {
        super("Timer", "Modify game speed", 0, ModuleCategory.OTHER);
        this.addSetting(dhauohfeidbf);
    }

    @Override
    public void onEnable() {
        PlayerUtil.setTimer(dhauohfeidbf.getValueFloat());
        super.onEnable();
    }

    @EventLink
    public final Listener<EventTickPre> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        PlayerUtil.setTimer(dhauohfeidbf.getValueFloat());
    };

    @Override
    public void onDisable() {
        PlayerUtil.setTimer(1.0f);
        super.onDisable();
    }
}

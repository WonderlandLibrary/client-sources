package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.movement.clicktp.MospixelClickTp;
import dev.stephen.nexus.module.modules.movement.clicktp.VanillaClickTp;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;

public class ClickTP extends Module {
    public final NewModeSetting clickTPMode = new NewModeSetting("ClickTP Mode", "Vanilla",
            new VanillaClickTp("Vanilla", this),
            new MospixelClickTp("Mospixel", this));

    public ClickTP() {
        super("ClickTP", "Middle Click To Teleport", 0, ModuleCategory.MOVEMENT);
        this.addSettings(clickTPMode);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(clickTPMode.getCurrentMode().getName());
    };
}

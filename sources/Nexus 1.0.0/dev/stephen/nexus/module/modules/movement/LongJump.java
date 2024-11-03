package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.movement.longjump.DoubleJumpLongJump;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;

public class LongJump extends Module {
    public final NewModeSetting longJumpMode = new NewModeSetting("Long Jump Mode", "Double Jump",
            new DoubleJumpLongJump("Double Jump", this));

    public LongJump() {
        super("LongJump", "Jumps big long distance", 0, ModuleCategory.MOVEMENT);
        this.addSetting(longJumpMode);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(longJumpMode.getCurrentMode().getName());
    };
}

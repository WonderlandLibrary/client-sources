package dev.stephen.nexus.module.modules.player;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.player.regen.FullRegen;
import dev.stephen.nexus.module.modules.player.regen.VanillaRegen;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;

public class Regen extends Module {
    public final NewModeSetting regenMode = new NewModeSetting("Regen Mode", "Vanilla",
            new VanillaRegen("Vanilla", this),
            new FullRegen("Full", this));

    public final NumberSetting health = new NumberSetting("Health", 1, 20, 10, 1);
    public final NumberSetting packets = new NumberSetting("Packets", 1, 100, 30, 1);

    public Regen() {
        super("Regen", "Regen your health", 0, ModuleCategory.PLAYER);
        this.addSettings(regenMode, health, packets);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
      this.setSuffix(regenMode.getCurrentMode().getName());
    };
}

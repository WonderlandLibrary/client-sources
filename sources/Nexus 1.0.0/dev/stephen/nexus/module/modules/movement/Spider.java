package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.modules.movement.spider.VanillaSpider;
import dev.stephen.nexus.module.modules.movement.spider.VerusSpider;
import dev.stephen.nexus.module.modules.movement.spider.VulcanSpider;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;
import dev.stephen.nexus.utils.mc.MoveUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;

public class Spider extends Module {

    public final NewModeSetting spiderMode = new NewModeSetting("Mode", "Vanilla",
            new VanillaSpider("Vanilla", this),
            new VerusSpider("Verus", this),
            new VulcanSpider("Vulcan", this));

    public final NumberSetting verticalMotion = new NumberSetting("Vertical Motion", 0.1, 1, 0.42, 0.01);

    public Spider() {
        super("Spider", "Allows you to climb walls", 0, ModuleCategory.MOVEMENT);
        this.addSettings(spiderMode, verticalMotion);

        verticalMotion.addDependency(spiderMode, "Vanilla");
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        this.setSuffix(spiderMode.getCurrentMode().getName());
    };
}

package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.modules.movement.fly.AirWalkFly;
import dev.stephen.nexus.module.modules.movement.fly.GrimBoatFly;
import dev.stephen.nexus.module.modules.movement.fly.VanillaFly;
import dev.stephen.nexus.module.modules.movement.fly.VulcanGlideFly;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;

public class Fly extends Module {
    public final NewModeSetting flyMode = new NewModeSetting("Fly Mode", "Vanilla",
            new VanillaFly("Vanilla", this),
            new AirWalkFly("Air Walk", this),
            new GrimBoatFly("Grim Boat", this),
            new VulcanGlideFly("Vulcan Glide", this));

    public final NumberSetting speed = new NumberSetting("Speed", 0, 5, 1, 0.001);


    public Fly() {
        super("Fly", "Lets you fly in vanilla", 0, ModuleCategory.MOVEMENT);
        addSettings(flyMode, speed);
        speed.addDependency(flyMode, "Vanilla");
    }

    @Override
    public void onEnable() {
        if (isNull()) {
            toggle();
            return;
        }
        super.onEnable();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(flyMode.getCurrentMode().getName());
    };
}
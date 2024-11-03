package dev.stephen.nexus.module.modules.player;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.modules.player.antivoid.BlinkAntiVoid;
import dev.stephen.nexus.module.modules.player.antivoid.MospixelAntiVoid;
import dev.stephen.nexus.module.modules.player.antivoid.MotionFlagAntiVoid;
import dev.stephen.nexus.module.modules.player.antivoid.VanillaAntiVoid;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;

public class AntiVoid extends Module {
    public NewModeSetting mode = new NewModeSetting("AntiVoid Mode", "Vanilla", new VanillaAntiVoid("Vanilla", this), new MotionFlagAntiVoid("MotionFlag", this), new MospixelAntiVoid("Mospixel", this), new BlinkAntiVoid("Blink", this));
    public final NumberSetting minFallDistance = new NumberSetting("Min AntiVoid Distance", 2, 30, 8, 1);

    public AntiVoid() {
        super("AntiVoid", "Prevents you from falling in the void", 0, ModuleCategory.PLAYER);
        this.addSettings(mode, minFallDistance);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(mode.getCurrentMode().getName());
    };
}

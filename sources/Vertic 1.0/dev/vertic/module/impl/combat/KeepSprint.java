package dev.vertic.module.impl.combat;

import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.NumberSetting;

public class KeepSprint extends Module {

    private final NumberSetting airMotion = new NumberSetting("Air motion", 0.6D, 0.6D, 1.0D, 0.1D);
    private final NumberSetting groundMotion = new NumberSetting("Ground motion", 1.0D, 0.6D, 1.0D, 0.1D);

    public KeepSprint() {
        super("KeepSprint", "Prevents you from slowing down after hitting an entity.", Category.COMBAT);
        this.addSettings(airMotion, groundMotion);
    }

    public double getMotion() {
        return mc.thePlayer.onGround ? groundMotion.getValue() : airMotion.getValue();
    }

}

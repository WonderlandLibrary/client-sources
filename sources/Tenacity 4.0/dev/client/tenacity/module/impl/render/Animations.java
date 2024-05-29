package dev.client.tenacity.module.impl.render;


import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;

public final class Animations extends Module {

    public static final ModeSetting modeSetting = new ModeSetting("Mode", "LoL",
            "LoL", "Stella", "Fathum", "1.7", "Exhi", "Exhi 2", "Shred", "Smooth", "Sigma");
    public static final BooleanSetting oldDamage = new BooleanSetting("Old Damage", false);

    public Animations() {
        super("Animations", Category.RENDER, "changes animations");
        this.addSettings(modeSetting, oldDamage);
    }

}

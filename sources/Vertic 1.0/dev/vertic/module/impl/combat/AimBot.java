package dev.vertic.module.impl.combat;

import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.*;

public class AimBot extends Module {

    private final NumberSetting range = new NumberSetting("Range", 3.5, 3.0, 6.0, 0.1);
    private final ModeSetting sort = new ModeSetting("Sort", "Distance", "Distance", "Health", "FOV");
    private final BooleanSetting onlyClick = new BooleanSetting("Only on click", true);

    public AimBot() {
        super("Aimbot", "Instantly lock on aims at targets.", Category.COMBAT);
        this.addSettings(range, sort, onlyClick);
    }



}

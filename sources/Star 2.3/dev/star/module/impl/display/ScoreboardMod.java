package dev.star.module.impl.display;

import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.NumberSetting;

public class ScoreboardMod extends Module {

    public static final NumberSetting yOffset = new NumberSetting("Y Offset", 0, 250, 1, 5);
    public static final BooleanSetting textShadow = new BooleanSetting("Text Shadow", true);
    public static final BooleanSetting redNumbers = new BooleanSetting("Red Numbers", false);

    public ScoreboardMod() {
        super("Scoreboard", Category.DISPLAY, "Scoreboard preferences");
        this.addSettings(yOffset, textShadow, redNumbers);
        this.setToggled(true);
    }
}


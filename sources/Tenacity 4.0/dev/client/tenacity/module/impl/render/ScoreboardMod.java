package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.BooleanSetting;

public class ScoreboardMod extends Module {

    public static final BooleanSetting textShadow = new BooleanSetting("Text Shadow", true);
    public static final BooleanSetting redNumbers = new BooleanSetting("Red Numbers", false);

    public ScoreboardMod() {
        super("Scoreboard", Category.RENDER, "Scoreboard preferences");
        this.addSettings(textShadow, redNumbers);
        this.setToggled(true);
    }

}

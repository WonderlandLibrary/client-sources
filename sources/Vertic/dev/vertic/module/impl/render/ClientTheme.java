package dev.vertic.module.impl.render;

import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.util.Theme;

import java.awt.*;

public class ClientTheme extends Module {

    private final ModeSetting theme = new ModeSetting("Theme", "Vertic", Theme.themes);
    private final NumberSetting offset = new NumberSetting("Color offset", 120, 0, 240, 10);

    public ClientTheme() {
        super("Client Theme", "", Category.RENDER);
        this.addSettings(theme, offset);
    }

    public Color getColor(final int offset) {
        switch (theme.getMode().toLowerCase()) {
            case "fire":
                return Theme.FIRE.getColor(offset);
            case "skull":
                return Theme.SKULL.getColor(offset);
            case "sky":
                return Theme.SKY.getColor(offset);
            case "wave":
                return Theme.WAVE.getColor(offset);
            default:
                return Theme.VERTIC.getColor(offset);
        }
    }

}

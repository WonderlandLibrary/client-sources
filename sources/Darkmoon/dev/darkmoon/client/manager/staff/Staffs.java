package dev.darkmoon.client.manager.staff;

import dev.darkmoon.client.manager.theme.Theme;
import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.utility.render.font.Fonts;
import lombok.Getter;

import java.awt.*;
import java.util.HashMap;

public enum Staffs {
    ACTIVE(Fonts.icons21.drawString("J", 2, 3, new Color(255, 255, 255).getRGB())),
    SPEC(Fonts.icons21.drawString("J", 2, 3, new Color(255, 255, 255).getRGB())),
    NEAR(Fonts.icons21.drawString("J", 2, 3, new Color(255, 255, 255).getRGB()));
    @Getter
    private final float staff;
    Staffs(float staff) {
        this.staff = staff;
    }
}

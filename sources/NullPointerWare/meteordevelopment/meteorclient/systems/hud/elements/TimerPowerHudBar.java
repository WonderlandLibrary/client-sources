/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.hud.elements;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.world.GrimTimer;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;

public class TimerPowerHudBar extends HudElement {
    private static final Color WHITE = new Color();

    public static final HudElementInfo<TimerPowerHudBar> INFO = new HudElementInfo<>(Hud.GROUP, "TimerPowerBar", "Displays GrimTimer power in a bar.", TimerPowerHudBar::new);

    private final SettingGroup sgBackground = settings.createGroup("Background");

    private final Setting<Boolean> background = sgBackground.add(new BoolSetting.Builder()
        .name("background")
        .description("Displays background.")
        .defaultValue(false)
        .build()
    );

    private final Setting<SettingColor> backgroundColor = sgBackground.add(new ColorSetting.Builder()
        .name("background-color")
        .description("Color used for the background.")
        .visible(background::get)
        .defaultValue(new SettingColor(31, 31, 31, 255))
        .build()
    );

    public TimerPowerHudBar() {
        super(INFO);
    }

    @Override
    public void render(HudRenderer renderer) {
        String n = "0.0";
        double power = Modules.get().get(GrimTimer.class).power;
        double max = Modules.get().get(GrimTimer.class).maxuse.get();
        boolean usingTimer = Modules.get().get(GrimTimer.class).usingTimer;

        setSize(renderer.textWidth(n) + 10, 18);

        if (background.get()) {
            int backgroundWidth = 110; // Set a fixed width for the background rectangle
            setSize(backgroundWidth + 10, 18); // Add padding to the hitbox size
            renderer.quad(this.x - 5, this.y - 5, backgroundWidth, 18, backgroundColor.get());
        }

        // Set the color of the progress bar based on whether the timer is being used
        Color color = usingTimer ? new Color(255, 0, 0) : new Color(0, 255, 0);
        renderer.quad(this.x, this.y, (int) (100 * (power / max)), 8, color); // Scale the width of the bar based on the power percentage
    }

    double calculatepercentage(double n, double f) {
        return n * 100 / f;
    }
}

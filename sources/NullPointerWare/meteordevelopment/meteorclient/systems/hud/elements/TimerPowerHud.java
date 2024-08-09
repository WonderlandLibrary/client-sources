/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.hud.elements;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.world.GrimTimer;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class TimerPowerHud extends HudElement {
    private static final Color WHITE = new Color();

    public static final HudElementInfo<TimerPowerHud> INFO = new HudElementInfo<>(Hud.GROUP, "TimerPower", "Displays GrimTimer power.", TimerPowerHud::new);

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Boolean> percentage = sgGeneral.add(new BoolSetting.Builder()
        .name("percentage")
        .description("Displays percentage instead of an integer.")
        .defaultValue(true)
        .build()
    );

    public TimerPowerHud() {
        super(INFO);
    }

    @Override
    public void render(HudRenderer renderer) {
        String n = "0.0";
        if (percentage.get()) {
            n = String.valueOf(Math.round(calculatepercentage(Modules.get().get(GrimTimer.class).power, Modules.get().get(GrimTimer.class).maxuse.get()))) + "%";
        } else {
            n = String.valueOf(Modules.get().get(GrimTimer.class).power);
        }
        setSize(renderer.textWidth(n), renderer.textHeight());
        renderer.text(String.valueOf(n), this.x, this.y, Color.GREEN, true ,1.15d);
    }

    double calculatepercentage(double n, double f) {
        return n * 100 / f;
    }
}

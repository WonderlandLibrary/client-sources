/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.hud.elements;

import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.Disabler;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class VulcanSecondsHud extends HudElement {
    private static final Color WHITE = new Color();

    public static final HudElementInfo<VulcanSecondsHud> INFO = new HudElementInfo<>(Hud.GROUP, "VulcanSeconds", "Displays Vulcan seconds.", VulcanSecondsHud::new);

    public VulcanSecondsHud() {
        super(INFO);
    }

    @Override
    public void render(HudRenderer renderer) {
        String n = "0";
        n = String.valueOf(Modules.get().get(Disabler.class).vulcanfullticks/20);
        setSize(renderer.textWidth(n), renderer.textHeight());
        renderer.text(String.valueOf(n), this.x, this.y, Color.GREEN, true ,1.15d);
    }

    double calculatepercentage(double n, double f) {
        return n * 100 / f;
    }
}

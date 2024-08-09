/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public final class HideHacks extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public static boolean selfD = false;

    public HideHacks() {
        super(Categories.Misc, "HideHacks", "Hides hacks, useful in minehut sses lmfao");
    }

    @Override
    public void onActivate() {
        super.onActivate();
        selfD = true;
    }

    @Override
    public void onDeactivate() {
        super.onDeactivate();
        selfD = false;
    }
}

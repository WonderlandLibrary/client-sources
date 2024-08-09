/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public final class NoRotationLimit extends Module {

    public NoRotationLimit() {
        super(Categories.Misc, "no-rotation-limit", "Removes pitch limit.");
    }

    @Override
    public void onDeactivate() {
        mc.player.setPitch(wrapToRange(mc.player.getPitch(), -90, 90));
    }

    private float wrapToRange(float value, float minValue, float maxValue) {
        float range = maxValue - minValue;
        return ((value - minValue) % range + range) % range + minValue;
    }
}

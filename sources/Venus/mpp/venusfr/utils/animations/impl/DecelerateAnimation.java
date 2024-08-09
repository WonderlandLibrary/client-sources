/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.animations.impl;

import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;

public class DecelerateAnimation
extends Animation {
    public DecelerateAnimation(int n, double d) {
        super(n, d);
    }

    public DecelerateAnimation(int n, double d, Direction direction) {
        super(n, d, direction);
    }

    @Override
    protected double getEquation(double d) {
        double d2 = d / (double)this.duration;
        return 1.0 - (d2 - 1.0) * (d2 - 1.0);
    }
}


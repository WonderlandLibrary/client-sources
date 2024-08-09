/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.animations.impl;

import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;

public class EaseInOutQuad
extends Animation {
    public EaseInOutQuad(int n, double d) {
        super(n, d);
    }

    public EaseInOutQuad(int n, double d, Direction direction) {
        super(n, d, direction);
    }

    @Override
    protected double getEquation(double d) {
        double d2 = d / (double)this.duration;
        return d2 < 0.5 ? 2.0 * Math.pow(d2, 2.0) : 1.0 - Math.pow(-2.0 * d2 + 2.0, 2.0) / 2.0;
    }
}


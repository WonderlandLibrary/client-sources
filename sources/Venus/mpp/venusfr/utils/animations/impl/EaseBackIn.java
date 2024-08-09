/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.animations.impl;

import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;

public class EaseBackIn
extends Animation {
    private final float easeAmount;

    public EaseBackIn(int n, double d, float f) {
        super(n, d);
        this.easeAmount = f;
    }

    public EaseBackIn(int n, double d, float f, Direction direction) {
        super(n, d, direction);
        this.easeAmount = f;
    }

    @Override
    protected boolean correctOutput() {
        return false;
    }

    @Override
    protected double getEquation(double d) {
        double d2 = d / (double)this.duration;
        float f = this.easeAmount + 1.0f;
        return Math.max(0.0, 1.0 + (double)f * Math.pow(d2 - 1.0, 3.0) + (double)this.easeAmount * Math.pow(d2 - 1.0, 2.0));
    }
}


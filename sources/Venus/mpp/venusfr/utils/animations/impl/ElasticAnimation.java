/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.animations.impl;

import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;
import net.minecraft.util.math.MathHelper;

public class ElasticAnimation
extends Animation {
    float easeAmount;
    float smooth;
    boolean reallyElastic;

    public ElasticAnimation(int n, double d, float f, float f2, boolean bl) {
        super(n, d);
        this.easeAmount = f;
        this.smooth = f2;
        this.reallyElastic = bl;
    }

    public ElasticAnimation(int n, double d, float f, float f2, boolean bl, Direction direction) {
        super(n, d, direction);
        this.easeAmount = f;
        this.smooth = f2;
        this.reallyElastic = bl;
    }

    @Override
    protected double getEquation(double d) {
        double d2 = Math.pow(d / (double)this.duration, this.smooth);
        double d3 = this.easeAmount * 0.1f;
        return Math.pow(2.0, -10.0 * (this.reallyElastic ? Math.sqrt(d2) : d2)) * (double)MathHelper.sin((float)((d2 - d3 / 4.0) * (Math.PI * 2 / d3))) + 1.0;
    }
}


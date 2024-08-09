/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import com.jhlabs.math.Function2D;

public class MapFilter
extends TransformFilter {
    private Function2D xMapFunction;
    private Function2D yMapFunction;

    public void setXMapFunction(Function2D function2D) {
        this.xMapFunction = function2D;
    }

    public Function2D getXMapFunction() {
        return this.xMapFunction;
    }

    public void setYMapFunction(Function2D function2D) {
        this.yMapFunction = function2D;
    }

    public Function2D getYMapFunction() {
        return this.yMapFunction;
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = this.xMapFunction.evaluate(n, n2);
        float f2 = this.yMapFunction.evaluate(n, n2);
        fArray[0] = f * (float)this.transformedSpace.width;
        fArray[1] = f2 * (float)this.transformedSpace.height;
    }

    public String toString() {
        return "Distort/Map Coordinates...";
    }
}


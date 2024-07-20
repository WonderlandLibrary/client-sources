/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IExpression;

public class Constant
implements IExpression {
    private float value;

    public Constant(float value) {
        this.value = value;
    }

    @Override
    public float eval() {
        return this.value;
    }

    public String toString() {
        return "" + this.value;
    }
}


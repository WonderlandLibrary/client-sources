/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.optifine.expr.IExpressionFloat;
import net.optifine.shaders.uniform.ShaderParameterFloat;

public class ShaderParameterIndexed
implements IExpressionFloat {
    private ShaderParameterFloat type;
    private int index1;
    private int index2;

    public ShaderParameterIndexed(ShaderParameterFloat shaderParameterFloat) {
        this(shaderParameterFloat, 0, 0);
    }

    public ShaderParameterIndexed(ShaderParameterFloat shaderParameterFloat, int n) {
        this(shaderParameterFloat, n, 0);
    }

    public ShaderParameterIndexed(ShaderParameterFloat shaderParameterFloat, int n, int n2) {
        this.type = shaderParameterFloat;
        this.index1 = n;
        this.index2 = n2;
    }

    @Override
    public float eval() {
        return this.type.eval(this.index1, this.index2);
    }

    public String toString() {
        if (this.type.getIndexNames1() == null) {
            return "" + this.type;
        }
        return this.type.getIndexNames2() == null ? this.type + "." + this.type.getIndexNames1()[this.index1] : this.type + "." + this.type.getIndexNames1()[this.index1] + "." + this.type.getIndexNames2()[this.index2];
    }
}


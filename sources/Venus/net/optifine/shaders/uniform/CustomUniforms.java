/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.Map;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionCached;
import net.optifine.shaders.uniform.CustomUniform;

public class CustomUniforms {
    private CustomUniform[] uniforms;
    private IExpressionCached[] expressionsCached;

    public CustomUniforms(CustomUniform[] customUniformArray, Map<String, IExpression> map) {
        this.uniforms = customUniformArray;
        ArrayList<IExpressionCached> arrayList = new ArrayList<IExpressionCached>();
        for (String string : map.keySet()) {
            IExpression iExpression = map.get(string);
            if (!(iExpression instanceof IExpressionCached)) continue;
            IExpressionCached iExpressionCached = (IExpressionCached)((Object)iExpression);
            arrayList.add(iExpressionCached);
        }
        this.expressionsCached = arrayList.toArray(new IExpressionCached[arrayList.size()]);
    }

    public void setProgram(int n) {
        for (int i = 0; i < this.uniforms.length; ++i) {
            CustomUniform customUniform = this.uniforms[i];
            customUniform.setProgram(n);
        }
    }

    public void update() {
        this.resetCache();
        for (int i = 0; i < this.uniforms.length; ++i) {
            CustomUniform customUniform = this.uniforms[i];
            customUniform.update();
        }
    }

    private void resetCache() {
        for (int i = 0; i < this.expressionsCached.length; ++i) {
            IExpressionCached iExpressionCached = this.expressionsCached[i];
            iExpressionCached.reset();
        }
    }

    public void reset() {
        for (int i = 0; i < this.uniforms.length; ++i) {
            CustomUniform customUniform = this.uniforms[i];
            customUniform.reset();
        }
    }
}


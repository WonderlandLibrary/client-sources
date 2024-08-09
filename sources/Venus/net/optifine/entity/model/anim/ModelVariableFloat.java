/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.optifine.entity.model.anim.ModelVariableType;
import net.optifine.expr.IExpressionFloat;

public class ModelVariableFloat
implements IExpressionFloat {
    private String name;
    private ModelRenderer modelRenderer;
    private ModelVariableType enumModelVariable;

    public ModelVariableFloat(String string, ModelRenderer modelRenderer, ModelVariableType modelVariableType) {
        this.name = string;
        this.modelRenderer = modelRenderer;
        this.enumModelVariable = modelVariableType;
    }

    @Override
    public float eval() {
        return this.getValue();
    }

    public float getValue() {
        return this.enumModelVariable.getFloat(this.modelRenderer);
    }

    public void setValue(float f) {
        this.enumModelVariable.setFloat(this.modelRenderer, f);
    }

    public String toString() {
        return this.name;
    }
}


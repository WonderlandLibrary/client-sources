/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.optifine.entity.model.anim.ModelVariableFloat;
import net.optifine.expr.IExpressionResolver;

public interface IModelResolver
extends IExpressionResolver {
    public ModelRenderer getModelRenderer(String var1);

    public ModelVariableFloat getModelVariable(String var1);
}


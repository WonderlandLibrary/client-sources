/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.minecraft.client.model.ModelRenderer;
import net.optifine.entity.model.anim.IExpression;
import net.optifine.entity.model.anim.ModelVariable;

public interface IModelResolver {
    public ModelRenderer getModelRenderer(String var1);

    public ModelVariable getModelVariable(String var1);

    public IExpression getExpression(String var1);
}


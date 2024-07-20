/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.EnumRenderParameterEntity;
import net.optifine.entity.model.anim.IExpression;
import net.optifine.entity.model.anim.IRenderResolver;

public class RenderResolverEntity
implements IRenderResolver {
    @Override
    public IExpression getParameter(String name) {
        EnumRenderParameterEntity enumrenderparameterentity = EnumRenderParameterEntity.parse(name);
        return enumrenderparameterentity;
    }
}


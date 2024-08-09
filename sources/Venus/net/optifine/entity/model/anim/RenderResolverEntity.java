/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IRenderResolver;
import net.optifine.entity.model.anim.RenderEntityParameterBool;
import net.optifine.entity.model.anim.RenderEntityParameterFloat;
import net.optifine.expr.IExpression;

public class RenderResolverEntity
implements IRenderResolver {
    @Override
    public IExpression getParameter(String string) {
        RenderEntityParameterBool renderEntityParameterBool = RenderEntityParameterBool.parse(string);
        if (renderEntityParameterBool != null) {
            return renderEntityParameterBool;
        }
        RenderEntityParameterFloat renderEntityParameterFloat = RenderEntityParameterFloat.parse(string);
        return renderEntityParameterFloat != null ? renderEntityParameterFloat : null;
    }
}


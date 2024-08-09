/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.HashMap;
import java.util.Map;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;
import net.optifine.shaders.config.ExpressionShaderOptionSwitch;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionSwitch;

public class ShaderOptionResolver
implements IExpressionResolver {
    private Map<String, ExpressionShaderOptionSwitch> mapOptions = new HashMap<String, ExpressionShaderOptionSwitch>();

    public ShaderOptionResolver(ShaderOption[] shaderOptionArray) {
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            ShaderOption shaderOption = shaderOptionArray[i];
            if (!(shaderOption instanceof ShaderOptionSwitch)) continue;
            ShaderOptionSwitch shaderOptionSwitch = (ShaderOptionSwitch)shaderOption;
            ExpressionShaderOptionSwitch expressionShaderOptionSwitch = new ExpressionShaderOptionSwitch(shaderOptionSwitch);
            this.mapOptions.put(shaderOption.getName(), expressionShaderOptionSwitch);
        }
    }

    @Override
    public IExpression getExpression(String string) {
        return this.mapOptions.get(string);
    }
}


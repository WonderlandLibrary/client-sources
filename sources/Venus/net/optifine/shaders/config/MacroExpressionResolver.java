/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.Map;
import net.optifine.Config;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.FunctionBool;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;

public class MacroExpressionResolver
implements IExpressionResolver {
    private Map<String, String> mapMacroValues = null;

    public MacroExpressionResolver(Map<String, String> map) {
        this.mapMacroValues = map;
    }

    @Override
    public IExpression getExpression(String string) {
        String string2;
        String string3 = "defined_";
        if (string.startsWith(string3)) {
            String string4 = string.substring(string3.length());
            return this.mapMacroValues.containsKey(string4) ? new FunctionBool(FunctionType.TRUE, null) : new FunctionBool(FunctionType.FALSE, null);
        }
        while (this.mapMacroValues.containsKey(string) && (string2 = this.mapMacroValues.get(string)) != null && !string2.equals(string)) {
            string = string2;
        }
        int n = Config.parseInt(string, Integer.MIN_VALUE);
        if (n == Integer.MIN_VALUE) {
            Config.warn("Unknown macro value: " + string);
            return new ConstantFloat(0.0f);
        }
        return new ConstantFloat(n);
    }
}


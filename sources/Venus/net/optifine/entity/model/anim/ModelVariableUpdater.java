/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.optifine.Config;
import net.optifine.entity.model.anim.IModelResolver;
import net.optifine.entity.model.anim.ModelVariableFloat;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.ParseException;

public class ModelVariableUpdater {
    private String modelVariableName;
    private String expressionText;
    private ModelVariableFloat modelVariable;
    private IExpressionFloat expression;

    public boolean initialize(IModelResolver iModelResolver) {
        this.modelVariable = iModelResolver.getModelVariable(this.modelVariableName);
        if (this.modelVariable == null) {
            Config.warn("Model variable not found: " + this.modelVariableName);
            return true;
        }
        try {
            ExpressionParser expressionParser = new ExpressionParser(iModelResolver);
            this.expression = expressionParser.parseFloat(this.expressionText);
            return true;
        } catch (ParseException parseException) {
            Config.warn("Error parsing expression: " + this.expressionText);
            Config.warn(parseException.getClass().getName() + ": " + parseException.getMessage());
            return true;
        }
    }

    public ModelVariableUpdater(String string, String string2) {
        this.modelVariableName = string;
        this.expressionText = string2;
    }

    public void update() {
        float f = this.expression.eval();
        this.modelVariable.setValue(f);
    }
}


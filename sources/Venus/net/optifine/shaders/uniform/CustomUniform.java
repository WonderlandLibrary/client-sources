/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.optifine.expr.IExpression;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.uniform.ShaderUniformBase;
import net.optifine.shaders.uniform.UniformType;

public class CustomUniform {
    private String name;
    private UniformType type;
    private IExpression expression;
    private ShaderUniformBase shaderUniform;

    public CustomUniform(String string, UniformType uniformType, IExpression iExpression) {
        this.name = string;
        this.type = uniformType;
        this.expression = iExpression;
        this.shaderUniform = uniformType.makeShaderUniform(string);
    }

    public void setProgram(int n) {
        this.shaderUniform.setProgram(n);
    }

    public void update() {
        if (this.shaderUniform.isDefined()) {
            try {
                this.type.updateUniform(this.expression, this.shaderUniform);
            } catch (RuntimeException runtimeException) {
                SMCLog.severe("Error updating custom uniform: " + this.shaderUniform.getName());
                SMCLog.severe(runtimeException.getClass().getName() + ": " + runtimeException.getMessage());
                this.shaderUniform.disable();
                SMCLog.severe("Custom uniform disabled: " + this.shaderUniform.getName());
            }
        }
    }

    public void reset() {
        this.shaderUniform.reset();
    }

    public String getName() {
        return this.name;
    }

    public UniformType getType() {
        return this.type;
    }

    public IExpression getExpression() {
        return this.expression;
    }

    public ShaderUniformBase getShaderUniform() {
        return this.shaderUniform;
    }

    public String toString() {
        return this.type.name().toLowerCase() + " " + this.name;
    }
}


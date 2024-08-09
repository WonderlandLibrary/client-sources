/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionFloatArray;
import net.optifine.shaders.uniform.ShaderUniform1f;
import net.optifine.shaders.uniform.ShaderUniform1i;
import net.optifine.shaders.uniform.ShaderUniform2f;
import net.optifine.shaders.uniform.ShaderUniform3f;
import net.optifine.shaders.uniform.ShaderUniform4f;
import net.optifine.shaders.uniform.ShaderUniformBase;

public enum UniformType {
    BOOL,
    INT,
    FLOAT,
    VEC2,
    VEC3,
    VEC4;


    public ShaderUniformBase makeShaderUniform(String string) {
        switch (1.$SwitchMap$net$optifine$shaders$uniform$UniformType[this.ordinal()]) {
            case 1: {
                return new ShaderUniform1i(string);
            }
            case 2: {
                return new ShaderUniform1i(string);
            }
            case 3: {
                return new ShaderUniform1f(string);
            }
            case 4: {
                return new ShaderUniform2f(string);
            }
            case 5: {
                return new ShaderUniform3f(string);
            }
            case 6: {
                return new ShaderUniform4f(string);
            }
        }
        throw new RuntimeException("Unknown uniform type: " + this);
    }

    public void updateUniform(IExpression iExpression, ShaderUniformBase shaderUniformBase) {
        switch (1.$SwitchMap$net$optifine$shaders$uniform$UniformType[this.ordinal()]) {
            case 1: {
                this.updateUniformBool((IExpressionBool)iExpression, (ShaderUniform1i)shaderUniformBase);
                return;
            }
            case 2: {
                this.updateUniformInt((IExpressionFloat)iExpression, (ShaderUniform1i)shaderUniformBase);
                return;
            }
            case 3: {
                this.updateUniformFloat((IExpressionFloat)iExpression, (ShaderUniform1f)shaderUniformBase);
                return;
            }
            case 4: {
                this.updateUniformFloat2((IExpressionFloatArray)iExpression, (ShaderUniform2f)shaderUniformBase);
                return;
            }
            case 5: {
                this.updateUniformFloat3((IExpressionFloatArray)iExpression, (ShaderUniform3f)shaderUniformBase);
                return;
            }
            case 6: {
                this.updateUniformFloat4((IExpressionFloatArray)iExpression, (ShaderUniform4f)shaderUniformBase);
                return;
            }
        }
        throw new RuntimeException("Unknown uniform type: " + this);
    }

    private void updateUniformBool(IExpressionBool iExpressionBool, ShaderUniform1i shaderUniform1i) {
        boolean bl = iExpressionBool.eval();
        int n = bl ? 1 : 0;
        shaderUniform1i.setValue(n);
    }

    private void updateUniformInt(IExpressionFloat iExpressionFloat, ShaderUniform1i shaderUniform1i) {
        int n = (int)iExpressionFloat.eval();
        shaderUniform1i.setValue(n);
    }

    private void updateUniformFloat(IExpressionFloat iExpressionFloat, ShaderUniform1f shaderUniform1f) {
        float f = iExpressionFloat.eval();
        shaderUniform1f.setValue(f);
    }

    private void updateUniformFloat2(IExpressionFloatArray iExpressionFloatArray, ShaderUniform2f shaderUniform2f) {
        float[] fArray = iExpressionFloatArray.eval();
        if (fArray.length != 2) {
            throw new RuntimeException("Value length is not 2, length: " + fArray.length);
        }
        shaderUniform2f.setValue(fArray[0], fArray[1]);
    }

    private void updateUniformFloat3(IExpressionFloatArray iExpressionFloatArray, ShaderUniform3f shaderUniform3f) {
        float[] fArray = iExpressionFloatArray.eval();
        if (fArray.length != 3) {
            throw new RuntimeException("Value length is not 3, length: " + fArray.length);
        }
        shaderUniform3f.setValue(fArray[0], fArray[1], fArray[2]);
    }

    private void updateUniformFloat4(IExpressionFloatArray iExpressionFloatArray, ShaderUniform4f shaderUniform4f) {
        float[] fArray = iExpressionFloatArray.eval();
        if (fArray.length != 4) {
            throw new RuntimeException("Value length is not 4, length: " + fArray.length);
        }
        shaderUniform4f.setValue(fArray[0], fArray[1], fArray[2], fArray[3]);
    }

    public boolean matchesExpressionType(ExpressionType expressionType) {
        switch (1.$SwitchMap$net$optifine$shaders$uniform$UniformType[this.ordinal()]) {
            case 1: {
                return expressionType == ExpressionType.BOOL;
            }
            case 2: {
                return expressionType == ExpressionType.FLOAT;
            }
            case 3: {
                return expressionType == ExpressionType.FLOAT;
            }
            case 4: 
            case 5: 
            case 6: {
                return expressionType == ExpressionType.FLOAT_ARRAY;
            }
        }
        throw new RuntimeException("Unknown uniform type: " + this);
    }

    public static UniformType parse(String string) {
        UniformType[] uniformTypeArray = UniformType.values();
        for (int i = 0; i < uniformTypeArray.length; ++i) {
            UniformType uniformType = uniformTypeArray[i];
            if (!uniformType.name().toLowerCase().equals(string)) continue;
            return uniformType;
        }
        return null;
    }
}


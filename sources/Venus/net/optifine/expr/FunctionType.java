/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IParameters;
import net.optifine.expr.Parameters;
import net.optifine.expr.ParametersVariable;
import net.optifine.shaders.uniform.Smoother;
import net.optifine.util.MathUtils;

public enum FunctionType {
    PLUS(10, ExpressionType.FLOAT, "+", ExpressionType.FLOAT, ExpressionType.FLOAT),
    MINUS(10, ExpressionType.FLOAT, "-", ExpressionType.FLOAT, ExpressionType.FLOAT),
    MUL(11, ExpressionType.FLOAT, "*", ExpressionType.FLOAT, ExpressionType.FLOAT),
    DIV(11, ExpressionType.FLOAT, "/", ExpressionType.FLOAT, ExpressionType.FLOAT),
    MOD(11, ExpressionType.FLOAT, "%", ExpressionType.FLOAT, ExpressionType.FLOAT),
    NEG(12, ExpressionType.FLOAT, "neg", ExpressionType.FLOAT),
    PI(ExpressionType.FLOAT, "pi", new ExpressionType[0]),
    SIN(ExpressionType.FLOAT, "sin", ExpressionType.FLOAT),
    COS(ExpressionType.FLOAT, "cos", ExpressionType.FLOAT),
    ASIN(ExpressionType.FLOAT, "asin", ExpressionType.FLOAT),
    ACOS(ExpressionType.FLOAT, "acos", ExpressionType.FLOAT),
    TAN(ExpressionType.FLOAT, "tan", ExpressionType.FLOAT),
    ATAN(ExpressionType.FLOAT, "atan", ExpressionType.FLOAT),
    ATAN2(ExpressionType.FLOAT, "atan2", ExpressionType.FLOAT, ExpressionType.FLOAT),
    TORAD(ExpressionType.FLOAT, "torad", ExpressionType.FLOAT),
    TODEG(ExpressionType.FLOAT, "todeg", ExpressionType.FLOAT),
    MIN(ExpressionType.FLOAT, "min", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT)),
    MAX(ExpressionType.FLOAT, "max", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT)),
    CLAMP(ExpressionType.FLOAT, "clamp", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
    ABS(ExpressionType.FLOAT, "abs", ExpressionType.FLOAT),
    FLOOR(ExpressionType.FLOAT, "floor", ExpressionType.FLOAT),
    CEIL(ExpressionType.FLOAT, "ceil", ExpressionType.FLOAT),
    EXP(ExpressionType.FLOAT, "exp", ExpressionType.FLOAT),
    FRAC(ExpressionType.FLOAT, "frac", ExpressionType.FLOAT),
    LOG(ExpressionType.FLOAT, "log", ExpressionType.FLOAT),
    POW(ExpressionType.FLOAT, "pow", ExpressionType.FLOAT, ExpressionType.FLOAT),
    RANDOM(ExpressionType.FLOAT, "random", new ExpressionType[0]),
    ROUND(ExpressionType.FLOAT, "round", ExpressionType.FLOAT),
    SIGNUM(ExpressionType.FLOAT, "signum", ExpressionType.FLOAT),
    SQRT(ExpressionType.FLOAT, "sqrt", ExpressionType.FLOAT),
    FMOD(ExpressionType.FLOAT, "fmod", ExpressionType.FLOAT, ExpressionType.FLOAT),
    TIME(ExpressionType.FLOAT, "time", new ExpressionType[0]),
    IF(ExpressionType.FLOAT, "if", new ParametersVariable().first(ExpressionType.BOOL, ExpressionType.FLOAT).repeat(ExpressionType.BOOL, ExpressionType.FLOAT).last(ExpressionType.FLOAT)),
    NOT(12, ExpressionType.BOOL, "!", ExpressionType.BOOL),
    AND(3, ExpressionType.BOOL, "&&", ExpressionType.BOOL, ExpressionType.BOOL),
    OR(2, ExpressionType.BOOL, "||", ExpressionType.BOOL, ExpressionType.BOOL),
    GREATER(8, ExpressionType.BOOL, ">", ExpressionType.FLOAT, ExpressionType.FLOAT),
    GREATER_OR_EQUAL(8, ExpressionType.BOOL, ">=", ExpressionType.FLOAT, ExpressionType.FLOAT),
    SMALLER(8, ExpressionType.BOOL, "<", ExpressionType.FLOAT, ExpressionType.FLOAT),
    SMALLER_OR_EQUAL(8, ExpressionType.BOOL, "<=", ExpressionType.FLOAT, ExpressionType.FLOAT),
    EQUAL(7, ExpressionType.BOOL, "==", ExpressionType.FLOAT, ExpressionType.FLOAT),
    NOT_EQUAL(7, ExpressionType.BOOL, "!=", ExpressionType.FLOAT, ExpressionType.FLOAT),
    BETWEEN(7, ExpressionType.BOOL, "between", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
    EQUALS(7, ExpressionType.BOOL, "equals", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
    IN(ExpressionType.BOOL, "in", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT).last(ExpressionType.FLOAT)),
    SMOOTH(ExpressionType.FLOAT, "smooth", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT).maxCount(4)),
    TRUE(ExpressionType.BOOL, "true", new ExpressionType[0]),
    FALSE(ExpressionType.BOOL, "false", new ExpressionType[0]),
    VEC2(ExpressionType.FLOAT_ARRAY, "vec2", ExpressionType.FLOAT, ExpressionType.FLOAT),
    VEC3(ExpressionType.FLOAT_ARRAY, "vec3", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
    VEC4(ExpressionType.FLOAT_ARRAY, "vec4", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT);

    private int precedence;
    private ExpressionType expressionType;
    private String name;
    private IParameters parameters;
    public static FunctionType[] VALUES;
    private static final Map<Integer, Float> mapSmooth;

    private FunctionType(ExpressionType expressionType, String string2, ExpressionType ... expressionTypeArray) {
        this(0, expressionType, string2, expressionTypeArray);
    }

    private FunctionType(int n2, ExpressionType expressionType, String string2, ExpressionType ... expressionTypeArray) {
        this(n2, expressionType, string2, new Parameters(expressionTypeArray));
    }

    private FunctionType(ExpressionType expressionType, String string2, IParameters iParameters) {
        this(0, expressionType, string2, iParameters);
    }

    private FunctionType(int n2, ExpressionType expressionType, String string2, IParameters iParameters) {
        this.precedence = n2;
        this.expressionType = expressionType;
        this.name = string2;
        this.parameters = iParameters;
    }

    public String getName() {
        return this.name;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public ExpressionType getExpressionType() {
        return this.expressionType;
    }

    public IParameters getParameters() {
        return this.parameters;
    }

    public int getParameterCount(IExpression[] iExpressionArray) {
        return this.parameters.getParameterTypes(iExpressionArray).length;
    }

    public ExpressionType[] getParameterTypes(IExpression[] iExpressionArray) {
        return this.parameters.getParameterTypes(iExpressionArray);
    }

    public float evalFloat(IExpression[] iExpressionArray) {
        switch (1.$SwitchMap$net$optifine$expr$FunctionType[this.ordinal()]) {
            case 1: {
                return FunctionType.evalFloat(iExpressionArray, 0) + FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 2: {
                return FunctionType.evalFloat(iExpressionArray, 0) - FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 3: {
                return FunctionType.evalFloat(iExpressionArray, 0) * FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 4: {
                return FunctionType.evalFloat(iExpressionArray, 0) / FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 5: {
                float f = FunctionType.evalFloat(iExpressionArray, 0);
                float f2 = FunctionType.evalFloat(iExpressionArray, 1);
                return f - f2 * (float)((int)(f / f2));
            }
            case 6: {
                return -FunctionType.evalFloat(iExpressionArray, 0);
            }
            case 7: {
                return MathHelper.PI;
            }
            case 8: {
                return MathHelper.sin(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 9: {
                return MathHelper.cos(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 10: {
                return MathUtils.asin(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 11: {
                return MathUtils.acos(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 12: {
                return (float)Math.tan(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 13: {
                return (float)Math.atan(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 14: {
                return (float)MathHelper.atan2(FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1));
            }
            case 15: {
                return MathUtils.toRad(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 16: {
                return MathUtils.toDeg(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 17: {
                return this.getMin(iExpressionArray);
            }
            case 18: {
                return this.getMax(iExpressionArray);
            }
            case 19: {
                return MathHelper.clamp(FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1), FunctionType.evalFloat(iExpressionArray, 2));
            }
            case 20: {
                return MathHelper.abs(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 21: {
                return (float)Math.exp(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 22: {
                return MathHelper.floor(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 23: {
                return MathHelper.ceil(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 24: {
                return MathHelper.frac(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 25: {
                return (float)Math.log(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 26: {
                return (float)Math.pow(FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1));
            }
            case 27: {
                return (float)Math.random();
            }
            case 28: {
                return Math.round(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 29: {
                return Math.signum(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 30: {
                return MathHelper.sqrt(FunctionType.evalFloat(iExpressionArray, 0));
            }
            case 31: {
                float f = FunctionType.evalFloat(iExpressionArray, 0);
                float f3 = FunctionType.evalFloat(iExpressionArray, 1);
                return f - f3 * (float)MathHelper.floor(f / f3);
            }
            case 32: {
                Minecraft minecraft = Minecraft.getInstance();
                ClientWorld clientWorld = minecraft.world;
                if (clientWorld == null) {
                    return 0.0f;
                }
                return (float)(clientWorld.getGameTime() % 24000L) + minecraft.getRenderPartialTicks();
            }
            case 33: {
                int n = (iExpressionArray.length - 1) / 2;
                for (int i = 0; i < n; ++i) {
                    int n2 = i * 2;
                    if (!FunctionType.evalBool(iExpressionArray, n2)) continue;
                    return FunctionType.evalFloat(iExpressionArray, n2 + 1);
                }
                return FunctionType.evalFloat(iExpressionArray, n * 2);
            }
            case 34: {
                int n = (int)FunctionType.evalFloat(iExpressionArray, 0);
                float f = FunctionType.evalFloat(iExpressionArray, 1);
                float f4 = iExpressionArray.length > 2 ? FunctionType.evalFloat(iExpressionArray, 2) : 1.0f;
                float f5 = iExpressionArray.length > 3 ? FunctionType.evalFloat(iExpressionArray, 3) : f4;
                return Smoother.getSmoothValue(n, f, f4, f5);
            }
        }
        Config.warn("Unknown function type: " + this);
        return 0.0f;
    }

    private float getMin(IExpression[] iExpressionArray) {
        if (iExpressionArray.length == 2) {
            return Math.min(FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1));
        }
        float f = FunctionType.evalFloat(iExpressionArray, 0);
        for (int i = 1; i < iExpressionArray.length; ++i) {
            float f2 = FunctionType.evalFloat(iExpressionArray, i);
            if (!(f2 < f)) continue;
            f = f2;
        }
        return f;
    }

    private float getMax(IExpression[] iExpressionArray) {
        if (iExpressionArray.length == 2) {
            return Math.max(FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1));
        }
        float f = FunctionType.evalFloat(iExpressionArray, 0);
        for (int i = 1; i < iExpressionArray.length; ++i) {
            float f2 = FunctionType.evalFloat(iExpressionArray, i);
            if (!(f2 > f)) continue;
            f = f2;
        }
        return f;
    }

    private static float evalFloat(IExpression[] iExpressionArray, int n) {
        IExpressionFloat iExpressionFloat = (IExpressionFloat)iExpressionArray[n];
        return iExpressionFloat.eval();
    }

    public boolean evalBool(IExpression[] iExpressionArray) {
        switch (1.$SwitchMap$net$optifine$expr$FunctionType[this.ordinal()]) {
            case 35: {
                return false;
            }
            case 36: {
                return true;
            }
            case 37: {
                return !FunctionType.evalBool(iExpressionArray, 0);
            }
            case 38: {
                return FunctionType.evalBool(iExpressionArray, 0) && FunctionType.evalBool(iExpressionArray, 1);
            }
            case 39: {
                return FunctionType.evalBool(iExpressionArray, 0) || FunctionType.evalBool(iExpressionArray, 1);
            }
            case 40: {
                return FunctionType.evalFloat(iExpressionArray, 0) > FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 41: {
                return FunctionType.evalFloat(iExpressionArray, 0) >= FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 42: {
                return FunctionType.evalFloat(iExpressionArray, 0) < FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 43: {
                return FunctionType.evalFloat(iExpressionArray, 0) <= FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 44: {
                return FunctionType.evalFloat(iExpressionArray, 0) == FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 45: {
                return FunctionType.evalFloat(iExpressionArray, 0) != FunctionType.evalFloat(iExpressionArray, 1);
            }
            case 46: {
                float f = FunctionType.evalFloat(iExpressionArray, 0);
                return f >= FunctionType.evalFloat(iExpressionArray, 1) && f <= FunctionType.evalFloat(iExpressionArray, 2);
            }
            case 47: {
                float f = FunctionType.evalFloat(iExpressionArray, 0) - FunctionType.evalFloat(iExpressionArray, 1);
                float f2 = FunctionType.evalFloat(iExpressionArray, 2);
                return Math.abs(f) <= f2;
            }
            case 48: {
                float f = FunctionType.evalFloat(iExpressionArray, 0);
                for (int i = 1; i < iExpressionArray.length; ++i) {
                    float f3 = FunctionType.evalFloat(iExpressionArray, i);
                    if (f != f3) continue;
                    return false;
                }
                return true;
            }
        }
        Config.warn("Unknown function type: " + this);
        return true;
    }

    private static boolean evalBool(IExpression[] iExpressionArray, int n) {
        IExpressionBool iExpressionBool = (IExpressionBool)iExpressionArray[n];
        return iExpressionBool.eval();
    }

    public float[] evalFloatArray(IExpression[] iExpressionArray) {
        switch (1.$SwitchMap$net$optifine$expr$FunctionType[this.ordinal()]) {
            case 49: {
                return new float[]{FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1)};
            }
            case 50: {
                return new float[]{FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1), FunctionType.evalFloat(iExpressionArray, 2)};
            }
            case 51: {
                return new float[]{FunctionType.evalFloat(iExpressionArray, 0), FunctionType.evalFloat(iExpressionArray, 1), FunctionType.evalFloat(iExpressionArray, 2), FunctionType.evalFloat(iExpressionArray, 3)};
            }
        }
        Config.warn("Unknown function type: " + this);
        return null;
    }

    public static FunctionType parse(String string) {
        for (int i = 0; i < VALUES.length; ++i) {
            FunctionType functionType = VALUES[i];
            if (!functionType.getName().equals(string)) continue;
            return functionType;
        }
        return null;
    }

    static {
        VALUES = FunctionType.values();
        mapSmooth = new HashMap<Integer, Float>();
    }
}


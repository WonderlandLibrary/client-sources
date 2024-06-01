package net.optifine.entity.model.anim;

public class Parameters implements IParameters {
    private final ExpressionType[] parameterTypes;

    public Parameters(ExpressionType[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public ExpressionType[] getParameterTypes(IExpression[] params) {
        return this.parameterTypes;
    }
}

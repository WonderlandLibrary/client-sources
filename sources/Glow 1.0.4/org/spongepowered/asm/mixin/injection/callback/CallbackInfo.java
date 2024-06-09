package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.*;

public class CallbackInfo implements Cancellable
{
    private final String name;
    private final boolean cancellable;
    private boolean cancelled;
    
    public CallbackInfo(final String name, final boolean cancellable) {
        super();
        this.name = name;
        this.cancellable = cancellable;
    }
    
    public String getId() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", this.getClass().getSimpleName(), this.name, this.cancellable);
    }
    
    @Override
    public final boolean isCancellable() {
        return this.cancellable;
    }
    
    @Override
    public final boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void cancel() throws CancellationException {
        if (!this.cancellable) {
            throw new CancellationException(String.format("The call %s is not cancellable.", this.name));
        }
        this.cancelled = true;
    }
    
    static String getCallInfoClassName() {
        return CallbackInfo.class.getName();
    }
    
    public static String getCallInfoClassName(final Type type) {
        return (type.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/');
    }
    
    static String getConstructorDescriptor(final Type type) {
        if (type.equals(Type.VOID_TYPE)) {
            return getConstructorDescriptor();
        }
        if (type.getSort() == 10 || type.getSort() == 9) {
            return String.format("(%sZ%s)V", "Ljava/lang/String;", "Ljava/lang/Object;");
        }
        return String.format("(%sZ%s)V", "Ljava/lang/String;", type.getDescriptor());
    }
    
    static String getConstructorDescriptor() {
        return String.format("(%sZ)V", "Ljava/lang/String;");
    }
    
    static String getIsCancelledMethodName() {
        return "isCancelled";
    }
    
    static String getIsCancelledMethodSig() {
        return "()Z";
    }
}

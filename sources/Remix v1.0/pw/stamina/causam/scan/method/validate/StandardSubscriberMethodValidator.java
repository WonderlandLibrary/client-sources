package pw.stamina.causam.scan.method.validate;

import java.lang.reflect.*;

final class StandardSubscriberMethodValidator implements SubscriberMethodValidator
{
    @Override
    public void validate(final Method method) throws IllegalSubscriberMethodException {
        final String exceptionMessage = this._validate(method);
        if (exceptionMessage != null) {
            throw new IllegalSubscriberMethodException(method, exceptionMessage);
        }
    }
    
    public String _validate(final Method method) {
        if (!methodHasOneParameter(method)) {
            return "subscriber methods must have exactly 1 parameter";
        }
        if (isStaticMethod(method)) {
            return "subscriber methods cannot be static";
        }
        if (isSynchronizedMethod(method)) {
            return "subscriber methods cannot be synchronized, use the @Synchronize annotation instead";
        }
        return null;
    }
    
    private static boolean methodHasOneParameter(final Method method) {
        return method.getParameterCount() == 1;
    }
    
    private static boolean isStaticMethod(final Method method) {
        return Modifier.isStatic(method.getModifiers());
    }
    
    private static boolean isSynchronizedMethod(final Method method) {
        return Modifier.isSynchronized(method.getModifiers());
    }
}

package pw.stamina.causam.scan.method.validate;

import pw.stamina.causam.scan.*;
import java.lang.reflect.*;

public final class IllegalSubscriberMethodException extends ScanFailedException
{
    private final transient Method method;
    
    IllegalSubscriberMethodException(final Method method, final String message) {
        super(message);
        this.method = method;
    }
    
    public Method getMethod() {
        return this.method;
    }
}

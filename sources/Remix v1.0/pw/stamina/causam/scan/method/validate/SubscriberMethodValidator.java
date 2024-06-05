package pw.stamina.causam.scan.method.validate;

import java.lang.reflect.*;

public interface SubscriberMethodValidator
{
    void validate(final Method p0) throws IllegalSubscriberMethodException;
    
    default SubscriberMethodValidator standard() {
        return new StandardSubscriberMethodValidator();
    }
}

package pw.stamina.causam.scan.method.factory;

import pw.stamina.causam.publish.listen.*;
import pw.stamina.causam.publish.exception.*;
import java.lang.reflect.*;

final class MethodInvokingListener<T> implements Listener<T>
{
    private final Object handle;
    private final Method target;
    
    MethodInvokingListener(final Object handle, final Method target) {
        this.handle = handle;
        this.target = target;
    }
    
    @Override
    public void publish(final T event) throws PublicationException {
        try {
            this.target.invoke(this.handle, event);
        }
        catch (IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            throw new PublicationException(e);
        }
    }
}

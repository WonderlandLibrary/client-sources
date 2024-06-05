package pw.stamina.causam.publish.listen;

import pw.stamina.causam.publish.exception.*;

@FunctionalInterface
public interface Listener<T>
{
    void publish(final T p0) throws PublicationException;
}

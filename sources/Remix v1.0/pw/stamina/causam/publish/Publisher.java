package pw.stamina.causam.publish;

import pw.stamina.causam.subscribe.*;
import pw.stamina.causam.event.*;
import pw.stamina.causam.publish.exception.*;
import java.util.*;

public interface Publisher
{
     <T> void publish(final T p0, final Iterable<Subscription<T>> p1);
    
     <T extends Cancellable> void publishCancellable(final T p0, final Iterable<Subscription<T>> p1);
    
    default Publisher immediate() {
        return new ImmediatePublisher();
    }
    
    default Publisher exceptionHandling(final Publisher publisher, final PublicationExceptionHandler exceptionHandler) {
        Objects.requireNonNull(publisher, "publisher");
        Objects.requireNonNull(exceptionHandler, "exceptionHandler");
        return new ExceptionHandlingPublisherDecorator(publisher, exceptionHandler);
    }
}

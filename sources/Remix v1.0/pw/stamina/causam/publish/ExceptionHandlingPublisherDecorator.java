package pw.stamina.causam.publish;

import pw.stamina.causam.subscribe.*;
import pw.stamina.causam.publish.exception.*;
import pw.stamina.causam.event.*;

public final class ExceptionHandlingPublisherDecorator implements Publisher
{
    private final Publisher publisher;
    private final PublicationExceptionHandler exceptionHandlers;
    
    ExceptionHandlingPublisherDecorator(final Publisher publisher, final PublicationExceptionHandler exceptionHandler) {
        this.publisher = publisher;
        this.exceptionHandlers = exceptionHandler;
    }
    
    @Override
    public <T> void publish(final T event, final Iterable<Subscription<T>> subscriptions) {
        try {
            this.publisher.publish(event, subscriptions);
        }
        catch (PublicationException e) {
            this.exceptionHandlers.handleException(e, null);
        }
    }
    
    @Override
    public <T extends Cancellable> void publishCancellable(final T event, final Iterable<Subscription<T>> subscriptions) {
        this.publish(event, subscriptions);
    }
}

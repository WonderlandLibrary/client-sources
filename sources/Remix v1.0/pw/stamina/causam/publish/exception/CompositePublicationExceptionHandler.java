package pw.stamina.causam.publish.exception;

import java.util.*;
import java.util.function.*;

public final class CompositePublicationExceptionHandler implements PublicationExceptionHandler
{
    private final List<PublicationExceptionHandler> exceptionHandlers;
    
    CompositePublicationExceptionHandler(final List<PublicationExceptionHandler> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }
    
    @Override
    public void handleException(final PublicationException exception, final PublicationExceptionContext context) {
        this.exceptionHandlers.forEach(handler -> handler.handleException(exception, context));
    }
    
    public static CompositePublicationExceptionHandler from(final PublicationExceptionHandler... handlers) {
        Objects.requireNonNull(handlers, "handlers");
        Arrays.stream(handlers).forEach(Objects::requireNonNull);
        return new CompositePublicationExceptionHandler(Arrays.asList(handlers));
    }
}

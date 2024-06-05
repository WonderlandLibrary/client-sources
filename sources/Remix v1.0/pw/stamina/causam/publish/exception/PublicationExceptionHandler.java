package pw.stamina.causam.publish.exception;

@FunctionalInterface
public interface PublicationExceptionHandler
{
    void handleException(final PublicationException p0, final PublicationExceptionContext p1);
    
    default PublicationExceptionHandler stackTracePrinting() {
        return new StackTracePrintingPublicationExceptionHandler();
    }
}

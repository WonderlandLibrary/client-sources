package pw.stamina.causam.publish.exception;

public final class StackTracePrintingPublicationExceptionHandler implements PublicationExceptionHandler
{
    StackTracePrintingPublicationExceptionHandler() {
    }
    
    @Override
    public void handleException(final PublicationException exception, final PublicationExceptionContext context) {
        exception.printStackTrace();
    }
}

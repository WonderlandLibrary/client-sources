package pw.stamina.causam.publish.exception;

public final class PublicationException extends RuntimeException
{
    public PublicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public PublicationException(final Throwable cause) {
        super(cause);
    }
}

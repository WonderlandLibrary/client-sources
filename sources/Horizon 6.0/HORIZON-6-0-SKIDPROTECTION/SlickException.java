package HORIZON-6-0-SKIDPROTECTION;

public class SlickException extends Exception
{
    public SlickException(final String message) {
        super(message);
    }
    
    public SlickException(final String message, final Throwable e) {
        super(message, e);
    }
}

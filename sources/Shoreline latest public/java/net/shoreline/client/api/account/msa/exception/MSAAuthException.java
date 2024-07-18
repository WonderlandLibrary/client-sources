package net.shoreline.client.api.account.msa.exception;

/**
 * @author xgraza
 * @since 03/31/24
 */
public final class MSAAuthException extends Exception
{
    private final String message;

    public MSAAuthException(final String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}

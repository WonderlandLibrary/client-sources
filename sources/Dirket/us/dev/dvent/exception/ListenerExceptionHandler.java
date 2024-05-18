package us.dev.dvent.exception;

/**
 * @author Foundry
 */
@FunctionalInterface
public interface ListenerExceptionHandler {
    void handleException(ListenerExceptionContext<?> context);
}

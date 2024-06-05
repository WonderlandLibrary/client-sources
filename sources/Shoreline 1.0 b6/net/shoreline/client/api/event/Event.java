package net.shoreline.client.api.event;

/**
 * @author linus
 * @since 1.0
 */
public class Event {
    // Event cancelable state. If this value is <tt>false</tt>, then the
    // event cannot be canceled.
    private final boolean cancelable =
            getClass().isAnnotationPresent(Cancelable.class);
    // Event canceled state. If the event is canceled, then the event
    // callbacks will not run, essentially preventing the code from running.
    private boolean canceled;

    /**
     * Returns <tt>true</tt> if the event is {@link Cancelable}. Events that are
     * not cancelable do not have access to {@link #setCanceled(boolean)}.
     *
     * @return <tt>true</tt> if the event is cancelable
     * @see #cancelable
     */
    public boolean isCancelable() {
        return cancelable;
    }

    /**
     * Returns <tt>true</tt> if the event is currently canceled. This method
     * will always return <tt>false</tt> if the event is not cancelable.
     *
     * @return <tt>true</tt> if the event is canceled
     * @see #canceled
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Sets the canceled state of the event. This method can only function
     * if {@link #cancelable} is <tt>true</tt>.
     *
     * @param cancel The canceled state
     * @see #isCancelable()
     * @see #canceled
     */
    public void setCanceled(boolean cancel) {
        if (isCancelable()) {
            canceled = cancel;
        }
    }

    /**
     * Sets the canceled state of the event to <tt>true</tt> (i.e. cancels the
     * event) and prevents the callbacks from running
     *
     * @see #setCanceled(boolean)
     */
    public void cancel() {
        setCanceled(true);
    }
}

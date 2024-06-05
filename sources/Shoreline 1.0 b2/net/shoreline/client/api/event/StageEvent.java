package net.shoreline.client.api.event;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Event
 * @see EventStage
 */
public class StageEvent extends Event
{
    // The current event stage which determines which segment of the event is
    // currently running.
    private EventStage stage;

    /**
     * Returns the current {@link EventStage} of the {@link Event}.
     *
     * @return The current event stage
     */
    public EventStage getStage()
    {
        return stage;
    }

    /**
     *
     *
     * @param stage
     */
    public void setStage(EventStage stage)
    {
        this.stage = stage;
    }
}

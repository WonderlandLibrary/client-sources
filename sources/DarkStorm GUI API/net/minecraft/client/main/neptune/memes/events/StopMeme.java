package net.minecraft.client.main.neptune.memes.events;

/**
 * The most basic form of an stoppable Event.
 * Stoppable events are called seperate from other events and the calling of methods is stopped
 * as soon as the EventStoppable is stopped.
 *
 * @author DarkMagician6
 * @since 26-9-13
 */
public abstract class StopMeme implements Memevnt {

    private boolean stopped;

    /**
     * No need for the constructor to be public.
     */
    protected StopMeme() {
    }

    /**
     * Sets the stopped state to true.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Checks the stopped boolean.
     *
     * @return
     *      True if the EventStoppable is stopped.
     */
    public boolean isStopped() {
        return stopped;
    }

}

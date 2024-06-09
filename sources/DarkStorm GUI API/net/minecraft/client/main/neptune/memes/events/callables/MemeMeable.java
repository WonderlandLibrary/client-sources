package net.minecraft.client.main.neptune.memes.events.callables;

import net.minecraft.client.main.neptune.memes.events.Memeable;
import net.minecraft.client.main.neptune.memes.events.Memevnt;

/**
 * Abstract example implementation of the Cancellable interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class MemeMeable implements Memevnt, Memeable {

    private boolean cancelled;

    protected MemeMeable() {
    }

    /**
     * @see net.minecraft.client.main.neptune.memes.events.Memeable.isCancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @see net.minecraft.client.main.neptune.memes.events.Memeable.setCancelled
     */
    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

}

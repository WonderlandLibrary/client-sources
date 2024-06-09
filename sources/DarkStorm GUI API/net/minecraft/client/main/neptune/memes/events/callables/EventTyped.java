package net.minecraft.client.main.neptune.memes.events.callables;

import net.minecraft.client.main.neptune.memes.events.Memevnt;
import net.minecraft.client.main.neptune.memes.events.Typed;

/**
 * Abstract example implementation of the Typed interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class EventTyped implements Memevnt, Typed {

	private final byte type;

	/**
	 * Sets the type of the event when it's called.
	 *
	 * @param eventType
	 *            The type ID of the event.
	 */
	protected EventTyped(byte eventType) {
		type = eventType;
	}

	/**
	 * @see net.minecraft.client.main.neptune.memes.events.Typed.getType
	 */
	@Override
	public byte getType() {
		return type;
	}

}

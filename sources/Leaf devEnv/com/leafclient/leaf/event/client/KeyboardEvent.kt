package com.leafclient.leaf.event.client

import com.leafclient.leaf.utils.client.keyboard.Key
import fr.shyrogan.publisher4k.Cancellable

/**
 * Events related to the [KeyboardEvent]
 */
abstract class KeyboardEvent(val key: Key): Cancellable() {

    /**
     * Event invoked when a key is pressed
     */
    class Press(key: Key): KeyboardEvent(key)

    /**
     * Event invoked when a key is released
     */
    class Release(key: Key): KeyboardEvent(key)

}
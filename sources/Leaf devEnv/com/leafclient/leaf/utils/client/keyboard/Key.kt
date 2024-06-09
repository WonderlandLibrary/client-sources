package com.leafclient.leaf.utils.client.keyboard

import com.leafclient.leaf.event.client.KeyboardEvent
import com.leafclient.leaf.management.event.EventManager
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import org.lwjgl.input.Keyboard

/**
 * Represents a [Key] of the keyboard.
 */
class Key(val keyName: String, val keyCode: Int) {

    /**
     * Creates a [Key] instance from a [keyName]
     */
    constructor(keyName: String): this(
        keyName.toLowerCase(),
        Keyboard.getKeyIndex(keyName.toUpperCase()).let {
            if(it == Keyboard.KEY_NONE) {
                val upperName = keyName.toUpperCase()
                mouseKeys
                    .firstOrNull { it.keyName.toUpperCase().equals(upperName, true) }
                    ?.keyCode ?: it
            } else {
                it
            }
        }
    )

    /**
     * Creates a [Key] instance from the [keyCode]
     */
    constructor(keyCode: Int): this(
        if(keyCode > 0)
            Keyboard.getKeyName(keyCode).toLowerCase()
        else
            mouseKeys
                .firstOrNull { it.keyCode == keyCode }
                ?.keyName ?: "NONE",
        keyCode
    )

    val isKeyDown: Boolean
        get() = pressedKeys.contains(keyCode)

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Key

        if (keyCode != other.keyCode) return false
        return true
    }

    override fun hashCode(): Int {
        return keyCode
    }

    companion object {
        private val pressedKeys = mutableListOf<Int>()
        private val mouseKeys = mutableListOf(
            Key("LEFT_CLICK", -100),
            Key("RIGHT_CLICK", -99),
            Key("MIDDLE_CLICK", -98),
            Key("BUTTON_1", -97),
            Key("BUTTON_2", -96),
            Key("BUTTON_3", -95),
            Key("BUTTON_4", -94),
            Key("BUTTON_5", -93),
            Key("BUTTON_6", -92),
            Key("BUTTON_7", -91),
            Key("BUTTON_8", -90),
            Key("BUTTON_9", -89),
            Key("BUTTON_10", -88),
            Key("BUTTON_11", -87),
            Key("BUTTON_12", -86),
            Key("BUTTON_13", -85)
        )

        fun press(key: Key): Boolean {
            if(key.isKeyDown)
                return false

            val e = EventManager.publish(KeyboardEvent.Press(key))
            if(!e.isCancelled)
                pressedKeys += key.keyCode
            return e.isCancelled
        }

        fun release(key: Key): Boolean {
            if(!key.isKeyDown)
                return false

            val e = EventManager.publish(KeyboardEvent.Release(key))
            if(!e.isCancelled)
                pressedKeys.removeIf {
                    key.keyCode == it
                }
            return e.isCancelled
        }

        init {
            EventManager.register(this)
        }
    }

}
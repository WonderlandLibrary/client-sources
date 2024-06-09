package com.leafclient.leaf.management.setting

import com.leafclient.leaf.management.setting.contraint.Contraint
import com.leafclient.leaf.management.setting.listener.SettingListener
import com.leafclient.leaf.management.utils.Labelable
import kotlin.reflect.KProperty

/**
 * Represents a [Setting] to the client and provides information for manipulate them.
 */
class Setting<T: Any>(override val label: String, val description: String, defaultValue: T): Labelable, SettingContainer() {

    @Transient val contraints = mutableMapOf<Class<out Contraint<T>>, Contraint<T>>()

    /**
     * Contains the current value of this [Setting]
     */
    var value = defaultValue
        set(value) {
            if(field != value) {
                var futureValue = value
                contraints.forEach { (_, constriction) ->
                    futureValue = constriction.constrict(futureValue)
                }
                field = futureValue
            }
        }

    /**
     * Delegation
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    /**
     * Delegation
     */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    init {
        listener?.initialize(this)
    }

    companion object {
        var listener: SettingListener? = null
    }

}